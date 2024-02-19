package mealplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CRUD {
    static boolean running = true;
    static menu menu;
    static String[][] mealDB = new String[10][4];
    static int mealsInDB = 0;
    static BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
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
    //connection.setAutoCommit(true);

    public static void CRUDInterface(String input) throws IOException {
        if (menu == menu.actionMenu) {
            switch (input) {
                case "add" -> add(input);
                case "show" -> show();
                case "exit" -> {
                    running = false;
                    System.out.println("Bye!");
                }
                //default -> running = false;
                default -> throw new IllegalStateException("Unexpected value: " + input);
            }
        } else if (menu == menu.CRUDMenu) {
        }
    }
    public static void add(String input) throws IOException {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            while(true) {
                input = buf.readLine();
                if (input.matches("breakfast|lunch|dinner") && !input.equals(" ")) {
                    mealDB[mealsInDB][1] = input;
                    break;
                } else System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
            while(true) {
                System.out.println("Input the meal's name:");
                input = buf.readLine();
                if (input.matches("^[a-zA-Z]+[\s]*[a-zA-Z]*$") && !input.equals("")) {
                    mealDB[mealsInDB][2] = input;
                    break;
                } else System.out.println("Wrong format. Use letters only!");
            }
            while(true) {
                System.out.println("Input the ingredients:");
                input = buf.readLine();
                if (input.matches("[a-zA-Z,\s]+") &&
                        !input.matches(".+,\s*,.+|.+,\s*")) {
                    mealDB[mealsInDB][3] = input;
                    break;
                } else System.out.println("Wrong format. Use letters only!");
            }
            mealsInDB++;
            System.out.println("The meal has been added!");
            menu = menu.actionMenu;
            }

    public static void show() {
        if (mealsInDB > 0) {
            for (int i = 0; i < mealsInDB ; i++) {
                System.out.printf("\nCategory: %s\nName: %s\nIngredients:\n", mealDB[i][1], mealDB[i][2]);
                String[] ingredients = mealDB[i][3].split(",\s*");
                for (int j = 0; j < ingredients.length; j++) {
                    System.out.println(ingredients[j]);
                }
            }
        } else System.out.println("No meals saved. Add a meal first.");
    }
}
