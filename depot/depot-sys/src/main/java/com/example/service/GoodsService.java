package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2022-11-27
 */
public interface GoodsService extends IService<Goods> {

    IPage pageC(IPage<Goods> page, Wrapper Wrapper);

    Goods getById1(Long id);
}
