package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextActivityLocationLoggingAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityLocationLoggingAttribute> CREATOR = new Parcelable.Creator<SemContextActivityLocationLoggingAttribute>() { // from class: com.samsung.android.hardware.context.SemContextActivityLocationLoggingAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityLocationLoggingAttribute createFromParcel(Parcel parcel) {
            return new SemContextActivityLocationLoggingAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityLocationLoggingAttribute[] newArray(int i) {
            return new SemContextActivityLocationLoggingAttribute[i];
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

    SemContextActivityLocationLoggingAttribute(Parcel parcel) {
        super(parcel);
        this.mStopPeriod = 60;
        this.mWaitPeriod = 120;
        this.mStayingRadius = 50;
        this.mAreaRadius = 150;
        this.mLppResolution = 0;
    }

    public SemContextActivityLocationLoggingAttribute(int i, int i2, int i3, int i4, int i5) {
        this.mStopPeriod = i;
        this.mWaitPeriod = i2;
        this.mStayingRadius = i3;
        this.mAreaRadius = i4;
        this.mLppResolution = i5;
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
        int i = this.mLppResolution;
        if (i >= 0 && i <= 2) {
            return true;
        }
        Log.e(TAG, "The lpp resolution is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("stop_period", this.mStopPeriod);
        bundle.putInt("wait_period", this.mWaitPeriod);
        bundle.putInt("staying_radius", this.mStayingRadius);
        bundle.putInt("area_radius", this.mAreaRadius);
        bundle.putInt("lpp_resolution", this.mLppResolution);
        super.setAttribute(24, bundle);
    }
}
