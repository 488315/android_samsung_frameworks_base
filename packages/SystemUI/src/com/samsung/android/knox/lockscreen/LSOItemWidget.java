package com.samsung.android.knox.lockscreen;

import android.os.Parcel;

/* loaded from: classes4.dex */
public class LSOItemWidget extends LSOItemData {
    public static final int LSO_FIELD_PACKAGE_NAME = 128;
    public String packageName;

    public LSOItemWidget() {
        super((byte) 5);
    }

    public String getWidget() {
        return this.packageName;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.packageName = readStringFromParcel(parcel, 128);
    }

    public void setWidget(String str) {
        this.packageName = str;
        updateFieldFlag(128);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public String toString() {
        return toString("CustomWidget: " + super.toString(), 128, "PackageName:" + this.packageName);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        writeToParcel(parcel, 128, this.packageName);
    }

    public LSOItemWidget(Parcel parcel) {
        super((byte) 5, parcel);
    }

    public LSOItemWidget(int i, int i2) {
        super((byte) 5);
        setDimension(i, i2);
    }

    public LSOItemWidget(int i, int i2, float f) {
        super((byte) 5);
        setDimension(i, i2, f);
    }
}
