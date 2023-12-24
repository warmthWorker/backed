package org.java.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshiptaskVo {
    private Integer taskId;
    private Integer academicTerm;
    private String courseCode;
    private String courseName;
    private String courseCategory;
    private Integer creditHours;
    private String className;
    private Integer studentCount;
    private Integer startWeek;
    private String requirements;
    private String remarks;
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date applicationDeadline;
    private Integer status = 0;// 未结束0 已结束1
}
