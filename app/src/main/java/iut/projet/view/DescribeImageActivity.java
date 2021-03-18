package iut.projet.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.controller.FirebaseDatabaseHelper;
import iut.projet.controller.FirebaseStorageHelper;
import iut.projet.model.metier.LoadImage;
import iut.projet.model.metier.Player;
import iut.projet.model.metier.Room;
import iut.projet.controller.RoomDataListener;
import iut.projet.controller.StorageConnectionListener;

public class DescribeImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private Player player;
    private TextView chrono;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_image_activity);
        imageView = findViewById(R.id.describe_image_activity_image);

        chrono = ((TextView) findViewById(R.id.describe_image_activity_chrono));
        AppCompatActivity thisActivity = this;

        StorageConnectionListener scl = new StorageConnectionListener() {
            @Override
            public void loadUri(Uri uri) {
                LoadImage loadImage = new LoadImage(imageView);
                loadImage.execute(uri.toString());
            }
        };

        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                //Permet de récupérer les images
                int turn = Integer.parseInt(getIntent().getStringExtra("currentTurn"));
                //Log.d("DEV", "SupposedName: "+player.getCurrentRoom().getLastPlayerId(player,turn)+String.valueOf(turn));
                FirebaseStorageHelper.getImage(player.getCurrentRoom().getLastPlayerId(player,turn)+String.valueOf(turn), scl);
                new CountDownTimer(30*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(String.valueOf(millisUntilFinished / 1000));
                    }
                    public void onFinish() {
                        player.sendExpression(turn+1, ((TextInputLayout) findViewById(R.id.describe_image_activity_player_suggestion)).getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                player.setReady(true);
                            }
                        });
                    }
                }.start();
            }
            @Override
            public void update() {

            }
            @Override
            public void launch() {
                int turn = Integer.valueOf(getIntent().getStringExtra("currentTurn"));
                int playerSize = player.getCurrentRoom().getPlayers().size();
                if(playerSize/2+playerSize%2==turn){
                    Intent intent = new Intent(thisActivity, ResultViewStart.class);
                    intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                    intent.putExtra("playerId",player.getPlayerId());
                    intent.putExtra("playerName",player.getPlayerName());
                    intent.putExtra("period", String.valueOf(0));
                    startActivity(intent);
                }
                else {
                    player.setReady(false);
                    Intent intent = new Intent(thisActivity, PaintActivity.class);
                    intent.putExtra("roomCode", player.getCurrentRoom().getRoomCode());
                    intent.putExtra("playerId", player.getPlayerId());
                    intent.putExtra("playerName", player.getPlayerName());
                    intent.putExtra("currentTurn", String.valueOf(turn+1));
                    startActivity(intent);
                }
            }
        };

        player = new Player( getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"),false);
        new Room(getIntent().getStringExtra("roomCode"),player,rdl);
    }
    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabaseHelper.removePlayer(player, player.getCurrentRoom().getRoomCode());
        Intent intent = new Intent(this, ActivitePrincipale.class);
        startActivity(intent);
    }
}
