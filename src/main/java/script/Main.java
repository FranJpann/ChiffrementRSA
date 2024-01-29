package script;

import tcp.Client;

public class Main {
    public static void main(String[] args) {
        String adresse = "172.20.42.84";
        int port = 9632;
        Client Alice = new Client("Alice", adresse, port);
    }
}
