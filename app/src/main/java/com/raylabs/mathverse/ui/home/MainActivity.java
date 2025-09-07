package com.raylabs.mathverse.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.data.local.entity.MenuData;
import com.raylabs.mathverse.feature.blackwhite.ui.BwActivity;
import com.raylabs.mathverse.feature.mychoice.ui.MyChoiceActivity;
import com.raylabs.mathverse.feature.pyramid.ui.PyramidActivity;
import com.raylabs.mathverse.feature.volume.ui.VolumeActivity;
import com.raylabs.mathverse.ui.sbar.BarcodeScannerActivity;
import com.raylabs.mathverse.ui.studentid.StudentActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MenuAdapter adapter;
    private List<MenuData> menuDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        int color = ContextCompat.getColor(this, R.color.white); // atau ambil dari theme
        getWindow().setStatusBarColor(color);

        boolean isLight = ColorUtils.calculateLuminance(color) > 0.5;
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(isLight);

        RecyclerView rvListMenu = findViewById(R.id.rv_listMenu);
        menuDataList = new MenuData().getListMenu(this);
        adapter = new MenuAdapter(this, menuDataList, idMenu -> {
            switch (idMenu) {
                case MenuData.BLACK_WHITE:
                    startActivity(new Intent(this, BwActivity.class));
                    break;
                case MenuData.PYRAMID:
                    startActivity(new Intent(this, PyramidActivity.class));
                    break;
                case MenuData.STUDENT_CARD:
                    startActivity(new Intent(this, StudentActivity.class));
                    break;
                case MenuData.VOLUME:
                    startActivity(new Intent(this, VolumeActivity.class));
                    break;
                case MenuData.BARCODE_READER:
                    startActivity(new Intent(this, BarcodeScannerActivity.class));
                    break;
                case MenuData.CHOICE:
                    startActivity(new Intent(this, MyChoiceActivity.class));
                    break;
            }
        });

        rvListMenu.setLayoutManager(new LinearLayoutManager(this));
        rvListMenu.setAdapter(adapter);
    }
}
