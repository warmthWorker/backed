package org.java.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InternshiptaskVo {
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime application_deadline;
}
