package org.java.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutFileDataVo {
    private Integer taskId;
    private String courseCode;//课程代码
    private String courseName;//课程名称
    private String courseCategory;//课程性质
    private String className;//合班信息
    private Integer creditHours;//学分
//    private Integer creditHours;//总学时学时 任务天数 * 8
    private Integer studentCount; //已选人数
//    private Integer totalWordTime;//总工作量
    private String teacher;//负责教师
//    private Integer personalWork;//个人工作量
}
