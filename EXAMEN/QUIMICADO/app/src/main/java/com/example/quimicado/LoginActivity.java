package com.example.quimicado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextContraseña;
    private Button btnLogin;
    private ImageView imageViewError;
    private TextView tvMensajeError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_login);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        btnLogin = findViewById(R.id.btnLogin);
        imageViewError = findViewById(R.id.imageViewError);
        tvMensajeError = findViewById(R.id.tvMensajeError);
    }

    public void listenerValidar(View view){
        String usuario = editTextUsuario.getText().toString().trim();
        String contraseña = editTextContraseña.getText().toString().trim();

        if (usuario.equals("admin") && contraseña.equals("admin")) {
            imageViewError.setVisibility(View.GONE);
            tvMensajeError.setVisibility(View.GONE);
            // si los datos son correctos, le mandamos a la actividad de administracion
            Intent i = new Intent(LoginActivity.this, AdministracionActivity.class);
            startActivity(i);
        } else {
            imageViewError.setVisibility(View.VISIBLE);
            tvMensajeError.setVisibility(View.VISIBLE);
        }
    }

    public void listenerVolver(View view){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}