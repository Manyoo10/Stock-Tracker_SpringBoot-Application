# Stock Tracker (Spring Boot)

A small Spring Boot application that fetches stock data (quote, overview, and daily history)
from the Alpha Vantage API and exposes a simple REST API.

## Features
- Fetch latest quote for a stock symbol
- Fetch company overview for a stock symbol
- Fetch historical daily prices (TIME_SERIES_DAILY)

## Prerequisites
- Java 21 (project `java.version` is 21)
- Maven (the project includes the Maven wrapper; no global install required)

## Project layout
- The Spring Boot app is located in `stock-tracker/stock-tracker`.
- Main application class: `stock-tracker/stock-tracker/src/main/java/com/stock_tracker/tracker/StockTrackerApplication.java`
- Controller: `stock-tracker/stock-tracker/src/main/java/com/stock_tracker/tracker/controller/StockController.java`
- Service: `stock-tracker/stock-tracker/src/main/java/com/stock_tracker/tracker/service/StockService.java`
- HTTP client: `stock-tracker/stock-tracker/src/main/java/com/stock_tracker/tracker/client/StockClient.java`

## Configuration
The application expects the following application properties (see `stock-tracker/stock-tracker/src/main/resources/application.properties`):

- `alpha.vantage.api.key` — your Alpha Vantage API key (required)
- `alpha.vantage.url` — base URL for Alpha Vantage (recommended value: `https://www.alphavantage.co/query`)

Note: `WebClientConfig` reads the property `alpha.vantage.url`. The provided `application.properties` currently contains `alpha.vantage.base.url`; update that key to `alpha.vantage.url` or change the config accordingly.

## Build and run (Windows)
Open a terminal in `stock-tracker/stock-tracker` and run:

```powershell
cd stock-tracker\stock-tracker
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Or run the packaged jar:

```powershell
java -jar target\stock-tracker-0.0.1-SNAPSHOT.jar
```

## REST API
Base path: `/api/v1/stocks`

- Get latest quote
	- `GET /api/v1/stocks/{symbol}`
	- Example: `GET /api/v1/stocks/IBM`

- Get company overview
	- `GET /api/v1/stocks/{symbol}/overview`
	- Example: `GET /api/v1/stocks/IBM/overview`

- Get historical daily prices
	- `GET /api/v1/stocks/{symbol}/history?days=30`
	- Query param `days` is optional (default: 30)

Example curl (replace `<API_HOST>` and `<SYMBOL>` as appropriate):

```bash
curl http://localhost:8080/api/v1/stocks/IBM
curl http://localhost:8080/api/v1/stocks/IBM/overview
curl "http://localhost:8080/api/v1/stocks/IBM/history?days=60"
```

## Notes & Known Issues
- The application uses the Alpha Vantage API. Obtain a (free) API key at https://www.alphavantage.co/support/#api-key and set `alpha.vantage.api.key` in `application.properties`.
- `WebClientConfig` currently expects the property name `alpha.vantage.url` — update `application.properties` to match.
- `StockService.getHistory` appears incomplete and may require finishing to return a list of `DailyStockResponse` objects.

## Tests
Run unit tests with:

```powershell
.\mvnw.cmd test
```

## Further reading
- See `stock-tracker/stock-tracker/HELP.md` for additional notes collected during project setup.

## License
Check the repository `LICENSE` file for licensing details.

