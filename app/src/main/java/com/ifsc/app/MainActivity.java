
package com.ifsc.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class MainActivity extends AppCompatActivity {

    private Button btnColor;
    private Button btnFree, btnRect, btnCircle, btnClear;
    private SimplePaint simplePaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Localizar componentes
        simplePaint = findViewById(R.id.simplePaint);
        btnColor = findViewById(R.id.btnColor);
        btnFree = findViewById(R.id.btnFree);
        btnRect = findViewById(R.id.btnRect);
        btnCircle = findViewById(R.id.btnCircle);
        btnClear = findViewById(R.id.btnClear);

        // Seletor de cores
        btnColor.setOnClickListener(view ->
                new ColorPickerDialog.Builder(this)
                        .setTitle("Escolha a cor")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton("Confirmar",
                                (ColorEnvelopeListener) (envelope, fromUser) ->
                                        simplePaint.setStrokeColor(envelope.getColor()))
                        .setNegativeButton("Cancelar",
                                (dialogInterface, i) ->
                                        dialogInterface.dismiss())
                        .attachAlphaSlideBar(true)
                        .attachBrightnessSlideBar(true)
                        .setBottomSpace(12)
                        .show()
        );

        // Desenho livre
        btnFree.setOnClickListener(v -> {
            simplePaint.setShapeType(SimplePaint.ShapeType.FREE_DRAW);
            highlightSelected(btnFree);
            Toast.makeText(this, "Linha livre", Toast.LENGTH_SHORT).show();
        });

        // Retângulo
        btnRect.setOnClickListener(v -> {
            simplePaint.setShapeType(SimplePaint.ShapeType.RECTANGLE);
            highlightSelected(btnRect);
            Toast.makeText(this, "Retângulo", Toast.LENGTH_SHORT).show();
        });

        // Círculo
        btnCircle.setOnClickListener(v -> {
            simplePaint.setShapeType(SimplePaint.ShapeType.CIRCLE);
            highlightSelected(btnCircle);
            Toast.makeText(this, "Círculo", Toast.LENGTH_SHORT).show();
        });

        // Limpar desenho
        btnClear.setOnClickListener(v -> {
            simplePaint.clearCanvas();
            Toast.makeText(this, "Canvas limpo!", Toast.LENGTH_SHORT).show();
        });

        // Ferramenta inicial
        highlightSelected(btnFree);
    }

    // Destacar ferramenta selecionada
    private void highlightSelected(Button selected) {

        int colorNormal = getResources().getColor(
                android.R.color.darker_gray, getTheme());

        int colorSelected = getResources().getColor(
                android.R.color.holo_blue_dark, getTheme());

        for (Button btn : new Button[]{
                btnFree, btnRect, btnCircle
        }) {
            btn.setBackgroundColor(colorNormal);
        }

        selected.setBackgroundColor(colorSelected);
    }
}
