package com.betbull.social.football.controller;

import com.betbull.social.football.dto.FootBallerCreateRequest;
import com.betbull.social.football.dto.FootBallerUpdateRequest;
import com.betbull.social.football.dto.FootBallerViewReponse;
import com.betbull.social.football.service.FootballerService;
import com.betbull.social.football.validator.FootballerRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;


@RestController
@RequestMapping(path = "/api/v1/footballer", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class FootballerController {

    private final FootballerService footballerService;

    private final FootballerRequestValidator footballerRequestValidator;

    @InitBinder("footBallerCreateRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(footballerRequestValidator);
    }


    @PostMapping(path = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FootBallerViewReponse> create(@Validated @RequestBody FootBallerCreateRequest footBallerCreateRequest) {

        return new ResponseEntity<>(footballerService.insertFootballer(footBallerCreateRequest), HttpStatus.OK);

    }

    @PostMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FootBallerViewReponse> update(@Validated @RequestBody FootBallerUpdateRequest footBallerUpdateRequest) {

        return new ResponseEntity<>(footballerService.updateFootballer(footBallerUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Boolean> delete(@Validated @NotNull @RequestParam Long footballerId) {

        return new ResponseEntity<>(footballerService.deleteFootballer(footballerId), HttpStatus.OK);

    }

    @GetMapping(path = "/findById")
    public ResponseEntity<FootBallerViewReponse> findByFootballerId(@Validated @NotNull @RequestParam Long footballerId) {

        return new ResponseEntity<>(footballerService.findById(footballerId), HttpStatus.OK);
    }


    @GetMapping(path = "/allFootballers")
    public ResponseEntity<Collection<FootBallerViewReponse>> getAllFootballers() {

        return new ResponseEntity<>(footballerService.getFootballers(), HttpStatus.OK);
    }


}
