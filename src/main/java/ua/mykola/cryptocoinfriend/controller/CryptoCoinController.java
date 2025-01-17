package ua.mykola.cryptocoinfriend.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ua.mykola.cryptocoinfriend.coingecko.CoingeckoClient;
import ua.mykola.cryptocoinfriend.model.CryptoCoin;

import java.util.List;

@Controller
public class CryptoCoinController {
    private final CoingeckoClient coingeckoClient;

    public CryptoCoinController(CoingeckoClient coingeckoClient) {
        this.coingeckoClient = coingeckoClient;
    }

    @QueryMapping
    public List<CryptoCoin> getCryptoCoins(@Argument String vsCurrency) {
        return coingeckoClient.getCryptoCoins(vsCurrency);
    };
}
