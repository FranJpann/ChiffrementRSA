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

    public static BigInteger[] convertJSONEncryptedMessageToListBigInteger(JSONObject obj) {
        try {
            int nbBigIntegers = 0;
            for (int i=0; obj.has("BigInteger"+i); i++) nbBigIntegers++;
            BigInteger[] bigIntegers = new BigInteger[nbBigIntegers];
            for (int i=0; obj.has("BigInteger"+i); i++) {
                BigInteger bigInteger = new BigInteger(obj.getString("BigInteger"+i));
                bigIntegers[i] = bigInteger;
            }
            return bigIntegers;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
