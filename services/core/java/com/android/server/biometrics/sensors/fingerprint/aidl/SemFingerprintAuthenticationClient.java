package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.R;
import android.adaptiveauth.Flags;
import android.app.PendingIntent;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationFailedInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.events.AuthenticationSucceededInfo;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda4;
import com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda5;
import com.android.server.biometrics.SemBiometricBoostingManager;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricNotification;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.CallbackWithProbe;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.SensorOverlays;
import com.android.server.biometrics.sensors.fingerprint.PowerPressHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;
import com.android.server.biometrics.sensors.fingerprint.Udfps;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFingerprintAuthenticationClient extends AuthenticationClient implements SemUdfpsConstraintStatusListener, SemFpTspBlockStatusHandler, Udfps, LockoutConsumer, PowerPressHandler {
    public final CallbackWithProbe mALSProbeCallback;
    public final Bundle mAttribute;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public int mAuthenticationFailedReason;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public SemBiometricNotification mBackgroundNotification;
    public AnonymousClass2 mBackgroundNotificationAction;
    public boolean mCanIgnoreLockout;
    public ICancellationSignal mCancellationSignal;
    public long mCaptureStartTime;
    public int mErrorEscrow;
    public boolean mIsAuthenticated;
    public final boolean mIsKeyguard;
    public boolean mIsScreenOnWhenStartCapture;
    public boolean mIsSetEarlyWakeUp;
    public final boolean mIsSettingApp;
    public final boolean mIsStrongBiometric;
    public int mLastErrorCode;
    public long mPowerPressedTimeStamp;
    public int mPrivilegedFlags;
    public PromptInfo mPromptInfo;
    public int mQualityErrorCount;
    public int mRejectCount;
    public final SensorOverlays mSensorOverlays;
    public final FingerprintSensorPropertiesInternal mSensorProps;
    public boolean mShouldSkipResponseDueToPowerPress;
    public int mTotalQualityErrorCount;
    public SemUdfpsSysUiImpl mUdfpsImpl;
    public int mVendorErrorEscrow;

    public SemFingerprintAuthenticationClient(Context context, Supplier supplier, IBinder iBinder, long j, ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, int i, BiometricLogger biometricLogger, BiometricContext biometricContext, boolean z2, TaskStackListener taskStackListener, AuthenticationStateListeners authenticationStateListeners, boolean z3, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i2, LockoutTracker lockoutTracker, Bundle bundle, long j3) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, fingerprintAuthenticateOptions, i, false, biometricLogger, biometricContext, z2, taskStackListener, lockoutTracker, z3, i2);
        setRequestId(j);
        this.mSensorOverlays = new SensorOverlays();
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mIsStrongBiometric = z2;
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        boolean z4 = false;
        this.mALSProbeCallback = new CallbackWithProbe(this.mLogger.mALSProbe, false);
        this.mAuthSessionCoordinator = ((BiometricContextProvider) biometricContext).mAuthSessionCoordinator;
        this.mAttribute = bundle;
        this.mIsKeyguard = !z && super.isKeyguard();
        if (!z) {
            String opPackageName = fingerprintAuthenticateOptions.getOpPackageName();
            if (context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0 && KnoxCustomManagerService.SETTING_PKG_NAME.equals(opPackageName)) {
                z4 = true;
            }
        }
        this.mIsSettingApp = z4;
        this.mPowerPressedTimeStamp = j3;
    }

    public boolean canEnableEarlyWakeUp() {
        if (!this.mIsKeyguard || isInteractive() || Utils.isFlipFolded(this.mContext)) {
            return false;
        }
        return SemBiometricFeature.FP_FEATURE_EARLY_WAKE_UP || !(InputManager.getInstance() == null || (InputManager.getInstance().semCheckInputFeature() & 1) == 1) || Settings.System.getInt(this.mContext.getContentResolver(), "double_tab_to_wake_up", 0) == 0;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    public final boolean canIgnoreLockout() {
        return this.mCanIgnoreLockout;
    }

    public SemBiometricNotification createBiometricNotification() {
        return new SemBiometricNotification(this.mContext, this.mOwner, 2);
    }

    public SemUdfpsSysUiImpl createUdfpsSysUiImpl(boolean z) {
        return new SemUdfpsSysUiImpl(this.mContext, this.mToken, this.mOwner, this.mTargetUserId, z, this.mIsKeyguard);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.HalClientMonitor, com.android.server.biometrics.sensors.BaseClientMonitor
    public final void destroy() {
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("destroy: "), this.mOwner, "FingerprintAuthenticationClient.Ext");
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
        AnonymousClass2 anonymousClass2 = this.mBackgroundNotificationAction;
        if (anonymousClass2 != null) {
            Utils.unregisterBroadcast(this.mContext, anonymousClass2);
            this.mBackgroundNotificationAction = null;
        }
        SemBioLoggingManager bioLoggingManager = getBioLoggingManager();
        bioLoggingManager.getFpHandler().post(new SemBioLoggingManager$$ExternalSyntheticLambda5(bioLoggingManager, (int) this.mRequestId, 0L, "U", this.mLastErrorCode, this.mTotalQualityErrorCount));
    }

    public void enableEarlyWakeUp() {
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        if (powerManager != null) {
            powerManager.setEarlyWakeUp(true);
        }
    }

    public SemBioAnalyticsManager getAnalyticsManager() {
        return SemBioAnalyticsManager.get();
    }

    public SemBioLoggingManager getBioLoggingManager() {
        return SemBioLoggingManager.get();
    }

    public PromptInfo getBiometricPromptInfo(int i) {
        try {
            return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")).semGetPromptInfo(i);
        } catch (RemoteException e) {
            Slog.w("FingerprintAuthenticationClient.Ext", "getBiometricPrompt: failed to get prompt info" + e.getMessage());
            return null;
        }
    }

    public Handler getBiometricsHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    }

    public SemBiometricBoostingManager getBoostingManager() {
        return SemBiometricBoostingManager.getInstance();
    }

    public long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public Handler getHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler();
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public final void handleLifecycleAfterAuth(boolean z) {
        if (z) {
            this.mCallback.onClientFinished(this, true);
        }
    }

    public final boolean hasPrivilegedFlag(int i) {
        return (this.mPrivilegedFlags & i) != 0;
    }

    public boolean isInteractive() {
        return ((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive();
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public final boolean isKeyguard() {
        return this.mIsKeyguard;
    }

    public boolean isTalkBackEnabled() {
        return Utils.isTalkBackEnabled(this.mContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x022e A[EDGE_INSN: B:73:0x022e->B:75:0x022e BREAK  A[LOOP:0: B:67:0x0214->B:70:0x0227], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01d7  */
    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAcquired(int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 836
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.sensors.fingerprint.aidl.SemFingerprintAuthenticationClient.onAcquired(int, int):void");
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public final void onAuthenticated(BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList) {
        if (this.mShouldSkipResponseDueToPowerPress) {
            Slog.d("FingerprintAuthenticationClient.Ext", "SKIP DUE TO POWER PRESS");
            if (z) {
                getHandler().post(new SemFingerprintAuthenticationClient$$ExternalSyntheticLambda0(this, 2));
            }
            this.mShouldSkipResponseDueToPowerPress = false;
            this.mPowerPressedTimeStamp = 0L;
            return;
        }
        Slog.i("FingerprintService", "handleAuthenticated: " + z);
        this.mIsAuthenticated = z;
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnAuthenticated(z);
        }
        final int biometricId = identifier.getBiometricId();
        final long currentTimeMillis = System.currentTimeMillis() - this.mCaptureStartTime;
        resumeFaceAuth();
        boolean z2 = this.mIsAuthenticated;
        if (!z2) {
            this.mRejectCount++;
        }
        if (z2) {
            final SemBioLoggingManager bioLoggingManager = getBioLoggingManager();
            final Context context = this.mContext;
            final int i = (int) this.mRequestId;
            final int i2 = this.mRejectCount;
            final int i3 = this.mTotalQualityErrorCount;
            final boolean z3 = this.mIsScreenOnWhenStartCapture;
            bioLoggingManager.getFpHandler().post(new Runnable() { // from class: com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.this;
                    int i4 = i;
                    long j = currentTimeMillis;
                    int i5 = i2;
                    int i6 = i3;
                    Context context2 = context;
                    int i7 = biometricId;
                    boolean z4 = z3;
                    synchronized (semBioLoggingManager.mFpLoggingInfo) {
                        try {
                            SemBioLoggingManager.LoggingInfo loggingInfo = (SemBioLoggingManager.LoggingInfo) semBioLoggingManager.mFpLoggingInfo.get(i4);
                            if (loggingInfo != null) {
                                loggingInfo.mResultTime = System.currentTimeMillis();
                                loggingInfo.mLatency = j;
                                loggingInfo.mResult = "M";
                                loggingInfo.mExtra = i5;
                                loggingInfo.mBadQualityCount = i6;
                                semBioLoggingManager.fpAddLoggingInfo(loggingInfo);
                                semBioLoggingManager.mFpLoggingInfo.delete(i4);
                                if (semBioLoggingManager.mIsFpBioStarEnabled) {
                                    Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
                                    intent.putExtra("action_type", 1);
                                    intent.putExtra("id", i7);
                                    loggingInfo.sendBroadcast(context2, intent, z4);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        } else {
            final SemBioLoggingManager bioLoggingManager2 = getBioLoggingManager();
            final Context context2 = this.mContext;
            final int i4 = (int) this.mRequestId;
            final int i5 = this.mAuthenticationFailedReason;
            final int i6 = this.mQualityErrorCount;
            final boolean z4 = this.mIsScreenOnWhenStartCapture;
            bioLoggingManager2.getFpHandler().post(new Runnable() { // from class: com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.this;
                    int i7 = i4;
                    long j = currentTimeMillis;
                    int i8 = i5;
                    int i9 = i6;
                    Context context3 = context2;
                    boolean z5 = z4;
                    synchronized (semBioLoggingManager.mFpLoggingInfo) {
                        try {
                            SemBioLoggingManager.LoggingInfo loggingInfo = (SemBioLoggingManager.LoggingInfo) semBioLoggingManager.mFpLoggingInfo.get(i7);
                            if (loggingInfo != null) {
                                loggingInfo.mResultTime = System.currentTimeMillis();
                                loggingInfo.mLatency = j;
                                loggingInfo.mResult = "N";
                                loggingInfo.mExtra = i8;
                                loggingInfo.mBadQualityCount = i9;
                                semBioLoggingManager.fpAddLoggingInfo(loggingInfo);
                                if (semBioLoggingManager.mIsFpBioStarEnabled) {
                                    Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
                                    intent.putExtra("action_type", 2);
                                    intent.putExtra("no_match_reason", i8);
                                    loggingInfo.sendBroadcast(context3, intent, z5);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }
        final boolean z5 = this.mIsAuthenticated;
        final int i7 = this.mQualityErrorCount;
        final int i8 = this.mRejectCount;
        final boolean z6 = this.mIsScreenOnWhenStartCapture;
        getBiometricsHandler().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.SemFingerprintAuthenticationClient$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SemFingerprintAuthenticationClient semFingerprintAuthenticationClient = SemFingerprintAuthenticationClient.this;
                boolean z7 = z5;
                long j = currentTimeMillis;
                int i9 = i8;
                int i10 = i7;
                boolean z8 = z6;
                if (!z7) {
                    SemBioAnalyticsManager analyticsManager = semFingerprintAuthenticationClient.getAnalyticsManager();
                    analyticsManager.fpInsertLog(3, "FPIF", semFingerprintAuthenticationClient.mOwner);
                    analyticsManager.fpInsertLog(1, "FPTF", Long.toString(j));
                    analyticsManager.fpInsertLog(3, z8 ? "FPOF" : "FPFF", semFingerprintAuthenticationClient.mOwner);
                    if (z8 && (Utils.isFlipFolded(semFingerprintAuthenticationClient.mContext) || Utils.isFolderFolded(semFingerprintAuthenticationClient.mContext))) {
                        analyticsManager.fpInsertLog(3, "FFOF", semFingerprintAuthenticationClient.mOwner);
                        return;
                    } else {
                        if (z8) {
                            return;
                        }
                        if (Utils.isFlipFolded(semFingerprintAuthenticationClient.mContext) || Utils.isFolderFolded(semFingerprintAuthenticationClient.mContext)) {
                            analyticsManager.fpInsertLog(3, "FFFF", semFingerprintAuthenticationClient.mOwner);
                            return;
                        }
                        return;
                    }
                }
                SemBioAnalyticsManager analyticsManager2 = semFingerprintAuthenticationClient.getAnalyticsManager();
                analyticsManager2.fpInsertLog(3, "FPIS", semFingerprintAuthenticationClient.mOwner);
                analyticsManager2.fpInsertLog(1, "FPTS", Long.toString(j));
                analyticsManager2.fpInsertLog(1, "FPSF", Integer.toString(i9));
                analyticsManager2.fpInsertLog(1, "FPSQ", Integer.toString(i10));
                analyticsManager2.fpInsertLog(3, z8 ? "FPOS" : "FPFS", semFingerprintAuthenticationClient.mOwner);
                if (z8 && (Utils.isFlipFolded(semFingerprintAuthenticationClient.mContext) || Utils.isFolderFolded(semFingerprintAuthenticationClient.mContext))) {
                    analyticsManager2.fpInsertLog(3, "FFOS", semFingerprintAuthenticationClient.mOwner);
                } else {
                    if (z8) {
                        return;
                    }
                    if (Utils.isFlipFolded(semFingerprintAuthenticationClient.mContext) || Utils.isFolderFolded(semFingerprintAuthenticationClient.mContext)) {
                        analyticsManager2.fpInsertLog(3, "FFFS", semFingerprintAuthenticationClient.mOwner);
                    }
                }
            }
        });
        this.mQualityErrorCount = 0;
        super.onAuthenticated(identifier, z, arrayList);
        if (this.mLockoutTracker == null) {
            Slog.d("FingerprintAuthenticationClient", "Lockout is implemented by the HAL");
        } else if (!z || isBiometricPrompt()) {
            int lockoutModeForUser = this.mLockoutTracker.getLockoutModeForUser(this.mTargetUserId);
            if (lockoutModeForUser != 0) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(lockoutModeForUser, "Fingerprint locked out, lockoutMode(", ")", "FingerprintAuthenticationClient");
                int i9 = lockoutModeForUser == 1 ? 7 : 9;
                this.mSensorOverlays.hide(this.mSensorId);
                this.mAuthenticationStateListeners.onAuthenticationError(new AuthenticationErrorInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason(), FingerprintManager.getErrorString(this.mContext, i9, 0), i9).build());
                onErrorInternal(i9, 0, false);
                cancel();
            }
        } else {
            this.mLockoutTracker.resetFailedAttemptsForUser(this.mTargetUserId, true);
        }
        if (!z) {
            this.mState = 3;
            if (Flags.reportBiometricAuthAttempts()) {
                this.mAuthenticationStateListeners.onAuthenticationFailed(new AuthenticationFailedInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason(), this.mTargetUserId).build());
                return;
            }
            return;
        }
        this.mState = 4;
        this.mSensorOverlays.hide(this.mSensorId);
        if (Flags.reportBiometricAuthAttempts()) {
            this.mAuthenticationStateListeners.onAuthenticationSucceeded(new AuthenticationSucceededInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason(), this.mIsStrongBiometric, this.mTargetUserId).build());
        }
        this.mAuthenticationStateListeners.onAuthenticationStopped(new AuthenticationStoppedInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason()).build());
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        int i3;
        int i4;
        SemBioAnalyticsManager.EventData eventData;
        if (i != 5 || (i3 = this.mErrorEscrow) == 0) {
            i3 = i;
            i4 = i2;
        } else {
            i4 = this.mVendorErrorEscrow;
        }
        if (i3 == 8) {
            this.mLastErrorCode = i4;
        } else {
            this.mLastErrorCode = i3;
        }
        AuthenticationStateListeners authenticationStateListeners = this.mAuthenticationStateListeners;
        BiometricSourceType biometricSourceType = BiometricSourceType.FINGERPRINT;
        authenticationStateListeners.onAuthenticationError(new AuthenticationErrorInfo.Builder(biometricSourceType, getRequestReason(), FingerprintManager.getErrorString(this.mContext, i3, i4), i3).build());
        super.onError(i3, i4);
        if (i3 == 18) {
            Context context = this.mContext;
            Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = BiometricNotificationUtils.sLastAlertTime;
            long j2 = elapsedRealtime - j;
            if (j == 0 || j2 >= BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                BiometricNotificationUtils.sLastAlertTime = elapsedRealtime;
                BiometricNotificationUtils.showNotificationHelper(context, context.getString(R.string.managed_profile_label_badge), context.getString(R.string.managed_profile_label_badge_2), context.getString(R.string.managed_profile_label), PendingIntent.getActivityAsUser(context, 0, ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("android.settings.FINGERPRINT_SETTINGS", KnoxCustomManagerService.SETTING_PKG_NAME), 67108864, null, UserHandle.CURRENT), null, null, "sys", "FingerprintBadCalibrationNotificationChannel", "FingerprintBadCalibration", -1, false, 0);
            } else {
                Slog.v("BiometricNotificationUtils", "Skipping calibration notification : " + j2);
            }
        }
        this.mSensorOverlays.hide(this.mSensorId);
        this.mAuthenticationStateListeners.onAuthenticationStopped(new AuthenticationStoppedInfo.Builder(biometricSourceType, getRequestReason()).build());
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnError(i3, i4);
        }
        resumeFaceAuth();
        SemBioAnalyticsManager analyticsManager = getAnalyticsManager();
        String str = this.mOwner;
        analyticsManager.getClass();
        if (i3 != 1) {
            if (i3 != 7) {
                if (i3 == 8) {
                    eventData = new SemBioAnalyticsManager.EventData();
                    eventData.mFeature = "FPER";
                    eventData.mExtra = Integer.toString(i4);
                    eventData.mType = 3;
                } else if (i3 != 9) {
                    eventData = null;
                }
            }
            SemBioAnalyticsManager.EventData eventData2 = new SemBioAnalyticsManager.EventData();
            eventData2.mFeature = "FPIB";
            eventData2.mExtra = str;
            eventData2.mType = 3;
            eventData = eventData2;
        } else {
            eventData = new SemBioAnalyticsManager.EventData();
            eventData.mFeature = "FPER";
            eventData.mExtra = Integer.toString(i3);
            eventData.mType = 3;
        }
        if (eventData != null) {
            analyticsManager.fpInsertLog(eventData);
        }
        if (i3 == 8 && i4 == 1007) {
            SemBioAnalyticsManager.EventData eventData3 = new SemBioAnalyticsManager.EventData();
            eventData3.mFeature = "FPPD";
            eventData3.mExtra = null;
            eventData3.mType = 2;
            analyticsManager.fpInsertLog(eventData3);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public final void onLockoutPermanent() {
        this.mAuthSessionCoordinator.lockedOutFor(this.mTargetUserId, this.mSensorStrength, this.mSensorId, this.mRequestId);
        this.mLogger.logOnError(this.mContext, getOperationContext(), 9, 0, this.mTargetUserId);
        PerformanceTracker.getInstanceForSensorId(this.mSensorId).incrementPermanentLockoutForUser(this.mTargetUserId);
        try {
            this.mAuthenticationStateListeners.onAuthenticationError(new AuthenticationErrorInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason(), FingerprintManager.getErrorString(this.mContext, 9, 0), 9).build());
            this.mListener.onError(this.mSensorId, this.mCookie, 9, 0);
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
        }
        this.mShouldSendErrorToClient = false;
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public final void onLockoutTimed(long j) {
        this.mAuthSessionCoordinator.lockOutTimed(this.mTargetUserId, this.mSensorStrength, this.mSensorId, j, this.mRequestId);
        this.mLogger.logOnError(this.mContext, getOperationContext(), 7, 0, this.mTargetUserId);
        PerformanceTracker.getInstanceForSensorId(this.mSensorId).incrementTimedLockoutForUser(this.mTargetUserId);
        try {
            this.mAuthenticationStateListeners.onAuthenticationError(new AuthenticationErrorInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason(), FingerprintManager.getErrorString(this.mContext, 7, 0), 7).build());
            this.mListener.onError(this.mSensorId, this.mCookie, 7, 0);
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
        }
        this.mShouldSendErrorToClient = false;
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener
    public final void onOneHandModeEnabled() {
        onError(8, 5001);
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void onPointerDown(PointerContext pointerContext) {
        try {
            this.mState = 1;
            AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            if (aidlSession.hasContextMethods()) {
                aidlSession.mSession.onPointerDownWithContext(pointerContext);
            } else {
                aidlSession.mSession.onPointerDown(pointerContext.pointerId, (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
            }
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i = this.mSensorId;
            IFingerprintServiceReceiver iFingerprintServiceReceiver = clientMonitorCallbackConverter.mFingerprintServiceReceiver;
            if (iFingerprintServiceReceiver != null) {
                iFingerprintServiceReceiver.onUdfpsPointerDown(i);
            }
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void onPointerUp(PointerContext pointerContext) {
        try {
            this.mState = 3;
            AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            if (aidlSession.hasContextMethods()) {
                aidlSession.mSession.onPointerUpWithContext(pointerContext);
            } else {
                aidlSession.mSession.onPointerUp(pointerContext.pointerId);
            }
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i = this.mSensorId;
            IFingerprintServiceReceiver iFingerprintServiceReceiver = clientMonitorCallbackConverter.mFingerprintServiceReceiver;
            if (iFingerprintServiceReceiver != null) {
                iFingerprintServiceReceiver.onUdfpsPointerUp(i);
            }
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler
    public final void onTspBlocked() {
        onAcquired(6, 1004);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler
    public final void onTspUnBlocked() {
        onAcquired(6, 1005);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void onUdfpsUiEvent(int i) {
        if (i == 2) {
            try {
                ((AidlSession) this.mLazyDaemon.get()).mSession.onUiReady();
            } catch (RemoteException e) {
                Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
            }
        }
    }

    public final void resumeFaceAuth() {
        if (this.mIsKeyguard) {
            getBiometricsHandler().post(new SemFingerprintAuthenticationClient$$ExternalSyntheticLambda0(this, 0));
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public final boolean semHasPromptPrivilegedAttr() {
        return (this.mPromptPrivilegedFlags & 8) != 0;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public final boolean semIsAllowedBackgroundAuthentication() {
        return this.mAllowBackgroundAuthentication || hasPrivilegedFlag(4);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void setIgnoreDisplayTouches(boolean z) {
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.setIgnoreDisplayTouches(z);
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.biometrics.sensors.fingerprint.aidl.SemFingerprintAuthenticationClient$2] */
    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        if (this.mSensorProps.isAnyUdfpsType()) {
            this.mState = 2;
        } else {
            this.mState = 1;
        }
        if (this.mState == 4 || this.mCancellationSignal == null) {
            return;
        }
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.start();
        }
        if (semIsAllowedBackgroundAuthentication() && !this.mIsKeyguard && !Utils.isSystem(this.mContext, this.mOwner)) {
            if (this.mBackgroundNotification == null) {
                this.mBackgroundNotification = createBiometricNotification();
            }
            Intent intent = new Intent("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION");
            intent.putExtra("package", this.mOwner);
            intent.putExtra("authenticator", 2);
            if (this.mBackgroundNotificationAction == null) {
                this.mBackgroundNotificationAction = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.SemFingerprintAuthenticationClient.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent2) {
                        SemFingerprintAuthenticationClient semFingerprintAuthenticationClient = SemFingerprintAuthenticationClient.this;
                        semFingerprintAuthenticationClient.getClass();
                        if ("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION".equals(intent2.getAction()) && semFingerprintAuthenticationClient.mOwner.equals(intent2.getStringExtra("package")) && intent2.getIntExtra("authenticator", 0) == 2) {
                            Slog.i("FingerprintAuthenticationClient.Ext", "Cancel authentication by Notification action");
                            semFingerprintAuthenticationClient.cancel();
                        }
                    }
                };
                Utils.registerBroadcastAsUser(this.mContext, this.mBackgroundNotificationAction, new IntentFilter("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION"), UserHandle.CURRENT, BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler());
            }
            this.mBackgroundNotification.postNotification(intent);
        }
        SemBioLoggingManager bioLoggingManager = getBioLoggingManager();
        int i = (int) this.mRequestId;
        bioLoggingManager.getFpHandler().post(new SemBioLoggingManager$$ExternalSyntheticLambda4(bioLoggingManager, isBiometricPrompt() ? "AP" : "A", this.mOwner, i));
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        this.mSensorOverlays.show(this.mSensorId, getRequestReason(), this);
        this.mAuthenticationStateListeners.onAuthenticationStarted(new AuthenticationStartedInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason()).build());
        try {
            final AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            OperationContextExt operationContext = getOperationContext();
            final int i = 0;
            final int i2 = 1;
            ((BiometricContextProvider) this.mBiometricContext).subscribe(operationContext, new Consumer(this) { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient$$ExternalSyntheticLambda0
                public final /* synthetic */ SemFingerprintAuthenticationClient f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i) {
                        case 0:
                            SemFingerprintAuthenticationClient semFingerprintAuthenticationClient = this.f$0;
                            AidlSession aidlSession2 = aidlSession;
                            OperationContext operationContext2 = (OperationContext) obj;
                            semFingerprintAuthenticationClient.getClass();
                            try {
                                if (aidlSession2.hasContextMethods()) {
                                    semFingerprintAuthenticationClient.mCancellationSignal = aidlSession2.mSession.authenticateWithContext(semFingerprintAuthenticationClient.mOperationId, operationContext2);
                                } else {
                                    semFingerprintAuthenticationClient.mCancellationSignal = aidlSession2.mSession.authenticate(semFingerprintAuthenticationClient.mOperationId);
                                }
                                if (!((BiometricContextProvider) semFingerprintAuthenticationClient.mBiometricContext).isAwake()) {
                                    semFingerprintAuthenticationClient.mALSProbeCallback.mProbe.disable();
                                    break;
                                } else {
                                    semFingerprintAuthenticationClient.mALSProbeCallback.mProbe.enable();
                                    break;
                                }
                            } catch (RemoteException e) {
                                Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
                                semFingerprintAuthenticationClient.onError(1, 0);
                                semFingerprintAuthenticationClient.mSensorOverlays.hide(semFingerprintAuthenticationClient.mSensorId);
                                semFingerprintAuthenticationClient.mCallback.onClientFinished(semFingerprintAuthenticationClient, false);
                                return;
                            }
                        default:
                            SemFingerprintAuthenticationClient semFingerprintAuthenticationClient2 = this.f$0;
                            AidlSession aidlSession3 = aidlSession;
                            OperationContext operationContext3 = (OperationContext) obj;
                            semFingerprintAuthenticationClient2.getClass();
                            if (aidlSession3.hasContextMethods()) {
                                try {
                                    aidlSession3.mSession.onContextChanged(operationContext3);
                                } catch (RemoteException e2) {
                                    Slog.e("FingerprintAuthenticationClient", "Unable to notify context changed", e2);
                                }
                            }
                            if (!((BiometricContextProvider) semFingerprintAuthenticationClient2.mBiometricContext).isAwake()) {
                                semFingerprintAuthenticationClient2.mALSProbeCallback.mProbe.disable();
                                break;
                            } else {
                                semFingerprintAuthenticationClient2.mALSProbeCallback.mProbe.enable();
                                break;
                            }
                    }
                }
            }, new Consumer(this) { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient$$ExternalSyntheticLambda0
                public final /* synthetic */ SemFingerprintAuthenticationClient f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i2) {
                        case 0:
                            SemFingerprintAuthenticationClient semFingerprintAuthenticationClient = this.f$0;
                            AidlSession aidlSession2 = aidlSession;
                            OperationContext operationContext2 = (OperationContext) obj;
                            semFingerprintAuthenticationClient.getClass();
                            try {
                                if (aidlSession2.hasContextMethods()) {
                                    semFingerprintAuthenticationClient.mCancellationSignal = aidlSession2.mSession.authenticateWithContext(semFingerprintAuthenticationClient.mOperationId, operationContext2);
                                } else {
                                    semFingerprintAuthenticationClient.mCancellationSignal = aidlSession2.mSession.authenticate(semFingerprintAuthenticationClient.mOperationId);
                                }
                                if (!((BiometricContextProvider) semFingerprintAuthenticationClient.mBiometricContext).isAwake()) {
                                    semFingerprintAuthenticationClient.mALSProbeCallback.mProbe.disable();
                                    break;
                                } else {
                                    semFingerprintAuthenticationClient.mALSProbeCallback.mProbe.enable();
                                    break;
                                }
                            } catch (RemoteException e) {
                                Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
                                semFingerprintAuthenticationClient.onError(1, 0);
                                semFingerprintAuthenticationClient.mSensorOverlays.hide(semFingerprintAuthenticationClient.mSensorId);
                                semFingerprintAuthenticationClient.mCallback.onClientFinished(semFingerprintAuthenticationClient, false);
                                return;
                            }
                        default:
                            SemFingerprintAuthenticationClient semFingerprintAuthenticationClient2 = this.f$0;
                            AidlSession aidlSession3 = aidlSession;
                            OperationContext operationContext3 = (OperationContext) obj;
                            semFingerprintAuthenticationClient2.getClass();
                            if (aidlSession3.hasContextMethods()) {
                                try {
                                    aidlSession3.mSession.onContextChanged(operationContext3);
                                } catch (RemoteException e2) {
                                    Slog.e("FingerprintAuthenticationClient", "Unable to notify context changed", e2);
                                }
                            }
                            if (!((BiometricContextProvider) semFingerprintAuthenticationClient2.mBiometricContext).isAwake()) {
                                semFingerprintAuthenticationClient2.mALSProbeCallback.mProbe.disable();
                                break;
                            } else {
                                semFingerprintAuthenticationClient2.mALSProbeCallback.mProbe.enable();
                                break;
                            }
                    }
                }
            }, this.mOptions);
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
            onError(1, 0);
            this.mSensorOverlays.hide(this.mSensorId);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void stopHalOperation() {
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("onAuthenticationStopped: "), this.mOwner, "FingerprintAuthenticationClient.Ext");
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.stop();
        }
        getBoostingManager().release(this.mContext, 2);
        this.mSensorOverlays.hide(this.mSensorId);
        this.mAuthenticationStateListeners.onAuthenticationStopped(new AuthenticationStoppedInfo.Builder(BiometricSourceType.FINGERPRINT, getRequestReason()).build());
        unsubscribeBiometricContext();
        ICancellationSignal iCancellationSignal = this.mCancellationSignal;
        if (iCancellationSignal == null) {
            Slog.e("FingerprintAuthenticationClient", "cancellation signal was null");
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (RemoteException e) {
            Slog.e("FingerprintAuthenticationClient", "Remote exception", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void vibrateError() {
        Utils.semVibrate(this.mContext, 5);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void vibrateSuccess() {
        Utils.semVibrate(this.mContext, 1);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final ClientMonitorCallback wrapCallbackForStart(ClientMonitorCallback clientMonitorCallback) {
        return new ClientMonitorCompositeCallback(this.mALSProbeCallback, new HalClientMonitor.AnonymousClass1(), clientMonitorCallback);
    }
}
