package com.wkt.entrance.controller;

import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.utils.RestfulResultUtils;
import com.wkt.entrance.utils.ZmjUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wkt.entrance.common.CommonController;
import com.wkt.entrance.common.aspect.RestfulAnnotation;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.utils.VerifyCodeUtils;
import com.wkt.entrance.utils.sysenum.ErrorCode;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Objects;

@Controller
public class HelloController extends CommonController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String loginPage2() throws IOException {
        return "login";
    }

    @RequestMapping(value = "/loginPage" , method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/hello" , method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    @RestfulAnnotation
    public String hello(){
        return "hello";
    }


    @RequestMapping(value = "/login" ,produces="application/json;charset=UTF-8")
    @ResponseBody
    @RestfulAnnotation
    public RestfulResult login()throws CommonException{
        throw new CommonException(ErrorCode.NOT_LOGIN);
    }

    @RequestMapping(value = "/notLogin" , method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    @RestfulAnnotation
    public void notLogin(){
        throw new CommonException(ErrorCode.NOT_LOGIN);
    }

    @RequestMapping(value = "/loginError" , method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    @RestfulAnnotation
    public void loginError(){
        throw new CommonException(ErrorCode.VERIFY_ERROR,"登录失败，账号密码错误");
    }

    @RequestMapping(value = "/loginSuccess" , method = RequestMethod.GET)
    public ModelAndView loginSuccess() throws IOException {
        return new ModelAndView("redirect:http://www.58wxy.com/wkt-entrance/static/index.html");
    }

    @RequestMapping(value = "/home" , method = RequestMethod.GET)
    public String  home(){
        System.out.println(ZmjUtil.isAjaxRequest(request));
        return "index";
    }

    /**
     * 生成验证码图片base64方法
     *
     * @throws Exception
     */
    @RequestMapping("/authImageBase64")
    @ResponseBody
    @RestfulAnnotation
    public String authImageBase64() throws Exception {
        JSONObject jsonObject = new JSONObject();
        //利用图片工具生成图片
        //是生成的验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //将验证码存入Session
        session.setAttribute("imageCode", verifyCode);
        //生成图片
        response.setContentType("image/png");
        int w = 100, h = 30;
        jsonObject.put("src", "data:image/jpg;base64," + VerifyCodeUtils.outputImage(w, h, verifyCode));
        return jsonObject.toString();
    }

    /**
     * 生成验证码图片图片方法
     *
     * @throws Exception
     */
    @RequestMapping("/authImage")
    public void authImage() throws Exception {
        //利用图片工具生成图片
        //是生成的验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //将验证码存入Session
        session.setAttribute("imageCode", verifyCode);
        //生成图片
        response.setContentType("image/png");
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/checkImageCode", method = RequestMethod.GET, produces = "application/javascript;charset=UTF-8")
    @ResponseBody
    @RestfulAnnotation
    public String checkImageCode(String imageCode) throws Exception {
        String imageCodeT = (String) session.getAttribute("imageCode");
        if (Objects.equals(imageCodeT, "") || Objects.equals(imageCode, imageCodeT)) {
            throw new CommonException(ErrorCode.VERIFY_ERROR, "验证码错误");
        }
        return "Success";
    }
}
