package controller;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Portfolio;
import model.PortfolioModel;
import view.PortfolioView;

/**
 * This class implements the PortfolioController interface. It controls the flow of
 * a Portfolio Management application by taking inputs and passing it to the model
 * and receiving inputs from the model and passing them to view for displaying to the user.
 */
public class Controller implements PortfolioController {

  private PortfolioModel model;
  private PortfolioView view;


  private final Scanner sc;

  /**
   * Constructor to create a Controller object and to set the InputStream for it.
   *
   * @param in InputStream to be set in the controller.
   */
  public Controller(InputStream in) {
    sc = new Scanner(in);
  }

  /**
   * This method delegates the tasks to the model and the view.
   *
   * @param model the model object.
   * @param view  the view object.
   */
  public void goController(PortfolioModel model, PortfolioView view) {
    this.model = model;
    this.view = view;

    int inputNext = 0;

    view.showSelectPortfolioOptions();
    int portfolioType = sc.nextInt();

    //================================= OBSERVING PORTFOLIO ==============================
    if (portfolioType == 1) {


      //Show start up menu.
      view.startUpMenu();

      //User object saved in currentUser.
      handleInputForUserLogin(portfolioType);

      //Welcome message for the user along with menu.
      view.welcomeUser(model.getCurrentUser().getFirstName());

      while (inputNext != -1) {
        view.showPortfolioRecurringMenu();
        try {
          inputNext = sc.nextInt();
        } catch (InputMismatchException e) {
          //input remains 0
        }

        switch (inputNext) {

          case 1:
            createPortfolio(1);
            view.portfolioCreatedMessage();
            int addMoreStocks = 1;
            while (addMoreStocks == 1) {
              addNewStockInPortfolio(model.getWorkingPortfolio(), portfolioType);
              view.createPortfolioMenu();
              addMoreStocks = sc.nextInt();
            }
            try {
              this.model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
              view.portfolioSavedMessage();
            } catch (Exception e) {
              view.showErrorMessage(e.getMessage());
            }
            break;

          case 2:
            showPortfolios();
            break;

          case 3:
            selectPortfolio();
            try {
              LocalDate yest = java.time.LocalDate.now().minusDays(1);
              handleWeekend(yest);
              showPricesForADate(yest);
            } catch (IllegalArgumentException e) {
              view.showErrorMessage(e.getMessage());
            }

            break;

          case 4:
            view.portfolioEnterPortfolioPath();
            try {
              uploadPortfolio(1);
            } catch (Exception e) {
              view.showErrorMessage(e.getMessage());
            }

            break;

          default:

            inputNext = -1;
        }
      }
    }

    //================================= TRADING PORTFOLIO ==============================
    else if (portfolioType == 2) {

      //Show start up menu.
      view.startUpMenu();

      //User object saved in currentUser.
      handleInputForUserLogin(portfolioType);

      //Welcome message for the user along with menu.
      view.welcomeUser(model.getCurrentUser().getFirstName());

      while (inputNext != -1) {
        view.showPortfolioRecurringMenu();
        try {
          inputNext = sc.nextInt();
        } catch (InputMismatchException e) {
          //input remains 0
        }

        switch (inputNext) {

          case 1:
            createPortfolio(portfolioType);
            view.portfolioCreatedMessage();
            int addMoreStocks = 1;
            while (addMoreStocks == 1) {
              addNewStockInPortfolio(model.getWorkingPortfolio(), portfolioType);
              view.createAdvPortfolioMenu();
              addMoreStocks = sc.nextInt();
            }
            try {
              this.model.savePortfolio(model.getWorkingPortfolio(), model.getCurrentUser());
              view.portfolioSavedMessage();
            } catch (Exception e) {
              view.showErrorMessage(e.getMessage());
            }
            break;

          case 2:
            try {
              showPortfolios();
            } catch (Exception e) {
              view.showErrorMessage(e.getMessage());
            }
            break;

          case 3:
            try {
              selectPortfolio();
              showOperationsOnSelectedPortfolio(model.getWorkingPortfolio());
            } catch (Exception e) {
              view.showErrorMessage(e.getMessage());
            }
            break;

          case 4:
            view.portfolioEnterPortfolioPath();
            try {
              uploadPortfolio(2);
            } catch (Exception e) {
              view.showErrorMessage(e.getMessage());
            }

            break;

          default:

            inputNext = -1;
        }
      }
    }


  }

  private void showOperationsOnSelectedPortfolio(Portfolio newPortfolio) {
    int option = 0;
    while (option != -1) {
      view.showSelectedPortfolioOptions();
      option = sc.nextInt();
      switch (option) {
        case 1:
          try {
            addNewStockInPortfolio(newPortfolio, 2);
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }
          break;
        case 2:
          try {
            sellStockInPortfolio(newPortfolio);
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }
          break;
        case 3:
          try {
            //put get value of the portfolio on a given date.
            view.showEnterDateInput();
            String date = sc.next();
            double totalValue = getTotalValueOnDate(LocalDate.parse(date), newPortfolio);
            view.showTotalValue(totalValue, date);
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }
          break;
        case 4:
          try {
            //put get cost basis of the portfolio on a given date.
            view.showEnterDateInput();
            String date1 = sc.next();
            double costBasis = getCostBasis(LocalDate.parse(date1), newPortfolio);
            view.showCostBasisValue(costBasis, date1);
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }

          break;

        case 5:
          try {
            model.savePortfolio(newPortfolio, model.getCurrentUser());
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }

          break;
        case 6:
          try {
            showComposition(newPortfolio);
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }
          break;
        case 7:
          try {
            handleInputForGraphAnalysis(newPortfolio);
          } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
          }
          break;

        default:
          option = -1;
      }
    }

  }

  private void handleInputForGraphAnalysis(Portfolio newPortfolio) {

    view.showEnterFromDateInput();
    String fromDate = sc.next();
    view.showEnterToDateInput();
    String toDate = sc.next();

    List<String> timestamp = model.getGraphTimestamp(fromDate, toDate);

    if (timestamp.get(0).equals("month")) {
      view.plotStarBarGraphMonth(newPortfolio.getPortfolioName(), fromDate, toDate, timestamp,
              model.getGraphStarCount(newPortfolio, timestamp));
    } else {
      view.plotStarBarGraphDayYear(newPortfolio.getPortfolioName(), fromDate, toDate, timestamp,
              model.getGraphStarCount(newPortfolio, timestamp));
    }
  }

  private void showComposition(Portfolio newPortfolio) {
    view.showEnterDateInput();
    LocalDate date = LocalDate.parse(sc.next());
    model.showComposition(newPortfolio, date);
    view.showAdvPortfolioContents(model.getConsolidatedPortfolio());

  }

  private double getCostBasis(LocalDate date, Portfolio newPortfolio) {

    return model.getCostBasis(date, newPortfolio);

  }


  private void sellStockInPortfolio(Portfolio newPortfolio) {

    view.portfolioEnterTransactionDate();
    String date = sc.next();

    //display list of stocks in portfolio.
    model.showComposition(newPortfolio, LocalDate.parse(date));
    view.showAdvPortfolioContents(model.getConsolidatedPortfolio());
    view.showSelectStockIDMessage();
    String stockID = sc.next();

    view.portfolioEnterStockQty();
    double sellQty = sc.nextDouble();


    model.sellStock(newPortfolio, stockID, sellQty, date);


  }


  private void showPricesForADate(LocalDate yest) throws IllegalArgumentException {
    view.showGetPricesOnADate();
    int onSpecificDate = sc.nextInt();
    if (onSpecificDate == 1) {
      view.showEnterDateInput();
      String oldDate = sc.next();

      model.validateDateInput(oldDate);
      LocalDate oldLocalDate = LocalDate.parse(oldDate);

      //future date check.
      if (oldLocalDate.isAfter(yest)) {
        throw new IllegalArgumentException("Date entered is of today or in future.");
      }

      //get prices of stocks on given date.
      List<Double> oldPrices = getStockPricesOnDate(oldLocalDate, model.getWorkingPortfolio());

      view.showPortfolioContents(model.getWorkingPortfolio(), oldPrices, oldDate,
              model.showTotalInvestment(model.getWorkingPortfolio(), oldPrices));
    } else {
      List<Double> prices = getStockPricesOnDate(yest, model.getWorkingPortfolio());

      view.showPortfolioContents(model.getWorkingPortfolio(), prices, yest.toString(),
              model.showTotalInvestment(model.getWorkingPortfolio(), prices));
    }

  }

  private void handleWeekend(LocalDate yest) {

    if (yest.getDayOfWeek().toString().equals("SUNDAY")) {
      yest = yest.minusDays(2);
    }
    if (yest.getDayOfWeek().toString().equals("SATURDAY")) {
      yest = yest.minusDays(1);
    }

  }

  private List<Double> getStockPricesOnDate(LocalDate yest, Portfolio newPortfolio) {
    return model.getStockPricesOnDate(yest, newPortfolio);
  }

  private double getTotalValueOnDate(LocalDate date, Portfolio newPortfolio) {
    return model.getTotalValueOnDate(date, newPortfolio);
  }

  private void handleInputForUserLogin(int portfolioType) {
    int input = 0;
    try {
      input = sc.nextInt();
    } catch (InputMismatchException e) {
      //input remains 0
    }
    switch (input) {
      case 1:
        login(portfolioType);
        break;
      case 2:
        createUser(portfolioType);
        break;
      default:
        System.exit(0);
    }
  }

  private void createUser(int portfolioType) {
    double commissionFee = 0;

    view.userEnterFirstName();
    String firstName = sc.next();
    view.userEnterLastName();
    String lastName = sc.next();
    view.userEnterMiddleName();
    String middleName = sc.next();
    view.userEnterUserID();
    String userID = sc.next();
    view.userEnterPassword();
    String password = sc.next();

    if (portfolioType == 2) {
      view.userEnterCommissionFee();
      commissionFee = sc.nextDouble();
    }

    try {
      model.createUser(firstName, lastName, middleName, userID, password, portfolioType,
              commissionFee);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage("Invalid input given.");
      System.exit(0);
    }

  }

  private void login(int portfolioType) {
    view.userEnterUserID();
    String userID = sc.next();
    view.userEnterPassword();
    String password = sc.next();
    try {
      model.login(userID, password, portfolioType);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
      System.exit(0);
    }

  }

  private void addNewStockInPortfolio(Portfolio portfolio, int portfolioType) {

    try {
      view.portfolioEnterStockTicker();
      String stockID = sc.next();
      model.addStockInPortfolio("stockID", stockID, portfolio);

      view.portfolioEnterStockName();
      String stockName = sc.next();
      model.addStockInPortfolio("stockName", stockName, portfolio);

      view.portfolioEnterStockQty();
      String stockQuantity = sc.next();
      model.addStockInPortfolio("stockQuantity", stockQuantity, portfolio);


      if (portfolioType == 2) {
        view.portfolioEnterTransactionDate();
        String date = sc.next();
        model.addStockInPortfolio("transactionDate", date, portfolio);

        model.addStockInPortfolio("setBuyingPriceAndValue", "", portfolio);

      }
    } catch (Exception e) {
      view.showErrorMessage(e.getMessage());
      System.exit(0);
    }


  }

  private void selectPortfolio() {
    //show all portfolios before selecting.
    List<String> portfolioNames = model.showPortfolios(model.getCurrentUser());
    view.showListOfPortfolios(portfolioNames);
    view.showSelectPortfolioMessage();
    int selection = sc.nextInt();

    //checking is required portfolio is already loaded in the object.
    if (model.getWorkingPortfolio() != null && model.getWorkingPortfolio().getPortfolioName()
            .equals(portfolioNames
                    .get(selection - 1))) {
      return;
    }

    try {
      model.selectPortfolio(model.getCurrentUser(), portfolioNames.get(selection - 1));
    } catch (Exception e) {
      view.showErrorMessage(e.getMessage());
      System.exit(0);
    }

  }


  private void showPortfolios() {
    try {
      List<String> portfolioNames = model.showPortfolios(model.getCurrentUser());
      view.showListOfPortfolios(portfolioNames);
    } catch (Exception e) {
      view.showErrorMessage(e.getMessage());
      System.exit(0);
    }

  }

  private void createPortfolio(int portfolioType) {
    view.portfolioEnterPortfolioName();
    String portfolioName = sc.next();

    try {
      model.createPortfolio(portfolioName, model.getCurrentUser(), portfolioType);
    } catch (Exception e) {
      view.showErrorMessage(e.getMessage());
      System.exit(0);
    }

  }

  private void uploadPortfolio(int portfolioType) {
    //take file path input from user.
    String filepath = sc.next();
    String[] name = filepath.split("/");
    String fileName = name[name.length - 1];
    String[] updFileName = fileName.split("\\.");
    String updatedFileName = updFileName[0];
    //check if file format is XML.
    if (!updFileName[1].equals("xml")) {
      throw new IllegalArgumentException("Incorrect file format");
    }

    //pass user and filepath to model.
    model.uploadPortfolio(model.getCurrentUser(), filepath);

    //load data into Portfolio variable.
    model.selectPortfolio(model.getCurrentUser(), updatedFileName);

    if (portfolioType == 1) {
      //load data into Portfolio variable.
      model.selectPortfolio(model.getCurrentUser(), updatedFileName);
      LocalDate yest = java.time.LocalDate.now().minusDays(1);
      handleWeekend(yest);
      showPricesForADate(yest);
    }
    if (portfolioType == 2) {
      view.showEnterDateInput();
      String date = sc.next();
      model.showComposition(model.getWorkingPortfolio(), LocalDate.parse(date));
      view.showAdvPortfolioContents(model.getConsolidatedPortfolio());
    }

  }
}
