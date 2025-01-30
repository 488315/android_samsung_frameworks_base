package android.hardware.scontext;

import android.p009os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAutoRotationAttribute extends SContextAttribute {
    private static final String TAG = "SContextAutoRotationAttribute";
    private int mDeviceType;

    SContextAutoRotationAttribute() {
        this.mDeviceType = 0;
        setAttribute();
    }

    public SContextAutoRotationAttribute(int deviceType) {
        this.mDeviceType = 0;
        this.mDeviceType = deviceType;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDeviceType;
        if (i != 0 && i != 2 && i != 4) {
            Log.m96e(TAG, "The device type is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("device_type", this.mDeviceType);
        super.setAttribute(6, attribute);
    }
}
