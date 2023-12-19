package com.example.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
public class DialogoConfirmacion extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Seguro que quieres cerrar la aplicacion?")
                        .setTitle("Confirmación")
                        .setPositiveButton("ACEPTAR",
                                new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.i("Dialogos", "Confirmación Aceptada");
                                            Toast.makeText(getActivity(), "Hasta Pronto!!", Toast.LENGTH_LONG).show();

                                            // Cierra la actividad actual y, por lo tanto, la aplicación
                                            getActivity().finish();
                                        }
                                })
                        .setNegativeButton("CANCELAR",
                                new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                Log.i("Dialogos", "Confirmación denegada");
                                                Toast.makeText(getActivity(),
                                                        "Botón Negativo pulsado", Toast.LENGTH_LONG).show();
                                        }
                                });
                return builder.create();
        }
}