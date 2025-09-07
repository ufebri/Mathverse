package com.raylabs.mathverse.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.data.local.entity.MenuData;
import com.raylabs.mathverse.feature.blackwhite.ui.BwActivity;
import com.raylabs.mathverse.feature.pyramid.ui.PyramidActivity;
import com.raylabs.mathverse.ui.sbar.BarcodeScannerActivity;
import com.raylabs.mathverse.ui.studentid.StudentActivity;
import com.raylabs.mathverse.ui.volume.VolumeActivity;

public class MainActivity extends AppCompatActivity {

    private MenuAdapter adapter;
    private List<MenuData> menuDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        });

        rvListMenu.setLayoutManager(new LinearLayoutManager(this));
        rvListMenu.setAdapter(adapter);
    }
}
