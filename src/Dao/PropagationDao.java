package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.Propagation;
import util.DBConnection;

public class PropagationDao {
	
	//插入用户在某时刻的mood值
		public void AddPropagation(Propagation userTimeMood) {
			java.sql.Connection connection = DBConnection.getConnection();
			String sql = "insert into propagation(Tag,uid,post,friends,friendspost,timewindow) values " + "('" + 
			      userTimeMood.getTag() + 
			"','" + userTimeMood.getUid() +
			"','" + userTimeMood.getPost() +
			"','" + userTimeMood.getFriends() +
			"','" + userTimeMood.getFriendspost() +
			"','" + userTimeMood.getTimewindow() + "')";
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

}
