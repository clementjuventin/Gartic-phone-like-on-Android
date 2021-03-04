package iut.projet.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import iut.projet.R;
import iut.projet.metier.Player;
import iut.projet.metier.Room;
import iut.projet.metier.RoomDataListener;
import iut.projet.metier.RoomStateListener;

public class HostActivity extends AppCompatActivity {
    private Player player;
    private RoomDataListener rdl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity);

        Context context = this;


        RecyclerView listView = findViewById(R.id.host_activity_listView);

        rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                listView.setLayoutManager(new LinearLayoutManager(context));
                listView.setAdapter(new AdaptateurRoomList(player.getCurrentRoom().getPlayers()));
            }
            /*
            if(listView.getAdapter() != null)
            ((AdaptateurRoomList) listView.getAdapter()).updatePlayers(player.getCurrentRoom().getPlayers());
             */
            //
        };
        player = new Player(getIntent().getStringExtra("playerName"));
        if(getIntent().getStringExtra("roomCode")!=null){
            ((TextView) findViewById(R.id.host_activity_code)).setText(getIntent().getStringExtra("roomCode"));
            player.connectToRoom(getIntent().getStringExtra("roomCode"),rdl);
        }
        else {
            player.createRoom(rdl);
            ((TextView) findViewById(R.id.host_activity_code)).setText(player.getCurrentRoom().getRoomCode());
        }
    }
}
