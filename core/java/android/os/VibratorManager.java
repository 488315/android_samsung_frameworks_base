package android.os;

import android.app.ActivityThread;
import android.content.Context;
import android.util.Log;

/* loaded from: classes3.dex */
public abstract class VibratorManager {
  private static final String TAG = "VibratorManager";
  private final String mPackageName;

  public abstract void cancel();

  public abstract void cancel(int i);

  public abstract String executeVibrationDebugCommand(int i);

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
    Log.m102w(TAG, "Always-on effects aren't supported");
    return false;
  }

  public final void vibrate(CombinedVibration effect) {
    vibrate(effect, null);
  }

  public final void vibrate(CombinedVibration effect, VibrationAttributes attributes) {
    vibrate(Process.myUid(), this.mPackageName, effect, null, attributes);
  }
}
