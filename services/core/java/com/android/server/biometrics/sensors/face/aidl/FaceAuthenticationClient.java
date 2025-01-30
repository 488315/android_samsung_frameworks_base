package com.android.server.biometrics.sensors.face.aidl;

import android.R;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceAuthenticationFrame;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.biometrics.SemBiometricNotification;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.LockoutCache;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.face.SemFaceMainThread;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.UsageStats;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FaceAuthenticationClient extends AuthenticationClient implements LockoutConsumer {
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public SemBiometricNotification mBackgroundNotification;
    public BroadcastReceiver mBackgroundNotificationAction;
    public final int[] mBiometricPromptIgnoreList;
    public final int[] mBiometricPromptIgnoreListVendor;
    public final Bundle mBundle;
    public boolean mCanIgnoreLockout;
    public ICancellationSignal mCancellationSignal;
    public final int[] mKeyguardIgnoreList;
    public final int[] mKeyguardIgnoreListVendor;
    public int mLastAcquire;
    public final NotificationManager mNotificationManager;
    public int mPrivilegedAttr;
    public final ClientMonitorCallback mSemCancelDaemonCallback;
    public SensorPrivacyManager mSensorPrivacyManager;
    public final UsageStats mUsageStats;

    public FaceAuthenticationClient(Context context, Supplier supplier, IBinder iBinder, long j, ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, FaceAuthenticateOptions faceAuthenticateOptions, int i, boolean z2, BiometricLogger biometricLogger, BiometricContext biometricContext, boolean z3, UsageStats usageStats, LockoutCache lockoutCache, boolean z4, int i2) {
        this(context, supplier, iBinder, j, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, usageStats, lockoutCache, z4, (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class), i2);
    }

    public FaceAuthenticationClient(Context context, Supplier supplier, IBinder iBinder, long j, ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, FaceAuthenticateOptions faceAuthenticateOptions, int i, boolean z2, BiometricLogger biometricLogger, BiometricContext biometricContext, boolean z3, UsageStats usageStats, LockoutCache lockoutCache, boolean z4, SensorPrivacyManager sensorPrivacyManager, int i2) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, null, null, z4, false, i2);
        PromptInfo biometricPromptInfo;
        this.mLastAcquire = 23;
        this.mSemCancelDaemonCallback = new ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(BaseClientMonitor baseClientMonitor) {
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z5) {
                Slog.i("FaceAuthenticationClient", "mSemCancelDaemonCallback.onClientFinished");
                FaceAuthenticationClient.this.dismissNotification();
                if (FaceAuthenticationClient.this.mCancellationSignal != null) {
                    FaceAuthenticationClient.this.getServiceExtImpl().daemonCancel(FaceAuthenticationClient.this.mCancellationSignal, true);
                }
            }
        };
        setRequestId(j);
        this.mUsageStats = usageStats;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mAuthSessionCoordinator = biometricContext.getAuthSessionCoordinator();
        Resources resources = getContext().getResources();
        this.mBiometricPromptIgnoreList = resources.getIntArray(R.array.fingerprint_acquired_vendor);
        this.mBiometricPromptIgnoreListVendor = resources.getIntArray(R.array.maps_starting_lat_lng);
        this.mKeyguardIgnoreList = resources.getIntArray(R.array.imAddressTypes);
        this.mKeyguardIgnoreListVendor = resources.getIntArray(R.array.networkAttributes);
        Bundle bundle = SemFaceUtils.getBundle();
        this.mBundle = bundle;
        SemFaceUtils.resetBundle();
        int i3 = 0;
        if (bundle != null) {
            this.mPrivilegedAttr = bundle.getInt("sem_privileged_attr", 0);
            Slog.d("FaceAuthenticationClient", "mPrivilegedAttr = " + this.mPrivilegedAttr + ", bundle = " + bundle);
        } else {
            this.mPrivilegedAttr = 0;
        }
        if (isBiometricPrompt() && (biometricPromptInfo = getBiometricPromptInfo(getCookie())) != null) {
            int semGetPrivilegedFlag = biometricPromptInfo.semGetPrivilegedFlag();
            if (semGetPrivilegedFlag != 0 && (semGetPrivilegedFlag & 4) != 0) {
                this.mPrivilegedAttr |= 1;
            }
            i3 = biometricPromptInfo.semGetDisplayId();
        }
        semSetDisplayId(i3);
        if (Utils.isKeyguard(getContext(), getOwnerString()) || (this.mPrivilegedAttr & 1) != 0) {
            this.mCanIgnoreLockout = true;
        }
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
        try {
            SensorPrivacyManager sensorPrivacyManager = this.mSensorPrivacyManager;
            if (sensorPrivacyManager != null && sensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
                onError(8, 100003);
                this.mCallback.onClientFinished(this, false);
                return;
            }
            getServiceExtImpl().startOperation(2);
            Slog.w("FaceAuthenticationClient", "authenticate START");
            long currentTimeMillis = System.currentTimeMillis();
            if (getServiceExtImpl().isUsingSehAPI()) {
                this.mCancellationSignal = getServiceExtImpl().daemonAuthenticate(this.mOperationId);
                showBackgroundAuthenticationNotificationIfNeeded();
            } else {
                this.mCancellationSignal = doAuthenticate();
            }
            Slog.w("FaceAuthenticationClient", "authenticate FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + this.mCancellationSignal);
        } catch (RemoteException e) {
            Slog.e("FaceAuthenticationClient", "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    public final ICancellationSignal doAuthenticate() {
        final AidlSession aidlSession = (AidlSession) getFreshDaemon();
        if (aidlSession.hasContextMethods()) {
            OperationContextExt operationContext = getOperationContext();
            ICancellationSignal authenticateWithContext = aidlSession.getSession().authenticateWithContext(this.mOperationId, operationContext.toAidlContext((FaceAuthenticateOptions) getOptions()));
            getBiometricContext().subscribe(operationContext, new Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FaceAuthenticationClient.lambda$doAuthenticate$0(AidlSession.this, (OperationContext) obj);
                }
            });
            return authenticateWithContext;
        }
        return aidlSession.getSession().authenticate(this.mOperationId);
    }

    public static /* synthetic */ void lambda$doAuthenticate$0(AidlSession aidlSession, OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (RemoteException e) {
            Slog.e("FaceAuthenticationClient", "Unable to notify context changed", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void stopHalOperation() {
        unsubscribeBiometricContext();
        dismissNotification();
        if (this.mCancellationSignal != null) {
            try {
                getServiceExtImpl().daemonCancel(this.mCancellationSignal, false);
            } catch (RemoteException e) {
                Slog.e("FaceAuthenticationClient", "Remote exception when requesting cancel", e);
                onError(1, 0);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public void handleLifecycleAfterAuth(boolean z) {
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList) {
        super.onAuthenticated(identifier, z, arrayList);
        this.mState = 4;
        this.mUsageStats.addEvent(new UsageStats.AuthenticationEvent(getStartTimeMs(), System.currentTimeMillis() - getStartTimeMs(), z, 0, 0, getTargetUserId()));
        dismissNotification();
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        dismissNotification();
        this.mUsageStats.addEvent(new UsageStats.AuthenticationEvent(getStartTimeMs(), System.currentTimeMillis() - getStartTimeMs(), false, i, i2, getTargetUserId()));
        if (i == 16) {
            BiometricNotificationUtils.showReEnrollmentNotification(getContext());
        }
        super.onError(i, i2);
    }

    public final int[] getAcquireIgnorelist() {
        return isBiometricPrompt() ? this.mBiometricPromptIgnoreList : this.mKeyguardIgnoreList;
    }

    public final int[] getAcquireVendorIgnorelist() {
        return isBiometricPrompt() ? this.mBiometricPromptIgnoreListVendor : this.mKeyguardIgnoreListVendor;
    }

    public final boolean shouldSendAcquiredMessage(int i, int i2) {
        if (i == 22) {
            if (!Utils.listContains(getAcquireVendorIgnorelist(), i2)) {
                return true;
            }
        } else if (!Utils.listContains(getAcquireIgnorelist(), i)) {
            return true;
        }
        return false;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        this.mLastAcquire = i;
        onAcquiredInternal(i, i2, shouldSendAcquiredMessage(i, i2));
        PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAcquireForUser(getTargetUserId(), isCryptoOperation());
    }

    public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) {
        int acquiredInfo = faceAuthenticationFrame.getData().getAcquiredInfo();
        int vendorCode = faceAuthenticationFrame.getData().getVendorCode();
        this.mLastAcquire = acquiredInfo;
        onAcquiredInternal(acquiredInfo, vendorCode, false);
        if (!shouldSendAcquiredMessage(acquiredInfo, vendorCode) || getListener() == null) {
            return;
        }
        try {
            getListener().onAuthenticationFrame(faceAuthenticationFrame);
        } catch (RemoteException e) {
            Slog.w("FaceAuthenticationClient", "Failed to send authentication frame", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutTimed(long j) {
        this.mAuthSessionCoordinator.lockOutTimed(getTargetUserId(), getSensorStrength(), getSensorId(), j, getRequestId());
        getLogger().logOnError(getContext(), getOperationContext(), 7, 0, getTargetUserId());
        PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementTimedLockoutForUser(getTargetUserId());
        onError(7, 0);
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutPermanent() {
        this.mAuthSessionCoordinator.lockedOutFor(getTargetUserId(), getSensorStrength(), getSensorId(), getRequestId());
        getLogger().logOnError(getContext(), getOperationContext(), 9, 0, getTargetUserId());
        PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementPermanentLockoutForUser(getTargetUserId());
        onError(9, 0);
    }

    public SemFaceServiceExImpl getServiceExtImpl() {
        return SemFaceServiceExImpl.getInstance();
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

    public PromptInfo getBiometricPromptInfo(int i) {
        try {
            return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).semGetPromptInfo(i);
        } catch (RemoteException e) {
            Slog.w("FaceAuthenticationClient", "getBiometricPrompt: failed to get prompt info" + e.getMessage());
            return null;
        }
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
        intent.putExtra("authenticator", 8);
        if (this.mBackgroundNotificationAction == null) {
            this.mBackgroundNotificationAction = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent2) {
                    if ("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION".equals(intent2.getAction()) && FaceAuthenticationClient.this.getOwnerString().equals(intent2.getStringExtra("package")) && intent2.getIntExtra("authenticator", 0) == 8 && FaceAuthenticationClient.this.mCancellationSignal != null) {
                        Slog.i("FaceAuthenticationClient", "Cancel authentication by Notification action");
                        FaceAuthenticationClient.this.getServiceExtImpl().daemonCancel(FaceAuthenticationClient.this.mCancellationSignal, false);
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

    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    public boolean canIgnoreLockout() {
        return this.mCanIgnoreLockout;
    }
}
