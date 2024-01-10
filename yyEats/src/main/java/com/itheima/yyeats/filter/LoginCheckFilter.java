package com.itheima.yyeats.filter;

import cn.hutool.core.text.AntPathMatcher;
import com.alibaba.fastjson.JSON;
import com.itheima.yyeats.common.BaseContext;
import com.itheima.yyeats.common.R;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @param
 * * check whether user login
 * @return
 */
@WebFilter(filterName = "loginCheckFilter")
@Slf4j
public class LoginCheckFilter implements Filter {

//    address comparer
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        1. get current uri
        String requestURI = request.getRequestURI();

        log.info("Intercepted the request:{}",requestURI);

//        2. check whether need to be checj
        String [] urls =new String[]{
                "/employee/login",
                "employee/logout",
                "/backend/**",
                "/front/**"
        };
        boolean check = check(urls,requestURI);

//        3. if no need to check login status
        if(check){
            log.info("current request{}not check",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

//        4. check login status, if so, go ahead
       if( request.getSession().getAttribute("employee")!=null) {
            log.info("user already login,id is{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");

           BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
       }

//        5. if not login, return no login
        log.info("user not login");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestURI){
        for(String url: urls){
            boolean match = PATH_MATCHER.match(url,requestURI);
            if(match) return true;
        }
        return false;
    }
}
