package com.wkt.entrance.common.aspect;

import com.wkt.entrance.common.RestfulResult;
import com.wkt.entrance.common.exception.CommonException;
import com.wkt.entrance.utils.RestfulResultUtils;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 注释RestfulAnnotation的切面，用于自动装箱RestfulResult
 * @author zmj
 */
@Aspect
@Component
public class RestfulControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(RestfulControllerAspect.class);

    @Pointcut("@annotation(RestfulAnnotation)")
    public void restController(){

    }

    @Around("restController()")
    public  Object doAround(ProceedingJoinPoint pjp) throws CommonException {
        Object result;
        String methodName = pjp.getSignature().getName();
        try {
            // 获取目标方法原始的调用参数
            Object[] args = pjp.getArgs();
            // 保存目标方法执行后的返回值
            result = pjp.proceed(args);
            //自动装箱RestfulAnnotation，json解析
            if(result instanceof String){
                return RestfulResultUtils.success(result).toString();
            }else if(result instanceof RestfulResult){
                return result;
            }else {
                return result;
            }
        }catch (CommonException ce){
            throw ce;
        }catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new CommonException(throwable.getMessage());
        }
    }
}
