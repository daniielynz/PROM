package com.example.xml2;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AemetXmlParser {

    public List<InformacionMeteorologica> parsearXML(String url) throws Exception {
        // Crear un nuevo analizador XML
        XmlPullParser parser = Xml.newPullParser();
        // Deshabilitar el procesamiento de espacios de nombres
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

        // Utilizar un bloque try-with-resources para cerrar automáticamente el InputStream
        try (InputStream inputStream = new URL(url).openStream()) {
            // Configurar el analizador con la entrada XML
            parser.setInput(inputStream, null);
            // Iniciar el proceso de parsing y obtener la lista de información meteorológica
            return procesarParsing(parser);
        }
    }

    // Método para procesar el parsing del XML y construir la lista de información meteorológica
    private List<InformacionMeteorologica> procesarParsing(XmlPullParser parser) throws Exception {
        List<InformacionMeteorologica> informacionDias = new ArrayList<>();
        int eventType = parser.getEventType();
        boolean esPrimerDia = true;

        // Iterar sobre los eventos del analizador XML
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    // Obtener el nombre de la etiqueta de inicio
                    String nombreEtiqueta = parser.getName();

                    // Si es el primer "dia" encontrado, parsear la información del día y agregar a la lista
                    if (esPrimerDia && "dia".equals(nombreEtiqueta)) {
                        InformacionMeteorologica infoDia = parsearDia(parser);
                        informacionDias.add(infoDia);
                        esPrimerDia = false;
                    }
                    break;
            }

            eventType = parser.next();
        }

        return informacionDias;
    }

    private InformacionMeteorologica parsearDia(XmlPullParser parser) throws Exception {
        String estadoCielo = "";
        String temperatura = "";
        String humedadRelativa = "";
        String direccionViento = "";
        String velocidadViento = "";
        String sensacionTermica = "";
        int eventType = parser.getEventType();

        // Iterar sobre los eventos del analizador XML para el día actual
        while (eventType != XmlPullParser.END_TAG || !"dia".equals(parser.getName())) {
            String nombreEtiqueta = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    // Obtener el nombre de la etiqueta de inicio
                    nombreEtiqueta = parser.getName();

                    // Según la etiqueta, obtener los datos correspondientes
                    if ("estado_cielo".equals(nombreEtiqueta)) {
                        estadoCielo = parser.getAttributeValue(null, "descripcion");
                    } else if ("temperatura".equals(nombreEtiqueta)) {
                        temperatura = parser.nextText();
                    } else if ("humedad_relativa".equals(nombreEtiqueta)) {
                        humedadRelativa = parser.nextText();
                    } else if ("viento".equals(nombreEtiqueta)) {
                        direccionViento = obtenerTextoHijo(parser, "direccion");
                        velocidadViento = obtenerTextoHijo(parser, "velocidad");
                    } else if ("sens_termica".equals(nombreEtiqueta)) {
                        sensacionTermica = parser.nextText();
                    }
                    break;
            }

            // Ir al siguiente evento en el analizador XML
            eventType = parser.next();
        }

        // Construir y devolver el objeto InformacionMeteorologica con los datos parseados
        return new InformacionMeteorologica(temperatura, estadoCielo, humedadRelativa, direccionViento, velocidadViento, sensacionTermica);
    }

    // Método para obtener el texto de un elemento hijo específico
    private String obtenerTextoHijo(XmlPullParser parser, String nombreEtiquetaHijo) throws Exception {
        // Encontrar la etiqueta del hijo
        while (parser.next() != XmlPullParser.START_TAG || !nombreEtiquetaHijo.equals(parser.getName())) {}
        // Obtener el texto del hijo
        String texto = parser.nextText();
        // Ir al siguiente evento (para asegurar que estemos en la etiqueta de cierre del hijo)
        parser.nextTag();
        // Devolver el texto obtenido
        return texto;
    }
}
