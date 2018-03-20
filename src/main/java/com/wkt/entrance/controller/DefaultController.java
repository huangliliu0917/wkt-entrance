package com.wkt.entrance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Bs_goods_type;
import com.wkt.entrance.entity.Bs_person;
import com.wkt.entrance.mapper.Bs_personMapper;
import com.wkt.entrance.service.Bs_goods_typeService;
import com.wkt.entrance.service.Bs_personService;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.RoleCode;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author : zmj
 * @description :
 * ---------------------------------
 */
@RestController
public class DefaultController extends com.wkt.entrance.common.CommonController{

    @Autowired
    Bs_goods_typeService bs_goods_typeService;

    @Autowired
    Bs_personService bs_personService;

    @Autowired
    Bs_personMapper bs_personMapper;
    /**
     * 获取微信群类型列表
     * @return
     */
    @GetMapping("/getGoodsType")
    public RestfulResult getGoodsType(){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_goods_type());
        List list = bs_goods_typeService.selectList(entityWrapper);
        return RestfulResultUtils.success(list);
    }

    /**
     * 获取用户信息
     * @return
     */
    @PostMapping("/getUserInfoByID")
    @ResponseBody
    public RestfulResult getUserInfoByID(String ClientID){
        try {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.setEntity(new Bs_person());
            entityWrapper.where("ClientID = {0}",ClientID);
            Bs_person bs_person = bs_personService.selectOne(entityWrapper);
            bs_person.setPersonPassword(null);
            return RestfulResultUtils.success(bs_person);
        }catch (CommonException ce){
            ce.printStackTrace();
            throw ce;
        }catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(ErrorCode.UNKNOWNS_ERROR,e.getMessage());
        }
    }

    /**
     * 获取接单会员统计信息
     * @return
     */
    @GetMapping("/getMerchantsCount")
    public RestfulResult getMerchantsCount(){
        JSONObject jsonObject = new JSONObject();
        //当日注册接单会员数
        jsonObject.put("today",selectCountOfPerson(0,RoleCode.ROLE_MERCHANTS,false));
        //昨日注册接单会员数
        jsonObject.put("yesterday",selectCountOfPerson(1,RoleCode.ROLE_MERCHANTS,false));
        //本月注册接单会员数
        jsonObject.put("thisMonth",selectCountOfPerson(null,RoleCode.ROLE_MERCHANTS,true));
        //总数
        jsonObject.put("total",selectCountOfPerson(null,RoleCode.ROLE_MERCHANTS,false));
        return RestfulResultUtils.success(jsonObject);
    }

    /**
     * 获取发单会员统计信息
     * @return
     */
    @GetMapping("/getOrdinaryUserCount")
    public RestfulResult getOrdinaryUserCount(){
        JSONObject jsonObject = new JSONObject();
        //当日注册发单会员数
        jsonObject.put("today",selectCountOfPerson(0,RoleCode.ROLE_USER,false));
        //昨日注册发单会员数
        jsonObject.put("yesterday",selectCountOfPerson(1,RoleCode.ROLE_USER,false));
        //本月注册发单会员数
        jsonObject.put("thisMonth",selectCountOfPerson(null,RoleCode.ROLE_USER,true));
        //总数
        jsonObject.put("total",selectCountOfPerson(null,RoleCode.ROLE_USER,false));
        return RestfulResultUtils.success(jsonObject);
    }
}
