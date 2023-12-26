package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.java.entity.pojo.Document;
import org.java.entity.pojo.DocumentCategory;

import java.util.List;

public interface DocumentCategoryService extends IService<DocumentCategory> {

    public List<DocumentCategory> getRootCategories();

    public List<DocumentCategory> getSubcategories(Long parentId);

    public List<Document> getFiles(Long categoryId);

    public boolean createCategory(Long outlineId, String newCategoryName);
}
