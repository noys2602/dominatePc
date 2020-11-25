package com.noy.dominatepc.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.noy.dominatepc.R;
import com.noy.dominatepc.server.Server;

public class PowerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_layout);
        findViewById(R.id.restart_button).setOnClickListener(this);
        findViewById(R.id.shutdown_button).setOnClickListener(this);
        findViewById(R.id.sleep_button).setOnClickListener(this);
        findViewById(R.id.logout_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restart_button:
                Server.getInstance().sendPowerRestartClickAction();
                break;
            case R.id.shutdown_button:
                Server.getInstance().sendPowerShutdownClickAction();
                break;
            case R.id.sleep_button:
                Server.getInstance().sendPowerSleepClickAction();
                break;
            case R.id.logout_button:
                Server.getInstance().sendPowerLogoutClickAction();
                break;
        }
    }
}
