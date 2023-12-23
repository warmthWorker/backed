package org.java.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.java.entity.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("select *from user where username = #{username}")
    public User selectByUsername(String username);
}
