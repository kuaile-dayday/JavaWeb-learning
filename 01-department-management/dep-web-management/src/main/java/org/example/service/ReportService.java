package org.example.service;

import org.example.pojo.ClazzOption;
import org.example.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    /*
     * 统计员工性别信息
     * */
    List<Map<String, Object>> getEmpGenderData();

    /*
    * 统计学员学历信息
    * */
    List<Map<String, Object>> getStuDegreeData();

    /*
     * 班级人数统计
     * */
    ClazzOption getStuCountData();
}
