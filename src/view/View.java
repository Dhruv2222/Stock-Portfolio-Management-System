package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.Portfolio;

/**
 * This class implements the PortfolioView interface and implements all the operations
 * required to display results of the Portfolio Management system.
 */
public class View implements PortfolioView {

  private final PrintStream out;

  public View(PrintStream out) {
    this.out = out;
  }

  @Override
  public void startUpMenu() {
    this.out.println("Welcome to Model.Portfolio Management Application:");
    this.out.println("======================");
    this.out.println("Select an option:");
    this.out.println("======================");
    this.out.println("1. Login");
    this.out.println("2. Create new user");
    this.out.println("3. Exit");
  }

  @Override
  public void welcomeUser(String firstName) {
    this.out.println();
    this.out.println("Hello " + firstName + ",");
  }

  @Override
  public void showPortfolioRecurringMenu() {

    this.out.println();
    this.out.println("What would you like to do:");
    this.out.println("======================");
    this.out.println("1. Create portfolio");
    this.out.println("2. View portfolios");
    this.out.println("3. Select a portfolio");
    this.out.println("4. Upload a portfolio");
    this.out.println("5. Exit");
  }

  @Override
  public void showSelectPortfolioMessage() {
    this.out.print("Select the portfolio you want to view: ");
  }

  @Override
  public void portfolioCreatedMessage() {
    this.out.println("Portfolio was created successfully.");
  }

  @Override
  public void createPortfolioMenu() {
    this.out.println("Select an option:");
    this.out.println("1. Enter a stock");
    this.out.println("2. Save portfolio");
  }

  @Override
  public void createAdvPortfolioMenu() {
    this.out.println("Select an option:");
    this.out.println("1. Buy a stock");
    this.out.println("2. Save portfolio");
  }

  @Override
  public void showSelectedPortfolioOptions() {
    this.out.println("======================");
    this.out.println("Select operation on the portfolio:");
    this.out.println("1. Buy a stock");
    this.out.println("2. Sell a stock");
    this.out.println("3. Get value on a date");
    this.out.println("4. Get cost-basis");
    this.out.println("5. Save portfolio");
    this.out.println("6. Show portfolio composition");
    this.out.println("7. Graph analysis");
    this.out.println("8. Go back");

  }

  @Override
  public void showSelectStockIDMessage() {
    this.out.print("Enter stock ticker you want to sell: ");
  }

  @Override
  public void showTotalValue(double totalValue, String date) {
    this.out.println("Total value of the portfolio on " + date + " is: " + totalValue);
  }

  @Override
  public void showCostBasisValue(double costBasis, String date1) {
    this.out.println("Cost basis of the portfolio before " + date1 + " is: " + costBasis);
  }

  @Override
  public void portfolioSavedMessage() {
    this.out.println("Portfolio saved!");
  }

  @Override
  public void portfolioEnterStockTicker() {
    this.out.println("Add details for new stock:");
    this.out.println("======================");
    this.out.print("Enter stock ticker (No spaces): ");
  }

  @Override
  public void portfolioEnterStockName() {
    this.out.print("Enter Stock Name (No spaces): ");
  }

  @Override
  public void portfolioEnterStockQty() {
    this.out.print("Enter Stock Quantity: ");
  }

  @Override
  public void userEnterFirstName() {
    this.out.print("Enter first name: ");
  }

  @Override
  public void userEnterLastName() {
    this.out.print("Enter last name: ");
  }

  @Override
  public void userEnterMiddleName() {
    this.out.print("Enter middle name: ");
  }

  @Override
  public void userEnterUserID() {
    this.out.print("Enter userID:");
  }

  @Override
  public void userEnterPassword() {
    this.out.print("Enter password: ");
  }

  @Override
  public void portfolioEnterPortfolioName() {
    this.out.print("Enter portfolio name: ");
  }

  @Override
  public void showListOfPortfolios(List<String> portfolioNames) {
    this.out.println("Following are your saved portfolios");
    int count = 0;
    for (String p : portfolioNames) {
      count++;
      this.out.println(count + ". " + p);
    }
  }

  @Override
  public void showPortfolioContents(Portfolio portfolio, List<Double> prices, String date,
                                    Double total) {
    this.out.println("Composition of " + portfolio.getPortfolioName() + " portfolio:-");
    this.out.println();
    this.out.printf("%-15s %-15s %-15s %-15s\n", "Stock Ticker", "Stock Name", "Stock Quantity",
            "Stock price (on " + date + " )");
    this.out.println("---------------------------------------------------------------------------");
    for (int i = 0; i < portfolio.getStockID().size(); i++) {
      this.out.printf("%-15s %-15s %-15s $%-15s\n", portfolio.getStockID().get(i),
              portfolio.getStockName()
                      .get(i), portfolio.getStockQuantity().get(i), prices.get(i));
    }
    this.out.println();
    this.out.format("Total value : $%.2f", total);
    this.out.println();
  }

  @Override
  public void showAdvPortfolioContents(Portfolio portfolio) {
    this.out.println("Composition of the portfolio:-");
    this.out.println();
    this.out.printf("%-15s %-15s %-15s\n", "Stock Ticker", "Stock Name", "Stock Quantity");
    this.out.println("---------------------------------------------------------------------------");
    for (int i = 0; i < portfolio.getStockID().size(); i++) {
      this.out.printf("%-15s %-15s %-15s\n", portfolio.getStockID().get(i),
              portfolio.getStockName()
                      .get(i), portfolio.getStockQuantity().get(i));
    }
    this.out.println();
  }

  @Override
  public void plotStarBarGraphMonth(String portfolioName, String fromDate, String toDate,
                                    List<String> timestamps, List<Integer> starCount) {

    this.out.println();
    this.out.println("Performance of portfolio " + portfolioName + " from " + fromDate + " to " +
            toDate);

    for (int i = 1; i < timestamps.size(); i++) {
      List<String> monthAndYear = new ArrayList<>(List.of(timestamps.get(i).split(" ")));

      String month = Character.toUpperCase(monthAndYear.get(0).charAt(0)) +
              monthAndYear.get(0).substring(1).toLowerCase().substring(0, 2) + " ";
      String year = monthAndYear.get(1);
      this.out.print(month + year + ": ");
      for (int j = 0; j < starCount.get(i); j++) {
        this.out.print("*");
      }
      this.out.println();
    }

    this.out.println();
    this.out.println("Scale: * = $" + starCount.get(0));

  }

  @Override
  public void plotStarBarGraphDayYear(String portfolioName, String fromDate, String toDate,
                                      List<String> timestamps, List<Integer> starCount) {

    this.out.println();
    this.out.println("Performance of portfolio " + portfolioName + " from " + fromDate + " to " +
            toDate);

    for (int i = 1; i < timestamps.size(); i++) {
      this.out.print(timestamps.get(i) + ": ");
      for (int j = 0; j < starCount.get(i); j++) {
        this.out.print("*");
      }
      this.out.println();
    }

    this.out.println();
    this.out.println("Scale: * = $" + starCount.get(0));

  }


  @Override
  public void showEnterFromDateInput() {
    this.out.println("Enter a start date (YYYY-MM-DD): ");
  }

  @Override
  public void showEnterToDateInput() {
    this.out.println("Enter a end date (YYYY-MM-DD): ");
  }


  @Override
  public void portfolioEnterPortfolioPath() {
    this.out.print("Enter the path of the portfolio: ");
  }

  @Override
  public void showGetPricesOnADate() {
    this.out.println("Do you want to view the prices of this portfolio on a specific date?");
    this.out.println("1. Yes");
    this.out.println("2. Previous day's price:");
  }

  @Override
  public void showEnterDateInput() {
    this.out.print("Enter a date (YYYY-MM-DD): ");
  }

  @Override
  public void showSelectPortfolioOptions() {
    this.out.println("Select type of portfolio");
    this.out.println("1. Observing portfolio");
    this.out.println("2. Trading portfolio");
  }

  @Override
  public void portfolioEnterTransactionDate() {
    this.out.print("Enter transaction date:");
  }

  @Override
  public void userEnterCommissionFee() {
    this.out.print("Enter your commission fee:");
  }

  @Override
  public void showErrorMessage(String s) {
    this.out.println("Error: " + s);
  }

}
