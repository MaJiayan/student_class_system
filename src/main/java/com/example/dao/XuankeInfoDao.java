package com.example.dao;


import com.example.entity.XuankeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface XuankeInfoDao extends Mapper<XuankeInfo> {
    @Select("select* from xuanke_info where name=#{name} and teacherId=#{teacherId} and studentId=#{studentId} limit 1")
    XuankeInfo find(@Param("name") String name, @Param("teacherId")Long teacherId,@Param("studentId") Long studentId);

    @Select("select* from xuanke_info where name=#{name}")
   XuankeInfo findByName(@Param("name") String name);
    @Select("select* from xuanke_info where name like concat('%',#{name},'%')")
    List<XuankeInfo> findByNamePage(@Param("name") String name);
    @Select("select e.*,f.name as studentName from " +
            "(select c.*,d.name as teacherName from" +
            " (select a.*,b.name as zhuanyeName from" +
            " xuanke_info as a left join zhuanye_info as b on a.zhuanyeId=b.id) " +
            "as c left join teacher_info as d on c.teacherId=d.id) " +
            "as e left join student_info as f on e.studentId=f.id")
    List<XuankeInfo> findAllJoinZhuanyeAndTeacherAndStudent();
    List<XuankeInfo> findByCondition(@Param("teacherId") Long teacherId,@Param("studentId") Long studentId);
//    @Select("select a.*,b.name as studentName from xuanke_info as a left join student_info as b on a.studentId=b.id")
//    List<XuankeInfo> findAllJoinStudent();
}
