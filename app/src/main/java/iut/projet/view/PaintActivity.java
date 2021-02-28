package iut.projet.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import iut.projet.R;
import iut.projet.metier.FirebaseStorageHelper;
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


        //((Button) R.id.paint_activity_validateButton).addOnLayoutChangeListener();
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

    public void validate(View view) {
        Bitmap image = paintView.getmBitmap();

        FirebaseStorageHelper.sendImage(image, "test.jpg");

        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput("TestImage.jpeg", Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException",e.getMessage());
        } catch (IOException e) {
            Log.d("IOException",e.getMessage());
        }
        Intent intent = new Intent(this, DescribeImageActivity.class);
        startActivity(intent);
    }
}
