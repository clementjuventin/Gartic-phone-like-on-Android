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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import iut.projet.R;
import iut.projet.metier.FirebaseStorageHelper;
import iut.projet.metier.Player;
import iut.projet.metier.Room;
import iut.projet.metier.RoomDataListener;
import iut.projet.paint.PaintView;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;
    Context ctxt = this;

    private Player player;
    private RoomDataListener rdl;
    private Room room;

    private TextView chrono;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_activity);
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        rdl = new RoomDataListener() {
            @Override
            public void initialize() {

            }

            @Override
            public void update() {

            }

            @Override
            public void lunch() {

            }
        };

        chrono = ((TextView) findViewById(R.id.paint_activity_chrono));
        player = new Player(getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"), true);
        room = new Room(getIntent().getStringExtra("roomCode"),player,rdl);

        AppCompatActivity thisActivity = this;
        new CountDownTimer(15*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                chrono.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public void onFinish() {
                Bitmap image = paintView.getmBitmap();
                FirebaseStorageHelper.sendImage(image, player.getPlayerId()+getIntent().getStringExtra("currentTurn"));
                /*
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput("TestImage.jpeg", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    Log.d("FileNotFoundException",e.getMessage());
                } catch (IOException e) {
                    Log.d("IOException",e.getMessage());
                }
                 */
                /*
                Intent intent = new Intent(ctxt, DescribeImageActivity.class);
                startActivity(intent);

                 */
                /*
                player.sendExpression(1, ((TextInputLayout) findViewById(R.id.gamesessionstart_expression_player_name)).getEditText().getText().toString());
                Intent intent = new Intent(thisActivity, PaintActivity.class);
                intent.putExtra("roomCode",player.getCurrentRoom().getRoomCode());
                intent.putExtra("playerId",player.getPlayerId());
                intent.putExtra("playerName",player.getPlayerName());
                startActivity(intent);

                 */
            }
        }.start();
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
