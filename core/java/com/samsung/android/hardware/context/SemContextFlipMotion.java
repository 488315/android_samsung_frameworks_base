package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SemContextFlipMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFlipMotion> CREATOR =
            new Parcelable.Creator<
                    SemContextFlipMotion>() { // from class:
                                              // com.samsung.android.hardware.context.SemContextFlipMotion.1
                @Override // android.os.Parcelable.Creator
                public SemContextFlipMotion createFromParcel(Parcel in) {
                    return new SemContextFlipMotion(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemContextFlipMotion[] newArray(int size) {
                    return new SemContextFlipMotion[size];
                }
            };
    public static final int STATUS_BACK = 2;
    public static final int STATUS_FRONT = 1;
    public static final int STATUS_RESET = 4;
    public static final int STATUS_START = 3;
    public static final int STATUS_UNKNOWN = 0;
    private Bundle mContext;

    SemContextFlipMotion() {
        this.mContext = new Bundle();
    }

    SemContextFlipMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getStatus() {
        return this.mContext.getInt("Action");
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
