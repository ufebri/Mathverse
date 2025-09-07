package com.raylabs.mathverse.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.data.local.entity.MenuData;
import com.raylabs.mathverse.data.local.entity.MenuViewHolder;


public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private Context mContext;
    private List<MenuData> mListData;
    private onClick onClick;

    public MenuAdapter(Context mContext, List<MenuData> mListData, onClick onClick) {
        this.mContext = mContext;
        this.mListData = mListData;
        this.onClick = onClick;
    }

    public interface onClick {
        void selectedMenu(int idMenu);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_data_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.bind(mListData.get(position), onClick);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}
