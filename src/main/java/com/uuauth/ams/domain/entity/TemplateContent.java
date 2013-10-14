package com.uuauth.ams.domain.entity;

/**
 */
public class TemplateContent implements java.io.Serializable {
	// Fields
	
	/**  */
	private static final long	serialVersionUID	= -5492479172147463860L;
	/**
        *
        */
	private Integer				tmpId;
	private String				proToken;
	private String				roleName;
	
	// Constructors
	
	/** default constructor */
	public TemplateContent() {
		//
	}
	
	/** full constructor */
	public TemplateContent(Integer tmpId, String proToken, String roleName) {
		this.tmpId = tmpId;
		this.proToken = proToken;
		this.roleName = roleName;
	}
	
	// Property accessors
	public Integer getTmpId() {
		return this.tmpId;
	}
	
	public void setTmpId(Integer tmpId) {
		this.tmpId = tmpId;
	}
	
	public String getProToken() {
		return this.proToken;
	}
	
	public void setProToken(String proToken) {
		this.proToken = proToken;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
