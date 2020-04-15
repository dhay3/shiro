package com.chz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class User {
    private Integer uId;
    private String name;
    private String password;
    @TableField(exist = false)
    private Set<Role> roles;
}
