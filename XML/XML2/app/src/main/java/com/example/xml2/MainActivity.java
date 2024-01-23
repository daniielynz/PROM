package com.example.xml2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvTemperatura;
    private TextView tvEstadoCielo;
    private TextView tvHumedadRelativa;
    private TextView tvDireccionViento;
    private TextView tvVelocidadViento;
    private TextView tvSensacionTermica;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTemperatura = findViewById(R.id.tvTemperatura);
        tvEstadoCielo = findViewById(R.id.tvEstadoCielo);
        tvHumedadRelativa = findViewById(R.id.tvHumedadRelativa);
        tvDireccionViento = findViewById(R.id.tvDireccionViento);
        tvVelocidadViento = findViewById(R.id.tvVelocidadViento);
        tvSensacionTermica = findViewById(R.id.tvSensacionTermica);

        cargarDatos();
    }

    private void cargarDatos() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    AemetXmlParser parser = new AemetXmlParser();
                    List<InformacionMeteorologica> listaInfo = parser.parsearXML("https://www.aemet.es/xml/municipios_h/localidad_h_01059.xml");


                    if (!listaInfo.isEmpty()) {
                        InformacionMeteorologica info = listaInfo.get(0); // Usamos solo el primer elemento

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvTemperatura.setText("Temperatura:\n " + info.getTemperatura() + "°C");
                                tvEstadoCielo.setText("Estado del cielo:\n " + info.getEstadoCielo());
                                tvSensacionTermica.setText("Sensación Térmica:\n " + info.getSensacionTermica() + "°C");
                                tvHumedadRelativa.setText("Humedad Relativa:\n " + info.getHumedadRelativa() + "%");
                                tvDireccionViento.setText("Dirección del Viento:\n " + info.getDireccionViento());
                                tvVelocidadViento.setText("Velocidad del Viento:\n " + info.getVelocidadViento() + " km/h");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });
    }
}

