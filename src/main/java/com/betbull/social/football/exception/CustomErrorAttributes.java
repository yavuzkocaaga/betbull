package com.betbull.social.football.exception;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
class CustomErrorAttributes extends DefaultErrorAttributes {

    //TODO Consider this when unit testing: https://stackoverflow.com/a/29123530/8405129

    public static final String HIBERNATE_DATA_EXCEPTION = "hibernate_data.exception";

    public static final String VALIDATION_DEFAULT_EXCEPTION = "validation.default.exception";

    private static final Integer STATUS_CODE_500_INTEGER = 500;

    private static final Integer STATUS_CODE_204_INTEGER = 204;

    private final MessageSource messageSource;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

        // Beware that exception maybe null. For example when it's 404 error.
        final Throwable exception = getError(webRequest);

        log.info("Processing exception", exception);


        final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        processForInfoLeakingExceptions(exception, errorAttributes, webRequest.getLocale());
        processForEnumFormatErrorMessage(exception, errorAttributes, webRequest.getLocale());
        processForDataExceptions(exception, errorAttributes);


        if (log.isDebugEnabled()) {
            log.debug("Handled error with response {}", errorAttributes);
        }
        return errorAttributes;
    }


    private void processForInfoLeakingExceptions(@Nullable final Throwable exception, final Map<String, Object> errorAttributes, Locale locale) {
        if (exception instanceof HttpMessageNotReadableException && !(exception.getCause() instanceof InvalidFormatException)) {
            errorAttributes.put("message", messageSource.getMessage("validation.required_body_missing", null, locale));
        } else if (exception instanceof HttpMessageNotReadableException && exception.getCause() instanceof InvalidFormatException) {
            final InvalidFormatException cause = (InvalidFormatException) exception.getCause();
            final List<JsonMappingException.Reference> pathList = cause.getPath();
            if (!pathList.isEmpty()) {
                final Object[] value = new Object[]{pathList.get(pathList.size() - 1).getFieldName(), cause.getTargetType()};
                final String message = messageSource.getMessage("validation.request_format_error", value, locale);
                log.error(exception.getMessage(), exception);
                errorAttributes.put("message", message);
            } else {
                final String message = messageSource.getMessage("app.exception.unknown_error", null, locale);
                log.error(exception.getMessage(), exception);
                errorAttributes.put("message", message);
            }
        } else if (STATUS_CODE_500_INTEGER.equals(errorAttributes.get("status"))) {
            // We override the message to avoid leaking any business or system information because it can be database exception, NPE etc.
            // and their exception message may include vulnerable info
            if (exception != null) {
                log.error(exception.getMessage(), exception);
            } else {
                log.error("An exception was occurred but this exception is null");
            }
            errorAttributes.put("message", messageSource.getMessage("app.exception.unknown_error", null, locale));

        }else  if (STATUS_CODE_204_INTEGER.equals(errorAttributes.get("status"))) {
            if (exception != null) {
                log.error(exception.getMessage(), exception);
            } else {
                log.error("An exception was occurred but this exception is null");
            }
            errorAttributes.put("message", messageSource.getMessage(exception.getMessage(), null, locale));

        }

    }

    private void processForEnumFormatErrorMessage(@Nullable final Throwable exception, final Map<String, Object> errorAttributes, Locale locale) {
        if (exception instanceof HttpMessageNotReadableException && exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) exception.getCause();
            if (invalidFormatException.getTargetType().isEnum()) {
                Object[] params = new Object[]{StringUtils.join(invalidFormatException.getTargetType().getEnumConstants(), ", ")};
                errorAttributes.put("message", messageSource.getMessage("validation.enum_format_error", params, locale));
            }
        } else if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            for (FieldError fieldError : bindException.getFieldErrors()) {
                final Class<?> clazz = bindException.getFieldType(fieldError.getField());
                if (clazz != null && clazz.isEnum()) {
                    Object[] params = new Object[]{StringUtils.join(clazz.getEnumConstants(), ", ")};
                    final String message = messageSource.getMessage("validation.enum_format_error", params, locale);
                    changeDefaultMessage(fieldError, message);
                }
            }
        }
    }

    private void processForDataExceptions(@Nullable final Throwable exception, final Map<String, Object> errorAttributes) {
        if (exception instanceof DataAccessException || exception instanceof HibernateException) {
            final String rootCauseMessage = ExceptionUtils.getRootCauseMessage(exception);
            errorAttributes.put("message", rootCauseMessage);
        }
    }




    private void changeDefaultMessage(FieldError fieldError, String message) {
        try {
            Field f = DefaultMessageSourceResolvable.class.getDeclaredField("defaultMessage");
            f.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);

            f.set(fieldError, message);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

}
