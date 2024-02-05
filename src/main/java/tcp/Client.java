package tcp;

public class Client {
    Serveur serveur;

    public Client(String name) {
        serveur = new Serveur(name);
        serveur.start();
    }

    public void sendMessage(String adresse, int port){
        serveur.sendMessage(adresse, port);
    }
}