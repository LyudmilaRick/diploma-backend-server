package ru.skypro.homework.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.service.AdsService;

/**
 * Контроллер обработки запросов по объявлениям и комментариям, размещенным на сайте.
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true",
        allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
@RequiredArgsConstructor
@Tag(name = "Объявления", description = "Методы работы с объявлениями и комментариями к ним.")
public class AdsController {

    private final AdsService adsService;

    @GetMapping("/ads")
    @Operation(tags = {"Объявления"}, summary = "Список объявлений", description = "Получить список всех объявлений.")
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        log.info("Invoke: {}()", "getAllAds");
        return new ResponseEntity<>(adsService.getAllAds(), HttpStatus.OK);
    }

    @GetMapping(value = "/ads/me")
    @Operation(tags = {"Объявления"}, summary = "Собственные объявления", description = "Получить список всех собственных объявлений.")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication auth) {
        log.info("Invoke: {}({})", "getAdsMe", auth.getName());
        return new ResponseEntity<>(adsService.getMeAds(auth.getName()), HttpStatus.OK);
    }

    @GetMapping("/ads/{ad_pk}")
    @Operation(tags = {"Объявления"}, summary = "Данные объявления", description = "Получить все данные по заданному объявлению.")
    public ResponseEntity<FullAdsDto> getAds(@PathVariable("ad_pk") Integer adsKey) {
        log.info("Invoke: {}({})", "getAds", adsKey);
        return new ResponseEntity<>(adsService.getAds(adsKey), HttpStatus.OK);
    }

    @PostMapping("/ads")
    @Operation(tags = {"Объявления"}, summary = "Новоё объявление", description = "Добавляет новоё объявление.")
    public ResponseEntity<AdsDto> addAds(Authentication auth, @Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления", required = true, schema = @Schema()) @Valid @RequestBody CreateAdsDto body) {
        log.info("Invoke: {}({})", "addAds", body);
        return new ResponseEntity<>(adsService.addAds(auth.getName(), body), HttpStatus.OK);
    }

    @PatchMapping("/ads/{ad_pk}")
    @Operation(tags = {"Объявления"}, summary = "Изменить объявление", description = "Изменить данные заданного объявления.")
    public ResponseEntity<AdsDto> updateAds(Authentication auth, @Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.DEFAULT, description = "Данные объявления", required = true, schema = @Schema()) @Valid @RequestBody AdsDto body) {
        log.info("Invoke: {}({}, {})", "updateAds", adsKey, body);
        return new ResponseEntity<>(adsService.updateAds(auth.getName(), adsKey, body), HttpStatus.OK);
    }

    @DeleteMapping("/ads/{ad_pk}")
    @Operation(tags = {"Объявления"}, summary = "Удалить объявление", description = "Удалить объявление по заданному идентификатору.")
    public ResponseEntity<?> deleteAds(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey) {
        log.info("Invoke: {}({})", "deleteAds", adsKey);
        adsService.deleteAds(adsKey);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/ads/{ad_pk}/comments")
    @Operation(tags = {"Объявления"}, summary = "Список комментариев к объявлению", description = "Получить список всех комментариев по заданному объявлению.")
    public ResponseEntity<ResponseWrapperCommentDto> getAllComments(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey) {
        log.info("Invoke: {}({})", "getAllComments", adsKey);
        return new ResponseEntity<>(adsService.getAllComments(adsKey), HttpStatus.OK);
    }

    @GetMapping("/ads/{ad_pk}/comment/{id}")
    @Operation(tags = {"Объявления"}, summary = "Комментарий по объявлению", description = "Получить данные комментария по заданному объявлению.")
    public ResponseEntity<AdsComment> getComment(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.PATH, description = "Идентификатор комментария", required = true, schema = @Schema()) @PathVariable("id") Integer comKey) {
        log.info("Invoke: {}({}, {})", "getComment", adsKey, comKey);
        return new ResponseEntity<>(adsService.getComment(adsKey, comKey), HttpStatus.OK);
    }

    @PostMapping("/ads/{ad_pk}/comment")
    @Operation(tags = {"Объявления"}, summary = "Новый комментарий", description = "Добавляет новый комментарий к существующему объявлению.")
    public ResponseEntity<AdsComment> addComment(Authentication auth, @Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.DEFAULT, description = "Данные комментария", required = true, schema = @Schema()) @Valid @RequestBody AdsComment body) {
        log.info("Invoke: {}({}, {})", "addComment", adsKey, body);
        return new ResponseEntity<>(adsService.addComment(auth.getName(), adsKey, body), HttpStatus.OK);
    }

    @PatchMapping("/ads/{ad_pk}/comment/{id}")
    @Operation(tags = {"Объявления"}, summary = "Изменить комментарий", description = "Изменить содержимое комментария по заданному объявлению.")
    public ResponseEntity<AdsComment> updateComment(Authentication auth, @Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.PATH, description = "Идентификатор комментария", required = true, schema = @Schema()) @PathVariable("id") Integer comKey, @Parameter(in = ParameterIn.DEFAULT, description = "Данные комментария", required = true, schema = @Schema()) @Valid @RequestBody AdsComment body) {
        log.info("Invoke: {}({}, {}, {})", "updateComment", adsKey, comKey, body);
        return new ResponseEntity<>(adsService.updateComment(auth.getName(), adsKey, comKey, body), HttpStatus.OK);
    }

    @Operation(tags = {"Объявления"}, summary = "Удаление комментария", description = "Удаляет существующий комментарий к объявлению.")
    @DeleteMapping("/ads/{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteComment(@Parameter(in = ParameterIn.PATH, description = "Ключ записи объявления", required = true, schema = @Schema()) @PathVariable("ad_pk") Integer adsKey, @Parameter(in = ParameterIn.PATH, description = "Идентификатор комментария", required = true, schema = @Schema()) @PathVariable("id") Integer comKey) {
        log.info("Invoke: {}({}, {})", "deleteComment", adsKey, comKey);
        adsService.deleteComment(adsKey, comKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/upl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(tags = {"Объявления"}, summary = "Сохранить изображение", description = "Сохраняет на сервере сайта картинку, полученную от клиента.")
    public String setImage(@RequestParam MultipartFile file) {
        log.info("Invoke: {}({})", "setImage", file != null ? file.getSize() : 0);
        return adsService.upLoadAdsImg(file);

    }

    @GetMapping(value = {"/images/{id}", "/images/{id}/"}, produces = {MediaType.IMAGE_PNG_VALUE})
    @Operation(tags = {"Объявления"}, summary = "Получить изображение", description = "Возвращает клиенту содержимое файла картинки по её ключу.")
    public byte[] getImage(@PathVariable("id") String imgKey) {
        log.info("Invoke: {}({})", "getImage", imgKey);
        return adsService.getImage(imgKey);
    }

}
