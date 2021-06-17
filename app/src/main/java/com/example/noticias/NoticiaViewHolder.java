package com.example.noticias;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private MyOnItemClick listener;
    TextView tvIdentificador;
    TextView tvFecha;
    TextView tvFuente;
    TextView tvImagen;
    TextView tvTitulo;
    TextView tvDescripcion;
    private int position;

    public NoticiaViewHolder(@NonNull View itemView, MyOnItemClick listener) {
        super(itemView);
        tvIdentificador = itemView.findViewById(R.id.tvIdentificador);
        tvFecha = itemView.findViewById(R.id.tvFecha);
        tvFuente = itemView.findViewById(R.id.tvFuente);
        tvImagen = itemView.findViewById(R.id.tvImagen);
        tvTitulo = itemView.findViewById(R.id.tvTitulo);
        tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(position);
    }
}
