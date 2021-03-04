package iut.projet.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import iut.projet.R;
import iut.projet.metier.Player;
import iut.projet.metier.Room;

public class HostActivity extends AppCompatActivity {
    private Player host;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity);

        host = new Player(getIntent().getStringExtra("playerName"));
        host.createRoom();

        ((TextView) findViewById(R.id.host_activity_code)).setText(host.getCurrentRoom().getRoomCode());

        RecyclerView listView = findViewById(R.id.host_activity_listView);

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new AdaptateurRoomList(host.getCurrentRoom().getPlayers()));
    }
}
