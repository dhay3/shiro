package com.chz;

import com.chz.dao.UserMapper;
import com.chz.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ShiroThymeleafAuthorityApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

    }

    @Test
    public void testDB() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_user u,t_role r where u.u_id = r.r_id ");
        System.out.println(list);
    }

    @Test
    public void testQuery() {
        User users = userMapper.queryUserRoles("zsf");
        System.out.println(users);
    }

    @Test
    public void testMD5() {
        ByteSource salt = ByteSource.Util.bytes("zsf");
        SimpleHash md5 = new SimpleHash("MD5", 111, salt, 10);
        System.out.println(md5);
    }

}
