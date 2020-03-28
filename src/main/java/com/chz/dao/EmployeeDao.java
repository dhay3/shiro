package com.chz.dao;

import com.chz.pojo.Department;
import com.chz.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//员工dao
@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employees = null;
    @Autowired
    private DepartmentDao departmentDao;

    //先加载静态代码块然后才自动装配,所以不能通过departmentDao.querByDepId()
    static {
        employees = new HashMap<>();
        employees.put(1001, new Employee(1001, "张三", "a@qq.com", 1, new Department(103, "教研部")));
        employees.put(1002, new Employee(1002, "李四", "b@qq.com", 0, new Department(102, "市场部")));
        employees.put(1003, new Employee(1003, "王五", "c@qq.com", 1, new Department(105, "后勤部")));
        employees.put(1004, new Employee(1004, "赵六", "d@qq.com", 1, new Department(104, "运营部")));
        employees.put(1005, new Employee(1005, "老王", "f@qq.com", 0, new Department(102, "教学部")));
    }

    //主键自增
    private static Integer initId = 1006;

    //增加员工
    public void insertEmployee(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(initId++);
        }
        //前端只要传id就能设置部门
        employee.setDepartment(departmentDao.queryDepById(employee.getDepartment().getId()));
        //如果有id,就会覆盖map中原有的value
        employees.put(employee.getId(), employee);
    }

    //查询所有员工
    public Collection<Employee> queryEmployees() {
        return employees.values();
    }

    //查询员工
    public Employee queryEmployeeById(Integer id) {
        return employees.get(id);
    }

    //删除员工
    public void deleteEmployeeById(Integer id) {
        employees.remove(id);
    }
}
