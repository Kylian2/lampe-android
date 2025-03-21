package com.example.lampemagique.controller;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.lampemagique.R;
import com.example.lampemagique.model.Light;
import com.example.lampemagique.view.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LightController implements View.OnClickListener {
    private Handler handler = new Handler(Looper.getMainLooper()); // Handler pour l'UI
    private Light model;
    private MainActivity mainActivity;
    private static int POSITION = 1;

    public LightController(MainActivity activity){
        mainActivity = activity;
        this.model = new Light(100, 100, 100);
    }

    public void callSwitchOn(){
        LightThread lightThread = new LightThread();
        lightThread.start();
        sendToServer();
    }

    public void setState(boolean b) {
        if(b){
            mainActivity.setColor(model.getColor());
            model.setState(true);
            sendToServer();
        }else{
            model.setState(false);
            sendToServer(Color.rgb(100, 100, 100));
        }
    }

    public void setColor(int red, int green, int blue) {
        model.setRed(red);
        model.setGreen(green);
        model.setBlue(blue);
    }

    public void applyColor(){
        mainActivity.setColor(model.getColor());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.plusRed){
            model.setRed(model.getRed() + 5);
            mainActivity.setColor(model.getColor());
            setState(true);
        }
        if(v.getId() == R.id.minusRed){
            model.setRed(model.getRed() - 5);
            mainActivity.setColor(model.getColor());
            setState(true);
        }

        if(v.getId() == R.id.plusGreen){
            model.setGreen(model.getGreen() + 5);
            mainActivity.setColor(model.getColor());
            setState(true);
        }
        if(v.getId() == R.id.minusGreen){
            model.setGreen(model.getGreen() -5);
            mainActivity.setColor(model.getColor());
            setState(true);
        }

        if(v.getId() == R.id.plusBlue){
            model.setBlue(model.getBlue() + 5);
            mainActivity.setColor(model.getColor());
            setState(true);
        }
        if(v.getId() == R.id.minusBlue){
            model.setBlue(model.getBlue() - 5);
            mainActivity.setColor(model.getColor());
            setState(true);
        }
        if(v.getId() == R.id.color1){
            model.setColor(ContextCompat.getColor(mainActivity, R.color.color1));
            mainActivity.setColor(model.getColor());
            setState(true);
        }

        if(v.getId() == R.id.color2){
            model.setColor(ContextCompat.getColor(mainActivity, R.color.color2));
            mainActivity.setColor(model.getColor());
            setState(true);
        }

        if(v.getId() == R.id.color3){
            model.setColor(ContextCompat.getColor(mainActivity, R.color.color3));
            mainActivity.setColor(model.getColor());
            setState(true);
        }
        if(v.getId() == R.id.light){
            Log.d("DEVLOG", model.isOn()+"");
            if(model.isOn()){
                mainActivity.switchOff();
                model.setState(false);
            }else{
                Log.d("DEVLOG", "OFF");
                this.callSwitchOn();
            }
        }
    }

    public void sendToServer(){
        ServerThread server = new ServerThread(model.getColor(), POSITION);
        server.start();
    }

    public void sendToServer(int color){
        ServerThread server = new ServerThread(color, POSITION);
        server.start();
    }

    public int getRed() {
        return model.getRed();
    }

    public int getGreen(){
        return model.getGreen();
    }

    public int getBlue(){
        return model.getBlue();
    }

    private class LightThread extends Thread{
        public void run() {
            try {
                Log.d("DEVLOG", "HELLO, démarrage du thread...");
                int red = 0;
                int green = 0;
                int blue = 0;
                for(int i = 0; i < 2048; i++){
                    Thread.sleep(1);
                    if (i < 256) {  // Bleu montant
                        blue = i;
                        red = 0;
                        green = 0;
                    } else if (i < 512) {  // Vert montant
                        blue = 255;
                        red = 0;
                        green = i - 256;
                    } else if (i < 768) {  // Bleu descendant
                        blue = 255 - (i - 512);
                        red = 0;
                        green = 255;
                    } else if (i < 1024) {  // Rouge montant
                        blue = 0;
                        red = i - 768;
                        green = 255;
                    } else if (i < 1280) {  // Bleu montant
                        blue = i - 1024;
                        red = 255;
                        green = 255;
                    } else if (i < 1536) {  // Vert descendant
                        blue = 255;
                        red = 255;
                        green = 255 - (i - 1280);
                    } else if (i < 1792) {  // Bleu descendant
                        blue = 255 - (i - 1536);
                        red = 255;
                        green = 0;
                    } else if (i < 2048) {  // Rouge descendant
                        blue = 0;
                        red = 255 - (i - 1792);
                        green = 0;
                    }
                    mainActivity.setColor(Color.rgb(red, green, blue));
                    handler.post(() -> {
                        mainActivity.setColor(model.getColor());
                    });
                }
                mainActivity.setColor(model.getColor());
                model.setState(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerThread extends Thread {
        private final int color;
        private final int position;
        private final Handler handler = new Handler(); // Assurez-vous que "handler" est bien défini

        ServerThread(int color, int position) {
            this.color = color;
            this.position = position;
        }

        public String formatString() {
            String red   = String.format("%02x", Color.red(color));
            String green = String.format("%02x", Color.green(color));
            String blue  = String.format("%02x", Color.blue(color));

            String formatedString = String.format("%02d", position) + red + green + blue;

            return formatedString;
        }


        @Override
        public void run() {
            handler.post(() -> Log.d("ServerThread", "Démarrage du thread"));

            try {
                Socket socket = new Socket("chadok.info", 9998);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Envoi de la couleur
                String message = this.formatString();
                Log.d("DEVLOG", "Envoi : " + message);
                writer.println(message);

                try {
                    // Gestion de l'interruption potentielle tant que le serveur n'a pas répondu
                    while (!reader.ready()) {
                        Thread.sleep(500);
                    }
                    // Lecture du résultat
                    String result = reader.readLine();
                    Log.d("DEVLOG", result);
                } catch (InterruptedException e) {
                    Log.e("ServerThread", "Erreur de lecture", e);
                }

                socket.close();
            } catch (IOException e) {
                Log.e("ServerThread", "Erreur de connexion", e);
            } catch (NumberFormatException e) {
                Log.e("ServerThread", "Réponse invalide du serveur", e);
            }
        }
    }
}
