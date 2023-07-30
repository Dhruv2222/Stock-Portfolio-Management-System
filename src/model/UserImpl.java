package model;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.transform.TransformerException;

/**
 * This class implements the User interface and implements all the operations which are used to set
 * and get data from the User object.
 */
public class UserImpl extends AbstractUser {

  /**
   * Constructor to create new UserImpl objects.
   */
  public UserImpl() {
    //necessary for the proper functioning of the XML encoder.
  }

  private UserImpl(String firstName, String lastName, String middleName, String userID,
                   int password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.userID = userID;
    this.password = password;
  }


  public static UserBuilder getBuilder() {
    return new UserBuilder();
  }

  /**
   * This class is used create objects of the UserImpl class.
   */
  public static class UserBuilder {

    private String firstName;
    private String lastName;
    private String middleName;
    private String userID;
    private int password;

    //private constructor
    private UserBuilder() {
      this.firstName = "";
      this.lastName = "";
      this.middleName = "";
      this.userID = "";
      this.password = 0;
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


    /**
     * This method is used to build UserImpl object. It also stores the user directory.
     *
     * @return UserImpl objects.
     * @throws IllegalArgumentException when save user details fails.
     */
    public UserImpl build() throws IllegalArgumentException {
      UserImpl newUser = new UserImpl(firstName, lastName, middleName, userID, password);


      SaveUserDetails saveUserDetails = new SaveUserDetailsImpl();
      try {
        saveUserDetails.saveUserDetails(newUser, 1);
      } catch (TransformerException e) {
        throw new RuntimeException(e);
      }


      return newUser;
    }


  }

  @Override
  public List<String> showPortfolios() throws IllegalArgumentException {

    File directoryPath = new File("./user_account/" + this.getUserID());
    String[] portfolioNames = directoryPath.list();
    List<String> result = new ArrayList<>();

    if (portfolioNames == null) {
      throw new IllegalArgumentException("No such portfolio exists!");
    }
    for (String str : portfolioNames) {
      str = str.substring(0, str.length() - 4);
      if (str.equals("details") || str.startsWith("Adv_")) {
        continue;
      }
      result.add(str);
    }
    return result;

  }

  @Override
  public Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException {
    List<List<String>> result = getPortfolioHelper(portfolioName, "/");

    //check if file read was correct
    if (result.isEmpty()) {
      deleteFile(portfolioName);
      throw new IllegalArgumentException("Incorrect format of XML file.");
    }

    Portfolio reqPortfolio = PortfolioImpl.getBuilder().setPortfolioName(portfolioName,
            this).build();

    for (List<String> ls : result) {
      if (Model.checkTickerExistence(ls.get(0))) {
        reqPortfolio.setStockID(ls.get(0));
      } else {
        deleteFile(portfolioName);
        throw new IllegalArgumentException("StockID ticker is invalid.");
      }

      reqPortfolio.setStockName(ls.get(1));

      if (convertQuantityInput(ls.get(2), portfolioName, userID) < 0) {
        deleteFile(portfolioName);
        throw new IllegalArgumentException("Stock quantity entered is invalid.");
      }
      reqPortfolio.setStockQuantity(convertQuantityInput(ls.get(2), portfolioName, userID)
              .toString());
    }
    return reqPortfolio;
  }

  /**
   * This method is used to upload an XML file that a user has created outside the program.
   *
   * @param filepath the location of the file to be uploaded.
   */
  public void uploadPortfolio(String filepath) throws IllegalArgumentException {

    String dest = "./user_account/" + this.getUserID() + "/";
    Path src = Paths.get(filepath);
    if (!Files.exists(src)) {
      throw new IllegalArgumentException("Invalid path provided.");
    } else {
      String[] name = filepath.split("/");
      String fileName = name[name.length - 1];
      Path endDest = Paths.get(dest + fileName);
      try {
        //if target exists, replace it.
        Files.copy(src, endDest, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
