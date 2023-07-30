package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit tests on the PortfolioImpl class to verify its proper functioning.
 */
public class PortfolioImplTest {

  Portfolio portfolio;

  /**
   * Tests the creation of a new Portfolio object using PortfolioImpl using builder class.\
   */
  @Test
  public void testPortfolioImplBuilderClass() {
    User dummy = new UserImpl();
    portfolio = PortfolioImpl.getBuilder().setPortfolioName("testPortfolio", dummy)
            .build();
    assertEquals("testPortfolio", portfolio.getPortfolioName());
  }

  /**
   * Tests the getPortfolio method to verify name is set.
   */
  @Test
  public void testGetPortfolioName() {
    User dummy = new UserImpl();
    portfolio = PortfolioImpl.getBuilder().setPortfolioName("testPortfolio", dummy)
            .build();

    assertEquals("testPortfolio", portfolio.getPortfolioName());

  }

  /**
   * Tests the set and get stockID method.
   */
  @Test
  public void testSetAndGetStockID() {
    Model model = new Model();
    model.login("dmd", "qwerty", 1);
    portfolio = PortfolioImpl.getBuilder().setPortfolioName("testPortfolio",
                    model.getCurrentUser())
            .build();
    portfolio.setStockID("META");

    assertEquals("testPortfolio", portfolio.getPortfolioName());
    assertEquals("[META]", portfolio.getStockID().toString());

  }


  /**
   * Tests the set and get stockQuantity method.
   */
  @Test
  public void testSetAndGetStockName() {
    Model model = new Model();
    model.login("dmd", "qwerty", 1);
    portfolio = PortfolioImpl.getBuilder().setPortfolioName("testPortfolio",
                    model.getCurrentUser())
            .build();
    portfolio.setStockID("META");
    portfolio.setStockName("Meta Platforms");
    portfolio.setStockQuantity("350");

    assertEquals("testPortfolio", portfolio.getPortfolioName());
    assertEquals("[META]", portfolio.getStockID().toString());
    assertEquals("[Meta Platforms]", portfolio.getStockName().toString());
    assertEquals("[350]", portfolio.getStockQuantity().toString());


  }

  /**
   * Tests the set and get stockQuantity method when adding multiple stocks.
   */
  @Test
  public void testSetAndGetStockNameMultiple() {
    Model model = new Model();
    model.login("dmd", "qwerty", 1);
    portfolio = PortfolioImpl.getBuilder().setPortfolioName("testPortfolio",
                    model.getCurrentUser())
            .build();
    portfolio.setStockID("META");
    portfolio.setStockName("Meta Platforms");
    portfolio.setStockQuantity("350");
    portfolio.setStockID("AAPL");
    portfolio.setStockName("Apple Inc");
    portfolio.setStockQuantity("100");
    portfolio.setStockID("TSLA");
    portfolio.setStockName("Tesla Inc");
    portfolio.setStockQuantity("300");
    portfolio.setStockID("GOOG");
    portfolio.setStockName("Google Inc");
    portfolio.setStockQuantity("290");
    portfolio.setStockID("NFLX");
    portfolio.setStockName("Netflix");
    portfolio.setStockQuantity("120");

    assertEquals("testPortfolio", portfolio.getPortfolioName());
    assertEquals("[META, AAPL, TSLA, GOOG, NFLX]", portfolio.getStockID().toString());
    assertEquals("[Meta Platforms, Apple Inc, Tesla Inc, Google Inc, Netflix]",
            portfolio.getStockName().toString());
    assertEquals("[350, 100, 300, 290, 120]", portfolio.getStockQuantity().toString());


  }

}
