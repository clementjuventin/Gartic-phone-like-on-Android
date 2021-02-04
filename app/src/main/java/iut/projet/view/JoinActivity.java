package iut.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import iut.projet.R;

public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);
    }

    public void enterButton(View view) {
        Intent intent = new Intent(this, PaintActivity.class);
        startActivity(intent);
    }
}
