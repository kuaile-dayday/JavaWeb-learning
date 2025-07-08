package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {
   /*
   * 查询所有部门数据
   * */
//    @Results({
//            @Result(column = "create_time",property = "createTime"),
//            @Result(column = "update_time",property = "updateTime")
//    })
    @Select("select id,name,create_time,update_time from dept order by update_time desc ;")
    List<Dept> findAll();

    @Delete("delete from dept where id= #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

//    根据id 查询部门
    @Select("select id,name,create_time,update_time from dept where id = #{id}")
    Dept getById(Integer deptId);

    /*
    *  修改部门
    * */
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
