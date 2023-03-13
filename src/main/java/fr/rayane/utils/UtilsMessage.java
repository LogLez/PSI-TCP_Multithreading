package fr.rayane.utils;

import java.util.Scanner;

public class UtilsMessage {

    public static int askAction (Scanner scanner){
        System.out.println(ConsoleColors.WHITE_BRIGHT + "Please, which service is this terminal for ? \n");
        System.out.println(ConsoleColors.WHITE + "1 - Server\n2 - Client\n0 - Exit the terminal");

        int action = scanner.nextInt(); //Read the next Integer from the User

        //Check of the action is valid
        while (action < 0 || action > 2) {
            System.out.println(ConsoleColors.RED_BOLD + action + " is not a valid choice !");
            System.out.println(ConsoleColors.WHITE_BRIGHT + "Please, which service is this terminal for ? \n");
            System.out.println(ConsoleColors.WHITE + " 1 - Server\n2 - Client\n0 - Exit the terminal");
            action = scanner.nextInt();
        }

        return action;
    }

    public static int askPort (Scanner scanner){
        System.out.println(ConsoleColors.WHITE_BRIGHT + "Which port number do you want to use ? (0 to exit)");

        int port = scanner.nextInt(); //Read the next Integer from the User

        //Check of the port is valid
        while (port < 0 || 9999 < port) {
            System.out.println(ConsoleColors.RED +"The port number " + port + " is not accepted.");
            System.out.println(ConsoleColors.WHITE_BRIGHT +"Please, select a valid number between 1-9999 !");
            port = scanner.nextInt();
        }

        return port;
    }

    public static void welcomeMessage (){
        System.out.println(ConsoleColors.YELLOW + "###########################################################################");
        System.out.println(ConsoleColors.YELLOW +"#"+"                           "+ConsoleColors.GREEN_UNDERLINED +"WELCOME TO LAB1 v1.0"+ConsoleColors.YELLOW+"                          #"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW +"#"+ConsoleColors.RESET+"                                                                         "+ConsoleColors.YELLOW+"#"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW +"#"+ConsoleColors.YELLOW+"  - This labs allows you tu use MultiThreading.                          "+ConsoleColors.YELLOW+"#"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW +"#"+ConsoleColors.YELLOW+"  - You can use this terminal to create a server or a client with a      "+ConsoleColors.YELLOW+"#"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW +"#"+ConsoleColors.YELLOW+" specified port number                                                   "+ConsoleColors.YELLOW+"#"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW +"#"+ConsoleColors.RESET+"                                                                         "+ConsoleColors.YELLOW+"#"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW +"###########################################################################\n\n");

    }

    public static String transformStringToReverse(String name){
        StringBuilder reversedMsg = new StringBuilder("");
        for(int i = name.length() - 1; i >= 0; i--)
            reversedMsg.append(name.charAt(i));

        return reversedMsg.toString();
    }
}
