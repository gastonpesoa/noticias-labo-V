package com.example.noticias;

import java.util.Objects;

public class RssDescriptionModel {
    private String url;
    private String fuente;
    private Boolean activo;

    public RssDescriptionModel() {
    }

    public RssDescriptionModel(String url, String fuente, Boolean activo) {
        this.url = url;
        this.fuente = fuente;
        this.activo = activo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RssDescriptionModel rssModel = (RssDescriptionModel) o;
        return url.equals(rssModel.url) &&
                Objects.equals(fuente, rssModel.fuente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "RssModel{" +
                "url='" + url + '\'' +
                ", fuente='" + fuente + '\'' +
                ", activo=" + activo +
                '}';
    }
}
