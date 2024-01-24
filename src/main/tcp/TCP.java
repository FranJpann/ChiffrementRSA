package tcp;

import key.PrivateKey;
import key.PublicKey;

import java.net.Socket;

public abstract class TCP extends Thread{

    public String adresse;
    public int port;
    public Socket socket;
    public PublicKey publicKey;
    public PrivateKey privateKey;

    public TCP(String adresse, int port) {
            this.adresse = adresse;
            this.port = port;

            genKey();
    }

    public void genKey() {
        this.publicKey = new PublicKey();
        this.privateKey = new PrivateKey();
    }

    public abstract void traitement();
}
