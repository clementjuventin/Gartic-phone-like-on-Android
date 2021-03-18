package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import iut.projet.R;
import iut.projet.controller.FirebaseDatabaseHelper;
import iut.projet.model.metier.Player;
import iut.projet.controller.RoomDataListener;

public class HostActivity extends AppCompatActivity implements View.OnClickListener {
    private Player player;
    private RoomDataListener rdl;
    private Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity);
        Context context = this;
        RecyclerView listView = findViewById(R.id.host_activity_listView);

        AppCompatActivity thisActivity = this;
        rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                listView.setLayoutManager(new LinearLayoutManager(context));
                listView.setAdapter(new AdaptateurRoomList(player.getCurrentRoom().getPlayers()));
            }

            @Override
            public void update() {
                if(listView.getAdapter() != null)
                    ((AdaptateurRoomList) listView.getAdapter()).updatePlayers(player.getCurrentRoom().getPlayers());
            }

            @Override
            public void launch() {
                FirebaseDatabaseHelper.setLocked(player.getCurrentRoom().getRoomCode(), "1");
                player.setReady(false);
                Intent intent = new Intent(thisActivity, GameSessionStart.class);
                intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                intent.putExtra("playerId",player.getPlayerId());
                intent.putExtra("playerName",player.getPlayerName());
                startActivity(intent);
            }
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
        btnStart=findViewById(R.id.host_activity_start_button);
        btnStart.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.host_activity_start_button) {
            //Il faudrait mettre l'état du joueur à prêt. Si tous les joueurs sont prêts ça commence
            player.setReady(!player.isReady());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabaseHelper.removePlayer(player, player.getCurrentRoom().getRoomCode());
        Intent intent = new Intent(this, ActivitePrincipale.class);
        startActivity(intent);
    }
}
