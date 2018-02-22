package com.wkt.entrance.service;

import com.wkt.entrance.entity.Bs_orderform;
import com.wkt.entrance.common.CommonManager;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
public interface Bs_orderformService extends CommonManager<Bs_orderform> {
    public void orderFormAudit(String SubID,boolean isAudit);
}
