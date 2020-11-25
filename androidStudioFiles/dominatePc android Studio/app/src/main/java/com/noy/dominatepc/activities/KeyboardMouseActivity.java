package com.noy.dominatepc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.noy.dominatepc.R;
import com.noy.dominatepc.server.Server;

public class KeyboardMouseActivity extends AppCompatActivity implements View.OnTouchListener,
        TextWatcher {

    private EditText keyboardInput;
    private View mousePad;
    private Button leftBtn;
    private Button rightBtn;

    private Server server;
    private float initX;
    private float initY;
    private float disX;
    private float disY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_mouse_layout);

        server = Server.getInstance();

        keyboardInput = (EditText) findViewById(R.id.keyboard_input);
        keyboardInput.addTextChangedListener(this);

        mousePad = findViewById(R.id.mouse_pad);
        mousePad.setOnTouchListener(this);

        leftBtn = (Button) findViewById(R.id.mouse_left_click_button);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.getInstance().sendMouseLeftClickAction();
            }
        });
        rightBtn = (Button) findViewById(R.id.mouse_right_click_button);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.getInstance().sendMouseRightClickAction();
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (server.isConnected()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //save X and Y positions when user touches the TextView
                    initX = event.getX();
                    initY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    disX = event.getX() - initX; //Mouse movement in x direction
                    disY = event.getY() - initY; //Mouse movement in y direction

                    // set init to new position so that continuous mouse movement is captured
                    initX = event.getX();
                    initY = event.getY();
                    if (disX != 0 || disY != 0) {
                        Server.getInstance().sendMouseAction(new float[]{disX, disY});
                    }
                    break;
                case MotionEvent.ACTION_UP:

            }
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        Server.getInstance().sendKeyboardAction(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
