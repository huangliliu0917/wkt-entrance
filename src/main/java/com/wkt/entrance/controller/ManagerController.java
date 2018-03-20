package com.wkt.entrance.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.*;
import com.wkt.entrance.mapper.Bs_goodsMapper;
import com.wkt.entrance.mapper.Bs_orderformMapper;
import com.wkt.entrance.mapper.Sys_permissionMapper;
import com.wkt.entrance.mapper.Sys_roleMapper;
import com.wkt.entrance.service.Bs_goodsService;
import com.wkt.entrance.service.Bs_orderformService;
import com.wkt.entrance.service.Bs_personService;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.SysCode;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * @description :管理员控制器
 * ---------------------------------
 */
@RestController
public class ManagerController extends CommonController {
    @Autowired
    Bs_goodsMapper bs_goodsMapper;

    @Autowired
    Bs_goodsService bs_goodsService;

    @Autowired
    Bs_personService bs_personService;

    @Autowired
    Sys_permissionMapper sys_permissionMapper;

    @Autowired
    Sys_roleMapper sys_roleMapper;

    @Autowired
    Bs_orderformMapper bs_orderformMapper;

    @Autowired
    Bs_orderformService bs_orderformService;
    /**
     * 获取待审核商品列表
     * @return
     */
    @RequestMapping("/getWaitGoodsList")
    public RestfulResult getWaitGoodsList(int pages, int amount){
        Page page = new Page(pages,amount);
        List<Bs_goods> bs_goods = bs_goodsMapper.selectWaitGoodsList(page);
        page.setRecords(bs_goods);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_goods());
        entityWrapper.where("IsAble = {0}",SysCode.IS_ABLE_WAIT.getCode());
        entityWrapper.orderBy("IsAble",false);
        Integer selectCount = bs_goodsMapper.selectCount(entityWrapper);
        PageResult pageResult = new PageResult(selectCount,page);
        return RestfulResultUtils.success(pageResult);
    }

    /**
     * 审核微信群通过
     * @param goodsID
     * @return
     */
    @PostMapping("/checkGoodsThrough")
    public RestfulResult checkGoodsThrough(String[] goodsID){
        if(ZmjUtil.isNullOrEmpty(goodsID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"微信群ID不能为空！");
        }
        for (String s:goodsID) {
            System.out.println(s);
            bs_goodsService.checkGoods(s, SysCode.IS_ABLE_YES);
        }

        return RestfulResultUtils.success("成功！");
    }

    /**
     * 审核微信群不通过
     * @param goodsID
     * @return
     */
    @PostMapping("/checkGoodsNotThrough")
    public RestfulResult checkGoodsNotThrough(String[] goodsID){
        if (ZmjUtil.isNullOrEmpty(goodsID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"微信群ID不能为空！");
        }
        for (String s:goodsID) {
            System.out.println(s);
            bs_goodsService.checkGoods(s, SysCode.IS_ABLE_NO);
        }
        return RestfulResultUtils.success("成功！");
    }

    /**
     * 获取用户权限
     * @return
     * @throws Exception
     */
    @GetMapping("/getPermission")
    public RestfulResult getPermission() throws Exception {
        return RestfulResultUtils.success(sys_permissionMapper.findAllByPersonId(this.getThisUser().getId()));
    }

    /**
     * 获取用户角色
     * @return
     * @throws Exception
     */
    @GetMapping("/getRole")
    public RestfulResult getRole() throws Exception {
        return RestfulResultUtils.success(sys_roleMapper.getRoleByUsername(this.getThisUser().getUsername()));
    }

    /**
     * 获取菜单
     * @return
     * @throws Exception
     */
    @GetMapping("/showMenu")
    public RestfulResult showMenu() throws Exception {
        return RestfulResultUtils.success(sys_permissionMapper.getMenuByPersonId(this.getThisUser().getId()));
    }

    /**
     * 获取待审核订单列表
     * @return
     */
    @RequestMapping("/getOrderFormList")
    public RestfulResult getOrderFormList(int pages, int size,String goodType){
        Page page = new Page(pages,size);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_orderform());
        entityWrapper.like("GTypeID",goodType, SqlLike.DEFAULT);
        if(ZmjUtil.isNullOrEmpty(goodType)){
            goodType="";
        }
        List<Bs_orderform> bs_orderforms = bs_orderformMapper.selectOrderFormList(page,goodType);
        page.setRecords(bs_orderforms);
        Integer selectCount = bs_orderformMapper.selectCount(entityWrapper);
        PageResult pageResult = new PageResult(selectCount,page);
        return RestfulResultUtils.success(pageResult);
    }

    /**
     * 订单审核通过
     * @return
     */
    @PostMapping("/orderFormAudit")
    public RestfulResult orderFormAudit(String SubID){
        if(ZmjUtil.isNullOrEmpty(SubID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"SubID不能为空！");
        }
        bs_orderformService.orderFormAudit(SubID,true);
        return RestfulResultUtils.success("审核成功！");
    }

    /**
     * 批量订单审核通过
     * @param SubID
     * @return
     */
    @PostMapping("/batchOrderFormAudit")
    public RestfulResult batchOrderFormAudit(String[] SubID){
        if(ZmjUtil.isNullOrEmpty(SubID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"SubID不能为空！");
        }
        for (String s:SubID) {
            System.out.println(s);
            bs_orderformService.orderFormAudit(s,true);
        }
        return RestfulResultUtils.success("审核成功！");
    }

    /**
     * 订单审核不通过
     * @return
     */
    @PostMapping("/orderFormNotAudit")
    public RestfulResult orderFormNotAudit(String SubID){
        if(ZmjUtil.isNullOrEmpty(SubID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"SubID不能为空！");
        }
        bs_orderformService.orderFormAudit(SubID,false);
        return RestfulResultUtils.success("审核不通过成功！");
    }

    /**
     * 批量订单审核不通过
     * @param SubID
     * @return
     */
    @PostMapping("/batchOrderFormNotAudit")
    public RestfulResult batchOrderFormNotAudit(String[] SubID){
        if(ZmjUtil.isNullOrEmpty(SubID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"SubID不能为空！");
        }
        for (String s:SubID) {
            System.out.println(s);
            bs_orderformService.orderFormAudit(s,false);
        }
        return RestfulResultUtils.success("审核成功！");
    }

}
