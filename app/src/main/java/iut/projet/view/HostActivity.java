package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import iut.projet.R;
import iut.projet.metier.FirebaseStorageHelper;
import iut.projet.metier.Player;
import iut.projet.metier.Room;

public class HostActivity extends AppCompatActivity implements View.OnClickListener {
    private Player host;
    Context ctxt=this;
    Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity);

        host = new Player(ActivitePrincipale.getName());
        host.createRoom();

        ((TextView) findViewById(R.id.host_activity_code)).setText(host.getCurrentRoom().getRoomCode());

        RecyclerView listView = findViewById(R.id.host_activity_listView);

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new AdaptateurRoomList(host.getCurrentRoom().getPlayers()));

        btnStart=findViewById(R.id.host_activity_start_button);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.host_activity_start_button) {
            //Verif nombre de joueurs >= 3, if(){} else {}
            Intent intent = new Intent(ctxt, PaintActivity.class);
            startActivity(intent);
        }
    }
}
