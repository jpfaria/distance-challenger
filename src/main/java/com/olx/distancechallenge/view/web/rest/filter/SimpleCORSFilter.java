package com.olx.distancechallenge.view.web.rest.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(503)
public class SimpleCORSFilter extends OncePerRequestFilter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN  = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    @Value("${endpoints.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Value("${endpoints.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS,PATCH}")
    private String allowedMethods;

    @Value("${endpoints.cors.allowed-headers:x-requested-with,Content-Type,Pragma,Cache-Control,If-Modified-Since,X-Pricing-Token,Authorization}")
    private String allowedHeaders;

    @Value("${endpoints.cors.exposed-headers:X-Pricing-Token-Renewed,X-TID}")
    private String exposedHeaders;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (StringUtils.isNotEmpty(allowedOrigins)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigins);
        }

        if (StringUtils.isNotEmpty(allowedMethods)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, allowedMethods);
        }

        if (StringUtils.isNotEmpty(allowedHeaders)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, allowedHeaders);
        }

        if (StringUtils.isNotEmpty(exposedHeaders)) {
            response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeaders);
        }

        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
