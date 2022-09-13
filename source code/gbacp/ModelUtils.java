package fii.student.gbacp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fii.student.gbacp.UserModel;

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
	
	public static boolean insertCurriculum(Connection conn, String curriculumTitle, int id) throws SQLException {

	String sql = "Insert into curricula(id, title, id_users) values (seq_courses.nextval, ?, ?)";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, curriculumTitle);
	pstm.setInt(2, id);
	pstm.executeUpdate();
	return true;
	
	}

//public static Model findUser(Connection conn, String username) throws SQLException {
//
//String sql = "Select a.Id, a.Username, a.Password from users a "//
//		+ " where a.Username = ? ";
//
//PreparedStatement pstm = conn.prepareStatement(sql);
//pstm.setString(1, username);
//
//ResultSet rs = pstm.executeQuery();
//
//if (rs.next()) {
//	String password = rs.getString("Password");
//	Model user = new Model();
//	user.setUsername(username);
//	user.setPassword(password);
//	return user;
//}
//return null;
//}

//public static List<Product> queryProduct(Connection conn) throws SQLException {
//String sql = "Select a.Code, a.Name, a.Price from Product a ";
//
//PreparedStatement pstm = conn.prepareStatement(sql);
//
//ResultSet rs = pstm.executeQuery();
//List<Product> list = new ArrayList<Product>();
//while (rs.next()) {
//	String code = rs.getString("Code");
//	String name = rs.getString("Name");
//	float price = rs.getFloat("Price");
//	Product product = new Product();
//	product.setCode(code);
//	product.setName(name);
//	product.setPrice(price);
//	list.add(product);
//}
//return list;
//}
//
//public static Product findProduct(Connection conn, String code) throws SQLException {
//String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";
//
//PreparedStatement pstm = conn.prepareStatement(sql);
//pstm.setString(1, code);
//
//ResultSet rs = pstm.executeQuery();
//
//while (rs.next()) {
//	String name = rs.getString("Name");
//	float price = rs.getFloat("Price");
//	Product product = new Product(code, name, price);
//	return product;
//}
//return null;
//}
//
//public static void updateProduct(Connection conn, Product product) throws SQLException {
//String sql = "Update Product set Name =?, Price=? where Code=? ";
//
//PreparedStatement pstm = conn.prepareStatement(sql);
//
//pstm.setString(1, product.getName());
//pstm.setFloat(2, product.getPrice());
//pstm.setString(3, product.getCode());
//pstm.executeUpdate();
//}
//
//public static void insertProduct(Connection conn, Product product) throws SQLException {
//String sql = "Insert into Product(Code, Name,Price) values (?,?,?)";
//
//PreparedStatement pstm = conn.prepareStatement(sql);
//
//pstm.setString(1, product.getCode());
//pstm.setString(2, product.getName());
//pstm.setFloat(3, product.getPrice());
//
//pstm.executeUpdate();
//}
//
//public static void deleteProduct(Connection conn, String code) throws SQLException {
//String sql = "Delete From Product where Code= ?";
//
//PreparedStatement pstm = conn.prepareStatement(sql);
//
//pstm.setString(1, code);
//
//pstm.executeUpdate();
//}

	
}
