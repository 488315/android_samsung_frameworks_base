package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActiveTimeMonitor extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActiveTimeMonitor> CREATOR =
            new Parcelable.Creator<
                    SemContextActiveTimeMonitor>() { // from class:
                                                     // com.samsung.android.hardware.context.SemContextActiveTimeMonitor.1
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

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
