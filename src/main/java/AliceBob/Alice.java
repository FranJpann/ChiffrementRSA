package AliceBob;


import key.PrivateKey;
import key.PublicKey;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import chiffrement.*;

public class Alice {

    private final String adress;
    private final int port;
    private Socket socket;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private PublicKey BobKey;

    public Alice(String adress, int port) {
        this.adress = adress;
        this.port = port;
        this.privateKey = new PrivateKey();
        this.publicKey = new PublicKey();
    }

    public void script() {

        try{
            socket = new Socket(adress, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            //Alice recoit la clé publique de Bob
            String bobKeyString = in.readLine();
            BobKey = convertirChaineEnPublicKey(bobKeyString);

            //Alice envoit sa clé publique
            out.println(this.publicKey.getKey());

            //Alice fait son message et le chiffre
            String aliceMessage = "ça sort de jouer";

            Chiffrement messageChiffre = new Chiffrement(aliceMessage,publicKey.getE(),publicKey.getN());






            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey convertirChaineEnPublicKey(String chaine) {
        try {

            JSONObject obj = new JSONObject(chaine);

            String nStr = obj.getString("n");
            String eStr = obj.getString("e");

            BigInteger n = new BigInteger(nStr);
            BigInteger e = new BigInteger(eStr);

            return new PublicKey(n, e);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String adresse = "172.20.42.84";
        int port = 9632;

        new tcp.ClientTCP(adresse, port).traitement();
    }
}
