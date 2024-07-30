package com.daily.payloads.request;

import lombok.Getter;

/**
 * @author Majd Selmi
 */
@Getter
public class CheckoutRequest {
    private String status;
    private String key;
    private String address;
    private String notes;
}
