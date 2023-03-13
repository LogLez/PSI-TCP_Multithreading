package fr.rayane;

import fr.rayane.services.Client;
import fr.rayane.services.Server;
import fr.rayane.utils.ConsoleColors;
import fr.rayane.utils.UtilsMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws IOException {
        UtilsMessage.welcomeMessage();

        //Create Scanner to read entries
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object

        int action = UtilsMessage.askAction(scanner);

        if (action == Action.SHUTDOWN.getAction())
            return;

        int port = UtilsMessage.askPort(scanner);

        if (port == Action.SHUTDOWN.getAction())
            return;

        //Creation of the service !
        System.out.println(ConsoleColors.GREEN_BOLD + "Creation of the service...\n");
        System.out.println(ConsoleColors.YELLOW + "****************************************************\n" +ConsoleColors.RESET);

        if (action == Action.CREATE_SERVER.getAction())
            createServer(port);
        else if (action == Action.CREATE_CLIENT.getAction())
            createClient(port);

    }

    private static Server createServer(int port) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            Server server = new Server(serverSocket);
            server.startServer();
            return server;
        } catch (IOException e) {
            return null;
        }
    }

    private static Client createClient(int port) throws IOException {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            Client client = new Client(socket);
            client.listenForMessage();
            client.sendMessage();
            return client;
        } catch (IOException e) {
            return null;
        }
    }




    enum Action {

        SHUTDOWN(0),
        CREATE_SERVER(1),
        CREATE_CLIENT(2);

        private final int action;

        Action(int action) {
            this.action = action;
        }

        public int getAction() {
            return action;
        }
    }
}
