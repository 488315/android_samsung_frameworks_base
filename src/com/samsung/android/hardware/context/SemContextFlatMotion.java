package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextFlatMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFlatMotion> CREATOR = new Parcelable.Creator<SemContextFlatMotion>() { // from class: com.samsung.android.hardware.context.SemContextFlatMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion createFromParcel(Parcel parcel) {
            return new SemContextFlatMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion[] newArray(int i) {
            return new SemContextFlatMotion[i];
        }
    };
    public static final int FALSE = 2;
    public static final int TRUE = 1;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextFlatMotion() {
        this.mContext = new Bundle();
    }

    SemContextFlatMotion(Parcel parcel) {
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
