package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextCarryingDetectionAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextCarryingDetectionAttribute> CREATOR = new Parcelable.Creator<SemContextCarryingDetectionAttribute>() { // from class: com.samsung.android.hardware.context.SemContextCarryingDetectionAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCarryingDetectionAttribute createFromParcel(Parcel parcel) {
            return new SemContextCarryingDetectionAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCarryingDetectionAttribute[] newArray(int i) {
            return new SemContextCarryingDetectionAttribute[i];
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

    SemContextCarryingDetectionAttribute(Parcel parcel) {
        super(parcel);
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
    }

    public SemContextCarryingDetectionAttribute(int i, int i2) {
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        this.mMode = i;
        this.mData = i2;
        setAttribute();
    }

    public SemContextCarryingDetectionAttribute(int i, int i2, int i3) {
        this.mData = 1;
        this.mMode = i;
        this.mDpcmLowData = i2;
        this.mDpcmHighData = i3;
        Bundle bundle = new Bundle();
        bundle.putInt("dpcm_mode", this.mMode);
        if (i == 9) {
            bundle.putInt("dpcm_lowlux", i2);
            bundle.putInt("dpcm_highlux", i3);
        } else if (i == 10) {
            bundle.putInt("dpcm_lowcnt", i2);
            bundle.putInt("dpcm_highcnt", i3);
        }
        super.setAttribute(51, bundle);
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i;
        int i2 = this.mMode;
        if (i2 == 9) {
            int i3 = this.mDpcmLowData;
            if (i3 < -2 || i3 > 1000000 || (i = this.mDpcmHighData) < -2 || i > 1000000) {
                Log.d(TAG, "DPCM Data value is wrong");
                return false;
            }
        } else if (i2 == 10 && (this.mDpcmLowData < 0 || this.mDpcmHighData < 0)) {
            Log.d(TAG, "DPCM Data value is wrong");
            return false;
        }
        if (i2 < 1 || i2 > 12) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (i2 == 9 || i2 == 11 || i2 == 10 || i2 == 12) {
            return true;
        }
        int i4 = this.mData;
        if (i4 > 0 && i4 <= 127) {
            return true;
        }
        Log.d(TAG, "Data value is wrong!!");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("dpcm_mode", this.mMode);
        bundle.putInt("dpcm_data", this.mData);
        super.setAttribute(51, bundle);
    }
}
