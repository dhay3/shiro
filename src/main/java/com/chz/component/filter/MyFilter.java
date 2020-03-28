package com.chz.component.filter;

import javax.servlet.*;
import java.io.IOException;

//@WebFilter("/servlet")
public class MyFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("拦截servlet");
        chain.doFilter(req, resp);
        System.out.println("放行servlet");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
