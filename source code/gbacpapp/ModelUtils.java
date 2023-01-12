package fii.student.gbacpapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelUtils {

	public static UserModel findUser(Connection conn, String username, String password) throws SQLException {

	String sql = "Select a.Id, a.Username, a.Password from users a " //
			+ " where a.Username = ? and a.password= ?";
	
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, username);
	pstm.setString(2, password);
	ResultSet rs = pstm.executeQuery();
	
	if (rs.next()) {
		int id = rs.getInt("Id");
		UserModel user = new UserModel();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
	return null;
	}
	
	public static boolean insertUser(Connection conn, String username, String password) throws SQLException {

	String sql = "Select a.Id, a.Username, a.Password from users a " //
			+ " where a.Username = ? and a.password= ?";
	
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, username);
	pstm.setString(2, password);
	ResultSet rs = pstm.executeQuery();
	
	if (!rs.isBeforeFirst() ) {
		sql = "Insert into users(id, username, password) values (seq_users.nextval, ?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, username);
		pstm.setString(2, password);
		pstm.executeUpdate();
		return true;
	}
	
	return false;
	}
	
}
