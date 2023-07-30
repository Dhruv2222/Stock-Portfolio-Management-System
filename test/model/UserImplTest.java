package model;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit test on the UserImpl class.
 */
public class UserImplTest {

  private User createUserForTest(String userID) {
    return UserImpl.getBuilder().setFirstName("Lazy")
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
    User user = UserImpl.getBuilder().setFirstName("Lazy")
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
   * Tests showPortfolios method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testShowPortfoliosMethod() {
    Model model = new Model();
    model.login("lrf", "qwerty", 1);
    Portfolio portfolio1 = PortfolioImpl.getBuilder().setPortfolioName(
            "testPortfolio1", model.getCurrentUser())
            .build();
    portfolio1.savePortfolio(model.getCurrentUser());
    Portfolio portfolio2 = PortfolioImpl.getBuilder().setPortfolioName(
            "testPortfolio2", model.getCurrentUser())
            .build();
    portfolio2.savePortfolio(model.getCurrentUser());
    Portfolio portfolio3 = PortfolioImpl.getBuilder().setPortfolioName(
            "testPortfolio3", model.getCurrentUser())
            .build();
    portfolio3.savePortfolio(model.getCurrentUser());
    Portfolio portfolio4 = PortfolioImpl.getBuilder().setPortfolioName(
            "testPortfolio4", model.getCurrentUser())
            .build();
    portfolio4.savePortfolio(model.getCurrentUser());
    assertEquals("[testPortfolio4, testPortfolio3, testPortfolio2, testPortfolio1]",
            model.getCurrentUser().showPortfolios().toString());
  }

  /**
   * Tests getPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testGetPortfolioMethod() {
    Model model = new Model();
    model.login("lrf", "qwerty", 1);
    Portfolio portfolio = PortfolioImpl.getBuilder().setPortfolioName("testPortfolio5",
                    model.getCurrentUser())
            .build();
    portfolio.setStockID("META");
    portfolio.setStockName("Meta Platforms");
    portfolio.setStockQuantity("350");
    portfolio.savePortfolio(model.getCurrentUser());

    Portfolio retrievedPortfolio = model.getCurrentUser().getPortfolio("testPortfolio5");

    assertEquals("testPortfolio5", retrievedPortfolio.getPortfolioName());
    assertEquals("[Meta Platforms]", retrievedPortfolio.getStockName().toString());
    assertEquals("[META]", retrievedPortfolio.getStockID().toString());
    assertEquals("[350]", retrievedPortfolio.getStockQuantity().toString());
  }

  /**
   * Tests uploadPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testUploadPortfolioMethod_1() {
    Model model = new Model();
    model.login("lrf", "qwerty", 1);
    String filepath = "./data/silver.xml";
    model.getCurrentUser().uploadPortfolio(filepath);

    Portfolio retrievedPortfolio = model.getCurrentUser().getPortfolio("silver");

    assertEquals("silver", retrievedPortfolio.getPortfolioName());
    assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
    assertEquals("[GOOG, TSLA]", retrievedPortfolio.getStockID().toString());
    assertEquals("[400.0, 350.0]", retrievedPortfolio.getStockQuantity().toString());
  }

  /**
   * Tests uploadPortfolio method.
   * Requires to run testCreateNewUser() test before this.
   */
  @Test
  public void testUploadPortfolioMethod_2() {
    try {
      Model model = new Model();
      model.login("lrf", "qwerty", 1);
      String filepath = "./data/silverErrorTicker.xml";
      model.getCurrentUser().uploadPortfolio(filepath);

      Portfolio retrievedPortfolio = model.getCurrentUser().getPortfolio("silverErrorTicker");

      assertEquals("silverError", retrievedPortfolio.getPortfolioName());
      assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
      assertEquals("[GOOG, TSKA]", retrievedPortfolio.getStockID().toString());
      assertEquals("[400.0, 350.0]", retrievedPortfolio.getStockQuantity().toString());
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
      model.login("lrf", "qwerty", 1);
      String filepath = "./data/silverErrorQty.xml";
      model.getCurrentUser().uploadPortfolio(filepath);

      Portfolio retrievedPortfolio = model.getCurrentUser().getPortfolio("silverErrorQty");

      assertEquals("silverError2", retrievedPortfolio.getPortfolioName());
      assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
      assertEquals("[GOOG, TSLA]", retrievedPortfolio.getStockID().toString());
      assertEquals("[400.0, 350.0]", retrievedPortfolio.getStockQuantity().toString());
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
      model.login("lrf", "qwerty", 1);
      String filepath = "./data/silverErrorFormat.xml";
      model.getCurrentUser().uploadPortfolio(filepath);

      Portfolio retrievedPortfolio = model.getCurrentUser().getPortfolio("silverErrorFormat");

      assertEquals("silverError2", retrievedPortfolio.getPortfolioName());
      assertEquals("[Google, Tesla]", retrievedPortfolio.getStockName().toString());
      assertEquals("[GOOG, TSLA]", retrievedPortfolio.getStockID().toString());
      assertEquals("[400.0, 350.0]", retrievedPortfolio.getStockQuantity().toString());
    } catch (IllegalArgumentException e) {
      assertEquals("Incorrect format of XML file.", e.getMessage());
    }
  }


}
