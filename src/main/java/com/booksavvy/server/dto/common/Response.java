package com.booksavvy.server.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String type;
    private String message;
    private String status;
    private Object data;

    public Response(String type, String message, String status, Object data) {
        this.type = type;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public Response(String type, String message, String status) {
        this(type, message, status, null);
    }
}
