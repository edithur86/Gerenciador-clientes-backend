package com.estudo.api.resources.exceptions;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class StandartError implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;
    private String detailedMessage;
    private Long timeStamp;
    private List<StandartErrorDetails> detalhes;

}
