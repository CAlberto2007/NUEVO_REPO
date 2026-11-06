package com.example.cuentabar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {

 EditText editImporte;
 CheckBox checkPropina;
 SeekBar seekPropina;
 TextView textPorcentaje, textResultado;
 RadioGroup radioPago;
 RatingBar ratingServicio;
 AutoCompleteTextView autoCamarero;

 @Override
 public void onCreate(Bundle b) {
  super.onCreate(b);
  setContentView(R.layout.activity_main);

  editImporte = findViewById(R.id.editImporte);
  checkPropina = findViewById(R.id.checkPropina);
  seekPropina = findViewById(R.id.seekPropina);
  textPorcentaje = findViewById(R.id.textPorcentaje);
  radioPago = findViewById(R.id.radioPago);
  ratingServicio = findViewById(R.id.ratingServicio);
  autoCamarero = findViewById(R.id.autoCamarero);
  textResultado = findViewById(R.id.textResultado);

  String[] nombres = {"Carlos", "María", "Juan", "Ana", "Pedro"};

  autoCamarero.setAdapter(
          new ArrayAdapter<>(
                  this,
                  android.R.layout.simple_dropdown_item_1line,
                  nombres
          )
  );

  autoCamarero.setThreshold(3);

  seekPropina.setProgress(10);

  textPorcentaje.setText(
          getString(R.string.porcentaje_actual, 10)
  );

  seekPropina.setOnSeekBarChangeListener(
          new SeekBar.OnSeekBarChangeListener() {

           public void onProgressChanged(
                   SeekBar s,
                   int p,
                   boolean u
           ) {
            textPorcentaje.setText(
                    getString(R.string.porcentaje_actual, p)
            );
           }

           public void onStartTrackingTouch(SeekBar s) {
           }

           public void onStopTrackingTouch(SeekBar s) {
           }
          }
  );

  findViewById(R.id.buttonCalcular).setOnClickListener(
          new View.OnClickListener() {
           public void onClick(View v) {
            calcular();
           }
          }
  );
 }

 void calcular() {

  String t = editImporte.getText().toString().trim();

  if (t.isEmpty()) {
   editImporte.setError(getString(R.string.error_vacio));
   return;
  }

  double importe;

  try {
   importe = Double.parseDouble(t.replace(",", "."));
  } catch (Exception e) {
   editImporte.setError(getString(R.string.error_numero));
   return;
  }

  if (importe <= 0) {
   editImporte.setError(getString(R.string.error_positivo));
   return;
  }

  if (radioPago.getCheckedRadioButtonId() == -1) {
   Toast.makeText(
           this,
           R.string.error_pago,
           Toast.LENGTH_SHORT
   ).show();

   return;
  }

  double propina = checkPropina.isChecked()
          ? importe * seekPropina.getProgress() / 100.0
          : 0;

  double total = importe + propina;

  NumberFormat f = NumberFormat.getCurrencyInstance(
          Locale.getDefault()
  );

  String pago = radioPago.getCheckedRadioButtonId()
          == R.id.radioEfectivo
          ? getString(R.string.efectivo)
          : getString(R.string.tarjeta);

  textResultado.setText(
          getString(
                  R.string.resultado,
                  f.format(importe),
                  f.format(propina),
                  f.format(total),
                  pago,
                  ratingServicio.getRating(),
                  autoCamarero.getText().toString().trim()
          )
  );
 }
}