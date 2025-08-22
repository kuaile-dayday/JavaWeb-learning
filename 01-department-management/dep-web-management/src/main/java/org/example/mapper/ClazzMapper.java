package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;

import java.util.List;

@Mapper
public interface ClazzMapper {
//
//    /*
//    * 分页查询
//    * */
//    @Select("select c.*,e.name masterName from clazz c left join emp e on c.master_id = e.id limit #{start}, #{pageSize}")
//    public List<Clazz> list(Integer start,Integer pageSize);

    /*
    * 查询总记录数
    * */
    @Select("select count(*) from clazz")
    public Long count();

    /*
    * 条件分页查询（班级管理）
    * */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /*
    * 根据id删除班级
    * */
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    /*
    * 新增班级
    * */
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time)" +
            "values (#{name},#{room},#{beginDate},#{endDate},#{masterId},#{subject},#{createTime},#{updateTime})")
    void insert(Clazz clazz);

    /*
    * 根据ID查询班级
    * */
    @Select("select c.*,e.name masterName from clazz c left join emp e on c.master_id = e.id where c.id = #{id}")
    Clazz findByid(Integer id);

    /*
    * 查询班级列表
    * */
    @Select("select * from clazz")
    List<Clazz> listAll();

    /*
    * 修改班级
    * */
    void update(Clazz clazz);
}
