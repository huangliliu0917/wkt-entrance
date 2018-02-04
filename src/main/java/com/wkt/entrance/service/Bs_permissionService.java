package com.wkt.entrance.service;

import com.wkt.entrance.common.CommonManager;
import com.wkt.entrance.entity.Bs_permission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zmj
 * @since 2018-01-23
 */
public interface Bs_permissionService extends CommonManager<Bs_permission> {
    List<Bs_permission> findAllByPersonId(String ClientID);
}
