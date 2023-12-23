package org.java.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InternShipTaskDto {
    private String academic_term;
    private String course_code;
    private String course_name;
    private String course_category;
    private Integer credit_hours;
    private String class_name;
    private Integer student_count;
    private Integer start_week;
    private String requirements;
    private String remarks;
    private LocalDateTime application_deadline;
}
