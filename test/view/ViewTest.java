package view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit tests on the View class.
 */
public class ViewTest {

  PortfolioView pv;

  ByteArrayOutputStream bytes;

  @Before
  public void setup() {
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    pv = new View(out);

  }

  @Test
  public void testPortfolioCreatedMessage() {
    pv.portfolioCreatedMessage();
    assertEquals("Portfolio was created successfully.", bytes.toString().trim());
  }

  @Test
  public void testPortfolioSavedMessage() {
    pv.portfolioSavedMessage();
    assertEquals("Portfolio saved!", bytes.toString().trim());
  }

  @Test
  public void testPortfolioEnterStockName() {
    pv.portfolioEnterStockName();
    assertEquals("Enter Stock Name (No spaces): ", bytes.toString());
  }

  @Test
  public void testPortfolioEnterStockQty() {
    pv.portfolioEnterStockQty();
    assertEquals("Enter Stock Quantity: ", bytes.toString());
  }

  @Test
  public void testUserEnterFirstName() {
    pv.userEnterFirstName();
    assertEquals("Enter first name: ", bytes.toString());
  }

  @Test
  public void testUserEnterLastName() {
    pv.userEnterLastName();
    assertEquals("Enter last name: ", bytes.toString());
  }

  @Test
  public void testUserEnterMiddleName() {
    pv.userEnterMiddleName();
    assertEquals("Enter middle name: ", bytes.toString());
  }

  @Test
  public void testUserEnterUserID() {
    pv.userEnterUserID();
    assertEquals("Enter userID:", bytes.toString());
  }

  @Test
  public void testUserEnterPassword() {
    pv.userEnterPassword();
    assertEquals("Enter password: ", bytes.toString());
  }

  @Test
  public void testPortfolioEnterPortfolioName() {
    pv.portfolioEnterPortfolioName();
    assertEquals("Enter portfolio name: ", bytes.toString());
  }

  @Test
  public void testPortfolioEnterPortfolioPath() {
    pv.portfolioEnterPortfolioPath();
    assertEquals("Enter the path of the portfolio: ", bytes.toString());
  }

  @Test
  public void testStartUpMenu() {
    pv.startUpMenu();
    assertEquals("Welcome to Model.Portfolio Management Application:\n" +
            "======================\n" +
            "Select an option:\n" +
            "======================\n" +
            "1. Login\n" +
            "2. Create new user\n" +
            "3. Exit\n", bytes.toString());
  }

  @Test
  public void testPortfolioMenu() {
    pv.createPortfolioMenu();
    assertEquals("Select an option:\n" +
            "1. Enter a stock\n" +
            "2. Save portfolio\n", bytes.toString());
  }

  @Test
  public void testShowPortfolioRecurringMenu() {
    pv.showPortfolioRecurringMenu();
    assertEquals("\n" +
            "What would you like to do:\n" +
            "======================\n" +
            "1. Create portfolio\n" +
            "2. View portfolios\n" +
            "3. Select a portfolio\n" +
            "4. Upload a portfolio\n" +
            "5. Exit\n", bytes.toString());
  }

  @Test
  public void testShowSelectPortfolioMessage() {
    pv.showSelectPortfolioMessage();
    assertEquals("Select the portfolio you want to view: ", bytes.toString());
  }

  @Test
  public void testPortfolioEnterStockTicker() {
    pv.portfolioEnterStockTicker();
    assertEquals("Add details for new stock:\n" +
            "======================\n" +
            "Enter stock ticker (No spaces): ", bytes.toString());
  }

  @Test
  public void testShowEnterDateInput() {
    pv.showEnterDateInput();
    assertEquals("Enter a date (YYYY-MM-DD): ", bytes.toString());
  }

  @Test
  public void testShowGetPricesOnADate() {
    pv.showGetPricesOnADate();
    assertEquals("Do you want to view the prices of this portfolio on a specific date?\n"
            +
            "1. Yes\n" +
            "2. Previous day's price:\n", bytes.toString());

  }

  @Test
  public void testShowListOfPortfolios() {
    List<String> p = new ArrayList<>();
    p.add("Gold");
    p.add("Silver");
    p.add("Bronze");
    pv.showListOfPortfolios(p);
    assertEquals("Following are your saved portfolios\n" +
            "1. Gold\n" +
            "2. Silver\n" +
            "3. Bronze\n", bytes.toString());
  }

  @Test
  public void testWelcomeUser() {
    String firstName = "Vishant";
    pv.welcomeUser(firstName);
    assertEquals("\n" +
            "Hello Vishant,\n", bytes.toString());
  }

  @Test
  public void testTransactionDate() {
    pv.portfolioEnterTransactionDate();
    assertEquals("Enter transaction date:", bytes.toString());
  }

  @Test
  public void testShowSelectPortfolioOptions() {
    pv.showSelectPortfolioOptions();
    assertEquals("Select type of portfolio\n" +
            "1. Observing portfolio\n" +
            "2. Trading portfolio\n", bytes.toString());
  }

  @Test
  public void testShowSelectedPortfolioOptions() {
    pv.showSelectedPortfolioOptions();
    assertEquals("======================\n" +
            "Select operation on the portfolio:\n" +
            "1. Buy a stock\n" +
            "2. Sell a stock\n" +
            "3. Get value on a date\n" +
            "4. Get cost-basis\n" +
            "5. Save portfolio\n" +
            "6. Show portfolio composition\n" +
            "7. Graph analysis\n" +
            "8. Go back\n", bytes.toString());
  }

  @Test
  public void testCreateAdvPortfolioMenu() {
    pv.createAdvPortfolioMenu();
    assertEquals("Select an option:\n" +
            "1. Buy a stock\n" +
            "2. Save portfolio\n", bytes.toString());
  }

  @Test
  public void testShowSelectStockIDMessage() {
    pv.showSelectStockIDMessage();
    assertEquals("Enter stock ticker you want to sell: ", bytes.toString());
  }

  @Test
  public void testShowTotalValue() {
    double totalValue = 20.0;
    String date = "2022-11-11";
    pv.showTotalValue(totalValue, date);
    assertEquals("Total value of the portfolio on 2022-11-11 is: 20.0\n",
            bytes.toString());
  }

  @Test
  public void testShowCostBasisValue() {
    double costBasis = 300.0;
    String date1 = "2022-11-10";
    pv.showCostBasisValue(costBasis, date1);
    assertEquals("Cost basis of the portfolio before 2022-11-10 is: 300.0\n",
            bytes.toString());
  }

  /**
   * Test the graph display method when provided with months.
   */
  @Test
  public void testGraphDisplayMonth() {
    String portfolioName = "newPortfolio";
    String fromDate = "2022-03-11";
    String toDate = "2022-08-31";
    List<String> timestamps = new ArrayList<>();
    timestamps.add("month");
    timestamps.add("Mar 2022");
    timestamps.add("Apr 2022");
    timestamps.add("May 2022");
    timestamps.add("Jun 2022");
    timestamps.add("Jul 2022");
    timestamps.add("Aug 2022");

    List<Integer> starCount = new ArrayList<>();
    starCount.add(123);
    starCount.add(2);
    starCount.add(10);
    starCount.add(40);
    starCount.add(50);
    starCount.add(35);
    starCount.add(30);

    pv.plotStarBarGraphMonth(portfolioName, fromDate, toDate, timestamps, starCount);

    assertEquals("\n" +
                    "Performance of portfolio newPortfolio from 2022-03-11 to 2022-08-31\n" +
                    "Mar 2022: **\n" +
                    "Apr 2022: **********\n" +
                    "May 2022: ****************************************\n" +
                    "Jun 2022: **************************************************\n" +
                    "Jul 2022: ***********************************\n" +
                    "Aug 2022: ******************************\n" +
                    "\n" +
                    "Scale: * = $123\n",
            bytes.toString());
  }

  /**
   * Test the graph display method when provided with year input.
   */
  @Test
  public void testGraphDisplayYear() {
    String portfolioName = "newPortfolio";
    String fromDate = "2016-03-11";
    String toDate = "2022-01-31";
    List<String> timestamps = new ArrayList<>();
    timestamps.add("year");
    timestamps.add("2016");
    timestamps.add("2017");
    timestamps.add("2018");
    timestamps.add("2019");
    timestamps.add("2020");
    timestamps.add("2021");

    List<Integer> starCount = new ArrayList<>();
    starCount.add(123);
    starCount.add(2);
    starCount.add(10);
    starCount.add(40);
    starCount.add(50);
    starCount.add(35);
    starCount.add(30);

    pv.plotStarBarGraphDayYear(portfolioName, fromDate, toDate, timestamps, starCount);

    assertEquals("\n" +
                    "Performance of portfolio newPortfolio from 2016-03-11 to 2022-01-31\n" +
                    "2016: **\n" +
                    "2017: **********\n" +
                    "2018: ****************************************\n" +
                    "2019: **************************************************\n" +
                    "2020: ***********************************\n" +
                    "2021: ******************************\n" +
                    "\n" +
                    "Scale: * = $123\n",
            bytes.toString());
  }

  /**
   * Test the graph display method when provided with days input.
   */
  @Test
  public void testGraphDisplayDays() {
    String portfolioName = "newPortfolio";
    String fromDate = "2016-03-11";
    String toDate = "2016-03-17";
    List<String> timestamps = new ArrayList<>();
    timestamps.add("day");
    timestamps.add("2016-03-11");
    timestamps.add("2016-03-12");
    timestamps.add("2016-03-13");
    timestamps.add("2016-03-14");
    timestamps.add("2016-03-15");
    timestamps.add("2016-03-16");

    List<Integer> starCount = new ArrayList<>();
    starCount.add(123);
    starCount.add(2);
    starCount.add(10);
    starCount.add(40);
    starCount.add(50);
    starCount.add(35);
    starCount.add(30);

    pv.plotStarBarGraphDayYear(portfolioName, fromDate, toDate, timestamps, starCount);

    assertEquals("\n" +
                    "Performance of portfolio newPortfolio from 2016-03-11 to 2016-03-17\n" +
                    "2016-03-11: **\n" +
                    "2016-03-12: **********\n" +
                    "2016-03-13: ****************************************\n" +
                    "2016-03-14: **************************************************\n" +
                    "2016-03-15: ***********************************\n" +
                    "2016-03-16: ******************************\n" +
                    "\n" +
                    "Scale: * = $123\n",
            bytes.toString());
  }

}