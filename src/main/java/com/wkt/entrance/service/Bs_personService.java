package com.wkt.entrance.service;

import com.baomidou.mybatisplus.service.IService;
import com.wkt.entrance.entity.Bs_person;

/**
 * <p>
 * 客户个人信息表 服务类
 * </p>
 *
 * @author zmj
 * @since 2017-12-27
 */
public interface Bs_personService extends IService<Bs_person> {
    public Bs_person findByName(String name) ;
    public String registered(Bs_person bs_person, String registerWay);
    public Boolean updatePersonInfo(Bs_person bs_person);
    Bs_person findByWXOpenID(String WXOpenID);
    /**
     * 新增人员加入角色，入参为人员ID和角色名
     * @param roleName
     * @return
     */
    public boolean addPersonAsRoleName(String ClientID, String roleName);
    public boolean updatePersonAsRoleName(String ClientID, String roleName);
    /**
     * 添加用户账户
     * @param ClientID
     * @return
     */
    public boolean addAccPerson(String ClientID);
    /**
     * 接单用户接货接口
     * @param clientID  用户ID
     * @param isTry 是否为7日体验用户
     * @return
     */
    public boolean activationPerson(String clientID ,boolean isTry);
}
