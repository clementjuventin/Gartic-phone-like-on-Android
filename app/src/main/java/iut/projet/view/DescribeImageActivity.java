package iut.projet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import iut.projet.R;
import iut.projet.metier.FirebaseDatabaseHelper;
import iut.projet.metier.FirebaseStorageHelper;
import iut.projet.metier.LoadImage;
import iut.projet.metier.Player;
import iut.projet.metier.Room;
import iut.projet.metier.StorageConnectionListener;

public class DescribeImageActivity extends AppCompatActivity {
    ImageView imageView;
    private Player player;
    private Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_image_activity);
        imageView = findViewById(R.id.describe_image_activity_image);

        player = new Player( getIntent().getStringExtra("playerName"), getIntent().getStringExtra("playerId"),true);
        room = player.getCurrentRoom();

        StorageConnectionListener scl = new StorageConnectionListener() {
            @Override
            public void loadUri(Uri uri) {
                LoadImage loadImage = new LoadImage(imageView);
                loadImage.execute(uri.toString());
            }
        };
        //FirebaseStorageHelper.getImage(player.getPlayerId()+getIntent().getStringExtra("currentTurn"), scl);
        FirebaseStorageHelper.getImage("-MV7j6SNkUDThn7uHyxX"+"2", scl);
    }
}
