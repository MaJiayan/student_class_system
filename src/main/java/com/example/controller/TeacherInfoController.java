package com.example.controller;

import com.example.common.Result;

import com.example.entity.AdminInfo;
import com.example.entity.TeacherInfo;
import com.example.service.TeacherInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/teacherInfo")
public class TeacherInfoController {
    @Resource
    private TeacherInfoService teacherInfoService;
    @PutMapping
    public Result teacherInfo(@RequestBody TeacherInfo teacherInfo){
        teacherInfoService.update(teacherInfo);
        return Result.success();
    }
//    public Result update(@RequestBody TeacherInfo teacherInfo){
//        teacherInfoService.update(teacherInfo);
//        return Result.success();
//    }
    @PostMapping
    public Result add(@RequestBody TeacherInfo teacherInfo){
        teacherInfoService.add(teacherInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll(){
        List<TeacherInfo> list= teacherInfoService.findAll();
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        teacherInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo<TeacherInfo> info=teacherInfoService.findPage(pageNum,pageSize);
        return Result.success(info);
    }
    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@PathVariable String name){

        PageInfo<TeacherInfo> info=teacherInfoService.findPageName(pageNum,pageSize,name);
        return Result.success(info);
    }

}
