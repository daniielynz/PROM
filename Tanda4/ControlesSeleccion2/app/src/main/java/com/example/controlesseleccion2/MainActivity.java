package com.example.controlesseleccion2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        List<WebFavorita> webFavoritas = new ArrayList<>();
        webFavoritas.add(new WebFavorita("Futbin", "https://www.futbin.com", R.drawable.futbin));
        webFavoritas.add(new WebFavorita("Marca", "https://www.marca.com", R.drawable.marca));
        webFavoritas.add(new WebFavorita("Twitter", "https://www.twitter.com", R.drawable.twitter));

        ArrayAdapter<WebFavorita> adapter = new ArrayAdapter<WebFavorita>(this, R.layout.list_item, webFavoritas) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
                }

                WebFavorita webFavorita = getItem(position);

                ImageView imageView = convertView.findViewById(R.id.imageView);
                TextView textViewName = convertView.findViewById(R.id.textViewName);
                TextView textViewUrl = convertView.findViewById(R.id.textViewUrl);

                imageView.setImageResource(webFavorita.getImagenResource());
                textViewName.setText(webFavorita.getNombre());
                textViewUrl.setText(webFavorita.getUrl());

                return convertView;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebFavorita selectedWeb = webFavoritas.get(position);
                String url = selectedWeb.getUrl();

                // Abrir la URL en un navegador web
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}

