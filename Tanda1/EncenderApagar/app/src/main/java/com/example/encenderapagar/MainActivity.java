package com.example.encenderapagar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnFondoAzul (View v){
        LinearLayout caja = findViewById(R.id.layoutPrincipal);
        caja.setBackgroundColor(Color.BLUE);
    }

    public void btnFondoRojo (View v){
        LinearLayout caja = findViewById(R.id.layoutPrincipal);
        caja.setBackgroundColor(Color.RED);
    }
}