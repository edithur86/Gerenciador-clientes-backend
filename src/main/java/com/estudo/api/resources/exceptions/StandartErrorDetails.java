package com.estudo.api.resources.exceptions;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StandartErrorDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private Long detailedMessage;

}
