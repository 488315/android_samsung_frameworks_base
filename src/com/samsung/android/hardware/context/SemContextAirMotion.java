package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextAirMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextAirMotion> CREATOR = new Parcelable.Creator<SemContextAirMotion>() { // from class: com.samsung.android.hardware.context.SemContextAirMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAirMotion createFromParcel(Parcel parcel) {
            return new SemContextAirMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAirMotion[] newArray(int i) {
            return new SemContextAirMotion[i];
        }
    };
    public static final int DOWN = 3;
    public static final int LEFT = 2;
    public static final int RIGHT = 1;
    public static final int UNKNOWN = 0;
    public static final int UP = 4;
    private Bundle mContext;

    SemContextAirMotion() {
        this.mContext = new Bundle();
    }

    SemContextAirMotion(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getDirection() {
        return this.mContext.getInt("Direction");
    }

    public int getAngle() {
        return this.mContext.getInt("Angle");
    }

    public int getSpeed() {
        return this.mContext.getInt("Speed");
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
