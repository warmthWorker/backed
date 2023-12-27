package org.java.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.java.annotation.Excel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutFileDataVo {
    @Excel(name = "实习编号")
    private Integer taskId;
    @Excel(name = "课程代码")
    private String courseCode;//课程代码
    @Excel(name = "课程名称")
    private String courseName;//课程名称
    @Excel(name = "课程性质")
    private String courseCategory;//课程性质
    @Excel(name = "合班信息")
    private String className;//合班信息
    @Excel(name = "学分")
    private Integer creditHours;//学分
    @Excel(name = "总学时学时")
    private Integer creditScore;//总学时学时 任务天数 * 8
    @Excel(name = "已选人数")
    private Integer studentCount; //已选人数
    @Excel(name = "总工作量")
    private Integer totalWordTime;//总工作量
    @Excel(name = "负责教师")
    private String teacher;//负责教师
    @Excel(name = "个人工作量")
    private double personalWork;//个人工作量
}
