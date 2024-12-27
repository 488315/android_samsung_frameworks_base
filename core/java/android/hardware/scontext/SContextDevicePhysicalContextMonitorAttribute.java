package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDevicePhysicalContextMonitorAttribute extends SContextAttribute {
    private static final String TAG = "SContextDevicePhysicalContextMonitorAttribute";
    private int mData;
    private int mMode;
    private static int DEVICE_PHYSICAL_CONTEXT_MONITOR_MODE = 2;
    private static int DEVICE_PHYSICAL_CONTEXT_MONITOR_DATA = 1;

    SContextDevicePhysicalContextMonitorAttribute() {
        this.mMode = DEVICE_PHYSICAL_CONTEXT_MONITOR_MODE;
        this.mData = DEVICE_PHYSICAL_CONTEXT_MONITOR_DATA;
        setAttribute();
    }

    public SContextDevicePhysicalContextMonitorAttribute(int mode, int data) {
        this.mMode = DEVICE_PHYSICAL_CONTEXT_MONITOR_MODE;
        this.mData = DEVICE_PHYSICAL_CONTEXT_MONITOR_DATA;
        this.mMode = mode;
        this.mData = data;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode < 0) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (this.mData < 0) {
            Log.d(TAG, "Data value is wrong!!");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("dpcm_mode", this.mMode);
        attribute.putInt("dpcm_data", this.mData);
        super.setAttribute(51, attribute);
    }
}
