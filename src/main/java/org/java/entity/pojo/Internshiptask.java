package org.java.entity.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Internshiptask {//实习任务表
    @TableId(type = IdType.AUTO)
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
    private Date applicationDeadline; // 报名截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTaskTime; // 任务开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date taskDeadline; // 任务完成时间，手动结束
    private Integer status = 0;// 可以报名0 任务开始1  任务结束2
}
