package org.java.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.java.entity.pojo.Student;
import org.java.mapper.StudentsMapper;
import org.java.service.StudentsService;
import org.springframework.stereotype.Service;

/**
* @author 程子
* @description 针对表【students】的数据库操作Service实现
* @createDate 2023-12-23 11:06:20
*/
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Student>
    implements StudentsService {

}




