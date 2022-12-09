package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Goodstype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2022-11-27
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage pageC(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select * from goods where id = #{id};")
    Goods getById1(@Param("id") Long id);
}
