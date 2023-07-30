package model;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.transform.TransformerException;

/**
 * This class extends the AbstractUser class and implements all the operations which are used to set
 * and get data from the Advanced User object and also implements the unique functionalities of the
 * Advance UserImpl.
 */
public class AdvUserImpl extends AbstractUser {

  private double commissionFee;

  /**
   * Constructor to create new AdvUserImpl objects.
   */
  public AdvUserImpl() {
    //necessary for the proper functioning of the XML encoder.
  }

  private AdvUserImpl(String firstName, String lastName, String middleName, String userID,
                      int password, double commissionFee) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.userID = userID;
    this.password = password;
    this.commissionFee = commissionFee;
  }


  public static UserBuilder getBuilder() {
    return new AdvUserImpl.UserBuilder();
  }

  /**
   * This class is used create objects of the AdvUserImpl class.
   */
  public static class UserBuilder {

    private String firstName;
    private String lastName;
    private String middleName;
    private String userID;
    private int password;
    private double commissionFee;

    //private constructor
    private UserBuilder() {
      this.firstName = "";
      this.lastName = "";
      this.middleName = "";
      this.userID = "";
      this.password = 0;
      this.commissionFee = 0;
    }

    public UserBuilder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public UserBuilder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder setMiddleName(String middleName) {
      this.middleName = middleName;
      return this;
    }

    public UserBuilder setUserID(String userID) {
      this.userID = userID;
      return this;
    }

    public UserBuilder setPassword(String password) {
      this.password = Objects.hash(password);
      return this;
    }

    public UserBuilder setCommissionFee(double commissionFee) {
      this.commissionFee = commissionFee;
      return this;
    }


    /**
     * This method is used to build AdvUserImpl object. It also stores the user directory.
     *
     * @return AdvUserImpl object with the user details in it.
     */
    public AdvUserImpl build() throws IllegalArgumentException {
      AdvUserImpl newUser = new AdvUserImpl(firstName, lastName, middleName, userID, password,
              commissionFee);

      SaveUserDetails saveUserDetails = new SaveUserDetailsImpl();
      try {
        saveUserDetails.saveUserDetails(newUser, 2);
      } catch (TransformerException e) {
        throw new RuntimeException(e);
      }

      return newUser;
    }


  }


  @Override
  public List<String> showPortfolios() {

    File directoryPath = new File("./user_account/" + this.getUserID());
    String[] portfolioNames = directoryPath.list();
    List<String> result = new ArrayList<>();

    if (portfolioNames == null) {
      throw new IllegalArgumentException("No such portfolio exists!");
    }
    for (String str : portfolioNames) {
      str = str.substring(0, str.length() - 4);
      //check if it is the user details file or a normal portfolio file.
      if (str.equals("details") || !str.startsWith("Adv_")) {
        continue;
      }
      result.add(str.replace("Adv_", ""));
    }
    return result;
  }


  @Override
  public Portfolio getPortfolio(String portfolioName) {
    List<List<String>> result = getPortfolioHelper(portfolioName, "/Adv_");


    //check if file read was correct
    if (result.isEmpty()) {
      deleteFile(portfolioName);
      throw new IllegalArgumentException("Incorrect format of XML file.");
    }

    Portfolio reqPortfolio = new AdvPortfolioImpl(portfolioName);

    for (List<String> ls : result) {
      //check if ticker exists.
      if (!Model.checkTickerExistence(ls.get(0))) {
        deleteFile("Adv_" + portfolioName);
        throw new IllegalArgumentException("StockID ticker is invalid.");
      }

      //check if date is not in the future.
      if (!LocalDate.now().isAfter(LocalDate.parse(ls.get(3)))) {
        throw new IllegalArgumentException("Date entered is in the future.");
      }
      //add the new row in the portfolio.
      if (convertQuantityInput(ls.get(2), portfolioName, userID) >= 0) {
        reqPortfolio.addStock("stockID", ls.get(0));
        reqPortfolio.addStock("stockName", ls.get(1));
        reqPortfolio.addStock("stockQuantity", ls.get(2));
        reqPortfolio.addStock("transactionDate", ls.get(3));
        reqPortfolio.addStock("setBuyingPriceAndValue", ls.get(4));
      } else {
        reqPortfolio.sellStock(ls.get(0), Double.parseDouble(ls.get(2)), ls.get(3));
      }

    }

    return reqPortfolio;
  }

  /**
   * This method is used to upload an XML file that a user has created outside the program.
   *
   * @param filepath the location of the file to be uploaded.
   */
  @Override
  public void uploadPortfolio(String filepath) {
    String dest = "./user_account/" + this.getUserID() + "/";
    Path src = Paths.get(filepath);
    if (!Files.exists(src)) {
      throw new IllegalArgumentException("Invalid path provided.");
    } else {
      String[] name = filepath.split("/");
      String fileName = name[name.length - 1];
      Path endDest = Paths.get(dest + "Adv_" + fileName);
      try {
        //if target exists, replace it.
        Files.copy(src, endDest, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public double getCommissionFee() {
    return this.commissionFee;
  }
}
