package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/BookStore_DB";
        String user = "postgres";
        String password = "admin077";
        // connecting sql server
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Successful connection to PostgreSQL ");
            Books books = new Books(connection);
            books.createListGenre("novel");

            //createListGenre(connection,"novel");
            commandLine();
            //Retrieve a list of all books sold, including the book title, customer
            //name, and date of sale..
            String sqlQuery =   "SELECT books.Title,  Customers.Name, Sales.DateOfSale " +
                    "FROM Sales " + "JOIN books ON Sales.bookID = books.bookID " +
                    "JOIN customers ON Sales.CustomerID = customers.CustomerID ";
            //Find the total revenue generated from each genre of books.
            String sqlAggregation = "SELECT books.Genre, SUM(Sales.TotalPrice) AS SumPrice " +
                    "FROM Sales " + "JOIN books ON Sales.bookID = books.bookID " +
                    "GROUP BY books.Genre ";
           String sql = "SELECT * FROM books ";
            // here you can already make different requests for sqlQuery
           // int count = 7;
           // int id = 4;
          // updateBookDetails(connection,count,id);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                 ResultSet resultSet = preparedStatement.executeQuery()) {

               // createTrigger(connection);
                while (resultSet.next()) {
                    String bookTitle = resultSet.getString("Title");
                    String customerName = resultSet.getString("Name");
                    String dateOfSale = resultSet.getString("DateOfSale");

                //    System.out.println("Book: " + bookTitle +" " + "Customer Name: " + customerName + " " + "Date of Sale: " + dateOfSale);
                    System.out.println();
                }
            }

            // here you can already make different requests for sqlAggregation
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAggregation);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    String bookGenre = resultSet.getString("Genre");
                    double totalPrice = resultSet.getDouble("SumPrice");
                  //  System.out.println("Book Genre: " + bookGenre + " = " + totalPrice + "$");

                }
            }

        } catch (SQLException e) {
            System.out.println("Connection error to PostgreSQL " + e.getMessage() );
        }
    }

    public static void createTrigger(Connection connection) throws SQLException {
        String sqlFunction = " CREATE OR REPLACE FUNCTION update_books_quantity_in_stock() " +
                " RETURNS TRIGGER AS $$ " +
                " BEGIN " +
                "    UPDATE Books " +
                "    SET QuantityInStock = QuantityInStock - NEW.QuantitySold " +
                "    WHERE BookID = NEW.BookID; " +
                "    RETURN NEW; " +
                " END;           " +
                " $$ LANGUAGE plpgsql; " ;
        String sqlTrigger = "CREATE TRIGGER update_books_quantity " +
                "AFTER INSERT ON Sales " +
                "FOR EACH ROW " +
                "EXECUTE FUNCTION update_books_quantity_in_stock(); " ;
        try(Statement statement = connection.createStatement()) {
            statement.execute(sqlFunction);
            statement.execute(sqlTrigger);
        }


    }

    public static void updateBookDetails(Connection connection,int quantityInStock,int bookId) throws SQLException {
        String sqlUpdate = "UPDATE Books SET QuantityInStock = ? WHERE BookId = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setInt(1,quantityInStock);
            preparedStatement.setInt(2,bookId);
            preparedStatement.executeUpdate();
        }
    }

//    public static void createListGenre(Connection connection,String genre) throws SQLException {
//        String sqlGenre = "SELECT * FROM books WHERE Genre = ? ";
//        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlGenre)) {
//            preparedStatement.setString(1,genre);
//            try(ResultSet resultSet = preparedStatement.executeQuery()) {
//                System.out.print("We have books from this Genre- " + genre + "\n");
//                while (resultSet.next()) {
//                            System.out.println(" Title- " + resultSet.getString("Title")
//                            + ", Author- " + resultSet.getString("Author"));
//                }
//            }
//        }
//    }

    public static void commandLine() {
        System.out.println("You can choose from these three options what to do ");
        System.out.print("1. Book Management \n");
        System.out.print("2. Customer  Management \n");
        System.out.print("3. Sales Management \n");
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        while (!out) {
            String str =  scanner.nextLine();
            switch (str) {
                case "1" -> {
                    System.out.println("In Book Management you can update books " +
                            "details or see list of book from genre ");
                    System.out.print("1. Update books details \n");
                    System.out.print("2. Create list from genre \n");
                    while (!out) {
                        String str2 = scanner.nextLine();
                        switch (str2) {
                            case "1" -> {
                                System.out.println("yoo");
                                out = true;
                            }
                            case "2" -> {
                                System.out.println("boo");
                                out = true;
                            }
                            default -> System.out.println("1 kam 2");
                        }
                    }

                    out = true;
                }
                case "2" -> {
                    System.out.println("ddd");
                    out = true;
                }
                case "3" -> {
                    System.out.println("www");
                    out = true;
                }
                default -> System.out.println("Plz enter 1 , 2 or 3");
            }
        }
        System.out.println("the end");
    }

}