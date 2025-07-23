package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActiveTimeMonitor extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActiveTimeMonitor> CREATOR = new Parcelable.Creator<SemContextActiveTimeMonitor>() { // from class: com.samsung.android.hardware.context.SemContextActiveTimeMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActiveTimeMonitor createFromParcel(Parcel parcel) {
            return new SemContextActiveTimeMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActiveTimeMonitor[] newArray(int i) {
            return new SemContextActiveTimeMonitor[i];
        }
    };
    private Bundle mContext;

    SemContextActiveTimeMonitor() {
        this.mContext = new Bundle();
    }

    SemContextActiveTimeMonitor(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getDuration() {
        return this.mContext.getInt("ActiveTimeDuration");
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
