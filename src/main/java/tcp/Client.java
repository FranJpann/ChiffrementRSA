package tcp;

import key.PrivateKey;
import key.PublicKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

    String name;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Client(String name) {
        this.name = name;
        new Serveur().start();
    }

    public void sendMessage(String adress, int port, String message) {
        try{
            Socket socket = new Socket(adress, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            out.println(message);

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}