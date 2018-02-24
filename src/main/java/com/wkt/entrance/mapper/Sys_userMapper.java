package com.wkt.entrance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wkt.entrance.entity.Bs_person;
import com.wkt.entrance.entity.Sys_user;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2018-01-23
 */
public interface Sys_userMapper extends BaseMapper<Sys_user> {
    Sys_user findByName(@Param("name") String name);
    Integer selectMaxId();
}
