package model;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface represents the methods supported by a Portfolio in a
 * Portfolio Management application.
 */
public interface Portfolio {

  /**
   * Getter method to get the name of the portfolio.
   *
   * @return portfolio name of string data type.
   */
  String getPortfolioName();

  /**
   * Getter method to get the stockID i.e., a ticker.
   *
   * @return the stockID.
   */
  List<String> getStockID();

  /**
   * Getter method to get the name of the stock.
   *
   * @return the stock name.
   */
  List<String> getStockName();

  /**
   * Getter method to get the quantity of the stock.
   *
   * @return the stock quantity.
   */
  List<String> getStockQuantity();

  /**
   * Setter method to set the stockID.
   *
   * @param stockID of string data type.
   */
  void setStockID(String stockID);

  /**
   * Setter method to set the stockName.
   *
   * @param stockName of string datatype.
   */
  void setStockName(String stockName);

  /**
   * Setter method to set the stockQuantity.
   *
   * @param stockQuantity of String datatype.
   */
  void setStockQuantity(String stockQuantity);

  /**
   * This method saves the portfolio created by the user.
   *
   * @param currentUser the user who is currently using the application.
   */
  void savePortfolio(User currentUser);


  /**
   * Method to get the date of the transaction.
   *
   * @return the list of date.
   */
  List<LocalDate> getTransactionDate();

  /**
   * Method to get the buying price of the stock.
   *
   * @return the list of buying price.
   */
  List<Double> getBuyPrice();

  /**
   * Method to get the value of stock.
   *
   * @return the list of stock value.
   */
  List<Double> getStockValue();

  /**
   * Setter method to set the transaction date.
   *
   * @param date of the transaction of type LocalDate.
   */
  void setTransactionDate(LocalDate date);

  /**
   * Setter method to set the buying price of the stock.
   *
   * @param buyPrice of the stock of type double.
   */
  void setBuyPrice(double buyPrice);

  /**
   * Setter method to set the value of stock.
   *
   * @param stockValue of type double.
   */
  void setStockValue(double stockValue);

  /**
   * Method to sell specific number of shares of a specific stock.
   *
   * @param stockID stock ticker of the stock.
   * @param sellQty the quantity of stocks that need to be sold.
   * @param date    the date for selling the stocks.
   */
  void sellStock(String stockID, double sellQty, String date);

  /**
   * Method to add specific number of share of a specific stock.
   *
   * @param key   for condition checking.
   * @param value for condition checking.
   */
  void addStock(String key, String value);


}