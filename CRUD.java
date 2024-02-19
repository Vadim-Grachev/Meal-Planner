package mealplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CRUD {
    static boolean running = true;
    static BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
    static String category = "";
    static String meal = "";
    static String ingredients = "";
    static int meal_id = 0;

    public static void CRUDInterface(String input, Connection connection, Statement statement) throws IOException, SQLException {

            switch (input) {
                case "add" -> {
                    add(input);
                    statement.executeUpdate("insert into meals (category, meal, meal_id) values ('" + category + "', '" + meal + "', " + meal_id + ")");
                    statement.executeUpdate("insert into ingredients (ingredient, ingredient_id, meal_id) values ('" + ingredients + "', '" + meal_id + "', " + meal_id + ")");
                }
                case "show" -> show(connection);
                case "exit" -> {
                    running = false;
                    System.out.println("Bye!");
                }
                default -> throw new IllegalStateException("Unexpected value: " + input);
            }

    }
    public static void add(String input) throws IOException, SQLException {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            while(true) {
                input = buf.readLine();
                if (input.matches("breakfast|lunch|dinner") && !input.equals(" ")) {
                    category = input;
                    break;
                } else System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
            while(true) {
                System.out.println("Input the meal's name:");
                input = buf.readLine();
                if (input.matches("^[a-zA-Z]+[\s]*[a-zA-Z]*$") && !input.equals(" ")) {
                    meal = input;
                    break;
                } else System.out.println("Wrong format. Use letters only!");
            }
            while(true) {
                System.out.println("Input the ingredients:");
                input = buf.readLine();
                if (input.matches("[a-zA-Z,\s]+") &&
                        !input.matches(".+,\s*,.+|.+,\s*")) {
                    ingredients = input;
                    break;
                } else System.out.println("Wrong format. Use letters only!");
            }
            meal_id++;
            System.out.println("The meal has been added!");
            }

    public static void show(Connection connection) throws SQLException {

            PreparedStatement psMeal = connection.prepareStatement("SELECT * FROM meals");
            ResultSet rsMeal = psMeal.executeQuery();
            while (rsMeal.next()) {
                System.out.println("Category: " + rsMeal.getString("category"));
                System.out.println("Name: " + rsMeal.getString("meal"));
                int meal_id = rsMeal.getInt("meal_id");
                PreparedStatement psIngredient = connection.prepareStatement("SELECT * FROM ingredients WHERE meal_id = " + meal_id);
                ResultSet rsIngredient = psIngredient.executeQuery();
                System.out.println("Ingredients:");
                while (rsIngredient.next()) {
                    String[] ingredients = rsIngredient.getString("ingredient").split(",\s*");
                    for (String ingredient : ingredients) {
                        System.out.println(ingredient);
                    }
                }
                System.out.println();
            }
    }
}
