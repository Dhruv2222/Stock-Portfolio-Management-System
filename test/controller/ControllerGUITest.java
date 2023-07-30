package controller;

import org.junit.Test;

import model.Model;
import model.Portfolio;
import model.User;
import view.JFrameView;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit tests on the ControllerGUI class in isolation.
 */
public class ControllerGUITest {

  Model mockModel;

  private static class MockModelCreateUser extends Model {
    private final StringBuilder log;

    public MockModelCreateUser(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void createUser(String firstName, String lastName, String middleName, String userID,
                           String password, int portfolioType, double commissionFee) {
      String temp = firstName + ";" + lastName + ";" + middleName + ";" + userID + ";"
              + password + ";" + portfolioType + ";" + commissionFee;
      log.append("createUser();");
      log.append(temp);

    }

  }

  private static class MockModelLoginUser extends Model {
    private final StringBuilder log;

    public MockModelLoginUser(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void login(String userID, String password, int portfolioType) {
      String temp = userID + ";" + password + ";" + portfolioType;
      log.append("login();");
      log.append(temp);

    }

  }

  private static class MockModelCreatePortfolio extends Model {
    private final StringBuilder log;

    public MockModelCreatePortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void createPortfolio(String portfolioName, User currentUser, int portfolioType) {
      String temp = portfolioName + ";" + currentUser.getUserID() + ";" + portfolioType;
      log.append("createPortfolio();");
      log.append(temp);

    }

  }

  private static class MockModelSavePortfolio extends Model {
    private final StringBuilder log;

    public MockModelSavePortfolio(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void savePortfolio(Portfolio newPortfolio, User currentUser) {
      String temp = newPortfolio.getPortfolioName() + ";" + currentUser.getUserID();
      log.append("savePortfolio();");
      log.append(temp);

    }

  }


  @Test
  public void testCreateUser() {
    StringBuilder log = new StringBuilder();
    mockModel = new MockModelCreateUser(log);

    IControllerGUI controller = new ControllerGUI(mockModel, new JFrameView());

    try {
      controller.createUser("dhruv", "doshi", "M", "dmd",
              "qwerty", "10");
      assertEquals("createUser();dhruv;doshi;M;dmd;qwerty;0;10.0",
              log.toString());
    } catch (Exception e) {
      //pass
    }

  }

  @Test
  public void testLoginUser() {
    StringBuilder log = new StringBuilder();
    mockModel = new MockModelLoginUser(log);

    IControllerGUI controller = new ControllerGUI(mockModel, new JFrameView());

    try {
      controller.login("dmd", "qwerty");
      assertEquals("login();dmd;qwerty;0",
              log.toString());
    } catch (Exception e) {
      //pass
    }

  }

  @Test
  public void testCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    mockModel = new MockModelCreatePortfolio(log);

    IControllerGUI controller = new ControllerGUI(mockModel, new JFrameView());

    try {
      controller.createPortfolio("money");
      assertEquals("createPortfolio();money",
              log.toString());
    } catch (Exception e) {
      //pass
    }

  }

  @Test
  public void testSavePortfolio() {
    StringBuilder log = new StringBuilder();
    mockModel = new MockModelSavePortfolio(log);

    IControllerGUI controller = new ControllerGUI(mockModel, new JFrameView());

    try {
      controller.savePortfolio("GOOG", "Google", "35", "2022-11-11");
      assertEquals("savePortfolio();GOOG;Google;35;2022-11-11",
              log.toString());
    } catch (Exception e) {
      //pass
    }

  }

}