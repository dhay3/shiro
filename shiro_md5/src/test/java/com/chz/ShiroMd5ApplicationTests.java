package com.chz;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroMd5ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test//获取MD5
    public void testMD5() {
        //加密形式
        String AlgorithmName = "MD5";
        //登入密码
        String source = "111";
        //设置盐值,为了即使两个不同的用户明文密码相同,存到数据库加密后的密码还是不同的
        ByteSource salt = ByteSource.Util.bytes("zsf");
        //加密次数
        int hashIterations = 10;
        SimpleHash md5 = new SimpleHash(AlgorithmName, source, salt, hashIterations);
        System.out.println(md5);
    }
}
