package com.uuauth.ams.controller.taglib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class UrlencodeStrTag extends TagSupport {
	
	private static final long	serialVersionUID	= 1L;
	protected String			value;
	
	public int doStartTag() throws JspException {
		try {
			String out = "";
			
			if (value == null || value.equals(""))
				value = "";
			else
				out = java.net.URLEncoder.encode(value, "utf-8");
			
			pageContext.getOut().print(out);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				pageContext.getOut().print(value);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		String a = "流下";
		try {
			System.out.println(java.net.URLEncoder.encode(a, "gb2312"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
