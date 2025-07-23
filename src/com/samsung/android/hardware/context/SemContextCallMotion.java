package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextCallMotion extends SemContextEventContext {
    public static final int ACTION = 1;
    public static final Parcelable.Creator<SemContextCallMotion> CREATOR = new Parcelable.Creator<SemContextCallMotion>() { // from class: com.samsung.android.hardware.context.SemContextCallMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCallMotion createFromParcel(Parcel parcel) {
            return new SemContextCallMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCallMotion[] newArray(int i) {
            return new SemContextCallMotion[i];
        }
    };
    public static final int NONE = 0;
    private Bundle mContext;

    SemContextCallMotion() {
        this.mContext = new Bundle();
    }

    SemContextCallMotion(Parcel parcel) {
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
