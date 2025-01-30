package com.samsung.android.knox.lockscreen;

import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOItemSpace extends LSOItemData {
    public LSOItemSpace() {
        super((byte) 1);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final String toString() {
        return "SpaceView " + super.toString();
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
