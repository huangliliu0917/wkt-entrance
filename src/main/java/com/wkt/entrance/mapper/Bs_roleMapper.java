package com.wkt.entrance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wkt.entrance.entity.Bs_role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2017-12-27
 */
public interface Bs_roleMapper extends BaseMapper<Bs_role> {
    List<Bs_role> getRoleByClientID(String ClientID);
}
