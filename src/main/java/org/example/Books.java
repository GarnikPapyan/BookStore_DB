package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Books {
     private final Connection connection ;
     public Books(Connection connection) {
        this.connection = connection;
     }
     Scanner scanner = new Scanner(System.in);
    public void  addNewRow() throws SQLException {
            String sqlAdd = "INSERT INTO Books(Title,Author,Genre,Price,QuantityInStock) VALUES (?,?,?,?,?) ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sqlAdd)){
                boolean out = false;
                System.out.println("Add book Title");
                String title = scanner.nextLine();
                System.out.println("Add book Author");
                String author = scanner.nextLine();
                System.out.println("Add book Genre");
                String genre = scanner.nextLine();

                double price = 0.0;
                while (!out) {
                    try {
                        System.out.println("Add book Price");
                        price = scanner.nextDouble();
                        out = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Plz enter only number ");
                        scanner.next();
                    }
                }
                out = false;
                int quantityInStock = 0;
                while (!out) {
                    try {
                        System.out.println("Add book QuantityInStock");
                        quantityInStock = scanner.nextInt();
                        out = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Plz enter only number ");
                        scanner.next();
                    }
                }

                preparedStatement.setString(1,title);
                preparedStatement.setString(2,author);
                preparedStatement.setString(3,genre);
                preparedStatement.setDouble(4,price);
                preparedStatement.setInt(5,quantityInStock);

                preparedStatement.executeUpdate();
                System.out.println("Added was successful");
            }
    }


    public void deleteRow() throws SQLException {
        String sqlDelete = "DELETE FROM books WHERE BookId = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)){
            int bookId = -1;
            boolean out = false;
            while (!out) {
                try {
                    System.out.println("Write the ID of the book you want to delete");
                    bookId = scanner.nextInt();
                    if(validBookId(bookId)) {
                        out = true;
                    } else  {
                        System.out.println("Plz enter the valid book ID!!! ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Plz enter only ID ");
                    scanner.next();
                }
            }
            preparedStatement.setInt(1,bookId);

            if(preparedStatement.executeUpdate() > 0) {
                System.out.println("Deleted was successful");
            } else  {
                System.out.println("Book with BookID " + bookId + " not found.");
            }

        }
    }

    public void createListGenre() throws SQLException {
        String sqlGenre = "SELECT * FROM books WHERE Genre = ? ";
        String sql = "SELECT DISTINCT Genre FROM books ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlGenre);
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement1.executeQuery()) {
            List<String> myList = new ArrayList<>();
            System.out.println("we have books fromm this genre ");
            while (resultSet.next()) {
                String myGenre = resultSet.getString("Genre");
                myList.add(myGenre);
                System.out.print(myGenre + "\t");
                System.out.println();
            }

            System.out.println("Enter your favorite genre");
            String genre = " ";
            boolean isValid = false;
            while (!isValid) {
                genre = scanner.nextLine();
                    if(myList.contains(genre)) {
                        isValid = true;
                    } else {
                        System.out.println("Try again and choose type genre from list");
                    }
            }
            preparedStatement.setString(1,genre);
            try(ResultSet resultSet2 = preparedStatement.executeQuery()) {
                System.out.print("We have books from this Genre- " + genre + "\n");
                while (resultSet2.next()) {
                    System.out.println(" Title- " + resultSet2.getString("Title")
                            + ", Author- " + resultSet2.getString("Author"));
                }
            }

        }
    }

    public void updateBookDetails() throws SQLException {
        String sqlUpdate = "UPDATE Books SET QuantityInStock = ? WHERE BookId = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            int bookId = 0;
            boolean out = false;
            while (!out) {
                System.out.println("Enter the BookId whose quantity you want to change");
              try {
                  bookId = scanner.nextInt();

                  if (validBookId(bookId)) {
                      out = true;
                  } else {
                      System.out.println("Not Valid book ID, try again!");
                  }
              } catch (InputMismatchException e) {
                  System.out.println("Plz enter only bookID ");
                  scanner.next();
              }
            }
            out = false;
            int quantityInStock = 0;
            System.out.println("Enter the new quantity in stock ");
            while (!out) {
                try {
                    quantityInStock = scanner.nextInt();
                    if (quantityInStock >= 0) {
                        out = true;
                    } else {
                        System.out.println("Not valid QuantityInStock, try again!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Plz enter only NUMBER ");
                    scanner.next();
                }
            }

            preparedStatement.setInt(1,quantityInStock);
            preparedStatement.setInt(2,bookId);
            preparedStatement.executeUpdate();
            System.out.println("Book details update was successful!");
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

}
