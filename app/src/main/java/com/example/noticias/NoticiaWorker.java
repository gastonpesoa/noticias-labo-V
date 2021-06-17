package com.example.noticias;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

public class NoticiaWorker implements Runnable {
    private Handler h;
    private NoticiaHttpManager httpManager;

    public NoticiaWorker() {
    }

    public NoticiaWorker(Handler h) {
        this.h = h;
    }

    @Override
    public void run() {
        httpManager = new NoticiaHttpManager("https://www.telam.com.ar/rss2/politica.xml");
        if (httpManager.isReady()){
            try {
                sendMsg(httpManager.getStrDataByGET());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg(String msg){
        Message message = new Message();
        message.obj = msg;
        h.sendMessage(message);
    }
}
