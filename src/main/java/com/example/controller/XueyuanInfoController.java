package com.example.controller;

import com.example.common.Result;
import com.example.entity.TeacherInfo;
import com.example.entity.XueyuanInfo;
import com.example.service.TeacherInfoService;
import com.example.service.XueyuanInfoService;
import com.github.pagehelper.PageInfo;
import javafx.scene.chart.XYChart;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/xueyuanInfo")
public class XueyuanInfoController {
    @Resource
    private XueyuanInfoService xueyuanInfoService;
    @PutMapping
//    public Result xueyuanInfo(@RequestBody XueyuanInfo xueyuanInfo){
//        xueyuanInfoService.update(xueyuanInfo);
//        return Result.success();
//    }
    public Result update(@RequestBody XueyuanInfo xueyuanInfo){
        xueyuanInfoService.update(xueyuanInfo);
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody XueyuanInfo xueyuanInfo){
        xueyuanInfoService.add(xueyuanInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll(){
        List<XueyuanInfo> list= xueyuanInfoService.findAll();
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        xueyuanInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo<XueyuanInfo> info=xueyuanInfoService.findPage(pageNum,pageSize);
        return Result.success(info);
    }
    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@PathVariable String name){

        PageInfo<XueyuanInfo> info=xueyuanInfoService.findPageName(pageNum,pageSize,name);
        return Result.success(info);
    }
}
