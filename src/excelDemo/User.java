package excelDemo;

public class User {
	private String org;
	private String userId;
	private String type;
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public User(String org, String userId, String type){
		this.org = org;
		this.userId = userId;
		this.type = type;
	}
}
