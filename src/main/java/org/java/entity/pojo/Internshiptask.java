package org.java.entity.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Internshiptask {
    private Integer task_id;
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
