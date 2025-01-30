package android.hardware.scontext;

import android.p009os.Bundle;
import android.util.Log;
import com.android.internal.p029os.BinderCallsStats;

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

    public SContextSleepMonitorAttribute(int sensibility, int samplingInterval) {
        this.mSensibility = 80;
        this.mSamplingInterval = 100;
        this.mSensibility = sensibility;
        this.mSamplingInterval = samplingInterval;
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
            Log.m96e(TAG, "The sensibility is wrong.");
            return false;
        }
        if (this.mSamplingInterval < 0) {
            Log.m96e(TAG, "The sampling interval is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("sensibility", this.mSensibility);
        attribute.putInt(BinderCallsStats.SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mSamplingInterval);
        super.setAttribute(29, attribute);
    }
}
