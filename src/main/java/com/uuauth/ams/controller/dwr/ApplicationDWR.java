/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    ApplicationDWR.java
 * Creator:     joe.zhao
 * Create-Date: 下午09:26:15
 */
package com.uuauth.ams.controller.dwr;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.uuauth.aps.ApsPmsConst;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: ApplicationDWR, v 0.1 2012-6-17 下午09:26:15 Exp $
 */
public class ApplicationDWR extends BaseDWR implements ApsPmsConst {
	
	public ApplicationDWR() {
		super();
	}
	
	public long getOnLineNum(HttpServletRequest req) {
		ServletContext context = req.getServletContext();
		Long onLineNum = (Long) context.getAttribute(ONLINE_NUM);
		return onLineNum.longValue();
	}
	
}
