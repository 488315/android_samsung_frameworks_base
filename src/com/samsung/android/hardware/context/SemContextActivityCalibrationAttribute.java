package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextActivityCalibrationAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityCalibrationAttribute> CREATOR = new Parcelable.Creator<SemContextActivityCalibrationAttribute>() { // from class: com.samsung.android.hardware.context.SemContextActivityCalibrationAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibrationAttribute createFromParcel(Parcel parcel) {
            return new SemContextActivityCalibrationAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibrationAttribute[] newArray(int i) {
            return new SemContextActivityCalibrationAttribute[i];
        }
    };
    private static final String TAG = "SemContextActivityCalibrationAttribute";
    private int mData;
    private float mSpeed;
    private int mStatus;

    SemContextActivityCalibrationAttribute() {
        this.mStatus = 0;
        this.mData = 0;
        this.mSpeed = 0.0f;
        setAttribute();
    }

    SemContextActivityCalibrationAttribute(Parcel parcel) {
        super(parcel);
        this.mStatus = 0;
        this.mData = 0;
        this.mSpeed = 0.0f;
    }

    public SemContextActivityCalibrationAttribute(int i, int i2) {
        this.mSpeed = 0.0f;
        this.mStatus = i;
        this.mData = i2;
        setAttribute();
    }

    public SemContextActivityCalibrationAttribute(int i, int i2, float f) {
        this.mStatus = i;
        this.mData = i2;
        this.mSpeed = f;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mStatus;
        if (i < 0 || i > 2) {
            Log.e(TAG, "Moving Status is wrong!!");
            return false;
        }
        int i2 = this.mData;
        if (i2 >= 0 && i2 <= 3) {
            return true;
        }
        Log.e(TAG, "Data of calibration is wrong!!");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        byte[] bArr = {(byte) this.mStatus, (byte) this.mData};
        bundle.putByteArray("activity_calibration", bArr);
        bundle.putFloat("activity_speed", this.mSpeed);
        Log.d(TAG, "Activity Status Data : " + ((int) bArr[0]) + ((int) bArr[1]) + ", Speed : " + this.mSpeed);
        super.setAttribute(53, bundle);
    }
}
