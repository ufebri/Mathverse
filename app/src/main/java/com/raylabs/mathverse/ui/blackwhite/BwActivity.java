package com.raylabs.mathverse.ui.blackwhite;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raylabs.mathverse.R;


public class BwActivity extends AppCompatActivity {

    private TextView tvTopWhite, tvTopBlack, tvCenterBlack, tvCenterWhite, tvBottomWhite, tvBottomBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bw);

        //binding
        tvTopWhite = findViewById(R.id.tv_top_white);
        tvTopBlack = findViewById(R.id.tv_top_black);
        tvCenterBlack = findViewById(R.id.tv_center_black);
        tvCenterWhite = findViewById(R.id.tv_center_white);
        tvBottomWhite = findViewById(R.id.tv_bottom_white);
        tvBottomBlack = findViewById(R.id.tv_bottom_black);


        Button btnActionColor = findViewById(R.id.btn_action_click);
        Button btnRemoveColor = findViewById(R.id.btn_action_clear);

        //onClick
        btnActionColor.setOnClickListener(v -> setColorResult(true));

        btnRemoveColor.setOnClickListener(v -> setColorResult(false));
    }

    private void setColorResult(boolean isShowingColor) {
        tvTopWhite.setText(isShowingColor ? getString(R.string.text_color_white) : "");
        tvTopBlack.setText(isShowingColor ? getString(R.string.text_color_black) : "");
        tvCenterBlack.setText(isShowingColor ? getString(R.string.text_color_black) : "");
        tvCenterWhite.setText(isShowingColor ? getString(R.string.text_color_white) : "");
        tvBottomWhite.setText(isShowingColor ? getString(R.string.text_color_white) : "");
        tvBottomBlack.setText(isShowingColor ? getString(R.string.text_color_black) : "");
    }

}