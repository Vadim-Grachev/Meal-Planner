package mealplanner;

import java.io.IOException;

import static mealplanner.CRUD.*;

public class Main {
  public static void main(String[] args) throws IOException {

    menu = menu.actionMenu;
    while (running) {
      if (menu == menu.actionMenu) {
        System.out.println("What would you like to do (add, show, exit)?");
      }
      if (menu == menu.CRUDMenu) {
        System.out.println("");
      }
      while(true) {
        String input = buf.readLine();
        if (input.matches("add|show|exit") && !input.equals(" ")) {
          CRUDInterface(input);
          break;
        } else break;
      }
    }


  }
}