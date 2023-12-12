package com.example.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    static final String CANAL_ID ="mi_canal";
    static final int NOTIFICACION_ID = 1;
    private int cont, n1, n2;
    private EditText editText1 ,editText2, editText3;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = 0;

        editText1 = findViewById(R.id.etNum1);
        editText2 = findViewById(R.id.etNum2);
        editText3 = findViewById(R.id.etResultado);
        tvResult = findViewById(R.id.tvMensaje);

        generar();
    }
    private void generar(){
        n1 = (int)(Math.random() * (50 + 1));
        n2 = (int)(Math.random() * (50 + 1));

        editText3.setText("");
        editText1.setText(n1+"");
        editText2.setText(n2+"");
    }

    public void comprobarResultado(View v){
        String mensaje;
        if(editText3.getText()!=null && !editText3.getText().equals("")){
            int result = Integer.parseInt(editText3.getText().toString());

            if(result == (n1+n2)){
                cont ++;
                mensaje = "CORRECTO\n "+cont+"/10";
                tvResult.setTextColor(Color.GREEN);
            }else{
                mensaje = "INCORRECTO\n "+cont+"/10";
                tvResult.setTextColor(Color.RED);
            }
            generar();
        }else{
            tvResult.setTextColor(Color.RED);
            mensaje = "Tienes que rellenar el campo del resultado";
        }

        tvResult.setText(mensaje);

        if(cont == 11){
            notificacion();
        }
    }

    private void notificacion(){
        //Creamos notificacion
        notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripción del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion =
                new NotificationCompat.Builder(MainActivity.this,CANAL_ID)
                        .setSmallIcon(R.drawable.outline_android_black_24dp)
                        .setContentTitle("Has logrado 10 aciertos")
                        .setContentText("Enhorabuena!!");
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
    }
}