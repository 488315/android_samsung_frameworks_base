package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextStepCountAlert extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextStepCountAlert> CREATOR = new Parcelable.Creator<SemContextStepCountAlert>() { // from class: com.samsung.android.hardware.context.SemContextStepCountAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlert createFromParcel(Parcel parcel) {
            return new SemContextStepCountAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlert[] newArray(int i) {
            return new SemContextStepCountAlert[i];
        }
    };
    public static final int EXPIRED = 1;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextStepCountAlert() {
        this.mContext = new Bundle();
    }

    SemContextStepCountAlert(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAlert() {
        return this.mContext.getInt("Action") == 1 ? 1 : 0;
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
