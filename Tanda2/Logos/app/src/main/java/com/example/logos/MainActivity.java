package com.example.logos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void botonImagenYahoo (View v){
        ImageView img = (ImageView)findViewById(R.id.imgFoto);
        img.setImageResource(R.drawable.yahoo);
    }

    public void botonImagenBing (View v){
        ImageView img = (ImageView)findViewById(R.id.imgFoto);
        img.setImageResource(R.drawable.bing);
    }

    public void botonImagenGoogle (View v){
        ImageView img = (ImageView)findViewById(R.id.imgFoto);
        img.setImageResource(R.drawable.google);
    }
}