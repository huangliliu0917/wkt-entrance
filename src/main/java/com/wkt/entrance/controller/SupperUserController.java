package com.wkt.entrance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.*;
import com.wkt.entrance.mapper.Bs_goodsMapper;
import com.wkt.entrance.mapper.Recharge_applyMapper;
import com.wkt.entrance.mapper.Reflect_applyMapper;
import com.wkt.entrance.service.*;
import com.wkt.entrance.utils.MD5Util;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.RoleCode;
import com.wkt.entrance.utils.sysenum.SysCode;
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

    @Autowired
    Recharge_applyService recharge_applyService;

    @Autowired
    Reflect_applyService reflect_applyService;

    @Autowired
    Sys_userService sys_userService;
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


    /**
     * 充值待审核列表
     * @return
     */
    @PostMapping("/getRechargeApplyList")
    public RestfulResult getRechargeApplyList(int pages, int size){
        Page page = new Page(pages,size);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Recharge_apply());
        entityWrapper.orderBy("IsAble",false).orderBy("Apply_date",false);
        Page selectPage = recharge_applyService.selectPage(page, entityWrapper);
        Integer count = recharge_applyService.selectCount(entityWrapper);
        PageResult pageResult = new PageResult(count,selectPage);
        return RestfulResultUtils.success(pageResult);
    }

    /**
     * 充值审核通过
     * @return
     */
    @PostMapping("/rechargeApplyAudit")
    public RestfulResult rechargeApplyAudit(String action_no){
        if(ZmjUtil.isNullOrEmpty(action_no)){
            throw new CommonException(ErrorCode.NULL_ERROR,"action_no不能为空！");
        }
        recharge_applyService.rechargeApplyAudit(action_no,true);
        return RestfulResultUtils.success("审核成功！");
    }

    /**
     * 充值待审核不通过
     * @return
     */
    @PostMapping("/rechargeApplyNotAudit")
    public RestfulResult rechargeApplyNotAudit(String action_no){
        if(ZmjUtil.isNullOrEmpty(action_no)){
            throw new CommonException(ErrorCode.NULL_ERROR,"action_no不能为空！");
        }
        recharge_applyService.rechargeApplyAudit(action_no,false);
        return RestfulResultUtils.success("审核不通过成功！");
    }

    /**
     * 提现待审核列表
     * @return
     */
    @PostMapping("/getReflect_applyList")
    public RestfulResult getReflect_applyList(int pages, int size){
        Page page = new Page(pages,size);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Reflect_apply());
        entityWrapper.orderBy("IsAble",false).orderBy("Apply_date",false);;
        Page selectPage = reflect_applyService.selectPage(page, entityWrapper);
        Integer count = reflect_applyService.selectCount(entityWrapper);
        PageResult pageResult = new PageResult(count,selectPage);
        return RestfulResultUtils.success(pageResult);
    }

    /**
     * 提现审核通过
     * @return
     */
    @PostMapping("/reflect_applyAudit")
    public RestfulResult reflect_applyAudit(String action_no,String Ali_order_number){
        if(ZmjUtil.isNullOrEmpty(action_no)){
            throw new CommonException(ErrorCode.NULL_ERROR,"action_no不能为空！");
        }
        if (ZmjUtil.isNullOrEmpty(Ali_order_number)){
            throw new CommonException(ErrorCode.NULL_ERROR,"淘宝订单号不能为空！");
        }
        reflect_applyService.reflectApplyAudit(action_no,Ali_order_number,true);
        return RestfulResultUtils.success("审核成功！");
    }

    /**
     * 提现审核不通过
     * @return
     */
    @PostMapping("/reflect_applyNotAudit")
    public RestfulResult reflect_applyNotAudit(String action_no){
        if(ZmjUtil.isNullOrEmpty(action_no)){
            throw new CommonException(ErrorCode.NULL_ERROR,"action_no不能为空！");
        }
        reflect_applyService.reflectApplyAudit(action_no,null,false);
        return RestfulResultUtils.success("审核不通过成功！");
    }

    /**
     * 添加合伙人
     * @param sys_user
     * @return
     */
    @PostMapping("/addPartner")
    public RestfulResult addPartner(Sys_user sys_user ){
        if(ZmjUtil.isNullOrEmpty(sys_user.getUsername())){
            throw new CommonException(ErrorCode.VERIFY_ERROR,"用户名不能为空！");
        }
        if(ZmjUtil.isNullOrEmpty(sys_user.getPassword())){
            throw new CommonException(ErrorCode.VERIFY_ERROR,"密码不能为空！");
        }else {
            sys_user.setPassword(MD5Util.encode(sys_user.getPassword()));
        }
        //添加合伙人
        sys_userService.addSys_user(sys_user, RoleCode.ROLE_PARTNER);
        return RestfulResultUtils.success("添加成功！");
    }

    /**
     * 添加管理员
     * @param sys_user
     * @return
     */
    @PostMapping("/addAdminUser")
    public RestfulResult addAdminUser(Sys_user sys_user ){
        if(ZmjUtil.isNullOrEmpty(sys_user.getUsername())){
            throw new CommonException(ErrorCode.VERIFY_ERROR,"用户名不能为空！");
        }
        if(ZmjUtil.isNullOrEmpty(sys_user.getPassword())){
            throw new CommonException(ErrorCode.VERIFY_ERROR,"密码不能为空！");
        }else {
            sys_user.setPassword(MD5Util.encode(sys_user.getPassword()));
        }
        //添加管理员
        sys_userService.addSys_user(sys_user, RoleCode.ROLE_ADMIN_USER);
        return RestfulResultUtils.success("添加成功！");
    }
}
