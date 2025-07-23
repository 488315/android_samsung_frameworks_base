package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextAutoBrightnessAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextAutoBrightnessAttribute> CREATOR = new Parcelable.Creator<SemContextAutoBrightnessAttribute>() { // from class: com.samsung.android.hardware.context.SemContextAutoBrightnessAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute createFromParcel(Parcel parcel) {
            return new SemContextAutoBrightnessAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute[] newArray(int i) {
            return new SemContextAutoBrightnessAttribute[i];
        }
    };
    private static final int MODE_CONFIGURATION = 1;
    private static final int MODE_DEVICE_MODE = 0;
    private static final String TAG = "SemContextAutoBrightnessAttribute";
    private int mDeviceMode;
    private byte[] mLuminanceTable;
    private int mMode;

    SemContextAutoBrightnessAttribute() {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        setAttribute();
    }

    SemContextAutoBrightnessAttribute(Parcel parcel) {
        super(parcel);
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
    }

    public SemContextAutoBrightnessAttribute(int i) {
        this.mLuminanceTable = null;
        this.mDeviceMode = i;
        this.mMode = 0;
        setAttribute();
    }

    public SemContextAutoBrightnessAttribute(byte[] bArr) {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = 1;
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length];
            this.mLuminanceTable = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            setAttribute();
            return;
        }
        Log.e(TAG, "The luminanceTable is wrong.");
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mMode;
        if (i == 0) {
            int i2 = this.mDeviceMode;
            if (i2 < 0 || i2 > 2) {
                Log.e(TAG, "The device mode is wrong.");
                return false;
            }
        } else if (i == 1 && this.mLuminanceTable == null) {
            Log.e(TAG, "The luminance configuration data is null.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", this.mMode);
        int i = this.mMode;
        if (i == 1) {
            bundle.putByteArray("luminance_config_data", this.mLuminanceTable);
        } else if (i == 0) {
            bundle.putInt("device_mode", this.mDeviceMode);
        }
        super.setAttribute(39, bundle);
    }
}
