package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextFreeFallDetection extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFreeFallDetection> CREATOR = new Parcelable.Creator<SemContextFreeFallDetection>() { // from class: com.samsung.android.hardware.context.SemContextFreeFallDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFreeFallDetection createFromParcel(Parcel parcel) {
            return new SemContextFreeFallDetection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFreeFallDetection[] newArray(int i) {
            return new SemContextFreeFallDetection[i];
        }
    };
    public static final int END = 2;
    public static final int START = 1;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextFreeFallDetection() {
        this.mContext = new Bundle();
    }

    SemContextFreeFallDetection(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getStatus() {
        return this.mContext.getInt("status");
    }

    public long getHeight() {
        return this.mContext.getLong("height");
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
