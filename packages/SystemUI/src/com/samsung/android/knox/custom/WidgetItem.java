package com.samsung.android.knox.custom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WidgetItem implements Parcelable {
    public static final Parcelable.Creator<WidgetItem> CREATOR = new Parcelable.Creator<WidgetItem>() { // from class: com.samsung.android.knox.custom.WidgetItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WidgetItem createFromParcel(Parcel parcel) {
            return new WidgetItem(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WidgetItem[] newArray(int i) {
            return new WidgetItem[i];
        }
    };
    public final String TAG;
    public int mCellX;
    public final String mCellX_KEY;
    public int mCellY;
    public final String mCellY_KEY;
    public Intent mIntent;
    public final String mIntent_KEY;
    public int mMoreItems;
    public final String mMoreItems_KEY;
    public String mParent;
    public final String mParent_KEY;
    public int mSizeX;
    public final String mSizeX_KEY;
    public int mSizeY;
    public final String mSizeY_KEY;
    public int mWidgetId;
    public final String mWidgetId_KEY;
    public int mWidgetType;
    public final String mmWidgetType_KEY;

    public /* synthetic */ WidgetItem(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCellX() {
        return this.mCellX;
    }

    public int getCellY() {
        return this.mCellY;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getMoreItems() {
        return this.mMoreItems;
    }

    public String getParent() {
        return this.mParent;
    }

    public int getSizeX() {
        return this.mSizeX;
    }

    public int getSizeY() {
        return this.mSizeY;
    }

    public int getWidgetId() {
        return this.mWidgetId;
    }

    public int getWidgetType() {
        return this.mWidgetType;
    }

    public String toString() {
        return "descr:" + describeContents() + " widgetType:" + this.mWidgetType + " parent:" + this.mParent + " intent:" + this.mIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", this.mIntent);
        parcel.writeBundle(bundle);
        parcel.writeInt(this.mWidgetType);
        parcel.writeInt(this.mWidgetId);
        parcel.writeString(this.mParent);
        parcel.writeInt(this.mCellX);
        parcel.writeInt(this.mCellY);
        parcel.writeInt(this.mSizeX);
        parcel.writeInt(this.mSizeY);
        parcel.writeInt(this.mMoreItems);
    }

    public WidgetItem(int i, Intent intent, int i2, String str, int i3, int i4, int i5, int i6, int i7) {
        this.TAG = "WidgetItem";
        this.mmWidgetType_KEY = "WIDGET";
        this.mIntent_KEY = "INTENT";
        this.mWidgetId_KEY = "ID";
        this.mParent_KEY = "PARENT";
        this.mCellX_KEY = "CELLX";
        this.mCellY_KEY = "CELLY";
        this.mSizeX_KEY = "SIZEX";
        this.mSizeY_KEY = "SIZEY";
        this.mMoreItems_KEY = "MORE";
        this.mWidgetType = i;
        this.mIntent = intent;
        this.mWidgetId = i2;
        this.mParent = str;
        this.mCellX = i3;
        this.mCellY = i4;
        this.mSizeX = i5;
        this.mSizeY = i6;
        this.mMoreItems = i7;
    }

    private WidgetItem(Parcel parcel) {
        this.TAG = "WidgetItem";
        this.mmWidgetType_KEY = "WIDGET";
        this.mIntent_KEY = "INTENT";
        this.mWidgetId_KEY = "ID";
        this.mParent_KEY = "PARENT";
        this.mCellX_KEY = "CELLX";
        this.mCellY_KEY = "CELLY";
        this.mSizeX_KEY = "SIZEX";
        this.mSizeY_KEY = "SIZEY";
        this.mMoreItems_KEY = "MORE";
        this.mIntent = (Intent) parcel.readBundle().getParcelable("intent");
        this.mWidgetType = parcel.readInt();
        this.mWidgetId = parcel.readInt();
        this.mParent = parcel.readString();
        this.mCellX = parcel.readInt();
        this.mCellY = parcel.readInt();
        this.mSizeX = parcel.readInt();
        this.mSizeY = parcel.readInt();
        this.mMoreItems = parcel.readInt();
    }
}
