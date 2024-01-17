package com.itheima.yyeats.controller;

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

}
