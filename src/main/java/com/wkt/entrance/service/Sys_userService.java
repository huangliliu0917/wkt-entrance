package com.wkt.entrance.service;

import com.wkt.entrance.common.CommonManager;
import com.wkt.entrance.entity.Sys_user;
import com.wkt.entrance.utils.sysenum.RoleCode;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zmj
 * @since 2018-01-23
 */
public interface Sys_userService extends CommonManager<Sys_user> {
    void addSys_user(Sys_user sys_user,RoleCode roleCode);

    /**
     * 新增人员加入角色，入参为人员ID和角色名
     * @param roleName
     * @return
     */
    public boolean addUserAsRoleName(Integer User_id, String roleName) ;
}
