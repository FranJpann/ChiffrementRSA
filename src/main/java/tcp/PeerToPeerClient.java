package tcp;

import chiffrement.Chiffrement;
import key.PrivateKey;
import key.PublicKey;
import org.json.JSONObject;
import utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PeerToPeerClient {
    private final int PORT = 12345;
    private String name;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private Map<String, PublicKey> storedPublicKeys;

    public PeerToPeerClient(String name) {
        this.name = name;
        publicKey = new PublicKey();
        privateKey = new PrivateKey(publicKey);
        storedPublicKeys = new HashMap<>();

        // Démarrer le serveur pour recevoir des messages
        new Thread(() -> startReceiver()).start();
    }

    private void startReceiver() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println(Inet4Address.getLocalHost().getHostAddress()+ ":" +serverSocket.getLocalPort());
            System.out.println("En attente de connexions entrantes...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nouvelle connexion entrante!");

                handleIncomingMessages(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessages(Socket socket) {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            String message = dis.readUTF();
            JSONObject response = new JSONObject(message);

            if(response.get("topic").equals("firstKey") && !storedPublicKeys.containsKey((String) response.get("name"))){
                    storedPublicKeys.put((String) response.get("name"), Utils.convertJSONToPublicKey(response));
                    System.out.println("PublicKey reçue ("+response.get("name")+")");
                    sendPublicKey((String) response.get("adress"), Integer.parseInt((String) response.get("port")), false);
            }
            else if(response.get("topic").equals("secondKey") &&  !storedPublicKeys.containsKey((String) response.get("name"))) {
                storedPublicKeys.put((String) response.get("name"), Utils.convertJSONToPublicKey(response));
                System.out.println("PublicKey reçue ("+response.get("name")+")");
            }
            else if(response.get("topic") == "encryptedMessage") {
                System.out.println("là faut décrypter:\n" + response.get("encryptedMessage"));
            }
            else {
                System.out.println("Topic inconnu");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPublicKey(String host, int port, boolean first) {
        try {
            Socket socket = new Socket(host, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String topic;
            if(first) topic = "firstKey";
            else topic = "secondKey";

            dos.writeUTF("{\"adress\":"+Inet4Address.getLocalHost().getHostAddress()+ ",\"port\":\""+this.PORT+"\","+
                    "\"name\":\""+name+"\", \"topic\":\""+topic+"\", \"n\":\""+publicKey.getN()+"\", \"e\":\""+publicKey.getE()+"\"}");
            dos.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPublicKey(Socket socket) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("{\"adress\":"+Inet4Address.getLocalHost().getHostAddress()+ ",\"port\":\""+this.PORT+"\","+
                    "\"name\":\""+name+"\", \"topic\":\"key\", \"n\":\""+publicKey.getN()+"\", \"e\":\""+publicKey.getE()+"\"}");
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String host, int port, String message, String nomDestinataire) {
        if(storedPublicKeys.containsKey(nomDestinataire)){
            try {
                Socket socket = new Socket(host, port);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                PublicKey destinatairePublicKey = storedPublicKeys.get(nomDestinataire);
                BigInteger[] encryptedMessage = Chiffrement.chiffrer(destinatairePublicKey, message);

                StringBuilder encryptedJSON = new StringBuilder("{\"adress\":" + Inet4Address.getLocalHost().getHostAddress() + ",\"port\":\"" + this.PORT + "\"," +
                        "\"name\":\"" + name + "\", \"topic\":\"encryptedMessage\"");

                int i=0;
                for(BigInteger bigInteger: encryptedMessage){
                    encryptedJSON.append(", BigInteger").append(i).append(":").append(bigInteger.toString());
                    i++;
                }
                encryptedJSON.append("}");

                dos.writeUTF(encryptedJSON.toString());
                dos.flush();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Le message n'a pas pu être envoyé (mauvais destinataire)");
        }
    }

    public void sendMessage(Socket socket, String message, String nomDestinataire) {
        if(storedPublicKeys.containsKey(nomDestinataire)){
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                PublicKey destinatairePublicKey = storedPublicKeys.get(nomDestinataire);
                BigInteger[] encryptedMessage = Chiffrement.chiffrer(destinatairePublicKey, message);

                StringBuilder encryptedJSON = new StringBuilder("{\"adress\":" + Inet4Address.getLocalHost().getHostAddress() + ",\"port\":\"" + this.PORT + "\"," +
                        "\"name\":\"" + name + "\", \"topic\":\"encryptedMessage\"");

                int i=0;
                for(BigInteger bigInteger: encryptedMessage){
                    encryptedJSON.append(", BigInteger").append(i).append(":").append(bigInteger.toString());
                    i++;
                }
                encryptedJSON.append("}");

                dos.writeUTF(encryptedJSON.toString());
                dos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Le message n'a pas pu être envoyé (mauvais destinataire)");
        }
    }
}