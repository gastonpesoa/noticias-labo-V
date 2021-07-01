package com.example.noticias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NoticiaRssDialog extends DialogFragment {
    DialogInterface.OnClickListener listener;
    View view;

    public NoticiaRssDialog(DialogInterface.OnClickListener lister, View view) {
        this.listener = lister;
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Configurar RSS");
        builder.setMessage("Ingrese la url para agregar un RSS");
        builder.setPositiveButton("Aceptar", this.listener);
        builder.setNegativeButton("Cancelar", this.listener);
        builder.setView(this.view);
        AlertDialog ad = builder.create();
        return ad;
    }
}
