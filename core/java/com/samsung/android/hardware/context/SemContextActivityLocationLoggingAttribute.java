package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextActivityLocationLoggingAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityLocationLoggingAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextActivityLocationLoggingAttribute>() { // from class:
                                                                    // com.samsung.android.hardware.context.SemContextActivityLocationLoggingAttribute.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextActivityLocationLoggingAttribute createFromParcel(Parcel in) {
                    return new SemContextActivityLocationLoggingAttribute(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextActivityLocationLoggingAttribute[] newArray(int size) {
                    return new SemContextActivityLocationLoggingAttribute[size];
                }
            };
    private static final String TAG = "SemContextActivityLocationLoggingAttribute";
    private int mAreaRadius;
    private int mLppResolution;
    private int mStayingRadius;
    private int mStopPeriod;
    private int mWaitPeriod;

    SemContextActivityLocationLoggingAttribute() {
        this.mStopPeriod = 60;
        this.mWaitPeriod = 120;
        this.mStayingRadius = 50;
        this.mAreaRadius = 150;
        this.mLppResolution = 0;
        setAttribute();
    }

    SemContextActivityLocationLoggingAttribute(Parcel src) {
        super(src);
        this.mStopPeriod = 60;
        this.mWaitPeriod = 120;
        this.mStayingRadius = 50;
        this.mAreaRadius = 150;
        this.mLppResolution = 0;
    }

    public SemContextActivityLocationLoggingAttribute(
            int stopPeriod, int waitPeriod, int stayingRadius, int areaRadius, int lppResolution) {
        this.mStopPeriod = 60;
        this.mWaitPeriod = 120;
        this.mStayingRadius = 50;
        this.mAreaRadius = 150;
        this.mLppResolution = 0;
        this.mStopPeriod = stopPeriod;
        this.mWaitPeriod = waitPeriod;
        this.mStayingRadius = stayingRadius;
        this.mAreaRadius = areaRadius;
        this.mLppResolution = lppResolution;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStopPeriod < 0) {
            Log.e(TAG, "The stop period is wrong.");
            return false;
        }
        if (this.mWaitPeriod < 0) {
            Log.e(TAG, "The wait period is wrong.");
            return false;
        }
        if (this.mStayingRadius < 0) {
            Log.e(TAG, "The staying radius is wrong.");
            return false;
        }
        if (this.mAreaRadius < 0) {
            Log.e(TAG, "The area radius is wrong.");
            return false;
        }
        if (this.mLppResolution < 0 || this.mLppResolution > 2) {
            Log.e(TAG, "The lpp resolution is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("stop_period", this.mStopPeriod);
        attribute.putInt("wait_period", this.mWaitPeriod);
        attribute.putInt("staying_radius", this.mStayingRadius);
        attribute.putInt("area_radius", this.mAreaRadius);
        attribute.putInt("lpp_resolution", this.mLppResolution);
        super.setAttribute(24, attribute);
    }
}
