package com.example.eas_ppb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eas_ppb.model.Menu;

import java.util.List;


public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {

    private Context ctx;
    private List<Menu> menus;

    public FavoritesListAdapter(Context ctx, List<Menu> menus) {
        this.ctx = ctx;
        this.menus = menus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu menu = menus.get(position);
        String uri = menu.getImages().get(0);

        holder.menuName.setText(menu.getMenuname());
        Glide.with(holder.itemView.getContext()).load(uri).into(holder.menuImage);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
        notifyDataSetChanged();
    }

    public Menu getMenuAt(int position) {
        return menus.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView menuName;
        private ImageView menuImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.textview_MenuName);
            menuImage = itemView.findViewById(R.id.imageview_MenuImage);
        }
    }
}
