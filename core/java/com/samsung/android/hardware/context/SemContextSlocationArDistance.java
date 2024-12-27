package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextSlocationArDistance extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextSlocationArDistance> CREATOR =
            new Parcelable.Creator<
                    SemContextSlocationArDistance>() { // from class:
                                                       // com.samsung.android.hardware.context.SemContextSlocationArDistance.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextSlocationArDistance createFromParcel(Parcel in) {
                    return new SemContextSlocationArDistance(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextSlocationArDistance[] newArray(int size) {
                    return new SemContextSlocationArDistance[size];
                }
            };
    public static final int MODE_AR_DISTANCE_CMD = 1;
    public static final int MODE_UNKNOWN = -1;
    private Bundle mContext;

    SemContextSlocationArDistance() {
        this.mContext = new Bundle();
    }

    SemContextSlocationArDistance(Parcel src) {
        readFromParcel(src);
    }

    public int[] getDataArray() {
        return this.mContext.getIntArray("DataList");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
