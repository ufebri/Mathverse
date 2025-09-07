package com.raylabs.mathverse.data.local.entity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.ui.home.MenuAdapter;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    private final ShapeableImageView sivIcon;
    private final TextView tvTitleMenu;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        sivIcon = itemView.findViewById(R.id.siv_ic_menu);
        tvTitleMenu = itemView.findViewById(R.id.tv_title_menu);
    }

    public void bind(MenuData mData, MenuAdapter.onClick onClick) {
        sivIcon.setImageDrawable(mData.getIcMenu(itemView.getContext(), mData.getIdMenu()));
        tvTitleMenu.setText(mData.getTitleMenu());

        itemView.setOnClickListener(v -> onClick.selectedMenu(mData.getIdMenu()));
    }
}
