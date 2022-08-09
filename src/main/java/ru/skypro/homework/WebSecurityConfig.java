package ru.skypro.homework;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * Класс глобальных настроек безопасности приложения.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * Создаёт объект управления пользователями на основе настроеной в приложении СУБД.
     * <p>
     * Используются таблицы аутентификации стандартной структуры, предусмотренной фреймворком.
     *
     * @return объект управления пользователями для JDBC.
     * @throws Exception исключения при создании объекта.
     */
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Настойка цепочки защитных Фильтров, используемых при обработке HTTP запросов от клиента.
     * <p>
     * Включает поддержку CORS протокола (защита от факерской подмены адресов сайтов).
     * <p>
     * Отключает поддержку CSRF протокола (защита от включения страниц сайта во фреймы на других сайтах).
     * <p>
     * Тонкая настройка API методов требующих и не требующих аутентификации.
     * <p>
     * Указание используемого типа аутентификации BASIC (простейший из известных).
     *
     * @param http объект {@see HttpSecurity}, позволяющий настраивать веб-безопасность для определенных http-запросов.
     * @return объект цепочки защитных Фильтров.
     * @throws Exception исключения при создании объекта.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/ads").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                .antMatchers("/v3/api-docs", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                .antMatchers("/ads/**", "/users/**", "/upl/**").authenticated()
                .and()
                .httpBasic(); // Authenticate users with HTTP basic authentication

        return http.build();
    }
}
