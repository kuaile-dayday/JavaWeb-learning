package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ClazzMapper;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

//    public PageResult<Clazz> page(Integer page, Integer pageSize) {
//        Long total = clazzMapper.count();
//
//        Integer start = (page - 1) * pageSize;
//        List<Clazz> clazzList = clazzMapper.list(start, pageSize);
//
//        if (!CollectionUtils.isEmpty(clazzList)) {
//            for (Clazz cla : clazzList) {  // 遍历集合
//                if(cla.getBeginDate().isAfter(LocalDate.now())){
//                    //BeginData 晚于当前日期
//                    cla.setStatus("未开班");
//                }
//                else if(cla.getEndDate().isBefore(LocalDate.now())){
//                    cla.setStatus("已结课");
//                }
//                else
//                    cla.setStatus("在读中");
//
//            }
//        }
//
//        return new PageResult<Clazz>(total,clazzList);
//    }

    /*
    * 班级管理——条件分页查询
    * */
    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        Integer page = clazzQueryParam.getPage();
        Integer pageSize = clazzQueryParam.getPageSize();
        PageHelper.startPage(page,pageSize);
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
        // 判断课程状态
        if (!CollectionUtils.isEmpty(clazzList)) {
            for (Clazz cla : clazzList) {  // 遍历集合
                if(cla.getBeginDate().isAfter(LocalDate.now())){
                    //BeginData 晚于当前日期
                    cla.setStatus("未开班");
                }
                else if(cla.getEndDate().isBefore(LocalDate.now())){
                    cla.setStatus("已结课");
                }
                else
                    cla.setStatus("在读中");

            }
        }

        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult<Clazz>(p.getTotal(),p.getResult());

    }

    /*
    * 班级管理——删除班级
    * */
    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    /*
    * 新增班级
    * */
    @Override
    public void insert(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        // 条件分页查询 就会添加状态和班主任姓名 刷新就会查询 然后就执行了set操作 所以这里不管了
        clazzMapper.insert(clazz);
    }

    /*
    * 根据ID查询班级
    * */
    @Override
    public Clazz findByid(Integer id) {
        Clazz clazz = clazzMapper.findByid(id);
        return clazz;
    }

    /*
    *
    * 查询班级列表*/
    @Override
    public List<Clazz> list() {
        List<Clazz> clazzList = clazzMapper.listAll();
        return clazzList;
    }

    /*
     * 修改班级
     * */
    @Override
    public void update(Clazz clazz) {
        clazzMapper.update(clazz);
    }


}
