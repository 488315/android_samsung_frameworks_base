package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextBounceLongMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextBounceLongMotion> CREATOR = new Parcelable.Creator<SemContextBounceLongMotion>() { // from class: com.samsung.android.hardware.context.SemContextBounceLongMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextBounceLongMotion createFromParcel(Parcel parcel) {
            return new SemContextBounceLongMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextBounceLongMotion[] newArray(int i) {
            return new SemContextBounceLongMotion[i];
        }
    };
    public static final int LEFT = 2;
    public static final int NONE = 0;
    public static final int RIGHT = 1;
    public static final int UNHAND = 3;
    private Bundle mContext;

    SemContextBounceLongMotion() {
        this.mContext = new Bundle();
    }

    SemContextBounceLongMotion(Parcel parcel) {
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
