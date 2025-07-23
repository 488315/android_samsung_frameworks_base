package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAutoBrightnessAttribute extends SContextAttribute {
    private static int MODE_CONFIGURATION = 1;
    private static int MODE_DEVICE_MODE = 0;
    private static final String TAG = "SContextAutoBrightnessAttribute";
    private int mDeviceMode;
    private byte[] mLuminanceTable;
    private int mMode;

    SContextAutoBrightnessAttribute() {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        setAttribute();
    }

    public SContextAutoBrightnessAttribute(int i) {
        this.mLuminanceTable = null;
        this.mDeviceMode = i;
        this.mMode = MODE_DEVICE_MODE;
        setAttribute();
    }

    public SContextAutoBrightnessAttribute(byte[] bArr) {
        this.mDeviceMode = 0;
        this.mLuminanceTable = bArr;
        this.mMode = MODE_CONFIGURATION;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mMode;
        if (i == MODE_DEVICE_MODE) {
            int i2 = this.mDeviceMode;
            if (i2 >= 0 && i2 <= 2) {
                return true;
            }
            Log.e(TAG, "The device mode is wrong.");
            return false;
        }
        if (i != MODE_CONFIGURATION || this.mLuminanceTable != null) {
            return true;
        }
        Log.e(TAG, "The luminance configration data is null.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", this.mMode);
        int i = this.mMode;
        if (i == MODE_CONFIGURATION) {
            bundle.putByteArray("luminance_config_data", this.mLuminanceTable);
        } else if (i == MODE_DEVICE_MODE) {
            bundle.putInt("device_mode", this.mDeviceMode);
        }
        super.setAttribute(39, bundle);
    }
}
