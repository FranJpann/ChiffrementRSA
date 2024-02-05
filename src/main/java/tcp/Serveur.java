package tcp;

import chiffrement.Dechiffrement;
import config.ConfigProperties;
import key.*;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.key.PublicKey;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Serveur extends Thread {

    private String name;
    private int port;
    private String adress;
    private ServerSocket serverSocket;
    private java.key.PublicKey publicKey;
    private PrivateKey privateKey;

    private Map<String, java.key.PublicKey> publicKeys;

    public Serveur(String name) {
        this.name = name;

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

        publicKeys = new HashMap<>();
    }

    public void addPublicKey(String name, java.key.PublicKey publicKey) {
        this.publicKeys.put(name, publicKey);
    }

    public PublicKey getPublicKey(String name) {
        return publicKeys.get(name);
    }

    public void run() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Alice(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            // Alice envoit sa clé publique
            out.println(this.publicKey.getKey());

            // Alice récupère la clé de Bob
            String bobKeys = in.readLine();
            publicKeys.put("Bob", Utils.convertStringToPublicKey(bobKeys));

            //Alice fait son message et le chiffre
            String aliceMessage = "ça sort de jouer";
            java.chiffrement.Chiffrement messageChiffre = new java.chiffrement.Chiffrement(aliceMessage, publicKey.getE(), publicKey.getN());

            out.println(Arrays.toString(messageChiffre.getChiffre()));

            // Alice recoit le message de Bob et le dechiffre
            List<BigInteger> encryptedMessageParts = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null) {
                // Assuming each line represents a BigInteger in string format
                BigInteger part = new BigInteger(line);
                encryptedMessageParts.add(part);
            }

            BigInteger[] encryptedMessageArray = encryptedMessageParts.toArray(new BigInteger[0]);

            Dechiffrement dechiffrement = new Dechiffrement(privateKey.getU(), privateKey.getN(), encryptedMessageArray);

            System.out.println(dechiffrement);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Bob(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            // Bob récupère la clé de Alice
            String bobKeys = in.readLine();
            publicKeys.put("Alice", Utils.convertStringToPublicKey(bobKeys));

            // Bob envoit sa clé publique
            out.println(this.publicKey.getKey());

            //Alice fait son message et le chiffre
            String aliceMessage = "ça sort de jouer";
            java.chiffrement.Chiffrement messageChiffre = new java.chiffrement.Chiffrement(aliceMessage, publicKey.getE(), publicKey.getN());

            out.println(Arrays.toString(messageChiffre.getChiffre()));

            // Alice recoit le message de Bob et le dechiffre
            List<BigInteger> encryptedMessageParts = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null) {
                // Assuming each line represents a BigInteger in string format
                BigInteger part = new BigInteger(line);
                encryptedMessageParts.add(part);
            }

            BigInteger[] encryptedMessageArray = encryptedMessageParts.toArray(new BigInteger[0]);

            Dechiffrement dechiffrement = new Dechiffrement(privateKey.getU(), privateKey.getN(), encryptedMessageArray);

            System.out.println(dechiffrement);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
