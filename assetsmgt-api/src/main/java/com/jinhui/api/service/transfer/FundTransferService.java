package com.jinhui.api.service.transfer;


import com.github.pagehelper.PageInfo;
import com.jinhui.api.entity.po.FundTransfer;

/**
 * 充值提现服务层
 *
 * @autor wsc
 * @create 2018-03-27 11:00
 **/
public interface FundTransferService {

    //充值
    int recharge(FundTransfer fundTransfer);

    //提现
    int withdraw(FundTransfer fundTransfer);


//    PageInfo<FundTransfer> queryListBySearch(String startDate, String accountType, Integer pageNo, Integer pageSize);

    PageInfo<FundTransfer> queryRechargeListBySearch(String startDate,String endDate, String transStatus, String userName,String userId,String sysUserId,Integer pageNum, Integer pageSize);

    PageInfo<FundTransfer> queryWithdrawListBySearch(String startDate,String endDate, String transStatus, String userName,String userId,String sysUserId,Integer pageNum, Integer pageSize);
}
