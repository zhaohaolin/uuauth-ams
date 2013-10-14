/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    UserMapServiceImpl.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:59:02
 */
package com.auth.proxy.domain.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commons.lang.SecurityUtil;
import org.commons.lang.WebClient;
import org.commons.lang.XmlConverter;

import com.auth.proxy.domain.model.EmployeeInfo;
import com.auth.proxy.domain.service.UserMapService;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: UserMapServiceImpl, v 0.1 2012-5-9 下午02:59:02 Exp $
 */
public class UserMapServiceImpl implements UserMapService {

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#initUserMap(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        String url = authGetUserListUrl + "?token=" + token + "&check="
                     + SecurityUtil.MD5(password + token);
        String xml = WebClient.retrieveWebContent(url);
        try {
            xml = new String(xml.getBytes("utf-8"), "iso8859-1");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            userMap = (Map<Integer, EmployeeInfo>) XmlConverter.XML2Object(xml);
            System.out.println("init User Map ..... true");
        } catch (Exception e) {
            System.out.println("init User Map ..... false");
        }
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#cacheToken(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void cacheToken(String name, String token, String role, String id) {
        if (cacheMap == null) {
            cacheMap = new HashMap<String, Map<String, String>>();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("role", role);
        map.put("id", id);
        cacheMap.put(name, map);
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#checkToken(java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, String> checkToken(String name, String token) {
        Map<String, String> result = null;
        if (cacheMap.containsKey(name)) {
            Map<String, String> map = cacheMap.get(name);
            if (token.equals(map.get("token"))) {
                result = new HashMap<String, String>();
                result.put("role", map.get("role"));
                result.put("id", map.get("id"));
            }
            cacheMap.remove(map);
        }
        return result;
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#addUser(java.lang.String)
     */
    @Override
    public boolean addUser(String id) {
        String url = authGetUserUrl + "?id=" + id + "&token=" + token + "&check="
                     + SecurityUtil.MD5(id + password + token);
        String xml = WebClient.retrieveWebContent(url);
        try {
            xml = new String(xml.getBytes("utf-8"), "iso8859-1");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            EmployeeInfo emInfo = (EmployeeInfo) XmlConverter.XML2Object(xml);
            userMap.put(emInfo.getId(), emInfo);
            System.out.println("add User ..... true");
            return true;
        } catch (Exception e) {
            System.out.println("add User ..... false");
            return false;
        }
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#getAllUserList()
     */
    @Override
    public List<EmployeeInfo> getAllUserList() {
        return getUserList(-1);
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#getWorkingUserList()
     */
    @Override
    public List<EmployeeInfo> getWorkingUserList() {
        return getUserList(1);
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#getLeaveUserList()
     */
    @Override
    public List<EmployeeInfo> getLeaveUserList() {
        return getUserList(0);
    }

    /*
     (non-Javadoc)
     * @see com.auth.server.application.UserMapService#getUser(java.lang.Integer)
     */
    @Override
    public EmployeeInfo getUser(Integer id) {
        return userMap.get(id);
    }

    public List<EmployeeInfo> getUserList(int status) {
        List<EmployeeInfo> list = new ArrayList<EmployeeInfo>();
        for (Integer id : userMap.keySet()) {
            EmployeeInfo emInfo = userMap.get(id);
            if (status != -1) {
                if (emInfo.getStatus() != status)
                    continue;
            }
            list.add(emInfo);
        }
        return list;
    }

    public void setAuthGetUserListUrl(String authGetUserListUrl) {
        this.authGetUserListUrl = authGetUserListUrl;
    }

    public void setAuthGetUserUrl(String authGetUserUrl) {
        this.authGetUserUrl = authGetUserUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String                           authGetUserListUrl = "http://auth.uuca11.com:580/remoteauth/get_user_list";
    private String                           authGetUserUrl     = "http://auth.uuca11.com:580/remoteauth/get_user";
    private Map<Integer, EmployeeInfo>       userMap;
    private Map<String, Map<String, String>> cacheMap;
    private String                           password;
    private String                           token;

}
