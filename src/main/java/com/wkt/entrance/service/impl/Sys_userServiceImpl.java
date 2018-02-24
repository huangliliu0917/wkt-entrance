package com.wkt.entrance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.common.CommonManagerImpl;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Sys_role;
import com.wkt.entrance.entity.Sys_role_user;
import com.wkt.entrance.entity.Sys_user;
import com.wkt.entrance.mapper.Sys_roleMapper;
import com.wkt.entrance.mapper.Sys_role_userMapper;
import com.wkt.entrance.mapper.Sys_userMapper;
import com.wkt.entrance.service.Sys_userService;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmj
 * @since 2018-01-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Sys_userServiceImpl extends CommonManagerImpl<Sys_userMapper, Sys_user> implements Sys_userService {

    @Autowired
    Sys_userMapper sys_userMapper;

    @Autowired
    Sys_roleMapper sys_roleMapper;

    @Autowired
    Sys_role_userMapper sys_role_userMapper;

    /**
     * 添加系统用户
     * @param sys_user
     * @param roleCode
     */
    @Override
    public void addSys_user(Sys_user sys_user,RoleCode roleCode) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Sys_user());
        entityWrapper.where("username ={0}",sys_user.getUsername());
        List selectList = selectList(entityWrapper);
        if(selectList!=null&&selectList.size()>0){
            throw new CommonException(ErrorCode.VERIFY_ERROR,"用户名已存在！");
        }
        //添加用户
        insert(sys_user);
        //添加权限
        addUserAsRoleName(sys_user.getId(),roleCode.getCode());
    }


    /**
     * 新增人员加入角色，入参为人员ID和角色名
     * @param roleName
     * @return
     */
    @Override
    public boolean addUserAsRoleName(Integer User_id, String roleName) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Sys_role());
        List<Sys_role> selectList = sys_roleMapper.selectList(entityWrapper.where("name={0}", roleName));
        Sys_role_user sys_role_user = new Sys_role_user();
        sys_role_user.setUser_id(User_id);
        sys_role_user.setRole_id(selectList.get(0).getId());
        sys_role_userMapper.insert(sys_role_user);
        return true;
    }

}
