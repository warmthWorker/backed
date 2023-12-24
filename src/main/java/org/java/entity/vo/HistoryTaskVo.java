package org.java.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTaskVo {
    private String username;
    private String collegeName;
    private Integer academicTerm;
    private String courseCode;
    private String courseName;
    private String courseCategory;
}
