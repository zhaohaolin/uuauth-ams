/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    SSOLoginServlet.java
 * Creator:     joe.zhao
 * Create-Date: 下午04:15:59
 */
package com.uuauth.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.toolkit.lang.ParamUtils;
import com.uuauth.login.resources.SwfLoader;
import com.uuauth.login.resources.SwfObjectJsLoader;
import com.uuauth.login.resources.history.HistoryCssLoader;
import com.uuauth.login.resources.history.HistoryHtmlLoader;
import com.uuauth.login.resources.history.HistoryJsLoader;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: SSOLoginServlet, v 0.1 2012-6-13 下午04:15:59 Exp $
 */
public class SSOLoginServlet extends HttpServlet {
	
	/**  */
	private static final long	serialVersionUID	= 6087009059319475976L;
	private String				authUrl				= "http://auth.hzglobe.com";
	private String				appToken			= "";							// 默认一个app
																					
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	
	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (!StringUtils.isEmpty(config.getInitParameter("authUrl")))
			authUrl = config.getInitParameter("authUrl");
		if (!StringUtils.isEmpty(config.getInitParameter("appToken")))
			appToken = config.getInitParameter("appToken");
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
		if (!StringUtils.isEmpty(req.getParameter("sp")))
			appToken = req.getParameter("sp");
		
		PrintWriter out = resp.getWriter();
		String path = req.getRequestURL().toString();
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		StringBuilder sb = new StringBuilder();
		String RN = "\r\n";
		
		sb.append(RN);
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
				+ RN);
		sb.append("<html>" + RN);
		sb.append("<head>" + RN);
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ RN);
		sb.append("<title>戈洛博员工工作平台-登录</title>" + RN);
		sb.append("<style type=\"text/css\">" + RN);
		sb.append("html,body{height:100%;margin:0; padding:0;overflow: hidden} body { margin:0; padding:0; overflow:hidden; text-align:center;  background-color: #ffffff; }   #flashContent { display:none; }"
				+ RN);
		sb.append("</style>" + RN);
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path
				+ "?action=historyCss\" />" + RN);
		sb.append("<script type=\"text/javascript\">" + RN);
		sb.append("var historyFrame = '" + path + "?action=swfhtml';" + RN);
		sb.append("</script>" + RN);
		sb.append("<script type=\"text/javascript\" src=\"" + path
				+ "?action=historyJs\"></script>" + RN);
		sb.append("<script type=\"text/javascript\" src=\"" + path
				+ "?action=swfobjectJs\"></script>" + RN);
		sb.append("<script type=\"text/javascript\">" + RN);
		sb.append("var closeWin=function(){if(confirm(\"您确定要关闭本页吗？\")){window.close();}}"
				+ RN);
		sb.append("var username=\"\";" + RN);
		sb.append("var password=\"\";" + RN);
		sb.append("window.onload=function(){\r\n username = getCookie('username');\r\n password = getCookie('password');\r\n }"
				+ RN);
		sb.append("var init=function(){return '{\"url\":\""
				+ this.authUrl
				+ "\", \"token\":\""
				+ appToken
				+ "\",\"username\":\"'+username+'\", \"password\":\"'+password+'\"}';\r\n}"
				+ RN);
		sb.append("var swfVersionStr = \"10.0.0\";" + RN);
		sb.append("var xiSwfUrlStr = \"" + path
				+ "?action=swf&name=playerProductInstall.swf" + "\";" + RN);
		sb.append("var flashvars = {};" + RN);
		sb.append("var params = {};" + RN);
		sb.append("params.quality = \"high\";" + RN);
		sb.append("params.bgcolor = \"#ffffff\";" + RN);
		sb.append("params.allowscriptaccess = \"always\";" + RN);
		sb.append("params.allowfullscreen = \"true\";" + RN);
		sb.append("var attributes = {};" + RN);
		sb.append("attributes.id = \"login\";" + RN);
		sb.append("attributes.name = \"login\";" + RN);
		sb.append("attributes.align = \"middle\";" + RN);
		sb.append("swfobject.embedSWF(\""
				+ path
				+ "?action=swf&name=login.swf\", \"flashContent\",\"100%\", \"100%\",swfVersionStr, xiSwfUrlStr,flashvars, params, attributes);"
				+ RN);
		sb.append("swfobject.createCSS(\"#flashContent\", \"display:block;text-align:left;\");"
				+ RN);
		// cookie function
		sb.append("function setCookie(name,value){" + RN);
		sb.append("var Days = 360;" + RN);
		sb.append("var exp  = new Date();" + RN);
		sb.append("exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);"
				+ RN);
		sb.append("document.cookie = name + \"=\"+ escape (value) + \";expires=\" + exp.toGMTString();"
				+ RN);
		sb.append("}" + RN);
		sb.append("function getCookie(name){" + RN);
		sb.append("var arr = document.cookie.match(new RegExp(\"(^| )\"+name+\"=([^;]*)(;|$)\"));"
				+ RN);
		sb.append("if(arr != null)" + RN);
		sb.append("return unescape(arr[2]); return null;" + RN);
		sb.append("}" + RN);
		sb.append("function delCookie(name){" + RN);
		sb.append("var cval=getCookie(name);" + RN);
		sb.append("if(cval!=null) " + RN);
		sb.append("document.cookie= name + \"=\"+cval+\";expires=Thu, 01-Jan-1970 00:00:01 GMT\";"
				+ RN);
		sb.append("}" + RN);
		
		sb.append("</script>" + RN);
		sb.append("</head>" + RN);
		sb.append("<body>" + RN);
		sb.append("<div id=\"flashContent\">" + RN);
		sb.append("<p>" + RN);
		sb.append("To view this page ensure that Adobe Flash Player version 10.0.0 or greater is installed"
				+ RN);
		sb.append("</p>" + RN);
		sb.append("<script type=\"text/javascript\">" + RN);
		sb.append("var pageHost = ((document.location.protocol == \"https:\") ? \"https://\" :  \"http://\"); "
				+ RN);
		sb.append("document.write(\"<a href='http://www.adobe.com/go/getflashplayer'><img src='\"  "
				+ RN);
		sb.append("+ pageHost + \"www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>\" );  "
				+ RN);
		sb.append("</script>" + RN);
		sb.append("</div>" + RN);
		sb.append("<noscript>" + RN);
		sb.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"100%\" height=\"100%\" id=\"login\">"
				+ RN);
		sb.append("<param name=\"movie\" value=\"" + path
				+ "?action=swf&name=login.swf" + "\" />" + RN);
		sb.append("<param name=\"quality\" value=\"high\" />" + RN);
		sb.append("<param name=\"bgcolor\" value=\"#ffffff\" />" + RN);
		sb.append("<param name=\"allowScriptAccess\" value=\"always\" />" + RN);
		sb.append("<param name=\"allowFullScreen\" value=\"true\" />" + RN);
		sb.append("<!--[if !IE]>-->" + RN);
		sb.append("<object type=\"application/x-shockwave-flash\" data=\""
				+ path + "?action=swf&name=login.swf"
				+ "\" width=\"100%\" height=\"100%\">" + RN);
		sb.append("<param name=\"quality\" value=\"high\" />" + RN);
		sb.append("<param name=\"bgcolor\" value=\"#ffffff\" />" + RN);
		sb.append("<param name=\"allowScriptAccess\" value=\"sameDomain\" />"
				+ RN);
		sb.append("<param name=\"allowFullScreen\" value=\"true\" />" + RN);
		sb.append("<!--<![endif]-->" + RN);
		sb.append("<!--[if gte IE 6]>-->" + RN);
		sb.append("<p>" + RN);
		sb.append("Either scripts and active content are not permitted to run or Adobe Flash Player version 10.0.0 or greater is not installed."
				+ RN);
		sb.append("</p>" + RN);
		sb.append("<!--<![endif]-->" + RN);
		sb.append("<a href=\"http://www.adobe.com/go/getflashplayer\">" + RN);
		sb.append("<img src=\"http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif\" alt=\"Get Adobe Flash Player\" />"
				+ RN);
		sb.append("</a>" + RN);
		sb.append("<!--[if !IE]>-->" + RN);
		sb.append("</object>" + RN);
		sb.append("<!--<![endif]-->" + RN);
		sb.append("</noscript>" + RN);
		sb.append("</body>" + RN);
		sb.append("</html>" + RN);
		
		out.write(sb.toString());
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
