package com.jinhui.api.mapper;


import com.jinhui.api.entity.dto.QueryPlatformFundParam;
import com.jinhui.api.entity.po.FundTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public interface FundTransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundTransfer record);

    int insertSelective(FundTransfer record);

    FundTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundTransfer record);

    int updateByPrimaryKey(FundTransfer record);

    /**
     * 根据条件查询账户资金明细
     *
     * @param startDate
     * @param accountType 资金类型
     * @return
     */
    List<FundTransfer> queryListBySearch(@Param("startDate") String startDate,
                                         @Param("accountType") String accountType, @Param("userId") String userId);

    /**
     * 按交易状态和业务类型统计用户的交易总金额
     */
    BigDecimal statistics(@Param("bussType") String bussType, @Param("transStatus") String transStatus, @Param("userId") String userId);

    /**
     * 根据条件查询充值流水
     * @param startDate
     * @param endDate
     * @param transStatus
     * @param userName
     * @param userId
     * @return
     */
    List<FundTransfer> queryRechargeListBySearch(@Param("startDate") String startDate,@Param("endDate") String endDate,
                                                 @Param("transStatus") String transStatus, @Param("userName") String userName,
                                                 @Param("userId") String userId,@Param("sysUserId") String sysUserId);

    /**
     * 根据条件查询提现流水
     * @param startDate
     * @param endDate
     * @param transStatus
     * @param userName
     * @param userId
     * @return
     */
    List<FundTransfer> queryWithdrawListBySearch(@Param("startDate") String startDate,@Param("endDate") String endDate,
                                                 @Param("transStatus") String transStatus, @Param("userName") String userName,
                                                 @Param("userId") String userId,@Param("sysUserId") String sysUserId);


    /**
     * 根据条件查询平台的流水信息
     */
    List<FundTransfer> queryBySearch(QueryPlatformFundParam param);


    /**
     * 获取两个时间之间的充值，eth提现流水的总额（交易状态为成功）
     */
    BigDecimal selectEthFundFlow(@Param("start") Date start, @Param("end") Date end, @Param("bussType") String bussType);


    /**
     * 查询出eth提现流水的流水总数（交易状态为成功）
     */
    int selectEthFundCount();

}