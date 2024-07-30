package com.daily.utils;

import com.daily.payloads.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.time.Instant.now;

/**
 * @author Majd Selmi
 */
@Component
public class ResponseUtils {
    private ResponseUtils() {}

    public static ResponseEntity<Response> handleResponse(String message, HttpStatus httpStatus, Map<?, ?> data) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .timestamp(now().toString())
                .data(data)
                .message(message)
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .build());
    }

    public static ResponseEntity<Response> handleException(Exception e, HttpStatus httpStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .timestamp(now().toString())
                .exception(e.getMessage())
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .build());
    }
}
