package com.noy.dominatepc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.noy.dominatepc.R;
import com.noy.dominatepc.server.Server;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_layout);
        findViewById(R.id.volume_down_button).setOnClickListener(this);
        findViewById(R.id.mute_button).setOnClickListener(this);
        findViewById(R.id.volume_up_button).setOnClickListener(this);
        findViewById(R.id.play_button).setOnClickListener(this);
        findViewById(R.id.stop_button).setOnClickListener(this);
        findViewById(R.id.ff_button).setOnClickListener(this);
        findViewById(R.id.rewind_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.volume_down_button:
                Server.getInstance().sendMediaVolumeDownClickAction();
                break;
            case R.id.mute_button:
                Server.getInstance().sendMediaMuteClickAction();
                break;
            case R.id.volume_up_button:
                Server.getInstance().sendMediaVolumeUpClickAction();
                break;
            case R.id.stop_button:
                Server.getInstance().sendMediaStopClickAction();
                break;
            case R.id.play_button:
                Server.getInstance().sendMediaPlayClickAction();
                break;
            case R.id.rewind_button:
                Server.getInstance().sendMediaRewindClickAction();
                break;
            case R.id.ff_button:
                Server.getInstance().sendMediaForwardClickAction();
        }
    }
}
