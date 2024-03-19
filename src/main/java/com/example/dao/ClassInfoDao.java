package com.example.dao;


import com.example.entity.ClassInfo;
import com.example.entity.ZhuanyeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ClassInfoDao extends Mapper<ClassInfo> {
    @Select("select* from class_info where name=#{name}")
    ClassInfo findByName(@Param("name") String name);
    @Select("select* from class_info where name like concat('%',#{name},'%')")
    List<ClassInfo> findByNamePage(@Param("name") String name);
    @Select("select c.*,d.name as teacherName from (select a.*,b.name as zhuanyeName " +
            "from class_info as a left join zhuanye_info as b on a.zhuanyeId=b.id)" +
            " as c left join teacher_info as d on c.teacherId=d.id")
    List<ClassInfo> findAllJoinZhuanyeAndTeacher();
    @Select("select * from class_info where name=#{name} and teacherId=#{teacherId} limit 1")
    ClassInfo findByNameAndTeacher(@Param("name")String name, @Param("teacherId")Long teacherId);
}
