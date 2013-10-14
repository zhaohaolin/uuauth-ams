/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    SpringContextServlet.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:05:46
 */
package com.uuauth.ams.controller;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: SpringContextServlet, v 0.1 2012-5-9 下午03:05:46 Exp $
 */
public class SpringContextServlet extends HttpServlet {
	
	/**  */
	private static final long		serialVersionUID	= -8931263224995255168L;
	protected WebApplicationContext	springContext;
	protected Logger				logger				= LoggerFactory
																.getLogger(getClass());
	
	public void init(ServletConfig config) {
		springContext = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
	}
	
}
