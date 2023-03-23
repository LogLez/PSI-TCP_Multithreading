package fr.rayane.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UtilsStream {

    /**
     * Send a message in the specififed stream by adding length in the first Byte
     * @param dataOutputStream : The stream wher to write
     * @param message : The message to write
     * @throws IOException
     */
    public static void sendMessage(DataOutputStream dataOutputStream, String message) throws IOException {
        byte[] data = message.getBytes();
        dataOutputStream.writeByte(data.length); //Send the first byte for the length
        dataOutputStream.write(data); //Write the data
        dataOutputStream.flush(); //Flush
    }

    /**
     * Receive a message in the specififed stream by getting length from the first Byte
     * @param dataInputStream : The stream wher to write
     * @return The received message
     */
    public static String receiveMessage(DataInputStream dataInputStream) throws IOException {
        byte[] data =new byte[dataInputStream.readByte()]; //Get the first byte to get the length
        dataInputStream.readFully(data); //Fill the data by reading
        return new String(data);
    }
}
