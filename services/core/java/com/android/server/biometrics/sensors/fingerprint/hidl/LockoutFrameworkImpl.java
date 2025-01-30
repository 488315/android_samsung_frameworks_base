package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricLockoutTracker;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.fingerprint.SemFpMainThread;

/* loaded from: classes.dex */
public class LockoutFrameworkImpl implements LockoutTracker {
  public final AlarmManager mAlarmManager;
  public final Context mContext;
  public final Handler mHandler;
  public final LockoutReceiver mLockoutReceiver;
  public final LockoutResetCallback mLockoutResetCallback;
  public final SparseLongArray mRemainingLockoutTime;
  public final SparseBooleanArray mTimedLockoutCleared = new SparseBooleanArray();
  public final SparseIntArray mFailedAttempts = new SparseIntArray();

  public interface LockoutResetCallback {
    void onLockoutReset(int i);
  }

  public final class LockoutReceiver extends BroadcastReceiver {
    public LockoutReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      Slog.v("LockoutTracker", "Resetting lockout: " + intent.getAction());
      if ("com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET"
          .equals(intent.getAction())) {
        int intExtra = intent.getIntExtra("lockout_reset_user", 0);
        if (LockoutFrameworkImpl.this.getLockoutModeForUser(intExtra) == 1) {
          LockoutFrameworkImpl.this.resetFailedAttemptsForUser(false, intExtra);
        }
      }
    }
  }

  public LockoutFrameworkImpl(Context context, LockoutResetCallback lockoutResetCallback) {
    this.mContext = context;
    this.mLockoutResetCallback = lockoutResetCallback;
    this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
    LockoutReceiver lockoutReceiver = new LockoutReceiver();
    this.mLockoutReceiver = lockoutReceiver;
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mRemainingLockoutTime = new SparseLongArray();
    context.registerReceiver(
        lockoutReceiver,
        new IntentFilter("com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET"),
        "android.permission.RESET_FINGERPRINT_LOCKOUT",
        SemFpMainThread.get().getHandler(),
        2);
  }

  public void resetFailedAttemptsForUser(boolean z, int i) {
    if (getLockoutModeForUser(i) != 0) {
      Slog.v(
          "LockoutTracker",
          "Reset biometric lockout for user: " + i + ", clearAttemptCounter: " + z);
    }
    if (z) {
      if (SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT) {
        SemBiometricLockoutTracker.get().resetFailedAttempts(i, 2);
      }
      this.mFailedAttempts.put(i, 0);
    }
    this.mTimedLockoutCleared.put(i, true);
    this.mRemainingLockoutTime.put(i, 0L);
    cancelLockoutResetForUser(i);
    this.mLockoutResetCallback.onLockoutReset(i);
  }

  public void addFailedAttemptForUser(int i) {
    SparseIntArray sparseIntArray = this.mFailedAttempts;
    sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
    this.mTimedLockoutCleared.put(i, false);
    if (SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT) {
      SemBiometricLockoutTracker.get().addFailedAttempt(i, 2);
    }
    if (getLockoutModeForUser(i) == 1) {
      scheduleLockoutResetForUser(i);
      this.mRemainingLockoutTime.put(i, SystemClock.elapsedRealtime() + 30000 + 1000);
    }
  }

  @Override // com.android.server.biometrics.sensors.LockoutTracker
  public int getLockoutModeForUser(int i) {
    int i2;
    if ((!SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT
            || SemBiometricLockoutTracker.get().getFailedAttempts(i) < 20)
        && (i2 = this.mFailedAttempts.get(i, 0)) < 20) {
      return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
    }
    return 2;
  }

  public int getRemainingLockoutTime(int i) {
    int i2 = 0;
    try {
      int lockoutModeForUser = getLockoutModeForUser(i);
      if (lockoutModeForUser == 2) {
        return -1;
      }
      if (lockoutModeForUser == 0 || lockoutModeForUser != 1) {
        return 0;
      }
      int elapsedRealtime =
          (int) ((this.mRemainingLockoutTime.get(i, 0L) - SystemClock.elapsedRealtime()) / 1000);
      if (elapsedRealtime >= 0) {
        return elapsedRealtime;
      }
      try {
        Slog.d("LockoutTracker", "remaining lockout = " + elapsedRealtime);
        return 0;
      } catch (Exception e) {
        e = e;
        i2 = elapsedRealtime;
        Slog.w("LockoutTracker", "getRemainingLockoutTime: " + e.getMessage());
        return i2;
      }
    } catch (Exception e2) {
      e = e2;
    }
  }

  public final void cancelLockoutResetForUser(int i) {
    this.mAlarmManager.cancel(getLockoutResetIntentForUser(i));
  }

  public final void scheduleLockoutResetForUser(final int i) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            LockoutFrameworkImpl.this.lambda$scheduleLockoutResetForUser$0(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleLockoutResetForUser$0(int i) {
    this.mAlarmManager.setExact(
        2, SystemClock.elapsedRealtime() + 30000, getLockoutResetIntentForUser(i));
  }

  public final PendingIntent getLockoutResetIntentForUser(int i) {
    return PendingIntent.getBroadcast(
        this.mContext,
        i,
        new Intent("com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET")
            .putExtra("lockout_reset_user", i),
        201326592);
  }
}
