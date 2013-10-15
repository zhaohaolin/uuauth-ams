/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetLoginTokenController.java
 * Creator:     joe.zhao
 * Create-Date: 下午04:20:47
 */
package com.uuauth.aps.controller;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toolkit.lang.SecurityUtil;
import com.uuauth.aps.domain.service.RemoteAuthService;

/**
 * 取得登录的随机token
 * @author joe.zhao
 * @version $Id: GetLoginTokenController, v 0.1 2012-5-10 下午04:20:47 Exp $
 */
@Controller
public class GetLoginTokenController implements AuthConstants {

    @RequestMapping("/remoteauth/get_login_token")
    @ResponseBody
    public String getLoginToken(HttpServletRequest req, HttpServletResponse resp) {
        //子系统的token
        String proToken = req.getParameter("proj");
        if (logger.isDebugEnabled()) {
            logger.debug("proToken=[{}]", proToken);
        }
        Random random = new Random();
        //服务端token算法
        String token = SecurityUtil.MD5(String.valueOf(random.nextLong())
            .concat(String.valueOf(random.nextLong()))
            .concat(String.valueOf(System.currentTimeMillis())));

        //保存login token到session中
        HttpSession session = req.getSession();
        session.setAttribute(CLIENT_TOKEN, token);

        //设置__token__ cookie跨域
        Cookie cookie = new Cookie(COOKIE_TOKEN, token);
        cookie.setDomain(remoteAuthService.getDomain());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);//cookie时长为1天
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);
        return token;
    }

    @Autowired(required = true)
    public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
        this.remoteAuthService = remoteAuthService;
    }

    private Logger            logger = LoggerFactory.getLogger(getClass());
    private RemoteAuthService remoteAuthService;

}
