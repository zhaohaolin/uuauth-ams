/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeeForm.java
 * Creator:     joe.zhao
 * Create-Date: 下午10:13:40
 */
package com.uuauth.ams.controller.form;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: EmployeeForm, v 0.1 2012-5-10 下午10:13:40 Exp $
 */
public class EmployeeForm {
	
	private String	searchname		= "";
	private Integer	searchdepid		= 0;
	private Integer	searchpostid	= 0;
	private Integer	searchstatus	= 0;
	private Integer	postflag		= 1;
	
	/**
	 * Getter method for property <tt>searchname</tt>.
	 * 
	 * @return property value of searchname
	 */
	public String getSearchname() {
		return this.searchname;
	}
	
	/**
	 * Setter method for property <tt>searchname</tt>.
	 * 
	 * @param searchname
	 *            value to be assigned to property searchname
	 */
	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}
	
	/**
	 * Getter method for property <tt>searchdepid</tt>.
	 * 
	 * @return property value of searchdepid
	 */
	public Integer getSearchdepid() {
		return this.searchdepid;
	}
	
	/**
	 * Setter method for property <tt>searchdepid</tt>.
	 * 
	 * @param searchdepid
	 *            value to be assigned to property searchdepid
	 */
	public void setSearchdepid(Integer searchdepid) {
		this.searchdepid = searchdepid;
	}
	
	/**
	 * Getter method for property <tt>searchpostid</tt>.
	 * 
	 * @return property value of searchpostid
	 */
	public Integer getSearchpostid() {
		return this.searchpostid;
	}
	
	/**
	 * Setter method for property <tt>searchpostid</tt>.
	 * 
	 * @param searchpostid
	 *            value to be assigned to property searchpostid
	 */
	public void setSearchpostid(Integer searchpostid) {
		this.searchpostid = searchpostid;
	}
	
	/**
	 * Getter method for property <tt>searchstatus</tt>.
	 * 
	 * @return property value of searchstatus
	 */
	public Integer getSearchstatus() {
		return this.searchstatus;
	}
	
	/**
	 * Setter method for property <tt>searchstatus</tt>.
	 * 
	 * @param searchstatus
	 *            value to be assigned to property searchstatus
	 */
	public void setSearchstatus(Integer searchstatus) {
		this.searchstatus = searchstatus;
	}
	
	/**
	 * Getter method for property <tt>postflag</tt>.
	 * 
	 * @return property value of postflag
	 */
	public Integer getPostflag() {
		return this.postflag;
	}
	
	/**
	 * Setter method for property <tt>postflag</tt>.
	 * 
	 * @param postflag
	 *            value to be assigned to property postflag
	 */
	public void setPostflag(Integer postflag) {
		this.postflag = postflag;
	}
	
}
