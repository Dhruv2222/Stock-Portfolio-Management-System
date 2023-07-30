package view;

import java.util.List;

import model.Portfolio;

/**
 * This interface represents the view section of the Portfolio Management system.
 * It represents all the methods required to display outputs and results to the user.
 */
public interface PortfolioView {

  /**
   * Displays the start-up menu.
   */
  void startUpMenu();

  /**
   * Displays a welcome message after user logs in.
   * @param firstName first name of user.
   */
  void welcomeUser(String firstName);

  /**
   * Shows a success message after a new portfolio is created.
   */
  void portfolioCreatedMessage();

  /**
   * Show portfolio menu after new portfolio is created.
   */
  void createPortfolioMenu();

  /**
   * Show message after portfolio is saved in the XML file.
   */
  void portfolioSavedMessage();

  /**
   * Show enter stock ticker message.
   */
  void portfolioEnterStockTicker();

  /**
   * Show enter stock name message.
   */
  void portfolioEnterStockName();

  /**
   * Show enter stock quantity message.
   */
  void portfolioEnterStockQty();

  /**
   * Show enter user's first name.
   */
  void userEnterFirstName();

  /**
   * Show enter user's last name.
   */
  void userEnterLastName();

  /**
   * Show enter user's middle name.
   */
  void userEnterMiddleName();

  /**
   * Show enter user's UserID.
   */
  void userEnterUserID();

  /**
   * Show enter user's password.
   */
  void userEnterPassword();

  /**
   * Show enter portfolio name.
   */
  void portfolioEnterPortfolioName();

  /**
   * Shows a list of portfolios.
   * @param portfolioList list of portfolio names.
   */
  void showListOfPortfolios(List<String> portfolioList);

  /**
   * Shows the contents in a portfolio.
   *
   * @param portfolio the portfolio object.
   * @param date date before which content of portfolio is to be shown.
   * @param prices list of stock prices.
   * @param total total value on a given date.
   */
  void showPortfolioContents(Portfolio portfolio, List<Double> prices, String date, Double total);

  /**
   * Shows the options in the portfolio menu.
   */
  void showPortfolioRecurringMenu();

  /**
   * Shows a message to prompt the user to select a portfolio.
   */
  void showSelectPortfolioMessage();

  /**
   * Shows a message to prompt the user to enter a filepath.
   */
  void portfolioEnterPortfolioPath();

  /**
   * Shows a message asking the user if they want prices on a specific date.
   */
  void showGetPricesOnADate();

  /**
   * Shows a message to prompt the user to enter a date.
   */
  void showEnterDateInput();

  /**
   * Shows options for selecting type of portfolio.
   */
  void showSelectPortfolioOptions();


  //=======================NEW METHODS========================

  /**
   * Shows method to enter transaction date.
   */
  void portfolioEnterTransactionDate();

  /**
   * Show portfolio menu after new portfolio is created.
   */
  void createAdvPortfolioMenu();

  /**
   * Show available operations on a selected portfolio.
   */
  void showSelectedPortfolioOptions();

  /**
   * Show select stock ID ticker message.
   */
  void showSelectStockIDMessage();

  /**
   * Show the total value of the portfolio on a given date.
   *
   * @param totalValue total value of the portfolio on a given date.
   * @param date date for getting the value of portfolio.
   */
  void showTotalValue(double totalValue, String date);

  /**
   * Show the cost basis value for a portfolio on a given date.
   *
   * @param costBasis the calculated cost basis value.
   * @param date1     the date until which cost basis is required.
   */
  void showCostBasisValue(double costBasis, String date1);

  /**
   * Show the contents of an advanced portfolio.
   *
   * @param portfolio advanced portfolio object.
   */
  void showAdvPortfolioContents(Portfolio portfolio);

  /**
   * Displays a bar chart consisting of stars to represent value of a portfolio over a time period.
   *
   * @param portfolioName name of the portfolio.
   * @param fromDate      the start date of graph.
   * @param toDate        the end date of graph.
   * @param timestamps    the list of timestamps to be displayed.
   * @param starCount     the number of stars to be displayed for corresponding timestamps.
   */
  void plotStarBarGraphMonth(String portfolioName, String fromDate, String toDate,
                             List<String> timestamps, List<Integer> starCount);

  void plotStarBarGraphDayYear(String portfolioName, String fromDate, String toDate,
                               List<String> timestamps, List<Integer> starCount);

  /**
   * Show message asking for start date from user.
   */
  void showEnterFromDateInput();

  /**
   * Show message asking for end date from user.
   */
  void showEnterToDateInput();


  /**
   * Show message to enter commission fee.
   */
  void userEnterCommissionFee();

  /**
   * Display error message as provided in the argument.
   *
   * @param s error message to be displayed.
   */
  void showErrorMessage(String s);
}
