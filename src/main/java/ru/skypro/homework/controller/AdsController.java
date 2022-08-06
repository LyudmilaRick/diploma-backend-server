package ru.skypro.homework.controller;

import java.io.IOException;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.service.AdsService;

/**
 * Контроллер обработки запросов по объявлениям и комментариям.
 */
@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Объявления", description = "Методы работы с объявлениями и комментариями к ним.")
public class AdsController {

    private final AdsService adsService;

    @GetMapping("/ads")
    @Operation(tags = {"Объявления"}, summary = "Список объявлений", description = "Получить список всех объявлений.")
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        log.info("Invoke: {0}()", "getAllAds");
        return new ResponseEntity<>(adsService.getAllAds(), HttpStatus.OK);
    }

    @GetMapping(value = "/ads/me")
    @Operation(tags = {"Объявления"}, summary = "Собственные объявления", description = "Получить список всех собственных объявлений.")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "authenticated", required = false) Boolean authenticated, @Parameter(in = ParameterIn.QUERY, schema = @Schema()) @Valid @RequestParam(value = "authorities[0].authority", required = false) String authority, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "credentials", required = false) Object credentials, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "details", required = false) Object details, @Parameter(in = ParameterIn.QUERY, schema = @Schema()) @Valid @RequestParam(value = "principal", required = false) Object principal) {
        log.info("Invoke: {0}({1}, {2}, {3}, {4}, {5})", "getAdsMe", authenticated, authority, credentials, details, principal);
        if (authenticated) {
            return new ResponseEntity<>(adsService.getMeAds(authority, credentials, details, principal), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/ads/{ad_pk}")
    @Operation(tags = {"Объявления"}, summary = "Данные объявления", description = "Получить все данные по заданному объявлению.")
    public ResponseEntity<FullAds> getAds(@PathVariable("ad_pk") Integer adsKey) {
        log.info("Invoke: {0}({1})", "getAds", adsKey);
        return new ResponseEntity<>(adsService.getAds(adsKey), HttpStatus.OK);
    }

    @PostMapping("/ads")
    @Operation(tags = {"Объявления"}, summary = "Новоё объявление", description = "Добавляет новоё объявление.")
    public ResponseEntity<Ads> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления", required = true, schema = @Schema()) @Valid @RequestBody CreateAds body) {
        log.info("Invoke: {0}({1})", "addAds", body);
        return new ResponseEntity<>(adsService.addAds(body), HttpStatus.OK);
    }

    @PatchMapping("/ads/{ad_pk}")
    @Operation(tags = {"Объявления"}, summary = "Изменить объявление", description = "Изменить данные заданного объявления.")
    public ResponseEntity<Ads> updateAds(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.DEFAULT, description = "Данные объявления", required = true, schema = @Schema()) @Valid @RequestBody Ads body) {
        log.info("Invoke: {0}({1}, {2})", "updateAds", adsKey, body);
        return new ResponseEntity<>(adsService.updateAds(adsKey, body), HttpStatus.OK);
    }

    @DeleteMapping("/ads/{ad_pk}")
    @Operation(tags = {"Объявления"}, summary = "Удалить объявление", description = "Удалить объявление по заданному идентификатору.")
    public ResponseEntity<?> deleteAds(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey) {
        log.info("Invoke: {0}({1})", "deleteAds", adsKey);
        adsService.deleteAds(adsKey);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/ads/{ad_pk}/comment")
    @Operation(tags = {"Объявления"}, summary = "Список комментариев к объявлению", description = "Получить список всех комментариев по заданному объявлению.")
    public ResponseEntity<ResponseWrapperComment> getAllComments(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey) {
        log.info("Invoke: {0}({1})", "getAllComments", adsKey);
        return new ResponseEntity<>(adsService.getAllComments(adsKey), HttpStatus.OK);
    }

    @GetMapping("/ads/{ad_pk}/comment/{id}")
    @Operation(tags = {"Объявления"}, summary = "Комментарий по объявлению", description = "Получить данные комментария по заданному объявлению.")
    public ResponseEntity<AdsComment> getComment(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.PATH, description = "Идентификатор комментария", required = true, schema = @Schema()) @PathVariable("id") Integer comKey) {
        log.info("Invoke: {0}({1}, {2})", "getComment", adsKey, comKey);
        return new ResponseEntity<>(adsService.getComment(adsKey, comKey), HttpStatus.OK);
    }

    @PostMapping("/ads/{ad_pk}/comment")
    @Operation(tags = {"Объявления"}, summary = "Новый комментарий", description = "Добавляет новый комментарий к существующему объявлению.")
    public ResponseEntity<AdsComment> addComment(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.DEFAULT, description = "Данные комментария", required = true, schema = @Schema()) @Valid @RequestBody AdsComment body) {
        log.info("Invoke: {0}({1}, {2})", "addComment", adsKey, body);
        return new ResponseEntity<>(adsService.addComment(adsKey, body), HttpStatus.OK);
    }

    @PatchMapping("/ads/{ad_pk}/comment/{id}")
    @Operation(tags = {"Объявления"}, summary = "Изменить комментарий", description = "Изменить содержимое комментария по заданному объявлению.")
    public ResponseEntity<AdsComment> updateComment(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.PATH, description = "Идентификатор комментария", required = true, schema = @Schema()) @PathVariable("id") Integer comKey, @Parameter(in = ParameterIn.DEFAULT, description = "Данные комментария", required = true, schema = @Schema()) @Valid @RequestBody AdsComment body) {
        log.info("Invoke: {0}({1}, {2}, {3})", "updateComment", adsKey, comKey, body);
        return new ResponseEntity<>(adsService.updateComment(adsKey, comKey, body), HttpStatus.OK);
    }

    @Operation(tags = {"Объявления"}, summary = "Удаление комментария", description = "Удаляет существующий комментарий к объявлению.")
    @DeleteMapping("/ads/{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteComment(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.PATH, description = "Идентификатор комментария", required = true, schema = @Schema()) @PathVariable("id") Integer comKey) {
        log.info("Invoke: {0}({1}, {2})", "deleteComment", adsKey, comKey);
        return new ResponseEntity<>(adsService.deleteComment(adsKey, comKey), HttpStatus.OK);
    }

    @PostMapping("/upl")
    @Operation(tags = {"Объявления"}, summary = "Сохранить изображение", description = "Сохраняет на сервере сайта картинку, полученную от клиента.")
    public String setImage(@RequestParam MultipartFile file) {
        log.info("Invoke: {0}({1})", "setImage", file != null ? file.getSize() : 0);

        if (file == null) {
            throw new IllegalArgumentException("Image file content is empty.");
        }

        try {
            return adsService.setImage(file.getName(), file.getSize(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/images/{id}/", produces = {MediaType.IMAGE_PNG_VALUE})
    @Operation(tags = {"Объявления"}, summary = "Получить изображение", description = "Возвращает клиенту содержимое файла картинки по её ключу.")
    public byte[] getImage(@PathVariable("id") String imgKey) {
        log.info("Invoke: {0}({1})", "getImage", imgKey);
        return adsService.getImage(imgKey);
    }

}
