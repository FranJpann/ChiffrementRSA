package script;

import chiffrement.Chiffrement;
import key.PrivateKey;
import key.PublicKey;
import tcp.PeerToPeerClient;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // PeerToPeerClient peerToPeerClient = new PeerToPeerClient("Bob");

        Socket socket = new Socket("192.168.1.34", 12345);
        PeerToPeerClient peerToPeerClient = new PeerToPeerClient("Alice");

        peerToPeerClient.sendPublicKey(socket, true);
        Thread.sleep(3000);
        peerToPeerClient.sendMessage(socket, "Ceci un message déchiffré", "Bob");
    }

     /* Exemple d'utilisation du chiffrement / déchiffrement
        PublicKey pb = new PublicKey();
        PrivateKey pk = new PrivateKey(pb);
        BigInteger[] messageEncrypted = Chiffrement.chiffrer(pb, "Ceci est un message déchiffré");
        String decryptedMessage = Chiffrement.dechiffrer(pk, messageEncrypted);
        System.out.println(decryptedMessage);
      */

}
