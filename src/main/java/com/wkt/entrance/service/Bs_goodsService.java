package com.wkt.entrance.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wkt.entrance.common.CommonManager;
import com.wkt.entrance.entity.Bs_goods;
import com.wkt.entrance.utils.sysenum.SysCode;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
public interface Bs_goodsService  extends CommonManager<Bs_goods> {
    /**
     * 微信群申请接口
     * @param bs_goods
     * @param imgFile
     */
    public void goodsApply(Bs_goods bs_goods, @RequestParam("file") MultipartFile imgFile);

    /**
     * 获取公共微信群列表
     * @param page
     * @param typeID
     * @param addr
     * @return
     */
    public Page<Bs_goods> showGoodsList(Page<Bs_goods> page, String typeID, String addr);

    public void uploadfileTest(MultipartFile imgFile);

    public List<Bs_goods> selectGoodsListByClientID(String ClientID);

    public void checkGoods(String goodsID,SysCode isAble );
}
