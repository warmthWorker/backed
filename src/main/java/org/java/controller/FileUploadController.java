package org.java.controller;


import lombok.extern.slf4j.Slf4j;
import org.java.entity.SecurityUser;
import org.java.entity.pojo.User;
import org.java.utils.UploadFileUtil;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileUploadController {
    @Autowired
    private UploadFileUtil uploadFileUtil;

    /**
     *
     * @param file
     * @param map  taskId ,categoryId
     * @return
     */
    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file
                                    ,@RequestParam Map<String, String> map) {
        log.info("文件上传{}==={}",file.getOriginalFilename(),map);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        try {
            uploadFileUtil.upload(file,map,user.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
       return Result.success("上传成功");
    }

}
