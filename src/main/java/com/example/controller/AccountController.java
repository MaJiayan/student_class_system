package com.example.controller;


import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.AdminInfo;
import com.example.entity.StudentInfo;
import com.example.entity.TeacherInfo;
import com.example.service.AdminInfoService;
import com.example.service.StudentInfoService;
import com.example.service.TeacherInfoService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class AccountController {
    @Resource
    private AdminInfoService adminInfoService;
    @Resource
    private TeacherInfoService teacherInfoService;
    @Resource
    private StudentInfoService studentInfoService;
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws Exception{

        SpecCaptcha captcha=new SpecCaptcha(135,33,4);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        CaptchaUtil.out(captcha,request,response);
//        ArithmeticCaptcha captcha1=new ArithmeticCaptcha(135,33);
//        captcha1.setLen(3);//几位运算
//        captcha1.getArithmeticString();
//        captcha1.text();
     //   CaptchaUtil.out(captcha1,request,response);
    }
    @PostMapping("/login")
    public Result login(@RequestBody Account user, HttpServletRequest request){
        if(!CaptchaUtil.ver(user.getVerCode(),request)){
            CaptchaUtil.clear(request);
            return Result.error("1001","验证码不正确");
        }

        if(ObjectUtil.isEmpty(user.getName())||ObjectUtil.isEmpty(user.getPassword())||ObjectUtil.isEmpty(user.getLevel())){
            return Result.error("-1","请输入完善信息");
        }
        Integer level=user.getLevel();
        Account loginUser=new Account();
        if(1==level){
            loginUser=adminInfoService.login(user.getName(),user.getPassword());
        }
        if(2==level){
            loginUser=teacherInfoService.login(user.getName(),user.getPassword());
        }
        if(3==level){
            loginUser=studentInfoService.login(user.getName(), user.getPassword());
        }
        //在session里面把用户信息存一份
        request.getSession().setAttribute("user",loginUser);
return Result.success(loginUser);
    }
    @PostMapping("/register")
    public Result register(@RequestBody Account user, HttpServletRequest request){
        if(ObjectUtil.isEmpty(user.getName())||ObjectUtil.isEmpty(user.getPassword())||ObjectUtil.isEmpty(user.getLevel())){
            return Result.error("-1","请输入完善信息");
        }
        Integer level=user.getLevel();

        if(2==level){
            TeacherInfo teacherInfo=new TeacherInfo();
            BeanUtils.copyProperties(user,teacherInfo);
            teacherInfoService.register(teacherInfo);
        }
        if(3==level){
            StudentInfo studentInfo=new StudentInfo();
            BeanUtils.copyProperties(user,studentInfo);
            studentInfoService.register(studentInfo);

        }

        //在session里面把用户信息存一份
       // request.getSession().setAttribute("user",loginUser);
        return Result.success();
    }
    @GetMapping("/getUser")
    public Result getUser(HttpServletRequest request){
        //从session中获取信息
       Account user=(Account) request.getSession().getAttribute("user");
       //判断当前登录的用户的角色
        Integer level=user.getLevel();

        if(1==level){
           AdminInfo adminInfo=adminInfoService.findById(user.getId());
           //adminInfo.setPassword("");
           return Result.success(adminInfo);
        }
        if(2==level){
            TeacherInfo teacherInfo=teacherInfoService.findById(user.getId());
//teacherInfo.setPassword("");
            return Result.success(teacherInfo);
        }
        if(3==level){
            StudentInfo studentInfo=studentInfoService.findById(user.getId());
//teacherInfo.setPassword("");
            return Result.success(studentInfo);

        }
        return Result.success(new Account());
    }
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account,HttpServletRequest request){
        Account user=(Account) request.getSession().getAttribute("user");
        //getUser(request);
        Integer level=user.getLevel();
        String oldPassword=account.getPassword();
        if(!user.getPassword().equals(oldPassword)){
            return Result.error("-1","原密码输入错误");
        }
        String newPassword=account.getNewPassword();

        if(1==level){
            AdminInfo adminInfo=new AdminInfo();
            BeanUtils.copyProperties(user,adminInfo);
            adminInfo.setPassword(newPassword);
            adminInfoService.update(adminInfo);
        }
        if(2==level){
            TeacherInfo teacherInfo=new TeacherInfo();
            BeanUtils.copyProperties(user,teacherInfo);
            teacherInfo.setPassword(newPassword);
            teacherInfoService.update(teacherInfo);
        }
        if(3==level){
            StudentInfo studentInfo=new StudentInfo();
            BeanUtils.copyProperties(user,studentInfo);
            studentInfo.setPassword(newPassword);
            studentInfoService.update(studentInfo);

        }
        request.getSession().setAttribute("user",null);
        return Result.success();
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        //Account user=(Account) request.getSession().getAttribute("user");
        request.getSession().setAttribute("user",null);

        return Result.success();
    }

}
