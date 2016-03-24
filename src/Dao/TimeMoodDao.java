package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.UserTimeMood;
import util.DBConnection;

public class TimeMoodDao {
	
	//插入用户在某时刻的mood值
	public void AdduserTimeMood(UserTimeMood userTimeMood) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "insert into usertimemood(uid,mood,time) values " + "('" + 
		      userTimeMood.getUid() + 
		"','" + userTimeMood.getMood() +
		"','" + userTimeMood.getTime() + "')";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		//System.out.println(sql);
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.executeUpdate(sql);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
	}
	
	//查春一个用户uid的在t时刻情绪值
	public UserTimeMood QueryUserTimeMood(String uid,int time) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from usertimemood where uid=" + uid+" and time="+time;
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		//System.out.println(sql);
		UserTimeMood userTimeMood1 = new UserTimeMood();
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
						
			while (rs.next()) {
				userTimeMood1.setId(rs.getInt(1));
				userTimeMood1.setUid(rs.getString(2));
				userTimeMood1.setMood(rs.getInt(3));
				userTimeMood1.setTime(rs.getInt(4));
			}
			rs.last();
			int num=rs.getRow();
			if(num==0){//没有查询到
				userTimeMood1.setId(-404);
				return userTimeMood1;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return userTimeMood1;
	}
    
	//查询一个用户所有的情绪值
	public List<UserTimeMood> QueryUserAllMood(String uid) {
        java.sql.Connection connection = DBConnection.getConnection();
        String sql = "select * from usertimemood where uid=" + uid;
        java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
        List<UserTimeMood> userMoodlist = new ArrayList<UserTimeMood>();
        //System.out.println(sql);
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	UserTimeMood usermood = new UserTimeMood();
            	usermood.setId(rs.getInt(1));
            	usermood.setUid(rs.getString(2));
            	usermood.setMood(rs.getInt(3));
            	usermood.setTime(rs.getInt(4));
                userMoodlist.add(usermood);
               // System.out.println(usermood);
            }
            rs.last();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBConnection.close(connection, pstmt, null);
        }
        return userMoodlist;
    }
}
