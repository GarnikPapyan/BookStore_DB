package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/BookStore_DB";
        String user = "postgres";
        String password = "admin077";
        // connecting sql server
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Successful connection to PostgreSQL ");
            //Retrieve a list of all books sold, including the book title, customer
            //name, and date of sale..
            String sqlQuery =   "SELECT books.Title,  Customers.Name, Sales.DateOfSale " +
                    "FROM Sales " + "JOIN books ON Sales.bookID = books.bookID " +
                    "JOIN customers ON Sales.CustomerID = customers.CustomerID ";
            //Find the total revenue generated from each genre of books.
            String sqlAggregation = "SELECT books.Genre, SUM(Sales.TotalPrice) AS SumPrice " +
                    "FROM Sales " + "JOIN books ON Sales.bookID = books.bookID " +
                    "GROUP BY books.Genre ";
            // here you can already make different requests for sqlQuery
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    String bookTitle = resultSet.getString("Title");
                    String customerName = resultSet.getString("Name");
                    String dateOfSale = resultSet.getString("DateOfSale");

                    System.out.println("Book: " + bookTitle +" " + "Customer Name: " + customerName + " " + "Date of Sale: " + dateOfSale);

                    System.out.println();
                }
            }

            // here you can already make different requests for sqlAggregation
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAggregation);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    String bookGenre = resultSet.getString("Genre");
                    double totalPrice = resultSet.getDouble("SumPrice");
                    System.out.println("Book Genre: " + bookGenre + " = " + totalPrice + "$");

                }
            }

        } catch (SQLException e) {
            System.out.println("Connection error to PostgreSQL " + e.getMessage() );
        }
    }

}