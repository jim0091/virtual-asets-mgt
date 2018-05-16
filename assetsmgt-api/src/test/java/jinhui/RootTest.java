package jinhui;


import com.jinhui.api.controller.FundController;
import com.jinhui.api.controller.PlatformController;
import com.jinhui.api.controller.TransController;
import com.jinhui.api.entity.dto.InvestmentParam;
import com.jinhui.api.entity.vo.TransQueryParam;
import com.jinhui.api.entity.vo.WebResult;
import com.jinhui.api.service.platform.PlatformService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 集成测试类
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.jinhui.*")//测试类要指定扫描路径才能把其他模块的配置类找出来
@SpringBootApplication
public class RootTest {


    @Autowired
    private FundController fundController;

    @Autowired
    private PlatformService platformService;


    @Autowired
    private TransController transController;

    @Autowired
    private PlatformController platformController;

    @Test
    public void test1() {
        TransQueryParam param=new TransQueryParam();
        param.setUserId("M990000001");
        param.setPageNum(1);
        param.setPageSize(10);
        WebResult webResult = fundController.queryTrans(param);

        System.out.println(webResult);

    }

    @Test
    public void test2() {
        InvestmentParam param=new InvestmentParam();
        param.setPageNum(1);
        param.setPageSize(10);
        WebResult investment = transController.investment(param);
        System.out.println(investment);
    }

    @Test
    public void test3(){

//        platformService.backupEthWalletBalance();
        WebResult webResult = platformController.checkPlatformFunds();
        System.out.println(webResult);

    }


}