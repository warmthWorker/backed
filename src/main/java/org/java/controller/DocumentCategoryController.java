package org.java.controller;


import lombok.extern.slf4j.Slf4j;
import org.java.entity.dto.CreateCategoryDto;
import org.java.entity.pojo.Document;
import org.java.entity.pojo.DocumentCategory;
import org.java.service.DocumentCategoryService;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
public class DocumentCategoryController {

    @Autowired
    private DocumentCategoryService categoryService;

    @GetMapping("/subcategories")
    public Result<List<DocumentCategory>> getSubcategories(Long parentId) {
        log.info("查询类目列表{}",parentId);
        if (parentId == null) {
            // 查询没有父目录的类别
            return Result.success(categoryService.getRootCategories());
        } else {
            // 查询指定父目录下的子类别
            return Result.success(categoryService.getSubcategories(parentId));
        }
    }

    @GetMapping("/files")
    public Result<List<Document>> getFiles(@RequestParam("categoryId") Long categoryId) {
        // 查询指定类别下的文件
        log.info("查询指定类别下的文件{}",categoryId);
        return Result.success(categoryService.getFiles(categoryId));
    }

    /**
     * 创建类目
     * OutlineId 需要传入的是同级目录的其他类别id
     * @param createCategoryDto
     * @return
     */
    @PostMapping("/create")
    public Result createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        log.info("创建类目:{},{}",createCategoryDto.getOutlineId(),createCategoryDto.getNewCategoryName());

        if (categoryService.createCategory(createCategoryDto.getOutlineId(), createCategoryDto.getNewCategoryName())){
            return Result.success("操作成功");
        }
        return Result.error("操作失败");
    }
}
