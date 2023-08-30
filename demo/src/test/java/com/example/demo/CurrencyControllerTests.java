package com.example.demo;

import com.example.demo.CurrencyController.CurrencyController;
import com.example.demo.DTOs.ConversionDto;
import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    public void testConvertCurrency() throws Exception {
        ConversionDto conversionDto= new ConversionDto();
        Mockito.when(currencyService.getConversion(Mockito.anyString()))
                .thenReturn(conversionDto.getConversionResult());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/currency/convert")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "EUR")
                        .param("amount", "25.0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        ConversionDto responseDto = new ObjectMapper().readValue(responseContent, ConversionDto.class);
        assertEquals(conversionDto, responseDto);
    }

    @Test
    public void testCompareWithBase() throws Exception {
        LatestDto expectedDto = new LatestDto(); // Initialize with appropriate values

        Mockito.when(currencyService.getComparisonRates(Mockito.anyString()))
                .thenReturn(expectedDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/currency/compare")
                        .param("base", "USD")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        LatestDto responseDto = new ObjectMapper().readValue(responseContent, LatestDto.class);

        assertEquals(expectedDto, responseDto);
    }

    @Test
    public void testRetrieveImages() throws Exception {
        List<ImageDto> expectedImages = new ArrayList<>(); // You can initialize it with sample data
        Mockito.when(currencyService.getAllCurrencyImages())
                .thenReturn(expectedImages);

        mockMvc.perform(MockMvcRequestBuilders.get("/currency/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testRetrieveOneImage() throws Exception {
        String expectedCurrency = "OMR";
        String expectedFlagUrl = "https://www.countryflagicons.com/FLAT/64/OM.png";

        ImageDto expectedDto = new ImageDto(expectedCurrency, expectedFlagUrl);

        Mockito.when(currencyService.getImage(Mockito.any()))
                .thenReturn(expectedDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/currency/image")
                        .param("countryCode", "OMR")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ImageDto responseDto = new ObjectMapper().readValue(responseContent, ImageDto.class);

        assertEquals(expectedCurrency, responseDto.getCurrency());
        assertEquals(expectedFlagUrl, responseDto.getUrl());
    }
}

