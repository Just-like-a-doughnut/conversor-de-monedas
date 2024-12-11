package com.conversor.modelos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExchangeRateService {
    private static final String BASE_URL = "https://api.exchangerate-api.com/v4/latest/";

    // Método para obtener la tasa de cambio entre dos monedas
    public double getExchangeRate(String baseCurrency, String targetCurrency) throws InvalidCurrencyException, Exception {
        if (!isValidCurrency(baseCurrency)) {
            throw new InvalidCurrencyException("La moneda base '" + baseCurrency + "' no es válida.");
        }
        if (!isValidCurrency(targetCurrency)) {
            throw new InvalidCurrencyException("La moneda objetivo '" + targetCurrency + "' no es válida.");
        }

        String urlStr = BASE_URL + baseCurrency;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Leer la respuesta
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parsear el JSON de respuesta usando Gson
        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject rates = jsonResponse.getAsJsonObject("rates");

        return rates.get(targetCurrency).getAsDouble();
    }

    // Método para validar si una moneda existe
    public boolean isValidCurrency(String currencyCode) throws Exception {
        String urlStr = BASE_URL + "USD"; // Usar una moneda base fija para obtener la lista de monedas
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Leer la respuesta
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parsear el JSON de respuesta usando Gson
        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject rates = jsonResponse.getAsJsonObject("rates");

        // Verificar si el código de moneda existe en las claves del objeto "rates"
        Set<String> currencyKeys = rates.keySet();
        return currencyKeys.contains(currencyCode);
    }
}

// Excepción para monedas no válidas o incorrectas
class InvalidCurrencyException extends Exception {
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
