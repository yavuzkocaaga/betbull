package com.betbull.social.football.validator;

import com.betbull.social.football.dto.FootBallerCreateRequest;
import com.betbull.social.football.dto.TeamCreateRequest;
import com.betbull.social.football.dto.TeamUpdateRequest;
import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
@AllArgsConstructor
public class TeamUpdateRequestValidator implements Validator {

    private static final String COUNTRY_IS_NOT_VALID = "validation.request.country.invalid";

    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return TeamUpdateRequest.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object o, Errors errors) {

            TeamUpdateRequest request = (TeamUpdateRequest) o;
            if (CountryCode.getByCode(request.getCountryCode()) == null) {
                errors.rejectValue("countryCode", COUNTRY_IS_NOT_VALID,
                        messageSource.getMessage(COUNTRY_IS_NOT_VALID, null, Locale.getDefault()));
            }

    }
}
