package iut.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.metier.Player;

public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        if(savedInstanceState!=null){
            Log.d("dev",(String) getIntent().getExtras().get("playerName"));
        }
    }

    public void enterButton(View view) {
        Player p = new Player("Clem");
        p.connectToRoom(((TextInputLayout)findViewById(R.id.joinActivity_code)).getEditText().getText().toString());

        Intent intent = new Intent(this, HostActivity.class);
        startActivity(intent);
    }
}
