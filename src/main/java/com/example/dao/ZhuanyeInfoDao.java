package com.example.dao;


import com.example.entity.StudentInfo;
import com.example.entity.ZhuanyeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ZhuanyeInfoDao extends Mapper<ZhuanyeInfo> {
    @Select("select* from zhuanye_info where name=#{name}")
    ZhuanyeInfo findByName(@Param("name") String name);
    @Select("select* from zhuanye_info where name like concat('%',#{name},'%')")
    List<ZhuanyeInfo> findByNamePage(@Param("name") String name);
    @Select("select a.*,b.name as xueyuanName from zhuanye_info as a left join xueyuan_info as b on a.xueyuanId=b.id")
    List<ZhuanyeInfo> findAllJoinXueyuan();

}
