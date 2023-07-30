package controller;

import model.PortfolioModel;
import view.PortfolioView;

/**
 * This interface represents a Controller of a Portfolio management application.
 */
public interface PortfolioController {

  /**
   * To give the control to the controller.
   * @param model the model object.
   * @param view the view object.
   */
  void goController(PortfolioModel model, PortfolioView view);

}
