package com.android.server.biometrics.sensors.fingerprint.aidl;

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
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.fingerprint.SemFpMainThread;

/* loaded from: classes.dex */
public class SemFpAidlLockoutHalImpl implements LockoutTracker {
    static final String ACTION_LOCKOUT_RESET = "com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET";
    static final String KEY_LOCKOUT_RESET_USER = "lockout_reset_user";
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final SparseIntArray mFailedAttempts;
    public final SemBiometricLockoutTracker mIntegratedLockoutTracker;
    public final SparseLongArray mRemainingLockoutTime;
    public final SparseBooleanArray mTimedLockoutCleared;

    public interface LockoutResetCallback {
        void onLockoutReset(int i);
    }

    public SemFpAidlLockoutHalImpl(Context context, LockoutResetCallback lockoutResetCallback) {
        this(context, SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT ? SemBiometricLockoutTracker.get() : null, lockoutResetCallback);
    }

    public SemFpAidlLockoutHalImpl(Context context, SemBiometricLockoutTracker semBiometricLockoutTracker, final LockoutResetCallback lockoutResetCallback) {
        this.mContext = context;
        this.mFailedAttempts = new SparseIntArray();
        this.mRemainingLockoutTime = new SparseLongArray();
        this.mTimedLockoutCleared = new SparseBooleanArray();
        this.mIntegratedLockoutTracker = semBiometricLockoutTracker;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlLockoutHalImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.v("FingerprintService", "Resetting lockout: " + intent.getAction());
                if (SemFpAidlLockoutHalImpl.ACTION_LOCKOUT_RESET.equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(SemFpAidlLockoutHalImpl.KEY_LOCKOUT_RESET_USER, 0);
                    if (SemFpAidlLockoutHalImpl.this.getLockoutModeForUser(intExtra) == 1) {
                        lockoutResetCallback.onLockoutReset(intExtra);
                    }
                }
            }
        }, new IntentFilter(ACTION_LOCKOUT_RESET), "android.permission.RESET_FINGERPRINT_LOCKOUT", SemFpMainThread.get().getHandler(), 2);
    }

    public void resetFailedAttemptsForUser(boolean z, int i) {
        if (getLockoutModeForUser(i) != 0) {
            Slog.v("FingerprintService", "Reset biometric lockout for user: " + i + ", clearAttemptCounter: " + z);
        }
        if (z) {
            SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
            if (semBiometricLockoutTracker != null) {
                semBiometricLockoutTracker.resetFailedAttempts(i, 2);
            }
            this.mFailedAttempts.put(i, 0);
        }
        this.mTimedLockoutCleared.put(i, true);
        this.mRemainingLockoutTime.put(i, 0L);
        cancelLockoutResetForUser(i);
    }

    public void addFailedAttemptForUser(int i) {
        SparseIntArray sparseIntArray = this.mFailedAttempts;
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
        this.mTimedLockoutCleared.put(i, false);
        SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
        if (semBiometricLockoutTracker != null) {
            semBiometricLockoutTracker.addFailedAttempt(i, 2);
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
            int elapsedRealtime = (int) ((this.mRemainingLockoutTime.get(i, 0L) - SystemClock.elapsedRealtime()) / 1000);
            if (elapsedRealtime >= 0) {
                return elapsedRealtime;
            }
            try {
                Slog.d("FingerprintService", "remaining lockout = " + elapsedRealtime);
                return 0;
            } catch (Exception e) {
                e = e;
                i2 = elapsedRealtime;
                Slog.w("FingerprintService", "getRemainingLockoutTime: " + e.getMessage());
                return i2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public int getLockoutModeForUser(int i) {
        int i2;
        SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
        if ((semBiometricLockoutTracker == null || semBiometricLockoutTracker.getFailedAttempts(i) < 20) && (i2 = this.mFailedAttempts.get(i, 0)) < 20) {
            return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
        }
        return 2;
    }

    public final void cancelLockoutResetForUser(int i) {
        this.mAlarmManager.cancel(getLockoutResetIntentForUser(i));
    }

    public final void scheduleLockoutResetForUser(int i) {
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + 30000, getLockoutResetIntentForUser(i));
    }

    public final PendingIntent getLockoutResetIntentForUser(int i) {
        return PendingIntent.getBroadcast(this.mContext, i, new Intent(ACTION_LOCKOUT_RESET).putExtra(KEY_LOCKOUT_RESET_USER, i), 201326592);
    }
}
