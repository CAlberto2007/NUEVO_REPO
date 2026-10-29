package com.example.tema3layouts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private String currentInput = "";
    private double firstValue = 0.0;
    private String currentOperator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora);

        tvResult = findViewById(R.id.Texto);

        setupNumberAndDecimalButtons();
        setupOperatorButtons();

        // Botón de igual (=)
        Button btnEquals = findViewById(R.id.botonigual);
        if (btnEquals != null) {
            btnEquals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculateResult();
                }
            });
        }

        // Botón A/C (Borrar todo - Criterio 7)
        Button btnClear = findViewById(R.id.botoneliminar);
        if (btnClear != null) {
            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentInput = "";
                    firstValue = 0.0;
                    currentOperator = "";
                    tvResult.setText("0");
                    isNewOperation = true;
                }
            });
        }
    }

    private void setupNumberAndDecimalButtons() {
        int[] numberIds = {
                R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3, R.id.boton4,
                R.id.boton5, R.id.boton6, R.id.boton7, R.id.boton8, R.id.boton9, R.id.botonpunto
        };

        final String[] numberValues = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."
        };

        for (int i = 0; i < numberIds.length; i++) {
            final String value = numberValues[i];
            Button btn = findViewById(numberIds[i]);
            if (btn != null) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNewOperation) {
                            if (value.equals(".")) {
                                currentInput = "0.";
                            } else if (value.equals("0")) {
                                currentInput = "0";
                                return; // Evita múltiples ceros iniciales (Criterio 5)
                            } else {
                                currentInput = value;
                            }
                            isNewOperation = false;
                        } else {
                            // Control de coma decimal única (Criterio 4)
                            if (value.equals(".") && currentInput.contains(".")) {
                                return;
                            }
                            // Control de ceros a la izquierda si el único valor es "0"
                            if (currentInput.equals("0") && value.equals("0")) {
                                return;
                            }
                            if (currentInput.equals("0") && !value.equals(".")) {
                                currentInput = value; // Reemplaza el "0" inicial por el nuevo número
                            } else {
                                currentInput += value;
                            }
                        }
                        tvResult.setText(currentInput);
                    }
                });
            }
        }
    }

    private void setupOperatorButtons() {
        setupOpButton(R.id.botonsuma, "+");
        setupOpButton(R.id.botonmenos, "-");
        setupOpButton(R.id.botonpor, "*");
        setupOpButton(R.id.botondividir, "/");
    }

    private void setupOpButton(int buttonId, final String operator) {
        Button btn = findViewById(buttonId);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!currentInput.isEmpty()) {
                        firstValue = Double.parseDouble(currentInput);
                        currentOperator = operator;
                        isNewOperation = true;
                    }
                }
            });
        }
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {
            double secondValue = Double.parseDouble(currentInput);
            double result = 0.0;

            if (currentOperator.equals("+")) {
                result = firstValue + secondValue;
            } else if (currentOperator.equals("-")) {
                result = firstValue - secondValue;
            } else if (currentOperator.equals("*")) {
                result = firstValue * secondValue;
            } else if (currentOperator.equals("/")) {
                if (secondValue != 0.0) {
                    result = firstValue / secondValue;
                } else {
                    result = 0.0;
                }
            }

            if (result % 1.0 == 0.0) {
                tvResult.setText(String.valueOf((int) result));
            } else {
                tvResult.setText(String.valueOf(result));
            }
            currentInput = tvResult.getText().toString();
            isNewOperation = true;
            currentOperator = "";
        }
    }
}