package com.jinhui.api.service.platform;

import com.jinhui.api.entity.vo.CheckPlatformVo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27 0027.
 */
public interface PlatformService {

    /**
     * 备份每天下午四点的eth钱包余额
     */
    void backupEthWalletBalance();


    /**
     *  查询前一天16:00到今天16:00的充值提现
     */


    List<CheckPlatformVo> queryPlatformCheckInfo();


}
