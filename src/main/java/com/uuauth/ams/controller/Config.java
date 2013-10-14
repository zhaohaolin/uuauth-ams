/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    Config.java
 * Creator:     joe.zhao
 * Create-Date: 下午05:37:29
 */
package com.uuauth.ams.controller;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: Config, v 0.1 2012-5-13 下午05:37:29 Exp $
 */
public class Config {
	
	public String getAuthPassword() {
		return this.authPassword;
	}
	
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	
	public String getAuthToken() {
		return this.authToken;
	}
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	private String	authPassword;
	private String	authToken;
	
}
