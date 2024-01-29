package tcp;

import key.PrivateKey;
import key.PublicKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

    Serveur serveur = new Serveur();

    public Client() {
        serveur = new Serveur();
    }
}