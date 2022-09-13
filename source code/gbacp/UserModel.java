package fii.student.gbacp;

public class UserModel {

	   private int id;
	   private String username;
	   private String password;

	   public UserModel() {

	   }

	   public UserModel(int id, String userName, String password) {
		  this.id = id;
	      this.username = userName;
	      this.password = password;
	   }
	   
	   public int getId() {
		      return id;
	   }

	   public void setId(int id) {
		      this.id = id;
	   }

	   public String getUsername() {
	      return username;
	   }

	   public void setUsername(String userName) {
	      this.username = userName;
	   }

	   public String getPassword() {
	      return password;
	   }

	   public void setPassword(String password) {
	      this.password = password;
	   }
	}