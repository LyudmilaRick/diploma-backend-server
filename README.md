# diploma-backend-server
Cобрать бэкенд-часть сайта на Java.

ТЗ на разработку
https://skyengpublic.notion.site/7856d24e254848a39b3e8c8aab921081
https://skyengpublic.notion.site/9-c9b2f3fc7b5c4b4889ec929544cbf55d

фронтенд контейнер для Docker’а
https://drive.google.com/drive/u/1/folders/1E7v5KSE9miAxIUl1GaRBuDtmW8XKxhol

Swagger Editor
https://editor.swagger.io/


Postman API Platform | Sign Up for Free
https://www.postman.com/

CORS для чайников: история возникновения, как устроен и оптимальные методы работы
https://habr.com/ru/company/macloud/blog/553826/
https://medium.com/nuances-of-programming/компьютерная-наука-наглядно-cors-20a97786c18c

snakeyaml-engine
https://bitbucket.org/snakeyaml/snakeyaml-engine/src/master/

IntelliJ IDEA
https://www.jetbrains.com/idea/resources/
cUX-cMd-6vh-Meg

Документирование API в Java приложении с помощью Swagger и OpenAPI 3.0
https://habr.com/ru/post/536388/

Simple Logging Facade for Java (SLF4J)
https://www.slf4j.org/

Lombok @Slf4j Examples
https://javabydeveloper.com/lombok-slf4j-examples/

LIKE Queries in Spring JPA Repositories
https://www.baeldung.com/spring-jpa-like-queries

List of Java Exceptions | Programming.Guide
https://programming.guide/java/list-of-java-exceptions.html

Список кодов состояния HTTP — Википедия
https://ru.wikipedia.org/wiki/Список_кодов_состояния_HTTP

CORS
https://habr.com/ru/company/macloud/blog/553826/
https://www.baeldung.com/spring-security-cors-preflight
https://www.moesif.com/blog/technical/cors/Authoritative-Guide-to-CORS-Cross-Origin-Resource-Sharing-for-REST-APIs/

Enabling Cross Origin Requests for a RESTful Web Service
https://spring.io/guides/gs/rest-service-cors/

Аннотации JPA (Java Persistence API) - Leo Life Blog
https://leodev.ru/blog/hibernate/аннотации-jpa-java-persistence-api/

Как настроить авторизацию в Spring Security
https://sysout.ru/dobavlenie-spring-security-v-proekt-nastrojki-po-umolchaniyu/
https://sysout.ru/kak-nastroit-avtorizatsiyu-v-spring-security/
https://sysout.ru/kak-ustroena-autentifikatsiya-v-spring-security/
https://sysout.ru/nastrojka-jdbc-autentifikatsii-v-spring-security/
https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring

Spring Security - JdbcUserDetailsManager Example | JDBC Authentication and Authorization
https://www.javainterviewpoint.com/spring-security-jdbcuserdetailsmanager-example/

Версионирование структуры БД при помощи Liquibase
https://habr.com/ru/post/548882/

Бэкенд-часть проекта предполагает реализацию следующего функционала:
----------------------------------------------------------------------
- Авторизация и аутентификация пользователей.
- Распределение ролей между пользователями: пользователь и администратор*.
- CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи – только свои.
- Под каждым объявлением пользователи могут оставлять отзывы.
- В заголовке сайта можно осуществлять поиск объявлений по названию.
- Показывать и сохранять картинки объявлений*.

Этапы проекта: 
Этап I. 	Настройка Spring-проекта.
Этап II. 	Настройка авторизации и аутентификации.
Этап III. 	Описание моделей объявлений и отзывов.
Этап IV. 	Определение permissions к контроллерам.
Этап V. 	Сохранение и получение картинок. 
Этап VI.	Финальная доработка проекта и создание презентации.

Платформа для перепродажи товаров по объявлениям
A platform for reselling things by ads

ads-reselling-platform

ads 	- объявления
reviews	- отзывы

поиск объявлений по названию
  объявления
  - ключ
  - название (tuitle)
  - содержимое (content)
  - картинки объявлений
  отзывы
  - ключ
  - текст

Auth Controller	- Авторизация
User Controller	- Пользователи
Ads  Controller	- Объявления

Links:
  http://localhost:8080
  http://localhost:8080/swagger-ui.html

Default user:
  user@gmail.com
  password
  USER

Журнал:
  C:\Users\rav16\ads-reselling-platform\app.log

База данных:
----------------------------------------------------------------------

ricksoft_ads_reselling
- A database of platform for reselling things by ads

logint  : ads_owner
password: 1ZSEywJo

Tables:
  user_data
  ads_header
  ads_review
  ads_images

Docker:
----------------------------------------------------------------------
  cmd
  docker run -p3000:3000 skypro-frontend-diploma-image
  http://localhost:3000


Server run:
----------------------------------------------------------------------
"C:\Program Files\Java\jdk-17.0.2+8\bin\java" -jar target\ricksoft-ads-reselling-platform-0.0.1-SNAPSHOT.jar
иди
startup.bat
