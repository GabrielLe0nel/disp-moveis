package com.ifsc.app;

import android.content.DialogInterface;
<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
=======
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
>>>>>>> 9a31030 (EAD paint)

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

<<<<<<< HEAD
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button colorButton;
    SimplePaint simplepaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorButton= findViewById(R.id.btnColor);
        simplepaint = findViewById(R.id.simplePaint);

        colorButton.setOnClickListener(view -> {
            new ColorPickerDialog.Builder(this)
                    .setTitle("ColorPicker Dialog")
                    .setPreferenceName("MyColorPickerDialog")
                    .setPositiveButton("confirma",
                            new ColorEnvelopeListener() {
                                @Override
                                public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                    setLayoutColor(envelope);
                                }
                            })
                    .setNegativeButton("cancela",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                    .attachAlphaSlideBar(true) // the default value is true.
                    .attachBrightnessSlideBar(true)  // the default value is true.
                    .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                    .show();
        });
    }
        public void setLayoutColor(ColorEnvelope envelope){
            simplepaint.setColor(envelope.getColor());

        }
}
=======
public class MainActivity extends AppCompatActivity {

    private Button btnColor;
    private Button btnFree, btnRect, btnCircle, btnClear;
    private SimplePaint simplePaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simplePaint = findViewById(R.id.simplePaint);
        btnColor    = findViewById(R.id.btnColor);
        btnFree     = findViewById(R.id.btnFree);
        btnRect     = findViewById(R.id.btnRect);
        btnCircle   = findViewById(R.id.btnCircle);
        btnClear    = findViewById(R.id.btnClear);

        // ── Seletor de Cor ──────────────────────────────────────────────────
        btnColor.setOnClickListener(view ->
                new ColorPickerDialog.Builder(this)
                        .setTitle("Escolha a cor")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton("Confirmar",
                                (ColorEnvelopeListener) (envelope, fromUser) ->
                                        simplePaint.setStrokeColor(envelope.getColor()))
                        .setNegativeButton("Cancelar",
                                (dialogInterface, i) -> dialogInterface.dismiss())
                        .attachAlphaSlideBar(true)
                        .attachBrightnessSlideBar(true)
                        .setBottomSpace(12)
                        .show()
        );

        // ── Formas ──────────────────────────────────────────────────────────
        btnFree.setOnClickListener(v -> {
            simplePaint.setShapeType(SimplePaint.ShapeType.FREE_DRAW);
            highlightSelected(btnFree);
            Toast.makeText(this, "Linha livre", Toast.LENGTH_SHORT).show();
        });

        btnRect.setOnClickListener(v -> {
            simplePaint.setShapeType(SimplePaint.ShapeType.RECTANGLE);
            highlightSelected(btnRect);
            Toast.makeText(this, "Retângulo", Toast.LENGTH_SHORT).show();
        });

        btnCircle.setOnClickListener(v -> {
            simplePaint.setShapeType(SimplePaint.ShapeType.CIRCLE);
            highlightSelected(btnCircle);
            Toast.makeText(this, "Círculo", Toast.LENGTH_SHORT).show();
        });

        // ── Limpar ──────────────────────────────────────────────────────────
        btnClear.setOnClickListener(v -> {
            simplePaint.clearCanvas();
            Toast.makeText(this, "Canvas limpo!", Toast.LENGTH_SHORT).show();
        });

        // Destaca o botão padrão
        highlightSelected(btnFree);
    }

    /** Destaca visualmente o botão de forma selecionado */
    private void highlightSelected(Button selected) {
        int colorNormal   = getResources().getColor(android.R.color.darker_gray, getTheme());
        int colorSelected = getResources().getColor(android.R.color.holo_blue_dark, getTheme());

        for (Button btn : new Button[]{btnFree, btnRect, btnCircle}) {
            btn.setBackgroundColor(colorNormal);
        }
        selected.setBackgroundColor(colorSelected);
    }
}
>>>>>>> 9a31030 (EAD paint)
