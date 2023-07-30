package controller;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import model.Model;
import model.Portfolio;
import model.PortfolioModel;
import model.User;
import view.PortfolioView;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit tests on the Controller class in isolation.
 */
public class ControllerTest {

  //main mock view
  private static class MockViewMethodCallCheck implements PortfolioView {

    private final StringBuilder out;

    public MockViewMethodCallCheck(StringBuilder out) {
      this.out = out;
    }

    @Override
    public void startUpMenu() {
      this.out.append("startUpMenu()");
    }

    @Override
    public void welcomeUser(String firstName) {
      this.out.append("welcomeUser()");
    }

    @Override
    public void showPortfolioRecurringMenu() {
      this.out.append("showPortfolioRecurringMenu()");
    }

    @Override
    public void showSelectPortfolioMessage() {
      this.out.append("showSelectPortfolioMessage()");

    }

    @Override
    public void portfolioCreatedMessage() {
      this.out.append("portfolioCreatedMessage()");
    }

    @Override
    public void createPortfolioMenu() {
      this.out.append("createPortfolioMenu()");
    }

    @Override
    public void portfolioSavedMessage() {
      this.out.append("portfolioSavedMessage()");
    }

    @Override
    public void portfolioEnterStockTicker() {
      this.out.append("portfolioEnterStockTicker()");
    }

    @Override
    public void portfolioEnterStockName() {
      this.out.append("portfolioEnterStockName()");
    }

    @Override
    public void portfolioEnterStockQty() {
      this.out.append("portfolioEnterStockQty()");
    }

    @Override
    public void userEnterFirstName() {
      this.out.append("userEnterFirstName()");
    }

    @Override
    public void userEnterLastName() {
      this.out.append("userEnterLastName()");
    }

    @Override
    public void userEnterMiddleName() {
      this.out.append("userEnterMiddleName()");
    }

    @Override
    public void userEnterUserID() {
      this.out.append("userEnterUserID()");
    }

    @Override
    public void userEnterPassword() {
      this.out.append("userEnterPassword()");
    }

    @Override
    public void portfolioEnterPortfolioName() {
      this.out.append("portfolioEnterPortfolioName()");
    }

    @Override
    public void showListOfPortfolios(List<String> portfolioNames) {
      this.out.append("showListOfPortfolios()").append(";");
      for (String p : portfolioNames) {
        this.out.append(p).append(";");
      }
    }

    @Override
    public void showPortfolioContents(Portfolio portfolio, List<Double> prices, String date,
                                      Double total) {
      this.out.append("showPortfolioContents()").append(";").append(portfolio
                      .getPortfolioName()).append(";").append(portfolio.getStockID())
              .append(";").append(portfolio.getStockName()).append(";")
              .append(portfolio.getStockQuantity()).append(";");
    }

    @Override
    public void portfolioEnterPortfolioPath() {
      this.out.append("portfolioEnterPortfolioPath()");
    }

    @Override
    public void showGetPricesOnADate() {
      this.out.append("showGetPricesOnADate()");
    }

    @Override
    public void showEnterDateInput() {
      this.out.append("showEnterDateInput()");
    }

    @Override
    public void showSelectPortfolioOptions() {
      this.out.append("showSelectPortfolioOptions()");
    }

    @Override
    public void portfolioEnterTransactionDate() {
      this.out.append("portfolioEnterTransactionDate()");
    }

    @Override
    public void createAdvPortfolioMenu() {
      this.out.append("createAdvPortfolioMenu()");
    }

    @Override
    public void showSelectedPortfolioOptions() {
      this.out.append("showSelectedPortfolioOptions()");
    }

    @Override
    public void showSelectStockIDMessage() {
      this.out.append("showSelectStockIDMessage()");
    }

    @Override
    public void showTotalValue(double totalValue, String date) {
      this.out.append("showTotalValue()");
    }

    @Override
    public void showCostBasisValue(double costBasis, String date1) {
      this.out.append("showCostBasisValue()");
    }

    @Override
    public void showAdvPortfolioContents(Portfolio portfolio) {
      this.out.append("showAdvPortfolioContents()");
    }

    @Override
    public void plotStarBarGraphMonth(String portfolioName, String fromDate, String toDate,
                                      List<String> timestamps, List<Integer> starCount) {
      this.out.append("plotStarBarGraphMonth()");
    }

    @Override
    public void plotStarBarGraphDayYear(String portfolioName, String fromDate, String toDate,
                                        List<String> timestamps, List<Integer> starCount) {
      this.out.append("plotStarBarGraphDayYear()");
    }

    @Override
    public void showEnterFromDateInput() {
      this.out.append("showEnterFromDateInput()");
    }

    @Override
    public void showEnterToDateInput() {
      this.out.append("showEnterToDateInput()");
    }

    @Override
    public void userEnterCommissionFee() {
      this.out.append("userEnterCommissionFee()");
    }

    @Override
    public void showErrorMessage(String s) {
      this.out.append("showErrorMessage()");
    }

  }

  //main mock model
  private static class MockModelMethodCallCheck implements PortfolioModel {

    private final StringBuilder log;

    public MockModelMethodCallCheck(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void createUser(String firstName, String lastName, String middleName, String userID,
                           String password, int portfolioType, double commissionFee) {
      log.append("createUser();");
    }

    @Override
    public void login(String userID, String password, int portfolioType) {
      log.append("login();");
    }

    @Override
    public void createPortfolio(String portfolioName, User currentUser, int portfolioType) {
      log.append("createPortfolio();");
    }

    @Override
    public void addStockInPortfolio(String key, String value, Portfolio newPortfolio) {
      log.append("addStockInPortfolio();");
    }

    @Override
    public void savePortfolio(Portfolio newPortfolio, User currentUser) {
      log.append("savePortfolio();");
    }

    @Override
    public List<String> showPortfolios(User currentUser) {
      log.append("showPortfolios();");
      return null;
    }

    @Override
    public double showTotalInvestment(Portfolio newPortfolio, List<Double> prices) {
      log.append("showTotalInvestment();");
      return 0;
    }

    @Override
    public void selectPortfolio(User currentUser, String portfolioID) {
      log.append("selectPortfolio();");
    }

    @Override
    public void uploadPortfolio(User currentUser, String filepath) {
      log.append("uploadPortfolio();");
    }

    @Override
    public List<Double> getStockPricesOnDate(LocalDate date, Portfolio newPortfolio) {
      log.append("getStockPricesOnDate();");
      return null;
    }

    @Override
    public void sellStock(Portfolio newPortfolio, String stockID, double sellQty, String date) {
      log.append("sellStock();");
    }

    @Override
    public double getTotalValueOnDate(LocalDate date, Portfolio newPortfolio) {
      log.append("getTotalValueOnDate();");
      return 0;
    }

    @Override
    public double getCostBasis(LocalDate date, Portfolio newPortfolio) {
      log.append("getCostBasis();");
      return 0;
    }

    @Override
    public void showComposition(Portfolio newPortfolio, LocalDate date) {
      log.append("showComposition();");
    }

    @Override
    public List<String> getGraphTimestamp(String fromDate, String toDate) {
      log.append("getGraphTimestamp();");
      return null;
    }

    @Override
    public List<Integer> getGraphStarCount(Portfolio newPortfolio, List<String> timestamp) {
      log.append("getGraphStarCount();");
      return null;
    }

    @Override
    public User getCurrentUser() {
      log.append("getCurrentUser();");
      return null;
    }

    @Override
    public Portfolio getWorkingPortfolio() {
      log.append("getWorkingPortfolio();");
      return null;
    }

    @Override
    public Portfolio getConsolidatedPortfolio() {
      log.append("getConsolidatedPortfolio();");
      return null;
    }

    @Override
    public void investFixedAmount(String amount, String date) {
      log.append("investFixedAmount();");
    }

    @Override
    public void startToEndInvest(String amount, String intervalNum, String intervalDuration,
                                 String startDate, String endDate, boolean ongoing) {
      log.append("startToEndInvest();");
    }

    @Override
    public List<Integer> getGraphHeight(Portfolio newPortfolio, List<String> timestamp) {
      log.append("getGraphHeight();");
      return null;
    }

    @Override
    public double getGraphIncrement() {
      log.append("getGraphIncrement();");
      return 0;
    }


    @Override
    public void collectTickerRatios(String ticker, String ratio) {
      log.append("collectTickerRatios();");
    }

    @Override
    public void collectTickerNames(String ticker, String stockName) {
      log.append("collectTickerNames();");
    }

    @Override
    public void startNewStrategy() {
      log.append("startNewStrategy();");
    }

    @Override
    public LocalDate validateDateInput(String date) {
      log.append("validateDateInput();");
      return null;
    }
  }

  private static class MockModelSellStock extends Model {

    private final StringBuilder log;

    public MockModelSellStock(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void sellStock(Portfolio newPortfolio, String stockID, double sellQty, String date) {
      this.log.append("sellStock();");
      this.log.append(newPortfolio.getPortfolioName()).append(";").append(stockID).append(";")
              .append(sellQty)
              .append(";").append(date);
    }

  }

  private static class MockModelGetTotalValueOnDate extends Model {

    private final StringBuilder log;

    public MockModelGetTotalValueOnDate(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double getTotalValueOnDate(LocalDate date, Portfolio newPortfolio) {
      this.log.append("getTotalValueOnDate();");
      this.log.append(newPortfolio.getPortfolioName()).append(";").append(date.toString());
      return 0;
    }

  }

  private static class MockModelGetCostBasis extends Model {

    private final StringBuilder log;

    public MockModelGetCostBasis(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double getCostBasis(LocalDate date, Portfolio newPortfolio) {
      this.log.append("getCostBasis();");
      this.log.append(newPortfolio.getPortfolioName()).append(";").append(date.toString());
      return 0;
    }

  }

  private static class MockModelShowComposition extends Model {

    private final StringBuilder log;

    public MockModelShowComposition(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void showComposition(Portfolio newPortfolio, LocalDate date) {
      this.log.append("showComposition();");
      this.log.append(newPortfolio.getPortfolioName()).append(";").append(date.toString());
    }

  }

  private static class MockModelGetGraphTimestamp extends Model {

    private final StringBuilder log;

    public MockModelGetGraphTimestamp(StringBuilder log) {
      this.log = log;
    }

    @Override
    public List<String> getGraphTimestamp(String fromDate, String toDate) {
      this.log.append("getGraphTimestamp();");
      this.log.append(fromDate).append(";").append(toDate);
      return null;
    }

  }

  private static class MockModelGetGraphStarCount extends Model {

    private final StringBuilder log;

    public MockModelGetGraphStarCount(StringBuilder log) {
      this.log = log;
    }

    @Override
    public List<Integer> getGraphStarCount(Portfolio newPortfolio, List<String> timestamp) {
      this.log.append("getGraphStarCount();");
      this.log.append(newPortfolio.getPortfolioName()).append(";").append(timestamp);
      return null;
    }

  }

  private static class MockModelCreateUser extends MockModelMethodCallCheck {

    public MockModelCreateUser(StringBuilder log) {
      super(log);
    }

    @Override
    public void createUser(String firstName, String lastName, String middleName, String userID,
                           String password, int portfolioType, double commissionFee) {
      super.log.append("createUser();");
      super.log.append(firstName).append(";").append(lastName).append(";").append(middleName)
              .append(";").append(userID).append(";").append(password);
    }

  }

  private static class MockModelLogin extends MockModelMethodCallCheck {

    public MockModelLogin(StringBuilder log) {
      super(log);
    }

    @Override
    public void login(String userID, String password, int portfolioType) {
      super.log.append("login();").append(userID).append(";").append(password);
    }


  }

  private static class MockModelCreatePortfolio extends Model {

    private final StringBuilder log;

    public MockModelCreatePortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void createPortfolio(String portfolioName, User currentUser, int portfolioType) {
      log.append("createPortfolio();").append(portfolioName);
    }

  }

  private static class MockModelAddNewStock extends Model {

    private final StringBuilder log;

    public MockModelAddNewStock(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void addStockInPortfolio(String key, String value, Portfolio newPortfolio) {
      log.append("addStockInPortfolio();").append(key).append(";").append(value).append(";")
              .append(newPortfolio.getPortfolioName()).append(":");
    }


  }

  private static class MockModelSavePortfolio extends Model {
    private final StringBuilder log;

    public MockModelSavePortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void savePortfolio(Portfolio newPortfolio, User currentUser) {
      log.append("savePortfolio();").append(newPortfolio.getPortfolioName()).append(";")
              .append(currentUser.getUserID());
    }

  }

  private static class MockModelShowPortfolios extends Model {
    private final StringBuilder log;

    public MockModelShowPortfolios(StringBuilder log) {
      this.log = log;
    }

    @Override
    public List<String> showPortfolios(User currentUser) {
      log.append("showPortfolios();").append(currentUser.getUserID());
      return null;
    }

  }

  private static class MockModelSelectPortfolio extends Model {
    private final StringBuilder log;

    public MockModelSelectPortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void selectPortfolio(User currentUser, String portfolioID) {
      log.append("selectPortfolio();").append(portfolioID).append(";")
              .append(currentUser.getUserID());
    }

  }

  private static class MockModelUploadPortfolio extends Model {
    private final StringBuilder log;

    public MockModelUploadPortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void uploadPortfolio(User currentUser, String filepath) {
      log.append("uploadPortfolio();").append(currentUser.getUserID()).append(";").append(filepath);
    }

  }

  private static class MockModelGetPriceOnDate extends Model {
    private final StringBuilder log;

    public MockModelGetPriceOnDate(StringBuilder log) {
      this.log = log;
    }

    @Override
    public List<Double> getStockPricesOnDate(LocalDate date, Portfolio newPortfolio) {
      log.append("getStockPricesOnDate();").append(date.toString()).append(";")
              .append(newPortfolio.getPortfolioName());
      return null;
    }

  }

  private static class MockModelTotalInvestment extends Model {
    private final StringBuilder log;

    public MockModelTotalInvestment(StringBuilder log) {
      this.log = log;
    }

    @Override
    public double showTotalInvestment(Portfolio newPortfolio, List<Double> prices) {
      log.append("showTotalInvestment();").append(newPortfolio.getPortfolioName()).append(";");
      for (Double d : prices) {
        log.append(d).append(";");
      }
      return 0;
    }

  }


  private PortfolioModel mockModelCreateUser;

  /**
   * Testing controller for create new user function.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testCreateUser() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelCreateUser(log);

    //setting input string
    String input = "1 2 Daniel\nCraig\nM\ndmc\nqwerty\n5";
    InputStream in = new ByteArrayInputStream(input.getBytes());

    //setting output variable and mock view.
    StringBuilder out = new StringBuilder();
    PortfolioView view = new MockViewMethodCallCheck(out);

    //creating controller object.
    PortfolioController controller = new Controller(in);
    try {
      controller.goController(mockModelCreateUser, view);
    } catch (Exception e) {
      assertEquals("createUser();Daniel;Craig;M;dmc;qwertygetCurrentUser();",
              log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterFirstName()" +
                      "userEnterLastName()userEnterMiddleName()userEnterUserID()" +
                      "userEnterPassword()",
              out.toString());
    }
  }

  /**
   * Testing controller for create new advanced user function.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testCreateAdvUser() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelCreateUser(log);

    //setting input string
    String input = "2 2 Daniel\nCraig\nM\ndmc\nqwerty\n5";
    InputStream in = new ByteArrayInputStream(input.getBytes());

    //setting output variable and mock view.
    StringBuilder out = new StringBuilder();
    PortfolioView view = new MockViewMethodCallCheck(out);

    //creating controller object.
    PortfolioController controller = new Controller(in);
    try {
      controller.goController(mockModelCreateUser, view);
    } catch (Exception e) {
      assertEquals("createUser();Daniel;Craig;M;dmc;qwertygetCurrentUser();",
              log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterFirstName()" +
                      "userEnterLastName()userEnterMiddleName()userEnterUserID()" +
                      "userEnterPassword()",
              out.toString());
    }
  }

  /**
   * Tests controller for Login functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testLogin() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelLogin(log);

    //setting input string
    String input = "1 1 dmd\nqwerty\n5";
    InputStream in = new ByteArrayInputStream(input.getBytes());

    //setting output variable and mock view.
    StringBuilder out = new StringBuilder();
    PortfolioView view = new MockViewMethodCallCheck(out);

    //creating controller object.
    PortfolioController controller = new Controller(in);
    try {
      controller.goController(mockModelCreateUser, view);
    } catch (Exception e) {
      assertEquals("login();dmd;qwertygetCurrentUser();", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()", out.toString());
    }
  }

  /**
   * Tests controller for Login functionality for advanced portfolio user.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testAdvLogin() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelLogin(log);

    //setting input string
    String input = "2 1 dmd\nqwerty\n5";
    InputStream in = new ByteArrayInputStream(input.getBytes());

    //setting output variable and mock view.
    StringBuilder out = new StringBuilder();
    PortfolioView view = new MockViewMethodCallCheck(out);

    //creating controller object.
    PortfolioController controller = new Controller(in);
    try {
      controller.goController(mockModelCreateUser, view);
    } catch (Exception e) {
      assertEquals("login();dmd;qwertygetCurrentUser();", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()", out.toString());
    }
  }


  /**
   * Tests controller for create portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testCreatePortfolio() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelCreatePortfolio(log);

    StringBuilder out = new StringBuilder();
    try {
      //setting input string
      String input = "1 1 dmd\nqwerty\n1 BronzeStocks\nTSLA\nTesla\n450\n1 GOOG\nGoogle\n200\n2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.
      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);

    } catch (Exception e) {
      assertEquals("createPortfolio();BronzeStocks", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()welcomeUser()" +
              "showPortfolioRecurringMenu()portfolioEnterPortfolioName()portfolioCreatedMessage()" +
              "portfolioEnterStockTicker()", out.toString());
    }

  }


  /**
   * Tests controller for create advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testCreateAdvPortfolio() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelCreatePortfolio(log);

    StringBuilder out = new StringBuilder();
    try {
      //setting input string
      String input = "2 1 dmd\nqwerty\n1 BronzeStocks\nTSLA\nTesla\n450\n2022-11-02\n1 GOOG\n" +
              "Google\n200\n2022-11-03\n2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.
      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);

    } catch (Exception e) {
      assertEquals("createPortfolio();BronzeStocks", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()welcomeUser()" +
              "showPortfolioRecurringMenu()portfolioEnterPortfolioName()portfolioCreatedMessage()" +
              "portfolioEnterStockTicker()", out.toString());
    }

  }


  /**
   * Tests controller for add more stocks while creating portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testAddStocks() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelAddNewStock(log);

    //setting input string
    String input = "1 dmd\nqwerty\n1 BronzeStocks\nTSLA\nTesla\n450\n2 6";
    InputStream in = new ByteArrayInputStream(input.getBytes());

    //setting output variable and mock view.
    StringBuilder out = new StringBuilder();
    PortfolioView view = new MockViewMethodCallCheck(out);

    //creating controller object.
    PortfolioController controller = new Controller(in);
    controller.goController(mockModelCreateUser, view);

    assertEquals("addStockInPortfolio();stockID;TSLA;BronzeStocks:addStockInPortfolio();" +
                    "stockName;Tesla;BronzeStocks:addStockInPortfolio();stockQuantity;" +
                    "450;BronzeStocks:",
            log.toString());
    assertEquals("startUpMenu()userEnterUserID()userEnterPassword()welcomeUser()" +
                    "showPortfolioRecurringMenu()portfolioEnterPortfolioName()" +
                    "portfolioCreatedMessage()" +
                    "portfolioEnterStockTicker()portfolioEnterStockName()portfolioEnterStockQty()" +
                    "createPortfolioMenu()portfolioSavedMessage()showPortfolioRecurringMenu()",
            out.toString());

  }

  /**
   * Tests controller for save portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testSavePortfolio() {
    //creating mock model.
    StringBuilder log = new StringBuilder();
    mockModelCreateUser = new MockModelSavePortfolio(log);

    //setting input string
    String input = "1 1 dmd\nqwerty\n1 BronzeStocks\nTSLA\nTesla\n450\n2 6";
    InputStream in = new ByteArrayInputStream(input.getBytes());

    //setting output variable and mock view.
    StringBuilder out = new StringBuilder();
    PortfolioView view = new MockViewMethodCallCheck(out);

    //creating controller object.
    PortfolioController controller = new Controller(in);
    controller.goController(mockModelCreateUser, view);

    assertEquals("savePortfolio();BronzeStocks;dmd", log.toString());
    assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                    "userEnterPassword()welcomeUser()" +
                    "showPortfolioRecurringMenu()portfolioEnterPortfolioName()" +
                    "portfolioCreatedMessage()" +
                    "portfolioEnterStockTicker()portfolioEnterStockName()portfolioEnterStockQty()" +
                    "createPortfolioMenu()portfolioSavedMessage()showPortfolioRecurringMenu()",
            out.toString());

  }


  /**
   * Tests controller for view portfolios functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testViewPortfolios() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelShowPortfolios(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (NullPointerException e) {
      assertEquals("showPortfolios();dmd", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()" +
                      "showPortfolioRecurringMenu()showListOfPortfolios();",
              out.toString());
    }
  }

  /**
   * Tests controller for view advanced portfolios functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testViewAdvPortfolios() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelShowPortfolios(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (NullPointerException e) {
      assertEquals("showPortfolios();dmd", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()" +
                      "showPortfolioRecurringMenu()showListOfPortfolios();",
              out.toString());
    }
  }

  /**
   * Tests controller for select portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testSelectPortfolio() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelSelectPortfolio(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n3 1 6 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (NullPointerException e) {
      assertEquals("selectPortfolio();Golden stocks;dmd", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()" +
                      "showPortfolioRecurringMenu()showListOfPortfolios();tester;Golden stocks;",
              out.toString());
    }
  }

  /**
   * Tests controller for select portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testSelectAdvPortfolio() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelSelectPortfolio(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 2 6 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (NullPointerException e) {
      assertEquals("selectPortfolio();Golden stocks;dmd", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()" +
                      "showPortfolioRecurringMenu()showListOfPortfolios();tester;Golden stocks;",
              out.toString());
    }
  }

  /**
   * Tests controller for upload portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testUploadPortfolio_1() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelUploadPortfolio(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n4 ./data/silver.xml 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {
      assertEquals("uploadPortfolio();dmd;./data/silver.xml",
              log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()" +
                      "userEnterUserID()userEnterPassword()welcomeUser()" +
                      "showPortfolioRecurringMenu()portfolioEnterPortfolioPath()" +
                      "showGetPricesOnADate()showPortfolioContents();silver;[GOOG, TSLA]" +
                      ";[Google, Tesla];[400.0, 350.0];showPortfolioRecurringMenu()",
              out.toString());
    }
  }

  /**
   * Tests controller for upload advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testUploadAdvPortfolio_1() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelUploadPortfolio(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n4 ./data/testGraph.xml 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {
      assertEquals("uploadPortfolio();dmd;./data/testGraph.xml",
              log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()" +
                      "showPortfolioRecurringMenu()portfolioEnterPortfolioPath()",
              out.toString());
    }
  }

  /**
   * Tests controller for upload portfolio functionality and the selecting view portfolio.
   * The loaded portfolio should not be loaded again.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testUploadPortfolio_2() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelUploadPortfolio(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n4 ./data/silver.xml 2 3 1 2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);

      //selectPortfolio() method call is not made even though that functionality is selected.
      //since the portfolio was already loaded by the user using the uploadPortfolio method.
      assertEquals("uploadPortfolio();dmd;./data/silver.xml",
              log.toString());


    } catch (RuntimeException e) {
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()welcomeUser()" +
              "showPortfolioRecurringMenu()portfolioEnterPortfolioPath()showGetPricesOnADate()" +
              "showPortfolioContents();silver;[GOOG, TSKA];[Google, Taska];[400, 350];" +
              "showPortfolioRecurringMenu()showListOfPortfolios();silver;tester;Golden stocks;" +
              "showGetPricesOnADate()showPortfolioContents();silver;[GOOG, TSKA];[Google, Taska];" +
              "[400, 350];showPortfolioRecurringMenu()", out.toString());
    }
  }

  /**
   * Tests controller for upload portfolio functionality and the selecting view portfolio.
   * The loaded portfolio should not be loaded again.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testUploadAdvPortfolio_4() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelUploadPortfolio(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n4 ./data/loadingFile.xml\n2022-11-02\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);

      //selectPortfolio() method call is not made even though that functionality is selected.
      //since the portfolio was already loaded by the user using the uploadPortfolio method.
      assertEquals("uploadPortfolio();dmd;./data/silver.xml",
              log.toString());


    } catch (RuntimeException e) {
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
              "portfolioEnterPortfolioPath()showEnterDateInput()showAdvPortfolioContents()" +
              "showPortfolioRecurringMenu()", out.toString());
    }
  }

  /**
   * Tests controller for upload portfolio functionality and the selecting view portfolio.
   * The loaded portfolio should not be loaded again.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testUploadAdvPortfolio_5() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelUploadPortfolio(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n4 ./data/loadingFileError.xml 2022-11-02";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);

      //selectPortfolio() method call is not made even though that functionality is selected.
      //since the portfolio was already loaded by the user using the uploadPortfolio method.
      assertEquals("uploadPortfolio();dmd;./data/loadingFileError.xml",
              log.toString());


    } catch (RuntimeException e) {
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
              "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
              "portfolioEnterPortfolioPath()showEnterDateInput()showAdvPortfolioContents()" +
              "showPortfolioRecurringMenu()", out.toString());
    }
  }

  /**
   * Tests controller for upload portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testUploadPortfolio_3() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelUploadPortfolio(log);

      //setting input string
      String input = "1 dmd\nqwerty\n4 ./data/silver.pdf 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (IllegalArgumentException e) {
      assertEquals("Incorrect file format", e.getMessage());
    }
  }

  /**
   * Tests controller for getPriceOnDate portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testGetPriceOnDate_1() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelGetPriceOnDate(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n3 2 1 2022-10-31\n 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {
      assertEquals("getStockPricesOnDate();2022-10-31;silver", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();metals;silver;showSelectPortfolioMessage()" +
                      "showGetPricesOnADate()showEnterDateInput()",
              out.toString());
    }
  }

  /**
   * Tests controller for getPriceOnDate portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testGetPriceOnDate_2() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelGetPriceOnDate(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n3 2 2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {
      assertEquals("getStockPricesOnDate();2022-11-15;silver", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();metals;silver;showSelectPortfolioMessage()" +
                      "showGetPricesOnADate()",
              out.toString());
    }
  }

  /**
   * Tests controller for total investment/value of portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testTotalInvestment() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelTotalInvestment(log);

      //setting input string
      String input = "1 1 dmd\nqwerty\n3 2 2 6";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);

      assertEquals("showTotalInvestment();silver;98.72;194.42;", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();metals;silver;showSelectPortfolioMessage()" +
                      "showGetPricesOnADate()showPortfolioContents();silver;[GOOG, TSLA];" +
                      "[Google, Tesla];[400.0, 350.0];showPortfolioRecurringMenu()",
              out.toString());

    } catch (RuntimeException e) {
      //pass
    }
  }


  /**
   * Tests controller- sell portfolio on advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testSellStock_1() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelSellStock(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 1 2 2022-11-15\nGOOG\n100\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {

      assertEquals("sellStock();loadingFile;GOOG;100.0;2022-11-15", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();loadingFile;showSelectPortfolioMessage()" +
                      "showSelectedPortfolioOptions()portfolioEnterTransactionDate()" +
                      "showAdvPortfolioContents()showSelectStockIDMessage()" +
                      "portfolioEnterStockQty()showSelectedPortfolioOptions()",
              out.toString());

    }
  }

  /**
   * Tests controller- get value of portfolio on a given date on advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testGetTotalValueOnDate() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelGetTotalValueOnDate(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 1 3 2022-11-15\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {

      assertEquals("getTotalValueOnDate();loadingFile;2022-11-15", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();loadingFile;showSelectPortfolioMessage()" +
                      "showSelectedPortfolioOptions()showEnterDateInput()showTotalValue()" +
                      "showSelectedPortfolioOptions()",
              out.toString());

    }
  }

  /**
   * Tests controller- get cost basis of portfolio on advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testGetCostBasis() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelGetCostBasis(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 1 4 2022-11-15\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {

      assertEquals("getCostBasis();loadingFile;2022-11-15", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();loadingFile;showSelectPortfolioMessage()" +
                      "showSelectedPortfolioOptions()showEnterDateInput()showCostBasisValue()" +
                      "showSelectedPortfolioOptions()",
              out.toString());

    }
  }

  /**
   * Tests controller- get composition of portfolio on advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testShowComposition() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelShowComposition(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 1 6 2022-11-15\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {

      assertEquals("showComposition();loadingFile;2022-11-15", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();loadingFile;showSelectPortfolioMessage()" +
                      "showSelectedPortfolioOptions()showEnterDateInput()" +
                      "showAdvPortfolioContents()showSelectedPortfolioOptions()",
              out.toString());

    }
  }

  /**
   * Tests controller- sell portfolio on advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testGetGraphTimestamp() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelGetGraphTimestamp(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 1 7 2021-12-31\n2022-05-31\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {
      assertEquals("getGraphTimestamp();2021-12-31;2022-05-31", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();loadingFile;showSelectPortfolioMessage()" +
                      "showSelectedPortfolioOptions()showEnterFromDateInput()" +
                      "showEnterToDateInput()",
              out.toString());

    }
  }

  /**
   * Tests controller- sell portfolio on advanced portfolio functionality.
   * Tests if correct inputs are given to the model.
   * Tests if correct method of View is called.
   */
  @Test
  public void testGetGraphStarCount() {
    StringBuilder log = new StringBuilder();
    StringBuilder out = new StringBuilder();
    try {
      //creating mock model.

      mockModelCreateUser = new MockModelGetGraphStarCount(log);

      //setting input string
      String input = "2 1 dmd\nqwerty\n3 1 7 2021-12-31\n2022-07-31\n";
      InputStream in = new ByteArrayInputStream(input.getBytes());

      //setting output variable and mock view.

      PortfolioView view = new MockViewMethodCallCheck(out);

      //creating controller object.
      PortfolioController controller = new Controller(in);
      controller.goController(mockModelCreateUser, view);
    } catch (RuntimeException e) {
      assertEquals("getGraphStarCount();loadingFile;[month, DECEMBER 2021, JANUARY 2022," +
              " FEBRUARY 2022, MARCH 2022, APRIL 2022, MAY 2022, JUNE 2022]", log.toString());
      assertEquals("showSelectPortfolioOptions()startUpMenu()userEnterUserID()" +
                      "userEnterPassword()welcomeUser()showPortfolioRecurringMenu()" +
                      "showListOfPortfolios();loadingFile;showSelectPortfolioMessage()" +
                      "showSelectedPortfolioOptions()showEnterFromDateInput()" +
                      "showEnterToDateInput()plotStarBarGraphMonth()showPortfolioRecurringMenu()",
              out.toString());

    }
  }


}
