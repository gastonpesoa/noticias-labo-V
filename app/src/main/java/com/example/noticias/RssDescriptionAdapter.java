package com.example.noticias;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RssDescriptionAdapter extends RecyclerView.Adapter<RssDescriptionViewHolder> {

    private MyOnToggleChange listener;
    private List<RssDescriptionModel> lista;
    private RssDescriptionViewHolder holder;

    public RssDescriptionAdapter(List<RssDescriptionModel> lista, MyOnToggleChange listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RssDescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_layout_url_rss, parent, false);
        RssDescriptionViewHolder rssViewHolder = new RssDescriptionViewHolder(v, this.listener);
        return rssViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RssDescriptionViewHolder holder, int position) {
        this.holder = holder;
        RssDescriptionModel rss = lista.get(position);
        holder.setPosition(position);
        holder.textViewFuente.setText(rss.getFuente());
        holder.textViewUrl.setText(rss.getUrl());
        if(rss.getActivo()){
            holder.toggleButtonActivo.setChecked(true);
        } else {
            holder.toggleButtonActivo.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
