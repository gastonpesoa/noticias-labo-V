package com.example.noticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyOnItemClick, Handler.Callback {

    List<NoticiaModel> noticias = new ArrayList<>();
    private NoticiaAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(this);
        NoticiaWorker w = new NoticiaWorker(handler, "https://www.telam.com.ar/rss2/politica.xml");
        w.start();

        this.adapter = new NoticiaAdapter(noticias, this);
        RecyclerView list = (RecyclerView)findViewById(R.id.list);
        list.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClick(int position) {
        Log.d("MainActivity", noticias.get(position).toString());
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        String text = (String) msg.obj;
        //Log.d("MainActivity", text);
        ArrayList<NoticiaModel> noticiasRes = NoticiaXmlPaser.parse(text);

        for (NoticiaModel noticia:noticiasRes){
            Log.d("Noticia", noticia.toString());
            this.noticias.add(noticia);
        }


        this.adapter.notifyDataSetChanged();
        return true;
    }
}