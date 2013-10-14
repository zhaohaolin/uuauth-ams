/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    PmsConst.java
 * Creator:     joe.zhao
 * Create-Date: 上午11:54:35
 */
package com.uuauth.aps;

/**
 * 用户角色权限常量类
 * @author joe.zhao
 * @version $Id: PmsConst, v 0.1 2012-5-20 上午11:54:35 Exp $
 */
public interface ApsPmsConst {

    /**
     * 保存到session中的用户信息key
     */
    public final static String sessionUser   = "sessionUser";
    /**
     * 保存到session中的用户角色信息key
     */
    public final static String sessionRole   = "sessionRole";
    /**
     * 保存到session中的用户权限信息key
     */
    public final static String sessionPowers = "sessionPowers";

}
