package com.example.ejerciciofragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FragmentListado extends Fragment {
    private Pelicula[] datos = new Pelicula[] {
            new Pelicula ("Los Vengadores: EndGame", "Anthony & Joe Russo", "Después de los eventos devastadores de \"Avengers: Infinity War\", el universo está en ruinas debido a las acciones de Thanos, el Titán Loco. Con la ayuda de los aliados que quedaron, los Vengadores deberán reunirse una vez más para intentar detenerlo y restaurar el orden en el universo de una vez por todas."),
            new Pelicula ("El Padrino", "Francis Ford Coppola", "Esta película es un retrato épico de la vida de la mafia en América a través de los ojos de la poderosa familia Corleone. Don Vito Corleone, el respetado y temido patriarca, busca asegurar su legado, mientras que su hijo más joven, Michael, se ve arrastrado al mundo del crimen organizado."),
            new Pelicula ("La La Land", "Damien Chazelle", "Ambientada en Los Ángeles, esta película musical sigue la historia de amor entre Mia, una aspirante a actriz, y Sebastian, un dedicado jazzista. Ambos luchan por mantener su relación mientras persiguen sus sueños en una ciudad conocida por destruir esperanzas y romper corazones."),
            new Pelicula ("Inception", "Christopher Nolan", "En un mundo donde la tecnología permite entrar en la mente humana a través de los sueños, un ladrón habilidoso tiene la tarea de implantar una idea en la mente de un CEO. Sin embargo, su habilidad en el arte del \"inception\" lo lleva a enfrentarse a su propio subconsciente y a peligros inesperados."),
            new Pelicula ("Pulp Fiction", "Quentin Tarantino", "Esta película es un entrelazado de historias de crimen y redención en Los Ángeles, que sigue a un grupo de personajes coloridos: dos asesinos a sueldo, un boxeador, la esposa de un gángster y dos bandidos. \"Pulp Fiction\" es conocida por su diálogo ingenioso y su narrativa no lineal."),
            new Pelicula ("Forrest Gump", "Robert Zemeckis", "La película cuenta la historia de Forrest Gump, un hombre con un coeficiente intelectual por debajo de la media, pero con un corazón de oro. A través de una serie de eventos fortuitos, Forrest se encuentra en medio de varios momentos históricos clave, pero su verdadero amor, Jenny, es lo que más influye en su vida."),
            new Pelicula ("Gladiator", "Ridley Scott", "El general romano Máximo es el soporte más leal del emperador Marco Aurelio, que lo ha conducido de victoria en victoria. Sin embargo, Cómodo, el hijo de Marco Aurelio, está celoso del prestigio de Máximo y aún más del amor que su padre siente por él.")
    };
    private ListView lstListado;
    private PeliculaListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstListado = (ListView)getView().findViewById(R.id.lstListado);
        lstListado.setAdapter(new AdaptadorPeliculas(this));
        lstListado.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (listener != null)
                    listener.onPeliculaSeleccionada(
                            (Pelicula)lstListado.getAdapter().getItem(position));
            }
        });
    }

    public interface PeliculaListener{
        void onPeliculaSeleccionada(Pelicula p);
    }
    public void setPeliculaListener (PeliculaListener listener){
        this.listener =listener;
    }

    class AdaptadorPeliculas extends ArrayAdapter<Pelicula> {
        Activity context;
        AdaptadorPeliculas(Fragment context) {
            super(context.getActivity(), R.layout.listitem_pelicula, datos);
            this.context = context.getActivity();
        }

        @NonNull
        @Override
        public View getView(int position,
                            @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_pelicula, null);
            TextView lblTitulo = (TextView) item.findViewById(R.id.lblTitulo);
            lblTitulo.setText(datos[position].getTitulo());
            TextView lblDirector = (TextView)item.findViewById(R.id.lblDirector);
            lblDirector.setText(datos[position].getDirector());
            return (item);
        }
    }
}