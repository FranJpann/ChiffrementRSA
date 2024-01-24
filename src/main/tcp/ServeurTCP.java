package tcp;

import jdk.jshell.execution.Util;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP extends Thread{
    private final int port;
    private Socket socket;

    public ServeurTCP(Socket socket, int port) {
        this.port = port;
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Connexion avec : " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            String response = in.readLine();

            System.out.println("Client : " + response);

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