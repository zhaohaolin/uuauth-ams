/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    LoginServlet.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:57:44
 */
package com.uuauth.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toolkit.lang.ParamUtils;
import com.uuauth.api.client.AppConst;
import com.uuauth.login.resources.SwfLoader;
import com.uuauth.login.resources.SwfObjectJsLoader;
import com.uuauth.login.resources.history.HistoryCssLoader;
import com.uuauth.login.resources.history.HistoryHtmlLoader;
import com.uuauth.login.resources.history.HistoryJsLoader;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: LoginServlet, v 0.1 2012-5-28 下午02:57:44 Exp $
 */
public class LoginServlet extends HttpServlet {
	
	/**  */
	private static final long	serialVersionUID	= -1474771546453426616L;
	private String				appToken			= "";
	private String				appPassword			= "";
	private String				url					= "";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		appToken = (String) config.getServletContext().getAttribute(
				AppConst.appToken);
		appPassword = (String) config.getServletContext().getAttribute(
				AppConst.appPassword);
		url = (String) config.getServletContext().getAttribute(AppConst.apiUrl);
		System.out.println("appPassword=" + appPassword);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}
	
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String action = "login";
		try {
			String temp = ParamUtils.getString(req, "action", null);
			if (temp != null) {
				action = temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			getClass().getMethod(
					action,
					new Class[] { HttpServletRequest.class,
							HttpServletResponse.class }).invoke(this,
					new Object[] { req, resp });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void login(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String path = req.getRequestURL().toString();
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		out.write("\r\n");
		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
		out.write("<html>\r\n");
		out.write("<head>\r\n");
		out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
		out.write("<title>戈洛博员工工作平台-登录</title>\r\n");
		out.write("<style type=\"text/css\">\r\n");
		out.write("html,body{height:100%;margin:0; padding:0;overflow: hidden} body { margin:0; padding:0; overflow:hidden; text-align:center;  background-color: #ffffff; }   #flashContent { display:none; }\r\n");
		out.write("</style>\r\n");
		out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path
				+ "?action=historyCss\" />\r\n");
		out.write("<script type=\"text/javascript\">\r\n");
		out.write("var historyFrame = '" + path + "?action=swfhtml';\r\n");
		out.write("</script>\r\n");
		out.write("<script type=\"text/javascript\" src=\"" + path
				+ "?action=historyJs\"></script>\r\n");
		out.write("<script type=\"text/javascript\" src=\"" + path
				+ "?action=swfobjectJs\"></script>\r\n");
		out.write("<script type=\"text/javascript\">\r\n");
		out.write("var closeWin=function(){if(confirm(\"您确定要关闭本页吗？\")){window.close();}}\r\n");
		out.write("var init=function(){return '{\"url\":\"" + this.url
				+ "\", \"token\":\"" + this.appToken + "\"}';}\r\n");
		out.write("var swfVersionStr = \"10.0.0\";\r\n");
		out.write("var xiSwfUrlStr = \"" + path
				+ "?action=swf&name=playerProductInstall.swf" + "\";\r\n");
		out.write("var flashvars = {};\r\n");
		out.write("var params = {};\r\n");
		out.write("params.quality = \"high\";\r\n");
		out.write("params.bgcolor = \"#ffffff\";\r\n");
		out.write("params.allowscriptaccess = \"always\";\r\n");
		out.write("params.allowfullscreen = \"true\";\r\n");
		out.write("var attributes = {};\r\n");
		out.write("attributes.id = \"login\";\r\n");
		out.write("attributes.name = \"login\";\r\n");
		out.write("attributes.align = \"middle\";\r\n");
		out.write("swfobject.embedSWF(\""
				+ path
				+ "?action=swf&name=login.swf\", \"flashContent\",\"100%\", \"100%\",swfVersionStr, xiSwfUrlStr,flashvars, params, attributes);\r\n");
		out.write("swfobject.createCSS(\"#flashContent\", \"display:block;text-align:left;\");\r\n");
		out.write("</script>\r\n");
		out.write("</head>\r\n");
		out.write("<body>\r\n");
		out.write("<div id=\"flashContent\">\r\n");
		out.write("<p>\r\n");
		out.write("To view this page ensure that Adobe Flash Player version 10.0.0 or greater is installed\r\n");
		out.write("</p>\r\n");
		out.write("<script type=\"text/javascript\">\r\n");
		out.write("var pageHost = ((document.location.protocol == \"https:\") ? \"https://\" :  \"http://\"); \r\n");
		out.write("document.write(\"<a href='http://www.adobe.com/go/getflashplayer'><img src='\"  \r\n");
		out.write("+ pageHost + \"www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>\" );  \r\n");
		out.write("</script>\r\n");
		out.write("</div>\r\n");
		out.write("<noscript>\r\n");
		out.write("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"100%\" height=\"100%\" id=\"login\">\r\n");
		out.write("<param name=\"movie\" value=\"" + path
				+ "?action=swf&name=login.swf" + "\" />\r\n");
		out.write("<param name=\"quality\" value=\"high\" />\r\n");
		out.write("<param name=\"bgcolor\" value=\"#ffffff\" />\r\n");
		out.write("<param name=\"allowScriptAccess\" value=\"always\" />\r\n");
		out.write("<param name=\"allowFullScreen\" value=\"true\" />\r\n");
		out.write("<!--[if !IE]>-->\r\n");
		out.write("<object type=\"application/x-shockwave-flash\" data=\""
				+ path + "?action=swf&name=login.swf"
				+ "\" width=\"100%\" height=\"100%\">\r\n");
		out.write("<param name=\"quality\" value=\"high\" />\r\n");
		out.write("<param name=\"bgcolor\" value=\"#ffffff\" />\r\n");
		out.write("<param name=\"allowScriptAccess\" value=\"sameDomain\" />\r\n");
		out.write("<param name=\"allowFullScreen\" value=\"true\" />\r\n");
		out.write("<!--<![endif]-->\r\n");
		out.write("<!--[if gte IE 6]>-->\r\n");
		out.write("<p>\r\n");
		out.write("Either scripts and active content are not permitted to run or Adobe Flash Player version 10.0.0 or greater is not installed.\r\n");
		out.write("</p>\r\n");
		out.write("<!--<![endif]-->\r\n");
		out.write("<a href=\"http://www.adobe.com/go/getflashplayer\">\r\n");
		out.write("<img src=\"http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif\" alt=\"Get Adobe Flash Player\" />\r\n");
		out.write("</a>\r\n");
		out.write("<!--[if !IE]>-->\r\n");
		out.write("</object>\r\n");
		out.write("<!--<![endif]-->\r\n");
		out.write("</noscript>\r\n");
		out.write("</body>\r\n");
		out.write("</html>\r\n");
		out.flush();
		out.close();
		return;
	}
	
	public void swf(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/x-shockwave-flash");
		String name = req.getParameter("name");
		SwfLoader.getFile(resp, name);
		return;
	}
	
	public void historyCss(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/css; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.print(HistoryCssLoader.getCss());
		writer.flush();
		writer.close();
		return;
	}
	
	public void historyJs(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/javascript; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.print(HistoryJsLoader.getJs());
		writer.flush();
		writer.close();
		return;
	}
	
	public void swfobjectJs(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/javascript; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.print(SwfObjectJsLoader.getJs());
		writer.flush();
		writer.close();
		return;
	}
	
	public void swfhtml(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.print(HistoryHtmlLoader.getHtml());
		writer.flush();
		writer.close();
		return;
	}
	
}
