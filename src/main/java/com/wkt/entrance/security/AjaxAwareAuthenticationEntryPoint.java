package com.wkt.entrance.security;

import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import com.wkt.entrance.utils.sysenum.SysCode;
import com.wkt.entrance.utils.sysenum.SysConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.wkt.entrance.utils.ZmjUtil.isAjaxRequest;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author : zmj
 * @description : 当检测到未登录时，转跳的方法
 * ---------------------------------
 */
public class AjaxAwareAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //判断是否是Ajax方式进入
        if(isAjaxRequest(request)){
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.getWriter().print(RestfulResultUtils.error(ErrorCode.NOT_LOGIN.getCode(),"尚未登录!").toString());
            response.getWriter().flush();
        }else{
            response.sendRedirect("notLogin");
        }
    }
}
