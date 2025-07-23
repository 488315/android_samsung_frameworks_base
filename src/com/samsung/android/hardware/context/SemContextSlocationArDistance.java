package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextSlocationArDistance extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextSlocationArDistance> CREATOR = new Parcelable.Creator<SemContextSlocationArDistance>() { // from class: com.samsung.android.hardware.context.SemContextSlocationArDistance.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSlocationArDistance createFromParcel(Parcel parcel) {
            return new SemContextSlocationArDistance(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSlocationArDistance[] newArray(int i) {
            return new SemContextSlocationArDistance[i];
        }
    };
    public static final int MODE_AR_DISTANCE_CMD = 1;
    public static final int MODE_UNKNOWN = -1;
    private Bundle mContext;

    SemContextSlocationArDistance() {
        this.mContext = new Bundle();
    }

    SemContextSlocationArDistance(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int[] getDataArray() {
        return this.mContext.getIntArray("DataList");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
    }
}
