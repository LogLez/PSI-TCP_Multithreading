package fr.rayane.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class UtilsMessage {


    /**
     * Ask action for the user in the console
     * @param scanner
     * @return the id of the action
     */
    public static int askAction(Scanner scanner){
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


    /**
     * Ask port for the user in the console
     * @param scanner
     * @return the port number
     */
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

    /**
     * Remove the name of a message and put "YOU" (Just for the sender)
     * @param message
     * @return
     */
    public static String removeNameFromMessage(String message){
        String[] args = message.split(":");
        if (args.length == 0)
            return message;

        StringBuilder result = new StringBuilder(ConsoleColors.YELLOW_BOLD + "You:" + ConsoleColors.WHITE_BRIGHT);
        for(int i = 1; i < args.length; i++)
            result.append(args[i]);

        return result.toString();
    }

    /**
     * Send first messages when the application is launched
     */
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

    /**
     * Reversed a message (ABCD --> DCBA)
     * @param message : The message to need to be reversed
     * @return reversed message
     */
    public static String transformStringToReverse(String message){
        StringBuilder reversed = new StringBuilder("");
        for(int i = message.length() - 1; i >= 0; i--)
            reversed.append(message.charAt(i));

        return reversed.toString();
    }
}
