package com.drdroid.sandbox.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private String orderId;
    private String city;
    private Integer merchantId;
    private String pickup;
    private String drop;
    private Double orderValue;

}
