package com.example.noticias;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaViewHolder> implements Handler.Callback{

    private MyOnItemClick listener;
    private List<NoticiaModel> lista;
    private Handler handlerImg;
    private NoticiaViewHolder holder;

    public NoticiaAdapter() {
    }

    public NoticiaAdapter(List<NoticiaModel> lista, MyOnItemClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        NoticiaViewHolder noticiaViewHolder = new NoticiaViewHolder(v, listener);
        return noticiaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {

        this.holder = holder;
        NoticiaModel noticia = lista.get(position);
        handlerImg = new Handler(Looper.myLooper(),this);

        if (noticia.getImg()==null){
            ImageWorker w = new ImageWorker(handlerImg, noticia.getImagen(), position);
            w.start();
        } else {
            holder.ivImagen.setImageBitmap(BitmapFactory.decodeByteArray(noticia.getImg(),0,noticia.getImg().length));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.tvFecha.setText(sdf.format(noticia.getFecha()));

        holder.tvIdentificador.setText(noticia.getLink());
        holder.tvFuente.setText(noticia.getFuente());
        holder.tvTitulo.setText(noticia.getTitulo());
        holder.tvDescripcion.setText(noticia.getDescripcion());
        //holder.tvImagen.setText(noticia.getImagen());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        byte[] image = (byte[]) msg.obj;
        NoticiaModel noticia = lista.get(msg.arg1);
        noticia.setImg(image);
        holder.ivImagen.setImageBitmap(BitmapFactory.decodeByteArray(noticia.getImg(), 0, noticia.getImg().length));
        this.notifyDataSetChanged();
        return true;
    }
}
