package utils;

import key.PublicKey;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Map;

public class Utils {

    public static void sendMessage(String adresse, int port, String message) {
        try{
            Socket socket = new Socket(adresse, port);

            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(message);

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String waitForResponse(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        return in.readLine();
    }

    public static PublicKey convertJSONToPublicKey(JSONObject obj) {
        try {
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
