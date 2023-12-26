package org.java.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.java.entity.dto.CreateCategoryDto;
import org.java.entity.pojo.Document;
import org.java.entity.pojo.DocumentCategory;
import org.java.entity.pojo.Internshiptask;
import org.java.mapper.DocumentMapper;
import org.java.service.DocumentCategoryService;
import org.java.service.InternshiptaskService;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@Slf4j
public class DocumentCategoryController {

    @Autowired
    private DocumentCategoryService categoryService;
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private InternshiptaskService internshiptaskService;

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
        Integer categoryId = categoryService.createCategory(createCategoryDto.getOutlineId(),
                                                createCategoryDto.getNewCategoryName());
        if (categoryId > 0){
            return Result.success(categoryId);
        }
        return Result.error("操作失败");
    }

    //获取关联实习任务的文件

    /**
     * 获取关联实习任务的文件
     * @param map
     * @return
     */
    @GetMapping("/getSymbolFiles")
    public Result<List<Document>> getSymbolFiles(@RequestParam Map<String, String> map){
        log.info("获取关联实习任务{}", map);

        Internshiptask internshiptask = internshiptaskService.getSymbol(map.get("courseCategory")
                                        , Integer.parseInt(map.get("academicTerm"))
                                        , map.get("courseName"));

        List<Document> documentList = documentMapper.selectList(new QueryWrapper<Document>()
                            .eq("task_id", internshiptask.getTaskId()));
        return Result.success(documentList);
    }
}
