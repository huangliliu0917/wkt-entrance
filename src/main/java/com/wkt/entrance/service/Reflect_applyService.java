package com.wkt.entrance.service;

import com.wkt.entrance.entity.Reflect_apply;
import com.wkt.entrance.common.CommonManager;

/**
 * <p>
 * 提现申请表 服务类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
public interface Reflect_applyService extends CommonManager<Reflect_apply> {
    public void reflectApplyAudit(String action_no,String Ali_order_number , boolean isAudit);
}
