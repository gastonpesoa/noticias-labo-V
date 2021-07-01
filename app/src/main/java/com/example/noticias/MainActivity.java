package com.example.noticias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyOnItemClick, Handler.Callback, SearchView.OnQueryTextListener, DialogInterface.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    JSONArray arrayUrlRss;
    List<NoticiaModel> noticias = new ArrayList<NoticiaModel>();
    List<NoticiaModel> noticiasCopia = new ArrayList<NoticiaModel>();
    List<NoticiaModel> noticiasCopiaParaQuerys = new ArrayList<NoticiaModel>();
    List<RssDescriptionModel> listaRss = new ArrayList<>();
    NoticiaAdapter adapter;
    private Handler handler;
    View dialogViewRssAdd;
    View dialogViewRssSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        this.prefs = getSharedPreferences("miConfig", Context.MODE_PRIVATE);
        this.editor = prefs.edit();
        String strJson = prefs.getString("rssPrefs", "0");
        this.arrayUrlRss = new JSONArray();

        if (strJson == null || "0".equalsIgnoreCase(strJson) || strJson.isEmpty()) {
            Log.d("Prefs not exist", "strJson: " + strJson);
        } else {
            Log.d("Prefs exist","strJson: " + strJson);
            //this.editor.putString("rssPrefs", "");
            //this.editor.commit();
            this.setArrayUrlRss(strJson);
            this.doRssRequests();
        }

        this.adapter = new NoticiaAdapter(noticias, this);
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            return true;
        } else if (id == R.id.rss_add) {
            LayoutInflater li = LayoutInflater.from(this);
            this.dialogViewRssAdd = li.inflate(R.layout.dialog_layout_rss_new, null);
            NoticiaRssDialog dialog = new NoticiaRssDialog(this, this.dialogViewRssAdd);
            dialog.show(getSupportFragmentManager(), "mostrar");
        } else if (id == R.id.rss_select){
            LayoutInflater li = LayoutInflater.from(this);
            this.dialogViewRssSelect = li.inflate(R.layout.dialog_layout_rss_select, null);
            RssSelectListener listener = new RssSelectListener(this.listaRss, this.noticias, this.noticiasCopia, this.noticiasCopiaParaQuerys, this.adapter, this.editor);
            RssSelectDialog dialogRssSelect = new RssSelectDialog(listener, this.dialogViewRssSelect, this.listaRss);
            dialogRssSelect.show(getSupportFragmentManager(), "rss_select");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        NoticiaModel noticiaDetalle = noticias.get(position);
        Log.d("MainActivity", noticiaDetalle.toString());
        Intent intent = new Intent(this, NoticiaDetalle.class);
        intent.putExtra("noticia", noticiaDetalle);
        startActivity(intent);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        String text = (String) msg.obj;
        //Log.d("main", text);
        //Log.d("arg2", EMessages.NOTICIA.toString());
        if(msg.arg2 == EMessages.NOTICIA.getValue()){
            try {
                JSONObject jsonResponse = new JSONObject(text);
                Log.d("volvio", jsonResponse.get("url").toString());
                ArrayList<NoticiaModel> noticiasRes = NoticiaXmlPaser.parse(jsonResponse.get("data").toString());

                this.noticiasCopia.addAll(noticiasRes);

                RssDescriptionModel rssRegistrado = null;

                for (RssDescriptionModel rss : this.listaRss) {
                    if (rss.getUrl().equalsIgnoreCase(jsonResponse.get("url").toString())){
                        rssRegistrado = rss;
                        break;
                    }
                }

                if (rssRegistrado == null){
                    Log.d("Handler","nuevo rss: " + jsonResponse.get("url").toString());
                    RssDescriptionModel rssDescription = new RssDescriptionModel();
                    rssDescription.setFuente(noticiasRes.get(0).getFuente());
                    rssDescription.setUrl(jsonResponse.get("url").toString());
                    rssDescription.setActivo(true);
                    this.listaRss.add(rssDescription);

                    JSONObject nuevoRss = new JSONObject();
                    nuevoRss.put("url", rssDescription.getUrl());
                    nuevoRss.put("fuente", rssDescription.getFuente());
                    nuevoRss.put("activo", rssDescription.getActivo().toString());

                    this.arrayUrlRss.put(nuevoRss);
                    String jsonStr = this.arrayUrlRss.toString();
                    this.editor.putString("rssPrefs", jsonStr);
                    this.editor.commit();

                    this.noticias.addAll(noticiasRes);
                    this.noticiasCopiaParaQuerys.addAll(noticiasRes);
                } else if(rssRegistrado != null && rssRegistrado.getActivo()){
                    this.noticias.addAll(noticiasRes);
                    this.noticiasCopiaParaQuerys.addAll(noticiasRes);
                }

                this.adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty() && newText.length() > 3) {
            Log.d("activity", "Hago una busqueda con:" + newText);
            List<NoticiaModel> noticiasFiltradas = new ArrayList<NoticiaModel>();
            for (NoticiaModel noticia : noticiasCopiaParaQuerys) {
                Log.d("query ",noticia.toString());
                if (noticia.getTitulo() != null && !noticia.getTitulo().isEmpty()) {
                    if (noticia.getTitulo().toLowerCase().contains(newText.toLowerCase())){
                        noticiasFiltradas.add(noticia);
                    }
                }
                if(noticia.getDescripcion() != null && !noticia.getDescripcion().isEmpty()){
                    if (noticia.getDescripcion().toLowerCase().contains(newText.toLowerCase())){
                        noticiasFiltradas.add(noticia);
                    }
                }
            }
            this.noticias.clear();
            this.noticias.addAll(noticiasFiltradas);
        }
        else {
            this.noticias.clear();
            this.noticias.addAll(noticiasCopiaParaQuerys);
        }
        this.adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == AlertDialog.BUTTON_POSITIVE) {
            EditText eTRss = (EditText) this.dialogViewRssAdd.findViewById(R.id.eTRss);
            String newUrlRss = eTRss.getText().toString().trim();
            Log.d("dialog rss", newUrlRss);
            if (newUrlRss != null && !newUrlRss.isEmpty() && URLUtil.isValidUrl(newUrlRss)){
                handler = new Handler(this);
                NoticiaWorker w = new NoticiaWorker(handler, newUrlRss);
                w.start();
            } else {
                Toast.makeText(this.getApplicationContext(),"La URL ingresada es inválida", Toast.LENGTH_LONG).show();
            }
        }  else if (which == AlertDialog.BUTTON_NEGATIVE){
            Log.d("dialog", "Cancelar!");
        }
    }

    private void setArrayUrlRss(String strJson){
        try {
            this.arrayUrlRss = new JSONArray(strJson);
            for (int i = 0; i < this.arrayUrlRss.length(); i++) {
                try {
                    JSONObject jsonObject = this.arrayUrlRss.getJSONObject(i);
                    String url = jsonObject.get("url").toString();
                    String fuente = jsonObject.get("fuente").toString();
                    boolean activo = "true".equalsIgnoreCase(jsonObject.get("activo").toString()) ? true : false;
                    RssDescriptionModel rss = new RssDescriptionModel(url, fuente, activo);
                    this.listaRss.add(rss);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doRssRequests(){
        for (RssDescriptionModel rss : this.listaRss) {
            Handler handler = new Handler(this);
            NoticiaWorker w = new NoticiaWorker(handler, rss.getUrl());
            w.start();
        }
    }

    @Override
    public void onRefresh() {
        this.noticias.clear();
        this.noticiasCopiaParaQuerys.clear();
        this.noticiasCopia.clear();
        this.doRssRequests();
    }
}