package com.itheima.yyeats.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.yyeats.common.R;
import com.itheima.yyeats.entity.Employee;
import com.itheima.yyeats.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    /*
    * 员工登陆
    * */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

//        1. encode the password from pages using md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2. query the database according to username from the page
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

//        3. if no result, return failure
        if(emp == null){
            return R.error("login failed");
        }

//        4. compare passwords, if no same, return failure
        if(!emp.getPassword().equals(password)){
            return R.error("wrong password");
        }

//        5. check whether status is ok
        if(emp.getStatus()==0){
            return R.error("Status is inactive");
        }

//        6. login successfully, store id into Session
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
//        1. clean userIde in Session
        request.getSession().removeAttribute("employee");
        return R.success("Logout successfully");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody  Employee employee){
        log.info("新增的员工信息：{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//        employee.setUpdateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//
//        Long empId = (Long)request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("Save new Employee successfully");
    }


//    Employee Pagination Query
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page ={},pageSize={},name={}",page,pageSize,name);

//        构造分页构造器
        Page pageInfo = new Page(page,pageSize);

//        构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper =new LambdaQueryWrapper<>();

//        添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

//        添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);


        return R.success(pageInfo);
    }


//    common method for every update function
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        System.out.println("hi0 "+employee.getStatus());
        log.info(employee.toString());
        System.out.println("hi1 "+employee.getStatus());
        Long empId= (Long)request.getSession().getAttribute("employee");
//      employee parameter has converted status
        employee.setUpdateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        employee.setUpdateUser(empId);
        System.out.println("hi2 "+employee.getStatus());
        employeeService.updateById(employee);

        return R.success("successfully edited employee's status");

    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("id of current searching employee");
        Employee employee = employeeService.getById(id);
        if(employee!=null){

            return R.success(employee);
        }
        return R.error("No such employee");
    }






}
