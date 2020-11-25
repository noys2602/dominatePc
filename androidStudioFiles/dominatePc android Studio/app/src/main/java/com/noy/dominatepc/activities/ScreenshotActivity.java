package com.noy.dominatepc.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.noy.dominatepc.R;
import com.noy.dominatepc.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotActivity extends AppCompatActivity {

    public static final String TAG = "ScreenshotActivity";

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        image = (ImageView) findViewById(R.id.screenshot_image);
        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = image.getDrawable();
                if (drawable != null) {
                    shareImage(((BitmapDrawable) drawable).getBitmap());
                } else {
                    Toast.makeText(ScreenshotActivity.this, "Screenshot was not taken", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.take_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.getInstance().sendScreenshotTakeClickAction();
            }
        });
    }

    private void shareImage(Bitmap bitmap) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            if (f.createNewFile()) {
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } else {
                Log.e(TAG, "Failed to create new file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg"));
        startActivity(Intent.createChooser(share, "Share Image"));
    }
}
