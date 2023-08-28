package com.example.demo.CurrencyController;

import com.example.demo.DTOs.ConversionDto;
import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/currency")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@EnableCaching
public class CurrencyController{

    private final CurrencyService currencyService;

    @GetMapping("/convert")
    //@Cacheable("conversion")
    public ResponseEntity<ConversionDto> convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam Double amount) {
        try {
            String apiUrl = "https://v6.exchangerate-api.com/v6/d84fc03fb84a9b82e7d3736d/pair/"+fromCurrency+"/" + toCurrency + "/" + amount;
            double conversion = currencyService.getConversion(apiUrl);
            return ResponseEntity.ok(new ConversionDto(conversion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/compare")
    //@Cacheable("comparison")
    public LatestDto compareWithBase(
            @RequestParam String base
    ) {
        try {
            String apiUrl = "https://v6.exchangerate-api.com/v6/d84fc03fb84a9b82e7d3736d/latest/" + base;
            return currencyService.getComparisonRates(apiUrl);
        } catch (Exception e) {
            return new LatestDto(); // Handle the error and return an appropriate response
        }
    }
    @GetMapping("/compareWithTwo")
    //@Cacheable("comparison")
    public @ResponseBody Map<String, String> compareWithBaseAndTwoCurrencies(
            @RequestParam String base, @RequestParam String countryOne, @RequestParam String countryTwo
    ) {
        try {
            String apiUrl = "https://v6.exchangerate-api.com/v6/d84fc03fb84a9b82e7d3736d/latest/" + base;
            return currencyService.getComparisonRatesWithTwoCurrencies(apiUrl,countryOne,countryTwo);
        } catch (Exception e) {
            return Collections.emptyMap(); // Handle the error and return an appropriate response
        }
    }

    @GetMapping("/")
    //@Cacheable("images")
    public List<ImageDto> retrieveImages(){
        try {
            return currencyService.getAllCurrencyImages();
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/image")
    //@Cacheable("image")
    public Object retrieveOneImage(@RequestParam String countryCode){
        try {
            return currencyService.getImage(countryCode);
        } catch (Exception e) {
            return null;
        }
    }

//    @CacheEvict(allEntries = true, value = {"conversion", "comparison", "images", "image"})
//    @PostMapping("/clear-cache")
//    public String clearCache() {
//        return "Cache cleared.";
//    }
}
