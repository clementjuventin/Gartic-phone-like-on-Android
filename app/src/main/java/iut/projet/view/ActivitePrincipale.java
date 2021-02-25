package iut.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import iut.projet.R;

public class ActivitePrincipale extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_principale);
    }

    public void hostButton(View view) {
        Intent intent = new Intent(this, HostActivity.class);
        startActivity(intent);
    }

    public void joinButton(View view) {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }
}

