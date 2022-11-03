package com.example.job4j_todo.validation.filter;

import com.example.job4j_todo.web.UserSession;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.equals("/") || uri.endsWith("logIn") || uri.endsWith("favicon.ico")
            || uri.endsWith("signPage") || uri.endsWith("signIn")) {
            chain.doFilter(req, res);
            return;
        }
        UserSession userSession = (UserSession) req.getSession().getAttribute(
                "scopedTarget.userSession");
        if (userSession == null || userSession.getAccount() == null) {
            res.sendRedirect(req.getContextPath() + "/");
            return;
        }
        chain.doFilter(req, res);
    }
}
