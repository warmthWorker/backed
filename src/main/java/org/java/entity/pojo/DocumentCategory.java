package org.java.entity.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCategory {
    @TableId(type = IdType.AUTO)
    private Integer categoryId;
    private String categoryName;
    private Integer parentCategoryId;
}
