package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2022-11-27
 */
public interface StorageService extends IService<Storage> {

    IPage pageC(IPage<Storage> page, Wrapper Wrapper);
}
