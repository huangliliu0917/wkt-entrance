package com.wkt.entrance.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Bs_goods;
import com.wkt.entrance.mapper.Bs_goodsMapper;
import com.wkt.entrance.service.Bs_goodsService;
import com.wkt.entrance.service.Bs_personService;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.SysCode;
import org.springframework.beans.factory.annotation.Autowired;
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
    /**
     * 获取待审核商品列表
     * @return
     */
    @RequestMapping("/getWaitGoodsList")
    public RestfulResult getWaitGoodsList(int pages, int amount){
        Page page = new Page(pages,amount);
        List<Bs_goods> bs_goods = bs_goodsMapper.selectWaitGoodsList(page);
        page.setRecords(bs_goods);
        return RestfulResultUtils.success(page);
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


}
