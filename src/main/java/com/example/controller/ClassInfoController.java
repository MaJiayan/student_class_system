package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.ClassInfo;
import com.example.entity.XuankeInfo;
import com.example.exception.CustomException;
import com.example.service.ClassInfoService;
import com.example.service.XuankeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/classInfo")
public class ClassInfoController {
    @Resource
    private ClassInfoService ClassInfoService;
    @Resource
    private XuankeInfoService xuankeInfoService;
    @PutMapping
//    public Result xueyuanInfo(@RequestBody XueyuanInfo xueyuanInfo){
//        xueyuanInfoService.update(xueyuanInfo);
//        return Result.success();
//    }
    public Result update(@RequestBody ClassInfo ClassInfo){
        ClassInfoService.update(ClassInfo);
        return Result.success();
    }
    @PostMapping("/xuanke")
    public Result xuanke(@RequestBody ClassInfo classInfo, HttpServletRequest request){
        Account user=(Account) request.getSession().getAttribute("user");
        if(ObjectUtil.isEmpty(user)){
            throw new CustomException("-1","登录已失效，请重新登录");
        }
        XuankeInfo info=xuankeInfoService.find(classInfo.getName(),classInfo.getTeacherId(),user.getId());
        if(ObjectUtil.isNotEmpty(info)){
            throw new CustomException("-1","您已选择这门课程，请勿重复选");
        }else {

        }
        XuankeInfo xuankeInfo=new XuankeInfo();
        //相同的全部拷贝
        BeanUtils.copyProperties(classInfo,xuankeInfo);
        xuankeInfo.setId(null);
        xuankeInfo.setStudentId(user.getId());
        xuankeInfo.setStatus("待开课");
        xuankeInfoService.add(xuankeInfo);
        classInfo.setYixuan(classInfo.getYixuan()+1);
        ClassInfoService.update(classInfo);
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody ClassInfo ClassInfo){
        ClassInfoService.add(ClassInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll(){
        List<ClassInfo> list= ClassInfoService.findAll();
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        ClassInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo<ClassInfo> info=ClassInfoService.findPage(pageNum,pageSize);
        return Result.success(info);
    }
    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@PathVariable String name){

        PageInfo<ClassInfo> info=ClassInfoService.findPageName(pageNum,pageSize,name);
        return Result.success(info);
    }
}
