package com.android.server.biometrics.sensors.face.hidl;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricLockoutTracker;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.LockoutTracker;

/* loaded from: classes.dex */
public class SemLockoutFrameworkImpl implements LockoutTracker {
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final SparseIntArray mLockoutFailedAttempts = new SparseIntArray();
    public final SparseLongArray mLockoutPreviousAttemptTime = new SparseLongArray();
    public final LockoutReceiver mLockoutReceiver;
    public final LockoutResetCallback mLockoutResetCallback;
    public long mLockoutRunningTime;

    public interface LockoutResetCallback {
        void onLockoutReset(int i);
    }

    public final class LockoutReceiver extends BroadcastReceiver {
        public LockoutReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.v("SemLockoutFrameworkImpl", "Resetting lockout: " + intent.getAction());
            if ("com.android.server.biometrics.face.ACTION_LOCKOUT_RESET".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("lockout_reset_user", 0);
                if (SemLockoutFrameworkImpl.this.getLockoutModeForUser(intExtra) != 2) {
                    SemLockoutFrameworkImpl.this.resetFailedAttemptsForUser(false, intExtra);
                }
            }
        }
    }

    public SemLockoutFrameworkImpl(Context context, LockoutResetCallback lockoutResetCallback) {
        this.mContext = context;
        this.mLockoutResetCallback = lockoutResetCallback;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        LockoutReceiver lockoutReceiver = new LockoutReceiver();
        this.mLockoutReceiver = lockoutReceiver;
        context.registerReceiver(lockoutReceiver, new IntentFilter("com.android.server.biometrics.face.ACTION_LOCKOUT_RESET"), "com.samsung.android.bio.face.permission.RESET_FACE_LOCKOUT", null);
    }

    public void resetFailedAttemptsForUser(boolean z, int i) {
        if (Utils.DEBUG) {
            Slog.i("SemLockoutFrameworkImpl", "resetFailedAttempts : " + z + ", user : " + i);
        }
        if (z) {
            if (SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT) {
                SemBiometricLockoutTracker.get().resetFailedAttempts(i, 8);
            }
            this.mLockoutFailedAttempts.put(i, 0);
        }
        this.mLockoutPreviousAttemptTime.put(i, 0L);
        cancelLockoutResetForUser(i);
        this.mLockoutResetCallback.onLockoutReset(i);
    }

    public void resetFailedAttempts(boolean z) {
        resetFailedAttemptsForUser(z, ActivityManager.getCurrentUser());
    }

    public void addFailedAttemptForUser(int i) {
        int i2 = this.mLockoutFailedAttempts.get(i, 0) + 1;
        this.mLockoutFailedAttempts.put(i, i2);
        this.mLockoutPreviousAttemptTime.put(i, SystemClock.elapsedRealtime());
        if (SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT) {
            SemBiometricLockoutTracker.get().addFailedAttempt(i, 8);
        }
        if (getLockoutMode(i) != 0) {
            scheduleLockoutResetForUser(i);
        }
        if (Utils.DEBUG) {
            Slog.i("SemLockoutFrameworkImpl", "addFailedAttemptForUser : " + i2 + ", user : " + i);
        }
    }

    public int getRemainingLockoutTime(int i) {
        int lockoutMode = getLockoutMode(i);
        if (lockoutMode == 2) {
            return -1;
        }
        if (lockoutMode == 1) {
            return (int) (this.mLockoutRunningTime / 1000);
        }
        return 0;
    }

    public final int getLockoutMode(int i) {
        if (SemBiometricFeature.FEATURE_INTEGRATED_LOCKOUT && SemBiometricLockoutTracker.get().getFailedAttempts(i) >= 20) {
            Slog.i("SemLockoutFrameworkImpl", "LO : integrated");
            return 2;
        }
        long j = this.mLockoutPreviousAttemptTime.get(i, 0L);
        int i2 = this.mLockoutFailedAttempts.get(i, 0);
        long elapsedRealtime = (j + 30000) - SystemClock.elapsedRealtime();
        this.mLockoutRunningTime = elapsedRealtime;
        int i3 = i2 < 20 ? ((i2 == 5 || i2 == 10 || i2 == 15) && elapsedRealtime > 0) ? 1 : 0 : 2;
        Slog.i("SemLockoutFrameworkImpl", "LO : " + i3);
        return i3;
    }

    public final void cancelLockoutResetForUser(int i) {
        this.mAlarmManager.cancel(getLockoutResetIntentForUser(i));
    }

    public final void scheduleLockoutResetForUser(int i) {
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + 30000, getLockoutResetIntentForUser(i));
    }

    public final PendingIntent getLockoutResetIntentForUser(int i) {
        return PendingIntent.getBroadcast(this.mContext, i, new Intent("com.android.server.biometrics.face.ACTION_LOCKOUT_RESET").putExtra("lockout_reset_user", i), 201326592);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public int getLockoutModeForUser(int i) {
        return getLockoutMode(i);
    }
}
