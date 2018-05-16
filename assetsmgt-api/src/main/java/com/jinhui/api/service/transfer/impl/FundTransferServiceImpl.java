package com.jinhui.api.service.transfer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.api.entity.po.FundTransfer;
import com.jinhui.api.entity.po.User;
import com.jinhui.api.mapper.FundTransferMapper;
import com.jinhui.api.service.transfer.FundTransferService;
import com.jinhui.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 充值提现服务层实现
 *
 * @autor wsc
 * @create 2018-03-27 11:06
 **/
@Service("fundTransferService")
public class FundTransferServiceImpl implements FundTransferService {

    @Autowired
    private FundTransferMapper fundTransferMapper;

    public int recharge(FundTransfer fundTransfer) {
        return fundTransferMapper.insertSelective(fundTransfer);
    }

    public int withdraw(FundTransfer fundTransfer) {
        return fundTransferMapper.insertSelective(fundTransfer);
    }

    @Override
    public PageInfo<FundTransfer> queryRechargeListBySearch(String startDate, String endDate, String transStatus, String userName,String userId,String sysUserId,Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<FundTransfer> list = fundTransferMapper.queryRechargeListBySearch(startDate,endDate,transStatus,userName,userId,sysUserId);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<FundTransfer> queryWithdrawListBySearch(String startDate, String endDate, String transStatus,String userName,String userId,String sysUserId, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<FundTransfer> list = fundTransferMapper.queryWithdrawListBySearch(startDate,endDate,transStatus,userName,userId,sysUserId);

        return new PageInfo<>(list);
    }

//    @Override
//    public PageInfo<FundTransfer> queryListBySearch(String startDate, String accountType, Integer pageNum, Integer pageSize) {
//        pageNum = pageNum == null ? 1 : pageNum;
//        pageSize = pageSize == null ? 10 : pageSize;
//        PageHelper.startPage(pageNum, pageSize);
//        User user = RedisUtils.getLocalUser();
//        List<FundTransfer> list = fundTransferMapper.queryListBySearch(startDate,accountType,user.getUserId());
//
//        return new PageInfo<>(list);
//    }
}
