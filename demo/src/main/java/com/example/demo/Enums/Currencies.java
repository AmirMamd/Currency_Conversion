package com.example.demo.Enums;

import lombok.Getter;

@Getter
public enum Currencies {
    USD("USD", "https://www.countryflagicons.com/FLAT/64/US.png"),
    EUR("EUR", "https://www.countryflagicons.com/FLAT/64/EU.png"),
    GBP("GBP", "https://www.countryflagicons.com/FLAT/64/GB.png"),
    JPY("JPY", "https://www.countryflagicons.com/FLAT/64/JP.png"),
    SAR("SAR", "https://www.countryflagicons.com/FLAT/64/SA.png"),
    AED("AED", "https://www.countryflagicons.com/FLAT/64/AE.png"),
    KWD("KWD", "https://www.countryflagicons.com/FLAT/64/KW.png"),
    BHD("BHD", "https://www.countryflagicons.com/FLAT/64/BH.png"),
    OMR("OMR", "https://www.countryflagicons.com/FLAT/64/OM.png"),
    QAR("QAR", "https://www.countryflagicons.com/FLAT/64/QA.png"),
    EGP("EGP","https://www.countryflagicons.com/FLAT/64/EG.png");

    private final String currency;
    private final String flagUrl;

    Currencies(String currency, String flagUrl) {
        this.currency = currency;
        this.flagUrl = flagUrl;
    }
}