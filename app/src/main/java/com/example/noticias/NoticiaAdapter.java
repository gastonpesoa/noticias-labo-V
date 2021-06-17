package com.example.noticias;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaViewHolder> {

    private MyOnItemClick listener;
    private List<NoticiaModel> lista;

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
        NoticiaModel noticia = lista.get(position);
        holder.tvIdentificador.setText(noticia.getIdentificador());
        holder.tvFecha.setText(noticia.getFecha().toString());
        holder.tvFuente.setText(noticia.getFuente());
        holder.tvTitulo.setText(noticia.getTitulo());
        holder.tvDescripcion.setText(noticia.getDescripcion());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
