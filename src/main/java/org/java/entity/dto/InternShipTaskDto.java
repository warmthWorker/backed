package org.java.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class InternShipTaskDto {
    private String academicTerm;
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
    private Date application_deadline;
}
