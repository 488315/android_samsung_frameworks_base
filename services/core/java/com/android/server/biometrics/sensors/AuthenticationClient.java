package com.android.server.biometrics.sensors;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.Context;
import android.hardware.biometrics.AuthenticateOptions;
import android.hardware.biometrics.BiometricManager;
import android.os.IBinder;
import android.util.Slog;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.function.Supplier;

public abstract class AuthenticationClient extends AcquisitionClient
        implements AuthenticationConsumer {
    public final ActivityManager mActivityManager;
    public final ActivityTaskManager mActivityTaskManager;
    public final boolean mAllowBackgroundAuthentication;
    public boolean mAuthAttempted;
    public boolean mAuthSuccess;
    public final BiometricManager mBiometricManager;
    public final boolean mIsRestricted;
    public final boolean mIsStrongBiometric;
    public final LockoutTracker mLockoutTracker;
    public final long mOperationId;
    public final AuthenticateOptions mOptions;
    public int mPromptPrivilegedFlags;
    public final boolean mRequireConfirmation;
    public final int mSensorStrength;
    public boolean mShouldCancelIfBackgroundAuthentication;
    public final boolean mShouldUseLockoutTracker;
    public long mStartTimeMs;
    public int mState;
    public final TaskStackListener mTaskStackListener;

    public AuthenticationClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            long j,
            boolean z,
            AuthenticateOptions authenticateOptions,
            int i,
            boolean z2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            boolean z3,
            TaskStackListener taskStackListener,
            LockoutTracker lockoutTracker,
            boolean z4,
            int i2) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                authenticateOptions.getUserId(),
                authenticateOptions.getOpPackageName(),
                i,
                authenticateOptions.getSensorId(),
                false,
                biometricLogger,
                biometricContext,
                authenticateOptions.isMandatoryBiometrics());
        this.mState = 0;
        this.mAuthSuccess = false;
        this.mIsStrongBiometric = z3;
        this.mOperationId = j;
        this.mRequireConfirmation = z2;
        this.mActivityTaskManager = ActivityTaskManager.getInstance();
        this.mBiometricManager =
                (BiometricManager) context.getSystemService(BiometricManager.class);
        this.mTaskStackListener = taskStackListener;
        this.mLockoutTracker = lockoutTracker;
        this.mIsRestricted = z;
        this.mAllowBackgroundAuthentication = z4;
        this.mShouldUseLockoutTracker = lockoutTracker != null;
        this.mSensorStrength = i2;
        this.mOptions = authenticateOptions;
        this.mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor,
              // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        binderDiedInternal(!isBiometricPrompt());
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void cancel() {
        super.cancel();
        unregisterListenersForTaskStack();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public void destroy() {
        super.destroy();
        unregisterListenersForTaskStack();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 3;
    }

    public final int getRequestReason() {
        if (isKeyguard()) {
            return 4;
        }
        if (isBiometricPrompt()) {
            return 3;
        }
        Context context = this.mContext;
        String str = this.mOwner;
        boolean z = Utils.DEBUG;
        return (context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL")
                                == 0
                        && KnoxCustomManagerService.SETTING_PKG_NAME.equals(str))
                ? 6
                : 5;
    }

    public abstract void handleLifecycleAfterAuth(boolean z);

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean interruptsPrecedingClients() {
        return true;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final boolean isCryptoOperation() {
        return this.mOperationId != 0;
    }

    public boolean isKeyguard() {
        return Utils.isKeyguard(this.mContext, this.mOwner);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x03b1  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x03e4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x042f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x04f5  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x038c  */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r1v29, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onAuthenticated(
            android.hardware.biometrics.BiometricAuthenticator.Identifier r23,
            boolean r24,
            java.util.ArrayList r25) {
        /*
            Method dump skipped, instructions count: 1440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.AuthenticationClient.onAuthenticated(android.hardware.biometrics.BiometricAuthenticator$Identifier,"
                    + " boolean, java.util.ArrayList):void");
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient,
              // com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        onErrorInternal(i, i2, true);
        this.mState = 4;
        unregisterListenersForTaskStack();
    }

    public boolean semHasPromptPrivilegedAttr() {
        return false;
    }

    public boolean semIsAllowedBackgroundAuthentication() {
        return this.mAllowBackgroundAuthentication;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        int lockoutState;
        int i;
        super.start(clientMonitorCallback);
        if (this.mShouldUseLockoutTracker) {
            i = this.mLockoutTracker.getLockoutModeForUser(this.mTargetUserId);
        } else {
            AuthSessionCoordinator authSessionCoordinator =
                    ((BiometricContextProvider) this.mBiometricContext).mAuthSessionCoordinator;
            int i2 = this.mTargetUserId;
            int i3 = this.mSensorStrength;
            synchronized (authSessionCoordinator) {
                lockoutState =
                        authSessionCoordinator.mMultiBiometricLockoutState.getLockoutState(i2, i3);
            }
            i = lockoutState;
        }
        if (i != 0) {
            Slog.v(
                    "Biometrics/AuthenticationClient",
                    "In lockout mode(" + i + ") ; disallowing authentication");
            onError(i == 1 ? 7 : 9, 0);
            return;
        }
        TaskStackListener taskStackListener = this.mTaskStackListener;
        if (taskStackListener != null) {
            this.mActivityTaskManager.registerTaskStackListener(taskStackListener);
            ActivityManager.SemProcessListener semProcessListener = this.mTaskStackListener;
            if (semProcessListener instanceof ActivityManager.SemProcessListener) {
                this.mActivityManager.semRegisterProcessListener(semProcessListener);
            }
        }
        Slog.d("Biometrics/AuthenticationClient", "Requesting auth for " + this.mOwner);
        this.mStartTimeMs = System.currentTimeMillis();
        this.mAuthAttempted = true;
        startHalOperation();
    }

    public final void unregisterListenersForTaskStack() {
        TaskStackListener taskStackListener = this.mTaskStackListener;
        if (taskStackListener != null) {
            this.mActivityTaskManager.unregisterTaskStackListener(taskStackListener);
            ActivityManager.SemProcessListener semProcessListener = this.mTaskStackListener;
            if (semProcessListener instanceof ActivityManager.SemProcessListener) {
                this.mActivityManager.semUnregisterProcessListener(semProcessListener);
            }
        }
    }
}
