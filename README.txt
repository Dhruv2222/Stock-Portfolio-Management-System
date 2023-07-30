
Name of the project: Assignment 4 Stocks (Part 1)
Group Members: Vishant Ketan Mehta - (mehta.visha@northeastern.edu) & Dhruv Mehul Doshi (doshi.dhru@northeastern.edu) 

README File lists the features implemented in the program.

Overview: 
- This is a "Stocks" application built in a Model-View-Controller style. 
- This is a text-based interactive application that allows the user to communicate with the program and use the different functionalities provided.
- The application uses a web-based API: Alpha Vantage to obtain stock-related data.
- Each publicly traded company's stock is given a unique "ticker symbol".
- This application allows the user to create portfolios by entering stock details.
- The total value of a user's holdings of a stock is the closing price of the stock on a given day, multiplied by the number of shares of that stock that the user owns.
- The total value of a portfolio is the sum of the values of the individual holdings.
- This application allows the user to create and track multiple portfolios.
- The program also allows the user to externally create, upload, and track a file/portfolio but it can only be of the ".xml" format.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Assignment 4:

Features completed:

1. Create User  
- This feature creates a user by taking the First Name, Middle Name, Last Name, UserID, and Password as inputs from the user.
- It is mandatory for a user to create an account to use the functionalities of the application.
- The Name, UserID, and password fields cannot be empty.
- The UserID of each user is supposed to be unique, i.e, no two users can have the same UserID.
- On entering an already existing UserID, an error message will be thrown.

2. Login 
- This feature validates the credentials of a user and logs the user into the system.
- Once the user has created an account, they can log into the system by entering the appropriate credentials, i.e., the correct UserID and password.
- On entering an incorrect UserID or password, an error message will be thrown.
- Authentication of a particular user requires: 
- The UserID entered during login to match with the UserID that was entered during user creation. 
- The Password entered during login to match with the password that was entered during user creation.

3. Create Portfolio 
- This feature allows the user to create one or more Portfolios by taking the Portfolio Name, Stock Ticker, Stock Name, and Stock Quantity as inputs from the user.
- Once the user has logged into the system, they can create a portfolio by selecting the appropriate option "Create portfolio" from the menu.
- The fields mentioned above cannot be empty.
- The portfolio names are supposed to be unique for a particular user, i.e., no two portfolios can have the same name for a particular user.
- The Stock ticker that the user enters should be a part of the listOfStockTickers.csv. Any invalid input for the stock ticker field will throw an error message.
- The User has the flexibility of assigning names to the stocks, provided an appropriate stock ticker is entered by the user.
- The Stock Quantity has to be a non-negative value else an error message will be thrown. 
- If the user gives a fraction input for stock quantity, the floor value of the stock quantity will be considered.
- Once the Portfolio has been created, shares cannot be added or removed from the portfolio. 

4. Save Portfolio (Persist a Portfolio)
- This feature saves the Portfolio in the user-specific directory.
- After adding the stock details for one stock, the user can either choose to add more stocks into the portfolio or save the portfolio.
- The user can select the appropriate option "Save Portfolio" from the menu to save the portfolio.
- The user can verify if the portfolio was saved by selecting the "View portfolios" option.
- If the user wants to retrieve the portfolio, they can select the "Select a Portfolio" option from the menu.

5. Upload Portfolio 
- This feature allows the user to load an external file into the program by entering the path of the file.
- The file should only be of type ".xml" and it should be correctly formatted.
- The path of the file should have a forward slash right before the name of the file in the path.
- The rest of the path can either have forward slashes or backward slashes.
- On entering a valid file path, the user will get a message that the file has been uploaded successfully.
- The user can then view the composition of the file.
- The user will have two options available: to view the composition of the file on a specific date or to view the previous day's composition of the file. 
- On selecting the appropriate option, the composition of the file will be displayed to the user.

6. Show Portfolios 
- This feature shows the user a list of portfolios that are associated with them.
- After successfully creating and saving a portfolio, the user can select the "View portfolios" option from the menu to view the list of portfolios that the user has.

7. Select Portfolio (Examine the composition of a portfolio and Determine the total value of a portfolio on a certain date)
- This feature allows the user to select a particular portfolio from the list of portfolios and examine the composition of that portfolio.
- To select a particular portfolio, the user has to create a portfolio first by entering the name of the portfolio and the details of the stocks.
- Once, the user has created a portfolio successfully, they have to save the portfolio.
- After saving the portfolio, the user can select the "View portfolios" option from the menu.
- A list of portfolios associated with that user will be displayed.
- The user can then select a particular portfolio from the list by entering the appropriate option "Select a portfolio" and view the composition/value of the portfolio.
- The user will get two options: to view the composition/value of the file on a specific date or to view the previous day's composition/value of the file. 
- The user can enter the option according to their requirement. Once entered, the composition and the total value of the portfolio will be made available to the user.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Assignment 5:

Additional features:

1. Purchase stocks
- This feature allows the user to purchase a specific number of shares of a specific stock on a specified date.
- The buying price of the stocks are computed according to the date of the purchase.
- Once the stocks are purchased successfully, they are added to the portfolio.
- The user can select the Buy a stock option from the menu to purchase stocks.
- The user needs to enter the date for purchasing stocks.

2. Sell stocks
- This feature allows the user to sell a specific number of shares of a specific stock on a specified date from a given portfolio.
- The selling price of the stocks are computed according to the date of selling.
- The user can select the Sell a stock option from the menu to sell stocks.
- The user can simply enter the date for selling the stocks from a particular portfolio.

3. Cost basis
- This feature determines the total amount of money invested by a user in a portfolio by a specific date.
- This accounts for all the purchases made in the portfolio till that date.
- The user can select the Get cost-basis option from the menu to view the cost-basis.
- The user can simply enter the date for which they want to get the cost basis for.

4. Value of Portfolio
- This feature computes the total value of a portfolio on a specific date.
- The user can select the Get value on a date option from the menu to view the value of the portfolio.
- It requires the user to enter the date for which they want to find out the value of the portfolio.

5. View Composition
- This feature shows the composition of an advanced portfolio.
- The Stock Ticker, Stock Name, and Stock Quantity of the stocks are displayed to the user.
- The user can select the Show portfolio composition option from the menu to view the composition.

6. Graph Analysis
- This feature shows the performance of a portfolio over time.
- A bar chart is displayed to the user.
- The number of asterisks on each line is a measure of the value of the portfolio at that timestamp.
- The end of the bar chart shows the scale in terms of how many dollars are represented by each asterisk.
- This feature requires the user to enter the period for which they want to analyze the performance of the portfolio.

7. Commission Fee
- The user can give an input of commission fee they will be charged per transaction while creating a new user account.
- This commission fee will then be used to compute the cost-basis for the portfolio.
- Commission fee is a fixed charge per transaction.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Assignment 6:

Additional features:

1. Invest fixed amount:
- This feature allows the user to invest a fixed amount into an existing portfolio containing multiple stocks using a specified weight for each stock in the portfolio.
- The user is asked to enter the Stock ticker, stock name, and ratio for each stock.
- The fixed amount strategy allows purchases of fractional shares depending on the amount specified.

2. Dollar-cost Averaging:
- This feature allows the creation of "start-to-finish" dollar-cost-averaging as a single operation.
- This feature allows the user to enter an amount, an interval, and a time range.
- It also allows the suer to create an ongoing strategy.
- The user may or may not specify an end date.

3. Graphical User Interface:
--> The graphical user interface offers the following functionality:
- The ability to create an inflexible and a flexible portfolio.
- The ability to buy/sell stocks by specifying the number of shares, and date (with or without commission fees).
- The ability to query the cost basis and value of a flexible portfolio at a certain date.
- The ability to save and retrieve flexible portfolios from files.
- The ability to invest a specific amount in an existing flexible portfolio on a specific date by specifying the weights of how that money should be invested in each stock inside that portfolio.
- The ability to create a portfolio using dollar-cost averaging as specified above, and query cost basis and value of such a portfolio at a specific date.