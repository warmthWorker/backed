package org.java.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputScoreDto {
    private Integer stuId;
    private double majorScore;
    private Integer taskId;
}
