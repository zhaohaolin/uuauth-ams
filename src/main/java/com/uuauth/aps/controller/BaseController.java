/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    BaseController.java
 * Creator:     joe.zhao
 * Create-Date: 下午10:43:43
 */
package com.uuauth.aps.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: BaseController, v 0.1 2012-6-3 下午10:43:43 Exp $
 */
public class BaseController {
	
	public HttpSession getSession(HttpServletRequest req,
			HttpServletResponse resp, String domain) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			session = req.getSession(true);
			String session_id = session.getId();
			Cookie cookie = new Cookie("JSESSIONID", session_id);
			cookie.setDomain(domain);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		return session;
	}
	
}
