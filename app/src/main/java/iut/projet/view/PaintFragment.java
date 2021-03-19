package iut.projet.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.controller.FirebaseDatabaseHelper;
import iut.projet.controller.FirebaseStorageHelper;
import iut.projet.controller.RoomStateListener;
import iut.projet.model.metier.Player;
import iut.projet.model.metier.Room;
import iut.projet.controller.RoomDataListener;
import iut.projet.controller.StorageInterractionListener;
import iut.projet.model.paint.PaintView;

public class PaintFragment extends Fragment implements View.OnClickListener{

    private PaintView paintView;

    private Player player;
    private  int turn;
    private TextView chrono;
    private TextView expressionTextView;

    public PaintFragment(Player player, int turn){
        super(R.layout.paint_activity);
        this.player = player;
        this.turn = turn;
    }

    @Override
    public void onStart() {
        super.onStart();

        getView().findViewById(R.id.paint_activity_blueButton).setOnClickListener(this);
        getView().findViewById(R.id.paint_activity_backButton).setOnClickListener(this);
        getView().findViewById(R.id.paint_activity_blackButton).setOnClickListener(this);
        getView().findViewById(R.id.paint_activity_redButton).setOnClickListener(this);
        getView().findViewById(R.id.paint_activity_greenButton).setOnClickListener(this);
        getView().findViewById(R.id.paint_activity_yellowButton).setOnClickListener(this);

        //Paint view settings
        paintView = (PaintView) getView().findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        chrono = ((TextView) getView().findViewById(R.id.paint_activity_chrono));
        expressionTextView = ((TextView) getView().findViewById(R.id.paint_activity_expression));

        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                FirebaseDatabaseHelper.getExpression(player.getCurrentRoom().getRoomCode(), player.getCurrentRoom().getLastPlayerId(player), turn, expressionTextView);
                new CountDownTimer(60 * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(String.valueOf(millisUntilFinished / 1000));
                        if(millisUntilFinished<1001){
                            paintView.setDrawIsEnable(false);
                        }
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
                        FirebaseStorageHelper.sendImage(image, player.getPlayerId() + String.valueOf(turn), sil);
                    }
                }.start();
            }

            @Override
            public void update() {

            }

            @Override
            public void launch() {
                player.setReady(false);
                player.getCurrentRoom().disableRoomEvents();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DescribeImageFragment(player, turn+1), null).commit();
            }
        };
        new Room(player.getCurrentRoom().getRoomCode(), player, rdl);
    }
    @Override
    public void onStop() {
        super.onStop();
        player.getCurrentRoom().disableRoomEvents();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paint_activity_blueButton:
                paintView.setCurrentColor(Color.BLUE);
                break;
            case R.id.paint_activity_greenButton:
                paintView.setCurrentColor(Color.GREEN);
                break;
            case R.id.paint_activity_redButton:
                paintView.setCurrentColor(Color.RED);
                break;
            case R.id.paint_activity_blackButton:
                paintView.setCurrentColor(Color.BLACK);
                break;
            case R.id.paint_activity_yellowButton:
                paintView.setCurrentColor(Color.YELLOW);
                break;
            case R.id.paint_activity_backButton:
                paintView.deleteLastFingerPath();
                break;
        }
    }
}