package tcp;

import jdk.jshell.execution.Util;
import key.PrivateKey;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP extends TCP {

    public ClientTCP(String adresse, int port) {
        super(adresse, port);
    }

    public void traitement() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            Utils.sendMessage(out, "message from client");
            System.out.println(Utils.getMessage(in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        String adresse = "localhost";
        int port = 9632;
        ClientTCP clientTCP = new ClientTCP(adresse, port);

        clientTCP.traitement();
    }
}
