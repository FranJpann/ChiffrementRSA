package tcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP {
  final static int port = 9632;

  public static void main(String[] args) {
    try {
      ServerSocket socketServeur = new ServerSocket(port);
      System.out.println("Lancement du serveur sur le port "+port);

      while (true) {
        Socket socketClient = socketServeur.accept();
        String message = "";

        System.out.println("Connexion avec : "+socketClient.getInetAddress());

        InputStream in = socketClient.getInputStream();
        // OutputStream out = socketClient.getOutputStream();

        BufferedReader input = new BufferedReader(
          new InputStreamReader(in));
        PrintStream out = new PrintStream(socketClient.getOutputStream());
        message = input.readLine();

        System.out.println(message);

        socketClient.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}