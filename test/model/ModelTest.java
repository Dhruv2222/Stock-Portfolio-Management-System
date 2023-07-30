package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class performs Junit tests on the Model class to verify its proper functioning.
 */
public class ModelTest {

  PortfolioModel model;
  StockMarketAPI api = new APIAlphaVantage();

  @Before
  public void setUp() {
    model = new Model();
  }

  /**
   * Tests the creation of new user object with valid inputs.
   */
  @Test
  public void testCreateUser_1() {
    model.createUser("Dhruv", "Doshi", "M",
            "dmd", "qwerty", 1, 0);
    assertEquals("Dhruv", model.getCurrentUser().getFirstName());
    assertEquals("Doshi", model.getCurrentUser().getLastName());
    assertEquals("M", model.getCurrentUser().getMiddleName());
    assertEquals("dmd", model.getCurrentUser().getUserID());
    assertEquals(Objects.hash("qwerty"), model.getCurrentUser().getPassword());
  }

  /**
   * Tests the creation of new user object with invalid inputs.
   * Password field is empty.
   */
  @Test
  public void testCreateUser_2() {
    try {
      model.createUser("Dhruv", "Doshi", "M",
              "dmd", "", 1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Password or UserID cannot be empty. Try again.", e.getMessage());
    }
  }

  /**
   * Tests the creation of new user object with invalid inputs.
   * UserID field is empty.
   */
  @Test
  public void testCreateUser_3() {
    try {
      model.createUser("Dhruv", "Doshi", "M", "",
              "qwerty", 1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Password or UserID cannot be empty. Try again.", e.getMessage());
    }
  }

  /**
   * Tests the creation of new user object with invalid inputs.
   * UserID already existing in the folder.
   * Requires running the testCreateUser_1 test first.
   */
  @Test
  public void testCreateUser_4() {
    try {
      model.createUser("Rahul", "Sharma", "K",
              "dmd", "poiuytrew", 1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("UserID already exists. Try again with different userID",
              e.getMessage());
    }
  }

  /**
   * Tests if existing user is able to login with correct credentials.
   * Requires running the testCreateUser_1 test first.
   */
  @Test
  public void testLoginUser_1() {
    model.login("dmd", "qwerty", 1);
    assertEquals("Dhruv", model.getCurrentUser().getFirstName());
    assertEquals("Doshi", model.getCurrentUser().getLastName());
    assertEquals("M", model.getCurrentUser().getMiddleName());
    assertEquals("dmd", model.getCurrentUser().getUserID());
    assertEquals(Objects.hash("qwerty"), model.getCurrentUser().getPassword());
  }

  /**
   * Tests if existing user is able to login with wrong credentials.
   * Requires running the testCreateUser_1 test first.
   */
  @Test
  public void testLoginUser_2() {
    try {
      model.login("dmd", "plmjuh", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid password entered. Run again.", e.getMessage());
    }
  }

  /**
   * Tests if existing user is able to login with wrong credentials.
   * Requires running the testCreateUser_1 test first.
   */
  @Test
  public void testLoginUser_3() {
    try {
      model.login("xyz", "plmjuh", 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("User does not exist.", e.getMessage());
    }
  }


  /**
   * Tests if new portfolio object is created using the createPortfolio method.
   */
  @Test
  public void testCreatePortfolio_1() {
    model.login("dmd", "qwerty", 1);
    model.createPortfolio("Golden stocks",
            model.getCurrentUser(), 1);
    assertEquals("Golden stocks", model.getWorkingPortfolio().getPortfolioName());
  }

  /**
   * Tests if new portfolio object is created using the createPortfolio method.
   * All inputs are valid.
   * Adding new stocks to the portfolio.
   */
  @Test
  public void testCreatePortfolio_2() {
    model.login("dmd", "qwerty", 1);
    model.createPortfolio("Golden stocks",
            model.getCurrentUser(), 1);
    assertEquals("Golden stocks", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());

    assertEquals("[GOOG, TSLA]", model.getWorkingPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla]", model.getWorkingPortfolio().getStockName().toString());
    assertEquals("[150.0, 650.0]", model.getWorkingPortfolio().getStockQuantity().toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

  }

  /**
   * Tests if new portfolio object is created using the createPortfolio method.
   * StockID input is invalid.
   * Adding new stocks to the portfolio.
   */
  @Test
  public void testCreatePortfolio_3() {
    model.login("dmd", "qwerty", 1);
    model.createPortfolio("Golden stockss",
            model.getCurrentUser(), 1);
    assertEquals("Golden stockss", model.getWorkingPortfolio().getPortfolioName());

    try {
      model.addStockInPortfolio("stockID", "RRRRRR", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
    } catch (IllegalArgumentException e) {
      assertEquals("StockID ticker is invalid.", e.getMessage());
    }

  }

  /**
   * Tests if new portfolio object is created using the createPortfolio method.
   * Portfolio name already exists in the user's folder.
   * Requires running the testCreatePortfolio_2 test first.
   */
  @Test
  public void testCreatePortfolio_4() {
    model.login("dmd", "qwerty", 1);
    try {
      model.createPortfolio("Golden stocks",
              model.getCurrentUser(), 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name already exists. Enter unique portfolio name",
              e.getMessage());
    }


  }


  /**
   * Tests if show portfolio displays the correct list of portfolios names for a user.
   */
  @Test
  public void testShowPortfolios_1() {
    model.login("dmd", "qwerty", 1);

    assertEquals("[Golden stocks]", model.getCurrentUser().showPortfolios()
            .toString());


  }

  /**
   * Tests if show portfolio displays the correct list of portfolios names for a user.
   */
  @Test
  public void testShowPortfolios_2() {
    model.login("d21", "qwe", 1);

    assertEquals("[testing, NewNew, tesing new, GoldStocks, new port, xyz, Bees, " +
            "NewPort, nifty]", model.getCurrentUser().showPortfolios().toString());


  }


  /**
   * Tests if the method select portfolio selects the correct portfolio and returns the correct
   * portfolio.
   */
  @Test
  public void testSelectPortfolio_1() {
    model.login("dmd", "qwerty", 1);

    Portfolio res = model.getCurrentUser().getPortfolio("Golden stocks");

    assertEquals("Golden stocks", res.getPortfolioName());
    assertEquals("[GOOG, TSLA]", res.getStockID().toString());
    assertEquals("[Google, Tesla]", res.getStockName().toString());
    assertEquals("[150.0, 650.0]", res.getStockQuantity().toString());


  }


  /**
   * Tests if total values of the portfolio is returned when totalInvestment method is called.
   * By default, the total value will be calculated on the previous day's closing value.
   */
  @Test
  public void testShowTotalInvestment_1() {
    model.login("dmd", "qwerty", 1);

    Portfolio res = model.getCurrentUser().getPortfolio("Golden stocks");
    List<Double> prices = new ArrayList<>();
    for (String i : res.getStockID()) {

      prices.add(api.getPrice(i, LocalDate.parse("2022-10-31")));
    }

    assertEquals("162100.0", model.showTotalInvestment(res, prices) + "");
  }

  /**
   * Tests if new portfolio object is created using the createPortfolio method.
   * Stock quantity entered is not valid.
   */
  @Test
  public void testCreatePortfolio_5() {
    model.login("dmd", "qwerty", 1);
    model.createPortfolio("Silver stocks",
            model.getCurrentUser(), 1);
    assertEquals("Silver stocks", model.getWorkingPortfolio().getPortfolioName());

    try {
      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "xyz", model.getWorkingPortfolio());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock quantity entered is invalid.", e.getMessage());
    }
  }

  /**
   * Tests if new portfolio object is created using the createPortfolio method.
   * Stock quantity entered is not valid.
   */
  @Test
  public void testCreatePortfolio_6() {
    model.login("dmd", "qwerty", 1);
    model.createPortfolio("Silver stocks",
            model.getCurrentUser(), 1);
    assertEquals("Silver stocks", model.getWorkingPortfolio().getPortfolioName());

    try {
      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "-20", model.getWorkingPortfolio());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock quantity entered is invalid.", e.getMessage());
    }

  }


  /**
   * Tests if list of prices on a given date is returned correctly.
   */
  @Test
  public void testGetStockPricesOnDate_1() {
    model.login("dmd", "qwerty", 1);

    Portfolio res = model.getCurrentUser().getPortfolio("Golden stocks");
    List<Double> prices = new ArrayList<>();

    assertEquals("[94.66, 227.54]", model.getStockPricesOnDate(LocalDate
            .parse("2022-10-31"), res).toString());

  }

  /**
   * Tests if fractional stock values are allowed to be entered.
   */
  @Test
  public void testGetStockPricesOnDate_3() {
    model.login("dmd", "qwerty", 1);

    Portfolio res = model.getCurrentUser().getPortfolio("Golden stocks");
    List<Double> prices = new ArrayList<>();

    assertEquals("[94.66, 227.54]", model.getStockPricesOnDate(LocalDate
            .parse("2022-10-31"), res).toString());

  }

  /**
   * Tests if list of prices on a given date is returned correctly.
   * 0 will be returned for a stock price if the date entered is a stock market holiday.
   */
  @Test
  public void testGetStockPricesOnDate_2() {
    model.login("dmd", "qwerty", 1);

    Portfolio res = model.getCurrentUser().getPortfolio("Golden stocks");
    List<Double> prices = new ArrayList<>();
    assertEquals("[0.0, 0.0]", model.getStockPricesOnDate(LocalDate
            .parse("2022-01-17"), res).toString());

  }

  @Test
  public void testBuyStock() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testBuyStock", model.getCurrentUser(), 2);

    assertEquals("testBuyStock", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    assertEquals("[GOOG, TSLA]",
            model.getWorkingPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla]",
            model.getWorkingPortfolio().getStockName().toString());
    assertEquals("[150.0, 650.0]",
            model.getWorkingPortfolio().getStockQuantity().toString());
    assertEquals("[1208.67, 244.74]",
            model.getWorkingPortfolio().getBuyPrice().toString());
    assertEquals("[181300.5, 159081.0]",
            model.getWorkingPortfolio().getStockValue().toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
  }

  @Test
  public void testBuyStockInvalidDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testBuyStockInvalidDate",
              model.getCurrentUser(), 2);

      assertEquals("testBuyStockInvalidDate",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-12",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date. Stock market closed on this date.", e.getMessage());
    }
  }

  @Test
  public void testBuyStockInvalidQuantity() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testBuyStockInvalidQuantity",
              model.getCurrentUser(), 2);

      assertEquals("testBuyStockInvalidQuantity",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "xyz", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Stock quantity entered is invalid.", e.getMessage());
    }
  }

  @Test
  public void testBuyStockInvalidStockTicker() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testBuyStockInvalidStockTicker",
              model.getCurrentUser(), 2);

      assertEquals("testBuyStockInvalidStockTicker",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "ABCD", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "100", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("StockID ticker is invalid.", e.getMessage());
    }
  }

  @Test
  public void testBuyStockInvalidStockQuantity() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testBuyStockInvalidStockQuantity",
              model.getCurrentUser(), 2);

      assertEquals("testBuyStockInvalidStockQuantity",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "-100", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Stock quantity entered is invalid.", e.getMessage());
    }
  }

  @Test
  public void testBuyStockInvalidStockQuantity1() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testBuyStockInvalidStockQuantity1",
              model.getCurrentUser(), 2);

      assertEquals("testBuyStockInvalidStockQuantity1",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "xyz", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Stock quantity entered is invalid.", e.getMessage());
    }
  }

  @Test
  public void testBuyStockStockQuantity0() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testBuyStockStockQuantity0",
            model.getCurrentUser(), 2);

    assertEquals("testBuyStockStockQuantity0",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "0", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());
    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[GOOG, TSLA]",
            model.getWorkingPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla]",
            model.getWorkingPortfolio().getStockName().toString());
    assertEquals("[0.0, 650.0]",
            model.getWorkingPortfolio().getStockQuantity().toString());
    assertEquals("[1208.67, 244.74]",
            model.getWorkingPortfolio().getBuyPrice().toString());
    assertEquals("[0.0, 159081.0]",
            model.getWorkingPortfolio().getStockValue().toString());
  }

  @Test
  public void testBuyStockInvalidDateFormat() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testBuyStockInvalidDateFormat",
              model.getCurrentUser(), 2);

      assertEquals("testBuyStockInvalidDateFormat",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "0", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "abcdefg",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date entered.", e.getMessage());
    }
  }

  @Test
  public void testSellStock() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testSellStock", model.getCurrentUser(), 2);

    assertEquals("testSellStock", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", 50.0, "2020-10-12");

    assertEquals("[GOOG, TSLA, GOOG]",
            model.getWorkingPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla, Google]",
            model.getWorkingPortfolio().getStockName().toString());
    assertEquals("[150.0, 650.0, -50.0]",
            model.getWorkingPortfolio().getStockQuantity().toString());
    assertEquals("[1208.67, 244.74, 1569.15]",
            model.getWorkingPortfolio().getBuyPrice().toString());
    assertEquals("[181300.5, 159081.0, -78457.5]",
            model.getWorkingPortfolio().getStockValue().toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
  }

  @Test
  public void testSellStockInvalidDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSellStock1", model.getCurrentUser(), 2);

      assertEquals("testSellStock1", model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "GOOG", 50.0, "2020-10-10");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date. Stock market closed on this date.", e.getMessage());
    }
  }

  @Test
  public void testSellStockNegativeQuantity() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testSellStock1", model.getCurrentUser(), 2);

    assertEquals("testSellStock1", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", -50.0, "2020-10-12");
    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[GOOG, TSLA, GOOG]",
            model.getWorkingPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla, Google]",
            model.getWorkingPortfolio().getStockName().toString());
    assertEquals("[150.0, 650.0, -50.0]",
            model.getWorkingPortfolio().getStockQuantity().toString());
    assertEquals("[1208.67, 244.74, 1569.15]",
            model.getWorkingPortfolio().getBuyPrice().toString());
    assertEquals("[181300.5, 159081.0, -78457.5]",
            model.getWorkingPortfolio().getStockValue().toString());
  }

  @Test
  public void testSellStockInvalidQuantity() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSellStock2", model.getCurrentUser(), 2);

      assertEquals("testSellStock2", model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "GOOG", 5000.0, "2020-10-12");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough quantity available.", e.getMessage());
    }
  }

  @Test
  public void testSellInvalidStock() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSellStock3", model.getCurrentUser(), 2);

      assertEquals("testSellStock3", model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "A", 100.0, "2020-10-12");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough quantity available.", e.getMessage());
    }
  }

  @Test
  public void testSellInvalidStockTicker() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSellStock5", model.getCurrentUser(), 2);

      assertEquals("testSellStock5", model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(
              model.getWorkingPortfolio(), "abcdefg", 100.0, "2020-10-12");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough quantity available.", e.getMessage());
    }
  }

  @Test
  public void testSellInvalidDateFormat() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSellStock4", model.getCurrentUser(), 2);

      assertEquals("testSellStock4", model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "150", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "xyz",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "A", 100.0, "2020-10-10");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough quantity available.", e.getMessage());
    }
  }

  @Test
  public void testSellStock0() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testSellStockQuantity0",
            model.getCurrentUser(), 2);

    assertEquals("testSellStockQuantity0", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "100", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "650", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", 0.0, "2020-10-13");
    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[GOOG, TSLA, GOOG]",
            model.getWorkingPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla, Google]",
            model.getWorkingPortfolio().getStockName().toString());
    assertEquals("[100.0, 650.0, 0.0]",
            model.getWorkingPortfolio().getStockQuantity().toString());
    assertEquals("[1208.67, 244.74, 1571.68]",
            model.getWorkingPortfolio().getBuyPrice().toString());
    assertEquals("[120867.0, 159081.0, 0.0]",
            model.getWorkingPortfolio().getStockValue().toString());
  }

  @Test
  public void testShowComposition() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testComposition", model.getCurrentUser(), 2);

    assertEquals("testComposition", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "250", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2020-10-12");
    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2020-10-13"));

    assertEquals("[GOOG, TSLA]",
            model.getConsolidatedPortfolio().getStockID().toString());
    assertEquals("[Google, Tesla]",
            model.getConsolidatedPortfolio().getStockName().toString());
    assertEquals("[150.0, 750.0]",
            model.getConsolidatedPortfolio().getStockQuantity().toString());

  }

  @Test
  public void testShowCompositionWhenBuy0Stocks() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testShowCompositionWhenBuy0Stocks",
            model.getCurrentUser(), 2);

    assertEquals("testShowCompositionWhenBuy0Stocks",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "0", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2020-10-13"));

    assertEquals("[TSLA]",
            model.getConsolidatedPortfolio().getStockID().toString());
    assertEquals("[Tesla]",
            model.getConsolidatedPortfolio().getStockName().toString());
    assertEquals("[750.0]",
            model.getConsolidatedPortfolio().getStockQuantity().toString());

  }

  @Test
  public void testShowCompositionInvalidDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testCompositionInvalidDate",
              model.getCurrentUser(), 2);

      assertEquals("testCompositionInvalidDate",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "250", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2020-10-12");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2020-10-17"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date. Stock market closed on this date.", e.getMessage());
    }
  }

  @Test
  public void testGetCostBasis() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testCostBasis", model.getCurrentUser(), 2);

    assertEquals("testCostBasis", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "300", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "800", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals(475015.0, model.getCostBasis(LocalDate.parse("2020-10-13"),
            model.getWorkingPortfolio()), 0);
  }

  @Test
  public void testGetCostBasisWhenBuyStocks0() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGetCostBasisWhenBuyStocks0",
            model.getCurrentUser(), 2);

    assertEquals("testGetCostBasisWhenBuyStocks0",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "0", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "800", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals(112414.0, model.getCostBasis(LocalDate.parse("2022-11-04"),
            model.getWorkingPortfolio()), 0);
  }

  @Test
  public void testGetCostBasisInvalidDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testCostBasisInvalidDate",
              model.getCurrentUser(), 2);

      assertEquals("testCostBasisInvalidDate",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "300", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "800", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

      model.getCostBasis(LocalDate.parse("2020-10-17"), model.getWorkingPortfolio());
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date. Stock market closed on this date.", e.getMessage());
    }
  }

  @Test
  public void testGetTotalValueOnDate() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGetTotalValueOnDate",
            model.getCurrentUser(), 2);

    assertEquals("testGetTotalValueOnDate", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "250", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2020-10-12");
    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals(570754.5, model.getTotalValueOnDate(LocalDate.parse("2020-10-13"),
            model.getWorkingPortfolio()), 0);
  }

  @Test
  public void testGetTotalValueOnInvalidDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testGetTotalValueOnInvalidDate",
              model.getCurrentUser(), 2);

      assertEquals("testGetTotalValueOnInvalidDate",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "250", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2019-10-10",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2020-10-12");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
      model.getTotalValueOnDate(LocalDate.parse("2020-10-17"), model.getWorkingPortfolio());

    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date. Stock market closed on this date.", e.getMessage());
    }
  }

  @Test
  public void testGetTotalValueOnDateWhenBuyStocks0() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGetTotalValueOnDateWhenBuyStocks0",
            model.getCurrentUser(), 2);

    assertEquals("testGetTotalValueOnDateWhenBuyStocks0",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "0", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2019-10-10",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals(334997.5, model.getTotalValueOnDate(LocalDate.parse("2020-10-13"),
            model.getWorkingPortfolio()), 0);
  }

  @Test
  public void testBuySellOnSameDate() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testBuySellOnSameDate",
            model.getCurrentUser(), 2);

    assertEquals("testBuySellOnSameDate", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "100", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "750", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2022-11-03");
    model.sellStock(model.getWorkingPortfolio(), "TSLA", 750.0, "2022-11-03");

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals(0.0, model.getTotalValueOnDate(LocalDate.parse("2022-11-03"),
            model.getWorkingPortfolio()), 0);
  }

  @Test
  public void testSaveAdvPortfolio() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testSaveAdvPortfolio",
            model.getCurrentUser(), 2);

    assertEquals("testSaveAdvPortfolio", model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-04",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
  }

  @Test
  public void testSaveAdvPortfolioInvalidDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSaveAdvPortfolioInvalidDate",
              model.getCurrentUser(), 2);

      assertEquals("testSaveAdvPortfolioInvalidDate",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-11-05",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-11-04",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date. Stock market closed on this date.", e.getMessage());
    }

  }

  @Test
  public void testSaveAdvPortfolioSellStocks() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testSaveAdvPortfolioSellStocks",
            model.getCurrentUser(), 2);

    assertEquals("testSaveAdvPortfolioSellStocks",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2022-11-04");

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
  }

  @Test
  public void testSellOnPreviousDate() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testSellOnPreviousDate",
              model.getCurrentUser(), 2);

      assertEquals("testSellOnPreviousDate",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-11-04",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-11-04",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.sellStock(model.getWorkingPortfolio(), "GOOG", 100.0, "2022-11-02");

      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough quantity available.", e.getMessage());
    }

  }

  @Test
  public void testShowPortfolio() {
    model.login("qqq", "qwe", 2);

    assertEquals("[bb, loadingFile, loadingFileError, newnew, newPort, plm, " +
            "testBuySellOnSameDate, testBuyStock, testBuyStockStockQuantity0, testComposition, " +
            "testCompositionInvalidDate, testCostBasis, testCostBasisInvalidDate, tester, " +
            "testGetCostBasisWhenBuyStocks0, testGetTotalValueOnDate, " +
            "testGetTotalValueOnDateWhenBuyStocks0, testGetTotalValueOnInvalidDate, testGraph, " +
            "testingAbstraction, testingOrder, testSaveAdvPortfolio, " +
            "testSaveAdvPortfolioSellStocks, testSellStock, testSellStockQuantity0, " +
            "testShowCompositionWhenBuy0Stocks, trial]", model.getCurrentUser().showPortfolios()
            .toString());
  }

  @Test
  public void testGraphAnalysis() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGraphAnalysis",
            model.getCurrentUser(), 2);

    assertEquals("testGraphAnalysis",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-10-31",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[3310, 0, 0, 0, 0, 1, 1, 1, 50]",
            model.getGraphStarCount(model.getWorkingPortfolio(),
                    model.getGraphTimestamp("2022-10-28", "2022-11-04")).toString());
  }

  @Test
  public void testGraphTimestampDays() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGraphTimestampDays",
            model.getCurrentUser(), 2);

    assertEquals("testGraphTimestampDays",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-10-31",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[day, 2022-10-28, 2022-10-29, 2022-10-30, 2022-10-31, 2022-11-01, " +
                    "2022-11-02, 2022-11-03, 2022-11-04]",
            model.getGraphTimestamp("2022-10-28", "2022-11-04").toString());
  }

  @Test
  public void testGraphTimestampInvalid() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testGraphTimestampInvalid",
              model.getCurrentUser(), 2);

      assertEquals("testGraphTimestampInvalid",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-10-31",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-11-03",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

      model.getGraphTimestamp("2022-10-10", "2022-10-11");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Interval entered is too small.", e.getMessage());
    }
  }

  @Test
  public void testGraphTimestampInvalid1() {
    try {
      model.login("qqq", "qwe", 2);
      model.createPortfolio("testGraphTimestampInvalid1",
              model.getCurrentUser(), 2);

      assertEquals("testGraphTimestampInvalid1",
              model.getWorkingPortfolio().getPortfolioName());

      model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-10-31",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
      model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
      model.addStockInPortfolio("transactionDate", "2022-11-03",
              model.getWorkingPortfolio());
      model.addStockInPortfolio("setBuyingPriceAndValue", "",
              model.getWorkingPortfolio());

      model.getGraphTimestamp("1990-10-10", "2022-10-11");
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    } catch (IllegalArgumentException e) {
      assertEquals("Interval entered is too big.", e.getMessage());
    }
  }

  @Test
  public void testGraphTimestampMonths() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGraphTimestampMonths",
            model.getCurrentUser(), 2);

    assertEquals("testGraphTimestampMonths",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-03-01",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[month, APRIL 2022, MAY 2022, JUNE 2022, JULY 2022, " +
                    "AUGUST 2022, SEPTEMBER 2022]",
            model.getGraphTimestamp("2022-04-28", "2022-11-04").toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

  }

  @Test
  public void testGraphTimestampYears() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGraphTimestampYears",
            model.getCurrentUser(), 2);

    assertEquals("testGraphTimestampYears",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2015-03-02",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[year, 2015, 2016, 2017, 2018, 2019, 2020, 2021]",
            model.getGraphTimestamp("2015-03-01", "2022-11-04").toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

  }

  @Test
  public void testGraphTimestampDaysGreaterThan30MonthsLessThan5() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGraphTimestampDaysGreaterThan30MonthsLessThan5",
            model.getCurrentUser(), 2);

    assertEquals("testGraphTimestampDaysGreaterThan30MonthsLessThan5",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2015-03-02",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[day, 2022-10-03, 2022-10-09, 2022-10-15, 2022-10-21, 2022-10-27, " +
                    "2022-11-02, 2022-11-08, 2022-11-14]",
            model.getGraphTimestamp("2022-10-03", "2022-11-15").toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

  }

  @Test
  public void testGraphTimestampMonthsGreaterThan30YearsLessThan5() {
    model.login("qqq", "qwe", 2);
    model.createPortfolio("testGraphTimestampMonthsGreaterThan30YearsLessThan5",
            model.getCurrentUser(), 2);

    assertEquals("testGraphTimestampMonthsGreaterThan30YearsLessThan5",
            model.getWorkingPortfolio().getPortfolioName());

    model.addStockInPortfolio("stockID", "GOOG", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Google", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "120", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2015-03-02",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.addStockInPortfolio("stockID", "TSLA", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", "Tesla", model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", "780", model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", "2022-11-03",
            model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

    assertEquals("[year, 2015, 2016, 2017]",
            model.getGraphTimestamp("2015-04-01", "2018-04-04").toString());

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());

  }


  @Test
  public void testInvestFixedAmount_1() {
    model.login("d21", "qwe", 2);

    model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

    assertEquals("testInvestFixedAmount_1",
            model.getWorkingPortfolio().getPortfolioName());

    Map<String, String> ratios = new HashMap<>();
    ratios.put("GOOG", 20.0 + "");
    ratios.put("AMZN", 30.0 + "");
    ratios.put("TSLA", 50.0 + "");

    Map<String, String> iDvsName = new HashMap<>();
    iDvsName.put("GOOG", "Google");
    iDvsName.put("AMZN", "Amazon");
    iDvsName.put("TSLA", "Tesla");

    model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2022-11-12"));

  }

  @Test
  public void testInvestFixedAmountNegativeRatio() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", -20.0 + "");
      ratios.put("AMZN", 30.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");

      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2022-11-12"));
    } catch (IllegalArgumentException e) {
      assertEquals("Ratio cannot be negative.", e.getMessage());
    }

  }

  @Test
  public void testInvestFixedAmountInvalidRatio() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", 20.0 + "");
      ratios.put("AMZN", 50.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");

      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2022-11-12"));
    } catch (IllegalArgumentException e) {
      assertEquals("Sum of ratios should add up to 100.", e.getMessage());
    }
  }

  @Test
  public void testInvestFixedAmountInvalidRatio1() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", 20.2 + "");
      ratios.put("AMZN", 30.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid ratio entered.", e.getMessage());
    }


  }

  @Test
  public void testInvestFixedAmountInvalidRatioSumLessThan100() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", 10.0 + "");
      ratios.put("AMZN", 30.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");
    } catch (IllegalArgumentException e) {
      assertEquals("Sum of ratios should add up to 100.", e.getMessage());
    }
  }

  @Test
  public void testInvestFixedAmountFutureDate() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", 20.0 + "");
      ratios.put("AMZN", 30.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");

      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2032-11-12"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be in future.", e.getMessage());
    }
  }

  @Test
  public void testInvestFixedAmountInvalidDate() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", 20.0 + "");
      ratios.put("AMZN", 30.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");

      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2022-11-27"));
    } catch (IllegalArgumentException e) {
      assertEquals("Incorrect date entered", e.getMessage());
    }
  }

  @Test
  public void testInvestFixedAmountValidAmount() {
    model.login("d21", "qwe", 2);

    model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

    assertEquals("testInvestFixedAmount_1",
            model.getWorkingPortfolio().getPortfolioName());

    Map<String, String> ratios = new HashMap<>();
    ratios.put("GOOG", 20.0 + "");
    ratios.put("AMZN", 30.0 + "");
    ratios.put("TSLA", 50.0 + "");

    Map<String, String> iDvsName = new HashMap<>();
    iDvsName.put("GOOG", "Google");
    iDvsName.put("AMZN", "Amazon");
    iDvsName.put("TSLA", "Tesla");

    model.startNewStrategy();

    model.collectTickerRatios("GOOG", 20.0 + "");
    model.collectTickerNames("GOOG", "Google");
    model.collectTickerRatios("AMZN", 30.0 + "");
    model.collectTickerNames("AMZN", "Amazon");
    model.collectTickerRatios("TSLA", 50.0 + "");
    model.collectTickerNames("TSLA", "Tesla");


    model.investFixedAmount("2000", "2022-11-11");

    model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
    model.getConsolidatedPortfolio();
    model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2022-11-12"));

  }

  @Test
  public void testInvestFixedInvalidAmount() {
    try {
      model.login("d21", "qwe", 2);

      model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

      assertEquals("testInvestFixedAmount_1",
              model.getWorkingPortfolio().getPortfolioName());

      Map<String, String> ratios = new HashMap<>();
      ratios.put("GOOG", 20.0 + "");
      ratios.put("AMZN", 30.0 + "");
      ratios.put("TSLA", 50.0 + "");

      Map<String, String> iDvsName = new HashMap<>();
      iDvsName.put("GOOG", "Google");
      iDvsName.put("AMZN", "Amazon");
      iDvsName.put("TSLA", "Tesla");

      model.startNewStrategy();

      model.collectTickerRatios("GOOG", 20.0 + "");
      model.collectTickerNames("GOOG", "Google");
      model.collectTickerRatios("AMZN", 30.0 + "");
      model.collectTickerNames("AMZN", "Amazon");
      model.collectTickerRatios("TSLA", 50.0 + "");
      model.collectTickerNames("TSLA", "Tesla");


      model.investFixedAmount("-2000", "2022-11-11");

      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
      model.getConsolidatedPortfolio();
      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse("2022-11-12"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid amount entered.", e.getMessage());
    }

  }

  @Test
  public void testInvestFixedAmountCostBasisMultipleDates() {
    model.login("d21", "qwe", 2);

    model.createPortfolio("testInvestFixedAmountCostBasisMultipleDates",
            model.getCurrentUser(), 2);

    assertEquals("testInvestFixedAmountCostBasisMultipleDates",
            model.getWorkingPortfolio().getPortfolioName());

    Map<String, String> ratios = new HashMap<>();
    ratios.put("GOOG", 20.0 + "");
    ratios.put("AMZN", 30.0 + "");
    ratios.put("TSLA", 50.0 + "");

    Map<String, String> iDvsName = new HashMap<>();
    iDvsName.put("GOOG", "Google");
    iDvsName.put("AMZN", "Amazon");
    iDvsName.put("TSLA", "Tesla");

    model.startNewStrategy();

    model.collectTickerRatios("GOOG", 20.0 + "");
    model.collectTickerNames("GOOG", "Google");
    model.collectTickerRatios("AMZN", 30.0 + "");
    model.collectTickerNames("AMZN", "Amazon");
    model.collectTickerRatios("TSLA", 50.0 + "");
    model.collectTickerNames("TSLA", "Tesla");


    model.investFixedAmount("2000", "2021-06-05");

    assertEquals(2000.0, model.getCostBasis(LocalDate.parse("2021-06-08"),
            model.getWorkingPortfolio()), 0);
    assertEquals(2000.0, model.getCostBasis(LocalDate.parse("2021-06-10"),
            model.getWorkingPortfolio()), 0);
    assertEquals(2000.0, model.getCostBasis(LocalDate.parse("2022-06-10"),
            model.getWorkingPortfolio()), 0);
    assertEquals(2000.0, model.getCostBasis(LocalDate.parse("2022-11-12"),
            model.getWorkingPortfolio()), 0);

  }

  @Test
  public void testInvestFixedAmountTotalValueMultipleDates() {
    model.login("d21", "qwe", 2);

    model.selectPortfolio(model.getCurrentUser(), "testInvestFixedAmount_1");

    assertEquals("testInvestFixedAmount_1",
            model.getWorkingPortfolio().getPortfolioName());

    Map<String, String> ratios = new HashMap<>();
    ratios.put("GOOG", 20.0 + "");
    ratios.put("AMZN", 30.0 + "");
    ratios.put("TSLA", 50.0 + "");

    Map<String, String> iDvsName = new HashMap<>();
    iDvsName.put("GOOG", "Google");
    iDvsName.put("AMZN", "Amazon");
    iDvsName.put("TSLA", "Tesla");

    model.startNewStrategy();

    model.collectTickerRatios("GOOG", 20.0 + "");
    model.collectTickerNames("GOOG", "Google");
    model.collectTickerRatios("AMZN", 30.0 + "");
    model.collectTickerNames("AMZN", "Amazon");
    model.collectTickerRatios("TSLA", 50.0 + "");
    model.collectTickerNames("TSLA", "Tesla");


    model.investFixedAmount("2000", "2021-06-05");

    assertEquals(0, model.getTotalValueOnDate(LocalDate.parse("2021-06-04"),
            model.getWorkingPortfolio()), 0);
    assertEquals(2012.5750278263272,
            model.getTotalValueOnDate(LocalDate.parse("2021-06-08"),
                    model.getWorkingPortfolio()), 0);
    assertEquals(578180.1741991284, model.getTotalValueOnDate(LocalDate.parse("2022-11-12"),
            model.getWorkingPortfolio()), 0);

  }

  @Test
  public void testDollarCostAveragingCostBasisMultipleDates() {
    model.login("d21", "qwe", 2);

    model.createPortfolio("testDollarCostAveraging",
            model.getCurrentUser(), 2);

    assertEquals("testDollarCostAveraging",
            model.getWorkingPortfolio().getPortfolioName());

    Map<String, String> ratios = new HashMap<>();
    ratios.put("GOOG", 20.0 + "");
    ratios.put("AMZN", 30.0 + "");
    ratios.put("TSLA", 50.0 + "");

    Map<String, String> iDvsName = new HashMap<>();
    iDvsName.put("GOOG", "Google");
    iDvsName.put("AMZN", "Amazon");
    iDvsName.put("TSLA", "Tesla");

    model.startNewStrategy();

    model.collectTickerRatios("GOOG", 20.0 + "");
    model.collectTickerNames("GOOG", "Google");
    model.collectTickerRatios("AMZN", 30.0 + "");
    model.collectTickerNames("AMZN", "Amazon");
    model.collectTickerRatios("TSLA", 50.0 + "");
    model.collectTickerNames("TSLA", "Tesla");


    model.startToEndInvest("2000", "2", "days", "2021-06-06",
            "2021-06-30", false);

    assertEquals(0, model.getCostBasis(LocalDate.parse("2021-06-05"),
            model.getWorkingPortfolio()), 0);
    assertEquals(2000.0, model.getCostBasis(LocalDate.parse("2021-06-07"),
            model.getWorkingPortfolio()), 0);
    assertEquals(4000.0, model.getCostBasis(LocalDate.parse("2021-06-09"),
            model.getWorkingPortfolio()), 0);
    assertEquals(6000.0, model.getCostBasis(LocalDate.parse("2021-06-11"),
            model.getWorkingPortfolio()), 0);
  }

  @Test
  public void testDollarCostAveragingTotalValueMultipleDates() {
    model.login("d21", "qwe", 2);

    model.createPortfolio("testDollarCostAveragingTotalVal",
            model.getCurrentUser(), 2);

    assertEquals("testDollarCostAveragingTotalVal",
            model.getWorkingPortfolio().getPortfolioName());

    Map<String, String> ratios = new HashMap<>();
    ratios.put("GOOG", 20.0 + "");
    ratios.put("AMZN", 30.0 + "");
    ratios.put("TSLA", 50.0 + "");

    Map<String, String> iDvsName = new HashMap<>();
    iDvsName.put("GOOG", "Google");
    iDvsName.put("AMZN", "Amazon");
    iDvsName.put("TSLA", "Tesla");

    model.startNewStrategy();

    model.collectTickerRatios("GOOG", 20.0 + "");
    model.collectTickerNames("GOOG", "Google");
    model.collectTickerRatios("AMZN", 30.0 + "");
    model.collectTickerNames("AMZN", "Amazon");
    model.collectTickerRatios("TSLA", 50.0 + "");
    model.collectTickerNames("TSLA", "Tesla");


    model.startToEndInvest("2000", "2", "days", "2021-06-06",
            "2021-06-30", false);

    assertEquals(0, model.getTotalValueOnDate(LocalDate.parse("2021-06-05"),
            model.getWorkingPortfolio()), 0);
    assertEquals(2000.0, model.getTotalValueOnDate(LocalDate.parse("2021-06-07"),
            model.getWorkingPortfolio()), 0);
    assertEquals(4005.7508325306744, model.getTotalValueOnDate(LocalDate.parse("2021-06-09"),
            model.getWorkingPortfolio()), 0);
    assertEquals(6072.098121111077, model.getTotalValueOnDate(LocalDate.parse("2021-06-11"),
            model.getWorkingPortfolio()), 0);
  }

}
