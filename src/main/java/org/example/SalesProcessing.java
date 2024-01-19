package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SalesProcessing {
    private final Connection connection;
    public SalesProcessing(Connection connection) {
        this.connection = connection;
    }
    Scanner scanner = new Scanner(System.in);

    public void newSales() throws SQLException {
        String sqlNewSale = "INSERT INTO sales(BookID,CustomerID,DateOfSale,QuantitySold,TotalPrice) " +
                "VALUES (?,?,?,?,?) " ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlNewSale)){
            boolean out = false;
            System.out.println("If you want buy enter ` BookId");
            int bookId = -1;
            int customerId = -1;
            String dateOfSale = "";
            int quantitySold = -1;
                while (!out) {
                    try{
                        bookId = scanner.nextInt();
                        if(validBookId(bookId)) {
                        while (!out) {
                            try {
                                System.out.println("enter customer ID ` ");
                                customerId = scanner.nextInt();
                                if(validCustomerId(customerId)) {
                                    scanner.nextLine();
                                    System.out.println("Enter buying day in this format ` YYYY-MM-DD ");
                                    dateOfSale = scanner.nextLine();
                                    while (!out) {
                                        System.out.println("Enter how many pieces of this book you want");
                                        try{
                                            quantitySold = scanner.nextInt();
                                            if(quantitySold > 0 && quantitySold <=getQuantityInStock(bookId)) {
                                                out = true;
                                            } else {
                                                System.out.println("there are so many books out of stock, try again");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Plz enter only NUMBER how much you want buy");
                                            scanner.next();
                                        }
                                    }
                                }else {
                                    System.out.println("Plz enter valid customer ID");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Plz enter Number for Customer ID");
                                scanner.next();
                            }
                        }
                    } else {
                        System.out.println("Plz enter valid book ID");
                    }
                } catch (InputMismatchException e) {
                        System.out.println("enter only Number from ID");
                        scanner.next();
                    }
            }
            preparedStatement.setInt(1,bookId);
            preparedStatement.setInt(2,customerId);
            preparedStatement.setString(3,dateOfSale);
            preparedStatement.setInt(4,quantitySold);
            double totalPrice = getBookPrice(bookId)*quantitySold;
            preparedStatement.setDouble(5,totalPrice);
            preparedStatement.executeUpdate();
            System.out.println("Buying was successful");
        }
    }

    public void revenueByGenre() throws SQLException {
        String sqlAggregation = "SELECT books.Genre, SUM(Sales.TotalPrice) AS SumPrice " +
                "FROM Sales " + "JOIN books ON Sales.bookID = books.bookID " +
                "GROUP BY books.Genre ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAggregation);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             System.out.println("Here are the books sold by genre");
            while (resultSet.next()) {
                String bookGenre = resultSet.getString("Genre");
                double totalPrice = resultSet.getDouble("SumPrice");
                System.out.println("Book Genre: " + bookGenre + " = " + totalPrice + "$");
            }
        }
    }

    public void reportOfAllBookSold() throws SQLException {
        String sqlReport =   "SELECT books.Title,  Customers.Name, Sales.DateOfSale " +
                "FROM Sales " + "JOIN books ON Sales.bookID = books.bookID " +
                "JOIN customers ON Sales.CustomerID = customers.CustomerID ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlReport);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("Title");
                String customerName = resultSet.getString("Name");
                String dateOfSale = resultSet.getString("DateOfSale");
                System.out.println("Book: " + bookTitle +" " + "Customer Name: " + customerName + " " + "Date of Sale: " + dateOfSale);
                System.out.println();
            }
        }
    }

    public boolean validBookId(int bookid) throws SQLException {
        String sqlValidBook = "SELECT BookID FROM books ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlValidBook);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            List <Integer> myList = new ArrayList<>();
            while (resultSet.next()) {
                myList.add(resultSet.getInt("BookID"));
            }
            return myList.contains(bookid);
        }
    }

    public boolean validCustomerId(int customerid) throws SQLException{
        String sqlValidCustomer = "SELECT CustomerID FROM customers ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlValidCustomer);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            List <Integer> myList = new ArrayList<>();
            while (resultSet.next()) {
                myList.add(resultSet.getInt("CustomerID"));
            }
            return myList.contains(customerid);
        }
    }

    public int getQuantityInStock(int bookId) throws SQLException {
        String sqlQuantity = "SELECT QuantityInStock FROM books WHERE BookID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuantity)) {
           preparedStatement.setInt(1,bookId);
           try(ResultSet resultSet = preparedStatement.executeQuery()){
               if(resultSet.next()) {
                   return resultSet.getInt("QuantityInStock");
               }
           }
        }
        return  -1;
    }

    public double getBookPrice(int BookId) throws SQLException {
        String sqlPrice = "SELECT Price FROM books WHERE BookID = ? ";
        double price = 0.0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlPrice)) {
            preparedStatement.setInt(1,BookId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    price = resultSet.getDouble("Price");
                }
            }
            return price;
        }
    }

}
