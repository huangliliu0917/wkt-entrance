package com.wkt.entrance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.entity.Acc_daybook;
import com.wkt.entrance.entity.Acc_person;
import com.wkt.entrance.entity.Recharge_apply;
import com.wkt.entrance.mapper.Acc_daybookMapper;
import com.wkt.entrance.mapper.Acc_personMapper;
import com.wkt.entrance.mapper.Recharge_applyMapper;
import com.wkt.entrance.service.Acc_personService;
import com.wkt.entrance.service.Recharge_applyService;
import com.wkt.entrance.common.CommonManagerImpl;
import com.wkt.entrance.utils.DateUtil;
import com.wkt.entrance.utils.sysenum.SysCode;
import com.wkt.entrance.utils.sysenum.TrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 充值订单申请表 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Recharge_applyServiceImpl extends CommonManagerImpl<Recharge_applyMapper, Recharge_apply> implements Recharge_applyService {

    @Autowired
    Acc_personMapper acc_personMapper;

    @Autowired
    Acc_personService acc_personService;

    @Autowired
    Acc_daybookMapper acc_daybookMapper;
    /**
     * 充值审核
     * @param action_no 流水号
     * @param isAudit true为通过 false为不通过
     */
    @Override
    public void rechargeApplyAudit(String action_no,boolean isAudit) {
        //审核
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Recharge_apply());
        entityWrapper.where("Action_no = {0}",action_no);
        //获取充值信息
        Recharge_apply recharge_apply = selectOne(entityWrapper);
        if(isAudit){
            recharge_apply.setIsAble(SysCode.IS_ABLE_YES.getCode());
            this.update(recharge_apply,entityWrapper);
        }else {
            recharge_apply.setIsAble(SysCode.IS_ABLE_NO.getCode());
            this.update(recharge_apply,entityWrapper);
            return;
        }
        //账户加金额
        Acc_daybook acc_daybook = new Acc_daybook();
        Acc_person userBalance = acc_personService.getUserBalance(recharge_apply.getClientID());
        //记录交易前金额
        acc_daybook.setBeforeBalance(userBalance.getBalance());
        userBalance.setBalance(userBalance.getBalance()+recharge_apply.getAmt());
        EntityWrapper entityWrapper1 = new EntityWrapper();
        entityWrapper1.setEntity(new Acc_person());
        entityWrapper1.where("ClientID = {0}",recharge_apply.getClientID());
        acc_personMapper.update(userBalance,entityWrapper1);

        //记流水日志

        acc_daybook.setAble_date(DateUtil.getNowTimestamp());
        acc_daybook.setAcc_date(DateUtil.getNowTimestamp());
        acc_daybook.setAction_no(recharge_apply.getAction_no());
        acc_daybook.setAli_order_number(recharge_apply.getAli_order_number());
        acc_daybook.setAmt(recharge_apply.getAmt());
        acc_daybook.setState(SysCode.STATE_T.getCode());
        acc_daybook.setTr_code(TrCode.RACHARGE.getCode());
        acc_daybook.setNote("充值流水，用户名："+recharge_apply.getUsername()+"，充值金额："+recharge_apply.getAmt());
        //记录交易后金额
        acc_daybook.setAfterBalance(userBalance.getBalance());
        acc_daybookMapper.insert(acc_daybook);
    }
}
