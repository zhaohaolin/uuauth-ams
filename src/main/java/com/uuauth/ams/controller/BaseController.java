package com.uuauth.ams.controller;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * TODO
 * @author joe
 * @version $Id: AbstractBaseController, v 0.1 2012-3-16 下午11:25:12 Exp $
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * get the method of spring context
     *
     * @param sc
     * @return WebApplicationContext
     */
    protected final static WebApplicationContext getContext(ServletContext sc) {
        return WebApplicationContextUtils.getWebApplicationContext(sc);
    }

    /**
     * the static method of addAttribute
     *
     * @param model
     * @param name
     * @param value
     */
    protected final static void addAttribute(Model model, String name, Object value) {
        model.addAttribute(name, value);
    }

    /**
     * the static method of set value
     *
     * @param model
     * @param name
     * @param value
     */
    protected final static void setValue(Model model, String name, Object value) {
        model.addAttribute(name, value);
    }

    /**
     * forward the view page file local path
     *
     * @param jsp
     * @return
     */
    protected final static String forward(String jsp) {
        return jsp;
    }

    /**
     * redirect the url request url
     *
     * @param path
     * @return
     */
    protected final static String redirect(String path) {
        return "redirect:" + path;
    }

}
