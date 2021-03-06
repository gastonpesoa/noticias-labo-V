package com.example.noticias;

import android.text.Html;
import android.util.Log;
import android.util.Xml;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoticiaXmlPaser {

    public static ArrayList<NoticiaModel> parse(String xmlText) {

        XmlPullParser parser = Xml.newPullParser();
        ArrayList<NoticiaModel> noticias = new ArrayList<NoticiaModel>();
        String text = "";
        String tag = "";
        String fuente = "";
        Boolean itemEsNoticia = false;
        NoticiaModel noticia = null;

        try {
            parser.setInput(new StringReader(xmlText));
            int event = 0;
            event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        //Log.d("XmlPaser TAG", tag);
                        if (tag.equalsIgnoreCase("title") && !itemEsNoticia) {
                            fuente = parser.nextText();
                            //Log.d("XmlPaser TEXT no item", fuente);
                        } else if (tag.equalsIgnoreCase("item")) {
                            //Log.d("XmlPaser ITEM", "nuevo item");
                            noticia = new NoticiaModel();
                            noticia.setFuente(fuente.trim());
                            noticias.add(noticia);
                            itemEsNoticia = true;
                        } else if (tag.equalsIgnoreCase("title") && itemEsNoticia) {
                            text = parser.nextText();
                            noticia.setTitulo(text.trim());
                            //noticia.setIdentificador(text);
                            //Log.d("XmlPaser TEXT title", text);
                        } else if (tag.equalsIgnoreCase("description") && itemEsNoticia) {
                            text = parser.nextText();
                            noticia.setDescripcion(formatString(text));
                            //Log.d("XmlPaser TEXT descript", text);
                        } else if (tag.equalsIgnoreCase("pubDate") && itemEsNoticia) {
                            text = parser.nextText();
                            SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                            noticia.setFecha(format.parse(text.trim()));
                            //Log.d("XmlPaser TEXT pubDate", text);
                        } else if (tag.equalsIgnoreCase("enclosure") && itemEsNoticia) {
                            String att = parser.getAttributeValue(null, "url");
                            //Log.d("XmlPaser TEXT att", att);
                            noticia.setImagen(att.trim());
                        } else if (tag.equalsIgnoreCase("link") && itemEsNoticia) {
                            text = parser.nextText();
                            noticia.setLink(text.trim());
                        }
                        break;
                }
                event = parser.next();
                //Log.d("XmlPaser event", Integer.valueOf(event).toString());
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return noticias;
    }

    private static String formatString(String text) {
        return Html.fromHtml(text
                .trim()
                .replaceAll("&amp;", "")
                .replaceAll("ldquo;", "")
                .replaceAll("rdquo;", ""))
                .toString();
    }
}
