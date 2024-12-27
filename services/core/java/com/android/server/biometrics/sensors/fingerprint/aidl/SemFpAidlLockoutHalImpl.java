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
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBiometricLockoutTracker;
import com.android.server.biometrics.sensors.LockoutTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpAidlLockoutHalImpl implements LockoutTracker {
    static final String ACTION_LOCKOUT_RESET = "com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET";
    static final String KEY_LOCKOUT_RESET_USER = "lockout_reset_user";
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final SemBiometricLockoutTracker mIntegratedLockoutTracker;
    public final SparseIntArray mFailedAttempts = new SparseIntArray();
    public final SparseLongArray mRemainingLockoutTime = new SparseLongArray();
    public final SparseBooleanArray mTimedLockoutCleared = new SparseBooleanArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LockoutResetCallback {
    }

    public SemFpAidlLockoutHalImpl(Context context, SemBiometricLockoutTracker semBiometricLockoutTracker, final LockoutResetCallback lockoutResetCallback) {
        this.mContext = context;
        this.mIntegratedLockoutTracker = semBiometricLockoutTracker;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlLockoutHalImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Slog.v("FingerprintService", "Resetting lockout: " + intent.getAction());
                if (SemFpAidlLockoutHalImpl.ACTION_LOCKOUT_RESET.equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(SemFpAidlLockoutHalImpl.KEY_LOCKOUT_RESET_USER, 0);
                    if (SemFpAidlLockoutHalImpl.this.getLockoutModeForUser(intExtra) == 1) {
                        ((Sensor) ((Sensor$$ExternalSyntheticLambda0) lockoutResetCallback).f$0).handleOnLockoutReset(intExtra);
                    }
                }
            }
        }, new IntentFilter(ACTION_LOCKOUT_RESET), "android.permission.RESET_FINGERPRINT_LOCKOUT", BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler(), 2);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void addFailedAttemptForUser(int i) {
        SparseIntArray sparseIntArray = this.mFailedAttempts;
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
        this.mTimedLockoutCleared.put(i, false);
        SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
        if (semBiometricLockoutTracker != null) {
            semBiometricLockoutTracker.addFailedAttempt(i, 2);
        }
        if (getLockoutModeForUser(i) == 1) {
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + 30000, PendingIntent.getBroadcast(this.mContext, i, new Intent(ACTION_LOCKOUT_RESET).putExtra(KEY_LOCKOUT_RESET_USER, i), 201326592));
            this.mRemainingLockoutTime.put(i, SystemClock.elapsedRealtime() + 31000);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final int getLockoutModeForUser(int i) {
        int i2;
        SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
        if ((semBiometricLockoutTracker == null || semBiometricLockoutTracker.getFailedAttempts(i) < 20) && (i2 = this.mFailedAttempts.get(i, 0)) < 20) {
            return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
        }
        return 2;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void resetFailedAttemptsForUser(int i, boolean z) {
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
        this.mAlarmManager.cancel(PendingIntent.getBroadcast(this.mContext, i, new Intent(ACTION_LOCKOUT_RESET).putExtra(KEY_LOCKOUT_RESET_USER, i), 201326592));
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void setLockoutModeForUser(int i, int i2) {
    }
}
