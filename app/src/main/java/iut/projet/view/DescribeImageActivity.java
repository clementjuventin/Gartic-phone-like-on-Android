package iut.projet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.net.MalformedURLException;
import java.net.URL;

import iut.projet.R;

public class DescribeImageActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_image_activity);
        imageView = findViewById(R.id.describe_image_activity_image);

        /////////////////////////////////////////////
        URL url = null;
        try {
            url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmp);
        /////////////////////////////////////////////

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
