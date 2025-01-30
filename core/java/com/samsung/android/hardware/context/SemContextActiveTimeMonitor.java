package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextActiveTimeMonitor extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActiveTimeMonitor> CREATOR = new Parcelable.Creator<SemContextActiveTimeMonitor>() { // from class: com.samsung.android.hardware.context.SemContextActiveTimeMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActiveTimeMonitor createFromParcel(Parcel in) {
            return new SemContextActiveTimeMonitor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActiveTimeMonitor[] newArray(int size) {
            return new SemContextActiveTimeMonitor[size];
        }
    };
    private Bundle mContext;

    SemContextActiveTimeMonitor() {
        this.mContext = new Bundle();
    }

    SemContextActiveTimeMonitor(Parcel src) {
        readFromParcel(src);
    }

    public int getDuration() {
        return this.mContext.getInt("ActiveTimeDuration");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
