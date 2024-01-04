package com.itheima.yyeats.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.yyeats.common.R;
import com.itheima.yyeats.entity.Employee;
import com.itheima.yyeats.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
}
