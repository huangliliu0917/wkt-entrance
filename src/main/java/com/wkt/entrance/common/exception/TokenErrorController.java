package com.wkt.entrance.common.exception;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
 * @description :公用异常控制器
 * ---------------------------------
 */
@RestController
public class TokenErrorController  extends BasicErrorController {
    public TokenErrorController(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    private static final String PATH = "/error";

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        //HttpStatus status = getStatus(request);
        int status;
        if(request.getAttribute("myStatus")!=null){
            status = (int) request.getAttribute("myStatus");
        }else {
            status = getStatus(request).value();
        }
        throw new  CommonException(status,body.get("error")+":"+body.get("message").toString());
        //return new ResponseEntity<Map<String, Object>>(body, status);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
