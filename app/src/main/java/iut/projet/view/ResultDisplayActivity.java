package iut.projet.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import iut.projet.R;
import iut.projet.controller.FirebaseDatabaseHelper;
import iut.projet.controller.FirebaseStorageHelper;
import iut.projet.controller.RoomDataListener;
import iut.projet.controller.StorageConnectionListener;
import iut.projet.model.metier.LoadImage;
import iut.projet.model.metier.Player;
import iut.projet.model.metier.Room;

public class ResultDisplayActivity extends AppCompatActivity {
    private Player player;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.result_display_activity);

        TextView usernameTv = ((TextView) findViewById(R.id.pseudo_draw_result_display_activity));
        TextView nextUsernameTv = ((TextView) findViewById(R.id.pseudo_result_display_activity));
        TextView expressionTv = ((TextView) findViewById(R.id.expression_result_display_activity));

        imageView = findViewById(R.id.result_display_activity_image);

        AppCompatActivity thisActivity = this;
        StorageConnectionListener scl = new StorageConnectionListener() {
            @Override
            public void loadUri(Uri uri) {
                LoadImage loadImage = new LoadImage(imageView);
                loadImage.execute(uri.toString());
            }
        };

        int turn = Integer.parseInt(getIntent().getStringExtra("turn"));
        int period = Integer.parseInt(getIntent().getStringExtra("period"));
        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                usernameTv.setText(player.getCurrentRoom().getPlayers().get((turn+period)%player.getCurrentRoom().getPlayers().size()).getPlayerName());

                FirebaseStorageHelper.getImage(player.getCurrentRoom().getPlayers().get((turn+period)%player.getCurrentRoom().getPlayers().size()).getPlayerId() + String.valueOf(turn), scl);
                new CountDownTimer(20*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if(millisUntilFinished<10001){
                            nextUsernameTv.setText(player.getCurrentRoom().getPlayers().get((turn+period+1)%player.getCurrentRoom().getPlayers().size()).getPlayerName());
                            FirebaseDatabaseHelper.getExpression(player.getCurrentRoom().getRoomCode(), player.getCurrentRoom().getPlayers().get((turn+period)%player.getCurrentRoom().getPlayers().size()).getPlayerId(), turn, expressionTv);
                        }
                        if(millisUntilFinished<4001 && millisUntilFinished>999){
                            Toast.makeText(getApplicationContext(), String.valueOf((int)millisUntilFinished/1000), Toast.LENGTH_SHORT).show();
                        }
                    }
                    public void onFinish() {
                        player.setReady(true);
                    }
                }.start();
            }

            @Override
            public void update() {

            }

            @Override
            public void launch() {
                player.setReady(false);
                int playersCount = player.getCurrentRoom().getPlayers().size();
                if(period == playersCount-1){
                    Toast.makeText(getApplicationContext(), R.string.endOfTheGame, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(thisActivity, ActivitePrincipale.class);
                    startActivity(intent);
                }
                else if(turn == playersCount/2+playersCount%2){
                    Intent intent = new Intent(thisActivity, ResultViewStart.class);
                    intent.putExtra("roomCode", player.getCurrentRoom().getRoomCode());
                    intent.putExtra("playerName", player.getPlayerName());
                    intent.putExtra("playerId", player.getPlayerId());
                    intent.putExtra("period", String.valueOf(period+1));
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(thisActivity, ResultDisplayActivity.class);
                    intent.putExtra("roomCode", player.getCurrentRoom().getRoomCode());
                    intent.putExtra("playerName", player.getPlayerName());
                    intent.putExtra("playerId", player.getPlayerId());
                    intent.putExtra("turn", String.valueOf(turn+1));
                    intent.putExtra("period", String.valueOf(period));
                    startActivity(intent);
                }
            }
        };

        player = new Player(getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"), false);
        new Room(getIntent().getStringExtra("roomCode"), player, rdl);
    }
}
