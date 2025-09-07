package com.raylabs.mathverse.feature.blackwhite.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.databinding.ActivityBwBinding;
import com.raylabs.mathverse.feature.blackwhite.domain.BwLabels;

public class BwActivity extends AppCompatActivity {

    private ActivityBwBinding binding;
    private BwViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBwBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        vm = new ViewModelProvider(this).get(BwViewModel.class);

        // Observe state
        vm.getState().observe(this, this::render);

        // onClick
        binding.btnActionClick.setOnClickListener(v -> setColorResult(true));
        binding.btnActionClear.setOnClickListener(v -> setColorResult(false));
    }

    private void setColorResult(boolean isShowingColor) {
        String white = getString(R.string.text_color_white);
        String black = getString(R.string.text_color_black);
        vm.setShowingColor(isShowingColor, white, black);
    }

    private void render(BwLabels m) {
        if (m == null) return;
        binding.tvTopWhite.setText(m.topWhite);
        binding.tvTopBlack.setText(m.topBlack);
        binding.tvCenterBlack.setText(m.centerBlack);
        binding.tvCenterWhite.setText(m.centerWhite);
        binding.tvBottomWhite.setText(m.bottomWhite);
        binding.tvBottomBlack.setText(m.bottomBlack);
    }
}