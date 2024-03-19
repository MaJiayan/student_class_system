package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.dao.XueyuanInfoDao;
import com.example.dao.ZhuanyeInfoDao;
import com.example.entity.XueyuanInfo;
import com.example.entity.ZhuanyeInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZhuanyeInfoService {
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;

    public ZhuanyeInfo findById(Long id) {
        return zhuanyeInfoDao.selectByPrimaryKey(id);
    }

    public void update(ZhuanyeInfo zhuanyeInfo) {
        zhuanyeInfoDao.updateByPrimaryKeySelective(zhuanyeInfo);
    }

    public void add(ZhuanyeInfo zhuanyeInfo) {
        ZhuanyeInfo info=zhuanyeInfoDao.findByName(zhuanyeInfo.getName());
        if(ObjectUtil.isNotEmpty(info)){
            throw new CustomException("-1","该学院名称已存在");
        }

        zhuanyeInfoDao.insertSelective(zhuanyeInfo);
    }

    public List<ZhuanyeInfo> findAll() {
        return zhuanyeInfoDao.findAllJoinXueyuan();
    }

    public void deleteById(Long id) {
        zhuanyeInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<ZhuanyeInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ZhuanyeInfo> infos=zhuanyeInfoDao.findAllJoinXueyuan();
        return PageInfo.of(infos);
    }

    public PageInfo<ZhuanyeInfo> findPageName(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<ZhuanyeInfo> infos=zhuanyeInfoDao.findByNamePage(name);
        for(ZhuanyeInfo zhuanyeInfo:infos){
            if(ObjectUtil.isNotEmpty(zhuanyeInfo.getXueyuanId())){
                XueyuanInfo xueyuanInfo=xueyuanInfoDao.selectByPrimaryKey(zhuanyeInfo.getXueyuanId());
                zhuanyeInfo.setXueyuanName(xueyuanInfo.getName());
            }
        }
        return PageInfo.of(infos);
    }
}
