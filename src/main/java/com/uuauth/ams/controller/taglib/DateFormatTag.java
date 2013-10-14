package com.uuauth.ams.controller.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.toolkit.lang.DTUtil;

public class DateFormatTag extends TagSupport {
	
	private static final long	serialVersionUID	= 1138854683229466582L;
	protected String			pattern;
	protected String			value;
	
	public int doStartTag() throws JspException {
		try {
			if ((value == null) || value.equals("") || value.equals("0")) {
				return SKIP_BODY;
			}
			
			String out = "";
			out = DTUtil.fmtLongTime(Long.parseLong(value), pattern);
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
	
	public String getPattern() {
		return pattern;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
