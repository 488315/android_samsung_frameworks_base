package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextHallSensorAttribute extends SContextAttribute {
    private static final String TAG = "SContextHallSensorAttribute";
    private int mDisplayStatus;

    SContextHallSensorAttribute() {
        this.mDisplayStatus = 0;
        setAttribute();
    }

    public SContextHallSensorAttribute(int i) {
        this.mDisplayStatus = i;
        setAttribute();
        Log.d(TAG, "constructor + " + i);
    }

    public int getDisplayStatus() {
        return this.mDisplayStatus;
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDisplayStatus;
        if (i >= 0 && i <= 2) {
            return true;
        }
        Log.e(TAG, "The display status is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("display_status", this.mDisplayStatus);
        Log.d(TAG, "hall sensor status   + " + bundle.getInt("display_status"));
        super.setAttribute(43, bundle);
    }
}
