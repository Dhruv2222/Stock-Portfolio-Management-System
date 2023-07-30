package model;

import java.time.LocalDate;

/**
 * This interface represents an API for retrieving the prices of a stock.
 */
interface StockMarketAPI {

  /**
   * Get the price of stock on a given date.
   *
   * @param date the date on which stock prices are required.
   * @return the price of a given stock on a given date.
   */
  double getPrice(String stockSymbol, LocalDate date);

}
