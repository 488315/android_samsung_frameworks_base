package com.samsung.android.knox.lockscreen;

import android.os.Parcel;

/* loaded from: classes4.dex */
public class LSOItemSpace extends LSOItemData {
    public LSOItemSpace() {
        super((byte) 1);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public String toString() {
        return "SpaceView " + super.toString();
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public LSOItemSpace(Parcel parcel) {
        super((byte) 1, parcel);
    }

    public LSOItemSpace(int i, int i2) {
        super((byte) 1);
        setDimension(i, i2);
    }

    public LSOItemSpace(int i, int i2, float f) {
        super((byte) 1);
        setDimension(i, i2, f);
    }
}
