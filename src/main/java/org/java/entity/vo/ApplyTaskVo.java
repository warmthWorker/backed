package org.java.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyTaskVo {
    private Integer taskId;
    private String academicTerm;
    private String courseCode;
    private String courseName;
    private String courseCategory;
    private Integer creditHours;
    private String className;
    private Integer studentCount;
    private Integer startWeek;
    private Date applicationDeadline;
}
