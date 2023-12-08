package tcp;

import java.net.Socket;

public abstract class TCP {

    public String adresse;
    public int port;
    public Socket socket;

    public abstract void traitement();
}
