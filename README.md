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

### Required API Configuration
- `alpha.vantage.api.key` — your Alpha Vantage API key (required)
- `alpha.vantage.base.url` — base URL for Alpha Vantage (default: `https://www.alphavantage.co/query?`)

### Database Configuration (H2)
- `spring.datasource.url` — H2 database file location (default: `jdbc:h2:file:../data/stockdb`)
- `spring.jpa.hibernate.ddl-auto` — set to `update` for automatic schema creation
- `spring.h2.console.enable` — set to `true` to enable H2 web console
- `spring.h2.console.path` — H2 console path (default: `/h2-console`)

### Caching
- `spring.cache.type` — set to `simple` for in-memory caching

The application uses **H2 database** for persisting favorite stocks. The database file is stored at `../data/stockdb` (relative to the `stock-tracker/stock-tracker` folder).

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

### Stock Data Endpoints
- **Get latest quote**
  - `GET /api/v1/stocks/{symbol}`
  - Example: `GET /api/v1/stocks/IBM`
  - Returns: Current price, last trading day, and symbol
  - Note: Results are cached for improved performance

- **Get company overview**
  - `GET /api/v1/stocks/{symbol}/overview`
  - Example: `GET /api/v1/stocks/IBM/overview`
  - Returns: Company information including industry, market cap, P/E ratio, etc.

- **Get historical daily prices**
  - `GET /api/v1/stocks/{symbol}/history?days=30`
  - Query param `days` is optional (default: 30)
  - Example: `GET /api/v1/stocks/IBM/history?days=60`
  - Returns: Daily OHLCV data (Open, High, Low, Close, Volume) for the specified number of days

### Favorites Management Endpoints
- **Add a stock to favorites**
  - `POST /api/v1/stocks/favorites`
  - Request body: `{"symbol": "IBM"}`
  - Returns: The saved favorite stock entry
  - Note: Attempting to add a duplicate favorite will throw a `FavoriteAlreadyExistsException`

- **Get all favorites with live prices**
  - `GET /api/v1/stocks/favorites`
  - Returns: List of all favorite stocks with current prices
  - Note: Uses cached price data for performance

### Example REST Calls

```bash
# Get latest quote
curl http://localhost:8080/api/v1/stocks/IBM

# Get company overview
curl http://localhost:8080/api/v1/stocks/IBM/overview

# Get historical daily prices (last 60 days)
curl "http://localhost:8080/api/v1/stocks/IBM/history?days=60"

# Add a stock to favorites
curl -X POST http://localhost:8080/api/v1/stocks/favorites \
  -H "Content-Type: application/json" \
  -d '{"symbol": "AAPL"}'

# Get all favorite stocks with live prices
curl http://localhost:8080/api/v1/stocks/favorites
```

## Database
The application uses **H2 database** to persist favorite stocks:
- Database is stored at `../data/stockdb` (file-based)
- Automatic schema creation is enabled via `spring.jpa.hibernate.ddl-auto=update`
- The `favorite_stocks` table stores symbol and ID
- Access the H2 web console at `http://localhost:8080/h2-console` (when enabled)

## Caching
- Stock quotes are cached in-memory to reduce API calls to Alpha Vantage
- Cache type: `simple` (Spring's built-in in-memory cache)
- Cached data improves response time for repeated requests

## Notes
- The application uses the Alpha Vantage API. Obtain a (free) API key at https://www.alphavantage.co/support/#api-key and set `alpha.vantage.api.key` in `application.properties`.
- Favorite stocks are stored in a persistent H2 database.
- Attempting to add a duplicate favorite stock will throw a `FavoriteAlreadyExistsException`.

## Tests
Run unit tests with:

```powershell
.\mvnw.cmd test
```

## Further reading
- See `stock-tracker/stock-tracker/HELP.md` for additional notes collected during project setup.

## License
Check the repository `LICENSE` file for licensing details.

