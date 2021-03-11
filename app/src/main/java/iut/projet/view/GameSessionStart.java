package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.metier.FirebaseDatabaseHelper;
import iut.projet.metier.Player;
import iut.projet.metier.Room;
import iut.projet.metier.RoomDataListener;

public class GameSessionStart extends AppCompatActivity {
    private Player player;
    private TextView chrono;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_session_start);

        chrono = ((TextView) findViewById(R.id.gamesessionstart_expression));

        AppCompatActivity thisActivity = this;

        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                new CountDownTimer(30*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(String.valueOf(millisUntilFinished / 1000));
                    }
                    public void onFinish() {
                        player.sendExpression(1, ((TextInputLayout) findViewById(R.id.gamesessionstart_expression_player_name)).getEditText().getText().toString());
                        FirebaseDatabaseHelper.setLocked(player.getCurrentRoom().getRoomCode(), "1");
                        Intent intent = new Intent(thisActivity, PaintActivity.class);
                        intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                        intent.putExtra("playerId",player.getPlayerId());
                        intent.putExtra("playerName",player.getPlayerName());
                        intent.putExtra("currentTurn","1");
                        startActivity(intent);
                    }
                }.start();
            }
            @Override
            public void update() {

            }
            @Override
            public void lunch() {

            }
        };
        player = new Player(getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"), true);
        new Room(getIntent().getStringExtra("roomCode"),player,rdl);
    }
}
