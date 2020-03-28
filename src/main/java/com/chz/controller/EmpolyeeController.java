package com.chz.controller;

import com.chz.dao.DepartmentDao;
import com.chz.dao.EmployeeDao;
import com.chz.pojo.Department;
import com.chz.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//templates下的文件只能从@Controller中访问,需要thymeleaf依赖
@Controller
/**
 * 所有return 都会经过模板引擎(thymeleafViewResolver)
 */
public class EmpolyeeController {
    //    spring 2.2.5可以访问到template下的index包含静态资源
//    @RequestMapping({"/index","/"})
//    public String hello(){
//        return "index";
//    }
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    //查询所有员工返回列表页面
    @GetMapping(path = "/emps")//员工管理
    public String employees(Model model) {
        Collection<Employee> employees = employeeDao.queryEmployees();
        model.addAttribute("employees", employees);//request域中的attr不会再其他页面生效
        return "emp/list";
    }

    //来到添加员工界面
    @GetMapping(path = "/emp")//restful select
    public String toAddPage(Model model) {
        //获取动态的下拉菜单
        Collection<Department> departments = departmentDao.queryDeps();
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    //    添加员工
    @PostMapping(path = "/emp") //restful insert
    public String addEmp(Employee employee) {
//        return "emps";//不能这么写,因为会经过模板引擎(视图解析器),并不是访问url
        employeeDao.insertEmployee(employee);//修改的时employee的map集合,所有可以重定向
        // /表示当前项目路径(不会带参数)
        return "redirect:/emps";
    }

    //修改员工页面(超链接无法用@PutMapping),查出员工并回显
    @GetMapping(path = "/emp/{id}")//restful update
    public String toEdit(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.queryEmployeeById(id);
        model.addAttribute("emp", employee);
        //获取动态的下拉菜单(如果只返回一个部门的前端的foreach就会失效)
        Collection<Department> departments = departmentDao.queryDeps();
        model.addAttribute("departments", departments);
        //回到修改页面(修改添加二合一)
        return "emp/add";//因为要回写参数要请求转发
    }

    @PutMapping(path = "/emp")//需要提交员工id
    public String editEmp(Employee employee) {
        //接收的数据没有id值就会覆盖原有的map中的value
        employeeDao.insertEmployee(employee);
        return "redirect:/emps";
    }

    //删除员工
    @DeleteMapping("/emp/{id}") //restful delete
    public String deleteEmp(@PathVariable("id") Integer id) {
        employeeDao.deleteEmployeeById(id);
        return "redirect:/emps";
    }


}
