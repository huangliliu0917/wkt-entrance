package com.wkt.entrance.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Bs_goods;
import com.wkt.entrance.entity.PageResult;
import com.wkt.entrance.entity.Sys_permission;
import com.wkt.entrance.entity.Sys_role;
import com.wkt.entrance.mapper.Bs_goodsMapper;
import com.wkt.entrance.mapper.Sys_permissionMapper;
import com.wkt.entrance.mapper.Sys_roleMapper;
import com.wkt.entrance.service.Bs_goodsService;
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
    public RestfulResult checkGoodsThrough(String goodsID){
        if (ZmjUtil.isNullOrEmpty(goodsID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"微信群ID不能为空！");
        }
        bs_goodsService.checkGoods(goodsID, SysCode.IS_ABLE_YES);
        return RestfulResultUtils.success("成功！");
    }

    /**
     * 审核微信群不通过
     * @param goodsID
     * @return
     */
    @PostMapping("/checkGoodsNotThrough")
    public RestfulResult checkGoodsNotThrough(String goodsID){
        if (ZmjUtil.isNullOrEmpty(goodsID)){
            throw new CommonException(ErrorCode.NULL_ERROR,"微信群ID不能为空！");
        }
        bs_goodsService.checkGoods(goodsID, SysCode.IS_ABLE_NO);
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
}
