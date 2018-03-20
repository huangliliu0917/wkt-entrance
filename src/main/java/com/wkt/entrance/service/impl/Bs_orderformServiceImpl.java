package com.wkt.entrance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Acc_daybook;
import com.wkt.entrance.entity.Acc_person;
import com.wkt.entrance.entity.Bs_orderform;
import com.wkt.entrance.entity.Recharge_apply;
import com.wkt.entrance.mapper.Acc_daybookMapper;
import com.wkt.entrance.mapper.Acc_personMapper;
import com.wkt.entrance.mapper.Bs_orderformMapper;
import com.wkt.entrance.service.Acc_personService;
import com.wkt.entrance.service.Bs_orderformService;
import com.wkt.entrance.common.CommonManagerImpl;
import com.wkt.entrance.service.Bs_personService;
import com.wkt.entrance.utils.DateUtil;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.SysCode;
import com.wkt.entrance.utils.sysenum.TrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Bs_orderformServiceImpl extends CommonManagerImpl<Bs_orderformMapper, Bs_orderform> implements Bs_orderformService {

    @Autowired
    Bs_orderformService bs_orderformService;

    @Autowired
    Acc_personMapper acc_personMapper;

    @Autowired
    Acc_personService acc_personService;

    @Autowired
    Acc_daybookMapper acc_daybookMapper;

    @Autowired
    Bs_personService bs_personService;

    /**
     * 订单审核
     * @param SubID
     * @param isAudit true为通过 false为不通过
     */
    @Override
    public void orderFormAudit(String SubID, boolean isAudit) {
        //获取订单信息
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Bs_orderform());
        entityWrapper.where("SubID = {0}",SubID);
        Bs_orderform bs_orderform = bs_orderformService.selectOne(entityWrapper);
        if(ZmjUtil.isNullOrEmpty(bs_orderform)){
            throw new CommonException(ErrorCode.NOT_FIND_ERROR,"未找到订单号为："+SubID+"的订单!");
        }
        if(isAudit){
            bs_orderform.setIsAble(SysCode.IS_ABLE_YES.getCode());
        }else {
            bs_orderform.setIsAble(SysCode.IS_ABLE_NO.getCode());
        }

        //记录退款钱金额
        //记录交易前金额
        Acc_daybook acc_daybook = new Acc_daybook();
        Acc_person userBalance = acc_personService.getUserBalance(bs_orderform.getClientID());
        acc_daybook.setBeforeBalance(userBalance.getBalance());
        //代扣退款
        userBalance.setBalance(userBalance.getBalance()+bs_orderform.getSpPrice());
        EntityWrapper entityWrapper1 = new EntityWrapper();
        entityWrapper1.setEntity(new Acc_person());
        entityWrapper1.where("ClientID = {0}",bs_orderform.getClientID());
        acc_personMapper.update(userBalance,entityWrapper1);

        //记流水日志
        acc_daybook.setAble_date(DateUtil.getNowTimestamp());
        acc_daybook.setAcc_date(DateUtil.getNowTimestamp());
        //记录订单编号
        acc_daybook.setSubID(bs_orderform.getSubID());
        //生成流水号
        acc_daybook.setAction_no(ZmjUtil.getOrderIdByUUId());
        acc_daybook.setAmt((bs_orderform.getSpPrice()));
        acc_daybook.setState(SysCode.STATE_T.getCode());
        //退款交易代码
        acc_daybook.setTr_code(TrCode.REFUND.getCode());
        //贷方ID
        acc_daybook.setCreditClientID(bs_orderform.getClientID());
        String userName = bs_personService.findByClientID(bs_orderform.getClientID()).getUserName();
        acc_daybook.setNote("代扣退款流水，用户名："+userName+"，退款金额："+acc_daybook.getAmt());

        //记录交易后金额
        acc_daybook.setAfterBalance(userBalance.getBalance());
        acc_daybookMapper.insert(acc_daybook);

        //更新订单表
        this.update(bs_orderform,entityWrapper);
    }
}
