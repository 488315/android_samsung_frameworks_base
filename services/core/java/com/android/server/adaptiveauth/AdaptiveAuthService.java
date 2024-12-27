package com.android.server.adaptiveauth;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.biometrics.AuthenticationStateListener;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.events.AuthenticationAcquiredInfo;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationFailedInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.events.AuthenticationSucceededInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockSettingsStateListener;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.WindowManagerInternal;

import java.util.Objects;

public final class AdaptiveAuthService extends SystemService {
    public static final boolean DEBUG;
    static final int MAX_ALLOWED_FAILED_AUTH_ATTEMPTS = 5;
    public final AnonymousClass2 mAuthenticationStateListener;
    public final BiometricManager mBiometricManager;
    final SparseIntArray mFailedAttemptsForUser;
    public final AnonymousClass3 mHandler;
    public final KeyguardManager mKeyguardManager;
    public final SparseLongArray mLastLockedTimestamp;
    public final LockPatternUtils mLockPatternUtils;
    public final LockSettingsInternal mLockSettings;
    public final AnonymousClass1 mLockSettingsStateListener;
    public final PowerManager mPowerManager;
    public final UserManagerInternal mUserManager;
    public final WindowManagerInternal mWindowManager;

    static {
        DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("AdaptiveAuthService", 3);
    }

    public AdaptiveAuthService(Context context) {
        this(context, new LockPatternUtils(context));
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.adaptiveauth.AdaptiveAuthService$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.adaptiveauth.AdaptiveAuthService$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.adaptiveauth.AdaptiveAuthService$3] */
    public AdaptiveAuthService(Context context, LockPatternUtils lockPatternUtils) {
        super(context);
        this.mFailedAttemptsForUser = new SparseIntArray();
        this.mLastLockedTimestamp = new SparseLongArray();
        this.mLockSettingsStateListener =
                new LockSettingsStateListener() { // from class:
                                                  // com.android.server.adaptiveauth.AdaptiveAuthService.1
                    public final void onAuthenticationFailed(int i) {
                        Slog.i(
                                "AdaptiveAuthService",
                                "LockSettingsStateListener#onAuthenticationFailed");
                        obtainMessage(1, 0, i).sendToTarget();
                    }

                    public final void onAuthenticationSucceeded(int i) {
                        if (AdaptiveAuthService.DEBUG) {
                            Slog.d(
                                    "AdaptiveAuthService",
                                    "LockSettingsStateListener#onAuthenticationSucceeded");
                        }
                        obtainMessage(1, 1, i).sendToTarget();
                    }
                };
        this.mAuthenticationStateListener =
                new AuthenticationStateListener
                        .Stub() { // from class:
                                  // com.android.server.adaptiveauth.AdaptiveAuthService.2
                    public final void onAuthenticationAcquired(
                            AuthenticationAcquiredInfo authenticationAcquiredInfo) {}

                    public final void onAuthenticationError(
                            AuthenticationErrorInfo authenticationErrorInfo) {}

                    public final void onAuthenticationFailed(
                            AuthenticationFailedInfo authenticationFailedInfo) {
                        Slog.i(
                                "AdaptiveAuthService",
                                "AuthenticationStateListener#onAuthenticationFailed");
                        obtainMessage(2, 0, authenticationFailedInfo.getUserId()).sendToTarget();
                    }

                    public final void onAuthenticationHelp(
                            AuthenticationHelpInfo authenticationHelpInfo) {}

                    public final void onAuthenticationStarted(
                            AuthenticationStartedInfo authenticationStartedInfo) {}

                    public final void onAuthenticationStopped(
                            AuthenticationStoppedInfo authenticationStoppedInfo) {}

                    public final void onAuthenticationSucceeded(
                            AuthenticationSucceededInfo authenticationSucceededInfo) {
                        if (AdaptiveAuthService.DEBUG) {
                            Slog.d(
                                    "AdaptiveAuthService",
                                    "AuthenticationStateListener#onAuthenticationSucceeded");
                        }
                        obtainMessage(2, 1, authenticationSucceededInfo.getUserId()).sendToTarget();
                    }
                };
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.android.server.adaptiveauth.AdaptiveAuthService.3
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i = message.what;
                        AdaptiveAuthService adaptiveAuthService = AdaptiveAuthService.this;
                        if (i == 1) {
                            boolean z = message.arg1 != 0;
                            int i2 = message.arg2;
                            if (AdaptiveAuthService.DEBUG) {
                                adaptiveAuthService.getClass();
                                Slog.d(
                                        "AdaptiveAuthService",
                                        "handleReportPrimaryAuthAttempt: success="
                                                + z
                                                + ", userId="
                                                + i2);
                            }
                            adaptiveAuthService.reportAuthAttempt(0, i2, z);
                            return;
                        }
                        if (i != 2) {
                            return;
                        }
                        boolean z2 = message.arg1 != 0;
                        int i3 = message.arg2;
                        if (AdaptiveAuthService.DEBUG) {
                            adaptiveAuthService.getClass();
                            Slog.d(
                                    "AdaptiveAuthService",
                                    "handleReportBiometricAuthAttempt: success="
                                            + z2
                                            + ", userId="
                                            + i3);
                        }
                        adaptiveAuthService.reportAuthAttempt(1, i3, z2);
                    }
                };
        this.mLockPatternUtils = lockPatternUtils;
        LockSettingsInternal lockSettingsInternal =
                (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        Objects.requireNonNull(lockSettingsInternal);
        this.mLockSettings = lockSettingsInternal;
        BiometricManager biometricManager =
                (BiometricManager) context.getSystemService(BiometricManager.class);
        Objects.requireNonNull(biometricManager);
        this.mBiometricManager = biometricManager;
        KeyguardManager keyguardManager =
                (KeyguardManager) context.getSystemService(KeyguardManager.class);
        Objects.requireNonNull(keyguardManager);
        this.mKeyguardManager = keyguardManager;
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Objects.requireNonNull(powerManager);
        this.mPowerManager = powerManager;
        WindowManagerInternal windowManagerInternal =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        Objects.requireNonNull(windowManagerInternal);
        this.mWindowManager = windowManagerInternal;
        UserManagerInternal userManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        Objects.requireNonNull(userManagerInternal);
        this.mUserManager = userManagerInternal;
    }

    public void init() {
        this.mLockSettings.registerLockSettingsStateListener(this.mLockSettingsStateListener);
        this.mBiometricManager.registerAuthenticationStateListener(
                this.mAuthenticationStateListener);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            init();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {}

    public final void reportAuthAttempt(int i, int i2, boolean z) {
        int i3;
        if (getContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            return;
        }
        if (z) {
            this.mFailedAttemptsForUser.delete(i2);
            if (this.mLastLockedTimestamp.indexOfKey(i2) >= 0) {
                long j = this.mLastLockedTimestamp.get(i2);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (i != 0) {
                    i3 = i == 1 ? 2 : 0;
                } else {
                    i3 = 1;
                }
                if (DEBUG) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    "collectTimeElapsedSinceLastLockedForUser: lastLockedTime=",
                                    j,
                                    ", authTime=");
                    m.append(elapsedRealtime);
                    m.append(", unlockType=");
                    m.append(i3);
                    Slog.d("AdaptiveAuthService", m.toString());
                }
                if (j <= elapsedRealtime) {
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.ADAPTIVE_AUTH_UNLOCK_AFTER_LOCK_REPORTED,
                            j,
                            elapsedRealtime,
                            i3);
                }
                this.mLastLockedTimestamp.delete(i2);
                return;
            }
            return;
        }
        int i4 = this.mFailedAttemptsForUser.get(i2, 0) + 1;
        Slog.i(
                "AdaptiveAuthService",
                "reportAuthAttempt: numFailedAttempts=" + i4 + ", userId=" + i2);
        this.mFailedAttemptsForUser.put(i2, i4);
        if (this.mKeyguardManager.isDeviceLocked(i2) && this.mKeyguardManager.isKeyguardLocked()) {
            Slog.d(
                    "AdaptiveAuthService",
                    "Not locking the device because the device is already locked.");
            return;
        }
        if (i4 < 5) {
            Slog.d(
                    "AdaptiveAuthService",
                    "Not locking the device because the number of failed attempts is below the"
                        + " threshold.");
            return;
        }
        this.mLockPatternUtils.requireStrongAuth(512, i2);
        int profileParentId = this.mUserManager.getProfileParentId(i2);
        Slog.i(
                "AdaptiveAuthService",
                "lockDevice: userId=" + i2 + ", parentUserId=" + profileParentId);
        if (profileParentId != i2) {
            this.mLockPatternUtils.requireStrongAuth(512, profileParentId);
        }
        this.mPowerManager.goToSleep(SystemClock.uptimeMillis());
        this.mWindowManager.lockNow();
        this.mLastLockedTimestamp.put(i2, SystemClock.elapsedRealtime());
    }
}
