package com.bradchao.hiskiophotoselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);

        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .build());
    }

    public void go(View view) {
        Album.album(this) // Image and video mix options.
                .multipleChoice() // Multi-Mode, Single-Mode: singleChoice().
                .columnCount(3) // The number of columns in the page list.
                .selectCount(1)  // Choose up to a few images.
                .camera(true) // Whether the camera appears in the Item.
                .cameraVideoQuality(1) // Video quality, [0, 1].
                .cameraVideoLimitDuration(Long.MAX_VALUE) // The longest duration of the video is in milliseconds.
                .cameraVideoLimitBytes(Long.MAX_VALUE) // Maximum size of the video, in bytes.
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        afterSelected(result);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Log.v("bradlog", "XX");
                    }
                })
                .start();
    }

    private void afterSelected(ArrayList<AlbumFile> result){
        for(AlbumFile albumFile : result){
            Log.v("bradlog", albumFile.getPath());

            //img.setImageBitmap(BitmapFactory.decodeFile(albumFile.getPath()));
            Glide.with(this).load(albumFile.getPath()).into(img);
        }
    }
}