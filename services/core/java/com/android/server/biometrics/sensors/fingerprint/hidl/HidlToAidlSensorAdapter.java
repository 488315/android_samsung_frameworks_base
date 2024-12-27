package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.StopUserClient;
import com.android.server.biometrics.sensors.UserSwitchProvider;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$AidlResponseHandlerCallback;
import com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler;
import com.android.server.biometrics.sensors.fingerprint.aidl.Sensor;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HidlToAidlSensorAdapter extends Sensor implements IHwBinder.DeathRecipient {
    public final AidlResponseHandler$AidlResponseHandlerCallback mAidlResponseHandlerCallback;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public int mCurrentUserId;
    public IBiometricsFingerprint mDaemon;
    public final Runnable mInternalCleanupRunnable;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public LockoutFrameworkImpl mLockoutTracker;
    public AidlSession mSession;
    public final HidlToAidlSensorAdapter$$ExternalSyntheticLambda0 mUserStartedCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$1, reason: invalid class name */
    public final class AnonymousClass1 implements AidlResponseHandler$AidlResponseHandlerCallback, UserSwitchProvider {
        public /* synthetic */ AnonymousClass1() {
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        public StartUserClient getStartUserClient(int i) {
            return HidlToAidlSensorAdapter.this.getFingerprintUpdateActiveUserClient(i, false);
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        public StopUserClient getStopUserClient(int i) {
            HidlToAidlSensorAdapter hidlToAidlSensorAdapter = HidlToAidlSensorAdapter.this;
            Context context = hidlToAidlSensorAdapter.mContext;
            return new HidlToAidlSensorAdapter$2$1(context, new HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(hidlToAidlSensorAdapter, 0), null, i, hidlToAidlSensorAdapter.mSensorProperties.sensorId, BiometricLogger.ofUnknown(context), hidlToAidlSensorAdapter.mBiometricContext, new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(this));
        }

        @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$AidlResponseHandlerCallback
        public void onEnrollSuccess() {
            HidlToAidlSensorAdapter hidlToAidlSensorAdapter = HidlToAidlSensorAdapter.this;
            hidlToAidlSensorAdapter.mScheduler.scheduleClientMonitor(hidlToAidlSensorAdapter.getFingerprintUpdateActiveUserClient(hidlToAidlSensorAdapter.mCurrentUserId, true), null);
        }

        @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$AidlResponseHandlerCallback
        public void onHardwareUnavailable() {
            HidlToAidlSensorAdapter hidlToAidlSensorAdapter = HidlToAidlSensorAdapter.this;
            hidlToAidlSensorAdapter.mDaemon = null;
            hidlToAidlSensorAdapter.mSession = null;
            hidlToAidlSensorAdapter.mCurrentUserId = -10000;
        }
    }

    public HidlToAidlSensorAdapter(FingerprintProvider fingerprintProvider, Context context, Handler handler, SensorProps sensorProps, LockoutResetDispatcher lockoutResetDispatcher, BiometricContext biometricContext, boolean z, Runnable runnable, AuthSessionCoordinator authSessionCoordinator, IBiometricsFingerprint iBiometricsFingerprint, AidlResponseHandler$AidlResponseHandlerCallback aidlResponseHandler$AidlResponseHandlerCallback) {
        super(fingerprintProvider, context, handler, Sensor.getFingerprintSensorPropertiesInternal(sensorProps, new ArrayList(), z), biometricContext);
        this.mCurrentUserId = -10000;
        this.mUserStartedCallback = new HidlToAidlSensorAdapter$$ExternalSyntheticLambda0(this);
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mInternalCleanupRunnable = runnable;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mDaemon = iBiometricsFingerprint;
        this.mAidlResponseHandlerCallback = aidlResponseHandler$AidlResponseHandlerCallback == null ? new AnonymousClass1() : aidlResponseHandler$AidlResponseHandlerCallback;
        if (iBiometricsFingerprint == null) {
            handler.post(new HidlToAidlSensorAdapter$$ExternalSyntheticLambda1(this, 0));
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final void generateEvent() {
        AidlSession aidlSession = this.mSession;
        if (aidlSession != null) {
            aidlSession.mAidlResponseHandler.onAcquired((byte) 7, 70001);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final int getCurrentSessionUserId() {
        AidlSession aidlSession = this.mSession;
        if (aidlSession != null) {
            return aidlSession.mUserId;
        }
        return 0;
    }

    public IBiometricsFingerprint getDaemon() {
        FingerprintProvider fingerprintProvider = this.mProvider;
        if (fingerprintProvider.mTestHalEnabled) {
            return SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL ? new TestHal(this.mContext, this.mSensorProperties.sensorId) : new SehTestHal(this.mContext, this.mSensorProperties.sensorId, false);
        }
        if (fingerprintProvider.mTpaHalModeEnabled) {
            return new SehTestHal(this.mContext, this.mSensorProperties.sensorId, true);
        }
        try {
            return SemBiometricFeature.FP_FEATURE_USE_AOSP_HAL ? IBiometricsFingerprint.getService() : ISehBiometricsFingerprint.getService();
        } catch (RemoteException e) {
            Slog.e("HidlToAidlSensorAdapter", "Failed to get fingerprint HAL", e);
            return null;
        } catch (NoSuchElementException e2) {
            Slog.w("HidlToAidlSensorAdapter", "NoSuchElementException", e2);
            return null;
        }
    }

    public final FingerprintUpdateActiveUserClient getFingerprintUpdateActiveUserClient(int i, boolean z) {
        Context context = this.mContext;
        return new FingerprintUpdateActiveUserClient(context, new HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(this, 3), i, this.mSensorProperties.sensorId, BiometricLogger.ofUnknown(context), this.mBiometricContext, new HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(this, 4), !FingerprintUtils.getInstance(this.mSensorProperties.sensorId).getBiometricsForUser(this.mContext, i).isEmpty(), this.mAuthenticatorIds, z, this.mUserStartedCallback);
    }

    public synchronized IBiometricsFingerprint getIBiometricsFingerprint() {
        IBiometricsFingerprint iBiometricsFingerprint = this.mDaemon;
        if (iBiometricsFingerprint != null) {
            return iBiometricsFingerprint;
        }
        IBiometricsFingerprint daemon = getDaemon();
        this.mDaemon = daemon;
        if (this.mProvider.mTestHalEnabled) {
            return daemon;
        }
        if (daemon == null) {
            Slog.w("HidlToAidlSensorAdapter", "Fingerprint HAL not available");
            return null;
        }
        daemon.asBinder().linkToDeath(this, 0L);
        this.mHandler.post(new HidlToAidlSensorAdapter$$ExternalSyntheticLambda1(this, 1));
        this.mInternalCleanupRunnable.run();
        return this.mDaemon;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final int getLockoutModeForUser(int i) {
        return this.mLockoutTracker.getLockoutModeForUser(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final LockoutTracker getLockoutTracker(boolean z) {
        if (z) {
            return null;
        }
        return this.mLockoutTracker;
    }

    public final synchronized AidlSession getSession() {
        AidlSession aidlSession = this.mSession;
        if (aidlSession != null && this.mDaemon != null) {
            return aidlSession;
        }
        HidlToAidlSensorAdapter$$ExternalSyntheticLambda3 hidlToAidlSensorAdapter$$ExternalSyntheticLambda3 = new HidlToAidlSensorAdapter$$ExternalSyntheticLambda3(this, 2);
        int i = this.mCurrentUserId;
        Context context = this.mContext;
        BiometricScheduler biometricScheduler = this.mScheduler;
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.mSensorProperties;
        AidlSession aidlSession2 = new AidlSession(hidlToAidlSensorAdapter$$ExternalSyntheticLambda3, i, new SemFpAidlResponseHandler(context, biometricScheduler, fingerprintSensorPropertiesInternal.sensorId, this.mCurrentUserId, this.mLockoutTracker, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, this.mAidlResponseHandlerCallback, this.mProvider.mCallbackDispatcher, this.mLockoutHalImpl, fingerprintSensorPropertiesInternal));
        this.mSession = aidlSession2;
        return aidlSession2;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public AidlSession getSessionForUser(int i) {
        AidlSession aidlSession = this.mSession;
        if (aidlSession == null || aidlSession.mUserId != i) {
            return null;
        }
        return aidlSession;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final void handleOnLockoutReset(int i) {
        AidlSession aidlSession = this.mSession;
        if (aidlSession == null || aidlSession.mUserId != i) {
            return;
        }
        aidlSession.mAidlResponseHandler.onLockoutCleared();
    }

    public void handleUserChanged(int i) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "User changed. Current user for fingerprint sensor is ", "HidlToAidlSensorAdapter");
        this.mSession = null;
        this.mCurrentUserId = i;
    }

    public void injectDaemon(IBiometricsFingerprint iBiometricsFingerprint) {
        this.mDaemon = iBiometricsFingerprint;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final boolean isHardwareDetected(String str) {
        return getIBiometricsFingerprint() != null;
    }

    public final void serviceDied(long j) {
        Slog.d("HidlToAidlSensorAdapter", "Fingerprint HAL died.");
        this.mSession = null;
        this.mDaemon = null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final void setTestHalEnabled(boolean z) {
        super.setTestHalEnabled(z);
        this.mDaemon = null;
        this.mSession = null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public final void setTpaHalEnabled() {
        this.mDaemon = null;
        this.mSession = null;
        this.mHandler.post(new HidlToAidlSensorAdapter$$ExternalSyntheticLambda1(this, 0));
    }
}
