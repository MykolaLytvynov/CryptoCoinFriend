package ua.mykola.cryptocoinfriend.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CryptoCoin {
    private String name;
    private String symbol;
    private LocalDateTime lastUpdated;
    private BigDecimal currentPrice;
    private String image;
    private BigDecimal marketCap;
    private BigDecimal totalVolume;
    private BigDecimal high24h;
    private BigDecimal low24h;
    private BigDecimal priceChange24h;
    private BigDecimal priceChangePercentage24h;
    private BigDecimal ath;
    private BigDecimal athChangePercentage;
    private LocalDateTime athDate;
    private BigDecimal atl;
    private BigDecimal atlChangePercentage;
    private LocalDateTime atlDate;
    private BigDecimal circulatingSupply;
}
