package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDevicePhysicalContextMonitorAttribute extends SContextAttribute {
    private static int DEVICE_PHYSICAL_CONTEXT_MONITOR_DATA = 1;
    private static int DEVICE_PHYSICAL_CONTEXT_MONITOR_MODE = 2;
    private static final String TAG = "SContextDevicePhysicalContextMonitorAttribute";
    private int mData;
    private int mMode;

    SContextDevicePhysicalContextMonitorAttribute() {
        this.mMode = DEVICE_PHYSICAL_CONTEXT_MONITOR_MODE;
        this.mData = DEVICE_PHYSICAL_CONTEXT_MONITOR_DATA;
        setAttribute();
    }

    public SContextDevicePhysicalContextMonitorAttribute(int i, int i2) {
        this.mMode = i;
        this.mData = i2;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode < 0) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (this.mData >= 0) {
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
