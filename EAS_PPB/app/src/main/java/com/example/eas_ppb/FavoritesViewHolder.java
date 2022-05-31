//package com.example.eas_ppb;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//class FavoritesViewHolder extends RecyclerView.ViewHolder {
//
//    Context ctx;
//    private TextView menuName;
//    private ImageView menuImage;
//
//    public FavoritesViewHolder(@NonNull View itemView) {
//        super(itemView);
//        menuName = itemView.findViewById(R.id.textview_MenuName);
//        menuImage = itemView.findViewById(R.id.imageview_MenuImage);
//    }
//
//    public void bind(String name, String uri) {
//        menuName.setText(name);
//        Glide.with(itemView.getContext()).load(uri).into(menuImage);
//    }
//
//    static FavoritesViewHolder create(ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.cardview_menu, parent, false);
//        return new FavoritesViewHolder(view);
//    }
//}
