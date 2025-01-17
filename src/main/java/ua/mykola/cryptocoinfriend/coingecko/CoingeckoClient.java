package ua.mykola.cryptocoinfriend.coingecko;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ua.mykola.cryptocoinfriend.model.CryptoCoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoingeckoClient {

    @Value("${coingecko.api.currencies}")
    String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final DateTimeFormatter dateTimeFormatter;

    public CoingeckoClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    }

    @Cacheable(value = "cryptoCoins", key = "#vsCurrency")
    public List<CryptoCoin> getCryptoCoins(String vsCurrency) {
        List<CryptoCoin> result = new ArrayList<>();

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("vs_currency", vsCurrency)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            for (JsonNode json : root) {
                result.add(toCryptoCoin(json));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response from Coingecko API: ", e);
        }

        return result;
    }

    @Scheduled(fixedRate = 3600000)
    @CacheEvict(value = "cryptoCoins", allEntries = true)
    public void evictCache() {
        // This method clears the cache for all cryptoCoins
    }

    private CryptoCoin toCryptoCoin(JsonNode json) {
        return CryptoCoin.builder()
                .name(json.get("name").asText())
                .symbol(json.get("symbol").asText())
                .lastUpdated(LocalDateTime.parse(json.get("last_updated").asText(), dateTimeFormatter))
                .currentPrice(BigDecimal.valueOf(json.get("current_price").asDouble()))
                .image(json.get("image").asText())
                .marketCap(BigDecimal.valueOf(json.get("market_cap").asDouble()))
                .totalVolume(BigDecimal.valueOf(json.get("total_volume").asDouble()))
                .high24h(BigDecimal.valueOf(json.get("high_24h").asDouble()))
                .low24h(BigDecimal.valueOf(json.get("low_24h").asDouble()))
                .priceChange24h(BigDecimal.valueOf(json.get("price_change_24h").asDouble()))
                .priceChangePercentage24h(BigDecimal.valueOf(json.get("price_change_percentage_24h").asDouble()))
                .ath(BigDecimal.valueOf(json.get("ath").asDouble()))
                .athChangePercentage(BigDecimal.valueOf(json.get("ath_change_percentage").asDouble()))
                .athDate(LocalDateTime.parse(json.get("ath_date").asText(), dateTimeFormatter))
                .atl(BigDecimal.valueOf(json.get("atl").asDouble()))
                .atlChangePercentage(BigDecimal.valueOf(json.get("atl_change_percentage").asDouble()))
                .atlDate(LocalDateTime.parse(json.get("atl_date").asText(), dateTimeFormatter))
                .circulatingSupply(BigDecimal.valueOf(json.get("circulating_supply").asDouble()))
                .build();
    }
}
