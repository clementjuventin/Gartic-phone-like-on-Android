package iut.projet.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
                usernameTv.setText(player.getCurrentRoom().getPlayers().get(turn+period).getPlayerName());

                FirebaseStorageHelper.getImage(player.getCurrentRoom().getPlayers().get(turn+period).getPlayerId() + String.valueOf(turn), scl);
            }

            @Override
            public void update() {

            }

            @Override
            public void launch() {
                int playersCount = player.getCurrentRoom().getPlayers().size();
                if(turn == playersCount/2+playersCount%2){
                    Intent intent = new Intent(thisActivity, ResultViewStart.class);
                    intent.putExtra("roomCode", player.getCurrentRoom().getRoomCode());
                    intent.putExtra("playerName", player.getPlayerName());
                    intent.putExtra("playerId", player.getPlayerId());
                    intent.putExtra("period", String.valueOf(period+1));
                    startActivity(intent);
                }
                else {
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
