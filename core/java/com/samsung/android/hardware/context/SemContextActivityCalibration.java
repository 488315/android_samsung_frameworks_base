package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActivityCalibration extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActivityCalibration> CREATOR = new Parcelable.Creator<SemContextActivityCalibration>() { // from class: com.samsung.android.hardware.context.SemContextActivityCalibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibration createFromParcel(Parcel in) {
            return new SemContextActivityCalibration(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibration[] newArray(int size) {
            return new SemContextActivityCalibration[size];
        }
    };
    public static final int DATA_CELL_ID = 2;
    public static final int DATA_GPS = 1;
    public static final int DATA_UNKNOWN = 0;
    public static final int DATA_WIFI = 3;
    public static final int STATUS_MOVING = 1;
    public static final int STATUS_STOP = 2;
    public static final int STATUS_UNKNOWN = 0;
    private Bundle mContext;

    SemContextActivityCalibration() {
        this.mContext = new Bundle();
    }

    SemContextActivityCalibration(Parcel src) {
        readFromParcel(src);
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
