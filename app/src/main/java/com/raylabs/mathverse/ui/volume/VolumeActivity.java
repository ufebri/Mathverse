package com.raylabs.mathverse.ui.volume;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raylabs.mathverse.R;

import java.text.DecimalFormat;
import java.util.Locale;


public class VolumeActivity extends AppCompatActivity {

    private EditText etLength, etWide;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        //Binding
        Button btnCalculate = findViewById(R.id.btn_calculate);
        Button btnClear = findViewById(R.id.btn_clear);
        etLength = findViewById(R.id.et_length);
        etWide = findViewById(R.id.et_wide);
        tvResult = findViewById(R.id.tv_result);

        //action
        btnClear.setOnClickListener(v -> clear());
        btnCalculate.setOnClickListener(v -> calculate());
    }


    private void calculate() {
        Double wide = Double.parseDouble(etWide.getText().toString());
        Double length = Double.parseDouble(etLength.getText().toString());

        Double result = wide * length;
        tvResult.setText(String.format(Locale.getDefault(), "Luas = %s", new DecimalFormat("0.#").format(result)));
    }

    private void clear() {
        etWide.setText("");
        etLength.setText("");

        tvResult.setText(getString(R.string.result));
    }
}