package com.hqyj.javaSpringBoot.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

/**
 * author  Jayoung
 * createDate  2020/8/17 0017 12:46
 * version 1.0
 */
@WebFilter(filterName = "requestParamaFilter", urlPatterns = "/**")
@Order(1)       //顺序，越小越靠前
public class RequestParamaFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestParamaFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("====== Init Request Param Filter ======");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("====== Do Request Param Filter ======");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpServletRequest) {
            @Override
            public String getParameter(String name) {
                String value = httpServletRequest.getParameter(name);
                if (StringUtils.isNoneBlank(value)){
                    return value.replaceAll("fuck","***");
                }
                return super.getParameter(name);
            }

            @Override
            public String[] getParameterValues(String name) {
                String[] values = httpServletRequest.getParameterValues(name);
                if (values != null && values.length>0){
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].replaceAll("fuck", "***");
                    }
                    return values;
                }
                return super.getParameterValues(name);
            }
        };

        //TODO
        filterChain.doFilter(wrapper,servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("====== Destroy Request Param Filter ======");
    }
}
