package controller;

/**
 * This interface represents all the methods required to support the functionalities of
 * inflexible and flexible portfolios for the Graphical User Interface.
 */
public interface IControllerGUI {

  /**
   * To get the type of user based on the type of portfolio selected.
   */
  void decideUserType();

  /**
   * Creates a new user object.
   * @param firstName first name of the user.
   * @param lastName last name of the user.
   * @param middleName middle initial of the user.
   * @param userID userID of the user.
   * @param password password of the user.
   * @param commissionFee commission fee entered by the user.
   */
  void createUser(String firstName, String lastName, String middleName, String userID,
                  String password, String commissionFee);

  /**
   * Login for an existing user.
   *
   * @param username   userID of the existing user.
   * @param password password of the existing user.
   */
  void login(String username, String password);

  /**
   * Creates a new portfolio for a user.
   *
   * @param portfolioName portfolio name entered by the user.
   */
  void createPortfolio(String portfolioName);

  /**
   * Add new stocks while building a new portfolio.
   *
   * @param stockID the stock ticker of a stock.
   * @param stockName the name of the stock.
   * @param stockQty the quantity of a particular stock.
   * @param transactionDate the date of transaction.
   */
  void addNewStockInPortfolio(String stockID, String stockName, String stockQty,
                              String transactionDate);

  /**
   * Method to save the portfolio object in file.
   * @param stockID the stock ticker of a stock that needs to be saved.
   * @param stockName the name of the stock that needs to be saved.
   * @param stockQty the quantity of a particular stock that needs to be saved.
   * @param transactionDate the date of transaction that needs to be saved.
   */
  void savePortfolio(String stockID, String stockName, String stockQty, String transactionDate);

  /**
   * Show portfolios held by a given user.
   *
   * @param type displaying list of portfolios or selecting from a list of portfolios.
   */
  void showPortfolios(String type);

  /**
   * Selects a portfolio from the list of portfolios given to the user.
   * @param portfolioID the portfolioID of the selected portfolio.
   * @param displayDate the date for which the prices are to be displayed.
   */
  void selectPortfolio(String portfolioID, boolean displayDate);

  /**
   * To get prices for either yesterday or a custom date entered by the user.
   * @param yest yesterday's date to get prices.
   * @param date the date on which prices are required.
   */
  void showPricesForADate(boolean yest, String date);

  /**
   * Upload an XML file to load the portfolio details and add it to the list of portfolios.
   * of the user and then display the composition of the portfolio uploaded.
   *
   * @param filepath the location of the XML file to be loaded.
   */
  void uploadPortfolio(String filepath);

  /**
   * Method to sell a stock.
   * @param stockID the stock ticker of the stock that needs to be sold.
   * @param quantity the quantity of the stock that needs to be sold.
   * @param date the date when the stock needs to be sold.
   */
  void sellStock(String stockID, String quantity, String date);

  /**
   * To get the value of a portfolio on a certain date.
   * @param date the date on which the value needs to be retrieved.
   */
  void getValueOnDate(String date);

  /**
   * Get the total cost basis of the portfolio till a given date.
   *
   * @param date the date on which cost basis is required.
   */
  void getCostBasis(String date);

  /**
   * Shows the composition of an advanced portfolio.
   *
   * @param date the date on which composition is required.
   */
  void showComposition(String date);

  /**
   * To get the type of portfolio selected by the user.
   * @param portfolioType observing portfolio or trading portfolio.
   */
  void selectedPortfolioType(int portfolioType);

  /**
   * Different features supported by the flexible portfolio.
   * @param option different options available to the user to select from.
   */
  void flexiblePortfolioFeatures(String option);

  /**
   * Method to save flexible portfolio.
   */
  void saveFlexPortfolio();

  /**
   * Method to add multiple stocks in the strategy.
   * @param ticker the stock ticker of the stock.
   * @param stockName the name of the stock.
   * @param ratio the percentage assigned by the user to each stock.
   * @param initial to check whether the stock entered was the initial stock.
   * @param invStr to find if the strategy is the fixed amount strategy or
   *               dollar-cost averaging strategy.
   */
  void addMoreStocksInStrategy(String ticker, String stockName, String ratio, boolean initial,
                               int invStr);

  /**
   * Method to invest a fixed amount into an existing portfolio.
   * @param ticker the stock ticker of the stock.
   * @param stockName the name of the stock.
   * @param ratio the percentage assigned by the user to each stock.
   * @param initial to check whether the stock entered was the initial stock.
   * @param amount the total amount that the user wants to invest.
   * @param buyingDate the date when the user wants to buy the stocks.
   */
  void investFixedAmount(String ticker, String stockName, String ratio, boolean initial,
                         String amount, String buyingDate);

  /**
   * Method to create dollar-cost averaging.
   * @param ticker the stock ticker of the stock.
   * @param stockName the name of the stock.
   * @param ratio the percentage assigned by the user to each stock.
   * @param amount the total amount that the user wants to invest.
   * @param intervalNum the numeric value of the interval for the dollar-cost average strategy.
   * @param intervalDuration the interval (month, year, day) for the dollar-cost average strategy.
   * @param startDate the start date of the strategy.
   * @param endDate the end date of the strategy.
   * @param ongoing to check if the strategy is an ongoing strategy.
   * @param initial to check whether the stock entered was the initial stock.
   */
  void dollarCostAvg(String ticker, String stockName, String ratio, String amount,
                     String intervalNum, String intervalDuration,
                     String startDate, String endDate, boolean ongoing, boolean initial);

  /**
   * Method to plot the line chart for analyzing the performance of a portfolio
   * over a period of time.
   * @param startDate start date of the time range when the performance of the portfolio is to be
   *                  analyzed.
   * @param endDate end date of the time range when the performance of the portfolio is to be
   *                analyzed.
   */
  void plotLineGraph(String startDate, String endDate);

  /**
   * Method to plot the bar chart for analyzing the performance of a portfolio
   * over a period of time.
   * @param startDate start date of the time range when the performance of the portfolio is to be
   *                  analyzed.
   * @param endDate end date of the time range when the performance of the portfolio is to be
   *                analyzed.
   */
  void plotBarGraph(String startDate, String endDate);

}
