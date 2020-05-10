package test;

public class UserBean {
	private String validName;
	private String validPassword;
	private String validVerCode;
	
	public void setValidName(String name) {
		this.validName = name;
	}
	
	
	public void setValidPassword(String password) {
		this.validPassword = password;
	}
	
	public void setValidVerCode(String verCode) {
		this.validVerCode = verCode;
	}
	
	
	public String getValidName() {
		return this.validName;
	}
	
	
	public String getValidPassword() {
		return this.validPassword;
	}
	
	public String getvalidVerCode() {
		return this.validVerCode;
	}
	
	
	public boolean isValidUsr(String name,String password,String code) {
		if(this.validName.equals(name) && this.validPassword.equals(password)&&this.validVerCode.equals(code))
			return true;
		return false;
	}
}
