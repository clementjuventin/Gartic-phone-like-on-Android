package iut.projet.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.model.metier.Player;
import iut.projet.model.metier.Room;
import iut.projet.controller.RoomDataListener;

public class GameStartFragment extends Fragment {
    private Player player;
    private TextView chrono;

    @RequiresApi(api = Build.VERSION_CODES.N)

    public GameStartFragment(Player player){
        super(R.layout.game_session_start);
        this.player = player;
    }

    @Override
    public void onStart() {
        super.onStart();

        chrono = ((TextView) getView().findViewById(R.id.gamesessionstart_expression));

        RoomDataListener rdl = new RoomDataListener() {
            @Override
            public void initialize() {
                new CountDownTimer(30*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        chrono.setText(String.valueOf(millisUntilFinished / 1000));
                    }
                    public void onFinish() {
                        player.sendExpression(1, ((TextInputLayout) getView().findViewById(R.id.gamesessionstart_expression_player_name)).getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener() {
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
                Intent intent = new Intent(getActivity(), PaintFragment.class);
                player.setReady(false);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PaintFragment(player, 1), null).commit();
            }
        };
        new Room(player.getCurrentRoom().getRoomCode(),player,rdl);
    }
}
