package com.example.noticias;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

public class NoticiaWorker implements Runnable {
    private String url;
    private Handler h;
    private NoticiaHttpManager httpManager;

    public NoticiaWorker() {
    }

    public NoticiaWorker(Handler h, String url) {
        this.h = h;
        this.url = url;
    }

    public void start(){
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        httpManager = new NoticiaHttpManager(this.url);
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
