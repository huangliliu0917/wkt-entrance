package com.wkt.entrance.service;

import com.wkt.entrance.entity.Recharge_apply;
import com.wkt.entrance.common.CommonManager;
import com.wkt.entrance.utils.sysenum.SysCode;

/**
 * <p>
 * 充值订单申请表 服务类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
public interface Recharge_applyService extends CommonManager<Recharge_apply> {
    public void  rechargeApplyAudit(String action_no,boolean isAudit);
}
