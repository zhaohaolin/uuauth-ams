/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetProjectController.java
 * Creator:     joe.zhao
 * Create-Date: 上午11:45:04
 */
package com.uuauth.aps.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uuauth.ams.domain.entity.ProjectInfo;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: GetProjectController, v 0.1 2012-5-16 上午11:45:04 Exp $
 */
@Controller
public class GetProjectController {

    @RequestMapping("/remoteauth/get_proj")
    @ResponseBody
    public ProjectInfo getProjectInfo(HttpServletRequest req) {
        String token = req.getParameter("token");
        if (token == null)
            return null;
        ProjectInfo proj = remoteAuthService.getProjectInfo(token);
        if (proj == null)
            return null;
        return proj;
    }

    @Autowired(required = true)
    public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
        this.remoteAuthService = remoteAuthService;
    }

    private RemoteAuthService remoteAuthService;

}
