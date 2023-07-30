package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class contains all the common functionality between the various implementations
 * of the User interface.
 */
public abstract class AbstractUser implements User {

  protected String firstName;
  protected String lastName;
  protected String middleName;
  protected String userID;
  protected int password;

  protected Double convertQuantityInput(String value, String portfolioName, String userID) throws
          IllegalArgumentException {
    double qty;
    try {
      qty = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      deleteFile(portfolioName);
      throw new IllegalArgumentException("Stock quantity entered is invalid.");
    }
    return Math.floor(qty);
  }

  protected void deleteFile(String portfolioName) {
    File file = new File("./user_account/" + this.userID + "/" + portfolioName + ".xml");
    file.delete();
  }

  protected List<List<String>> getPortfolioHelper(String portfolioName, String pre) {
    List<List<String>> result = new ArrayList<>();

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse("./user_account/" + this.getUserID() + pre
              + portfolioName + ".xml");
      NodeList stockIDList = doc.getElementsByTagName("stockID");
      for (int i = 0; i < stockIDList.getLength(); i++) {
        List<String> temp = new ArrayList<>();
        Node s = stockIDList.item(i);
        if (s.getNodeType() == Node.ELEMENT_NODE) {
          Element stockID = (Element) s;
          String stockIDData = stockID.getAttribute("stockIDData");
          temp.add(stockIDData);
          NodeList stockList = stockID.getChildNodes();
          for (int j = 0; j < stockList.getLength(); j++) {
            Node sl = stockList.item(j);
            if (sl.getNodeType() == Node.ELEMENT_NODE) {
              Element stock = (Element) sl;
              temp.add(stock.getTextContent());
            }
          }

        }
        result.add(temp);
      }
    } catch (ParserConfigurationException | IOException | SAXException e) {
      return new ArrayList<>();
    }

    return result;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public void setPassword(int password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getUserID() {
    return userID;
  }

  public int getPassword() {
    return password;
  }

  public double getCommissionFee() {
    throw new IllegalArgumentException();
  }

}
