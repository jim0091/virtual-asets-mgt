package com.jinhui.api.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.api.constants.AcctInfo;
import com.jinhui.api.constants.MarketAcctEnum;
import com.jinhui.api.constants.PlatformIConst;
import com.jinhui.api.constants.TransConst;
import com.jinhui.api.entity.dto.PlatformTrans;
import com.jinhui.api.entity.dto.QueryPlatformFundParam;
import com.jinhui.api.entity.po.FundTransfer;
import com.jinhui.api.entity.po.Market;
import com.jinhui.api.entity.po.PlatformAccount;
import com.jinhui.api.entity.vo.CheckPlatformVo;
import com.jinhui.api.entity.vo.PlatformFundVo;
import com.jinhui.api.entity.vo.PlatformInfoVo;
import com.jinhui.api.entity.vo.WebResult;
import com.jinhui.api.mapper.FundTransferMapper;
import com.jinhui.api.mapper.PlatformAccountMapper;
import com.jinhui.api.mapper.WalletTxMapper;
import com.jinhui.api.service.account.MarketServcie;
import com.jinhui.api.service.id.IdService;
import com.jinhui.api.service.platform.PlatformService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2018/4/24 0024.
 */

@RestController
@RequestMapping("/platform")
public class PlatformController {

    private static Logger logger = LoggerFactory.getLogger(PlatformController.class);


    @Autowired
    private PlatformService platformService;

    @Autowired
    private FundTransferMapper fundTransferMapper;

    @Autowired
    private IdService idService;

    @Autowired
    private PlatformAccountMapper platformAccountMapper;


    @Autowired
    private MarketServcie marketServcie;

    @Autowired
    private WalletTxMapper walletTxMapper;

    private final static BigDecimal wei18 = new BigDecimal("1000000000000000000");

    /**
     * 查询平台方的账号信息
     */
    @PostMapping("queryPlatformInfo")
    public WebResult queryPlatformInfo() {

        PlatformInfoVo platformInfoVo = new PlatformInfoVo();
        platformInfoVo.setPlatformId(PlatformIConst.userId);
        platformInfoVo.setPlatformName(PlatformIConst.userName);


        ArrayList list = new ArrayList();
        List<PlatformAccount> platformAccounts = platformAccountMapper.selectAllAccount();
        for (PlatformAccount platformAccount : platformAccounts) {
            PlatformInfoVo.Account account = platformInfoVo.new Account();

            account.setAccountAddress(platformAccount.getAccountAddr());
            account.setAccountName(platformAccount.getAccountName());

            list.add(account);
        }

        platformInfoVo.setAccounts(list);
        WebResult result = WebResult.ok();
        result.setData(platformInfoVo);

        return result;

    }

    /**
     * 平台交易录入
     */
    @PostMapping("savePlatformTrans")
    public WebResult savePlatformTrans(@RequestBody PlatformTrans platformTrans) {


        FundTransfer transfer = new FundTransfer();
        String accountName = platformTrans.getAccountName();
        AcctInfo acctInfo = AcctInfo.byType(accountName.toLowerCase());
        transfer.setAccountType(acctInfo.getCode());
        transfer.setBussType(platformTrans.getBussType());
        transfer.setCreateTime(new Date());
        transfer.setUpdateTime(new Date());
        transfer.setSerialNo(idService.generateTransNo());
        transfer.setUserId(platformTrans.getPlatformId());
        transfer.setUserName(platformTrans.getPlatformName());

        if (platformTrans.getBussType().equals("02")) {//转入
            transfer.setTransferOutAccount(platformTrans.getInputAddress());//转入账号
            transfer.setTransferInAccount(platformTrans.getAccountAddress());//平台账号
        } else if (platformTrans.getBussType().equals("03")) {//转出
            transfer.setTransferInAccount(platformTrans.getAccountAddress());//平台账号
            transfer.setTransferOutAccount(platformTrans.getInputAddress());//转入账号
        }
        transfer.setTransStatus(TransConst.TransStatus.confirm);

        Market market = marketServcie.selectByProductName(MarketAcctEnum.byAcctCode(acctInfo.getCode()).getProductName());
        transfer.setTransPrice(market.getLastPrice());
        transfer.setTransNum(new BigDecimal(platformTrans.getTransNum()));
        transfer.setTransAmount(transfer.getTransNum().multiply(transfer.getTransPrice()));
        transfer.setChargeAmount(BigDecimal.ZERO);
        transfer.setTransTime(new Date());


        fundTransferMapper.insert(transfer);

        return WebResult.ok();
    }


    /**
     * 平台资金记录查询
     */
    @PostMapping("queryPlatformFunds")
    public WebResult savePlatformTrans(@RequestBody QueryPlatformFundParam param) {

        param.setUserId(PlatformIConst.userId);
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<FundTransfer> fundTransfers = fundTransferMapper.queryBySearch(param);

        PageInfo pageInfo = new PageInfo<>(fundTransfers);
        WebResult result = WebResult.ok();
        result.setData(pageInfo);

        return result;

    }


    /**
     * 查询平台的资产
     */
    @GetMapping("queryAccountFunds")
    WebResult queryAccountFunds() {


        List<PlatformAccount> accounts = platformAccountMapper.selectAll();

        ArrayList list = new ArrayList();
        for (PlatformAccount account : accounts) {

            PlatformFundVo platformFundVo = new PlatformFundVo();
            platformFundVo.setUserId(PlatformIConst.userId);
            platformFundVo.setUserName(PlatformIConst.userName);
            platformFundVo.setTotalFund(account.getPositionVol().toPlainString());
            platformFundVo.setAccountName(account.getAccountDesc());
            list.add(platformFundVo);
        }

        WebResult result = WebResult.ok();
        result.setData(list);

        return result;
    }


    /**
     * 查询平台资产核对情况
     */
    @GetMapping("queryCheckInfo")
    public WebResult queryCheckInfo() {

        List<CheckPlatformVo> platformVos = platformService.queryPlatformCheckInfo();

        WebResult result = WebResult.ok();
        result.setData(platformVos);

        return result;
    }


    /**
     * 查询平台资产核对情况
     */
    @GetMapping("checkPlatformFunds")
    public WebResult checkPlatformFunds() {

        List<CheckPlatformVo> platformVos = platformService.queryPlatformCheckInfo();

        boolean passFlag = true;
        for (CheckPlatformVo platformVo : platformVos) {
            int countDiff = platformVo.getCountDiff();
            Double aDouble = Double.valueOf(platformVo.getFundDiff());
            if (countDiff != 0 || aDouble != 0) {
                passFlag = false;
            }
        }

        WebResult result = WebResult.ok();
        result.setData(passFlag);

        return result;
    }


}
