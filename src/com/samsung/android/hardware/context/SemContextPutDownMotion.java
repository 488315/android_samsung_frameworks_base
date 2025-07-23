package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextPutDownMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextPutDownMotion> CREATOR = new Parcelable.Creator<SemContextPutDownMotion>() { // from class: com.samsung.android.hardware.context.SemContextPutDownMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPutDownMotion createFromParcel(Parcel parcel) {
            return new SemContextPutDownMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPutDownMotion[] newArray(int i) {
            return new SemContextPutDownMotion[i];
        }
    };
    public static final int FALSE = 2;
    public static final int NONE = 0;
    public static final int TRUE = 1;
    private Bundle mContext;

    SemContextPutDownMotion() {
        this.mContext = new Bundle();
    }

    SemContextPutDownMotion(Parcel parcel) {
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
