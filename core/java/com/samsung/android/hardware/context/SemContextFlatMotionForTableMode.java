package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextFlatMotionForTableMode extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFlatMotionForTableMode> CREATOR = new Parcelable.Creator<SemContextFlatMotionForTableMode>() { // from class: com.samsung.android.hardware.context.SemContextFlatMotionForTableMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableMode createFromParcel(Parcel in) {
            return new SemContextFlatMotionForTableMode(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableMode[] newArray(int size) {
            return new SemContextFlatMotionForTableMode[size];
        }
    };
    public static final int FALSE = 2;
    public static final int NONE = 0;
    public static final int TRUE = 1;
    private Bundle mContext;

    SemContextFlatMotionForTableMode() {
        this.mContext = new Bundle();
    }

    SemContextFlatMotionForTableMode(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
