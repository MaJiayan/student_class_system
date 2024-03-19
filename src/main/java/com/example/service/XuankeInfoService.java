package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.dao.*;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class XuankeInfoService {
    @Resource
    private XuankeInfoDao XuankeInfoDao;
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private TeacherInfoDao teacherInfoDao;
    @Resource
    private StudentInfoDao studentInfoDao;
    @Resource
    private ClassInfoDao classInfoDao;

    public XuankeInfo findById(Long id) {
        return XuankeInfoDao.selectByPrimaryKey(id);
    }

    public void update(XuankeInfo XuankeInfo) {
        XuankeInfoDao.updateByPrimaryKeySelective(XuankeInfo);
    }

    public void add(XuankeInfo XuankeInfo) {
//        XuankeInfo info=XuankeInfoDao.findByName(XuankeInfo.getName());
//        if(ObjectUtil.isNotEmpty(info)){
//            throw new CustomException("-1","该学院名称已存在");
//        }

        XuankeInfoDao.insertSelective(XuankeInfo);
    }

    public List<XuankeInfo> findAll(HttpServletRequest request) {
        Account user=(Account) request.getSession().getAttribute("user");
        if(ObjectUtil.isEmpty(user)){
            throw new CustomException("-1","登录已失效，请重新登录");
        }
        List<XuankeInfo> list = null;
        if(1==user.getLevel()){
            return XuankeInfoDao.findAllJoinZhuanyeAndTeacherAndStudent();
        }else if(2==user.getLevel()){
            list= XuankeInfoDao.findByCondition(user.getId(),null );
        }else if(3==user.getLevel()){
            list= XuankeInfoDao.findByCondition(null, user.getId() );
        }
        if(list!=null){
            for(XuankeInfo xuankeInfo:list){
                ZhuanyeInfo zhuanyeInfo=zhuanyeInfoDao.selectByPrimaryKey(xuankeInfo.getZhuanyeId());
                TeacherInfo teacherInfo=teacherInfoDao.selectByPrimaryKey(xuankeInfo.getTeacherId());
                StudentInfo studentInfo=studentInfoDao.selectByPrimaryKey(xuankeInfo.getStudentId());
                xuankeInfo.setZhuanyeName(zhuanyeInfo.getName());
                xuankeInfo.setTeacherName(teacherInfo.getName());
                xuankeInfo.setStudentName(studentInfo.getName());
            }
        }
        return list;
    }

    public void deleteById(Long id) {
        XuankeInfo xuankeInfo=XuankeInfoDao.selectByPrimaryKey(id);
        ClassInfo classInfo=classInfoDao.findByNameAndTeacher(xuankeInfo.getName(),xuankeInfo.getTeacherId());
        classInfo.setYixuan(classInfo.getYixuan()-1);
        classInfoDao.updateByPrimaryKeySelective(classInfo);
        XuankeInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<XuankeInfo> findPage(Integer pageNum, Integer pageSize,HttpServletRequest request) {
        Account user=(Account) request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,pageSize);
        if(ObjectUtil.isEmpty(user)){
            throw new CustomException("-1","登录已失效，请重新登录");
        }
        List<XuankeInfo> list = null;
        if(1==user.getLevel()){
            list= XuankeInfoDao.findAllJoinZhuanyeAndTeacherAndStudent();
        }else if(2==user.getLevel()){
            list= XuankeInfoDao.findByCondition(user.getId(),null );
        }else if(3==user.getLevel()){
            list= XuankeInfoDao.findByCondition(null, user.getId() );
        }
        if(list!=null){
            for(XuankeInfo xuankeInfo:list){
                ZhuanyeInfo zhuanyeInfo=zhuanyeInfoDao.selectByPrimaryKey(xuankeInfo.getZhuanyeId());
                TeacherInfo teacherInfo=teacherInfoDao.selectByPrimaryKey(xuankeInfo.getTeacherId());
                StudentInfo studentInfo=studentInfoDao.selectByPrimaryKey(xuankeInfo.getStudentId());
                xuankeInfo.setZhuanyeName(zhuanyeInfo.getName());
                xuankeInfo.setTeacherName(teacherInfo.getName());
                xuankeInfo.setStudentName(studentInfo.getName());
            }
        }

    //    List<XuankeInfo> infos=XuankeInfoDao.findAllJoinZhuanyeAndTeacherAndStudent();
        return PageInfo.of(list);
    }

    public PageInfo<XuankeInfo> findPageName(Integer pageNum, Integer pageSize, String name,HttpServletRequest request) {
        Account user=(Account) request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,pageSize);
        List<XuankeInfo> infos=null;
        if(ObjectUtil.isEmpty(user)){
            throw new CustomException("-1","登录已失效，请重新登录");
        }

        if(1==user.getLevel()){

        }else if(2==user.getLevel()){
            infos= XuankeInfoDao.findByCondition(user.getId(),null );
        }else if(3==user.getLevel()){
            infos= XuankeInfoDao.findByCondition(null, user.getId() );
        }

//        for(XuankeInfo XuankeInfo:infos){
//            if(ObjectUtil.isNotEmpty(XuankeInfo.getZhuanyeId())){
//                ZhuanyeInfo zhuanyeInfo= zhuanyeInfoDao.selectByPrimaryKey(XuankeInfo.getZhuanyeId());
//                XuankeInfo.setZhuanyeName(zhuanyeInfo.getName());
//            }
//        }
//        for(XuankeInfo XuankeInfo:infos){
//            if(ObjectUtil.isNotEmpty(XuankeInfo.getTeacherId())){
//                TeacherInfo teacherInfo= teacherInfoDao.selectByPrimaryKey(XuankeInfo.getTeacherId());
//                XuankeInfo.setTeacherName(teacherInfo.getName());
//            }
//        }
//        for(XuankeInfo XuankeInfo:infos){
//            if(ObjectUtil.isNotEmpty(XuankeInfo.getStudentId())){
//                StudentInfo studentInfo= studentInfoDao.selectByPrimaryKey(XuankeInfo.getStudentId());
//                XuankeInfo.setStudentName(studentInfo.getName());
//            }
//        }
infos=XuankeInfoDao.findByNamePage(name);
        if(infos!=null){
            for(XuankeInfo xuankeInfo:infos){
                ZhuanyeInfo zhuanyeInfo=zhuanyeInfoDao.selectByPrimaryKey(xuankeInfo.getZhuanyeId());
                TeacherInfo teacherInfo=teacherInfoDao.selectByPrimaryKey(xuankeInfo.getTeacherId());
                StudentInfo studentInfo=studentInfoDao.selectByPrimaryKey(xuankeInfo.getStudentId());
                xuankeInfo.setZhuanyeName(zhuanyeInfo.getName());
                xuankeInfo.setTeacherName(teacherInfo.getName());
                xuankeInfo.setStudentName(studentInfo.getName());
            }
        }

        return PageInfo.of(infos);
    }

    public XuankeInfo find(String name, Long teacherId, Long studentId) {
        return XuankeInfoDao.find(name,teacherId,studentId);
    }

}
