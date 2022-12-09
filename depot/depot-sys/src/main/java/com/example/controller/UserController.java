package com.example.controller;


import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Menu;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.MenuService;
import com.example.service.UserService;
import com.example.utils.CheckCodeUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2022-11-25
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public Result list(){
        List list = userService.list();

        return Result.suc(list);
    }



    /** 查询用户名是否存在*/
    @GetMapping("/findByUsername")
    public Result findByUsername(@RequestParam String username){

        List list = userService.lambdaQuery().eq(User::getUsername, username).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }


    /** 新增*/
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.suc():Result.fail();
    }
    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }
    /** 登录*/
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword,user.getPassword()).list();
        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap result = new HashMap();
            result.put("user",user1);
            result.put("menu",menuList);

            return Result.suc(result);
        }
        return Result.fail();
    }

    /** 找回密码*/
    @PostMapping("/findPwd")
    public Result findPwd(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getEmail,user.getEmail()).list();
        if(list.size()>0){
            User user1 = (User)list.get(0);
            user1.setPassword(user.getPassword());

            return userService.updateById(user1)? Result.suc():Result.fail();
        }
        return Result.fail();
    }

    /** 修改*/
    @PostMapping("/modify")
    public Result modify(@RequestBody User user){
        userService.updateById(user);
        User user1 = userMapper.selectById(user.getId());
        return Result.suc(user1);
    }
    /** 新增或修改*/
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }
    /** 查询（模糊、匹配）*/
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.suc(userService.list(lambdaQueryWrapper));
    }

    /**搜索分页查询（模糊、匹配）法一*/
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");

//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(User::getName,user.getName());
//        return userService.list(lambdaQueryWrapper);

        Page<User> page = new Page<>();
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page, lambdaQueryWrapper);



        System.out.println("total="+result.getTotal());

        return result.getRecords();
    }

    /**搜索分页查询（模糊、匹配）法二*/
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");


        IPage<User> page = new Page<>();
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.pageC(page);

        System.out.println("total="+result.getTotal());

        return result.getRecords();
    }

    /**搜索分页查询（模糊、匹配）法三*/
    @PostMapping("/listPageCC")
    public List<User> listPageCC(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");


        IPage<User> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total="+result.getTotal());

        return result.getRecords();
    }

    /**搜索分页查询（模糊、匹配）法四*/
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String sex = (String)hashMap.get("sex");
        String roleId = (String)hashMap.get("roleId");

        IPage<User> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }

        if(StringUtils.isNotBlank(sex) && !"null".equals(sex)){
            lambdaQueryWrapper.like(User::getSex,sex);
        }

        if(StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }

        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

    @RequestMapping("/newCode")
    public void newCode(HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        //生成验证码
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(90, 40, os, 4);

        //存入Session
        session.setAttribute("checkCodeGen",checkCode);



    }


    @RequestMapping ("/checkCode")
    public Result checkCode(@RequestBody QueryPageParam param){

        String checkCode = param.getCheckCode();

        HttpSession session = request.getSession();
        String checkCodeGen = (String)session.getAttribute("checkCodeGen");

        if(checkCodeGen.equalsIgnoreCase(checkCode)){
            return Result.suc();
        }

            return Result.fail();
    }

    @RequestMapping("/toUploadAvatar")
    public Result updateAvatar(MultipartFile file){
        //判断文件类型
        String pType=file.getContentType();
        pType=pType.substring(pType.indexOf("/")+1);

        if("jpeg".equals(pType)){
            pType="jpg";
        }

        long time=System.currentTimeMillis();
        //这里我采用绝对路径
        String path="D:/workspace/depot-sys/src/main/resources/static/images/avatar"+time+"."+pType;
        String url = "http://localhost:8082/"+path.substring(path.indexOf("images/"));

        try{
            file.transferTo(new File(path));
            //文件路径保存到数据库中从而读取
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.uploadSuc(url);
    }

    @PostMapping("/saveUpload")
    public Result saveUpload(@RequestBody HashMap hashMap){

        String s_id = (String)hashMap.get("id");

        String url = (String) hashMap.get("url");
        System.out.println(url);

        int id = Integer.parseInt(s_id);
        userService.addAvatar(url,id);

        return Result.suc();
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam String id){

        int i = Integer.parseInt(id);

        User user = userMapper.selectById(i);

        return Result.suc(user);
    }

    @PostMapping("/modifyPwd")
    public Result modifyPwd(@RequestBody HashMap hashMap){

        Integer id = (Integer)hashMap.get("id");
        String password = (String)hashMap.get("password");

        User user = userMapper.selectById(id);
        user.setPassword(password);

        userMapper.updateById(user);

        return Result.suc();
    }

    @GetMapping("/findPwd")
    public Result findPwd(@RequestParam String username,String pwd){

        List<User> list = userService.lambdaQuery().eq(User::getPassword, pwd)
                .eq(User::getUsername, username).list();

        return list.size()>0?Result.suc():Result.fail();
    }

    @GetMapping("/epidemicMap")
    public String epidemicMap(){

        return HttpUtil.get("https://c.m.163.com/ug/api/wuhan/app/data/list-total?t=" + System.currentTimeMillis());
    }




}
