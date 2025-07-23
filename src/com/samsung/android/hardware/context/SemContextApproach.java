package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextApproach extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextApproach> CREATOR = new Parcelable.Creator<SemContextApproach>() { // from class: com.samsung.android.hardware.context.SemContextApproach.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproach createFromParcel(Parcel parcel) {
            return new SemContextApproach(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproach[] newArray(int i) {
            return new SemContextApproach[i];
        }
    };
    public static final int FAR = 0;
    public static final int NEAR = 1;
    private Bundle mContext;

    SemContextApproach() {
        this.mContext = new Bundle();
    }

    SemContextApproach(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getApproach() {
        return this.mContext.getInt("Proximity");
    }

    public int getUserID() {
        return this.mContext.getInt("UserID");
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
