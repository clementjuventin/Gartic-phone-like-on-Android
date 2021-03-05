package iut.projet.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import iut.projet.R;
import iut.projet.metier.Player;
import iut.projet.metier.RoomDataListener;

public class GameSessionStart extends AppCompatActivity {
    private Player player;
    private RoomDataListener rdl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_session_start);

        AppCompatActivity thisActivity = this;
    }
}
