package model;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class implements Portfolio interface and implements the operations of a portfolio
 * in the Portfolio Management system.
 */
public class PortfolioImpl extends AbstractPortfolio {


  private PortfolioImpl(String portfolioName) {
    this.portfolioName = portfolioName;
    this.stockID = new ArrayList<>();
    this.stockName = new ArrayList<>();
    this.stockQuantity = new ArrayList<>();
  }

  /**
   * A static method to get the builder object to create a new PortfolioImpl object.
   * @return portfolio builder object.
   */
  public static PortfolioBuilder getBuilder() {
    return new PortfolioBuilder();
  }

  /**
   * Inner class to build Portfolio objects.
   */
  public static class PortfolioBuilder {

    private String portfolioName;

    private PortfolioBuilder() {
      this.portfolioName = "";
    }

    /**
     * Method to set the portfolio name and to create a new portfolioImpl object.
     * @param portfolioName the name of the portfolio provided by the user.
     * @param currentUser the current user object.
     * @return portfolio builder object with the portfolio name set.
     * @throws IllegalArgumentException thrown if duplicate portfolio name is found.
     */
    public PortfolioBuilder setPortfolioName(String portfolioName, User currentUser)
            throws IllegalArgumentException {

      //check if portfolio name already exist
      File directoryPath = new File("./user_account/" + currentUser.getUserID());
      String[] portfolioNames = directoryPath.list();
      if (portfolioNames != null) {
        if (Arrays.asList(portfolioNames).contains(portfolioName)) {
          throw new IllegalArgumentException("Portfolio name already exists. " +
                  "Use a different portfolio name");
        }
      }

      this.portfolioName = portfolioName;
      return this;
    }

    public PortfolioImpl build() {
      return new PortfolioImpl(portfolioName);
    }

  }


  @Override
  public void savePortfolio(User currentUser) {

    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

      Document doc = dBuilder.newDocument();

      //creating a root element.
      Element rootElement = doc.createElement("portfolios");
      doc.appendChild(rootElement);

      //creating stockID element.


      for (int i = 0; i < this.stockID.size(); i++) {

        Element stockID = doc.createElement("stockID");
        rootElement.appendChild(stockID);

        //setting attribute to stockID
        Attr attr = doc.createAttribute("stockIDData");
        //add stock ID of each stock
        attr.setValue(this.stockID.get(i));
        stockID.setAttributeNode(attr);

        //setting
        Element stockName = doc.createElement("stockName");
        stockName.appendChild(doc.createTextNode(this.stockName.get(i)));
        stockID.appendChild(stockName);

        Element stockQuantity = doc.createElement("stockQuantity");
        stockQuantity.appendChild(doc.createTextNode(this.stockQuantity.get(i)));
        stockID.appendChild(stockQuantity);


      }



      saveAsXMLFile(doc, currentUser, "");


    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }

  }




  //new code for enabling abstraction.
  @Override
  public void addStock(String key, String value) {
    //add stockID, stock name and ticker.
    addStockHelper(key, value);
  }


}