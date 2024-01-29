package tcp;

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

public class Serveur {

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
}
