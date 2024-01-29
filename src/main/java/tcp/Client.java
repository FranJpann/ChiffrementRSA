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
import java.net.Socket;

public class Client {
    Serveur serveur = new Serveur();

    public Client() {
        serveur.start();
    }
}