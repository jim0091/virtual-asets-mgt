package com.jinhui.api.service.platform.impl;


import com.jinhui.api.entity.po.Wallet;
import com.jinhui.api.entity.po.WalletHistory;
import com.jinhui.api.entity.vo.CheckPlatformVo;
import com.jinhui.api.mapper.FundTransferMapper;
import com.jinhui.api.mapper.WalletHistoryMapper;
import com.jinhui.api.mapper.WalletMapper;
import com.jinhui.api.mapper.WalletTxMapper;
import com.jinhui.api.service.platform.PlatformService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27 0027.
 */
@Service
public class PlatformServiceImpl implements PlatformService {


    private final static BigDecimal wei18 = new BigDecimal("1000000000000000000");


    @Autowired
    private FundTransferMapper fundTransferMapper;


    @Autowired
    private WalletTxMapper walletTxMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private WalletHistoryMapper walletHistoryMapper;

    @Override
    public void backupEthWalletBalance() {

        String snapshotTime = "16:00:00";
        String s = DateTime.now().toString("yyyy-MM-dd");
        //获取当天的16:00时间点
        Date date = DateTime.parse(s +" "+snapshotTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //根据交易记录得到16:00时间点的钱包余额
        BigDecimal weiBalance = walletTxMapper.selectEthBalance(date);
//        转换单位，保留八位
        BigDecimal ethBalance = weiBalance.divide(wei18).setScale(8, BigDecimal.ROUND_DOWN);

        Wallet wallet = walletMapper.selectByEth();

        WalletHistory walletHistory = new WalletHistory();
        BeanUtils.copyProperties(wallet, walletHistory);
        walletHistory.setCreateTime(date);
        walletHistory.setBalance(weiBalance);
        walletHistory.setConvertBalance(ethBalance);

        walletHistoryMapper.insert(walletHistory);


    }

    @Override
    public List<CheckPlatformVo> queryPlatformCheckInfo() {
        ArrayList list=new ArrayList();

        CheckPlatformVo platformVo = new CheckPlatformVo();
        platformVo.setAccountName("ETH");

        //获取上一天16:00到今天16:00的用户充值总额

        Date preTime = getPreTime();
        Date currentTime = getCurrentTime();

        BigDecimal recharge = fundTransferMapper.selectEthFundFlow(preTime, currentTime, "02");

        //获取上一天16:00到今天16:00的用户提现总额
        BigDecimal withdraw = fundTransferMapper.selectEthFundFlow(preTime, currentTime, "03");

        //获取上一天16:00 eth钱包的余额
        BigDecimal ethBalancePre = convertToEth(walletTxMapper.selectEthBalance(preTime));

        //计算出“上一日钱包资产+今日资产进出”
        BigDecimal fundFlow = ethBalancePre.add(recharge).subtract(withdraw);
        platformVo.setFundFlow(fundFlow.toPlainString());

        //获取“今日eth钱包的资产”
        BigDecimal ethBalanceCurrent = convertToEth(walletTxMapper.selectEthBalance(currentTime));

        platformVo.setWalletBalance(ethBalanceCurrent.toPlainString());

        //计算出“上一日钱包资产+今日资产进出”与“今日eth钱包的资产”的差值
        platformVo.setFundDiff(fundFlow.subtract(ethBalanceCurrent).toPlainString());


        //查询出充值提现成功的申请数
        platformVo.setApplyCount(fundTransferMapper.selectEthFundCount());

        //查询出钱包的交易数
        platformVo.setTransCount(walletTxMapper.selectEthCount());

        platformVo.setCountDiff(platformVo.getApplyCount() - platformVo.getTransCount());


        list.add(platformVo);

        return list;
    }


    private Date getPreTime() {

        DateTime dateTime = new DateTime(getCurrentTime()).minusDays(1);
        return dateTime.toDate();
    }

    private Date getCurrentTime() {
        String snapshotTime = "16:00:00";
        String s = DateTime.now().toString("yyyy-MM-dd");
        //获取当天的16:00时间点
        Date date = DateTime.parse(s + " " + snapshotTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        return date;
    }

    private BigDecimal convertToEth(BigDecimal weiBalance) {
        System.out.println(weiBalance);
        return weiBalance.divide(wei18).setScale(8, BigDecimal.ROUND_DOWN);
    }


}
