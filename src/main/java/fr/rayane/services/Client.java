package fr.rayane.services;

import com.github.javafaker.Faker;
import fr.rayane.utils.ConsoleColors;
import fr.rayane.utils.UtilsMessage;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String name;

    public Client(Socket socket) throws IOException {
        try{
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.name = new Faker().name().firstName();

            byte[] data = name.getBytes();
            this.dataOutputStream.writeByte(data.length);
            this.dataOutputStream.write(data);
            this.dataOutputStream.flush();
        }catch (IOException e){
            closeEverything();
        }
    }

    public void sendingMessage() {
        try{
            //Read message sending by this client (LOOP) for sending them
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String reverse = UtilsMessage.transformStringToReverse(scanner.nextLine());
                String fullMsg = ConsoleColors.YELLOW_BOLD + name.toString() + ": "+ ConsoleColors.WHITE_BRIGHT  + reverse + "\n";

                if (fullMsg.length() > 255){
                    System.out.println("The message length is more than 255 characters.\nYou cannot send it");
                    continue;
                }

                byte[] data = fullMsg.getBytes();
                this.dataOutputStream.writeByte(data.length);
                this.dataOutputStream.write(data);
                this.dataOutputStream.flush();
            }
            closeEverything();
        }catch (IOException e){
            closeEverything();
        }
    }

    //Listening of this client (LOOP)
    //Read all message from server
    public void listeningForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageInServer;

                while (socket.isConnected()){
                    try{
                        byte[] data = new byte[dataInputStream.readByte()];
                        dataInputStream.readFully(data);
                        messageInServer =new String(data);
                        System.out.println(messageInServer);
                    }catch (IOException e){
                        closeEverything();
                    }
                }
                closeEverything();
            }
        }).start();
    }

    //ShutDown all Streams of this client
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
