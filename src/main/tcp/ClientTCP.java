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

        try{
            socket = new Socket(adresse, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void traitement() {

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            //System.out.println(privateKey.getKey());

            Utils.sendMessage(out, "test");

            //Utils.sendMessage(out, privateKey.getKey());
            //System.out.println(Utils.getMessage(in));

            socket.close();
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
