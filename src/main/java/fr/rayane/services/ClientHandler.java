package fr.rayane.services;

import fr.rayane.utils.ConsoleColors;
import fr.rayane.utils.UtilsMessage;
import fr.rayane.utils.UtilsStream;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String name;

    /**
     * Constructor of the ClientHandler
     * @param socket : Socket of the client
     */
    public ClientHandler(Socket socket){
        try {

            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.name = UtilsStream.receiveMessage(dataInputStream); //Get name of the client that we just sent in the constructor of the Client
            broadcastMessage("GLOBAL --> " + ConsoleColors.CYAN + this.name + ConsoleColors.YELLOW + " has entered the chat" + ConsoleColors.RESET);

            ClientHandler.clientHandlers.add(this);
        }catch (IOException e){
            closeEverything();
        }
    }

    /**
     * Read message and sent it to all ClientHandlers
     */
    @Override
    public void run() {

        String message;

        while(socket.isConnected()){
            try{
                message = UtilsStream.receiveMessage(dataInputStream);
                broadcastMessage(message);
            }catch (IOException e){
                closeEverything();
                break;
            }
        }

    }

    /**
     * Broadcast a message to all clients
     * @param message : The message to send
     */
    private void broadcastMessage(String message) {
        for(ClientHandler clientHandler : clientHandlers){
            try{
                if (clientHandler.name.equalsIgnoreCase(this.name)){
                    String ownMessage = UtilsMessage.removeNameFromMessage(message);
                    UtilsStream.sendMessage(clientHandler.dataOutputStream, ownMessage);
                    continue;
                }

                UtilsStream.sendMessage(clientHandler.dataOutputStream, message);
            }catch (IOException e){
                closeEverything();
            }
        }
    }

    /**
     * Close all streams and socket of this clientHandler
     */
    public void closeEverything() {
        delete();
        try {

            if (dataInputStream != null)
                dataInputStream.close();

            if (dataOutputStream != null)
                dataOutputStream.close();

            if (socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete the clientHandler
     */
    private void delete() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER --> A client has left the chat" );
    }

    public DataInputStream getDataInputStream() {return dataInputStream;}
    public DataOutputStream getDataOutputStream() {return dataOutputStream;}
}
