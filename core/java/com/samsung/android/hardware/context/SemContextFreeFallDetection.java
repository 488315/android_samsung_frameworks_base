package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextFreeFallDetection extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFreeFallDetection> CREATOR = new Parcelable.Creator<SemContextFreeFallDetection>() { // from class: com.samsung.android.hardware.context.SemContextFreeFallDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFreeFallDetection createFromParcel(Parcel in) {
            return new SemContextFreeFallDetection(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFreeFallDetection[] newArray(int size) {
            return new SemContextFreeFallDetection[size];
        }
    };
    public static final int END = 2;
    public static final int START = 1;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextFreeFallDetection() {
        this.mContext = new Bundle();
    }

    SemContextFreeFallDetection(Parcel src) {
        readFromParcel(src);
    }

    public int getStatus() {
        return this.mContext.getInt("status");
    }

    public long getHeight() {
        return this.mContext.getLong("height");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
