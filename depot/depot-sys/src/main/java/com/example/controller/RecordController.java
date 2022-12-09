package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Goods;
import com.example.entity.Record;
import com.example.entity.User;
import com.example.service.GoodsService;
import com.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2022-11-27
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;

    /** 新增*/
    @PostMapping("/save")
    public Result save(@RequestBody Record record){

        Goods goods = goodsService.getById1(record.getGoods());
        int n = record.getCount();
        if("2".equals(record.getAction())){
            n = -n;
            record.setCount(n);
        }
        int num = goods.getCount()+n;
        goods.setCount(num);
        goodsService.updateById(goods);

        return recordService.save(record)?Result.suc():Result.fail();
    }

    @PostMapping("/saveApproval")
    public Result saveApproval(@RequestBody Record record){

        if("1".equals(record.getStatus())){
            System.out.println(record.getGoods());
            Goods goods = goodsService.getById1(record.getGoods());
            int n = record.getCount();
            System.out.println(n);
            int num = goods.getCount()+n;
            goods.setCount(num);
            goodsService.updateById(goods);

            return recordService.save(record)?Result.suc():Result.fail();
        }
        return Result.fail();
    }

    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody Record record){
        return recordService.updateById(record)?Result.suc():Result.fail();
    }

    /** 删除*/
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam Integer id){
        return recordService.removeById(id)?Result.suc():Result.fail();
    }

    /** 页面分页查询*/
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String storage = (String)hashMap.get("storage");
        String goodstype = (String)hashMap.get("goodstype");
        String roleId = (String)hashMap.get("roleId");
        String userId = (String)hashMap.get("userId");


        IPage<Record> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply(" a.goods=b.id and c.id=b.storage and d.id=b.goodstype");

        if("2".equals(roleId)){
            queryWrapper.eq("a.userId",userId);
        }

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){

            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){

            queryWrapper.eq("c.id",storage);

        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){

            queryWrapper.eq("d.id",goodstype);
        }

        IPage result = recordService.pageC(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

}
