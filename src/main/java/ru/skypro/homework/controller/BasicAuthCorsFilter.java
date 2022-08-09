package ru.skypro.homework.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Класс фильта для предварительной обработки запросов клиента.
 * <p>
 * В данном проекте нет необходимости в данном фильтре, т.к. вся необходимая информация по CORS протоколу находится в
 * нотациях классов контроллеров.
 * <p>
 * На текущий момент используется только для отладки и DEBUG журналирования поступающих на сервер запросов.
 */
@Slf4j
@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {

    /**
     * Реализация фильтра запросов.
     * @param request объект данных запроса клиента.
     * @param response объект данных ответа сервера.
     * @param chain объект цепочки фильтров.
     * @throws ServletException ошибки сервлета.
     * @throws IOException  ошибки ввода/вывода.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // Debugging a sequence of requests from the frontend. 
        // Switch off on production server!
        if (log.isDebugEnabled()) {
            log.debug("Request: " + request.getMethod() + " " + request.getRequestURL() + " " + request.getQueryString());
            String authType = request.getAuthType();
            if (authType != null && authType.length() > 0) {
                log.debug("Authorized: " + request.getUserPrincipal().getName());
            }
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                log.debug("Headers:");
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    log.debug(" " + headerName + ": " + request.getHeader(headerName));
                }
            }
        }

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

}
