
import controller.Controller;
import controller.ControllerGUI;
import controller.IControllerGUI;
import controller.PortfolioController;
import view.IView;
import model.Model;
import view.JFrameView;
import model.PortfolioModel;
import view.PortfolioView;
import view.View;

/**
 * Class to hold the Main method of the Portfolio management system.
 */
public class PortfolioManagementMain {

  /**
   * Main class of the program. It creates model and view objects.
   * It gives control to the controller by passing model and view
   * objects as parameters.
   *
   * @param args optional String arguments.
   */
  public static void main(String[] args) {
    PortfolioModel model = new Model();

    if (args.length == 1 && args[0].equals("textbased")) {
      PortfolioView view = new View(System.out);
      PortfolioController controller = new Controller(System.in);

      controller.goController(model, view);
    } else {
      IView view = new JFrameView();

      IControllerGUI controller = new ControllerGUI(model, view);
    }


  }

}
