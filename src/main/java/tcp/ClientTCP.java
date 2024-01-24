package tcp;

import config.ConfigProperties;
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

    private PrivateKey privateKey;
    private PublicKey publicKey;

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

        ConfigProperties configProperties = new ConfigProperties();

        String adresse = configProperties.getConfigValue("server.adress");
        int port = Integer.parseInt(configProperties.getConfigValue("server.port"));;

        new ClientTCP(adresse, port).traitement();
    }
}
