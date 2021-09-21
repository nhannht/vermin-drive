package io.github.nhannht.vermindrive.mapper;

import io.github.nhannht.vermindrive.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    String selectSQL = "select * from users where username = #{username}";
    String insertSQL = "Insert into users (username,salt,password,firstname,lastname) values(#{userName},#{salt},#{password},#{firstName},#{lastName})";
    /**
     * find user from database with username
     * @param username
     * @return
     */
    @Select(selectSQL)
    User getUser(String username);

    @Insert(insertSQL)
    Integer insert(User user);
}
