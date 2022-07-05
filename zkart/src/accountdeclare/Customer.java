package accountdeclare;

import java.util.Stack;

public class Customer {
	private String userName;
	private String password;
	private String name;
	private long mobile;
	private int credit;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password.hashCode()+"";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	@Override
	public String toString() {
		return "Customer [userName=" + userName + ", password=" + password + ", name=" + name + ", mobile=" + mobile
				+ ", credit=" + credit + "]";
	}
	
	
}
