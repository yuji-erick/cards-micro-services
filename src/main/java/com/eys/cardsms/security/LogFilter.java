package com.eys.cardsms.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LogFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - startTime;

        logRequest(wrappedRequest);
        logResponse(wrappedResponse, duration);
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("\n============================== REQUEST ==============================\n")
                  .append("Method: ").append(request.getMethod()).append("\n")
                  .append("URL: ").append(request.getRequestURI()).append("\n")
                  .append("Headers: ").append(getHeadersAsString(request)).append("\n")
                  .append("Body: ").append(getRequestBody(request)).append("\n")
                  .append("\n============================== REQUEST ==============================\n");

        logger.info(requestLog.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) throws IOException {
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("\n============================== RESPONSE ==================================\n")
                   .append("Status: ").append(response.getStatus()).append("\n")
                   .append("Duration: ").append(duration).append(" ms\n")
                   .append("Body: ").append(getResponseBody(response)).append("\n")
                   .append("\n============================== RESPONSE ==================================\n");

        logger.info(responseLog.toString());

        response.copyBodyToResponse();
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        return buf.length > 0 ? new String(buf, StandardCharsets.UTF_8) : "[EMPTY]";
    }

    private String getResponseBody(ContentCachingResponseWrapper response) throws IOException {
        byte[] buf = response.getContentAsByteArray();
        return buf.length > 0 ? new String(buf, StandardCharsets.UTF_8) : "[EMPTY]";
    }

    private String getHeadersAsString(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("; ");
        }
        return headers.toString();
    }

}
