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

import java.util.List;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://applicationdessineitandroid-default-rtdb.europe-west1.firebasedatabase.app/");

    public static DatabaseReference createRoom(String roomCode, Player host){
        DatabaseReference roomRef = database.getReference(roomCode);
        String key = roomRef.push().getKey();
        roomRef.child(key).setValue(host.getPlayerName());

        host.setPlayerId(key);

        return roomRef;
    }
    public static DatabaseReference joinRoom(String roomCode,Player player) {
        DatabaseReference roomRef = database.getReference(roomCode);

        String key = roomRef.push().getKey();
        roomRef.child(key).setValue(player.getPlayerName());

        player.setPlayerId(key);

        return roomRef;
    }
    public static Task<DataSnapshot> getRooms() {
        return database.getReference().get();
    }
}
