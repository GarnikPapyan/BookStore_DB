package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerManagement {
    private final Connection connection ;
    public CustomerManagement(Connection connection) {
        this.connection = connection;
    }
    Scanner scanner = new Scanner(System.in);

    public void addNewRow() throws SQLException {
        String sqlAdd = "INSERT INTO customers(Name,Email,Phone) VALUES (?,?,?) ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlAdd)) {
            System.out.println("Enter the new customer Name ");
            String name = scanner.nextLine();
            System.out.println("Enter the new customer Email ");
            String email = scanner.nextLine();
            System.out.println("Enter the new customer Phone number ");
            String phone = scanner.nextLine();
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,phone);
            preparedStatement.executeUpdate();
            System.out.println("Added was successful");
        }
    }
    public void deleteRow() throws SQLException {
        String sqlDelete = "DELETE FROM Customers WHERE CustomerID = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            int customerID = -1;
            boolean out = false;
            while (!out) {
                try {
                    System.out.println("Write the ID of the customer you want to delete");
                    customerID = scanner.nextInt();
                    if(validCustomerId(customerID)) {
                        out = true;
                    } else {
                        System.out.println("Plz enter the valid customer ID!!! ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Plz enter only ID ");
                    scanner.next();
                }
            }
            preparedStatement.setInt(1,customerID);
            if(preparedStatement.executeUpdate()>0) {
                System.out.println("Deleted was successful");
            } else  {
                System.out.println("Customer with CustomerID " + customerID + " not found.");
            }
        }
    }
    public void customerDetailsUpdate() throws SQLException {
        String sqlUpdate = "UPDATE customers SET Phone = ?, Email = ? WHERE CustomerID = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            int customerId = 0;
            String phone = "";
            String email = "";
            boolean out = false;
            System.out.println("Enter the CustomerID whose phone number and email you want to change");
            while (!out) {
                try {
                    customerId = scanner.nextInt();
                    if (validCustomerId(customerId)) {
                        out = true;
                    } else {
                        System.out.println("Not Valid customer ID, try again!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Plz enter only customer ID ");
                    scanner.next();
                }

                System.out.println("Enter the new customer Email ");
                scanner.nextLine();
                email = scanner.nextLine();
                System.out.println("Enter the new Phone Number ");
                phone = scanner.nextLine();

            }
            preparedStatement.setString(1,phone);
            preparedStatement.setString(2,email);
            preparedStatement.setInt(3,customerId);


            preparedStatement.executeUpdate();
            System.out.println("Customer details update was successful!");
        }
    }

    public void viewPurchaseHistory() throws SQLException {
        String sqlHistory = "SELECT * FROM sales WHERE CustomerID = ? ";
        String sqlCustomer = "SELECT CustomerID FROM sales ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlHistory);
        PreparedStatement preparedStatement1 = connection.prepareStatement(sqlCustomer);
        ResultSet resultSet = preparedStatement1.executeQuery()) {
            List<Integer> myList = new ArrayList<>();

            while (resultSet.next()) {
                myList.add(resultSet.getInt("CustomerID"));
            }
            boolean out = false;
            System.out.println("Enter the ID of the buyer whose purchase history you want to see ");
            int customerID = -1;
            while (!out) {
                try {
                    customerID = scanner.nextInt();
                    if(myList.contains(customerID) && validCustomerId(customerID)) {
                        out = true;
                    } else {
                        System.out.println("ENer valid customer ID");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Enter only Number(ID for Customer)");
                    scanner.next();
                }
            }
            preparedStatement.setInt(1,customerID);
            try(ResultSet resultSet1 = preparedStatement.executeQuery()) {
                while (resultSet1.next()) {
                    System.out.println("| "+ "BookID -" + resultSet1.getInt("BookID") +
                            " | Sold -" + resultSet1.getInt("QuantitySold") +
                            " | Date of Sale -" + resultSet1.getString("DateOfSale") +
                            " | Total Price -" + resultSet1.getDouble("TotalPrice") + " ");
                    System.out.println("|___________|_________|__________________________|____________________|");
                }

            }

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

}
