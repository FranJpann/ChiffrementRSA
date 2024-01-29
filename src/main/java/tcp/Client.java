package tcp;

import chiffrement.Chiffrement;
import chiffrement.Dechiffrement;
import key.PrivateKey;
import key.PublicKey;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {

    String name;
    private final String adress;
    private final int port;
    private Socket socket;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private PublicKey BobKey;
    Serveur serveur = new Serveur();

    public Client(String name, String adress, int port) {
        this.name = name;
        this.adress = adress;
        this.port = port;
        serveur.start();
    }

    public void Alice() throws IOException {
        try {
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
}