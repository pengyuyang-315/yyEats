package com.itheima.yyeats.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.yyeats.common.R;
import com.itheima.yyeats.entity.User;
import com.itheima.yyeats.service.UserService;
import com.itheima.yyeats.utils.SMSUtils;
import com.itheima.yyeats.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @param
 * @return
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> senMsg(@RequestBody User user, HttpSession session){
//        get phoneNumber
        String phone  = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
//        Generate Random 4-digit Verification Code
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
//            access aliyun api to send MSG
//            SMSUtils.sendMessage("yyEats");
            session.setAttribute(phone,code);
            return R.success("Validation Code sent");
        }
        return R.error("Failed");

    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){

//        get phoneNumber
        String phone = map.get("phone").toString();
//        get validation code
        String code = map.get("code").toString();
//        from session get code
        Object codeInSession = session.getAttribute(phone);
//        compare codes
        if(codeInSession!=null &&  codeInSession.equals(code)){
            //        if successful, login

            //   check whether phone exists
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("failed");
    }
}
