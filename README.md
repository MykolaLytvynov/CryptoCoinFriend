# CryptoCoinFriend

`CryptoCoinFriend` is a Spring Boot application that provides a GraphQL API to retrieve real-time cryptocurrency data from CoinGecko. The application allows users to query various details about cryptocurrencies, such as their current price, market cap, 24h price changes, all-time highs (ATH), and all-time lows (ATL).

## Features

- **GraphQL API** to fetch cryptocurrency data.
- Retrieves data from [CoinGecko API](https://www.coingecko.com/).
- Caching of data to optimize performance using simple in-memory caching.
- Regular cache eviction every hour to ensure data remains up-to-date.
- Supports querying for different currencies (e.g., USD, EUR).

## Usage

Once the application is running, you can access the GraphiQL interface at:

```
http://localhost:8080/graphiql
```

### GraphQL Queries

You can query the API using the following GraphQL schema:

```graphql
type Query {
  getCryptoCoins(vsCurrency: String!): [CryptoCoin]
}

type CryptoCoin {
  name: String
  symbol: String
  lastUpdated: String
  currentPrice: Float
  image: String
  marketCap: Float
  totalVolume: Float
  high24h: Float
  low24h: Float
  priceChange24h: Float
  priceChangePercentage24h: Float
  ath: Float
  athChangePercentage: Float
  athDate: String
  atl: Float
  atlChangePercentage: Float
  atlDate: String
  circulatingSupply: Float
}
```

#### Example Query:

```graphql
query {
  getCryptoCoins(vsCurrency: "usd") {
    name
    symbol
    currentPrice
    lastUpdated
    atlChangePercentage
    image
    atlDate
    ath
  }
}
```


#### Example Response:

```json
{
  "data": {
    "getCryptoCoins": [
      {
        "name": "Bitcoin",
        "symbol": "btc",
        "currentPrice": 4427158,
        "lastUpdated": "2025-01-17T17:30:10.102",
        "atlChangePercentage": 797597.85448,
        "image": "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
        "atlDate": "2013-07-06T00:00",
        "ath": 4528597
      },
      {
        "name": "Ethereum",
        "symbol": "eth",
        "currentPrice": 144807,
        "lastUpdated": "2025-01-17T17:30:12.852",
        "atlChangePercentage": 1512681.23432,
        "image": "https://coin-images.coingecko.com/coins/images/279/large/ethereum.png?1696501628",
        "atlDate": "2015-10-20T00:00",
        "ath": 170407
      }
    ]
  }
}
```

## Dependencies

- **Spring Boot Web**: For building the REST components of the application.
- **Spring Boot Starter GraphQL**: To provide GraphQL capabilities.
- **Lombok**: For boilerplate code reduction.

## Author

Mykola Lytvynov
