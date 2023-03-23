package fr.rayane.services;

import com.github.javafaker.Faker;
import fr.rayane.utils.ConsoleColors;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String name;

    public ClientHandler(Socket socket){
        try {

            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            int length= dataInputStream.readByte();
            byte[] data =new byte[length];
            dataInputStream.readFully(data);
            this.name =  new String(data); //get name from initialization of the client;
            broadcastMessage("GLOBAL --> " + ConsoleColors.CYAN + this.name + ConsoleColors.YELLOW + " has entered the chat" + ConsoleColors.RESET);

            ClientHandler.clientHandlers.add(this);
        }catch (IOException e){
            closeEverything();
        }
    }

    //Runnable to read messages in LOOP
    @Override
    public void run() {

        String message;

        while(socket.isConnected()){
            try{
                int length= dataInputStream.readByte();
                byte[] data =new byte[length];
                dataInputStream.readFully(data);
                message =new String(data);
                broadcastMessage(message);
            }catch (IOException e){
                closeEverything();
                break;
            }
        }

    }

    //Send the message to all Clients
    private void broadcastMessage(String message) {
        for(ClientHandler clientHandler : clientHandlers){
            try{
//                if (!clientHandler.name.equalsIgnoreCase(this.name))
//                    continue;

                byte[] data = message.getBytes(StandardCharsets.UTF_8);
                this.dataOutputStream.writeByte(data.length);
                this.dataOutputStream.write(data);
                this.dataOutputStream.flush();

            }catch (IOException e){
                closeEverything();
            }
        }
    }


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

    private void delete() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER --> A client has left the chat" );
    }

}
