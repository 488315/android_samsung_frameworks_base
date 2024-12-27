package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SemContextBounceLongMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextBounceLongMotion> CREATOR =
            new Parcelable.Creator<
                    SemContextBounceLongMotion>() { // from class:
                                                    // com.samsung.android.hardware.context.SemContextBounceLongMotion.1
                @Override // android.os.Parcelable.Creator
                public SemContextBounceLongMotion createFromParcel(Parcel in) {
                    return new SemContextBounceLongMotion(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemContextBounceLongMotion[] newArray(int size) {
                    return new SemContextBounceLongMotion[size];
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

    SemContextBounceLongMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
