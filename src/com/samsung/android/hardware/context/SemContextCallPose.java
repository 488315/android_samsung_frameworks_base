package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextCallPose extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextCallPose> CREATOR = new Parcelable.Creator<SemContextCallPose>() { // from class: com.samsung.android.hardware.context.SemContextCallPose.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCallPose createFromParcel(Parcel parcel) {
            return new SemContextCallPose(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCallPose[] newArray(int i) {
            return new SemContextCallPose[i];
        }
    };
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextCallPose() {
        this.mContext = new Bundle();
    }

    SemContextCallPose(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getPose() {
        return this.mContext.getInt("Pose");
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
