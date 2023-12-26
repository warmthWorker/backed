package org.java.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.pojo.Document;
import org.java.entity.pojo.DocumentCategory;
import org.java.mapper.DocumentCategoryMapper;
import org.java.mapper.DocumentMapper;
import org.java.service.DocumentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentCategoryServiceImpl extends ServiceImpl<DocumentCategoryMapper, DocumentCategory>
                implements DocumentCategoryService {

    @Autowired
    private DocumentCategoryMapper categoryMapper;

    @Autowired
    private DocumentMapper documentMapper;


    public List<DocumentCategory> getRootCategories() {
        // 查询没有父目录的类别
        return categoryMapper.selectList(new QueryWrapper<DocumentCategory>().isNull("parent_category_id"));
    }

    public List<DocumentCategory> getSubcategories(Long parentId) {
        // 查询指定父目录下的子类别
        return categoryMapper.selectList(new QueryWrapper<DocumentCategory>().eq("parent_category_id", parentId));
    }

    public List<Document> getFiles(Long categoryId) {
        // 查询指定类别下的文件
        return documentMapper.selectList(new QueryWrapper<Document>().eq("category_id", categoryId));
    }


    //创建类目
    public Integer createCategory(Long outlineId, String newCategoryName) {
        // 查询大纲所在的类目
        DocumentCategory outlineCategory = categoryMapper.selectById(outlineId);

        if (outlineCategory != null) {
            // 创建新的类目
            DocumentCategory newCategory = new DocumentCategory();
            newCategory.setCategoryName(newCategoryName);
            newCategory.setParentCategoryId(null); // 顶级类目，没有父目录
            categoryMapper.insert(newCategory);

            return  newCategory.getCategoryId();
        } else {
            return -1;
        }
    }
}
