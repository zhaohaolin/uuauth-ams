/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    CheckLoginController.java
 * Creator:     joe.zhao
 * Create-Date: 下午01:14:56
 */
package com.uuauth.aps.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: CheckLoginController, v 0.1 2012-6-2 下午01:14:56 Exp $
 */
@Controller
public class CheckLoginController implements AuthConstants {

    @RequestMapping("/remoteauth/check_login")
    public void getLoginToken(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clientIp = req.getRemoteAddr();
        String proToken = req.getParameter("proj");
        String returnUrl = req.getParameter("url");
        resp.setCharacterEncoding("utf-8");
        if (StringUtils.isEmpty(proToken)) {
            //如果参数不正确，跳回登录页
            resp.getWriter().print("参数错误!");
            resp.getWriter().flush();
            resp.getWriter().close();
            return;
        }
        ProjectInfo project = projectInfoRepository.getOne(proToken);

        //验证是否已经登录
        String token = "";
        String jumpUrl = "";
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (logger.isDebugEnabled()) {
                    logger.debug("cookie=[{}, {}, {}]",
                        new Object[] { cookie.getName(), cookie.getValue(), cookie.getDomain() });
                }
                if (COOKIE_TOKEN.equals(cookie.getName())) {
                    token = cookie.getValue();
                    //说明存在__token__,继续对token值进行验证,从缓存中取
                    //如果已经登录，从auto跳回
                    EmployeeInfo employee = (EmployeeInfo) sessionEmployeeService.get(token);
                    if (logger.isDebugEnabled()) {
                        logger.debug("employee=[{}]", employee);
                    }
                    if (null != employee) {
                        employee.setSessionToken(token);
                        String role = remoteAuthService.getUserRole(project, employee.getId());
                        String autoLoginUrl = autoLoginService.getLoginUrl(employee, project, role,
                            clientIp);
                        jumpUrl = resp.encodeRedirectURL(autoLoginUrl + "&url=" + returnUrl);
                        resp.sendRedirect(jumpUrl);
                        return;
                    }

                }
            }
            //说明__token__为空，直接跳到登录页
            String appToken = project.getToken();
            resp.setCharacterEncoding("utf-8");
            resp.sendRedirect(resp.encodeURL("/auth?sp=" + appToken));
            resp.getWriter().flush();
            resp.getWriter().close();
            return;
        }
        //说明__token__为空，直接跳到登录页
        String appToken = project.getToken();
        resp.setCharacterEncoding("utf-8");
        resp.sendRedirect(resp.encodeURL("/auth?sp=" + appToken));
        resp.getWriter().flush();
        resp.getWriter().close();
        return;

    }

    @Autowired(required = true)
    public void setAutoLoginService(AutoLoginService autoLoginService) {
        this.autoLoginService = autoLoginService;
    }

    @Autowired(required = true)
    public void setProjectInfoRepository(ProjectInfoRepository projectInfoRepository) {
        this.projectInfoRepository = projectInfoRepository;
    }

    @Autowired(required = true)
    public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
        this.remoteAuthService = remoteAuthService;
    }

    @Autowired(required = true)
    public void setSessionEmployeeService(SessionEmployeeService sessionEmployeeService) {
        this.sessionEmployeeService = sessionEmployeeService;
    }

    private AutoLoginService       autoLoginService;
    private ProjectInfoRepository  projectInfoRepository;
    private RemoteAuthService      remoteAuthService;
    private SessionEmployeeService sessionEmployeeService;
    private Logger                 logger = LoggerFactory.getLogger(getClass());
}
