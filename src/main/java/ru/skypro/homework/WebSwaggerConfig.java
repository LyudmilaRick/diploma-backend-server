package ru.skypro.homework;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс глобальных настроек Swagger.
 * <p>
 * Дополнительная информация:
 * <p>
 * OpenAPI Specification @see "https://swagger.io/specification/"
 * <p>
 * Documenting a Spring REST API Using OpenAPI 3.0 @see "https://www.baeldung.com/spring-rest-openapi-documentation"
 * <p>
 * Документирование API в Java приложении с помощью Swagger и OpenAPI 3.0
 *
 * @see "https://habr.com/ru/post/536388/"
 */
@Configuration
public class WebSwaggerConfig {

    /**
     * Настройка OpenAPI описания.
     *
     * @return объект описания API (@see "https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md").
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Функции API платформы перепродажи вещей по объявлениям")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("LRick@yandex.ru")
                                                .name("Людмила Рик")
                                )
                );
    }

}
