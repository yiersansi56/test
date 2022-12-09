package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Approval;
import com.example.mapper.ApprovalMapper;
import com.example.service.ApprovalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2022-11-30
 */
@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {

    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public IPage pageC(IPage<Approval> page, Wrapper<Approval> Wrapper) {
        return approvalMapper.pageC(page,Wrapper);
    }
}
