package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import ru.skypro.homework.dto.Ads;

import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;

/**
 * Интерфейс описания API методов для работы с объявлениями.
 */
public interface AdsApi {

    @Operation(summary = "addAdsComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = AdsComment.class))),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comment",
            produces = {"*/*"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<AdsComment> addAdsCommentsUsingPOST(@Parameter(in = ParameterIn.PATH, description = "ad_pk", required = true, schema = @Schema()) @PathVariable("ad_pk") String adPk, @Parameter(in = ParameterIn.DEFAULT, description = "comment", required = true, schema = @Schema()) @Valid @RequestBody AdsComment body);

    @Operation(summary = "addAds", description = "Добавить объявления", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Ads.class))),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads",
            produces = {"*/*"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Ads> addAdsUsingPOST(@Parameter(in = ParameterIn.DEFAULT, description = "createAds", required = true, schema = @Schema()) @Valid @RequestBody CreateAds body);

    @Operation(summary = "deleteAdsComment", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")})
    @RequestMapping(value = "/ads/{ad_pk}/comment/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAdsCommentUsingDELETE(@Parameter(in = ParameterIn.PATH, description = "ad_pk", required = true, schema = @Schema()) @PathVariable("ad_pk") String adPk, @Parameter(in = ParameterIn.PATH, description = "id", required = true, schema = @Schema()) @PathVariable("id") Integer id);

    @Operation(summary = "getALLAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads",
            produces = {"*/*"},
            method = RequestMethod.GET)
    ResponseEntity<ResponseWrapperAds> getALLAdsUsingGET();

    @Operation(summary = "getAdsComment", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = AdsComment.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comment/{id}",
            produces = {"*/*"},
            method = RequestMethod.GET)
    ResponseEntity<AdsComment> getAdsCommentUsingGET(@Parameter(in = ParameterIn.PATH, description = "ad_pk", required = true, schema = @Schema()) @PathVariable("ad_pk") String adPk, @Parameter(in = ParameterIn.PATH, description = "id", required = true, schema = @Schema()) @PathVariable("id") Integer id);

    @Operation(summary = "getAdsComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAdsComment.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comment",
            produces = {"*/*"},
            method = RequestMethod.GET)
    ResponseEntity<ResponseWrapperAdsComment> getAdsCommentsUsingGET(@Parameter(in = ParameterIn.PATH, description = "ad_pk", required = true, schema = @Schema()) @PathVariable("ad_pk") String adPk);

    @Operation(summary = "getAdsMe", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAds.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/me",
            produces = {"*/*"},
            method = RequestMethod.GET)
    ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "authenticated", required = false) Boolean authenticated, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "authorities[0].authority", required = false) String authorities0Authority, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "credentials", required = false) Object credentials, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "details", required = false) Object details, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "principal", required = false) Object principal);

    @Operation(summary = "getAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = FullAds.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{id}",
            produces = {"*/*"},
            method = RequestMethod.GET)
    ResponseEntity<FullAds> getAdsUsingGET(@Parameter(in = ParameterIn.PATH, description = "id", required = true, schema = @Schema()) @PathVariable("id") Integer id);

    @Operation(summary = "removeAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")})
    @RequestMapping(value = "/ads/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> removeAdsUsingDELETE(@Parameter(in = ParameterIn.PATH, description = "id", required = true, schema = @Schema()) @PathVariable("id") Integer id);

    @Operation(summary = "updateAdsComment", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = AdsComment.class))),
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")})
    @RequestMapping(value = "/ads/{ad_pk}/comment/{id}",
            produces = {"*/*"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    ResponseEntity<AdsComment> updateAdsCommentUsingPATCH(@Parameter(in = ParameterIn.PATH, description = "ad_pk", required = true, schema = @Schema()) @PathVariable("ad_pk") String adPk, @Parameter(in = ParameterIn.PATH, description = "id", required = true, schema = @Schema()) @PathVariable("id") Integer id, @Parameter(in = ParameterIn.DEFAULT, description = "comment", required = true, schema = @Schema()) @Valid @RequestBody AdsComment body);

    @Operation(summary = "updateAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = Ads.class))),
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")})
    @RequestMapping(value = "/ads/{id}",
            produces = {"*/*"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    ResponseEntity<Ads> updateAdsUsingPATCH(@Parameter(in = ParameterIn.PATH, description = "id", required = true, schema = @Schema()) @PathVariable("id") Integer id, @Parameter(in = ParameterIn.DEFAULT, description = "ads", required = true, schema = @Schema()) @Valid @RequestBody Ads body);

}
