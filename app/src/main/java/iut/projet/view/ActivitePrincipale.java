package iut.projet.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import iut.projet.R;

public class ActivitePrincipale extends AppCompatActivity implements View.OnClickListener{

    Context ctxt = this;
    Button btnHost;
    Button btnJoin;
    Button btnRules;
    TextInputLayout inputText;
    public static String name;
        public static String getName() {return name;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);

        inputText=(TextInputLayout) findViewById(R.id.activite_principale_player_name);

        btnHost=findViewById(R.id.host);
        btnJoin=findViewById(R.id.join);
        btnRules=findViewById(R.id.rules);
        btnHost.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnRules.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = inputText.getEditText().getText().toString();
        if (v.getId() == R.id.host) {
            if (name.isEmpty()){
                name = "Hote";
            }
            Intent intent = new Intent(ctxt, HostActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.join) {
            if (name.isEmpty()){
                name = "Invite";
            }
            Intent intent = new Intent(ctxt, JoinActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rules) {
        }
    }




}

