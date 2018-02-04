package com.wkt.entrance.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wkt.entrance.common.aspect.RestfulAnnotation;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.entity.Sys_user;
import com.wkt.entrance.mapper.Sys_userMapper;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.ZmjUtil;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
code is far away from bug with the animal protecting
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 *
 *   @description :  公共Controller所有Controller都继承CommonController
 *   ---------------------------------
 *   @author : zmj
 *   @date : Create in 2017/12/27 11:34　
 */
@Controller
public class CommonController {

    @Autowired
    Sys_userMapper sys_userMapper;

    protected String message="";

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;
    /**
     * jackson 对象转换器
     */
    protected ObjectMapper mapper = new ObjectMapper();

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){

        this.request = request;

        this.response = response;

        this.session = request.getSession();

        this.message="";

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取当前用户对象
     * @return
     * @throws Exception
     */
    public Sys_user getThisUser() throws Exception{
        String username="";
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            username=userDetails.getUsername();
        }catch (Exception e){
            System.out.println("No session dev...");
            String username2 = request.getHeader("username");
            String password = request.getHeader("password");
            Sys_user user =sys_userMapper.findByName(username);
            if(password.equals(user.getPassword())){
                username=username2;
            }
        }
        if(!ZmjUtil.isNullOrEmpty(username)){
            Sys_user sysUser= sys_userMapper.findByName(username);
            return sysUser;
        }else {
            throw new CommonException(ErrorCode.NOT_FIND_ERROR,"无法获取当前用户对象，请检查！");
        }
    }

}
