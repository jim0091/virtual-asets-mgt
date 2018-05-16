package com.jinhui.common.dao;

import com.jinhui.common.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 系统日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
@Component
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
