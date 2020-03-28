package com.chz.dao;

import com.chz.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.*;

//部门dao
@Repository
public class DepartmentDao {
    //模拟数据库中的数据
    private static Map<Integer, Department> departments = null;
    //数据库中已有的数据
    static {
        departments = new HashMap<>();
        departments.put(101, new Department(101, "教学部"));
        departments.put(102, new Department(102, "市场部"));
        departments.put(103, new Department(103, "教研部"));
        departments.put(104, new Department(104, "运营部"));
        departments.put(105, new Department(105, "后勤部"));
    }

    //获取所有信息
    public Collection<Department> queryDeps() {
        //values()返回一个Collection包含map中的value
        return departments.values();
    }

    //通过id查询部门
    public Department queryDepById(Integer id) {
        return departments.get(id);
    }
}
