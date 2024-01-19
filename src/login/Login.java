package login;

import database.Database;
import pojo.User;

import java.sql.*;

public class Login {

    public static User login(String email, String password) {

        Connection connection = Database.getConnection();
// SQL question to our database that set our userId,email and password
        try {
           PreparedStatement preparedStatement = connection.prepareStatement("SELECT users_id, email, password FROM Users WHERE email = ? AND password = ?");
           preparedStatement.setString(1, email);
           preparedStatement.setString(2, password);
           ResultSet result = preparedStatement.executeQuery();

          if (result.next()) {
             int usersId = result.getInt("users_id");
             String usersEmail = result.getString("email");
             String usersPassword = result.getString("password");

             User user = new User();
             user.setUserId(usersId);
             user.setEmail(usersEmail);
             user.setPassword(usersPassword);

              preparedStatement.close();
             return user;
          }
            //Close connection asap ,because to much traffic will crash database
            //Also open as late as possible
            preparedStatement.close();
            return null;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return null;
        }
    }
}
