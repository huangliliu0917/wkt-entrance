package com.wkt.entrance.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Bs_goods;
import com.wkt.entrance.entity.Bs_person;
import com.wkt.entrance.mapper.Bs_goodsMapper;
import com.wkt.entrance.service.Bs_goodsService;
import com.wkt.entrance.service.Bs_personService;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @description : 超级管理员控制器
 * ---------------------------------
 */
@RestController
public class SupperUserController extends CommonController {
    @Autowired
    Bs_personService bs_personService;
    /**
     * 接单用户激活接口
     * @param username  用户名
     * @param isTry 是否为7日体验用户
     * @return
     */
    @PostMapping("/activationPerson")
    public RestfulResult activationPerson(String username ,boolean isTry){
        if (ZmjUtil.isNullOrEmpty(username)){
            throw new CommonException(ErrorCode.NULL_ERROR,"用户名不能为空！");
        }
        if (ZmjUtil.isNullOrEmpty(isTry)){
            throw new CommonException(ErrorCode.NULL_ERROR,"是否为7日体验用户不能为空！");
        }
        Bs_person bs_person = bs_personService.findByName(username);
        if (ZmjUtil.isNullOrEmpty(bs_person)){
            throw new CommonException(ErrorCode.NOT_FIND_USER_ERROR,"未找到该用户！");
        }
        bs_personService.activationPerson(bs_person.getClientID(),isTry);
        return RestfulResultUtils.success("激活成功！");
    }
}
