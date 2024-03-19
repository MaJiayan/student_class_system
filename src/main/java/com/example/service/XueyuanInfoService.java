package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.TeacherInfoDao;
import com.example.dao.XueyuanInfoDao;
import com.example.entity.Account;
import com.example.entity.TeacherInfo;
import com.example.entity.XueyuanInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XueyuanInfoService {
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;



    public XueyuanInfo findById(Long id) {
        return xueyuanInfoDao.selectByPrimaryKey(id);
    }

    public void update(XueyuanInfo xueyuanInfo) {
        xueyuanInfoDao.updateByPrimaryKeySelective(xueyuanInfo);
    }

    public void add(XueyuanInfo xueyuanInfo) {
        XueyuanInfo info=xueyuanInfoDao.findByName(xueyuanInfo.getName());
        if(ObjectUtil.isNotEmpty(info)){
            throw new CustomException("-1","该学院名称已存在");
        }

        xueyuanInfoDao.insertSelective(xueyuanInfo);
    }

    public List<XueyuanInfo> findAll() {
        return xueyuanInfoDao.selectAll();
    }

    public void deleteById(Long id) {
        xueyuanInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<XueyuanInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<XueyuanInfo> infos=xueyuanInfoDao.selectAll();
        return PageInfo.of(infos);
    }

    public PageInfo<XueyuanInfo> findPageName(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<XueyuanInfo> infos=xueyuanInfoDao.findByNamePage(name);
        return PageInfo.of(infos);
    }
}
