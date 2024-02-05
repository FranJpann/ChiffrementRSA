package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class PeerToPeerClient {
    private static final int PORT = 12345;
    private String name;

    public PeerToPeerClient(String name) {
        this.name = name;

        Scanner scanner = new Scanner(System.in);

        // Démarrer le serveur pour recevoir des messages
        new Thread(() -> startReceiver()).start();

        // Envoyer des messages en tant que Sender
        while (true) {
            System.out.print("Vous: ");
            String message = scanner.nextLine();
            sendMessage("localhost", PORT, message);
        }
    }

    private void startReceiver() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("En attente de connexions entrantes...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nouvelle connexion entrante!");

                // Nouveau thread pour gérer la réception de messages
                new Thread(() -> handleIncomingMessages(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessages(Socket socket) {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                String message = dis.readUTF();
                System.out.println("Reçu: " + message);

                sendMessage(socket, "bien recu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String host, int port, String message) {
        try {
            Socket socket = new Socket(host, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(message);
            dos.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket, String message) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(message);
            dos.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}