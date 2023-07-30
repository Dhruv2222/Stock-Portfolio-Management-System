package controller;


import java.io.File;
import java.time.LocalDate;
import java.util.List;

import model.PortfolioModel;
import view.IView;

/**
 * The Controller GUI class implements the Features interface.
 * It controls the flow of a Portfolio Management application by taking inputs and passing it to the
 * model and receiving inputs from the model and passing them to view for displaying to the user.
 */
public class ControllerGUI implements IControllerGUI {

  private PortfolioModel model;
  private IView view;

  private int portfolioType;

  /**
   * Constructor to create the Controller object.
   *
   * @param model the model object.
   * @param view  the view object.
   */
  public ControllerGUI(PortfolioModel model, IView view) {
    this.model = model;
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void decideUserType() {
    try {
      if (portfolioType == 1) {

        view.createObservingUserInterface();
      } else {
        view.createFlexibleUserInterface();
      }
    } catch (Exception e) {
      view.showErrorDialogBox("Invalid selection.");
    }

  }

  @Override
  public void createUser(String firstName, String lastName, String middleName, String userID,
                         String password, String commissionFee) {
    try {
      model.createUser(firstName, lastName, middleName, userID, password, portfolioType,
              Double.parseDouble(commissionFee));
      view.showWelcomeMessage(model.getCurrentUser().getFirstName());
    } catch (Exception e) {
      view.showErrorDialogBox("Invalid inputs given.");
    }

  }

  @Override
  public void login(String username, String password) {
    try {
      model.login(username, password, portfolioType);
      view.showWelcomeMessage(model.getCurrentUser().getFirstName());
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void createPortfolio(String portfolioName) {
    try {
      model.createPortfolio(portfolioName, model.getCurrentUser(), portfolioType);
      if (portfolioType == 1) {
        view.showAddNewStockInObserving();
      } else {
        view.showAddNewStockInFlexible();
      }

    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void addNewStockInPortfolio(String stockID, String stockName, String stockQty,
                                     String transactionDate) {
    try {
      addStock(stockID, stockName, stockQty, transactionDate);
      view.showSuccessDialogBox("STOCK ADDED.", "Stock added to portfolio.");
      view.clearAddNewStockInputFields();
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void savePortfolio(String stockID, String stockName, String stockQty,
                            String transactionDate) {
    try {
      addStock(stockID, stockName, stockQty, transactionDate);
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
      view.showSuccessDialogBox("SAVED!", "Saved successfully.");
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  private void addStock(String stockID, String stockName, String stockQty, String transactionDate) {
    if (stockID.isEmpty() || stockName.isEmpty() || stockQty.isEmpty() ||
            transactionDate.isEmpty()) {
      throw new IllegalArgumentException("Missing fields.");
    }
    model.addStockInPortfolio("stockID", stockID, model.getWorkingPortfolio());
    model.addStockInPortfolio("stockName", stockName, model.getWorkingPortfolio());
    model.addStockInPortfolio("stockQuantity", stockQty, model.getWorkingPortfolio());
    model.addStockInPortfolio("transactionDate", transactionDate, model.getWorkingPortfolio());
    model.addStockInPortfolio("setBuyingPriceAndValue", "", model.getWorkingPortfolio());
  }

  @Override
  public void showPortfolios(String type) {
    try {
      if (type.equals("display")) {
        view.showPortfolios(model.showPortfolios(model.getCurrentUser()));
      } else if (type.equals("select")) {
        view.showPortfoliosForSelection(model.showPortfolios(model.getCurrentUser()));
      }
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }


  }

  @Override
  public void selectPortfolio(String portfolioID, boolean displayDate) {
    try {
      model.selectPortfolio(model.getCurrentUser(), portfolioID);
      if (portfolioType == 1) {
        view.showEnterDate(displayDate);
      } else {
        // advance portfolio menu
        view.showFlexiblePortfolioMenu();
      }
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void showPricesForADate(boolean useYesterday, String date) {
    try {
      //prices
      List<Double> prices;

      //yesterday's date
      LocalDate yesterday = java.time.LocalDate.now().minusDays(1);
      yesterday = handleWeekend(yesterday);

      //if user needs yesterday's prices.
      if (useYesterday) {
        //get prices of stocks on given date.
        prices = model.getStockPricesOnDate(yesterday, model.getWorkingPortfolio());
      }

      //user needs custom date's prices.
      else {
        //check date
        try {
          yesterday = LocalDate.parse(date);
          yesterday = handleWeekend(yesterday);
        } catch (Exception e) {
          throw new IllegalArgumentException("Invalid date entered. Try again.");
        }

        //future date check.
        if (yesterday.isAfter(yesterday)) {
          throw new IllegalArgumentException("Date entered is of today or in future.");
        }

        //get prices of stocks on given date.
        prices = model.getStockPricesOnDate(yesterday, model.getWorkingPortfolio());
      }

      view.showPortfolioContents(model.getWorkingPortfolio(), prices, yesterday);
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  private LocalDate handleWeekend(LocalDate yest) {

    if (yest.getDayOfWeek().toString().equals("SUNDAY")) {
      yest = yest.minusDays(2);
    }
    if (yest.getDayOfWeek().toString().equals("SATURDAY")) {
      yest = yest.minusDays(1);
    }
    return yest;
  }

  @Override
  public void uploadPortfolio(String filepath) {
    if (filepath.equals("null")) {
      view.showErrorDialogBox("Upload cancelled.");
    } else {
      String updatedFileName = "";
      try {
        model.uploadPortfolio(model.getCurrentUser(), filepath);
        String[] name = filepath.split("/");
        String fileName = name[name.length - 1];
        String[] updFileName = fileName.split("\\.");
        updatedFileName = updFileName[0];
        //check if file format is XML.
        if (!updFileName[1].equals("xml")) {
          view.showErrorDialogBox("Incorrect file format.");
        }
        model.selectPortfolio(model.getCurrentUser(), updatedFileName);
      } catch (Exception e) {
        deleteFile(updatedFileName);
        view.showErrorDialogBox("Incorrect file format.");
      }
    }

  }

  private void deleteFile(String portfolioName) {
    File file;
    if (portfolioType == 1) {
      file = new File("./user_account/" + this.model.getCurrentUser().getUserID()
              + "/" + portfolioName + ".xml");
    } else {
      file = new File("./user_account/" + this.model.getCurrentUser().getUserID()
              + "/Adv_" + portfolioName + ".xml");
    }
    file.delete();
  }


  @Override
  public void sellStock(String stockID, String quantity, String date) {
    try {
      model.sellStock(model.getWorkingPortfolio(), stockID, Double.parseDouble(quantity), date);
      view.showSuccessDialogBox("SOLD!", "Stock was sold successfully.");
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void getValueOnDate(String date) {
    try {
      view.showValueOfFlexPortfolioInPanel(date, model.getTotalValueOnDate(LocalDate.parse(date),
              model.getWorkingPortfolio()));
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

  @Override
  public void getCostBasis(String date) {
    try {
      view.showCostBasisOfFlexPortfolioInPanel(date, model.getCostBasis(LocalDate.parse(date),
              model.getWorkingPortfolio()));
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

  @Override
  public void showComposition(String date) {
    try {
      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse(date));
      view.showFlexPortfolioContents(model.getConsolidatedPortfolio());
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void selectedPortfolioType(int portfolioType) {
    this.portfolioType = portfolioType;
  }

  @Override
  public void flexiblePortfolioFeatures(String option) {
    try {
      switch (option) {
        case "Buy stock":
          view.showAddNewStockInFlexible();
          break;
        case "Sell stock":
          view.sellPortfolioInterface();
          break;
        case "Get value on a date":
          view.enterDateForFlexValueOnDate();
          break;
        case "Get cost-basis":
          view.enterDateForFlexCostBasis();
          break;
        case "Get portfolio composition":
          view.enterDateForFlexShowComposition();
          break;
        case "Graph analysis":
          view.graphAnalysisDateInput();
          break;
        case "Invest Fixed Amount":
          view.showInvestFixedAmount();
          break;
        case "Dollar-cost Averaging":
          view.showDollarCostAverage(false);
          break;
        case "Go back":
          view.goBackToListOfPortfolios();
          break;
        default:
          //pass
      }
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }


  }

  @Override
  public void saveFlexPortfolio() {
    try {
      model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
      view.showSuccessDialogBox("SAVED!", "Changes in portfolio saved successfully.");
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

  @Override
  public void addMoreStocksInStrategy(String ticker, String stockName, String ratio,
                                      boolean initial, int invStr) {
    try {
      // reset the fields when strategy called for the first time.
      if (initial) {
        model.startNewStrategy();
      }

      // store entered fields
      model.collectTickerRatios(ticker, ratio);
      model.collectTickerNames(ticker, stockName);

      // show another set of fields
      if (invStr == 1) {
        view.addMoreInvStockFields(false);
      }
      if (invStr == 2) {
        view.addMoreSTFInvStockFields(false);
      }

    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

  // taking ticker and stock name as parameters to handle the case when only one stock is added.
  @Override
  public void investFixedAmount(String ticker, String stockName, String ratio, boolean initial,
                                String amount, String buyingDate) {
    try {
      // reset the fields when strategy called for the first time.
      if (initial) {
        model.startNewStrategy();
      }

      // store entered fields
      model.collectTickerRatios(ticker, ratio);
      model.collectTickerNames(ticker, stockName);

      // buy stocks
      model.investFixedAmount(amount, buyingDate);
      view.showSuccessDialogBox("PURCHASED", "All stocks purchased successfully.");
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

  @Override
  public void dollarCostAvg(String ticker, String stockName, String ratio, String amount,
                            String intervalNum, String intervalDuration,
                            String startDate, String endDate, boolean ongoing, boolean initial) {
    try {
      if (initial) {
        model.startNewStrategy();
      }

      // store entered fields
      model.collectTickerRatios(ticker, ratio);
      model.collectTickerNames(ticker, stockName);

      model.startToEndInvest(amount, intervalNum, intervalDuration, startDate, endDate, ongoing);

      view.showSuccessDialogBox("PURCHASED", "Stocks purchased successfully.");
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

  @Override
  public void plotLineGraph(String startDate, String endDate) {
    try {
      List<String> timestamps = model.getGraphTimestamp(startDate,
              endDate);


      view.graphAnalysisLine(model.getWorkingPortfolio().getPortfolioName(), startDate, endDate,
              timestamps, model.getGraphHeight(model.getWorkingPortfolio(), timestamps),
              model.getGraphIncrement());
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }

  }

  @Override
  public void plotBarGraph(String startDate, String endDate) {
    try {
      List<String> timestamps = model.getGraphTimestamp(startDate,
              endDate);


      view.graphAnalysisBar(model.getWorkingPortfolio().getPortfolioName(), startDate, endDate,
              timestamps, model.getGraphHeight(model.getWorkingPortfolio(), timestamps),
              model.getGraphIncrement());
    } catch (Exception e) {
      view.showErrorDialogBox(e.getMessage());
    }
  }

}
