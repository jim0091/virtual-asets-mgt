package com.jinhui.api.controller;

import com.github.pagehelper.PageInfo;
import com.jinhui.api.constants.CoinInfo;
import com.jinhui.api.entity.dto.BuyProduct;
import com.jinhui.api.entity.dto.ProductParam;
import com.jinhui.api.entity.po.Market;
import com.jinhui.api.entity.po.RegularProduct;
import com.jinhui.api.entity.vo.CoinVo;
import com.jinhui.api.entity.vo.ProductVo;
import com.jinhui.api.entity.vo.RegularProductVo;
import com.jinhui.api.entity.vo.WebResult;
import com.jinhui.api.mapper.MarketMapper;
import com.jinhui.api.mapper.RegularProductMapper;
import com.jinhui.api.service.product.ProductService;
import com.jinhui.api.utils.ListPageUtil;
import com.jinhui.api.utils.ValidatorUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
@RestController
@RequestMapping("/product")
public class ProductController {


    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private MarketMapper marketMapper;


    @Autowired
    private RegularProductMapper regularProductMapper;


    /**
     * 查询产品大类
     */
    @GetMapping("queryProductType")
    WebResult queryProductType() {


        List<String> list = Arrays.asList("BTC类", "USDT类", "ETH类", "固收类");

        WebResult result = WebResult.ok();
        result.setData(list);

        return result;

    }


    @GetMapping("queryProduct")
    WebResult buyProduct(@RequestParam("productName") String productName) {
        RegularProduct regularProduct = regularProductMapper.selectByName(productName);

        RegularProductVo productVo = RegularProductVo.createRegularProductVo(regularProduct);
        WebResult result = WebResult.ok();
        result.setData(productVo);

        return result;

    }


    @GetMapping("queryProducts")
    WebResult queryProducts() {
        List<RegularProduct> regularProducts = regularProductMapper.selectAll();

        List<RegularProductVo> voList = new ArrayList();
        for (RegularProduct regularProduct : regularProducts) {
            RegularProductVo productVo = RegularProductVo.createRegularProductVo(regularProduct);
            voList.add(productVo);
        }
        WebResult result = WebResult.ok();
        result.setData(voList);

        return result;

    }


    @PostMapping("buyProduct")
    WebResult buyProduct(@RequestBody BuyProduct buyProduct) {

        //验证
        ValidatorUtils.validateEntity(buyProduct);

        //检查资金密码

        //购买定期产品
        productService.buyProduct(buyProduct);

        return WebResult.ok();

    }


    /**
     * 按条件查询产品
     */
    @PostMapping("queryProductBySearch")
    WebResult queryProductBySearch(@RequestBody ProductParam productParam) {

        System.out.println(productParam);
        if (StringUtils.isBlank(productParam.getProductId())) {
            productParam.setProductId(null);
        }
        if (StringUtils.isBlank(productParam.getProductType())) {
            productParam.setProductType(null);
        }

        List<RegularProduct> regularProducts = regularProductMapper.selectBySearch(productParam);

        List<ProductVo> list = new ArrayList<>();

        for (RegularProduct regularProduct : regularProducts) {
            ProductVo productVo = new ProductVo();
            productVo.setProductName(regularProduct.getProductName());
            productVo.setProductId(regularProduct.getProductId());
            productVo.setCreateDate(regularProduct.getCreateTime());
            productVo.setProductType(regularProduct.getProductType());
            list.add(productVo);
        }

        List<Market> markets = marketMapper.selectBySearch(productParam);
        for (Market market : markets) {
            ProductVo productVo = new ProductVo();
            productVo.setProductName(market.getProductName());
            productVo.setProductId(market.getProductId());
            productVo.setCreateDate(market.getUpdateTime());
            productVo.setProductType(market.getProductType());
            list.add(productVo);

        }


        Integer pageNum = productParam.getPageNum();
        Integer pageSize = productParam.getPageSize();
        ListPageUtil pageUtil = new ListPageUtil(list, pageNum, pageSize);
        PageInfo pageInfo = pageUtil.toPageInfo();

        WebResult result = WebResult.ok();
        result.setData(pageInfo);
        return result;

    }


    /**
     * 按条件查询币种 ,币种未完善，
     */
    @GetMapping("queryCoinBySearch")
    WebResult queryCoinBySearch(String coinCode) {

        CoinInfo[] values = CoinInfo.values();
        List<CoinVo> list = new ArrayList<>();
        for (CoinInfo value : values) {
            CoinVo coinVo = new CoinVo();
            coinVo.setCoinCode(value.getCode());
            coinVo.setCoinName(value.getName());
            coinVo.setCreateDate(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            list.add(coinVo);
        }
        WebResult result = WebResult.ok();
        result.setData(list);
        if (StringUtils.isNotBlank(coinCode)) {
            List<CoinVo> collect = list.stream().filter(i -> i.getCoinCode().equals(coinCode)).collect(Collectors.toList());
            result.setData(collect);
        }

        return result;
    }

}
