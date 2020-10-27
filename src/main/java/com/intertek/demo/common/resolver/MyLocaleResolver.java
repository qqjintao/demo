package com.intertek.demo.common.resolver;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author barry.jt.huang
 */
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("lang");
        if (StringUtils.isEmpty(l)) {
            if(request.getSession().getAttribute("lang") != null){
                return new Locale(request.getSession().getAttribute("lang").toString(), request.getSession().getAttribute("country").toString());
            }
            return request.getLocale();
        } else if("EN".equalsIgnoreCase(l)){
            request.getSession().setAttribute("lang", "en");
            request.getSession().setAttribute("country", "US");
            return new Locale("en", "US");
        }else{
            request.getSession().setAttribute("lang", "zh");
            request.getSession().setAttribute("country", "CN");
            return new Locale("zh", "CN");
        }
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
