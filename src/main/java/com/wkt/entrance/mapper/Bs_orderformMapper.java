package com.wkt.entrance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.wkt.entrance.entity.Bs_orderform;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2018-01-25
 */
public interface Bs_orderformMapper extends BaseMapper<Bs_orderform> {
    List<Bs_orderform> selectOrderFormList(Pagination page,@Param("goodType") String goodType);
}
