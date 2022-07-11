package com.estudo.api.commons;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiCollectionResponse {

    private Object items;
    private boolean hasNext = false;

}
