package com.jinhui.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.api.constants.AcctInfo;
import com.jinhui.api.entity.dto.QueryFundParam;
import com.jinhui.api.entity.po.*;
import com.jinhui.api.entity.vo.*;
import com.jinhui.api.mapper.*;

import com.jinhui.api.service.account.AccountService;
import com.jinhui.api.utils.ListPageUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
@RestController
@RequestMapping("/fund")
public class FundController {

    private static Logger logger = LoggerFactory.getLogger(FundController.class);

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private AccountService accountService;


    @Autowired
    private ProductPositionMapper productPositionMapper;

    @Autowired
    private RegularTradeMapper regularTradeMapper;

    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private FundGatherMapper fundGatherMapper;


    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询用户账户的余额
     */
    @GetMapping("queryAcctBalance")
    WebResult queryAcctBalance(@RequestParam String accountType, @RequestParam String userId) {


        WebResult result = WebResult.ok();
        boolean exist = accountService.isExist(userId, AcctInfo.byType(accountType));
        if (!exist) {
            result.setData("0");
            return result;
        }

        Account account = accountService.queryAccount(userId, AcctInfo.byType(accountType));

        result.setData(account.getPositionVol());

        return result;
    }


    /**
     * 查询交易记录
     */
    @PostMapping("queryTrans")
    public WebResult queryTrans(@RequestBody TransQueryParam transQueryParam) {


        if (StringUtils.isBlank(transQueryParam.getUserId())) {
            transQueryParam.setUserId(null);
        }
        if (StringUtils.isBlank(transQueryParam.getUserName())) {
            transQueryParam.setUserName(null);
        }


        List<TradeRecordVo> list = new ArrayList<>();

        List<Trade> trades = tradeMapper.selectByParam(transQueryParam);
        for (Trade trade : trades) {
            TradeRecordVo tradeRecordVo = TradeRecordVo.toTradeRecordVo(trade);
            list.add(tradeRecordVo);
        }


        List<RegularTrade> regularTrades = regularTradeMapper.selectByQueryParam(transQueryParam);

        for (RegularTrade regularTrade : regularTrades) {
            TradeRecordVo tradeRecordVo = TradeRecordVo.toTradeRecordVo(regularTrade);
            list.add(tradeRecordVo);
        }

        //按时间倒序排列
        List<TradeRecordVo> collect = list.stream().sorted(Comparator.comparing(TradeRecordVo::getTransTime).reversed()).collect(Collectors.toList());

        Integer pageNum = transQueryParam.getPageNum();
        Integer pageSize = transQueryParam.getPageSize();
        ListPageUtil pageUtil = new ListPageUtil(collect, pageNum, pageSize);
        PageInfo pageInfo = pageUtil.toPageInfo();

        WebResult result = WebResult.ok();
        result.setData(pageInfo);

        return result;

    }

    /**
     * 查询用户的资产
     */

    @PostMapping("queryFunds")
    WebResult queryTrans(@RequestBody QueryFundParam queryFundParam) {


        if (StringUtils.isBlank(queryFundParam.getUserId())) {
            queryFundParam.setUserId(null);
        }
        if (StringUtils.isBlank(queryFundParam.getUserName())) {
            queryFundParam.setUserName(null);
        }

        Integer pageNum = queryFundParam.getPageNum();
        Integer pageSize = queryFundParam.getPageSize();
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<Account> accounts = accountMapper.selectFundsByParam(queryFundParam);

        PageInfo pageInfo = new PageInfo<>(accounts);

        List<Account> accountList = pageInfo.getList();
        List<UserFundVo> list = new ArrayList<>();
        for (Account account : accountList) {

            UserFundVo userFundVo = new UserFundVo();


            userFundVo.setUserId(account.getUserId());
            userFundVo.setUserName(account.getUserName());
            userFundVo.setUserAccount(account.getUserAccount());
            userFundVo.setFundName(account.getAccountName());
            userFundVo.setFundBalance(account.getPositionVol().toPlainString());

            //查询相应的定期产品持仓总金额
            BigDecimal position = productPositionMapper.selectTotalPosition(account.getUserId(), account.getAccountName());
            userFundVo.setInvestFund(position.toPlainString());

            //相加得到总资产
            BigDecimal totalFunds = position.add(account.getPositionVol());
            userFundVo.setTotalFund(totalFunds.toPlainString());

            //把总资产换算成cny金额
            AcctInfo acctInfo = AcctInfo.byCode(account.getAccountType());

            Market market = marketMapper.selectByBuyPayAndSellPay(AcctInfo.cny.getType(), acctInfo.getType());
            BigDecimal sellPrice = market.getSellPrice();
            userFundVo.setTotalAmount(totalFunds.multiply(sellPrice).toPlainString());

            list.add(userFundVo);
        }

        pageInfo.setList(list);

        WebResult result = WebResult.ok();
        result.setData(pageInfo);
        return result;
    }




}
