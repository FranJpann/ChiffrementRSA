package tcp;

import key.PrivateKey;
import key.PublicKey;

import java.net.Socket;

public abstract class TCP extends Thread{

    public String adresse;
    public int port;
    public Socket socket;

    public TCP(String adresse, int port) {
            this.adresse = adresse;
            this.port = port;

            try{
                socket = new Socket(adresse, port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    public PublicKey publicKey;
    public PrivateKey privateKey;

    public abstract void traitement();
}
