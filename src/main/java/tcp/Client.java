package tcp;

public class Client {
    Serveur serveur;

    public Client(String name) {
        serveur = new Serveur(name);
        serveur.start();
    }
}