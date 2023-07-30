package model;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface represents a Portfolio which offers various operations on the portfolio.
 */
public interface PortfolioModel {

  /**
   * Creates a new user object.
   *
   * @param firstName  first name entered by the user.
   * @param lastName   last name entered by the user.
   * @param middleName middle name entered by the user.
   * @param userID     userID entered by the user.
   * @param password   password entered by the user.
   * @param portfolioType type of portfolio.
   * @param commissionFee commission fee entered by user.
   */
  void createUser(String firstName, String lastName, String middleName, String userID,
                  String password, int portfolioType, double commissionFee);


  /**
   * Login for an existing user.
   *
   * @param userID   userID of the existing user.
   * @param password password of the existing user.
   * @param portfolioType type of portfolio.
   */
  void login(String userID, String password, int portfolioType);


  /**
   * Creates a new portfolio for a user.
   *
   * @param portfolioName portfolio name entered by the user.
   * @param currentUser   User object of the current user.
   * @param portfolioType type of portfolio.
   */
  void createPortfolio(String portfolioName, User currentUser, int portfolioType);

  /**
   * Add new stocks while building a new portfolio.
   *
   * @param key          the field to be appended.
   * @param value        the value to be appended in the field.
   * @param newPortfolio Portfolio object to be modified.
   */
  void addStockInPortfolio(String key, String value, Portfolio newPortfolio);

  /**
   * Method to save the portfolio object in file.
   *
   * @param newPortfolio the Portfolio object to be saved.
   * @param currentUser current user object.
   */
  void savePortfolio(Portfolio newPortfolio, User currentUser);


  /**
   * Gives the list of portfolios held by a given user.
   *
   * @param currentUser userID of the user whose list of portfolios is required.
   * @return a list of Portfolio objects.
   */
  List<String> showPortfolios(User currentUser);


  /**
   * Gives the total investment made by the user across all portfolios.
   *
   * @param newPortfolio portfolio object of which total value is required.
   * @param prices       list of prices of the corresponding stocks in the portfolio.
   * @return total value of the portfolio.
   */
  double showTotalInvestment(Portfolio newPortfolio, List<Double> prices);


  /**
   * Selects a portfolio from the list of portfolios given to the user.
   *
   * @param currentUser User object of the current user.
   * @param portfolioID portfolio ID of the selected portfolio.
   */
  void selectPortfolio(User currentUser, String portfolioID);


  /**
   * Upload an XML file to load the portfolio details and add it to the list of portfolios.
   * of the user and then display the composition of the portfolio uploaded.
   *
   * @param currentUser User object of the current user.
   * @param filepath    the location of the XML file to be loaded.
   */
  void uploadPortfolio(User currentUser, String filepath);

  /**
   * Gives a list of prices of the stocks in a portfolio on a given date.
   *
   * @param date         the date on which prices are required.
   * @param newPortfolio the portfolio of which prices are required.
   * @return a list of prices corresponding to the stockID index.
   */
  List<Double> getStockPricesOnDate(LocalDate date, Portfolio newPortfolio);

  /**
   * Sell the stock given stockID and quantity from the portfolio.
   *
   * @param newPortfolio portfolio object.
   * @param stockID      the stockID of the stock to be sold.
   * @param sellQty      the quantity of the stock to be sold.
   * @param date date for selling the stock.
   */
  void sellStock(Portfolio newPortfolio, String stockID, double sellQty, String date);

  /**
   * Get the total value of portfolio on a given date.
   *
   * @param date         the date on which stock is to be sold.
   * @param newPortfolio the portfolio object.
   * @return total value of the portfolio on a given date.
   */
  double getTotalValueOnDate(LocalDate date, Portfolio newPortfolio);

  /**
   * Get the total cost basis of the portfolio till a given date.
   *
   * @param date         the date on which cost basis is required.
   * @param newPortfolio the portfolio object.
   * @return cost basis value of the portfolio on a given date.
   */
  double getCostBasis(LocalDate date, Portfolio newPortfolio);

  /**
   * Shows the composition of an advanced portfolio.
   *
   * @param newPortfolio the portfolio object.
   * @param date date for selling the stock.
   */
  void showComposition(Portfolio newPortfolio, LocalDate date);

  /**
   * Get timestamps from the start date to the end date for graph analysis.
   *
   * @param fromDate starting date for timestamps.
   * @param toDate   end date for timestamps.
   * @return a list of timestamps.
   */
  List<String> getGraphTimestamp(String fromDate, String toDate);

  /**
   * Get star count for graph analysis corresponding to the value of the given portfolio on the
   * timestamps given.
   *
   * @param newPortfolio the portfolio object.
   * @param timestamp    the list of strings containing the timestamps for which values are
   *                     required.
   * @return the list of integers containing star counts.
   */
  List<Integer> getGraphStarCount(Portfolio newPortfolio, List<String> timestamp);

  /**
   * Gives the current user for a session.
   *
   * @return User object of the current user.
   */
  User getCurrentUser();

  /**
   * Gives the working portfolio created/selected by the user.
   *
   * @return Portfolio object of the working portfolio.
   */
  Portfolio getWorkingPortfolio();

  /**
   * Gives the consolidated portfolio created/selected by the user.
   *
   * @return Portfolio object of the consolidated portfolio.
   */
  Portfolio getConsolidatedPortfolio();

  /**
   * Buys the stocks in a portfolio according to the total amount to be spent and the given ratios
   * for each stock.
   * @param amount the total amount to be invested.
   * @param date the date on which investment is to be made.
   */
  void investFixedAmount(String amount, String date);

  /**
   * Method which offers creating start-to-finish dollar-cost averaging as a single operation.
   * @param amount the total amount that the user wants to invest.
   * @param intervalNum the numeric value of the interval for the dollar-cost average strategy.
   * @param intervalDuration the interval (month, year, day) for the dollar-cost average strategy.
   * @param startDate the start date of the strategy.
   * @param endDate the end date of the strategy.
   * @param ongoing to check if the strategy is an ongoing strategy.
   */
  void startToEndInvest(String amount, String intervalNum, String intervalDuration,
                        String startDate, String endDate, boolean ongoing);

  /**
   * This method gets the scale of the graph in the form of days, months, years.
   * @param newPortfolio Portfolio object to be modified.
   * @param timestamp the list of strings containing the timestamps for which values are
   *                  required.
   * @return a list of integers.
   */
  List<Integer> getGraphHeight(Portfolio newPortfolio, List<String> timestamp);

  /**
   * This method gets the graph intervals.
   * @return a double value for the increment.
   */
  double getGraphIncrement();

  /**
   * This method collects the tickers and ratios and validates the existence of a ticker.
   * @param ticker the stock ticker of the stock.
   * @param ratio the percentage assigned by the user to each stock.
   */
  void collectTickerRatios(String ticker, String ratio);

  /**
   * This method collects the tickers and stock names and validates the existence of a ticker.
   * @param ticker the stock ticker of the stock.
   * @param stockName the name of the stock.
   */
  void collectTickerNames(String ticker, String stockName);

  /**
   * This method initializes the fields tickerName and tickerRatio.
   */
  void startNewStrategy();

  /**
   * This method validates the date input (format check and date existence check).
   * @param date date as a string.
   * @return date object of class LocalDate.
   */
  LocalDate validateDateInput(String date);

}