package com.example.eas_ppb.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "menus_table")
public class Menu implements Parcelable {

	@ColumnInfo(name = "menu_id")
	@PrimaryKey
	@NonNull
	@SerializedName("_id")
	private String id;

	@ColumnInfo(name = "menu_images")
	@NonNull
	@SerializedName("images")
	private List<String> images;

	@ColumnInfo(name = "menu_name")
	@NonNull
	@SerializedName("menuname")
	private String menuname;

	@ColumnInfo(name = "menu_description")
	@NonNull
	@SerializedName("description")
	private String description;

	@ColumnInfo(name = "menu_v")
	@NonNull
	@SerializedName("__v")
	private int V;

	@ColumnInfo(name = "isFavorite")
	@NonNull
	private boolean isFavorite;

	public Menu(@NonNull List<String> images, @NonNull String menuname, @NonNull String description, int V) {
		this.images = images;
		this.menuname = menuname;
		this.description = description;
		V = V;
	}

	protected Menu(Parcel in) {
		this.id = in.readString();
		images = new ArrayList<String>();
		in.readStringList(images);
		this.menuname = in.readString();
		this.description = in.readString();
		this.V = in.readInt();
		this.isFavorite = in.readByte() != 0;
	}

	public String getId(){
		return id;
	}

	public List<String> getImages(){
		return images;
	}

	public String getMenuname(){
		return menuname;
	}

	public String getDescription(){
		return description;
	}

	public int getV(){
		return V;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImages(@NonNull List<String> images) {
		this.images = images;
	}

	public void setMenuname(@NonNull String menuname) {
		this.menuname = menuname;
	}

	public void setDescription(@NonNull String description) {
		this.description = description;
	}

	public void setV(int v) {
		V = v;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean favorite) {
		isFavorite = favorite;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeStringList(images);
		dest.writeString(menuname);
		dest.writeString(description);
		dest.writeInt(V);
		dest.writeByte((byte) (isFavorite ? 1 : 0));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Menu> CREATOR = new Creator<Menu>() {
		@Override
		public Menu createFromParcel(Parcel in) {
			return new Menu(in);
		}

		@Override
		public Menu[] newArray(int size) {
			return new Menu[size];
		}
	};

}