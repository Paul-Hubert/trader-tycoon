package bean;

import java.util.InputMismatchException;

import javax.servlet.http.HttpServletRequest;

public class LoginBean {
	private String user,pass;
	
	public LoginBean() {
		
	}
	
	public void set(HttpServletRequest request) throws InputMismatchException {
		setUser(request.getParameter("user"));
		if(user == null || user == "") throw new InputMismatchException("Username not set");
		setPass(request.getParameter("pass"));
		if(pass == null || pass == "") throw new InputMismatchException("Password not set");
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}

}
