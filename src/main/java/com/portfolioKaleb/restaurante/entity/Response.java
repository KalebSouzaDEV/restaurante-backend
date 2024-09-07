package com.portfolioKaleb.restaurante.entity;

import lombok.Getter;

@Getter
public class Response<T> {
    private T data;
    private String message;

    public Response(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public boolean hasMessage(){
        return message!= null;
    }
}
