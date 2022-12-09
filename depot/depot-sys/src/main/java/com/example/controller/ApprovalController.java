package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Approval;
import com.example.entity.Goods;
import com.example.entity.Record;
import com.example.service.ApprovalService;
import com.example.service.GoodsService;
import com.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/list")
    public Result list(){
        List list = approvalService.list();

        return Result.suc(list);
    }

    /** 新增*/
    @PostMapping("/save")
    public Result save(@RequestBody Approval approval){

        int n = approval.getCount();
        if("2".equals(approval.getRadio())){
            n = -n;
            approval.setCount(n);
        }

        return approvalService.save(approval)?Result.suc():Result.fail();
    }

    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody Approval approval){
        return approvalService.updateById(approval)?Result.suc():Result.fail();
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
        String status = (String)hashMap.get("status");



        IPage<Approval> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        QueryWrapper<Approval> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("a.goods=b.id and b.storage=c.id and b.goodsType=d.id");

        if("2".equals(roleId)){
            queryWrapper.eq("a.user_id",userId);
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
        if(StringUtils.isNotBlank(status) && !"null".equals(status)){

            queryWrapper.eq("a.status",status);
        }


        IPage result = approvalService.pageC(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

}
