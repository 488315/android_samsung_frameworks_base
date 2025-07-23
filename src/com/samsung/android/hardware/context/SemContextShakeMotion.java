package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextShakeMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextShakeMotion> CREATOR = new Parcelable.Creator<SemContextShakeMotion>() { // from class: com.samsung.android.hardware.context.SemContextShakeMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion createFromParcel(Parcel parcel) {
            return new SemContextShakeMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion[] newArray(int i) {
            return new SemContextShakeMotion[i];
        }
    };
    public static final int NONE = 0;
    public static final int START = 1;
    public static final int STOP = 2;
    private Bundle mContext;

    SemContextShakeMotion() {
        this.mContext = new Bundle();
    }

    SemContextShakeMotion(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getShakeStatus() {
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
