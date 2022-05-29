package com.example.eas_ppb;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "menus_table")
public class Menu implements Parcelable {

	@ColumnInfo(name = "menu_id")
	@PrimaryKey(autoGenerate = true)
	@SerializedName("_id")
	private String id;

	@ColumnInfo(name = "menu_images")
	@NonNull
	@SerializedName("images")
	final private List<String> images;

	@ColumnInfo(name = "menu_name")
	@NonNull
	@SerializedName("menuname")
	final private String menuname;

	@ColumnInfo(name = "menu_description")
	@NonNull
	@SerializedName("description")
	final private String description;

	@ColumnInfo(name = "menu_v")
	@NonNull
	@SerializedName("__v")
	final private int V;

	public Menu(@NonNull List<String> images, @NonNull String menuname, @NonNull String description, int v) {
		this.images = images;
		this.menuname = menuname;
		this.description = description;
		this.V = v;
	}

	protected Menu(Parcel in) {
		this.id = in.readString();
		images = new ArrayList<String>();
		in.readStringList(images);
		this.menuname = in.readString();
		this.description = in.readString();
		this.V = in.readInt();
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

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeStringList(images);
		dest.writeString(menuname);
		dest.writeString(description);
		dest.writeInt(V);
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