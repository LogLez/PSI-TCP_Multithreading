package fr.rayane.services;

import fr.rayane.utils.ConsoleColors;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private UUID id;

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.id = UUID.randomUUID();
            clientHandlers.add(this);
            broadcastMessage("GLOBAL --> " + ConsoleColors.CYAN + this.id.toString() + ConsoleColors.YELLOW + " has entered the chat" + ConsoleColors.RESET);

        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {

        String message;

        while(socket.isConnected()){
            try{
                message = bufferedReader.readLine();
                broadcastMessage(message);
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }

    }

    private void broadcastMessage(String message) {
        for(ClientHandler clientHandler : clientHandlers){
            try{
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
       delete();
       try{

           if (bufferedReader != null)
               bufferedReader.close();

           if (bufferedWriter != null)
               bufferedWriter.close();

           if (socket != null)
               socket.close();

       }catch (IOException e){
           e.printStackTrace();
       }

    }


    private void delete() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER --> A client has left the chat" );
    }

}
