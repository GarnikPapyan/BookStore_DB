package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Books {
     private final Connection connection ;
     public Books(Connection connection) {
        this.connection = connection;
     }
    public void createListGenre(String genre) throws SQLException {
        String sqlGenre = "SELECT * FROM books WHERE Genre = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlGenre)) {
            preparedStatement.setString(1,genre);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.print("We have books from this Genre- " + genre + "\n");
                while (resultSet.next()) {
                    System.out.println(" Title- " + resultSet.getString("Title")
                            + ", Author- " + resultSet.getString("Author"));
                }
            }
        }
    }

}
