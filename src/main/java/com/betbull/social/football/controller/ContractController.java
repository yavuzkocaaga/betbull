package com.betbull.social.football.controller;

import com.betbull.social.football.dto.ContractRequest;
import com.betbull.social.football.service.ContractService;
import com.betbull.social.football.validator.ContractRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/contract", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ContractController {

    private final ContractService contractService;

    private final ContractRequestValidator contractRequestValidator;

    @InitBinder("contractRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(contractRequestValidator);
    }


    @PostMapping(path = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> create(@Validated @RequestBody ContractRequest contractRequest) {

        return new ResponseEntity<>(contractService.createContract(contractRequest), HttpStatus.OK);

    }



}
