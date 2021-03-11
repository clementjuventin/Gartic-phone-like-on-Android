package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import iut.projet.R;
import iut.projet.metier.FirebaseDatabaseHelper;
import iut.projet.metier.FirebaseStorageHelper;
import iut.projet.metier.LoadImage;
import iut.projet.metier.Player;
import iut.projet.metier.Room;
import iut.projet.metier.RoomDataListener;
import iut.projet.metier.StorageConnectionListener;

public class DescribeImageActivity extends AppCompatActivity {
    ImageView imageView;
    private Player player;
    private TextView chrono;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_image_activity);
        imageView = findViewById(R.id.describe_image_activity_image);

        chrono = ((TextView) findViewById(R.id.describe_image_activity_chrono));
        AppCompatActivity thisActivity = this;

        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                new CountDownTimer(30*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(String.valueOf(millisUntilFinished / 1000));
                    }
                    public void onFinish() {
                        int turn = Integer.parseInt(getIntent().getStringExtra("currentTurn"))+1;
                        player.sendExpression(turn, ((TextInputLayout) findViewById(R.id.describe_image_activity_player_suggestion)).getEditText().getText().toString());
                        Intent intent = new Intent(thisActivity, PaintActivity.class);
                        intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                        intent.putExtra("playerId",player.getPlayerId());
                        intent.putExtra("playerName",player.getPlayerName());
                        intent.putExtra("currentTurn",String.valueOf(turn));
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

        player = new Player( getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"),true);
        new Room(getIntent().getStringExtra("roomCode"),player,rdl);

        StorageConnectionListener scl = new StorageConnectionListener() {
            @Override
            public void loadUri(Uri uri) {
                LoadImage loadImage = new LoadImage(imageView);
                loadImage.execute(uri.toString());
            }
        };
        //Permet de récupérer les images
        FirebaseStorageHelper.getImage(player.getPlayerId()+getIntent().getStringExtra("currentTurn"), scl);
    }
}
