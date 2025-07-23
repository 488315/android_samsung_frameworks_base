package android.hardware.scontext;

import android.os.Bundle;
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

    public SContextAutoRotationAttribute(int i) {
        this.mDeviceType = i;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDeviceType;
        if (i == 0 || i == 2 || i == 4) {
            return true;
        }
        Log.e(TAG, "The device type is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("device_type", this.mDeviceType);
        super.setAttribute(6, bundle);
    }
}
