package android.os;

import android.app.ActivityThread;
import android.content.Context;
import android.util.Log;

import com.samsung.android.vibrator.VibrationDebugInfo;

public abstract class VibratorManager {
    private static final String TAG = "VibratorManager";
    protected final String mPackageName;

    public abstract void cancel();

    public abstract void cancel(int i);

    public abstract String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo);

    public abstract Vibrator getDefaultVibrator();

    public abstract Vibrator getVibrator(int i);

    public abstract int[] getVibratorIds();

    public abstract int semGetNumberOfSupportedPatterns();

    public abstract int semGetSupportedVibrationType();

    public abstract void vibrate(
            int i,
            String str,
            CombinedVibration combinedVibration,
            String str2,
            VibrationAttributes vibrationAttributes);

    public VibratorManager() {
        this.mPackageName = ActivityThread.currentPackageName();
    }

    protected VibratorManager(Context context) {
        this.mPackageName = context.getOpPackageName();
    }

    public boolean setAlwaysOnEffect(
            int uid,
            String opPkg,
            int alwaysOnId,
            CombinedVibration effect,
            VibrationAttributes attributes) {
        Log.w(TAG, "Always-on effects aren't supported");
        return false;
    }

    public final void vibrate(CombinedVibration effect) {
        vibrate(effect, null);
    }

    public final void vibrate(CombinedVibration effect, VibrationAttributes attributes) {
        vibrate(Process.myUid(), this.mPackageName, effect, null, attributes);
    }

    public void performHapticFeedback(
            int constant, boolean always, String reason, boolean fromIme) {
        Log.w(TAG, "performHapticFeedback is not supported");
    }
}
