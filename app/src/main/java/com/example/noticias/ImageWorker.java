package com.example.noticias;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public class ImageWorker implements Runnable {

    private int position;
    private String url;
    private Handler h;
    private NoticiaHttpManager httpManager;

    public ImageWorker() {
    }

    public ImageWorker(Handler h, String url, int position) {
        this.h = h;
        this.url = url;
        this.position = position;
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
                sendMsg(httpManager.getBytesDataByGET());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg(byte[] msg){
        Message message = new Message();
        message.obj = msg;
        message.arg1 = position;
        h.sendMessage(message);
    }
}
