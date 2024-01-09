package com.example.ficheros_ejercicio3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class WebAdapter extends ArrayAdapter<Web> {

    public WebAdapter(Context context, List<Web> webItems) {
        super(context, 0, webItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Web webItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_web, parent, false);
        }

        TextView nombreTextView = convertView.findViewById(R.id.textViewNombre);
        TextView enlaceTextView = convertView.findViewById(R.id.textViewEnlace);
        ImageView logoImageView = convertView.findViewById(R.id.imageViewLogo);

        if (webItem != null) {
            nombreTextView.setText(webItem.getNombre());
            enlaceTextView.setText(webItem.getEnlace());

            // Obtener el ID de la imagen desde el nombre
            int imageResourceId = getContext().getResources().getIdentifier(webItem.getLogo(), "drawable", getContext().getPackageName());

            if (imageResourceId != 0) {
                logoImageView.setImageResource(imageResourceId);
            } else {
                Log.e("WebAdapter", "No se encontró la imagen con el nombre: " + webItem.getLogo());
            }
        }

        return convertView;
    }

}
