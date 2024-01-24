package tcp;

import jdk.jshell.execution.Util;
import key.PrivateKey;
import key.PublicKey;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP {

    private final String adress;
    private final int port;
    private Socket socket;

    public ClientTCP(String adress, int port) {
        this.adress = adress;
        this.port = port;
    }

    public void traitement() {

        try{
            socket = new Socket(adress, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            out.println("test");

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String adresse = "localhost";
        int port = 9632;

        new ClientTCP(adresse, port).traitement();
    }
}
