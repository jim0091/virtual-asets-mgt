package com.jinhui.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.api.constants.PlatformIConst;
import com.jinhui.api.constants.ServiceRateConst;
import com.jinhui.api.controller.vo.TransSearchVo;
import com.jinhui.api.entity.dto.BuyCoin;
import com.jinhui.api.entity.dto.InvestmentParam;
import com.jinhui.api.entity.dto.SellCoin;
import com.jinhui.api.entity.po.FundTransfer;
import com.jinhui.api.entity.po.Market;
import com.jinhui.api.entity.po.ServiceRate;
import com.jinhui.api.entity.vo.InvestmentVo;
import com.jinhui.api.entity.vo.ProductMarketVo;
import com.jinhui.api.entity.vo.WebResult;
import com.jinhui.api.mapper.AccountMapper;
import com.jinhui.api.service.account.ServiceRateService;
import com.jinhui.api.service.trans.TransService;
import com.jinhui.api.service.transfer.FundTransferService;
import com.jinhui.api.utils.ValidatorUtils;
import com.jinhui.common.utils.RedisUtils;
import com.jinhui.common.utils.ResultResp;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/2/6 0006.
 */
@RestController
@RequestMapping("/trans")
public class TransController {

    private static Logger logger = LoggerFactory.getLogger(TransController.class);

    @Autowired
    private TransService transService;

    @Autowired
    private ServiceRateService serviceRateService;


    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private FundTransferService fundTransferService;

    @Autowired
    private static BigDecimal hundred = new BigDecimal("100");


    /**
     * 查询行情
     */
    @GetMapping("queryMarkets")
    WebResult queryMarkets() throws Exception {

        List<Market> markets = transService.queryMarkets();
        List<ProductMarketVo> productMarketVoList = new ArrayList<>();

        ServiceRate buyRate = serviceRateService.queryRateByType(ServiceRateConst.买入费率);
        ServiceRate sellRate = serviceRateService.queryRateByType(ServiceRateConst.卖出费率);


        for (Market market : markets) {
            ProductMarketVo vo = new ProductMarketVo();
            BeanUtils.copyProperties(market, vo);
            vo.setType(market.getBuyCalUnit().toUpperCase());
            vo.setBuyRate(buyRate.getRate().multiply(hundred).toString() + "%");
            vo.setSellRate(sellRate.getRate().multiply(hundred).toString() + "%");
            productMarketVoList.add(vo);
        }

        //过滤掉cny的交易对
        List<ProductMarketVo> vos = productMarketVoList.stream().filter(i -> !i.getBuyPayUnit().equals("cny")).collect(Collectors.toList());

        WebResult result = WebResult.ok();
        result.setData(vos);

        return result;

    }

    /**
     * 根据产品名称查询行情
     */
    @GetMapping("queryMarket")
    WebResult queryMarket(@RequestParam("productName") String productName) throws Exception {

        ServiceRate buyRate = serviceRateService.queryRateByType(ServiceRateConst.买入费率);
        ServiceRate sellRate = serviceRateService.queryRateByType(ServiceRateConst.卖出费率);


        Market market = transService.queryMarket(productName);
        ProductMarketVo vo = new ProductMarketVo();
        BeanUtils.copyProperties(market, vo);
        vo.setType(market.getBuyCalUnit().toUpperCase());
        vo.setBuyRate(buyRate.getRate().multiply(hundred).toString() + "%");
        vo.setSellRate(sellRate.getRate().multiply(hundred).toString() + "%");

        WebResult result = WebResult.ok();

        result.setData(vo);

        return result;

    }


    @PostMapping("buyCoin")
    WebResult buyCoin(@RequestBody BuyCoin buyCoin) {

        //验证
        ValidatorUtils.validateEntity(buyCoin);

        //检查资金密码

        //购买
//        BigDecimal buyNum = new BigDecimal(buyCoin.getBuyVol());
//        String productId = buyCoin.getProductId();
        transService.buy(buyCoin);

        return WebResult.ok();

    }

    @PostMapping("sellCoin")
    WebResult sellCoin(@RequestBody SellCoin sellCoin) {

        //验证
        ValidatorUtils.validateEntity(sellCoin);


        //检查资金密码

        //出售
//        BigDecimal sellNum = new BigDecimal(sellCoin.getSellVol());
//        String productId = sellCoin.getProductId();
        transService.sell(sellCoin);

        return WebResult.ok();

    }


    /**
     * 投资管理查询
     */
    @PostMapping("investment")
    public WebResult investment(@RequestBody InvestmentParam investment) {

        if (StringUtils.isBlank(investment.getOperateId())) {
            investment.setOperateId(null);
        }
        if (StringUtils.isBlank(investment.getUserName())) {
            investment.setUserName(null);
        }
        if (StringUtils.isBlank(investment.getUserId())) {
            investment.setUserId(null);
        }


        Integer pageNum = investment.getPageNum();
        Integer pageSize = investment.getPageSize();
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<InvestmentVo> accounts = accountMapper.selectByParam(investment);
        PageInfo<InvestmentVo> pageInfo = new PageInfo<>(accounts);

        WebResult result = WebResult.ok();
        result.setData(pageInfo);
        return result;
    }

    @ApiOperation(value = "充值流水查询")
    @PostMapping(value = "/rechargeList")
    @ResponseBody
    public ResultResp rechargeList(@RequestBody TransSearchVo transSearchVo, HttpServletRequest req) {
        String sysUserId = RedisUtils.getRedisUser(req).getUserId() + "";
        String sysRoleId = RedisUtils.getRedisUser(req).getRoleId() + "";
        if(!"2".equals(sysRoleId)){   //超管 财务 运营
            sysUserId = null;
        }

        PageInfo<FundTransfer> list = fundTransferService.queryRechargeListBySearch(transSearchVo.getStartDate(), transSearchVo.getEndDate(),
                transSearchVo.getTransStatus(), transSearchVo.getUserName(),
                transSearchVo.getUserId(), sysUserId,transSearchVo.getPageNum(),
                transSearchVo.getPageSize());

        //把平台的记录过滤掉
        list.getList().removeIf(i -> i.getUserId().equals(PlatformIConst.userId));

        return ResultResp.successData(list, "");
    }

    @ApiOperation(value = "提现流水查询")
    @PostMapping(value = "/withdrawList")
    @ResponseBody
    public ResultResp withdrawList(@RequestBody TransSearchVo transSearchVo, HttpServletRequest req) {
        String sysUserId = RedisUtils.getRedisUser(req).getUserId() + "";
        String sysRoleId = RedisUtils.getRedisUser(req).getRoleId() + "";
        if(!"2".equals(sysRoleId)){   //超管 财务 运营
            sysUserId = null;
        }

        PageInfo<FundTransfer> list = fundTransferService.queryWithdrawListBySearch(transSearchVo.getStartDate(), transSearchVo.getEndDate(),
                transSearchVo.getTransStatus(), transSearchVo.getUserName(),
                transSearchVo.getUserId(), sysUserId,transSearchVo.getPageNum(),
                transSearchVo.getPageSize());

        //把平台的记录过滤掉
        list.getList().removeIf(i -> i.getUserId().equals(PlatformIConst.userId));

        return ResultResp.successData(list, "");
    }


}
