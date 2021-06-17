package com.example.noticias;

import android.util.Log;

public class NoticiaThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++){
            Log.d(Integer.valueOf(i).toString(), "Ejecucion desde Thread");
        }
    }
}
