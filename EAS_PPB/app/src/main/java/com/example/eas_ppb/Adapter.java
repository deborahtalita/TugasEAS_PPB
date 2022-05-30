package com.example.eas_ppb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eas_ppb.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private List<Menu> menus, filteredMenus;
    private OnItemClickListener onItemClickListener;

    public Adapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
        this.filteredMenus = menus;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_menu, viewGroup, false);
        return new Holder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
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

    class Holder extends RecyclerView.ViewHolder{
        private TextView menuName;
        private ImageView menuImage;

        public Holder(@NonNull View itemView, final Adapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            this.menuName = itemView.findViewById(R.id.textview_MenuName);
            this.menuImage = itemView.findViewById(R.id.imageview_MenuImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(menus.get(getAdapterPosition()));
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Menu menu);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
