package model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit test on the UserImpl class.
 */
public class AdvUserImplTest {

  private User createUserForTest(String userID) {
    return AdvUserImpl.getBuilder().setFirstName("Lazy")
            .setMiddleName("I")
            .setLastName("Fox")
            .setUserID(userID)
            .setPassword("qwerty").build();
  }

  /**
   * Tests creating a new user object using the builder class.
   */
  @Test
  public void testCreateNewUser() {
    User user = AdvUserImpl.getBuilder().setFirstName("Lazy")
            .setMiddleName("R")
            .setLastName("Fox")
            .setUserID("lrf")
            .setPassword("qwerty").build();
    assertEquals("Lazy", createUserForTest("lrf").getFirstName());
  }


  /**
   * Tests getFirstName method.
   */
  @Test
  public void testGetFirstNameMethod() {
    assertEquals("Lazy", createUserForTest("lrf2").getFirstName());
  }

  /**
   * Tests getMiddleName method.
   */
  @Test
  public void testGetMiddleNameMethod() {
    assertEquals("I", createUserForTest("lrf3").getMiddleName());
  }

  /**
   * Tests getMiddleName method.
   */
  @Test
  public void testGetLastNameMethod() {
    assertEquals("Fox", createUserForTest("lrf4").getLastName());
  }

  /**
   * Tests getUserID method.
   */
  @Test
  public void testGetUserIDMethod() {
    assertEquals("lrf5",
            createUserForTest("lrf5").getUserID());
  }

  /**
   * Tests getUserID method.
   */
  @Test
  public void testGetPasswordMethod() {
    assertEquals(Objects.hash("qwerty"), createUserForTest("lrf6").getPassword());
  }

  /**
   * Tests uploadPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testUploadPortfolioMethod_1() {
    Model model = new Model();
    model.login("qqq", "qwe", 2);
    String filepath = "./data/Adv_testBuyStock.xml";
    model.getCurrentUser().uploadPortfolio(filepath);

    Portfolio retrievedPortfolio = model.getCurrentUser().
            getPortfolio("Adv_testBuyStock");

    assertEquals("Adv_testBuyStock", retrievedPortfolio.getPortfolioName());
    assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
    assertEquals("[GOOG, TSLA]", retrievedPortfolio.getStockID().toString());
    assertEquals("[150.0, 650.0]", retrievedPortfolio.getStockQuantity().toString());
    assertEquals("[2019-10-10, 2019-10-10]",
            retrievedPortfolio.getTransactionDate().toString());
    assertEquals("[1208.67, 244.74]", retrievedPortfolio.getBuyPrice().toString());
    assertEquals("[181300.5, 159081.0]", retrievedPortfolio.getStockValue().toString());

  }

  /**
   * Tests uploadPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testUploadPortfolioMethod_2() {
    try {
      Model model = new Model();
      model.login("qqq", "qwe", 2);
      String filepath = "./data/Adv_testBuyStockTickerError.xml";
      model.getCurrentUser().uploadPortfolio(filepath);

      Portfolio retrievedPortfolio = model.getCurrentUser().
              getPortfolio("Adv_testBuyStockTickerError");

      assertEquals("Adv_testBuyStockTickerError", retrievedPortfolio.getPortfolioName());
      assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
      assertEquals("[GOOG, TSLA]", retrievedPortfolio.getStockID().toString());
      assertEquals("[150.0, 650.0]", retrievedPortfolio.getStockQuantity().toString());
      assertEquals("[2019-10-10, 2019-10-10]",
              retrievedPortfolio.getTransactionDate().toString());
      assertEquals("[1208.67, 244.74]", retrievedPortfolio.getBuyPrice().toString());
      assertEquals("[181300.5, 159081.0]", retrievedPortfolio.getStockValue().toString());
    } catch (IllegalArgumentException e) {
      assertEquals("StockID ticker is invalid.", e.getMessage());
    }
  }

  /**
   * Tests uploadPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testUploadPortfolioMethod_3() {
    try {
      Model model = new Model();
      model.login("lrf", "qwerty", 2);
      String filepath = "./data/Adv_testBuyStockQtyError.xml";
      model.getCurrentUser().uploadPortfolio(filepath);

      Portfolio retrievedPortfolio = model.getCurrentUser().
              getPortfolio("Adv_testBuyStockQtyError");

      assertEquals("Adv_testBuyStockQtyError", retrievedPortfolio.getPortfolioName());
      assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
      assertEquals("[GOOG, TSKA]", retrievedPortfolio.getStockID().toString());
      assertEquals("[150.0, 650.0]", retrievedPortfolio.getStockQuantity().toString());
      assertEquals("[2019-10-10, 2019-10-10]",
              retrievedPortfolio.getTransactionDate().toString());
      assertEquals("[1208.67, 244.74]", retrievedPortfolio.getBuyPrice().toString());
      assertEquals("[181300.5, 159081.0]", retrievedPortfolio.getStockValue().toString());
    } catch (IllegalArgumentException e) {
      assertEquals("Stock quantity entered is invalid.", e.getMessage());
    }
  }

  /**
   * Tests uploadPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testUploadPortfolioMethod_4() {
    try {
      Model model = new Model();
      model.login("lrf", "qwerty", 2);
      String filepath = "./data/Adv_testBuyStockErrorFormat.xml";
      model.getCurrentUser().uploadPortfolio(filepath);

      Portfolio retrievedPortfolio = model.getCurrentUser().
              getPortfolio("Adv_testBuyStockErrorFormat");

      assertEquals("Adv_testBuyStockErrorFormat", retrievedPortfolio.getPortfolioName());
      assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
      assertEquals("[GOOG, TSKA]", retrievedPortfolio.getStockID().toString());
      assertEquals("[150.0, 650.0]", retrievedPortfolio.getStockQuantity().toString());
      assertEquals("[2019-10-10, 2019-10-10]",
              retrievedPortfolio.getTransactionDate().toString());
      assertEquals("[1208.67, 244.74]", retrievedPortfolio.getBuyPrice().toString());
      assertEquals("[181300.5, 159081.0]", retrievedPortfolio.getStockValue().toString());
    } catch (IllegalArgumentException e) {
      assertEquals("Incorrect format of XML file.", e.getMessage());
    }
  }

}
