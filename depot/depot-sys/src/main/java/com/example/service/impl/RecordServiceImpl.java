package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Goods;
import com.example.entity.Record;
import com.example.mapper.GoodsMapper;
import com.example.mapper.RecordMapper;
import com.example.service.RecordService;
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
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public IPage pageC(IPage<Record> page, Wrapper Wrapper) {
        return recordMapper.pageC(page,Wrapper);
    }
}
