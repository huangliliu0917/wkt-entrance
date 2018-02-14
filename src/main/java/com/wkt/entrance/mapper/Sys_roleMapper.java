package com.wkt.entrance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wkt.entrance.entity.Sys_role;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2018-01-23
 */
public interface Sys_roleMapper extends BaseMapper<Sys_role> {
    Sys_role getRoleByUsername(@Param("username") String username);

}
