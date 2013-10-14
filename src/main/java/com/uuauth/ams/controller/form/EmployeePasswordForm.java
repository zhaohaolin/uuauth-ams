/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeePasswordForm.java
 * Creator:     joe.zhao
 * Create-Date: 下午09:53:39
 */
package com.uuauth.ams.controller.form;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: EmployeePasswordForm, v 0.1 2012-5-12 下午09:53:39 Exp $
 */
public class EmployeePasswordForm {
	
	private int		id;
	private String	pwdOld;
	private String	pwdNew;
	private String	pwdAgain;
	
	public String getPwdOld() {
		return this.pwdOld;
	}
	
	public void setPwdOld(String pwdOld) {
		this.pwdOld = pwdOld;
	}
	
	public String getPwdNew() {
		return this.pwdNew;
	}
	
	public void setPwdNew(String pwdNew) {
		this.pwdNew = pwdNew;
	}
	
	public String getPwdAgain() {
		return this.pwdAgain;
	}
	
	public void setPwdAgain(String pwdAgain) {
		this.pwdAgain = pwdAgain;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
