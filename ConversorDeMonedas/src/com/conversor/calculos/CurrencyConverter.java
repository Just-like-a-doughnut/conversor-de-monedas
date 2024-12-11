package com.conversor.calculos;

import com.conversor.modelos.ExchangeRateService;

public class CurrencyConverter {
    private ExchangeRateService exchangeRateService;

    public CurrencyConverter() {
        this.exchangeRateService = new ExchangeRateService();
    }

    public double convert(String baseCurrency, String targetCurrency, double amount) {
        try {
            double rate = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);
            return amount * rate;
        } catch (Exception e) {
            System.err.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return 0.0;
        }
    }
}
