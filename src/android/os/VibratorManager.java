package android.os;

import android.app.ActivityThread;
import android.content.Context;
import android.os.vibrator.VendorVibrationSession;
import android.util.Log;
import com.samsung.android.vibrator.VibrationDebugInfo;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public abstract class VibratorManager {
    private static final String TAG = "VibratorManager";
    protected final String mPackageName;

    public abstract void cancel();

    public abstract void cancel(int i);

    public abstract String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo);

    public abstract Vibrator getDefaultVibrator();

    public abstract Vibrator getVibrator(int i);

    public abstract int[] getVibratorIds();

    public boolean hasCapabilities(int i) {
        return false;
    }

    public abstract int semGetNumberOfSupportedPatterns();

    public abstract int semGetSupportedVibrationType();

    public abstract void vibrate(int i, String str, CombinedVibration combinedVibration, String str2, VibrationAttributes vibrationAttributes);

    public VibratorManager() {
        this.mPackageName = ActivityThread.currentPackageName();
    }

    protected VibratorManager(Context context) {
        this.mPackageName = context.getOpPackageName();
    }

    public boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) {
        Log.w(TAG, "Always-on effects aren't supported");
        return false;
    }

    public final void vibrate(CombinedVibration combinedVibration) {
        vibrate(combinedVibration, null);
    }

    public final void vibrate(CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) {
        vibrate(Process.myUid(), this.mPackageName, combinedVibration, null, vibrationAttributes);
    }

    public void performHapticFeedback(int i, String str, int i2, int i3) {
        Log.w(TAG, "performHapticFeedback is not supported");
    }

    public void performHapticFeedbackForInputDevice(int i, int i2, int i3, String str, int i4, int i5) {
        Log.w(TAG, "performHapticFeedbackForInputDevice is not supported");
    }

    public void startVendorSession(int[] iArr, VibrationAttributes vibrationAttributes, String str, CancellationSignal cancellationSignal, Executor executor, VendorVibrationSession.Callback callback) {
        Log.w(TAG, "startVendorSession is not supported");
    }
}
