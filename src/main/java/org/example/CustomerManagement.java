package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerManagement {
    private final Connection connection ;
    public CustomerManagement(Connection connection) {
        this.connection = connection;
    }

    private void customerDetailsUpdate(int customerId,String phone,String address) throws SQLException {
        String sqlUpdate = "UPDATE Customers SET Phone = ?, Address = ? WHERE CustomerID = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setInt(1,customerId);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,phone);
            preparedStatement.executeUpdate();
        }
    }
}
