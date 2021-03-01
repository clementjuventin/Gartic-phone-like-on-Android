package iut.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import iut.projet.R;

public class ActivitePrincipale extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    Button btnHost;
    Button btnJoin;
    Button btnRules;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);

        btnHost=findViewById(R.id.host);
        btnJoin=findViewById(R.id.join);
        btnRules=findViewById(R.id.rules);
        btnJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.join) {
            Log.e("Test","Je suis passé par le onClick");
            //intent = new Intent(this, JoinActivity.class);
            startActivity(new Intent(v.getContext(), JoinActivity.class));
        }
    }







}

