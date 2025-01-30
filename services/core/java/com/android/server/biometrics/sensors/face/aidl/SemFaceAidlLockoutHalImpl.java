package com.android.server.biometrics.sensors.face.aidl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricLockoutTracker;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.face.SemFaceMainThread;

/* loaded from: classes.dex */
public class SemFaceAidlLockoutHalImpl implements LockoutTracker {
  public final AlarmManager mAlarmManager;
  public final Context mContext;
  public final SparseIntArray mFailedAttempts;
  public final SemBiometricLockoutTracker mIntegratedLockoutTracker;
  public final SparseLongArray mRemainingLockoutTime;
  public final SparseBooleanArray mTimedLockoutCleared;

  public interface LockoutResetCallback {
    void onLockoutReset(int i);
  }

  public SemFaceAidlLockoutHalImpl(Context context, LockoutResetCallback lockoutResetCallback) {
    this(
        context,
        SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT ? SemBiometricLockoutTracker.get() : null,
        lockoutResetCallback);
  }

  public SemFaceAidlLockoutHalImpl(
      Context context,
      SemBiometricLockoutTracker semBiometricLockoutTracker,
      final LockoutResetCallback lockoutResetCallback) {
    this.mContext = context;
    this.mFailedAttempts = new SparseIntArray();
    this.mRemainingLockoutTime = new SparseLongArray();
    this.mTimedLockoutCleared = new SparseBooleanArray();
    this.mIntegratedLockoutTracker = semBiometricLockoutTracker;
    this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
    context.registerReceiver(
        new BroadcastReceiver() { // from class:
                                  // com.android.server.biometrics.sensors.face.aidl.SemFaceAidlLockoutHalImpl.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            Slog.v("FaceService.lockout", "Resetting lockout: " + intent.getAction());
            if ("com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET"
                .equals(intent.getAction())) {
              int intExtra = intent.getIntExtra("lockout_reset_user", 0);
              if (SemFaceAidlLockoutHalImpl.this.getLockoutModeForUser(intExtra) == 1) {
                lockoutResetCallback.onLockoutReset(intExtra);
              }
            }
          }
        },
        new IntentFilter("com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET"),
        "com.samsung.android.bio.face.permission.RESET_FACE_LOCKOUT",
        SemFaceMainThread.get().getHandler(),
        2);
  }

  public void resetFailedAttemptsForUser(boolean z, int i) {
    if (getLockoutModeForUser(i) != 0) {
      Slog.v(
          "FaceService.lockout",
          "Reset biometric lockout for user: " + i + ", clearAttemptCounter: " + z);
    }
    if (z) {
      SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
      if (semBiometricLockoutTracker != null) {
        semBiometricLockoutTracker.resetFailedAttempts(i, 8);
      }
      this.mFailedAttempts.put(i, 0);
    }
    this.mTimedLockoutCleared.put(i, true);
    this.mRemainingLockoutTime.put(i, 0L);
    cancelLockoutResetForUser(i);
  }

  public void addFailedAttemptForUser(int i) {
    int i2 = this.mFailedAttempts.get(i, 0) + 1;
    if (Utils.DEBUG) {
      Slog.v("FaceService.lockout", "addFailedAttemptForUser failedAttempts : " + i2);
    }
    this.mFailedAttempts.put(i, i2);
    this.mTimedLockoutCleared.put(i, false);
    SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
    if (semBiometricLockoutTracker != null) {
      semBiometricLockoutTracker.addFailedAttempt(i, 8);
    }
    if (getLockoutModeForUser(i) == 1) {
      scheduleLockoutResetForUser(i);
      this.mRemainingLockoutTime.put(i, SystemClock.elapsedRealtime() + 30000 + 1000);
    }
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
        Slog.d("FaceService.lockout", "remaining lockout = " + elapsedRealtime);
        return 0;
      } catch (Exception e) {
        e = e;
        i2 = elapsedRealtime;
        Slog.w("FaceService.lockout", "getRemainingLockoutTime: " + e.getMessage());
        return i2;
      }
    } catch (Exception e2) {
      e = e2;
    }
  }

  @Override // com.android.server.biometrics.sensors.LockoutTracker
  public int getLockoutModeForUser(int i) {
    SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
    if (semBiometricLockoutTracker != null
        && semBiometricLockoutTracker.getFailedAttempts(i) >= 20) {
      return 2;
    }
    int i2 = this.mFailedAttempts.get(i, 0);
    if (Utils.DEBUG) {
      Slog.v("FaceService.lockout", "getLockoutModeForUser failedAttempts : " + i2);
    }
    if (i2 >= 20) {
      return 2;
    }
    return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
  }

  public final void cancelLockoutResetForUser(int i) {
    this.mAlarmManager.cancel(getLockoutResetIntentForUser(i));
  }

  public final void scheduleLockoutResetForUser(int i) {
    this.mAlarmManager.setExact(
        2, SystemClock.elapsedRealtime() + 30000, getLockoutResetIntentForUser(i));
  }

  public final PendingIntent getLockoutResetIntentForUser(int i) {
    return PendingIntent.getBroadcast(
        this.mContext,
        i,
        new Intent("com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET")
            .putExtra("lockout_reset_user", i),
        201326592);
  }
}
