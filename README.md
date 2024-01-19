# Bookstore Management System

The Bookstore Management System is a Java application for managing book sales, customer information, and book inventory. The system uses a PostgreSQL database to store data and includes functionalities such as adding/updating books, managing customers, processing sales, and handling triggers for stock quantity updates.

## Prerequisites

Before running the application, ensure you have the following:

- Java Development Kit (JDK) installed
- PostgreSQL database installed and running
- JDBC driver for PostgreSQL (included in the `lib` folder)

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/bookstore-management-system.git

  
Конечно, вот пример README.md файла для вашего проекта:

markdown
Copy code
# Bookstore Management System

The Bookstore Management System is a Java application for managing book sales, customer information, and book inventory. The system uses a PostgreSQL database to store data and includes functionalities such as adding/updating books, managing customers, processing sales, and handling triggers for stock quantity updates.

## Prerequisites

Before running the application, ensure you have the following:

- Java Development Kit (JDK) installed
- PostgreSQL database installed and running
- JDBC driver for PostgreSQL (included in the `lib` folder)

## Setup

1. Clone the repository:
   
   ```bash
   git clone https://github.com/your-username/bookstore-management-system.git
   
2. Set up the database:

- Create a PostgreSQL database named Bookstore_DB.
- Execute the SQL script in db_setup.sql to create the necessary tables.

3. Update the database connection details:

- Open src/main/java/org/example/DatabaseConfig.java.
- Modify the JDBC_URL, USERNAME, and PASSWORD fields with your PostgreSQL credentials.

## Classes

1. Books - In Book Management you can update books details ,see list of book from genre,add and delete books.
2. CustomerManagement - In Customer Management you can update customer details ,view purchase history,add and delete customer.
3. SalesProcessing - In Sales Management you can buying new books,generate a report of all books sold,calculate total revenue by genre.
4. CreateTables - In this class a table is created and a default value is inserted so that the tables are not allowed and the trigger itself is launched
5. Main - My application should be operated through a CLI (Command Line Interface), which will allow the use of all functionalities described in the technical specifications.

1. Books class has functions`
   
- Update books details.Using the book ID you can change the number of books in the warehouse.
- Create list from genre.You will be given a list of book genres and by choosing one from them, you will receive existing books from this genre in the warehouse.
- Add new book.With this you can add a new book write the name, genre, author of the book.
- Delete book.Using this book ID you can delete it.

2. CustomerManagement class has functions`
   
- Update customer details.Using the customer ID you can change the phone number and Email of customer.
- View purchase history.According to our selected client ID, it shows how many books he bought when and the total price.
- Add new customer.With this you can add a new customer write the name,Email and phone number.
- Delete customer.Using this customer ID you can delete it.

3. SalesProcessing class has functions`

- Buying new books.We buy an input book, the ID of the book we want to buy, the ID of the client who is buying, the year-month-day of purchase, and the quantity. The trigger automatically calculates and deducts from the warehouse the quantity that is being purchased.
- Report of all sold books.This is a report on books being purchased and who bought them and when.
- Total revenue by genre.Report on how many books sold in total by genre.

  
