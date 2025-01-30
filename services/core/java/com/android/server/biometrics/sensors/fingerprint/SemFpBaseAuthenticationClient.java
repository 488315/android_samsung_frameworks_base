package com.android.server.biometrics.sensors.fingerprint;

import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.AuthenticateOptions;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.PromptInfo;
import android.hardware.face.FaceManager;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioFgThread;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricNotification;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class SemFpBaseAuthenticationClient extends AuthenticationClient implements SemUdfpsConstraintStatusListener, SemFpTspBlockStatusHandler {
    public Bundle mAttribute;
    public int mAuthenticationFailedReason;
    public Map mAuthenticatorIds;
    public SemBiometricNotification mBackgroundNotification;
    public BroadcastReceiver mBackgroundNotificationAction;
    public boolean mCanIgnoreLockout;
    public long mCaptureStartTime;
    public int mErrorEscrow;
    public Injector mInjector;
    public boolean mIsAuthenticated;
    public final boolean mIsKeyguard;
    public boolean mIsScreenOnWhenStartCapture;
    public boolean mIsSetEarlyWakeUp;
    public final boolean mIsSettingApp;
    public int mLastErrorCode;
    public int mPrivilegedFlags;
    public PromptInfo mPromptInfo;
    public int mQualityErrorCount;
    public int mRejectCount;
    public int mTotalQualityErrorCount;
    public SemUdfpsSysUiImpl mUdfpsImpl;
    public int mVendorErrorEscrow;

    public class Injector {
        public PromptInfo getBiometricPromptInfo(int i) {
            try {
                return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).semGetPromptInfo(i);
            } catch (RemoteException e) {
                Slog.w("FingerprintService", "getBiometricPrompt: failed to get prompt info" + e.getMessage());
                return null;
            }
        }

        public SemUdfpsSysUiImpl createUdfpsSysUiImpl(Context context, IBinder iBinder, String str, int i, boolean z) {
            return new SemUdfpsSysUiImpl(context, iBinder, str, i, z);
        }

        public SemBiometricNotification createBiometricNotification(Context context, String str) {
            return new SemBiometricNotification(context, 2, str);
        }

        public boolean isInteractive(Context context) {
            return ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        }

        public void enableEarlyWakeUp(Context context) {
            ((PowerManager) context.getSystemService(PowerManager.class)).setEarlyWakeUp(true);
        }

        public boolean isFoldedInFlipType(Context context) {
            return Utils.isFlipFolded(context);
        }

        public boolean checkTDDIDoubleTap(Context context) {
            if (SemBiometricFeature.FP_FEATURE_EARLY_WAKE_UP) {
                return true;
            }
            return !(InputManager.getInstance() == null || (InputManager.getInstance().semCheckInputFeature() & 1) == 1) || Settings.System.getInt(context.getContentResolver(), "double_tab_to_wake_up", 0) == 0;
        }

        public boolean isTalkBackEnabled(Context context) {
            return Utils.isTalkBackEnabled(context);
        }

        public void vibrateSuccess(Context context) {
            Utils.semVibrate(context, 1);
        }

        public void vibrateError(Context context) {
            Utils.semVibrate(context, 5);
        }

        public void acquireBoosting(Context context) {
            SemBiometricBoostingManager.getInstance().acquireFingerprintDvfs(context, 2000);
        }

        public void releaseBoosting(Context context) {
            SemBiometricBoostingManager.getInstance().release(context, 2);
        }
    }

    public SemFpBaseAuthenticationClient(Context context, Supplier supplier, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j, boolean z, AuthenticateOptions authenticateOptions, int i, boolean z2, BiometricLogger biometricLogger, BiometricContext biometricContext, boolean z3, TaskStackListener taskStackListener, LockoutTracker lockoutTracker, boolean z4, boolean z5, int i2) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j, z, authenticateOptions, i, z2, biometricLogger, biometricContext, z3, taskStackListener, lockoutTracker, z4, true, i2);
        this.mInjector = new Injector();
        this.mAttribute = new Bundle();
        this.mIsKeyguard = !isRestricted() && super.isKeyguard();
        this.mIsSettingApp = !isRestricted() && Utils.isSettings(context, authenticateOptions.getOpPackageName());
    }

    public void setInjectorForTest(Injector injector) {
        this.mInjector = injector;
    }

    public SemBioAnalyticsManager getAnalyticsManager() {
        return SemBioAnalyticsManager.get();
    }

    public SemBioLoggingManager getBioLoggingManager() {
        return SemBioLoggingManager.get();
    }

    public void setExtraAttribute(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.mAttribute = bundle;
        if (isBiometricPrompt()) {
            this.mPromptInfo = this.mInjector.getBiometricPromptInfo(getCookie());
        }
        setPrivilegedFlags();
        semSetDisplayId(getDisplayId());
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            initForUdfps();
        }
        Slog.i("FingerprintService", "FingerprintClientExt: " + Integer.toBinaryString(this.mPrivilegedFlags) + ", " + Integer.toBinaryString(getPromptPrivilegedFlags()));
    }

    public final void setPrivilegedFlags() {
        if (this.mIsKeyguard) {
            this.mPrivilegedFlags |= 27;
        } else if (this.mIsSettingApp) {
            this.mPrivilegedFlags |= 2;
        }
        this.mPrivilegedFlags |= this.mAttribute.getInt("sem_privileged_attr", 0);
        setPromptPrivilegedFlags();
        if (hasPrivilegedFlag(1)) {
            this.mCanIgnoreLockout = true;
        }
        if (hasPrivilegedFlag(8)) {
            semSetVibrationEffectUsage(false);
        }
        if (semIsAllowedBackgroundAuthentication()) {
            this.mPrivilegedFlags |= 4;
        }
    }

    public final void setPromptPrivilegedFlags() {
        PromptInfo promptInfo = this.mPromptInfo;
        if (promptInfo != null) {
            setPromptPrivilegedFlags(promptInfo.semGetPrivilegedFlag());
            if (hasPromptPrivilegedAttr(1) || hasPromptPrivilegedAttr(4)) {
                this.mPrivilegedFlags = 1 | this.mPrivilegedFlags;
            }
        }
    }

    public final int getDisplayId() {
        PromptInfo promptInfo = this.mPromptInfo;
        if (promptInfo != null) {
            return promptInfo.semGetDisplayId();
        }
        return this.mAttribute.getInt("EXTRA_KEY_DISPLAY_ID");
    }

    public final boolean hasExtraAuthenticationFlag(int i) {
        return (this.mAttribute.getInt("EXTRA_KEY_AUTH_FLAG", 0) & i) != 0;
    }

    public final boolean hasPromptPrivilegedAttr(int i) {
        return (getPromptPrivilegedFlags() & i) != 0;
    }

    private void initForUdfps() {
        SemUdfpsSysUiImpl createUdfpsSysUiImpl = this.mInjector.createUdfpsSysUiImpl(getContext(), getToken(), getOwnerString(), getTargetUserId(), hasPrivilegedFlag(16) || hasExtraAuthenticationFlag(32768));
        this.mUdfpsImpl = createUdfpsSysUiImpl;
        if (this.mIsKeyguard) {
            createUdfpsSysUiImpl.setSysUiType(4);
        }
        if (isBiometricPrompt()) {
            return;
        }
        this.mUdfpsImpl.checkGuideLayerAndTouchBlock(this.mPrivilegedFlags);
        this.mUdfpsImpl.setCustomIconAttribute(this.mAttribute, this.mPrivilegedFlags);
        this.mUdfpsImpl.setSysUiListener(new SemUdfpsSysUiImpl.SysUiCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpBaseAuthenticationClient.1
            @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
            public void onSysUiDismissed() {
                SemFpBaseAuthenticationClient.this.onUserCanceled();
            }

            @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
            public void onSysUiError(int i, int i2) {
                if ((i == 2 && i2 == 2) && SemFpBaseAuthenticationClient.this.isKeyguard()) {
                    SemFpBaseAuthenticationClient.this.mErrorEscrow = 8;
                    SemFpBaseAuthenticationClient.this.mVendorErrorEscrow = 5000;
                }
                if (SemFpBaseAuthenticationClient.this.wasAuthAttempted()) {
                    SemFpBaseAuthenticationClient.this.cancel();
                } else {
                    SemFpBaseAuthenticationClient.this.onError(5, 0);
                }
            }
        });
    }

    public boolean hasPrivilegedFlag(int i) {
        return (this.mPrivilegedFlags & i) != 0;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler
    public void onTspBlocked() {
        onAcquired(6, 1004);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler
    public void onTspUnBlocked() {
        onAcquired(6, 1005);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener
    public void onOneHandModeEnabled() {
        onError(8, 5001);
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean isRestricted() {
        return super.isRestricted() || getPromptPrivilegedFlags() != 0;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean isKeyguard() {
        return this.mIsKeyguard;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl;
        super.start(clientMonitorCallback);
        if (getState() == 4) {
            return;
        }
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE && (semUdfpsSysUiImpl = this.mUdfpsImpl) != null) {
            semUdfpsSysUiImpl.start();
        }
        showBackgroundAuthenticationNotificationIfNeeded();
        getBioLoggingManager().fpStart((int) getRequestId(), isBiometricPrompt() ? "AP" : "A", getOwnerString());
    }

    private void showBackgroundAuthenticationNotificationIfNeeded() {
        if (!semIsAllowedBackgroundAuthentication() || isKeyguard() || Utils.isSystem(getContext(), getOwnerString())) {
            return;
        }
        if (this.mBackgroundNotification == null) {
            this.mBackgroundNotification = this.mInjector.createBiometricNotification(getContext(), getOwnerString());
        }
        Intent intent = new Intent("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION");
        intent.putExtra("package", getOwnerString());
        intent.putExtra("authenticator", 2);
        if (this.mBackgroundNotificationAction == null) {
            this.mBackgroundNotificationAction = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpBaseAuthenticationClient.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent2) {
                    SemFpBaseAuthenticationClient.this.handleNotificationAction(intent2);
                }
            };
            Utils.registerBroadcastAsUser(getContext(), this.mBackgroundNotificationAction, new IntentFilter("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION"), UserHandle.CURRENT, SemFpMainThread.get().getHandler());
        }
        this.mBackgroundNotification.postNotification(intent);
    }

    public final void handleNotificationAction(Intent intent) {
        if ("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION".equals(intent.getAction()) && getOwnerString().equals(intent.getStringExtra("package")) && intent.getIntExtra("authenticator", 0) == 2) {
            Slog.i("FingerprintService", "Cancel authentication by Notification action");
            cancel();
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void stopHalOperation() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl;
        Slog.i("FingerprintService", "onAuthenticationStopped: " + getOwnerString());
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE && (semUdfpsSysUiImpl = this.mUdfpsImpl) != null) {
            semUdfpsSysUiImpl.stop();
        }
        this.mInjector.releaseBoosting(getContext());
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList) {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl;
        this.mIsAuthenticated = z;
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE && (semUdfpsSysUiImpl = this.mUdfpsImpl) != null) {
            semUdfpsSysUiImpl.handleOnAuthenticated(z);
        }
        handleAuthenticationResult(identifier.getBiometricId());
        super.onAuthenticated(identifier, z, arrayList);
    }

    public final void handleAuthenticationResult(int i) {
        long currentTimeMillis = System.currentTimeMillis() - this.mCaptureStartTime;
        resumeFaceAuth();
        if (!this.mIsAuthenticated) {
            this.mRejectCount++;
        }
        handleLoggingData(i, currentTimeMillis);
        handleBigDataOnBackgroundThread(this.mIsAuthenticated, this.mQualityErrorCount, this.mRejectCount, currentTimeMillis, this.mIsScreenOnWhenStartCapture);
        this.mQualityErrorCount = 0;
    }

    public final void handleLoggingData(int i, long j) {
        if (this.mIsAuthenticated) {
            getBioLoggingManager().fpMatch(getContext(), (int) getRequestId(), j, this.mRejectCount, this.mTotalQualityErrorCount, i, this.mIsScreenOnWhenStartCapture);
        } else {
            getBioLoggingManager().fpNoMatch(getContext(), (int) getRequestId(), j, this.mAuthenticationFailedReason, this.mQualityErrorCount, this.mIsScreenOnWhenStartCapture);
        }
    }

    public final void handleBigDataOnBackgroundThread(final boolean z, final int i, final int i2, final long j, final boolean z2) {
        SemBioFgThread.get().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpBaseAuthenticationClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFpBaseAuthenticationClient.this.lambda$handleBigDataOnBackgroundThread$0(z, j, i2, i, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleBigDataOnBackgroundThread$0(boolean z, long j, int i, int i2, boolean z2) {
        if (z) {
            sendBigDataForAuthenticationSucceed(j, i, i2, z2);
        } else {
            sendBigDataForAuthenticationFail(j, z2);
        }
    }

    public final void sendBigDataForAuthenticationSucceed(long j, int i, int i2, boolean z) {
        SemBioAnalyticsManager analyticsManager = getAnalyticsManager();
        analyticsManager.fpInsertLog("FPIS", getOwnerString(), -1, 3);
        analyticsManager.fpInsertLog("FPTS", Long.toString(j), -1, 1);
        analyticsManager.fpInsertLog("FPSF", Integer.toString(i), -1, 1);
        analyticsManager.fpInsertLog("FPSQ", Integer.toString(i2), -1, 1);
        analyticsManager.fpInsertLog(z ? "FPOS" : "FPFS", getOwnerString(), -1, 3);
        if (z && (Utils.isFlipFolded(getContext()) || Utils.isFolderFolded(getContext()))) {
            analyticsManager.fpInsertLog("FFOS", getOwnerString(), -1, 3);
        } else {
            if (z) {
                return;
            }
            if (Utils.isFlipFolded(getContext()) || Utils.isFolderFolded(getContext())) {
                analyticsManager.fpInsertLog("FFFS", getOwnerString(), -1, 3);
            }
        }
    }

    public final void sendBigDataForAuthenticationFail(long j, boolean z) {
        SemBioAnalyticsManager analyticsManager = getAnalyticsManager();
        analyticsManager.fpInsertLog("FPIF", getOwnerString(), -1, 3);
        analyticsManager.fpInsertLog("FPTF", Long.toString(j), -1, 1);
        analyticsManager.fpInsertLog(z ? "FPOF" : "FPFF", getOwnerString(), -1, 3);
        if (z && (Utils.isFlipFolded(getContext()) || Utils.isFolderFolded(getContext()))) {
            analyticsManager.fpInsertLog("FFOF", getOwnerString(), -1, 3);
        } else {
            if (z) {
                return;
            }
            if (Utils.isFlipFolded(getContext()) || Utils.isFolderFolded(getContext())) {
                analyticsManager.fpInsertLog("FFFF", getOwnerString(), -1, 3);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl;
        int i3;
        if (i == 5 && (i3 = this.mErrorEscrow) != 0) {
            i2 = this.mVendorErrorEscrow;
            i = i3;
        }
        if (i == 8) {
            this.mLastErrorCode = i2;
        } else {
            this.mLastErrorCode = i;
        }
        super.onError(i, i2);
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE && (semUdfpsSysUiImpl = this.mUdfpsImpl) != null) {
            semUdfpsSysUiImpl.handleOnError(i, i2);
        }
        resumeFaceAuth();
        sendBigDataForError(i, i2);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        Slog.d("FingerprintService", "onAcquired: " + i + ", " + i2);
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
            if (semUdfpsSysUiImpl != null) {
                semUdfpsSysUiImpl.handleOnAcquired(i, i2);
            }
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
                sendUdfpsPointEventForUltrasonic(i, i2);
            }
        }
        boolean z = true;
        if (i == 6) {
            z = true ^ isInternalEvent(i2);
            handleVendorEvent(i2);
        }
        if (FingerprintUtils.semIsQualityFailedEvent(i, i2)) {
            handleQualityFailedEvent(i, i2);
        }
        if (z) {
            if (SemBiometricFeature.FP_FEATURE_TSP_BLOCK && i2 == 1005) {
                i = 0;
                i2 = 0;
            }
            super.onAcquired(i, i2);
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0030 -> B:15:0x0037). Please report as a decompilation issue!!! */
    public final void sendUdfpsPointEventForUltrasonic(int i, int i2) {
        if (i == 6) {
            if (i2 == 10002 || i2 == 10004 || i2 == 10007) {
                try {
                    if (getListener() != null) {
                        if (i2 == 10002) {
                            getListener().onUdfpsPointerDown(getSensorId());
                        } else {
                            getListener().onUdfpsPointerUp(getSensorId());
                        }
                    }
                } catch (RemoteException e) {
                    Slog.w("FingerprintService", "Failed to invoke sendAcquired", e);
                }
            }
        }
    }

    public final boolean isInternalEvent(int i) {
        return i == 10011 || i == 1006 || FingerprintUtils.semIsAuthenticationFailedReasonEvent(i);
    }

    public final void handleVendorEvent(int i) {
        if (i == 1006) {
            getAnalyticsManager().fpInsertLogHelp(6, i, getOwnerString(), 1);
            getBioLoggingManager().fpCaptureFailed(getContext(), (int) getRequestId(), System.currentTimeMillis() - this.mCaptureStartTime, i, this.mIsScreenOnWhenStartCapture);
        } else if (i == 10011) {
            setEarlyWakeUp();
        } else if (i == 10001) {
            this.mIsSetEarlyWakeUp = false;
        } else if (i == 10002) {
            handleCaptureStarted();
        }
        if (FingerprintUtils.semIsAuthenticationFailedReasonEvent(i)) {
            handleAuthenticationFailedReasonEvent(i);
        }
    }

    public final void handleCaptureStarted() {
        this.mCaptureStartTime = System.currentTimeMillis();
        setEarlyWakeUp();
        pauseFaceAuth();
        if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
            this.mInjector.acquireBoosting(getContext());
        }
        if (this.mInjector.isTalkBackEnabled(getContext())) {
            vibrateSuccess();
        }
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            getBioLoggingManager().fpUpdateUdfpsTouchMap((int) getRequestId());
        }
        this.mIsScreenOnWhenStartCapture = this.mInjector.isInteractive(getContext());
    }

    public final void handleAuthenticationFailedReasonEvent(int i) {
        this.mAuthenticationFailedReason = i;
        PerformanceTracker.getInstanceForSensorId(getSensorId()).semIncrementNoMatchReason(getTargetUserId(), i);
        getAnalyticsManager().fpInsertLogHelp(6, i, String.valueOf(i), 3);
    }

    public final void handleQualityFailedEvent(int i, int i2) {
        resumeFaceAuth();
        if (!hasPrivilegedFlag(8)) {
            vibrateError();
        }
        this.mQualityErrorCount++;
        this.mTotalQualityErrorCount++;
        PerformanceTracker.getInstanceForSensorId(getSensorId()).semIncrementQualityForUser(getTargetUserId(), isCryptoOperation());
        getAnalyticsManager().fpInsertLogHelp(i, i2, getOwnerString(), 3);
        getBioLoggingManager().fpCaptureFailed(getContext(), (int) getRequestId(), System.currentTimeMillis() - this.mCaptureStartTime, i == 6 ? i2 : i, this.mIsScreenOnWhenStartCapture);
    }

    public final void setEarlyWakeUp() {
        if (this.mIsSetEarlyWakeUp || !isKeyguard() || this.mInjector.isInteractive(getContext()) || this.mInjector.isFoldedInFlipType(getContext()) || !this.mInjector.checkTDDIDoubleTap(getContext())) {
            return;
        }
        this.mInjector.enableEarlyWakeUp(getContext());
        this.mIsSetEarlyWakeUp = true;
    }

    public final void pauseFaceAuth() {
        if (isKeyguard()) {
            SemBioFgThread.get().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpBaseAuthenticationClient$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SemFpBaseAuthenticationClient.this.lambda$pauseFaceAuth$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pauseFaceAuth$1() {
        FaceManager faceManager = (FaceManager) getContext().getSystemService(FaceManager.class);
        if (faceManager != null) {
            faceManager.semPauseAuth();
        }
    }

    public final void resumeFaceAuth() {
        if (isKeyguard()) {
            SemBioFgThread.get().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpBaseAuthenticationClient$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SemFpBaseAuthenticationClient.this.lambda$resumeFaceAuth$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resumeFaceAuth$2() {
        FaceManager faceManager = (FaceManager) getContext().getSystemService(FaceManager.class);
        if (faceManager != null) {
            faceManager.semResumeAuth();
        }
    }

    public final void sendBigDataForError(int i, int i2) {
        getAnalyticsManager().fpInsertLogError(i, i2, getOwnerString());
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.HalClientMonitor, com.android.server.biometrics.sensors.BaseClientMonitor
    public void destroy() {
        Slog.i("FingerprintService", "destroy: " + getOwnerString());
        super.destroy();
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.destroy();
            this.mUdfpsImpl = null;
        }
        SemBiometricNotification semBiometricNotification = this.mBackgroundNotification;
        if (semBiometricNotification != null) {
            semBiometricNotification.cancelNotification();
            this.mBackgroundNotification = null;
        }
        if (this.mBackgroundNotificationAction != null) {
            Utils.unregisterBroadcast(getContext(), this.mBackgroundNotificationAction);
            this.mBackgroundNotificationAction = null;
        }
        getBioLoggingManager().fpStop((int) getRequestId(), "U", 0L, this.mLastErrorCode, this.mTotalQualityErrorCount);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void vibrateSuccess() {
        this.mInjector.vibrateSuccess(getContext());
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void vibrateError() {
        this.mInjector.vibrateError(getContext());
    }

    public boolean useEarlyAuthenticationResult() {
        return isKeyguard() && !this.mInjector.isInteractive(getContext());
    }

    public void handleEarlyAuthenticationResult() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnAuthenticated(true);
        }
        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
            SemUdfpsHelper.getInstance().getOpticalSensorHelper().restoreFunctionForLightSource(0L);
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean semHasPromptPrivilegedAttr(int i) {
        return hasPromptPrivilegedAttr(i);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    public boolean canIgnoreLockout() {
        return this.mCanIgnoreLockout;
    }

    public Map getAuthenticatorIds() {
        return this.mAuthenticatorIds;
    }
}
