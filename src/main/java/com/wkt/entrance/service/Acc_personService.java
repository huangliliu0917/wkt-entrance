package com.wkt.entrance.service;

import com.wkt.entrance.common.CommonManager;
import com.wkt.entrance.entity.Acc_person;
import com.wkt.entrance.entity.Recharge_apply;
import com.wkt.entrance.entity.Reflect_apply;

/**
 * <p>
 * 用户账户表 服务类
 * </p>
 *
 * @author zmj
 * @since 2018-01-29
 */
public interface Acc_personService extends CommonManager<Acc_person> {
    /**
     * 获取用户余额
     * @param ClientID
     */
    public Acc_person getUserBalance(String ClientID);

    public boolean rechargeApply(Recharge_apply recharge_apply);

    public boolean reflectApply(Reflect_apply reflect_apply);
}
