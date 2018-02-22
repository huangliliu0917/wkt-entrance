package com.wkt.entrance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wkt.entrance.entity.Acc_daybook;
import com.wkt.entrance.entity.Acc_person;
import com.wkt.entrance.entity.Reflect_apply;
import com.wkt.entrance.mapper.Acc_daybookMapper;
import com.wkt.entrance.mapper.Acc_personMapper;
import com.wkt.entrance.mapper.Reflect_applyMapper;
import com.wkt.entrance.service.Acc_personService;
import com.wkt.entrance.service.Reflect_applyService;
import com.wkt.entrance.common.CommonManagerImpl;
import com.wkt.entrance.utils.DateUtil;
import com.wkt.entrance.utils.sysenum.SysCode;
import com.wkt.entrance.utils.sysenum.TrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 提现申请表 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2018-02-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Reflect_applyServiceImpl extends CommonManagerImpl<Reflect_applyMapper, Reflect_apply> implements Reflect_applyService {

    @Autowired
    Acc_personMapper acc_personMapper;

    @Autowired
    Acc_personService acc_personService;

    @Autowired
    Acc_daybookMapper acc_daybookMapper;
    /**
     * 提现审核
     * @param action_no
     * @param isAudit true为通过 false为不通过
     */
    @Override
    public void reflectApplyAudit(String action_no,String Ali_order_number, boolean isAudit) {
        //审核
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.setEntity(new Reflect_apply());
        entityWrapper.where("Action_no = {0}",action_no);
        //获取提现信息
        Reflect_apply reflect_apply = selectOne(entityWrapper);
        reflect_apply.setAble_date(DateUtil.getNowTimestamp());
        if(isAudit){
            reflect_apply.setIsAble(SysCode.IS_ABLE_YES.getCode());
            this.update(reflect_apply,entityWrapper);
        }else {
            reflect_apply.setIsAble(SysCode.IS_ABLE_NO.getCode());
            this.update(reflect_apply,entityWrapper);
            return;
        }
        //账户减金额
        Acc_daybook acc_daybook = new Acc_daybook();
        Acc_person userBalance = acc_personService.getUserBalance(reflect_apply.getClientID());
        //记录交易前金额
        acc_daybook.setBeforeBalance(userBalance.getBalance());
        userBalance.setBalance(userBalance.getBalance()-reflect_apply.getAmt());
        EntityWrapper entityWrapper1 = new EntityWrapper();
        entityWrapper1.setEntity(new Acc_person());
        entityWrapper1.where("ClientID = {0}",reflect_apply.getClientID());
        acc_personMapper.update(userBalance,entityWrapper1);

        //记流水日志
        acc_daybook.setAli_order_number(Ali_order_number);
        acc_daybook.setAble_date(DateUtil.getNowTimestamp());
        acc_daybook.setAcc_date(DateUtil.getNowTimestamp());
        acc_daybook.setAction_no(reflect_apply.getAction_no());
        acc_daybook.setAli_account(reflect_apply.getAli_account());
        acc_daybook.setAli_name(reflect_apply.getAli_name());
        acc_daybook.setAmt(reflect_apply.getAmt());
        acc_daybook.setState(SysCode.STATE_T.getCode());
        acc_daybook.setTr_code(TrCode.REFLECT.getCode());
        acc_daybook.setNote("提现流水，用户名："+reflect_apply.getUsername()+"，提现金额："+reflect_apply.getAmt());
        //记录交易后金额
        acc_daybook.setAfterBalance(userBalance.getBalance());
        acc_daybookMapper.insert(acc_daybook);
    }
}
