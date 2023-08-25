package com.example.demo.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    @JsonProperty("currency_code")
    private String currency;
    @JsonProperty("image_url")
    private String url;
}
