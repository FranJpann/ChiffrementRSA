package tcp;

import chiffrement.Chiffrement;
import chiffrement.Dechiffrement;
import config.ConfigProperties;
import key.PrivateKey;
import key.PublicKey;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static utils.Utils.convertirChaineEnPublicKey;

public class Serveur extends Thread {

    private int port;
    private String adress;
    private ServerSocket serverSocket;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private Map<String, Map<String, Integer>> keys;

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

        keys = new HashMap<>();
    }

    public void addKeys(String name, int publicKey, int privateKey) {
        this.keys.put(name, Map.ofEntries(entry("publicKey", publicKey), entry("privateKey", privateKey)));
    }

    public Map<String, Integer> getKeys(String name) {
        return keys.get(name);
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

    public void Alice() {
        try {
            Socket socket = new Socket(adress, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            //Alice recoit la clé publique de Bob
            String bobKeyString = in.readLine();
            BobKey = Utils.convertirChaineEnPublicKey(bobKeyString);

            //Alice envoit sa clé publique
            out.println(this.publicKey.getKey());

            //Alice fait son message et le chiffre
            String aliceMessage = "ça sort de jouer";
            Chiffrement messageChiffre = new Chiffrement(aliceMessage, publicKey.getE(), publicKey.getN());

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
