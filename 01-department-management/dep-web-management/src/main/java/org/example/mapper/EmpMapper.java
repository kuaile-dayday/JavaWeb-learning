package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

//    -------------------- 原始分页查询实现 ------------------------
//    /*
//    *  查询总记录数 total
//    * */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();
//
//    /*
//    * 分页查询数据  rows
//    * */
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id=d.id " +
//            "order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id=d.id " +
//            "where e.name like ? and e.gender = ? and e.entry_date between ? and ?" +
//            "order by e.update_time desc")
//    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
    public List<Emp> list(EmpQueryParam empQueryParam);

/*
* 新增员工基本信息
* */
    @Options(useGeneratedKeys = true,keyProperty = "id")// 获取主键
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

/*
* 根据ID批量删除员工的基本信息
* */
    void deleteByIds(List<Integer> ids);

    /*
    * 根据ID查询员工信息以及工作经历信息
    * */
    Emp getById(Integer id);

    /*
    * 根据ID更新员工基本信息
    * */
    void updateById(Emp emp);

    /*
    * 统计员工职位人数
    * */
    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();

    /*
    * 统计员工性别人数
    * */
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    /*
    * 查询全部班主任员工
    * */
//    @Select("select name from emp where job = 1")
    @Select("select e.* from emp e where job = 1")
    List<Emp> listMaster();

    /*
    * 根据用户名和密码查询员工信息
    * */
    @Select("select id, username, name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}