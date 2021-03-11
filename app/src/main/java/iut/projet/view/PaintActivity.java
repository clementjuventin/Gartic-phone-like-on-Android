package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import iut.projet.R;
import iut.projet.metier.FirebaseDatabaseHelper;
import iut.projet.metier.FirebaseStorageHelper;
import iut.projet.metier.Player;
import iut.projet.metier.Room;
import iut.projet.metier.RoomDataListener;
import iut.projet.metier.StorageInterractionListener;
import iut.projet.paint.PaintView;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;

    private Player player;
    private TextView chrono;
    private TextView expressionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_activity);

        //Paint view settings
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        chrono = ((TextView) findViewById(R.id.paint_activity_chrono));
        expressionTextView = ((TextView) findViewById(R.id.paint_activity_expression));

        AppCompatActivity thisActivity = this;
        player = new Player(getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"), false);

        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                int turn = Integer.parseInt(getIntent().getStringExtra("currentTurn"));
                FirebaseDatabaseHelper.getExpression(player.getCurrentRoom().getRoomCode(), player.getCurrentRoom().getLastPlayerId(player,turn), turn, expressionTextView);
                new CountDownTimer(60*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(String.valueOf(millisUntilFinished / 1000));
                    }
                    public void onFinish() {
                        Bitmap image = paintView.getmBitmap();
                        StorageInterractionListener sil = new StorageInterractionListener() {
                            @Override
                            public void complete() {
                                player.setReady(true);
                            }

                            @Override
                            public void failed() {

                            }
                        };
                        FirebaseStorageHelper.sendImage(image, player.getPlayerId()+getIntent().getStringExtra("currentTurn"), sil);
                    }
                }.start();
            }

            @Override
            public void update() {

            }

            @Override
            public void lunch() {
                player.setReady(false);
                Intent intent = new Intent(thisActivity, DescribeImageActivity.class);
                intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                intent.putExtra("playerId",player.getPlayerId());
                intent.putExtra("playerName",player.getPlayerName());
                intent.putExtra("currentTurn", getIntent().getStringExtra("currentTurn"));
                startActivity(intent);
            }
        };
        new Room(getIntent().getStringExtra("roomCode"),player, rdl);
    }

    public void changeColorToBlue(View view) {
        paintView.setCurrentColor(Color.BLUE);
    }

    public void changeColorToGreen(View view) {
        paintView.setCurrentColor(Color.GREEN);
    }

    public void changeColorToRed(View view) {
        paintView.setCurrentColor(Color.RED);
    }

    public void changeColorToYellow(View view) {
        paintView.setCurrentColor(Color.YELLOW);
    }

    public void changeColorToBlack(View view) {
        paintView.setCurrentColor(Color.BLACK);
    }

    public void back(View view) {
        paintView.deleteLastFingerPath();
    }

}
