package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.UserFeature;
import util.DBConnection;

public class UserFeatureDao {

	// 插入用户的特征值
	public void AdduserFeature(UserFeature userFeature) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "insert into maxsubgraph(uid,post,friends,friendspost) values " + "('" + userFeature.getUid() + "','" + userFeature.getPost() + "','" + userFeature.getFriends() + "','" + userFeature.getFriendspost() + "')";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		// System.out.println(sql);
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

	// 查询最大子图中的所有用户
	public List<UserFeature> QueryUserFeature() {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from maxsubgraph";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		List<UserFeature> userFeaturelist = new ArrayList<UserFeature>();
		//System.out.println(sql);
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				UserFeature userFeature = new UserFeature();
				userFeature.setId(rs.getInt(1));
				userFeature.setUid(rs.getString(2));
				userFeature.setPost(rs.getString(3));
				userFeature.setFriends(rs.getString(4));
				userFeature.setFriendspost(rs.getString(5));

				userFeaturelist.add(userFeature);
				// System.out.println(userFeature);
			}
			rs.last();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return userFeaturelist;
	}

}
