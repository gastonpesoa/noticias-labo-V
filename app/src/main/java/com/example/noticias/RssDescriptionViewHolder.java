package com.example.noticias;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RssDescriptionViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
    private MyOnToggleChange listener;
    TextView textViewUrl;
    TextView textViewFuente;
    ToggleButton toggleButtonActivo;
    private int position;

    public RssDescriptionViewHolder(@NonNull View itemView, MyOnToggleChange listener) {
        super(itemView);
        textViewUrl = itemView.findViewById(R.id.txtUrl);
        textViewFuente = itemView.findViewById(R.id.txtFuente);
        toggleButtonActivo = itemView.findViewById(R.id.tbActivo);
        toggleButtonActivo.setOnCheckedChangeListener(this);
        this.listener = listener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.listener.onToggleChange(this.position, isChecked);
    }
}
