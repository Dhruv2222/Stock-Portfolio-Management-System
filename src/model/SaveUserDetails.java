package model;

import javax.xml.transform.TransformerException;

/**
 * This interface represents the methods required to save and validate the details of a user.
 */
public interface SaveUserDetails {

  /**
   * This method saves the details of the user in XML format.
   *
   * @param newUser user object.
   * @param portfolioType type of portfolio.
   * @throws TransformerException when save user details fails.
   */
  void saveUserDetails(User newUser, int portfolioType) throws TransformerException;

  /**
   * This method validates and returns a user object.
   *
   * @param userID   the userID of a user as String.
   * @param password password of the user as int.
   * @param portfolioType type of portfolio.
   * @return the user object containing the details of the user.
   */
  User getUserDetails(String userID, String password, int portfolioType);
}
