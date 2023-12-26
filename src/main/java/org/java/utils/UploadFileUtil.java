package org.java.utils;

import org.java.entity.pojo.Document;
import org.java.mapper.DocumentMapper;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

@Component
public class UploadFileUtil {
    @Autowired
    private DocumentMapper documentMapper;
    private static final String UPLOAD_DIR = "C:\\uploads\\";


    public boolean upload(MultipartFile file , Map<String, String> map,Integer userId) throws IOException {
        Document document = new Document();
        // 获取文件名并进行中文编码转换
        String originalFilename = new String(Objects.requireNonNull(file.getOriginalFilename())
                .getBytes("ISO-8859-1"), "UTF-8");

        // 对文件名进行URL编码
        String encodedFilename = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8);
        // 文件存储路径
        String filePath = UPLOAD_DIR + encodedFilename;

        // 保存文件到指定路径
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);

        document.setDocumentName(originalFilename);
        document.setFilePath(filePath);
        document.setUserId(userId);
        document.setRequired(false);
        document.setTaskId(Integer.parseInt(map.get("taskId")));
        document.setCategoryId(Integer.parseInt(map.get("categoryId")));

        return documentMapper.insert(document) > 0;
    }
}
