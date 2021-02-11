package iut.projet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import iut.projet.R;

public class DescribeImageActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_image_activity);
        imageView = findViewById(R.id.describe_image_activity_image);
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput("TestImage.jpeg");
            imageView.setImageDrawable(Drawable.createFromStream(inputStream,"TestImage.jpeg"));
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException",e.getMessage());
        } catch (IOException e) {
            Log.d("IOException",e.getMessage());
        }
    }
}
