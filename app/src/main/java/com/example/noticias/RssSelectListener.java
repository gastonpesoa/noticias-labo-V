package com.example.noticias;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RssSelectListener implements DialogInterface.OnClickListener {

    private List<NoticiaModel> noticiasQuerys;
    private SharedPreferences.Editor editor;
    private List<NoticiaModel> noticiasCopia;
    private List<NoticiaModel> noticias;
    private NoticiaAdapter adapter;
    List<RssDescriptionModel> rsses;

    public RssSelectListener(List<RssDescriptionModel> listaRss, List<NoticiaModel> noticias, List<NoticiaModel> noticiasCopia, List<NoticiaModel> noticiasQuerys, NoticiaAdapter adapter, SharedPreferences.Editor editor) {
        this.rsses = listaRss;
        this.noticias = noticias;
        this.noticiasCopia = noticiasCopia;
        this.noticiasQuerys = noticiasQuerys;
        this.adapter = adapter;
        this.editor = editor;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("rssDialogListener", "Aceptar!");
        JSONArray jsonArrayRss = new JSONArray();
        List<NoticiaModel> noticiasFiltradas = new ArrayList<>();
        for (RssDescriptionModel rss: this.rsses) {
            Log.d("rss", rss.toString());
            if (rss.getActivo()){
                for (NoticiaModel noticia: this.noticiasCopia) {
                    if (noticia.getFuente().equalsIgnoreCase(rss.getFuente())){
                       noticiasFiltradas.add(noticia);
                    }
                }
            }
            JSONObject jsonRss = new JSONObject();
            try {
                jsonRss.put("url", rss.getUrl());
                jsonRss.put("fuente", rss.getFuente());
                jsonRss.put("activo", rss.getActivo().toString());
                jsonArrayRss.put(jsonRss);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.editor.putString("rssPrefs", jsonArrayRss.toString());
        this.editor.commit();
        this.noticias.clear();
        this.noticias.addAll(noticiasFiltradas);
        this.noticiasQuerys.clear();
        this.noticiasQuerys.addAll(noticiasFiltradas);
        this.adapter.notifyDataSetChanged();
    }
}
