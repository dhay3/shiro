package com.chz.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Teacher {
    private Integer id;
    private String tName;
    private Integer age;
    private Integer gender;
    private String password;
    @TableLogic
    private Integer flag;
    @Version
    private Integer version;
}
