package com.estudo.api.services.exceptions;

import com.estudo.api.commons.Translator;

import java.text.MessageFormat;
import java.util.List;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8823602357345693346L;

	public ObjectNotFoundException(String msg){
        super(Translator.toLocale(msg.replaceAll("[{}]","")));
    }

    public ObjectNotFoundException(String msg, String[] args){

        super(MessageFormat.format(Translator.toLocale(msg.replaceAll("[{}]", "")), args));

    }

    public ObjectNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
