package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextSpecificPoseAlert extends SemContextEventContext {
    public static final int ACTION = 1;
    public static final Parcelable.Creator<SemContextSpecificPoseAlert> CREATOR = new Parcelable.Creator<SemContextSpecificPoseAlert>() { // from class: com.samsung.android.hardware.context.SemContextSpecificPoseAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlert createFromParcel(Parcel parcel) {
            return new SemContextSpecificPoseAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlert[] newArray(int i) {
            return new SemContextSpecificPoseAlert[i];
        }
    };
    public static final int NONE = 0;
    private Bundle mContext;

    SemContextSpecificPoseAlert() {
        this.mContext = new Bundle();
    }

    SemContextSpecificPoseAlert(Parcel parcel) {
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
