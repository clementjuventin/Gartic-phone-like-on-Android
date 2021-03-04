package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;

public class ActivitePrincipale extends AppCompatActivity {
    private SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);
        preferences = this.getSharedPreferences(
                "fr.iut.cours", Context.MODE_PRIVATE);
        ((TextInputLayout)findViewById(R.id.activite_principale_player_name)).getEditText().setText(preferences.getString("fr.iut.cours.name", "Incognito"));
    }

    public void hostButton(View view) {
        String name = ((TextInputLayout)findViewById(R.id.activite_principale_player_name)).getEditText().getText().toString();
        Intent intent = new Intent(this, HostActivity.class);
        intent.putExtra("playerName",name);

        updatePreferencesForName(name);

        startActivity(intent);
    }

    public void joinButton(View view) {
        String name = ((TextInputLayout)findViewById(R.id.activite_principale_player_name)).getEditText().getText().toString();
        Intent intent = new Intent(this, JoinActivity.class);
        intent.putExtra("playerName",name);

        updatePreferencesForName(name);

        startActivity(intent);
    }
    private void updatePreferencesForName(String name){
        preferences.edit().putString("fr.iut.cours.name", name.isEmpty()?"Incognito":name).apply();
    }
}

