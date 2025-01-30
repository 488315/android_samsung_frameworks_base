package com.android.server.biometrics.sensors.face.hidl;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.PromptInfo;
import android.hardware.face.FaceAuthenticateOptions;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricNotification;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.face.SemFaceMainThread;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.UsageStats;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class FaceAuthenticationClient extends AuthenticationClient implements LockoutConsumer {
    public SemBiometricNotification mBackgroundNotification;
    public BroadcastReceiver mBackgroundNotificationAction;
    public final int[] mBiometricPromptIgnoreList;
    public final int[] mBiometricPromptIgnoreListVendor;
    public final Bundle mBundle;
    public CancellationSignal mCancellationSignal;
    public final int[] mKeyguardIgnoreList;
    public final int[] mKeyguardIgnoreListVendor;
    public int mLastAcquire;
    public final SemLockoutFrameworkImpl mLockoutFrameworkImpl;
    public int mPrivilegedAttr;
    public final ClientMonitorCallback mSemCancelDaemonCallback;
    public SensorPrivacyManager mSensorPrivacyManager;
    public final UsageStats mUsageStats;

    public abstract void daemonAuthenticate(long j);

    public abstract void daemonAuthenticationCancel();

    public FaceAuthenticationClient(Context context, Supplier supplier, IBinder iBinder, long j, ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, FaceAuthenticateOptions faceAuthenticateOptions, int i, boolean z2, BiometricLogger biometricLogger, BiometricContext biometricContext, boolean z3, LockoutTracker lockoutTracker, UsageStats usageStats, boolean z4, int i2) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, null, lockoutTracker, z4, false, i2);
        PromptInfo promptInfo;
        this.mSemCancelDaemonCallback = new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.FaceAuthenticationClient.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(BaseClientMonitor baseClientMonitor) {
                Slog.i("FaceAuthenticationClient", "mSemCancelDaemonCallback.onClientStarted");
                FaceAuthenticationClient.this.getBiometricContext().getAuthSessionCoordinator().authStartedFor(FaceAuthenticationClient.this.getTargetUserId(), FaceAuthenticationClient.this.getSensorId(), FaceAuthenticationClient.this.getRequestId());
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z5) {
                Slog.i("FaceAuthenticationClient", "mSemCancelDaemonCallback.onClientFinished");
                FaceAuthenticationClient.this.getBiometricContext().getAuthSessionCoordinator().authEndedFor(FaceAuthenticationClient.this.getTargetUserId(), Utils.getCurrentStrength(FaceAuthenticationClient.this.getSensorId()), FaceAuthenticationClient.this.getSensorId(), FaceAuthenticationClient.this.getRequestId(), z5);
            }
        };
        setRequestId(j);
        this.mUsageStats = usageStats;
        this.mSensorPrivacyManager = (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class);
        Resources resources = getContext().getResources();
        this.mBiometricPromptIgnoreList = resources.getIntArray(R.array.fingerprint_acquired_vendor);
        this.mBiometricPromptIgnoreListVendor = resources.getIntArray(R.array.maps_starting_lat_lng);
        this.mKeyguardIgnoreList = resources.getIntArray(R.array.imAddressTypes);
        this.mKeyguardIgnoreListVendor = resources.getIntArray(R.array.networkAttributes);
        this.mLockoutFrameworkImpl = (SemLockoutFrameworkImpl) lockoutTracker;
        Bundle bundle = SemFaceUtils.getBundle();
        this.mBundle = bundle;
        int i3 = 0;
        if (bundle != null) {
            this.mPrivilegedAttr = bundle.getInt("sem_privileged_attr", 0);
        } else {
            this.mPrivilegedAttr = 0;
        }
        if (isBiometricPrompt()) {
            try {
                promptInfo = IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).semGetPromptInfo(getCookie());
            } catch (RemoteException e) {
                Slog.w("FaceAuthenticationClient", "FaceAuthenticationClient getPrompt Info Exception" + e.getMessage());
                promptInfo = null;
            }
            if (promptInfo != null) {
                int semGetPrivilegedFlag = promptInfo.semGetPrivilegedFlag();
                if (semGetPrivilegedFlag != 0 && (semGetPrivilegedFlag & 4) != 0) {
                    this.mPrivilegedAttr |= 1;
                }
                i3 = promptInfo.semGetDisplayId();
            }
        }
        semSetDisplayId(i3);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        this.mState = 1;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public ClientMonitorCallback wrapCallbackForStart(ClientMonitorCallback clientMonitorCallback) {
        return new ClientMonitorCompositeCallback(this.mSemCancelDaemonCallback, getLogger().getAmbientLightProbe(true), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        SensorPrivacyManager sensorPrivacyManager = this.mSensorPrivacyManager;
        if (sensorPrivacyManager != null && sensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
            onError(8, 100003);
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            daemonAuthenticate(getOperationId());
            showBackgroundAuthenticationNotificationIfNeeded();
        } catch (RemoteException e) {
            Slog.e("FaceAuthenticationClient", "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void stopHalOperation() {
        try {
            dismissNotification();
            daemonAuthenticationCancel();
        } catch (RemoteException e) {
            Slog.e("FaceAuthenticationClient", "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public void handleLifecycleAfterAuth(boolean z) {
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public int handleFailedAttempt(int i) {
        Slog.i("FaceAuthenticationClient", "handleFailedAttempt");
        if (Utils.isKeyguard(getContext(), getOwnerString()) || (this.mPrivilegedAttr & 1) != 0) {
            return 0;
        }
        this.mLockoutFrameworkImpl.addFailedAttemptForUser(i);
        int lockoutModeForUser = getLockoutTracker().getLockoutModeForUser(i);
        PerformanceTracker instanceForSensorId = PerformanceTracker.getInstanceForSensorId(getSensorId());
        if (lockoutModeForUser == 2) {
            onLockoutPermanent();
            instanceForSensorId.incrementPermanentLockoutForUser(i);
        } else if (lockoutModeForUser == 1) {
            onLockoutTimed(30000L);
            instanceForSensorId.incrementTimedLockoutForUser(i);
        }
        return lockoutModeForUser;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList) {
        super.onAuthenticated(identifier, z, arrayList);
        this.mState = 4;
        dismissNotification();
        boolean z2 = this.mUpdatedAuthenticated;
        if (z2) {
            resetFailedAttempts(getTargetUserId());
        } else {
            int lockoutModeForUser = this.mLockoutFrameworkImpl.getLockoutModeForUser(getTargetUserId());
            ClientMonitorCallbackConverter listener = getListener();
            try {
                if (lockoutModeForUser == 1) {
                    listener.onError(getSensorId(), getCookie(), 7, 0);
                } else if (lockoutModeForUser == 2) {
                    listener.onError(getSensorId(), getCookie(), 9, 0);
                }
            } catch (RemoteException e) {
                Slog.e("FaceAuthenticationClient", "onAuthenticated : Unable to notify listener, finishing", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
        this.mUsageStats.addEvent(new UsageStats.AuthenticationEvent(getStartTimeMs(), System.currentTimeMillis() - getStartTimeMs(), z2, 0, 0, getTargetUserId()));
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        dismissNotification();
        this.mUsageStats.addEvent(new UsageStats.AuthenticationEvent(getStartTimeMs(), System.currentTimeMillis() - getStartTimeMs(), false, i, i2, getTargetUserId()));
        super.onError(i, i2);
    }

    public final int[] getAcquireIgnorelist() {
        return isBiometricPrompt() ? this.mBiometricPromptIgnoreList : this.mKeyguardIgnoreList;
    }

    public final int[] getAcquireVendorIgnorelist() {
        return isBiometricPrompt() ? this.mBiometricPromptIgnoreListVendor : this.mKeyguardIgnoreListVendor;
    }

    public final boolean shouldSend(int i, int i2) {
        if (i == 22) {
            return !Utils.listContains(getAcquireVendorIgnorelist(), i2);
        }
        return !Utils.listContains(getAcquireIgnorelist(), i);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        this.mLastAcquire = i;
        if (i == 13) {
            BiometricNotificationUtils.showReEnrollmentNotification(getContext());
        }
        if (getLockoutTracker().getLockoutModeForUser(getTargetUserId()) == 0) {
            PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAcquireForUser(getTargetUserId(), isCryptoOperation());
        }
        onAcquiredInternal(i, i2, shouldSend(i, i2));
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void vibrateError() {
        if (Utils.isKeyguard(getContext(), getOwnerString())) {
            return;
        }
        if (isBiometricPrompt()) {
            Utils.semVibrate(getContext(), 113);
        } else {
            Utils.semVibrate(getContext(), 5);
        }
    }

    public final void resetFailedAttempts(int i) {
        this.mLockoutFrameworkImpl.resetFailedAttemptsForUser(true, i);
    }

    public void setCancellationSignal(CancellationSignal cancellationSignal) {
        this.mCancellationSignal = cancellationSignal;
    }

    public final void showBackgroundAuthenticationNotificationIfNeeded() {
        if (!SemFaceUtils.hasPrivilegedAttr(this.mBundle, 4) || isKeyguard() || Utils.isSystem(getContext(), getOwnerString())) {
            return;
        }
        if (this.mBackgroundNotification == null) {
            this.mBackgroundNotification = new SemBiometricNotification(getContext(), 8, getOwnerString());
        }
        Intent intent = new Intent("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION");
        intent.putExtra("package", getOwnerString());
        intent.putExtra("authenticator", 2);
        if (this.mBackgroundNotificationAction == null) {
            this.mBackgroundNotificationAction = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.face.hidl.FaceAuthenticationClient.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent2) {
                    if ("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION".equals(intent2.getAction()) && FaceAuthenticationClient.this.getOwnerString().equals(intent2.getStringExtra("package")) && intent2.getIntExtra("authenticator", 0) == 2 && FaceAuthenticationClient.this.mCancellationSignal != null && !FaceAuthenticationClient.this.mCancellationSignal.isCanceled()) {
                        Slog.i("FaceAuthenticationClient", "Cancel authentication by Notification action");
                        FaceAuthenticationClient.this.mCancellationSignal.cancel();
                    }
                }
            };
            Utils.registerBroadcastAsUser(getContext(), this.mBackgroundNotificationAction, new IntentFilter("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION"), UserHandle.CURRENT, SemFaceMainThread.get().getHandler());
        }
        this.mBackgroundNotification.postNotification(intent);
    }

    public final void dismissNotification() {
        Slog.i("FaceAuthenticationClient", "dismissNotification");
        if (this.mBackgroundNotificationAction != null) {
            Utils.unregisterBroadcast(getContext(), this.mBackgroundNotificationAction);
            this.mBackgroundNotificationAction = null;
        }
        SemBiometricNotification semBiometricNotification = this.mBackgroundNotification;
        if (semBiometricNotification != null) {
            semBiometricNotification.cancelNotification();
            this.mBackgroundNotification = null;
        }
    }

    public void onImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
        getListener().onImageProcessed(bArr, i, i2, i3, i4, bundle);
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutTimed(long j) {
        getBiometricContext().getAuthSessionCoordinator().lockOutTimed(getTargetUserId(), getSensorStrength(), getSensorId(), j, getRequestId());
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutPermanent() {
        getBiometricContext().getAuthSessionCoordinator().lockedOutFor(getTargetUserId(), getSensorStrength(), getSensorId(), getRequestId());
    }
}
