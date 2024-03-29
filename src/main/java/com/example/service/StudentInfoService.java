package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.StudentInfoDao;
import com.example.dao.XueyuanInfoDao;
import com.example.entity.Account;
import com.example.entity.StudentInfo;
import com.example.entity.XueyuanInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentInfoService {
    @Resource
    private StudentInfoDao studentInfoDao;
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;


    public void register(StudentInfo studentInfo) {
     StudentInfo info= studentInfoDao.findByName(studentInfo.getName());
     if(ObjectUtil.isNotEmpty(info)){
         throw  new CustomException(ResultCode.USER_EXIST_ERROR);
     }
     studentInfoDao.insertSelective(studentInfo);
    }

    public Account login(String name, String password) {
        StudentInfo studentInfo= studentInfoDao.findByNameAndPassword(name,password);
        if(ObjectUtil.isEmpty(studentInfo)){
            throw new CustomException("-1","用户名、密码或角色错误");
        }
        return studentInfo;
    }

    public StudentInfo findById(Long id) {
        return studentInfoDao.selectByPrimaryKey(id);
    }

    public void update(StudentInfo studentInfo) {
        studentInfoDao.updateByPrimaryKeySelective(studentInfo);
    }

    public void add(StudentInfo studentInfo) {
        StudentInfo info=studentInfoDao.findByName(studentInfo.getName());
        if(ObjectUtil.isNotEmpty(info)){
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if(ObjectUtil.isEmpty(studentInfo.getPassword())){
            studentInfo.setPassword("123456");
        }
        studentInfo.setLevel(3);
        studentInfoDao.insertSelective(studentInfo);
    }

    public List<StudentInfo> findAll() {
       // List<StudentInfo> list=studentInfoDao.selectAll();
        //1
//        for(StudentInfo studentInfo:list){
//            if(ObjectUtil.isNotEmpty(studentInfo.getXueyuanId())){
//                XueyuanInfo xueyuanInfo=xueyuanInfoDao.selectByPrimaryKey(studentInfo.getXueyuanId());
//                studentInfo.setXueyuanName(xueyuanInfo.getName());
//            }
//        }
//
//        return list;
        //2
        List<StudentInfo> list1=studentInfoDao.findAllJoinXueyuan();
        return list1;
    }
    public void deleteById(Long id) {
        studentInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<StudentInfo> findPage(Integer pageNum, Integer pageSize) {
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        List<StudentInfo> list=studentInfoDao.findAllJoinXueyuan();
        return PageInfo.of(list);
    }

    public PageInfo<StudentInfo> findPageName(Integer pageNum, Integer pageSize, String name) {
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        List<StudentInfo> infos=studentInfoDao.findByNamePage(name);
        for(StudentInfo studentInfo:infos){
            if(ObjectUtil.isNotEmpty(studentInfo.getXueyuanId())){
                XueyuanInfo xueyuanInfo=xueyuanInfoDao.selectByPrimaryKey(studentInfo.getXueyuanId());
                studentInfo.setXueyuanName(xueyuanInfo.getName());
            }
        }
        return PageInfo.of(infos);
    }

}
