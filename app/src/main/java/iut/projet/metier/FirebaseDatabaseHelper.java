package iut.projet.metier;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://applicationdessineitandroid-default-rtdb.europe-west1.firebasedatabase.app/");

    public static DatabaseReference createRoom(String roomCode, Player host){
        DatabaseReference roomRef = database.getReference("ABC");//roomCode);//->debug
        String key = roomRef.push().getKey();
        host.setPlayerId(key);

        roomRef.child("players").child(host.getPlayerId()).setValue(host.toDictionary());

        roomRef.child("game").child("locked").setValue("0");

        return roomRef;
    }
    public static DatabaseReference joinRoom(String roomCode,Player player, List<Player> players, RoomDataListener rdl) {
        DatabaseReference roomRef = database.getReference(roomCode);

        player.setPlayerId(roomRef.push().getKey());

        roomRef.child("players").child(player.getPlayerId()).setValue(player.toDictionary());
        roomRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                rdl.initialize();
            }
        });
        return roomRef;
    }
    public static Task<DataSnapshot> getRooms() {
        return database.getReference().get();
    }
    public static void isLocked(String roomCode, RoomStateListener rsl){
        DatabaseReference roomRef = database.getReference(roomCode);
        roomRef.child("game").child("locked");
        roomRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().hasChild("game")){
                    if(((String) task.getResult().child("game").child("locked").getValue()).equals("0")){
                        rsl.roomExist();
                        return;
                    }
                }
                rsl.roomNotExist();
            }
        });
    }

    public static void setPlayerReady(String roomCode, Player player) {
        DatabaseReference roomRef = database.getReference(roomCode);
        roomRef.child("players").child(player.getPlayerId()).child("ready").setValue(player.isReady()?"1":"0");
    }
    public static void setLocked(String roomCode, String state) {
        DatabaseReference roomRef = database.getReference(roomCode);
        roomRef.child("game").child("locked").setValue(state);
    }
}
