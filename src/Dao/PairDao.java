package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Pair;
import util.DBConnection;

public class PairDao {
		
	public void Addpair(Pair pair) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "insert into pair(user,follow) values " + "('" + 
		      pair.getUser() + 
		"','" + pair.getFollow() + "')";
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
	
	
	public List<Pair> QueryUserPair(Pair pair) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from pair where user=" + pair.getUser();
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		List<Pair> pairlist = new ArrayList<Pair>();
		//System.out.println(sql);
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Pair pair1 = new Pair();
				pair1.setId(rs.getInt(1));
				pair1.setUser(rs.getString(2));
				pair1.setFollow(rs.getString(3));
				pairlist.add(pair1);
				//System.out.println(pair1);
			}
			rs.last();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return pairlist;
	}

	/*
	 * 
	 * 
	 * public void Addinfo_id(Info info) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "insert into pre_id values " + "('" + info.getId() + "','" + info.getState() + "','" + info.getNowid() + ")";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
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
	public void UpdateInfo(Pair info) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "update pre_info set" + " var_id='" + info.getVar_id() + "',var_time='" + info.getVar_time() + "',var_site='" + info.getVar_site()+ "',var_infoid='" + info.getVar_infoid() + "'";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
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
		public void DeleteInfo(Pair info) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "delete from pre_info where var_id=" + info.getId();
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
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
	public Pair QueryPair(Pair pair) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from pre_id where user=" + pair.getUser();
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
		Pair pair1 = new Pair();
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pair1.setId(rs.getInt(1));
				pair1.setUser(rs.getString(2));
				pair1.setFollow(rs.getString(3));
			}
			rs.last();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return pair1;
	}
	public Info QueryBook(Info info) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from pre_id where id=" + info.getId();
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
		Info info1 = new Info();
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				info1.setId(rs.getString(1));
				info1.setState(rs.getString(2));
				info1.setNowid(rs.getString(3));
			}
			rs.last();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return info1;
	}*/

	/*public List<Book> QueryBook1(Book book) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from Book where bookname LIKE '%" + book.getBookname() + "%'";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
		List<Book> booklist = new ArrayList<Book>();
		Book book1 = new Book();
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				book1.setBookname(rs.getString(1));
				book1.setBooknumber(rs.getString(2));
				book1.setPressname(rs.getString(3));
				book1.setAuthor(rs.getString(4));
				book1.setTranslator(rs.getString(5));
				book1.setPrice(rs.getString(6));
				book1.setDate(rs.getString(7));
				book1.setBorrowcount(Integer.parseInt(rs.getString(8)));
				booklist.add(book1);
				// System.out.println(book);
			}
			rs.last();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return booklist;
	}

	public List<Book> QueryAllBook() {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "select * from Book order by borrowcount desc";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		List<Book> booklist = new ArrayList<Book>();
		System.out.println(sql);
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				Book book = new Book();
				book.setBookname(rs.getString(1));
				book.setBooknumber(rs.getString(2));
				book.setPressname(rs.getString(3));
				book.setAuthor(rs.getString(4));
				book.setTranslator(rs.getString(5));
				book.setPrice(rs.getString(6));
				book.setDate(rs.getString(7));
				book.setBorrowcount(Integer.parseInt(rs.getString(8)));
				booklist.add(book);
				System.out.println(book);
			}
			rs.last();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.close(connection, pstmt, null);
		}
		return booklist;
	}

	public void UpdateBookBorrowCount(Book book) {
		java.sql.Connection connection = DBConnection.getConnection();
		String sql = "update Book set " + "borrowcount=" + book.getBorrowcount() + " where booknumber='" + book.getBooknumber() + "'";
		java.sql.PreparedStatement pstmt = DBConnection.getPreparedStatement(connection, sql);
		System.out.println(sql);
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
	}*/
}
