package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Approval;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2022-11-30
 */
@Mapper
public interface ApprovalMapper extends BaseMapper<Approval> {

    IPage<Approval> pageC(IPage<Approval> page,@Param(Constants.WRAPPER) Wrapper<Approval> wrapper);
}
