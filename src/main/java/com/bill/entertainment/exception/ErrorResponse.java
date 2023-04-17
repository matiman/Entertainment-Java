package com.bill.entertainment.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("status")
    private final int statusCode;

    @JsonProperty("message")
    private final String message;
}
