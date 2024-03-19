package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.dao.ClassInfoDao;
import com.example.dao.TeacherInfoDao;
import com.example.dao.ZhuanyeInfoDao;
import com.example.entity.TeacherInfo;
import com.example.entity.XueyuanInfo;
import com.example.entity.ClassInfo;
import com.example.entity.ZhuanyeInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassInfoService {
    @Resource
    private ClassInfoDao classInfoDao;
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private TeacherInfoDao teacherInfoDao;

    public ClassInfo findById(Long id) {
        return classInfoDao.selectByPrimaryKey(id);
    }

    public void update(ClassInfo ClassInfo) {
        classInfoDao.updateByPrimaryKeySelective(ClassInfo);
    }

    public void add(ClassInfo ClassInfo) {
        ClassInfo info=classInfoDao.findByName(ClassInfo.getName());
        if(ObjectUtil.isNotEmpty(info)){
            throw new CustomException("-1","该学院名称已存在");
        }

        classInfoDao.insertSelective(ClassInfo);
    }

    public List<ClassInfo> findAll() {
        return classInfoDao.findAllJoinZhuanyeAndTeacher();
    }

    public void deleteById(Long id) {
        classInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<ClassInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ClassInfo> infos=classInfoDao.findAllJoinZhuanyeAndTeacher();
        return PageInfo.of(infos);
    }

    public PageInfo<ClassInfo> findPageName(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<ClassInfo> infos=classInfoDao.findByNamePage(name);
        for(ClassInfo ClassInfo:infos){
            if(ObjectUtil.isNotEmpty(ClassInfo.getZhuanyeId())){
                ZhuanyeInfo zhuanyeInfo= zhuanyeInfoDao.selectByPrimaryKey(ClassInfo.getZhuanyeId());
                ClassInfo.setZhuanyeName(zhuanyeInfo.getName());
            }
        }
        for(ClassInfo ClassInfo:infos){
            if(ObjectUtil.isNotEmpty(ClassInfo.getTeacherId())){
                TeacherInfo teacherInfo= teacherInfoDao.selectByPrimaryKey(ClassInfo.getTeacherId());
                ClassInfo.setTeacherName(teacherInfo.getName());
            }
        }
        return PageInfo.of(infos);
    }
}
