package com.example.demo.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDto {
    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("target_code")
    private String targetCode;

    private String amount;

    @JsonProperty("conversion_rate")
    private String conversionRate;

    @JsonProperty("conversion_value")
    private double conversionValue;


}
