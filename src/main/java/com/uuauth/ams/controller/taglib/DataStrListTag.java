package com.uuauth.ams.controller.taglib;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class DataStrListTag extends TagSupport {
	
	private static final long	serialVersionUID	= 1138854683229466582L;
	protected String			name;
	protected String			list;
	protected String			value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int doStartTag() throws JspException {
		try {
			if ((value == null) || value.equals("") || (name == null)
					|| name.equals("")) {
				return SKIP_BODY;
			}
			
			String listName = "list";
			
			if ((list != null) && !list.equals("")) {
				listName = list;
			}
			
			String out = "";
			Class<?> clazz = Class.forName(name);
			Field field = clazz.getField(listName);
			@SuppressWarnings("unchecked")
			Map<String, String> list = (Map<String, String>) field.get(null);
			out = list.get(value);
			
			if (out == null) {
				out = "无";
			}
			
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
	
	public String getList() {
		return list;
	}
	
	public void setList(String list) {
		this.list = list;
	}
}
