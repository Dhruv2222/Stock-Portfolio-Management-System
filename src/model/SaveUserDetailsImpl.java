package model;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class implements the SaveUserDetails interface and provides operations to save and
 * retrieve the user details.
 */
public class SaveUserDetailsImpl implements SaveUserDetails {

  @Override
  public void saveUserDetails(User newUser, int portfolioType) throws TransformerException {

    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();
      Element rootElement = doc.createElement("details");
      doc.appendChild(rootElement);

      Element userID = doc.createElement("userDetails");
      rootElement.appendChild(userID);

      Attr attr = doc.createAttribute("userID");
      attr.setValue(newUser.getUserID());
      userID.setAttributeNode(attr);

      Element firstName = doc.createElement("firstName");
      firstName.appendChild(doc.createTextNode(newUser.getFirstName()));
      userID.appendChild(firstName);

      Element lastName = doc.createElement("lastName");
      lastName.appendChild(doc.createTextNode(newUser.getLastName()));
      userID.appendChild(lastName);

      Element middleName = doc.createElement("middleName");
      middleName.appendChild(doc.createTextNode(newUser.getMiddleName()));
      userID.appendChild(middleName);

      Element password = doc.createElement("password");
      password.appendChild(doc.createTextNode(String.valueOf(newUser.getPassword())));
      userID.appendChild(password);

      if (portfolioType == 1) {
        Element commissionFee = doc.createElement("commissionFee");
        commissionFee.appendChild(doc.createTextNode(0 + ""));
        userID.appendChild(commissionFee);
      }

      if (portfolioType == 2) {
        Element commissionFee = doc.createElement("commissionFee");
        commissionFee.appendChild(doc.createTextNode(newUser.getCommissionFee() + ""));
        userID.appendChild(commissionFee);
      }


      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);

      File newFile = new File("./user_account/" + newUser.getUserID() + "/details.xml");
      newFile.getParentFile().mkdirs();

      StreamResult result = new StreamResult(newFile);
      transformer.transform(source, result);

    } catch (ParserConfigurationException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void deleteFile(String userID) {
    File file = new File("./user_account/" + userID + "/details.xml");
    file.delete();
  }

  @Override
  public User getUserDetails(String userID, String password, int portfolioType) throws
          IllegalArgumentException {
    List<List<String>> result = getUserDetailsHelper(userID);

    if (result.isEmpty()) {
      deleteFile(userID);
      throw new IllegalArgumentException("User does not exist.");
    }

    if (Objects.hash(password) == Integer.parseInt(result.get(0).get(4))) {
      if (portfolioType == 1) {
        return UserImpl.getBuilder().setUserID(userID)
                .setFirstName(result.get(0).get(1))
                .setLastName(result.get(0).get(2))
                .setMiddleName(result.get(0).get(3))
                .setPassword(password)
                .build();
      } else {
        return AdvUserImpl.getBuilder().setUserID(userID)
                .setFirstName(result.get(0).get(1))
                .setLastName(result.get(0).get(2))
                .setMiddleName(result.get(0).get(3))
                .setPassword(password)
                .setCommissionFee(Double.parseDouble(result.get(0).get(5)))
                .build();
      }

    } else {
      throw new IllegalArgumentException("Invalid password entered. Run again.");
    }


  }

  private List<List<String>> getUserDetailsHelper(String userID) {

    List<List<String>> result = new ArrayList<>();

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse("./user_account/" + userID + "/details.xml");
      NodeList userIDList = doc.getElementsByTagName("userDetails");
      for (int i = 0; i < userIDList.getLength(); i++) {
        List<String> temp = new ArrayList<>();
        Node s = userIDList.item(i);
        if (s.getNodeType() == Node.ELEMENT_NODE) {
          Element userDetails = (Element) s;
          userID = userDetails.getAttribute("userID");
          temp.add(userID);
          NodeList userList = userDetails.getChildNodes();
          for (int j = 0; j < userList.getLength(); j++) {
            Node sl = userList.item(j);
            if (sl.getNodeType() == Node.ELEMENT_NODE) {
              Element user = (Element) sl;
              temp.add(user.getTextContent());
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
}
