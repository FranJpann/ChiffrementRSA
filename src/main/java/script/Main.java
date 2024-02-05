package script;

import tcp.PeerToPeerClient;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        // PeerToPeerClient peerToPeerClient = new PeerToPeerClient("Bob");

        Socket socket = new Socket("192.168.1.34", 12345);
        PeerToPeerClient peerToPeerClient = new PeerToPeerClient("Alice");
        peerToPeerClient.sendPublicKey(socket);

    }
}
