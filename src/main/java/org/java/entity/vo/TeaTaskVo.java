package org.java.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeaTaskVo {
    private Integer id;
    private String username;
    private String collegeName;
    private int mark;
    private int academicTerm;
    private int studentCount;
    private String courseName;
    private String courseCategory;
    private String courseCode;
}
