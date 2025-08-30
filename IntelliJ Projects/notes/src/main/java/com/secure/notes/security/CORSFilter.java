package com.secure.notes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Optional.ofNullable;


public class CORSFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(CORSFilter.class.getName());

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String origin = ((HttpServletRequest) req).getHeader("Origin");

        if (ofNullable(origin).isPresent() && origin.equals(frontendUrl)) {
            LOGGER.info("CORSFilter run");
            response.addHeader("Access-Control-Allow-Origin", frontendUrl);
            response.addHeader("Access-Control-Allow-Credentials", "true");

            if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type,Accept,Authorization,x-xsrf-token");
                response.setStatus(200);
            }
        }
        chain.doFilter(request, response);
    }
}
