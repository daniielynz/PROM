package com.example.xml1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String url = "https://www.europapress.es/rss/rss.aspx";
    private TextView txtResultado;
    private List<Noticia> noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResultado = (TextView)findViewById(R.id.txtResultado);
        //Carga del XML mediante tarea Asincrona
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute(url);
    }
    //Tarea Asíncrona para cargar un XML en segundo plano
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {
        protected Boolean doInBackground(String... params) {
            RssParserDOM domParser = new RssParserDOM(params[0]);
            noticias = domParser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            txtResultado.setText("");
            if (noticias != null) {
                for (int i = 0; i < noticias.size(); i++) {
                    txtResultado.setText(txtResultado.getText().toString() + noticias.get(i).getTitulo()+"\n"+noticias.get(i).getEnlace()+"\n\n\n");
                }
            }
        }
    }
}

