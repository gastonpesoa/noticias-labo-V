package com.example.noticias;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

public class NoticiaXmlPaser {

    public static ArrayList<NoticiaModel> parse(String xmlText) {

        XmlPullParser parser = Xml.newPullParser();
        ArrayList<NoticiaModel> noticias = new ArrayList<NoticiaModel>();
        String text = "";
        Boolean itemEsNoticia = false;
        NoticiaModel noticia = null;

        try {
            parser.setInput(new StringReader(xmlText));
            int event = 0;
            event = parser.getEventType();
            //Log.d("XmlPaser TAG NEXT", Integer.valueOf(event).toString());
            while (event != XmlPullParser.END_DOCUMENT)
            {
                String tag = parser.getName();

                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        //Log.d("XmlPaser START_TAG", tag);
                        if (tag.equalsIgnoreCase("item"))
                        {
                            itemEsNoticia = true;
                            noticia = new NoticiaModel();
                            noticia.setFuente("telam");
                            noticia.setFecha(new Date());
                            noticia.setImagen("img");
                            //Log.d("NOTICIA START_TAG item", noticia.getFuente());
                        }
                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        //Log.d("XmlPaser TEXT", text);
                        break;

                    case XmlPullParser.END_TAG:
                        //Log.d("XmlPaser END_TAG", text);
                        if (tag.equalsIgnoreCase("item") && itemEsNoticia) {
                            noticias.add(noticia);
                        } else if (tag.equalsIgnoreCase("title") && itemEsNoticia){
                            noticia.setTitulo(text);
                            noticia.setIdentificador(text);
                            //Log.d("XmlPaser TAG title", text);
                        } else if (tag.equalsIgnoreCase("description")  && itemEsNoticia){
                            noticia.setDescripcion(text);
                            //Log.d("XmlPaser TAG summary", text);
                        } else if (tag.equalsIgnoreCase("pubDate")  && itemEsNoticia){
                            //noticia.setFecha(new Date(text));
                            //Log.d("XmlPaser TAG pubDate", text);
                        }
                        /*if(noticia!=null){
                            Log.d("NOTICIA END_TAG item", noticia.toString());
                        }*/
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return noticias;
    }
}
