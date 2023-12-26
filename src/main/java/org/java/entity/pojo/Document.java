package org.java.entity.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @TableId(type = IdType.AUTO)
    private Integer documentId;
    private String documentName;
    private Integer categoryId;
    private Integer taskId;
    private Integer userId;
    private String filePath;
    private boolean isRequired;
}
