package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);
        preferences = this.getSharedPreferences(
                "fr.iut.cours", Context.MODE_PRIVATE);

        inputText=(TextInputLayout) findViewById(R.id.activite_principale_player_name);
        inputText.getEditText().setText(preferences.getString("fr.iut.cours.name", "Incognito"));

        btnHost=findViewById(R.id.host);
        btnJoin=findViewById(R.id.join);
        btnRules=findViewById(R.id.rules);
        btnHost.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnRules.setOnClickListener(this);

        @Override
        public void onClick(View v) {
            name = inputText.getEditText().getText().toString();
            if (v.getId() == R.id.host) {
                String name = ((TextInputLayout)findViewById(R.id.activite_principale_player_name)).getEditText().getText().toString();
                Intent intent = new Intent(this, HostActivity.class);
                intent.putExtra("playerName",name);

                updatePreferencesForName(name);

                startActivity(intent);
            }
            if (v.getId() == R.id.join) {
                String name = ((TextInputLayout)findViewById(R.id.activite_principale_player_name)).getEditText().getText().toString();
                Intent intent = new Intent(this, JoinActivity.class);
                intent.putExtra("playerName",name);

                updatePreferencesForName(name);

                startActivity(intent);
            }
            if (v.getId() == R.id.rules) {
            }
        }
    }
    private void updatePreferencesForName(String name){
        preferences.edit().putString("fr.iut.cours.name", name.isEmpty()?"Incognito":name).apply();
    }
}

