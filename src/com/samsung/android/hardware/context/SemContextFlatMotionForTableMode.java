package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextFlatMotionForTableMode extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFlatMotionForTableMode> CREATOR = new Parcelable.Creator<SemContextFlatMotionForTableMode>() { // from class: com.samsung.android.hardware.context.SemContextFlatMotionForTableMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableMode createFromParcel(Parcel parcel) {
            return new SemContextFlatMotionForTableMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableMode[] newArray(int i) {
            return new SemContextFlatMotionForTableMode[i];
        }
    };
    public static final int FALSE = 2;
    public static final int NONE = 0;
    public static final int TRUE = 1;
    private Bundle mContext;

    SemContextFlatMotionForTableMode() {
        this.mContext = new Bundle();
    }

    SemContextFlatMotionForTableMode(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
