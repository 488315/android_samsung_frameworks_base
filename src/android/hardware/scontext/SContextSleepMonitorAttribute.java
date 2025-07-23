package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;
import com.android.internal.os.BinderCallsStats;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSleepMonitorAttribute extends SContextAttribute {
    private static final String TAG = "SContextSleepMonitorAttribute";
    private int mSamplingInterval;
    private int mSensibility;

    SContextSleepMonitorAttribute() {
        this.mSensibility = 80;
        this.mSamplingInterval = 100;
        setAttribute();
    }

    public SContextSleepMonitorAttribute(int i, int i2) {
        this.mSensibility = i;
        this.mSamplingInterval = i2;
        setAttribute();
    }

    public int getSensibility() {
        return this.mSensibility;
    }

    public int getSamplingInterval() {
        return this.mSamplingInterval;
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mSensibility < 0) {
            Log.e(TAG, "The sensibility is wrong.");
            return false;
        }
        if (this.mSamplingInterval >= 0) {
            return true;
        }
        Log.e(TAG, "The sampling interval is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("sensibility", this.mSensibility);
        bundle.putInt(BinderCallsStats.SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mSamplingInterval);
        super.setAttribute(29, bundle);
    }
}
