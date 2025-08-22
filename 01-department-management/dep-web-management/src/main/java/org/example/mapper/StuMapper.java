package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StuMapper {
    /*
    * 条件分页查询
    * */
    List<Student> page(StuQueryParam stuQueryParam);

    /*
    * 根据学院id删除 可批量删除
    * */
    void delete(List<Integer> ids);

    /*
    * 添加学员
    * */
    @Insert("insert into student(name, no, gender, phone, degree, clazz_id, id_card, is_college, address, graduation_date, violation_count, violation_score, create_time, update_time)" +
            "values (#{name},#{no},#{gender},#{phone},#{degree},#{clazzId},#{idCard},#{isCollege},#{address},#{graduationDate},#{violationCount},#{violationScore},#{createTime},#{updateTime})")
    void insert(Student stu);

    /*
     * 根据ID查询学员
     * */
    @Select("select * from student where id=#{id}")
    Student findByid(Integer id);

    /*
    * 修改学员
    * */
    void update(Student student);

    /*
    * 修改违纪信息
    * */
    @Update("update student set violation_score=#{sum}, violation_count=#{count} where id=#{id}")
    void violate(Integer id, short sum, short count);

    /*
     * 统计学员学历信息
     * */
    @MapKey("degree")
    List<Map<String,Object>> countStuDegreeData();

    /*
    * 统计班级人数
    * */
    @MapKey("clazz_id")
    List<Map<String, Object>> countStuCountData();
}
