package com.example.controller;

import com.example.common.Result;

import com.example.entity.StudentInfo;
import com.example.service.StudentInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/studentInfo")
public class StudentController {
    @Resource
    private StudentInfoService studentInfoService;
    @PutMapping
    public Result update(@RequestBody StudentInfo studentInfo){
        studentInfoService.update(studentInfo);
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody StudentInfo studentInfo){
        studentInfoService.add(studentInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll(){
        List<StudentInfo> list= studentInfoService.findAll();
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        studentInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo<StudentInfo> info=studentInfoService.findPage(pageNum,pageSize);
        return Result.success(info);
    }
    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@PathVariable String name){

        PageInfo<StudentInfo> info=studentInfoService.findPageName(pageNum,pageSize,name);
        return Result.success(info);
    }

}
