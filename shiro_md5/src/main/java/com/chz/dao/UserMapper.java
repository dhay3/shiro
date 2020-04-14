package com.chz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

//@Mapper
//如果添加了@MapperScann就可以不用配置@Mapper,要添加在Application中不是test
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where name=#{username}")
    @Results(id = "roleMap", value = {
            //因为采用了Mybatis的注解配置查询,字段名与属性名不相同,要指出
            @Result(property = "uId",column = "u_id",id = true),
            @Result(property = "roles", column = "u_id",
                    many = @Many(select = "com.chz.dao.RoleMapper.queryRole",
                            fetchType = FetchType.LAZY))
    })
    User queryUserRoles(@Param("username") String username);
}
