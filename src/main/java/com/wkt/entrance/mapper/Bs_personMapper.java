package com.wkt.entrance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wkt.entrance.entity.Bs_person;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户个人信息表 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2017-12-27
 */
public interface Bs_personMapper extends BaseMapper<Bs_person> {
    List<Bs_person> getAll();
    Bs_person findByName(@Param("name") String name);
    Bs_person findByWXOpenID(@Param("WXOpenID") String WXOpenID);
    Boolean uploadPhoto(@Param("photo") String photo, @Param("UserName") String UserName);
    Boolean updateIsTry(Map<String,Object> map);
}
