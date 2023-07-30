package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class APIAlphaVantage implements StockMarketAPI {


  private final List<List<String>> fin;

  private Map<String, Map<String, String>> store;

  public APIAlphaVantage() {
    store = new HashMap<>();
    fin = new ArrayList<>();
  }

  @Override
  public double getPrice(String stockSymbol, LocalDate date) throws IllegalArgumentException {

    if (date.isAfter(LocalDate.now().minusDays(1))) {
      throw new IllegalArgumentException("Enter past date.");
    }

    // check if stock ticker already in storage
    if (this.store.containsKey(stockSymbol)) {
      return getPriceFromStorage(stockSymbol, date);
    }

    // since it is a new stock, store data in map.
    URL url;
    try {

      String apiKey = "DRY4TVAZVYOBCZY3";
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    String data = output.toString();

    String[] dataSplit = data.split("\n");

    List<String> s = new ArrayList<>();
    Collections.addAll(s, dataSplit);


    for (String j : s) {
      String[] temp = j.split(",");
      List<String> tempList = new ArrayList<>();
      Collections.addAll(tempList, temp);
      fin.add(tempList);
    }

    Map<String, String> temp = new HashMap<>();
    for (List<String> d : fin) {
      temp.put(d.get(0), d.get(4));
    }
    store.put(stockSymbol, temp);

    // after storing data check again.
    return getPrice(stockSymbol, date);

  }

  double getPriceFromStorage(String stockSymbol, LocalDate date) {
    if (!store.containsKey(stockSymbol)) {
      throw new IllegalArgumentException("Stock not in storage");
    }
    if (store.get(stockSymbol).containsKey(date.toString())) {
      return Double.parseDouble(store.get(stockSymbol).get(date.toString()));
    }
    return getPriceFromStorage(stockSymbol, date.plusDays(1));
  }

}
