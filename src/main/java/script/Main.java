package script;

import tcp.Client;

public class Main {
    public static void main(String[] args) {
        Client Alice = new Client("Alice");
        Alice.sendMessage("172.20.42.85", 9632, "gros con");
    }
}
