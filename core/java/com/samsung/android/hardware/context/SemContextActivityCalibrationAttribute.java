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
        public SemContextActivityCalibrationAttribute createFromParcel(Parcel in) {
            return new SemContextActivityCalibrationAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityCalibrationAttribute[] newArray(int size) {
            return new SemContextActivityCalibrationAttribute[size];
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

    SemContextActivityCalibrationAttribute(Parcel src) {
        super(src);
        this.mStatus = 0;
        this.mData = 0;
        this.mSpeed = 0.0f;
    }

    public SemContextActivityCalibrationAttribute(int status, int data) {
        this.mStatus = 0;
        this.mData = 0;
        this.mSpeed = 0.0f;
        this.mStatus = status;
        this.mData = data;
        setAttribute();
    }

    public SemContextActivityCalibrationAttribute(int status, int data, float speed) {
        this.mStatus = 0;
        this.mData = 0;
        this.mSpeed = 0.0f;
        this.mStatus = status;
        this.mData = data;
        this.mSpeed = speed;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStatus < 0 || this.mStatus > 2) {
            Log.e(TAG, "Moving Status is wrong!!");
            return false;
        }
        if (this.mData < 0 || this.mData > 3) {
            Log.e(TAG, "Data of calibration is wrong!!");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        byte[] acData = {(byte) this.mStatus, (byte) this.mData};
        attribute.putByteArray("activity_calibration", acData);
        attribute.putFloat("activity_speed", this.mSpeed);
        Log.d(TAG, "Activity Status Data : " + ((int) acData[0]) + ((int) acData[1]) + ", Speed : " + this.mSpeed);
        super.setAttribute(53, attribute);
    }
}
