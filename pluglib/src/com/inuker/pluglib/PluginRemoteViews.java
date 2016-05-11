package com.inuker.pluglib;

import android.os.Parcel;
import android.os.Parcelable;

public class PluginRemoteViews implements Parcelable {
	
	private String mLayoutName;

	public String getLayoutName() {
		return mLayoutName;
	}

	public void setmLayoutName(String layoutName) {
		this.mLayoutName = layoutName;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mLayoutName);
	}
	
	public PluginRemoteViews(Parcel in) {
		mLayoutName = in.readString();
	}

	public static final Creator<PluginRemoteViews> CREATOR = new Creator<PluginRemoteViews>() {
        public PluginRemoteViews createFromParcel(Parcel source) {
            return new PluginRemoteViews(source);
        }

        public PluginRemoteViews[] newArray(int size) {
            return new PluginRemoteViews[size];
        }
    };
}
