package tcp;

import jdk.jshell.execution.Util;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP extends TCP {
    private final Socket socket;

    public ServeurTCP(Socket socket, int port) {
        super("localhost", port);
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Connexion avec : " + socket.getInetAddress());

            traitement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void traitement() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //PrintStream out = new PrintStream(socket.getOutputStream());

            System.out.println("Client : " + Utils.getMessage(in));

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            int port = 9632;
            ServerSocket socketServeur = new ServerSocket(port);
            String adresse = Inet4Address.getLocalHost().getHostAddress();

            System.out.println("Lancement du serveur sur le port " + port);
            System.out.println("Adresse publique : " + adresse);

            while (true) {
                Socket socketClient = socketServeur.accept();
                ServeurTCP serveurTCP = new ServeurTCP(socketClient, port);
                serveurTCP.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}