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

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LockoutFrameworkImpl implements LockoutTracker {
    public final AlarmManager mAlarmManager;
    public final Handler mHandler;
    public final LockoutResetCallback mLockoutResetCallback;
    public final Function mLockoutResetIntent;
    public final SparseLongArray mRemainingLockoutTime;
    public final SparseBooleanArray mTimedLockoutCleared = new SparseBooleanArray();
    public final SparseIntArray mFailedAttempts = new SparseIntArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockoutReceiver extends BroadcastReceiver {
        public LockoutReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Slog.v("LockoutFrameworkImpl", "Resetting lockout: " + intent.getAction());
            if ("com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET"
                    .equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("lockout_reset_user", 0);
                if (LockoutFrameworkImpl.this.getLockoutModeForUser(intExtra) == 1) {
                    LockoutFrameworkImpl.this.resetFailedAttemptsForUser(intExtra, false);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LockoutResetCallback {}

    public LockoutFrameworkImpl(
            Context context,
            LockoutResetCallback lockoutResetCallback,
            Function function,
            Handler handler) {
        this.mLockoutResetCallback = lockoutResetCallback;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        LockoutReceiver lockoutReceiver = new LockoutReceiver();
        Handler handler2 = handler == null ? new Handler(Looper.getMainLooper()) : handler;
        this.mHandler = handler2;
        this.mLockoutResetIntent = function;
        this.mRemainingLockoutTime = new SparseLongArray();
        context.registerReceiver(
                lockoutReceiver,
                new IntentFilter(
                        "com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET"),
                "android.permission.RESET_FINGERPRINT_LOCKOUT",
                handler2,
                2);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void addFailedAttemptForUser(final int i) {
        SparseIntArray sparseIntArray = this.mFailedAttempts;
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
        this.mTimedLockoutCleared.put(i, false);
        if (SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT) {
            SemBiometricLockoutTracker.get().addFailedAttempt(i, 2);
        }
        if (getLockoutModeForUser(i) == 1) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LockoutFrameworkImpl lockoutFrameworkImpl = LockoutFrameworkImpl.this;
                            lockoutFrameworkImpl.mAlarmManager.setExact(
                                    2,
                                    SystemClock.elapsedRealtime() + 30000,
                                    (PendingIntent)
                                            lockoutFrameworkImpl.mLockoutResetIntent.apply(
                                                    Integer.valueOf(i)));
                        }
                    });
            this.mRemainingLockoutTime.put(i, SystemClock.elapsedRealtime() + 31000);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final int getLockoutModeForUser(int i) {
        int i2;
        if ((!SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT
                        || SemBiometricLockoutTracker.get().getFailedAttempts(i) < 20)
                && (i2 = this.mFailedAttempts.get(i, 0)) < 20) {
            return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
        }
        return 2;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void resetFailedAttemptsForUser(int i, boolean z) {
        if (getLockoutModeForUser(i) != 0) {
            Slog.v(
                    "LockoutFrameworkImpl",
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
        this.mAlarmManager.cancel(
                (PendingIntent) this.mLockoutResetIntent.apply(Integer.valueOf(i)));
        HidlToAidlSensorAdapter hidlToAidlSensorAdapter =
                (HidlToAidlSensorAdapter)
                        ((HidlToAidlSensorAdapter$$ExternalSyntheticLambda0)
                                        this.mLockoutResetCallback)
                                .f$0;
        hidlToAidlSensorAdapter.mLockoutResetDispatcher.notifyLockoutResetCallbacks(
                hidlToAidlSensorAdapter.mSensorProperties.sensorId);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void setLockoutModeForUser(int i, int i2) {}
}
