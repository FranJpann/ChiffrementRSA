package tcp;

import config.ConfigProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread {

    private int port;
    private String adress;
    private ServerSocket serverSocket;

    public Serveur() {
        ConfigProperties configProperties = new ConfigProperties();

        this.port = Integer.parseInt(configProperties.getConfigValue("server.port"));
        try {
            this.adress = Inet4Address.getLocalHost().getHostAddress();
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lancement du serveur sur le port " + port);
        System.out.println("Adresse publique : " + adress);
    }

    public void run() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
                new ServeurRunnable(client).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ServeurRunnable extends Thread {

        Socket client;

        public ServeurRunnable(Socket client) {
            this.client = client;
        }

        public void run() {
            try {
                System.out.println("Connexion avec : " + client.getInetAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                PrintStream out = new PrintStream(client.getOutputStream());

                String response = in.readLine();

                System.out.println("Client : " + response);

                client.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
