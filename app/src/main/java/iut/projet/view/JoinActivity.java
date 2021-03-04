package iut.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.metier.Player;
import iut.projet.metier.RoomStateListener;

public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);
    }

    public void enterButton(View view) {
        Player p = new Player(getIntent().getStringExtra("playerName"));

        AppCompatActivity thisActivity = this;
        RoomStateListener roomStateListener = new RoomStateListener() {
            @Override
            public void roomNotExist() {
                Toast.makeText(getApplicationContext(), R.string.conn_error_text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void roomExist() {
                Intent intent = new Intent(thisActivity, HostActivity.class);
                startActivity(intent);
            }
        };

        p.connectToRoom(((TextInputLayout)findViewById(R.id.joinActivity_code)).getEditText().getText().toString(), roomStateListener);
    }
}
