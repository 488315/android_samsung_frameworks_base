package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated(forRemoval = true, since = "13.0")
public class SemContextAutoRotation extends SemContextEventContext {
    public static final int ANGLE_0 = 0;
    public static final int ANGLE_180 = 2;
    public static final int ANGLE_270 = 3;
    public static final int ANGLE_90 = 1;
    public static final Parcelable.Creator<SemContextAutoRotation> CREATOR =
            new Parcelable.Creator<
                    SemContextAutoRotation>() { // from class:
                                                // com.samsung.android.hardware.context.SemContextAutoRotation.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextAutoRotation createFromParcel(Parcel in) {
                    return new SemContextAutoRotation(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextAutoRotation[] newArray(int size) {
                    return new SemContextAutoRotation[size];
                }
            };
    public static final int DEVICE_TYPE_MOBILE = 0;
    public static final int DEVICE_TYPE_TABLET = 2;
    public static final int DEVICE_TYPE_WIDE_TABLET = 4;
    public static final int NONE = -1;
    private Bundle mContext;

    SemContextAutoRotation() {
        this.mContext = new Bundle();
    }

    SemContextAutoRotation(Parcel src) {
        readFromParcel(src);
    }

    public int getAngle() {
        return this.mContext.getInt("Angle");
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
