package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Utils {

    public static void sendMessage(String adresse, int port, String message) {
        try{
            Socket socket = new Socket(adresse, port);

            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(message);

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String waitForResponse(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        return in.readLine();
    }
}
