package com.raylabs.mathverse.data.local.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.raylabs.mathverse.R;

import java.util.ArrayList;
import java.util.List;

public class MenuData {

    private int idMenu;
    private String titleMenu;
    private Drawable icMenu;

    public MenuData(int idMenu, String titleMenu, Drawable icMenu) {
        this.idMenu = idMenu;
        this.titleMenu = titleMenu;
        this.icMenu = icMenu;
    }

    public MenuData() {
    }

    public int getIdMenu() {
        return idMenu;
    }

    public String getTitleMenu() {
        return titleMenu;
    }

    public Drawable getIcMenu(Context context, int idMenu) {
        return ContextCompat.getDrawable(context, getIdICMenu(idMenu));
    }

    public static final int BLACK_WHITE = 1;
    public static final int PYRAMID = 2;
    public static final int VOLUME = 3;
    public static final int STUDENT_CARD = 4;
    public static final int BARCODE_READER = 5;
    public static final int CHOICE = 6;

    private int getIdICMenu(int idMenu) {
        int id = 0;
        switch (idMenu) {
            case BLACK_WHITE:
                id = R.drawable.baseline_radio_button_checked_24;
                break;
            case PYRAMID:
                id = R.drawable.outline_6_ft_apart_24;
                break;
            case VOLUME:
                id = R.drawable.baseline_123_24;
                break;
            case STUDENT_CARD:
                id = R.drawable.baseline_card_membership_24;
                break;
            case BARCODE_READER:
                id = R.drawable.baseline_barcode_reader_24;
                break;
            case CHOICE:
                id = R.drawable.outline_add_task_24;
                break;
        }
        return id;
    }

    public List<MenuData> getListMenu(Context context) {
        List<MenuData> menuDataList = new ArrayList<>();
        menuDataList.add(new MenuData(CHOICE, "Compare Your Choice", getIcMenu(context, CHOICE)));
        menuDataList.add(new MenuData(BLACK_WHITE, "Black White", getIcMenu(context, BLACK_WHITE)));
        menuDataList.add(new MenuData(PYRAMID, "Pyramid", getIcMenu(context, PYRAMID)));
        menuDataList.add(new MenuData(VOLUME, "Volume", getIcMenu(context, VOLUME)));
        menuDataList.add(new MenuData(STUDENT_CARD, "Student ID", getIcMenu(context, STUDENT_CARD)));
        menuDataList.add(new MenuData(BARCODE_READER, "Barcode Reader", getIcMenu(context, BARCODE_READER)));

        return menuDataList;
    }
}
