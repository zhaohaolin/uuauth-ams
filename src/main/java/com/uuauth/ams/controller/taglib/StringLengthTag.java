package com.uuauth.ams.controller.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.toolkit.lang.AdjuctUtil;

public class StringLengthTag extends TagSupport {
	
    private static final long serialVersionUID = 1138854683229466582L;
    protected String          value;
    protected int             length;

    public int doStartTag() throws JspException {
        try {
            if ((value == null) || value.equals("")) {
                return SKIP_BODY;
            }

            String out = "";
            out = AdjuctUtil.getAdjuct(value.trim(), length);
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
