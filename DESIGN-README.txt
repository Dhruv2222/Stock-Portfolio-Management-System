
Name of the project: Assignment 6 Stocks
Group Members: Vishant Ketan Mehta (mehta.visha@northeastern.edu) & Dhruv Mehul Doshi (doshi.dhru@northeastern.edu) 

README file gives a description of the design.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Stock Application:

- The design implemented in this application is based on the Model-View-Controller approach.
- The behavior of the program is isolated into three different categories: functionality, user output, and user interaction and task delegation.
- The model implements all the functionalities offered by the program.
- The view shows the results and outputs to the user.
- The controller takes the inputs from the user and delegates tasks to the model and the view.
- The model and the view cannot directly communicate with each other, i.e. they cannot access each other directly. 
- The controller acts as a mediator between the model and the view and communicates with both.

- The application uses a web-based API: Alpha Vantage to obtain stock-related data.
- The application has a text-based interface which allows the user to interact with the application and use all the available features.
- This application allows the user to create portfolios by entering stock details.
- The total value of a user's holdings of a stock is the closing price of the stock on a given day, multiplied by the number of shares of that stock that the user owns.
- The total value of a portfolio is the sum of the values of the individual holdings.
- The application allows the user to create and save portfolios in ".xml" format in the user-specific directory.
- The user can create a ".xml" file outside of the program and have the application load it in. 

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Design Updates: (Assignment 6)

1. Added a new interface - IControllerGUI and a new class - ControllerGUI
- The ControllerGUI class implements the IControllerGUI interface.
- This interface represents all the methods required to support the functionalities of inflexible and flexible portfolios for the Graphical User Interface.

2. Added a new interface - IView and a new class - JFrameView
- This interface was added to support the Graphical User Interface.
- The JFrameView class implements the IView interface.

3. Updated the PortfolioModel interface
- To support the additional functionality of the new portfolio, new methods were added to the PortfolioModel interface and then implemented in the Model class. 
- This change was necessary so that the controller still communicates with only one model.

4. Added a new class PlotChartGUIImpl
- This class extends the PlotChartBarImpl.
- It modifies the way in which the height of bar chart is returned to support its display in GUI.

5. Added a method to the APIAlphaVantage class
- This method has been added to avoid repeated calls to the AlphaVantage API.

6. Updated the Controller class
- This update was necessary as in the previous iteration, we were validating the date fields in the Controller class.
- These validations are now done in the Model.  

7. Updated the PortfolioManagementMain class
- This update was necessary to provide options to the user to select between a Text-Based Interface and a Graphical User Interface.

8. Added a new interface TimeBasedInvestmentStrategy and a new class DollarCostAvgStartToFinishCategory
- The interface represents the high level time-based long-term investment strategies which are primarily based on a fixed amount given to be invested periodically during a time interval.
- The class implements the TimeBasedInvestmentStrategy interface and provides start-to-finish investment strategy where a fixed amount is invested periodically in the given duration.
- The investment can also be ongoing.
- In future, if new high level time-based long-term investment strategies are to be incorporated in this design, the interface must be implemented in a new class and the required functionality
can be implemented there.
- This will prevent changes in the existing code.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Design Updates: (Assignment 5)

1. Added AbstractUser Class
- This class implements the User interface.
- This class contains all the common functionality between the various implementations of the User interface.
- This class has been introduced to reduce the amount of redundant code and to avoid code duplication.

2. Added AdvUserImpl Class 
- This class extends AbstractUser class.
- This class implements all the operations which are used to set and get data from the Advanced User object.

3. Added AbstractPortfolio Class
- This class implements the Portfolio interface.
- This class contains all the common functionality between the various implementations of the Portfolio interface.
- This class has been introduced to reduce the amount of redundant code and to avoid code duplication.

4. Added AdvPortfolioImpl Class
- This class implements Portfolio interface. 
- This class implements the operations of an advanced portfolio in the Portfolio Management system.

5. Added SaveUserDetails Interface
- This interface represents the methods required to save and validate the details of a user.

6. Added SaveUserDetailsImpl Class
- This class implements the SaveUserDetails interface.

7. Added PlotChart Interface
- This interface represents the methods required to plot a bar chart for analyzing the performance of a portfolio over a period of time.

8. Added PlotChartBarImpl Class
- This class implements the PlotChart interface.
- This class implements all the methods which are required to plot the bar chart for analyzing the performance of a portfolio over a period of time.

9. Updated Portfolio interface
- The interface was updated to support added functionality of the new flexible portfolio.
- Another way out was to make another new interface which extends older interface, but then the object types in all places where the older interface had been earlier used. We explored both the approaches and concluded that both of them had their pros and cons. We finally decided to go with our current design since the changes required were lesser and minimal changes in the interface were required.

10. Updated User interface
- Similar to the portfolio interface, the user interface also had to be updated to support the additional functionality of the new Advance user. 

11. Updated PortfolioModel interface
- To support the additional functionality of the new portfolio, new methods were added to the PortfolioModel interface and then implemented in the Model class. This change was necessary so that the controller still communicates with only one model.

12. Abstracted the UserImpl class:
- The common functionality between UserImpl and AdvUserImpl was removed from the respective classes and added to the abstract class. This was done to reduce redundant code.

13. Abstracted the PortfolioImpl class:
- The common functionality between PortfolioImpl and AdvPortfolioImpl was removed from the respective classes and added to the abstract class. This was done to reduce redundant code.

14. Updated Controller class:
- According to the suggestions provided on Assignment 4, we made the code more modular. The controller was initially communicating with more than one objects from the model. In this iteration, we have made changes to ensure that it communicates with only 1 Model class.

15. Added methods to PortfolioView interface and View class.
- According to our requirement for displaying results and messages, we have added new methods to the PortfolioView interface and implemented them in the View class.

16. Handled errors gracefully
- In the previous iteration, the program threw exceptions and aborted when provided invalid inputs. Now, this has been handled properly so that a message informing the user about the error is shown and then the program stops.


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Design Justification:

A) XML file format:
1. XML does not use computer language, instead it uses human-readable language.
2. XML is readable and understandable.
3. XML is extendable. It allows one to create their own tags and also use tags created by others.

B) File System Storage:
1. It is mandatory for a user to create an account in order to use the functionalities of the application.
2. In this way a session on the application can be user-specific.
3. Whenever a user creates an account, a "details.xml" file containing the details of that user is created inside a folder with a name equivalent to the UserID of that user.
   Example: If suppose a user named "Tom" with a UserID "tom10" creates an account, a folder named "tom10" will be created in the "user_account" folder and the details.xml file will be stored there. 
4. This approach creates a unique directory for each user.
5. Whenever a user creates a portfolio, it will be stored in the user's directory.
6. The user can then access their portfolios by logging into the application.
7. This design style makes the application secure for each user and each session on the application is a dedicated session.

C) Getting Stock Prices through API Calls:
1. APIs provide access to real-time and historical stock data.
2. This feature can help the users to make informed decisions about which stocks to buy or sell.
3. Getting stock prices through APIs can also help the users to compare the value of their current portfolio with the value of the portfolio at any other date in the past.

D) Considering only the Closing value of the stocks:
1. The closing value will provide the user with the exact value of the stocks on a given date (except Saturdays/Sundays/Public holidays).
2. While the opening value may vary, the closing value is a more appropriate metric to evaluate the value of a portfolio.

E) Creating Portfolio objects:
1. Creating portfolio objects eliminates the need for creating variables to store data.
2. Portfolio objects help in storing data together.

F) Creating user objects:
1. Creating user objects helps in persisting portfolios created by each user.

G) Setters and Getters:
1. They help in hiding the state of an object and prevent unauthorized, direct access to them.
2. They help in retrieving value of variables outside the encapsulating class.

H) Using listOfStockTickers.csv file:
1. The stock tickers are validated with the help of the csv file.
2. Reduces the number of calls to the API.

I) Using builder class:
1. It helps in maintaining copies of all fields that would be required to build the object.
2. It maintains default values for all the methods.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
The program comprises three different packages: Model, View, and Controller.
These packages consist of their respective interfaces and classes.

A) The Model consists of seven interfaces:

1. User interface
- This interface represents a User of a Portfolio Management system.
- The UserImpl class implements the User interface.
- The UserImpl class consists of a no-argument, empty constructor and a private constructor that takes firstName, lastName, middleName, userID, and password as arguments.
- The UserImpl class uses setters and getters for setting and getting the values of the variables.

2. Portfolio interface
- This interface represents the methods supported by a Portfolio in a Portfolio Management application.
- The PortfolioImpl class implements the Portfolio interface.
- The PortfolioImpl class consists of a private constructor that initializes the portfolioName, stockID, stockName, and stockQuantity.
- The PortfolioImpl class uses setters and getters for setting and getting the values of the variables.
- The savePortfolio method in the PortfolioImpl class writes and stores the Portfolio details in an xml file.
- The getPortfolioObject method is an XML parser method that converts data stored in xml files into a human-readable format.

3. PortfolioModel interface
- This interface represents a Portfolio which offers various operations on the portfolio.
- The Model class implements the PortfolioModel interface.
- This class implements functionalities like createUser, login, createPortfolio, showPortfolios, selectPortfolio, showTotalInvestment, uploadPortfolio, getStockPricesOnDate.

4. StockMarketAPI interface
- This interface represents an API for retrieving the prices of a stock.
- This interface represents the getPrice method. This method gets the price of stocks from the API on a particular date.
- The APIAlphaVantage class implements the StockMarketAPI interface.
- The API key is defined in this class.
- This class consists of a public constructor APIAlphaVantage which takes stockSymbol as an argument.

5. PlotChart interface
- This interface represents the methods required to plot a barchart for analyzing the performance of a portfolio over a period of time.
- This interface represents the getTimeStamp and getStarCount methods.
- The getTimeStamp method is to get the timestamp from the start date to the end date.
- The getStarCount method is to get the count of stars which represents the performance of a portfolio at a particular period in time.
- The PlotChartBarImpl Class implements the PlotChart interface.

6. SaveUserDetails Interface
- This interface represents the methods required to save and validate the details of a user.
- The SaveuserDetailsImpl Class implements the SaveUserDetails interface.
- This interface represents the saveUserDetails method and getUserDetails method.
- The saveUserDetails method saves the details of the user in XML format.
- The getUserDetails method validates and returns a user object.

7. TimeBasedInvestmentStrategy Interface
- The interface represents the high level time-based long-term investment strategies which are primarily based on a fixed amount given to be invested periodically during a time interval.
- The DollarCostAvgStartToFinishCategory class implements the TimeBasedInvestmentStrategy interface and provides start-to-finish investment strategy where a fixed amount is invested periodically in the given duration.

B) The Controller consists of two interfaces:

1. PortfolioController interface
- This interface represents a Controller of a Portfolio management application.
- This interface represents the go method that gives control to the controller.
- The Controller class implements the PortfolioController method.
- It controls the flow of a Portfolio Management application. 
- It takes inputs and passes it to the model and receives inputs from the model and passes them to view for displaying to the user.
- The Controller class consists of a public constructor Controller which takes one argument for the input.
- The go method initializes the model and the view.
- The Controller class delegates the tasks to the model and the view.
- The Controller class uses the Scanner class to get the user input.

2. IControllerGUI interface
- This interface represents all the methods required to support the functionalities of inflexible and flexible portfolios for the Graphical User Interface.
- The ControllerGUI class implements the IControllerGUI interface.

C) The View consists of two interfaces:

1. PortfolioView interface
- This interface represents the view section of the Portfolio Management system.
- This interface represents all the methods required to display outputs and results to the user.
- The View class implements the PortfolioView interface.
- The View class consists of a public constructor View that takes one argument for the output.

2. IView interface
- This interface represents the view section of the Portfolio Management system.
- It represents all the methods required to display outputs and results to the user through the Graphical User interface.
- The JFrameView class implements the IView interface and extends the JFrame.

D) PortfolioManagementMain class
- This is a class to hold the Main method of the Portfolio management system.
- The main method creates model and view objects.
- It gives control to the controller by passing model and view objects as parameters.
- The PortfolioManagementMain method does not directly implement any functionality.
- It does not directly interact with the user. The Controller does this.
- It does not directly show any results. The View does this.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Validations:

Inflexible Portfolio.

A) User Creation:
1. First Name, Middle Name, and Last Name cannot be empty fields.
2. User ID cannot be an empty field.
3. Password cannot be an empty field.
4. User ID should be unique for each user.
5. If a UserID already exists, an IllegalArgumentException is thrown.

B) Login:
1. User ID entered during login should match with the User ID entered during user creation (not case-sensitive).
2. Password entered during login should match with the password entered during user creation (case-sensitive).
3. If the UserID entered is invalid, an IllegalArgumentException is thrown.
4. If the password entered is invalid, an IllegalArgumentException is thrown.
5. If an invalid user tries to log into the system, an IllegalArgumentException is thrown.

C) Create Portfolio:
1. Portfolio Name cannot be an empty field.
2. PortfolioName should be unique.
3. Ticker cannot be an empty field.
4. Ticker should be present in the listOfStockTickers.csv file.
5. Stock Name cannot be an empty field.
6. Stock Quantity cannot be an empty field.
7. Stock Quantity can only be a non-negative value.
8. Stock Quantity cannot be zero.
9. If a Portfolio does not exist, an IllegalArgumentException is thrown.
10. If the StockID ticker is invalid, an IllegalArgumentException is thrown.
11. If the Stock quantity is invalid, an IllegalArgumentException is thrown.
12. If the Portfolio Name already exists, an IllegalArgumentException is thrown.

D) Select Portfolio:
1. Date field cannot be empty.
2. Date entered should be in YYYY-MM-DD format.
3. Date entered should not be a future date.
4. If the date entered by the user is of an invalid format, an IllegalArgumentException is thrown.
5. If the date entered by the user is a future date, an IllegalArgumentException is thrown.
6. If the user enters today's date, an IllegalArgumentException will be thrown.

E) Upload Portfolio:
1. Filepath cannot be empty.
2. Filepath should have a forward slash right before the name of the file in the path.
(C:\Users\User\Downloads/silver.xml OR C:/Users/User/Downloads/silver.xml OR C:\Users/User\Downloads/silver.xml)
3. File should be of .xml format.
4. If an invalid filepath is provided, an IllegalArgumentException is thrown.
5. If the file uploaded is of incorrect format, an IllegalArgumentException is thrown.

Flexible Portfolio:

A) Buy Stocks:
1. Stock Name, Stock Ticker, Quantity, and transaction date cannot be empty.
2. Stock Ticker should be valid.
3. Stock Quantity can only be a non-negative value.
4. Transaction date format should be valid.
5. Transaction date should not be a future date.

B) Sell stocks:
1. Stock Ticker, Quantity, and transaction date cannot be empty.
2. Stock Ticker should be valid.
3. Stock Quantity can only be a non-negative value.
4. Transaction date format should be valid.
5. Transaction date should not be a future date.

C) Get value on date:
1. Date cannot be empty.
2. Date format should be valid.
3. Date should not be a future date.

D) Get cost-basis:
1. Date cannot be empty.
2. Date format should be valid.
3. Date should not be a future date.

E) Show Portfolio Composition:
1. Date cannot be empty.
2. Date format should be valid.
3. Date should not be a future date.

F) Graph Analysis:
1. Start date and end date cannot be empty.
2. Date format should be valid.
3. Date should not be a future date.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Assumptions and Considerations:

Inflexible Portfolio: (for text-based interface)

1. Stock markets are closed on Saturdays and Sundays.
2. If a user enters a date that is either a Saturday or a Sunday, return the price of stocks as of the most recent valid date (Friday).
3. The user has the liberty to name a particular stock provided the ticker symbol is valid. 
4. If the user enters a date when the stock market is closed, the total value returned is 0.
5. While uploading a file, if the file already exists in the directory, the old file is replaced by the new one.
6. Due to the API restrictions, the stock prices of only 5 stocks can be retrieved in a minute.
7. Since, the closing value is used in the application, the portfolio can't be examined for today's date.
8. If the user gives a fraction input for stock quantity, the floor value of the stock quantity will be considered.
9. Filepath should have a forward slash right before the name of the file in the path (..../xyz.xml).

Flexible Portfolio: (for text-based interface)

1. Date entered cannot be a future date.
2. The maximum value and the minimum value in the graph will be displayed using 50 and 1 stars respectively. The scale used is relative and is decided based on the min and max values in the portfolio.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Testing:

1. Model - Tested all the classes in the model package for all the features.
2. Controller - Tested the Controller for all the features.
3. View - Tested the View for all the features.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Limitations:

1. First Name, Last Name, Middle Name, Portfolio name, Stock Name need to be entered without spaces for text-based interface.
