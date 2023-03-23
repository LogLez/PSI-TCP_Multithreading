package fr.rayane.services;

import com.github.javafaker.Faker;
import fr.rayane.utils.ConsoleColors;
import fr.rayane.utils.UtilsMessage;
import fr.rayane.utils.UtilsStream;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String name;

    /**
     * Constructor of Client
     * @param socket : Socket of the client
     * @throws IOException
     */
    public Client(Socket socket) throws IOException {
        try{
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.name = new Faker().name().firstName();
            UtilsStream.sendMessage(dataOutputStream, name); //Send name for the clientHandler
        }catch (IOException e){
            closeEverything();
        }
    }

    /**
     * Send message when client write in the console
     * Message has to be lower than 255 characters
     * The message will be reversed to the others clients
     */
    public void sendingMessage() {
        try{
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String reverse = UtilsMessage.transformStringToReverse(scanner.nextLine());
                String fullMsg = ConsoleColors.YELLOW_BOLD + name.toString() + ": "+ ConsoleColors.WHITE_BRIGHT  + reverse + "\n";

                if (fullMsg.length() > 255){
                    System.out.println("The message length is more than 255 characters.\nYou cannot send it");
                    continue;
                }
                UtilsStream.sendMessage(dataOutputStream, fullMsg);
            }
            closeEverything();
        }catch (IOException e){
            closeEverything();
        }
    }

    /**
     * Listen when the client receive a message from another client
     */
    public void listeningForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageInServer;

                while (socket.isConnected()){
                    try{
                        messageInServer = UtilsStream.receiveMessage(dataInputStream);
                        System.out.println(messageInServer);
                    }catch (IOException e){
                        closeEverything();
                    }
                }
                closeEverything();
            }
        }).start();
    }

    /**
     * Close all streams and socket of this clientHandler
     */
    private void closeEverything() {

        try{

            if (dataInputStream != null)
                dataInputStream.close();

            if (dataOutputStream != null)
                dataOutputStream.close();

            if (socket != null)
                socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
