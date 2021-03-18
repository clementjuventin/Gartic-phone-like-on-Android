package iut.projet.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import iut.projet.R;
import iut.projet.controller.FirebaseDatabaseHelper;
import iut.projet.controller.FirebaseStorageHelper;
import iut.projet.model.metier.Player;
import iut.projet.model.metier.Room;
import iut.projet.controller.RoomDataListener;
import iut.projet.controller.StorageInterractionListener;
import iut.projet.model.paint.PaintView;

public class PaintFragment extends Fragment {

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
                FirebaseDatabaseHelper.getExpression(player.getCurrentRoom().getRoomCode(), player.getCurrentRoom().getLastPlayerId(player, turn), turn, expressionTextView);
                new CountDownTimer(60 * 1000, 1000) {
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
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DescribeImageFragment(player, turn), null).commit();
            }
        };
        new Room(player.getCurrentRoom().getRoomCode(), player, rdl);
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
