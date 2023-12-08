package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP extends Thread {
  final static int port = 9632;
  private final Socket socket;

  public static void main(String[] args) {
    try {
      ServerSocket socketServeur = new ServerSocket(port);
      System.out.println("Lancement du serveur sur le port "+port);

      while (true) {
        Socket socketClient = socketServeur.accept();
        ServeurTCP serveurTCP = new ServeurTCP(socketClient);
        serveurTCP.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ServeurTCP(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      System.out.println("Connexion avec : "+socket.getInetAddress());

      InputStream in = socket.getInputStream();

      BufferedReader input = new BufferedReader(
              new InputStreamReader(in));
      PrintStream out = new PrintStream(socket.getOutputStream());

      traitements(input, out);

      socket.close();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public void traitements(BufferedReader in, PrintStream out) {
    try {
      String message = in.readLine();
      System.out.println(message);
      out.println(message);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}