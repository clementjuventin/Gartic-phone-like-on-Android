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
        DatabaseReference roomRef = database.getReference(roomCode);
        String key = roomRef.push().getKey();
        roomRef.child(key).child("playerName").setValue(host.getPlayerName());

        host.setPlayerId(key);

        return roomRef;
    }
    public static DatabaseReference joinRoom(String roomCode,Player player, List<Player> players, RoomDataListener rdl) {
        DatabaseReference roomRef = database.getReference(roomCode);

        player.setPlayerId(roomRef.push().getKey());
        roomRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                roomRef.child(player.getPlayerId()).child("playerName").setValue(player.getPlayerName());
                for (DataSnapshot ds:task.getResult().getChildren()) {
                    players.add(new Player((String) ds.child("playerName").getValue(), ds.getKey()));
                }
                players.add(player);
                for (Player p:players) {
                    Log.d("DEV:ONCOMPLETE", p.getPlayerId()+" "+ p.getPlayerName());
                }
                rdl.initialize();
            }
        });

        return roomRef;
    }
    public static Task<DataSnapshot> getRooms() {
        return database.getReference().get();
    }
}
