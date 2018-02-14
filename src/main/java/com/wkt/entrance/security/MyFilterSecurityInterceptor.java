package com.wkt.entrance.security;

import com.wkt.entrance.utils.RestfulResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        //预请求处理
        if(request instanceof HttpServletRequest) {
            if("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())){
                System.out.println("OPTIONS");
                ((HttpServletResponse) response).setHeader("Content-type", "text/html;charset=UTF-8");
                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin",((HttpServletRequest) request).getHeader("Origin"));
                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","*");
                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
                ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "access-control-allow-credentials,x-requested-with");
                response.getWriter().print(RestfulResultUtils.success().toString());
                response.getWriter().flush();
                return;
            }
        }
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
            //fi里面有一个被拦截的url
            //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
            //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够

        fi.getRequest().getSession(false);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
