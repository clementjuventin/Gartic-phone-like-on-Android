package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import iut.projet.R;

public class ActivitePrincipale extends AppCompatActivity implements View.OnClickListener{

    Context ctxt = this;
    Button btnHost;
    Button btnJoin;
    Button btnRules;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);

        //JoinButton();


        btnHost=findViewById(R.id.host);
        btnJoin=findViewById(R.id.join);
        btnRules=findViewById(R.id.rules);
        btnHost.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnRules.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Log.e("Test","Je suis passé par le onClick");
        if (v.getId() == R.id.host) {
            Log.e("Test","Je suis passé par le onClick : Host");
        }
        if (v.getId() == R.id.join) {
            Intent intent = new Intent(ctxt, JoinActivity.class);
            Log.e("Test","Je suis passé par le onClick : Join");
            startActivity(intent);
        }
        if (v.getId() == R.id.rules) {
            Log.e("Test","Je suis passé par le onClick : Rules");
        }
    }







}

