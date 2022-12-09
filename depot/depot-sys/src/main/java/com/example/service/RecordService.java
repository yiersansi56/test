package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Goodstype;
import com.example.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2022-11-27
 */
public interface RecordService extends IService<Record> {

    IPage pageC(IPage<Record> page, Wrapper Wrapper);
}
