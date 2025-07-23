package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActivityCalibration extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActivityCalibration> CREATOR = new Parcelable.Creator<SemContextActivityCalibration>() { // from class: com.samsung.android.hardware.context.SemContextActivityCalibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibration createFromParcel(Parcel parcel) {
            return new SemContextActivityCalibration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibration[] newArray(int i) {
            return new SemContextActivityCalibration[i];
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

    SemContextActivityCalibration(Parcel parcel) {
        readFromParcel(parcel);
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
