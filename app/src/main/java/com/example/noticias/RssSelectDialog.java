package com.example.noticias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RssSelectDialog extends DialogFragment implements MyOnToggleChange {
    List<RssDescriptionModel> lista;
    DialogInterface.OnClickListener listener;
    View view;

    public RssSelectDialog(DialogInterface.OnClickListener listener, View view, List<RssDescriptionModel> lista) {
        this.listener = listener;
        this.view = view;
        this.lista = lista;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Configurar RSS");
        builder.setMessage("Seleccione los RSS que desea habilitar");
        builder.setPositiveButton("Guardar", this.listener);

        builder.setView(this.view);

        RecyclerView rv = (RecyclerView)this.view.findViewById(R.id.lista_rss);
        LinearLayoutManager layoutManager = new LinearLayoutManager(builder.getContext());
        rv.setLayoutManager(layoutManager);

        RssDescriptionAdapter adapter = new RssDescriptionAdapter(this.lista, this);
        rv.setAdapter(adapter);

        AlertDialog ad = builder.create();
        return ad;
    }

    @Override
    public void onToggleChange(int position, boolean isChecked) {
        String isCheck = isChecked ? "chequeado" : "no chequeado";
        for (RssDescriptionModel rss: this.lista) {
            Log.d("rsses antes", rss.toString());
        }
        Log.d("dialog rss", isCheck);
        Log.d("en la posicion", "" + position);
        RssDescriptionModel rss = this.lista.get(position);
        rss.setActivo(isChecked);
    }
}
