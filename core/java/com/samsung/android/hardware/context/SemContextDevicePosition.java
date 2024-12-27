package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextDevicePosition extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextDevicePosition> CREATOR = new Parcelable.Creator<SemContextDevicePosition>() { // from class: com.samsung.android.hardware.context.SemContextDevicePosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDevicePosition createFromParcel(Parcel in) {
            return new SemContextDevicePosition(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDevicePosition[] newArray(int size) {
            return new SemContextDevicePosition[size];
        }
    };
    public static final int MOVING = 3;
    public static final int SCREEN_DOWN = 2;
    public static final int SCREEN_PERFECT_DOWN = 6;
    public static final int SCREEN_UP = 1;
    public static final int SCREEN_VERTICALITY = 4;
    public static final int SCREEN_VERTICALITY_REVERSE = 5;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextDevicePosition() {
        this.mContext = new Bundle();
    }

    SemContextDevicePosition(Parcel src) {
        readFromParcel(src);
    }

    public int getPosition() {
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
