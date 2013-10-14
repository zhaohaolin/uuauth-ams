package com.uuauth.api.domain;

/**
 * ProjectInfoId generated by MyEclipse - Hibernate Tools
 */
public class Project implements java.io.Serializable {
	// Fields
	
	/**  */
	private static final long	serialVersionUID	= -6591513958821708526L;
	/**
        *
        */
	private String				name;
	private String				token;
	private String				description;
	private String				homepage;
	private String				loginurl;
	private String				loginpassword;
	private int					registerTime;
	private int					type;
	private int					status;
	
	/** default constructor */
	public Project() {
		//
	}
	
	/** full constructor */
	public Project(String name, String token, String description,
			String homepage, String loginurl, String loginpassword) {
		this.name = name;
		this.token = token;
		this.description = description;
		this.homepage = homepage;
		this.loginurl = loginurl;
		this.loginpassword = loginpassword;
	}
	
	// Property accessors
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getHomepage() {
		return this.homepage;
	}
	
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	public String getLoginurl() {
		return this.loginurl;
	}
	
	public void setLoginurl(String loginurl) {
		this.loginurl = loginurl;
	}
	
	public String getLoginpassword() {
		return this.loginpassword;
	}
	
	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}
	
	public int getRegisterTime() {
		return this.registerTime;
	}
	
	public void setRegisterTime(int registerTime) {
		this.registerTime = registerTime;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}