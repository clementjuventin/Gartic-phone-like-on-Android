package iut.projet.metier;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://applicationdessineitandroid-default-rtdb.europe-west1.firebasedatabase.app/");

    public static DatabaseReference createRoom(String roomCode, Player host){
        DatabaseReference roomRef = database.getReference(roomCode);
        String key = roomRef.push().getKey();
        roomRef.child(key).setValue(host);

        host.setPlayerId(key);

        return roomRef;
    }

    public static DatabaseReference joinRoom(String roomCode, Player player) {
        DatabaseReference roomRef = database.getReference(roomCode);//Tester si exist
        String key = roomRef.push().getKey();
        roomRef.child(key).setValue(player);

        player.setPlayerId(key);

        return roomRef;
    }
}
