package com.chz.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
//员工表
@Data
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;//0 女 1男
    private Department department;
    private Date birth;

    public Employee(Integer id, String lastName, String email, Integer gender, Department department) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.department = department;
        //内部直接生成date
        this.birth = new Date();
    }
}
