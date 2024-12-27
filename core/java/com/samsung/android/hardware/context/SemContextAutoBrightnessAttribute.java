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
        public SemContextAutoBrightnessAttribute createFromParcel(Parcel in) {
            return new SemContextAutoBrightnessAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute[] newArray(int size) {
            return new SemContextAutoBrightnessAttribute[size];
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

    SemContextAutoBrightnessAttribute(Parcel src) {
        super(src);
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
    }

    public SemContextAutoBrightnessAttribute(int deviceMode) {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        this.mDeviceMode = deviceMode;
        this.mMode = 0;
        setAttribute();
    }

    public SemContextAutoBrightnessAttribute(byte[] luminanceTable) {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        this.mMode = 1;
        if (luminanceTable != null) {
            this.mLuminanceTable = new byte[luminanceTable.length];
            System.arraycopy(luminanceTable, 0, this.mLuminanceTable, 0, luminanceTable.length);
            setAttribute();
            return;
        }
        Log.e(TAG, "The luminanceTable is wrong.");
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode == 0) {
            if (this.mDeviceMode < 0 || this.mDeviceMode > 2) {
                Log.e(TAG, "The device mode is wrong.");
                return false;
            }
        } else if (this.mMode == 1 && this.mLuminanceTable == null) {
            Log.e(TAG, "The luminance configuration data is null.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("mode", this.mMode);
        if (this.mMode == 1) {
            attribute.putByteArray("luminance_config_data", this.mLuminanceTable);
        } else if (this.mMode == 0) {
            attribute.putInt("device_mode", this.mDeviceMode);
        }
        super.setAttribute(39, attribute);
    }
}
