package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import iut.projet.R;
import iut.projet.metier.Player;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener{

    Context ctxt = this;
    Button btnEnter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        btnEnter=findViewById(R.id.joinActivity_entrer_button);
        btnEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.joinActivity_entrer_button) {
            //Player p = new Player("Clem");
            //p.connectToRoom(((TextInputLayout)findViewById(R.id.joinActivity_code)).getEditText().getText().toString());
            //Intent intent = new Intent(ctxt, HostActivity.class);
            //startActivity(intent);
            Intent intent = new Intent(ctxt, PaintActivity.class);
            startActivity(intent);
        }
    }









    /*public void enterButton(View view) {
        Intent intent = new Intent(this, PaintActivity.class);
        //Verif si vide
    public void enterButton(View view) {
        Player p = new Player("Clem");
        p.connectToRoom(((TextInputLayout)findViewById(R.id.joinActivity_code)).getEditText().getText().toString());

        Intent intent = new Intent(this, HostActivity.class);
        startActivity(intent);
    }*/
}
