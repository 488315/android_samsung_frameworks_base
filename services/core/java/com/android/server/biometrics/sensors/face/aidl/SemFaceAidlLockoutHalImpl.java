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

import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBiometricLockoutTracker;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.LockoutTracker;

public final class SemFaceAidlLockoutHalImpl implements LockoutTracker {
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final SemBiometricLockoutTracker mIntegratedLockoutTracker;
    public final SparseIntArray mFailedAttempts = new SparseIntArray();
    public final SparseLongArray mRemainingLockoutTime = new SparseLongArray();
    public final SparseBooleanArray mTimedLockoutCleared = new SparseBooleanArray();

    public interface LockoutResetCallback {}

    public SemFaceAidlLockoutHalImpl(
            Context context,
            SemBiometricLockoutTracker semBiometricLockoutTracker,
            final LockoutResetCallback lockoutResetCallback) {
        this.mContext = context;
        this.mIntegratedLockoutTracker = semBiometricLockoutTracker;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        context.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.biometrics.sensors.face.aidl.SemFaceAidlLockoutHalImpl.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        AidlSession aidlSession;
                        Slog.v("FaceService.lockout", "Resetting lockout: " + intent.getAction());
                        if ("com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET"
                                .equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("lockout_reset_user", 0);
                            if (SemFaceAidlLockoutHalImpl.this.getLockoutModeForUser(intExtra) == 1
                                    && (aidlSession =
                                                    ((Sensor)
                                                                    ((Sensor$$ExternalSyntheticLambda1)
                                                                                    lockoutResetCallback)
                                                                            .f$0)
                                                            .mCurrentSession)
                                            != null
                                    && aidlSession.mUserId == intExtra) {
                                aidlSession.mAidlResponseHandler.onLockoutCleared();
                            }
                        }
                    }
                },
                new IntentFilter("com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET"),
                "com.samsung.android.bio.face.permission.RESET_FACE_LOCKOUT",
                BiometricHandlerProvider.sBiometricHandlerProvider.getFaceHandler(),
                2);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void addFailedAttemptForUser(int i) {
        int i2 = this.mFailedAttempts.get(i, 0) + 1;
        if (Utils.DEBUG) {
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i2, "addFailedAttemptForUser failedAttempts : ", "FaceService.lockout");
        }
        this.mFailedAttempts.put(i, i2);
        this.mTimedLockoutCleared.put(i, false);
        SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
        if (semBiometricLockoutTracker != null) {
            semBiometricLockoutTracker.addFailedAttempt(i, 8);
        }
        if (getLockoutModeForUser(i) == 1) {
            this.mAlarmManager.setExact(
                    2,
                    SystemClock.elapsedRealtime() + 30000,
                    PendingIntent.getBroadcast(
                            this.mContext,
                            i,
                            new Intent(
                                            "com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET")
                                    .putExtra("lockout_reset_user", i),
                            201326592));
            this.mRemainingLockoutTime.put(i, SystemClock.elapsedRealtime() + 31000);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final int getLockoutModeForUser(int i) {
        SemBiometricLockoutTracker semBiometricLockoutTracker = this.mIntegratedLockoutTracker;
        if (semBiometricLockoutTracker != null
                && semBiometricLockoutTracker.getFailedAttempts(i) >= 20) {
            return 2;
        }
        int i2 = this.mFailedAttempts.get(i, 0);
        if (Utils.DEBUG) {
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i2, "getLockoutModeForUser failedAttempts : ", "FaceService.lockout");
        }
        if (i2 >= 20) {
            return 2;
        }
        return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void resetFailedAttemptsForUser(int i, boolean z) {
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
        this.mAlarmManager.cancel(
                PendingIntent.getBroadcast(
                        this.mContext,
                        i,
                        new Intent(
                                        "com.android.server.biometrics.sensors.face.ACTION_LOCKOUT_RESET")
                                .putExtra("lockout_reset_user", i),
                        201326592));
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void setLockoutModeForUser(int i, int i2) {}
}
