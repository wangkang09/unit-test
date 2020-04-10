package com.wangkang.spock.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wangkang
 * @date 2020/4/10- 22:56
 * @since
 */
public interface UserMapper {
    @Insert("INSERT INTO user (name,age) values (#{name},#{age})")
    boolean addUser(@Param("name") String name, @Param("age") Integer age);

    @Select("SELECT count(*) FROM user")
    int count();
}
