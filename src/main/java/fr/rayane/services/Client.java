package fr.rayane.services;

import fr.rayane.utils.ConsoleColors;
import fr.rayane.utils.UtilsMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private UUID id;

    public Client(Socket socket) throws IOException {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.id = UUID.randomUUID();
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try{

            //Read message sending by this client (LOOP)
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String reverse = UtilsMessage.transformStringToReverse(scanner.nextLine());
                bufferedWriter.write(ConsoleColors.YELLOW_BOLD + id.toString() + ": "+ ConsoleColors.WHITE_BRIGHT  + reverse + "\n");
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //ShutDown all Streams of this client
    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

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

    //Listening of this client (LOOP)
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageInServer;

                while (socket.isConnected()){
                    try{
                        messageInServer = bufferedReader.readLine();
                        System.out.println(messageInServer);
                    }catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();

    }

}
