package com.example.noticias;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class NoticiaModel implements Serializable {

    private String link;
    private String titulo;
    private String descripcion;
    private String fuente;
    private String imagen;
    private Date fecha;
    private byte[] img;

    public NoticiaModel() {
    }

    public NoticiaModel(String link, String titulo, String descripcion, String fuente, String imagen, Date fecha) {
        this.link = link;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticiaModel that = (NoticiaModel) o;
        return Objects.equals(link, that.link) &&
                Objects.equals(titulo, that.titulo) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(fuente, that.fuente) &&
                Objects.equals(imagen, that.imagen) &&
                Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, titulo, descripcion, fuente, imagen, fecha);
    }

    @Override
    public String toString() {
        return "NoticiaModel{" +
                "identificador=" + link +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fuente='" + fuente + '\'' +
                ", imagen='" + imagen + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
