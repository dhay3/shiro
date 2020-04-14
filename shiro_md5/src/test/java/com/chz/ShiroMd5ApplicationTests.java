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
        //登入用户名
        ByteSource username = ByteSource.Util.bytes("zsf");
        //加密次数
        int hashIterations = 10;
        SimpleHash md5 = new SimpleHash(AlgorithmName, source, username, hashIterations);
        System.out.println(md5);
    }
}
