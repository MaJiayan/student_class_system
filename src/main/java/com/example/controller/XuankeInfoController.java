package com.example.controller;

import com.example.common.Result;
import com.example.entity.XuankeInfo;
import com.example.service.XuankeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/xuankeInfo")
public class XuankeInfoController {
    @Resource
    private XuankeInfoService XuankeInfoService;
    @PutMapping
//    public Result xueyuanInfo(@RequestBody XueyuanInfo xueyuanInfo){
//        xueyuanInfoService.update(xueyuanInfo);
//        return Result.success();
//    }
    public Result update(@RequestBody XuankeInfo XuankeInfo){
        XuankeInfoService.update(XuankeInfo);
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody XuankeInfo XuankeInfo){
        XuankeInfoService.add(XuankeInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll(HttpServletRequest request){
        List<XuankeInfo> list= XuankeInfoService.findAll(request);
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        XuankeInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,HttpServletRequest request){
        PageInfo<XuankeInfo> info=XuankeInfoService.findPage(pageNum,pageSize,request);
        return Result.success(info);
    }
    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@PathVariable String name,HttpServletRequest request){

        PageInfo<XuankeInfo> info=XuankeInfoService.findPageName(pageNum,pageSize,name,request);
        return Result.success(info);
    }
}
