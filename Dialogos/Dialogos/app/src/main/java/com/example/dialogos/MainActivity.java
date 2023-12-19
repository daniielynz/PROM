package com.example.dialogos;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogoPersonalizado dialogoAlerta = new DialogoPersonalizado();
        dialogoAlerta.show(fragmentManager, "tagAlerta");
    }
}
