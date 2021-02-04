package iut.projet.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import iut.projet.R;
import iut.projet.paint.PaintView;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_activity);
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
    }

    public void changeColorToBlue(View view) {
        paintView.setCurrentColor(Color.BLUE);
    }

    public void changeColorToGreen(View view) {
        paintView.setCurrentColor(Color.GREEN);
    }

    public void changeColorToRed(View view) {
        paintView.setCurrentColor(Color.RED);
    }

    public void changeColorToYellow(View view) {
        paintView.setCurrentColor(Color.YELLOW);
    }

    public void changeColorToBlack(View view) {
        paintView.setCurrentColor(Color.BLACK);
    }

    public void back(View view) {
        paintView.deleteLastFingerPath();
    }
}
