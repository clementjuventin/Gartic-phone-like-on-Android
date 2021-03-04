package iut.projet.metier;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class Player {
    //Identificateur du joueur
    private String playerId;
    private String playerName;
    private boolean ready;

    private Room currentRoom;

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player(String playerName, String playerId)
    {
        this(playerName);
        this.playerId = playerId;
    }
    public Player(String playerName)
    {
        this();
        this.playerName = playerName;
    }
    public Player()
    {
        this.ready = false;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void createRoom(RoomDataListener rdl){
        currentRoom = new Room(this, rdl);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void connectToRoom(String roomCode, RoomDataListener rdl){
        currentRoom = new Room(roomCode, this, rdl);
    }
    public void addRoomStateListener(RoomStateListener rsl, String roomCode){
        FirebaseDatabaseHelper.getRooms().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().hasChild(roomCode)){
                    rsl.roomExist();
                }
                else{
                    rsl.roomNotExist();
                }
            }
        });
    }
}
