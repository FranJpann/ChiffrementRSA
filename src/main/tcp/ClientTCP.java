package tcp;

import key.PrivateKey;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTCP {

    final static int port = 9632;

    public static void main(String[] args) {

        Socket socket;

        try {
            socket = new Socket("localhost", port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            traitements(in, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void traitements(BufferedReader in, PrintStream out) {
        // On envoie au serveur tcp
        out.println("test");

        // On recoit du serveur tcp
        try{
            System.out.println(in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
