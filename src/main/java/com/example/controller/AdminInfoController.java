package com.example.controller;

import com.example.common.Result;
import com.example.entity.AdminInfo;
import com.example.service.AdminInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController {
    @Resource
    private AdminInfoService adminInfoService;
    @PutMapping
    public Result adminInfo(@RequestBody AdminInfo adminInfo){
        adminInfoService.update(adminInfo);
        return Result.success();
    }
    @PostMapping
    public Result add(@RequestBody AdminInfo adminInfo){
        adminInfoService.add(adminInfo);
        return Result.success();
    }
    @GetMapping
    public Result findAll(){
        List<AdminInfo> list= adminInfoService.findAll();
        return Result.success(list);
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        adminInfoService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo<AdminInfo> info=adminInfoService.findPage(pageNum,pageSize);
        return Result.success(info);
    }
    @GetMapping("/page/{name}")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@PathVariable String name){

        PageInfo<AdminInfo> info=adminInfoService.findPageName(pageNum,pageSize,name);
        return Result.success(info);
    }
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        //Account user=(Account) request.getSession().getAttribute("user");
        request.getSession().setAttribute("user",null);

        return Result.success();
    }

}
