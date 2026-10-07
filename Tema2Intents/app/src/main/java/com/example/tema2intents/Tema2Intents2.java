package com.example.tema2intents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tema2Intents2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tema2_intents2);
    }

    protected void onStart() {
        super.onStart();
        Log.i("Ejemplo 2", "Estoy en on start de la actividad 2");
        Intent ejemplo = new Intent(Intent.ACTION_VIEW);
        ejemplo.setData(Uri.parse("http:www.google.es"));
        startActivity(ejemplo);
    }

}