package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Goodstype;
import com.example.entity.Storage;
import com.example.mapper.GoodstypeMapper;
import com.example.service.GoodstypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2022-11-27
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements GoodstypeService {

    @Autowired
    private GoodstypeMapper goodstypeMapper;

    @Override
    public IPage pageC(IPage<Goodstype> page, Wrapper Wrapper) {
        return goodstypeMapper.pageC(page,Wrapper);
    }
}
