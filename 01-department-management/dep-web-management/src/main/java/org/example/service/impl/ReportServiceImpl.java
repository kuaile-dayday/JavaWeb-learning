package org.example.service.impl;

import org.example.mapper.EmpMapper;
import org.example.mapper.StuMapper;
import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StuMapper stuMapper;

    @Override
    public JobOption getEmpJobData() {
//        1、调用mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();//map:  pos=教研主管, num=1
//        2、组装结果，并返回
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    /*
     * 统计学员学历信息
     * */
    @Override
    public List<Map<String,Object>> getStuDegreeData() {
        return stuMapper.countStuDegreeData();
    }

    /*
     * 班级人数统计
     * */
    @Override
    public ClazzOption getStuCountData() {
        //1、调用mapper接口，获取统计数据
        List<Map<String, Object>> list = stuMapper.countStuCountData();
        //2、组装结果，并返回
        List<Object> clazzList = list.stream().map(dataMap -> dataMap.get("stu")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new ClazzOption(clazzList,dataList);
    }
}
