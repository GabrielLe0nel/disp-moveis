package com.ifsc.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int contador=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.button);
        TextView textview = findViewById(R.id.textView);
        EditText edmin,edmax;
        edmin = findViewById((R.id.edMin));
        edmax = findViewById((R.id.edMax));

        textview.setText("");

        button.setOnClickListener(view -> {
            //contador++;
            // contador = (int)(Math.random() * ((max - min) + 1)) + min;
            //textview.setText(Integer.toString(contador));

            String smin = edmin.getText().toString();
            String smax = edmax.getText().toString();

            if(smin.isEmpty()){
                edmin.setError("Informe um inteiro");
                return;
            }

            if(smax.isEmpty()){
                edmax.setError("Informe um inteiro");
                return;
            }

            int min = Integer.parseInt(smin);
            int max = Integer.parseInt(smax);

            if(min > max){
                Toast.makeText(this, "Defina mínimo menor que máximo", Toast.LENGTH_SHORT).show();
                return;
            }

            Random random = new Random();
            int randomN = random.nextInt(min, max);
            textview.setText(Integer.toString((randomN)));


        });

    }
}