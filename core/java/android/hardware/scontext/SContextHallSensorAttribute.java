package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
public class SContextHallSensorAttribute extends SContextAttribute {
    private static final String TAG = "SContextHallSensorAttribute";
    private int mDisplayStatus;

    SContextHallSensorAttribute() {
        this.mDisplayStatus = 0;
        setAttribute();
    }

    public SContextHallSensorAttribute(int displayStatus) {
        this.mDisplayStatus = 0;
        this.mDisplayStatus = displayStatus;
        setAttribute();
        Log.d(TAG, "constructor + " + displayStatus);
    }

    public int getDisplayStatus() {
        return this.mDisplayStatus;
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDisplayStatus < 0 || this.mDisplayStatus > 2) {
            Log.e(TAG, "The display status is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("display_status", this.mDisplayStatus);
        Log.d(TAG, "hall sensor status   + " + attribute.getInt("display_status"));
        super.setAttribute(43, attribute);
    }
}
