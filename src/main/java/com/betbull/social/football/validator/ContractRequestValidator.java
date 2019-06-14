package com.betbull.social.football.validator;

import com.betbull.social.football.dto.ContractRequest;
import com.betbull.social.football.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
@AllArgsConstructor
public class ContractRequestValidator implements Validator {

    private static final String DATE_START_VALID_ERROR = "validation.request.contract.start.date.invalid";
    private static final String DATE_END_VALID_ERROR = "validation.request.contract.end.date.invalid";


    private MessageSource messageSource;


    @Override
    public boolean supports(Class<?> clazz) {
        return ContractRequest.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object o, Errors errors) {
        ContractRequest request = (ContractRequest) o;
        if (request.getContractEndDate().isBefore(request.getContractStartDate()) || request.getContractEndDate().equals(request.getContractStartDate())) {
            errors.rejectValue("contractEndDate", DATE_END_VALID_ERROR,
                    messageSource.getMessage(DATE_END_VALID_ERROR, null, Locale.getDefault()));
        }

        if (request.getContractStartDate().isAfter(request.getContractEndDate()) || request.getContractStartDate().equals(request.getContractEndDate())) {
            errors.rejectValue("contractStartDate", DATE_START_VALID_ERROR,
                    messageSource.getMessage(DATE_START_VALID_ERROR, null, Locale.getDefault()));
        }
    }
}
