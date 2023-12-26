package org.java.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInStudentVo {
    private String id;
    private String grade;
    private String className;
    private String name;
    private Integer status;
}
