package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Goods;
import com.example.entity.Storage;
import com.example.mapper.GoodsMapper;
import com.example.mapper.StorageMapper;
import com.example.service.GoodsService;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public IPage pageC(IPage<Goods> page, Wrapper Wrapper) {
        return goodsMapper.pageC(page,Wrapper);
    }

    @Override
    public Goods getById1(Long id) {
        return goodsMapper.getById1(id);
    }
}
