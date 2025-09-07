package com.raylabs.mathverse.feature.pyramid.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.feature.pyramid.data.PyramidGenerator;


public class PyramidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);

        int jmlBintang = 10;

        TextView tvPyramid = findViewById(R.id.pyramid);
        tvPyramid.setText(PyramidGenerator.build(jmlBintang, "*"));
    }
}