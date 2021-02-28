package iut.projet.metier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {

    public String getRoomCode() {
        return roomCode;
    }
    private String roomCode;

    private Player host;
    public List<Player> getPlayers() {
        return players;
    }

    private List<Player> players;
    private DatabaseReference roomRef;

    public Room(String roomCode, Player player){
        this.roomCode = roomCode;
        players = new ArrayList<>();
        addPlayer(player);

        this.roomRef = FirebaseDatabaseHelper.joinRoom(this.roomCode, player);

        player.setCurrentRoom(this);
    }

    public Room(Player host){
        this.host = host;
        this.roomCode = generateRoomCode();

        players = new ArrayList<>();
        addPlayer(this.host);

        this.roomRef = FirebaseDatabaseHelper.createRoom(this.roomCode, host);
        this.roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        host.setCurrentRoom(this);
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
}
