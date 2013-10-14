/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    FrameController.java
 * Creator:     joe.zhao
 * Create-Date: 下午04:53:40
 */
package com.uuauth.ams.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: FrameController, v 0.1 2012-5-10 下午04:53:40 Exp $
 */
@Controller
public class FrameController extends BaseController {
	
	@RequestMapping("/favicon.ico")
	@ResponseBody
	public String favicon() {
		return "";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return forward("/frame");
	}
	
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header() {
		return forward("/inc/header");
	}
	
	@RequestMapping(value = "/footer", method = RequestMethod.GET)
	public String footer() {
		return forward("/inc/footer");
	}
	
	@RequestMapping(value = "/toolbar", method = RequestMethod.GET)
	public String toolbar() {
		return forward("/inc/toolbar");
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return forward("/index");
	}
	
}
