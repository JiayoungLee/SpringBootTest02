package com.hqyj.javaSpringBoot.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author  Jayoung
 * createDate  2020/8/17 0017 16:08
 * version 1.0
 */
@Component
public class RequestViewInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestViewInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("==== Pre interceptor ====");
        return HandlerInterceptor.super.preHandle(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.debug("==== Post interceptor ====");

        if (modelAndView == null || modelAndView.getViewName().startsWith("redirect")){
            return;
        }
        String path = request.getServletPath();
        String template = (String) modelAndView.getModelMap().get("template");
        if (StringUtils.isBlank(template)){
            if (path.startsWith("/")){
                path = path.substring(1);
            }
            modelAndView.getModelMap().
                    addAttribute("template",path.toLowerCase());
        }

        HandlerInterceptor.super.preHandle(request,response,handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.debug("==== After interceptor ====");
        HandlerInterceptor.super.preHandle(request,response,handler);
    }

}
