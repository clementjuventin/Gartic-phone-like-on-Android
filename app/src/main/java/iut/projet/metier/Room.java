package iut.projet.metier;

import java.util.LinkedList;

public class Room {

    private String roomName;

    private String roomCode;
    private int roomHash;

    private Player host;

    private LinkedList<Player> players;

    public Room(Player host, String roomName){
        //+Réseau local
        this.host = host;
        this.roomName = roomName;
        addPlayer(this.host);

        roomHash = hashCode();
    }

    public void start(){

    }

    public void addPlayer(Player player){
        if(players.contains(player)){
            throw new IllegalStateException("Un joueur ne peut être qu'une fois dans une salle.");
        }
        players.add(player);
    }
    public void deletePlayer(Player player){
        if(!players.contains(player)){
            throw new IllegalStateException("Le joueur que l'on souhaite supprimé n'est pas dans la salle.");
        }
        players.remove(player);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + roomName.hashCode();
        hash = hash * 31 + host.hashCode();
        return hash;
    }
}
