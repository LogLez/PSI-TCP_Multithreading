package fr.rayane.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public record Server(ServerSocket serverSocket) {

    /**
     * Start the server and listen when a new client joined the port
     * Create the specific ClientHandler of the new Client
     * Start thread of this clientHandler
     */
    public void startServer() {

        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has connected !");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop the server
     */
    public void stop() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
