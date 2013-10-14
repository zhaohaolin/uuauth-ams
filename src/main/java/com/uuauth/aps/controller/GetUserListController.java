/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetUserListController.java
 * Creator:     joe.zhao
 * Create-Date: 下午05:03:50
 */
package com.uuauth.aps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toolkit.lang.SecurityUtil;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: GetUserListController, v 0.1 2012-5-10 下午05:03:50 Exp $
 */
@Controller
public class GetUserListController {

    @RequestMapping("/remoteauth/get_user_list")
    @ResponseBody
    public List<EmployeeInfo> getUserList(HttpServletRequest req) {
        String token = req.getParameter("token");
        String check = req.getParameter("check");
        String suffix = req.getParameter("suffix");

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(check))
            return null;

        //把suffix保存到内存缓存中
        if (!StringUtils.isEmpty(suffix))
            uuAuthAPIService.addSuffixToCache(token, suffix);

        ProjectInfo proj = remoteAuthService.getProjectInfo(token);
        if (proj == null)
            return null;
        if (!check.equals(SecurityUtil.MD5(proj.getLoginpassword() + proj.getToken())))
            return null;
        List<EmployeeInfo> list = employeeInfoRepository.list(null, 0, 0, 0);
        for (EmployeeInfo employee : list) {
            //禁止在网络上传递密码
            employee.setLoginPassword("");
        }
        //向验证中心注册子系统,为了不影响接口性能，采用异步方式执行

        return list;
    }

    @Autowired(required = true)
    public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
        this.remoteAuthService = remoteAuthService;
    }

    @Autowired(required = true)
    public void setEmployeeInfoRepository(EmployeeInfoRepository employeeInfoRepository) {
        this.employeeInfoRepository = employeeInfoRepository;
    }

    private RemoteAuthService      remoteAuthService;
    private EmployeeInfoRepository employeeInfoRepository;
    private UUAuthAPIService       uuAuthAPIService = UUAuthAPIService.getInstance();

}
