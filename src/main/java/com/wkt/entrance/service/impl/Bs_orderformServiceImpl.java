package com.wkt.entrance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Bs_orderform;
import com.wkt.entrance.entity.Recharge_apply;
import com.wkt.entrance.mapper.Bs_orderformMapper;
import com.wkt.entrance.service.Bs_orderformService;
import com.wkt.entrance.common.CommonManagerImpl;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.SysCode;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
@Service
public class Bs_orderformServiceImpl extends CommonManagerImpl<Bs_orderformMapper, Bs_orderform> implements Bs_orderformService {

    /**
     * 订单审核
     * @param SubID
     * @param isAudit true为通过 false为不通过
     */
    @Override
    public void orderFormAudit(String SubID, boolean isAudit) {
        Bs_orderform bs_orderform =  new Bs_orderform();
        bs_orderform.setSubID(SubID);
        if(isAudit){
            bs_orderform.setIsAble(SysCode.IS_ABLE_YES.getCode());
        }else {
            bs_orderform.setIsAble(SysCode.IS_ABLE_NO.getCode());
        }
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_orderform());
        entityWrapper.where("SubID = {0}",SubID);
        if(selectCount(entityWrapper)==0){
            throw new CommonException(ErrorCode.NOT_FIND_ERROR,"未找到订单号为："+SubID+"的订单!");
        }
        this.update(bs_orderform,entityWrapper);
    }
}
