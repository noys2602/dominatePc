package com.noy.dominatepc.server;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 */

public class Server {


    public static final int SERVER_PORT = 8820;
    public static final String SERVER_HOST = "10.0.0.9";

    private static Server instance;

    private Socket socket;
    private PrintWriter out;

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }

    private Server() {
    }

    public boolean connect(String ip) {
        try {
            socket = new Socket(InetAddress.getByName(ip), SERVER_PORT);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                    .getOutputStream())), true); //create output stream to send data to server

            CommunicationThread commThread = new CommunicationThread(socket);
            new Thread(commThread).start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();
                    Log.d("Server", "GOT: " + read);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendKeyboardAction(String data) {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("text", data);
            String keyboardJson = createJson(ServerActions.ACTION_KEYBOARD, jsonData);
            if (isConnected() && out != null && keyboardJson != null) {
                sendToServer(keyboardJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendMouseAction(float[] data) {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("dx", data[0]);
            jsonData.put("dy", data[1]);
            String mouseJson = createJson(ServerActions.ACTION_MOUSE_MOVE, jsonData);
            if (isConnected() && out != null && mouseJson != null) {
                sendToServer(mouseJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendMouseLeftClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MOUSE_LEFT_CLICK, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMouseRightClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MOUSE_RIGHT_CLICK, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaPlayClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_PLAY, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaStopClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_STOP, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaVolumeUpClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_VOLUME_UP, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaVolumeDownClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_VOLUME_DOWN, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaMuteClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_MUTE, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaRewindClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_REWIND, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendMediaForwardClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_MEDIA_FORWARD, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendPowerRestartClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_POWER_RESTART, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendPowerShutdownClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_POWER_SHUTDOWN, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendPowerSleepClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_POWER_SLEEP, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendPowerLogoutClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_POWER_LOGOUT, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    public void sendScreenshotTakeClickAction() {
        String mouseJson = createJson(ServerActions.ACTION_SCREENHSOT_TAKE, null);
        if (isConnected() && out != null && mouseJson != null) {
            sendToServer(mouseJson);
        }
    }

    protected String createJson(String action, JSONObject data) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", action);
            if (data != null) {
                jsonObject.put("data", data);
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void sendToServer(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                out.println(data);
            }
        }).start();
    }

}
