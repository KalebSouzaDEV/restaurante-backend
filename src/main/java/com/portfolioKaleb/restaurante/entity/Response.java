package com.portfolioKaleb.restaurante.entity;

import lombok.Getter;

@Getter
public class Response<T> {
    private T data;
    private String message;

    public Response(T data) {
        this.data = data;
    }
    public Response(String message) {
        this.message = message;
    }

    public boolean hasMessage(){
        return message!= null;
    }
}
