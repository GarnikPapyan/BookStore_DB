package org.example;

import java.sql.Connection;
import java.sql.*;

public class CreateTables {
    private final Connection connection ;
    public CreateTables(Connection connection) {
        this.connection = connection;
    }

    public void createBooksTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Books(" +
                    "BookID SERIAL PRIMARY KEY," +
                    "Title VARCHAR(250)," +
                    "Author VARCHAR(250)," +
                    "Genre VARCHAR(250)," +
                    "Price DOUBLE PRECISION," +
                    "QuantityInStock INT)");
        }

    }

    public void insertBook() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Books(Title,Author,Genre,Price,QuantityInStock) VALUES " +
                    "('War and Peace','Leo Tolstoy','novel',19.55,12), " +
                    "('Crime and Punishment','Fyodor Dostoevsky','novel',11.99,15), " +
                    "('1984','George Orwell','dystopia',9.55,9), " +
                    "('Ulysses','James Joyce','fantasy',25,55), " +
                    "('Harry Potter and the Philosophers Stone','Rowling','fantasy',30,12), " +
                    "('One Thousand and One Nights','Eastern','fantasy',9.99,21), " +
                    "('To Kill a Mockingbird','Harper Lee,','novel',27.99,15), " +
                    "('The Great Gatsby','Scott Fitzgerald','novel',55.5,7), " +
                    "('The Catcher in the Rye','J.D. Salinger','novel',29.55,6), " +
                    "('The Name of the Wind','Patrick Rothfuss','fantasy',13.5,8)");
        }
    }

    public  void createCustomersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Customers(" +
                    "CustomerID SERIAL PRIMARY KEY," +
                    "Name VARCHAR(250)," +
                    "Email VARCHAR(250)," +
                    "Phone VARCHAR(250))");
        }

    }
    public void  insertCustomer() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Customers(Name,Email,Phone) VALUES " +
                    "('Anna','anna@gmail.com','+3749315449'), " +
                    "('Ashot','ashot@gmail.com','+3749315489'), " +
                    "('Valod','anna@gmail.com','+3749347449'), " +
                    "('Tatev','tatev@gmail.com','+3749315789'), " +
                    "('Tigran','tigran@gmail.com','+3749373349')");
        }
    }
    public void createSalesTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Sales (" +
                    "SaleID SERIAL PRIMARY KEY," +
                    "BookID INT NOT NULL REFERENCES books(BookID)," +
                    "CustomerID INT NOT NULL REFERENCES customers(CustomerID)," +
                    "DateOfSale VARCHAR(50)," +
                    "QuantitySold INT," +
                    "TotalPrice DOUBLE PRECISION)");
        }
    }
    public void insertSales() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Sales(BookID,CustomerID,DateOfSale,QuantitySold,TotalPrice) VALUES " +
                    "(1, 1, '2023-01-01', 5, 5 * (SELECT Price FROM Books WHERE BookID = 1)), " +
                    "(2, 2, '2023-01-02', 3, 3 * (SELECT Price FROM Books WHERE BookID = 2)), " +
                    "(3, 3, '2023-01-03', 2, 2 * (SELECT Price FROM Books WHERE BookID = 3))");
        }
    }
    public  boolean checkDataExistence() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM books")) {
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }
}
