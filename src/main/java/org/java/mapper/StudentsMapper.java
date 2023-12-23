package org.java.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.java.entity.pojo.Student;

/**
* @author 程子
//* @description 针对表【students】的数据库操作Mapper
* @createDate 2023-12-23 11:06:20
* @Entity .Students
*/
@Mapper
public interface StudentsMapper extends BaseMapper<Student> {

}




