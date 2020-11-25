package com.noy.dominatepc.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.noy.dominatepc.R;
import com.noy.dominatepc.server.Server;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText ipField = (EditText) findViewById(R.id.ip_field);
        Button loginButton = (Button) findViewById(R.id.login_connect_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ServerConnectTask().execute(ipField.getText().toString());
            }
        });


    }

    private void onConnectSuccessfully() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public class ServerConnectTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... args) {
            return Server.getInstance().connect(args[0]);
        }

        @Override
        protected void onPostExecute(Boolean connected) {
            if (connected) {
                onConnectSuccessfully();
            }
        }
    }
}
