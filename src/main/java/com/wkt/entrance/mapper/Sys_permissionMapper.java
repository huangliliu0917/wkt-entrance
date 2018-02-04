package com.wkt.entrance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wkt.entrance.entity.Bs_permission;
import com.wkt.entrance.entity.Sys_permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2018-01-23
 */
@CacheConfig(cacheNames = "Sys_permission")
public interface Sys_permissionMapper extends BaseMapper<Sys_permission> {
    /**
     * @param id
     * @return
     */
    List<Sys_permission> findAllByPersonId(@Param("id") Integer id);

    /**
     * @return
     */
    @Cacheable(key = "'AllPermissions'")
    List<Sys_permission> findAll();
}
