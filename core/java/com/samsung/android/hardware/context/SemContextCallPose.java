package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextCallPose extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextCallPose> CREATOR =
            new Parcelable.Creator<
                    SemContextCallPose>() { // from class:
                                            // com.samsung.android.hardware.context.SemContextCallPose.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextCallPose createFromParcel(Parcel in) {
                    return new SemContextCallPose(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextCallPose[] newArray(int size) {
                    return new SemContextCallPose[size];
                }
            };
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    SemContextCallPose() {
        this.mContext = new Bundle();
    }

    SemContextCallPose(Parcel src) {
        readFromParcel(src);
    }

    public int getPose() {
        return this.mContext.getInt("Pose");
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
