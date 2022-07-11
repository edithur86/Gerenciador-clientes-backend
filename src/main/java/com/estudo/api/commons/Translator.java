package com.estudo.api.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode.replaceAll("[{}]", ""), null, locale);
    }

    public static String toLocale(String msgCode, String[] args) {
        String traduzido = toLocale(msgCode);
        return MessageFormat.format(traduzido, args);
    }

}
