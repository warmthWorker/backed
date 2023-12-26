package org.java.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDataDto {
    private Integer pageNumber;
    private Integer pageSize;
    private String username;
    private Integer taskId;
    private Integer term;
}
