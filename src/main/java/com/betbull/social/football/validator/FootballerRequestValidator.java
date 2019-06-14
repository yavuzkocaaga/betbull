package com.betbull.social.football.validator;

import com.betbull.social.football.dto.FootBallerCreateRequest;
import com.betbull.social.football.service.TeamService;
import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
@AllArgsConstructor
public class FootballerRequestValidator implements Validator {

    private static final String FOOTBALLER_COUNTRY_IS_NOT_VALID = "validation.request.country.invalid";


    private MessageSource messageSource;

    private TeamService teamService;

    @Override
    public boolean supports(Class<?> clazz) {
        return FootBallerCreateRequest.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object o, Errors errors) {
        FootBallerCreateRequest request = (FootBallerCreateRequest) o;
        if (CountryCode.getByCode(request.getNationality()) == null) {
            errors.rejectValue("nationality", FOOTBALLER_COUNTRY_IS_NOT_VALID,
                    messageSource.getMessage(FOOTBALLER_COUNTRY_IS_NOT_VALID, null, Locale.getDefault()));
        }

    }
}
