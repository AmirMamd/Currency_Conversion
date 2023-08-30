package com.example.demo.Service;
import com.example.demo.DTOs.ConversionDto;
import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Enums.Currencies;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public double getConversion(String apiUrl) {
        // Call the API using RestTemplate and retrieve the response
        try {
            ConversionDto response = restTemplate.getForObject(apiUrl, ConversionDto.class);
            assert response != null;
            return response.getConversionResult();
        }catch(Exception e){
            logger.error("Error occurred while fetching conversion: {}", e.getMessage());
            return 0.0;
        }
    }

    public Map<String, String> getComparisonRatesWithTwoCurrencies(String apiUrl, String countryOne, String countryTwo) {
        try {
            LatestDto latestDto = restTemplate.getForObject(apiUrl, LatestDto.class);

            assert latestDto != null;
            Map<String, String> conversionRates = latestDto.getConversionRates();

            Map<String, String> selectedRates = new HashMap<>();
            selectedRates.put(countryOne, conversionRates.get(countryOne));
            selectedRates.put(countryTwo, conversionRates.get(countryTwo));

            return selectedRates;
        } catch (Exception e) {
            logger.error("Error occurred while fetching comparison rates: {}", e.getMessage());
            return Collections.emptyMap(); // Return an empty map in case of an error
        }
    }

    public LatestDto getComparisonRates(String apiUrl) {
        try {
            return restTemplate.getForObject(apiUrl, LatestDto.class);
        }catch(Exception e){
            logger.error("Error occurred while fetching comparison rates: {}", e.getMessage());
            return new LatestDto();
        }
    }

    public List<ImageDto> getAllCurrencyImages() {
        List<ImageDto> currencyImages = new ArrayList<>();

        for (Currencies currencyEnum : Currencies.values()) {
            currencyImages.add(new ImageDto(currencyEnum.getCurrency(), currencyEnum.getFlagUrl()));
        }

        return currencyImages;
    }

    public Object getImage(String countryCode){
        for (Currencies currencyEnum : Currencies.values()) {
            if (currencyEnum.getCurrency().equalsIgnoreCase(countryCode)) {
                return new ImageDto(currencyEnum.getCurrency(), currencyEnum.getFlagUrl());
            }
        }
        return "Currency not found";

    }


}

