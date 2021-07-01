package com.example.noticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

public class NoticiaDetalle extends AppCompatActivity implements Handler.Callback {

    NoticiaModel noticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalle);

        Bundle extras = super.getIntent().getExtras();
        noticia = (NoticiaModel) extras.getSerializable("noticia");

        Log.d("NoticiaDetalle", noticia.toString());

        ((TextView)findViewById(R.id.dtvTitulo)).setText(noticia.getTitulo());
        ((TextView)findViewById(R.id.dtvDescripcion)).setText(noticia.getDescripcion());
        ((TextView)findViewById(R.id.dtvFuente)).setText(noticia.getFuente());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ((TextView)findViewById(R.id.dtvFecha)).setText(sdf.format(noticia.getFecha()));
        Handler handlerImg = new Handler(Looper.myLooper(),this);
        ImageWorker w = new ImageWorker(handlerImg, noticia.getImagen(), 0);
        w.start();

        FloatingActionButton fab = findViewById(R.id.share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, noticia.getLink());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Noticias");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        byte[] image = (byte[]) msg.obj;
        ((ImageView)findViewById(R.id.divImagen)).setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        return true;
    }
}