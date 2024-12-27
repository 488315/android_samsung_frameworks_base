package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SemContextCarryingDetectionAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextCarryingDetectionAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextCarryingDetectionAttribute>() { // from class:
                                                              // com.samsung.android.hardware.context.SemContextCarryingDetectionAttribute.1
                @Override // android.os.Parcelable.Creator
                public SemContextCarryingDetectionAttribute createFromParcel(Parcel in) {
                    return new SemContextCarryingDetectionAttribute(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemContextCarryingDetectionAttribute[] newArray(int size) {
                    return new SemContextCarryingDetectionAttribute[size];
                }
            };
    private static final int DATA = 1;
    private static final int MODE = 2;
    private static final String TAG = "SemContextCarryingDetection";
    private int mData;
    private int mDpcmHighData;
    private int mDpcmLowData;
    private int mMode;

    SemContextCarryingDetectionAttribute() {
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        setAttribute();
    }

    SemContextCarryingDetectionAttribute(Parcel src) {
        super(src);
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
    }

    public SemContextCarryingDetectionAttribute(int mode, int data) {
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        this.mMode = mode;
        this.mData = data;
        setAttribute();
    }

    public SemContextCarryingDetectionAttribute(int mode, int dpcmLowData, int dpcmHighData) {
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        this.mMode = mode;
        this.mDpcmLowData = dpcmLowData;
        this.mDpcmHighData = dpcmHighData;
        Bundle attribute = new Bundle();
        attribute.putInt("dpcm_mode", this.mMode);
        if (mode == 9) {
            attribute.putInt("dpcm_lowlux", dpcmLowData);
            attribute.putInt("dpcm_highlux", dpcmHighData);
        } else if (mode == 10) {
            attribute.putInt("dpcm_lowcnt", dpcmLowData);
            attribute.putInt("dpcm_highcnt", dpcmHighData);
        }
        super.setAttribute(51, attribute);
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode == 9) {
            if (this.mDpcmLowData < -2
                    || this.mDpcmLowData > 1000000
                    || this.mDpcmHighData < -2
                    || this.mDpcmHighData > 1000000) {
                Log.d(TAG, "DPCM Data value is wrong");
                return false;
            }
        } else if (this.mMode == 10 && (this.mDpcmLowData < 0 || this.mDpcmHighData < 0)) {
            Log.d(TAG, "DPCM Data value is wrong");
            return false;
        }
        if (this.mMode < 1 || this.mMode > 12) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (this.mMode == 9 || this.mMode == 11 || this.mMode == 10 || this.mMode == 12) {
            return true;
        }
        if (this.mData > 0 && this.mData <= 127) {
            return true;
        }
        Log.d(TAG, "Data value is wrong!!");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("dpcm_mode", this.mMode);
        attribute.putInt("dpcm_data", this.mData);
        super.setAttribute(51, attribute);
    }
}
