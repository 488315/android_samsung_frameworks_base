package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes6.dex */
public class SemContextWristUpMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextWristUpMotion> CREATOR = new Parcelable.Creator<SemContextWristUpMotion>() { // from class: com.samsung.android.hardware.context.SemContextWristUpMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWristUpMotion createFromParcel(Parcel parcel) {
            return new SemContextWristUpMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWristUpMotion[] newArray(int i) {
            return new SemContextWristUpMotion[i];
        }
    };

    @Deprecated
    public static final int NONE = 0;

    @Deprecated
    public static final int NORMAL = 1;
    private Bundle mContext;

    SemContextWristUpMotion() {
        this.mContext = new Bundle();
    }

    SemContextWristUpMotion(Parcel parcel) {
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
