package mealplanner;

import java.io.IOException;
import java.sql.*;

import static mealplanner.CRUD.*;

public class Main {
  public static void main(String[] args) throws IOException, SQLException {

    String DB_URL = "jdbc:postgresql:meals_db";
    String USER = "postgres";
    String PASS = "1111";
    Connection connection;
    {
      try {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    connection.setAutoCommit(true);

    Statement statement = connection.createStatement();
    //statement.executeUpdate("drop table if exists meals");
    //statement.executeUpdate("drop table if exists ingredients");
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals(" +
            "category VARCHAR(10) NOT NULL," +
            "meal VARCHAR(20) NOT NULL," +
            "meal_id INTEGER PRIMARY KEY)");
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients(" +
            "ingredient VARCHAR(255) NOT NULL," +
            "ingredient_id INTEGER NOT NULL," +
            "meal_id INTEGER NOT NULL)");

    while (running) {
        System.out.println("What would you like to do (add, show, exit)?");
      while(true) {
        String input = buf.readLine();
        if (input.matches("add|show|exit") && !input.equals(" ")) {
          CRUDInterface(input, connection, statement);
          break;
        } else break;
      }
    }


  }
}