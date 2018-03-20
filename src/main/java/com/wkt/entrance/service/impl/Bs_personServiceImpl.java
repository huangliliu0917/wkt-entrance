package com.wkt.entrance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.common.CommonManagerImpl;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.*;
import com.wkt.entrance.mapper.*;
import com.wkt.entrance.service.Bs_personService;
import com.wkt.entrance.utils.DateUtil;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.RoleCode;
import com.wkt.entrance.utils.sysenum.SysCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 客户个人信息表 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2017-12-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "Bs_person")
public class Bs_personServiceImpl extends CommonManagerImpl<Bs_personMapper, Bs_person> implements Bs_personService {
    @Autowired
    Bs_personMapper bs_personMapper;

    @Autowired
    Bs_role_personMapper bs_role_personMapper;

    @Autowired
    Bs_roleMapper bs_roleMapper;

    @Autowired
    Acc_personMapper acc_personMapper;

    @Autowired
    Bs_person_goods_listMapper bs_person_goods_listMapper;

    /**
     * 根据账户名获取用户信息
     * @param name
     * @return
     */
    @Override
    @Cacheable(key = "#root.caches[0].name + '.name:'+ #name")
    public Bs_person findByName(String name) {
        Bs_person bs_person = new Bs_person();
        bs_person.setUserName(name);
        return bs_personMapper.selectOne(bs_person);
    }

    /**
     * 注册功能
     * @param bs_person
     * @param registerWay
     * @return
     */
    @Override
    public String registered(Bs_person bs_person,String registerWay) {
        bs_person.setClientID(registerWay+"_"+UUID.randomUUID().toString().replaceAll("-", ""));
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_person());
        entityWrapper.where("UserName ={0}",bs_person.getUserName());
        List selectList = bs_personMapper.selectList(entityWrapper);
        if(selectList!=null&&selectList.size()>0){
            throw new CommonException(ErrorCode.VERIFY_ERROR,"用户名已存在！");
        }
        bs_personMapper.insert(bs_person);
        //默认权限
        addPersonAsRoleName(bs_person.getClientID(), RoleCode.ROLE_USER.getCode());
        //添加账户表
        addAccPerson(bs_person.getClientID());
        return bs_person.getClientID();
    }

    /**
     * 更新用户信息
     * @param bs_person
     * @return
     */
    @Override
    public Boolean updatePersonInfo(Bs_person bs_person) {
        try {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.setEntity(new Bs_person());
            entityWrapper.where("ClientID ={0}",bs_person.getClientID());
            bs_personMapper.update(bs_person,entityWrapper);
            return true;
        }catch (Exception e){
            throw new CommonException(ErrorCode.DATEBASE_ERRPR,e.getMessage());
        }
    }

    @Override
    @Cacheable(key = "#root.caches[0].name + '.WXOpenID:'+ #WXOpenID")
    public Bs_person findByWXOpenID(String WXOpenID) {
        return bs_personMapper.findByWXOpenID(WXOpenID);
    }

    /**
     * 新增人员加入角色，入参为人员ID和角色名
     * @param roleName
     * @return
     */
    @Override
    public boolean addPersonAsRoleName(String ClientID, String roleName) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_role());
        List<Bs_role> selectList = bs_roleMapper.selectList(entityWrapper.where("name={0}", roleName));
        Bs_role_person bs_role_person = new Bs_role_person();
        bs_role_person.setPerson_id(ClientID);
        bs_role_person.setRole_id(selectList.get(0).getId());
        bs_role_personMapper.insert(bs_role_person);
        return true;
    }

    /**
     * 新增人员加入角色，入参为人员ID和角色名
     * @param roleName
     * @return
     */
    @Override
    public boolean updatePersonAsRoleName(String ClientID, String roleName) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_role());
        List<Bs_role> selectList = bs_roleMapper.selectList(entityWrapper.where("name={0}", roleName));
        Bs_role_person bs_role_person = new Bs_role_person();
        bs_role_person.setPerson_id(ClientID);
        bs_role_person.setRole_id(selectList.get(0).getId());
        EntityWrapper entityWrapper2 = new EntityWrapper();
        entityWrapper.setEntity(new Bs_role_person());
        bs_role_personMapper.update(bs_role_person,entityWrapper2.where("Person_id = {0}",ClientID));
        return true;
    }

    /**
     * 添加用户账户
     * @param ClientID
     * @return
     */
    @Override
    public boolean addAccPerson(String ClientID){
        Acc_person acc_person = new Acc_person();
        acc_person.setBalance(0);
        acc_person.setClientID(ClientID);
        acc_personMapper.insert(acc_person);
        return true;
    }

    /**
     * 接单用户接货接口
     * @param clientID  用户ID
     * @param isTry 是否为7日体验用户
     * @return
     */
    @Override
    public boolean activationPerson(String clientID, boolean isTry) {
        Integer intIsTry;
        if(isTry){
            intIsTry= SysCode.IS_TRY_YES.getCode();
        }else {
            intIsTry= SysCode.IS_TRY_NO.getCode();
        }
        Map<String,Object> map = new HashMap();
        map.put("clientID",clientID);
        map.put("isTry",intIsTry);
        map.put("activationTime", DateUtil.getNowTimestamp());
        bs_personMapper.updateIsTry(map);
        this.updatePersonAsRoleName(clientID,RoleCode.ROLE_MERCHANTS.getCode());
        return true;
    }

    /**
     * 根据ID获取person对象
     * @param ClientID
     * @return
     */
    @Override
    public Bs_person findByClientID(String ClientID) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_person());
        entityWrapper.where("ClientID = {0}",ClientID);
        List<Bs_person> selectList = bs_personMapper.selectList(entityWrapper);
        if(ZmjUtil.isNullOrEmpty(selectList)){
            throw new CommonException(ErrorCode.NOT_FIND_USER_ERROR);
        }
        return selectList.get(0);
    }
}
