package iut.projet.metier;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Room {

    public String getRoomCode() {
        return roomCode;
    }

    private String roomCode;
    private int roomHash;

    private Player host;

    public List<Player> getPlayers() {
        return players;
    }

    private List<Player> players;

    public Room(Player host){
        this.host = host;
        this.roomCode = generateRoomCode();

        players = new ArrayList<>();
        addPlayer(this.host);

        roomHash = hashCode();

        FirebaseHelper.createRoom(this);
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
    private String generateRoomCode(){
        int stringLen = 8;
        Random random = new Random();
        char[] generatedString = new char [stringLen];

        for (int i=0;i<stringLen;i++){
            generatedString[i] = (char) ('A' + random.nextInt(26));
        }
        return String.valueOf(generatedString);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + host.hashCode();
        return hash;
    }
}
