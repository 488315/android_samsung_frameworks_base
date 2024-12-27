package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.events.AuthenticationAcquiredInfo;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintEnrollOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda4;
import com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda5;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.ALSProbe;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.CallbackWithProbe;
import com.android.server.biometrics.log.OperationContextExt;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BiometricNotificationUtils;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.HalClientMonitor;
import com.android.server.biometrics.sensors.SensorOverlays;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.PowerPressHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpPauseResumeHandler;
import com.android.server.biometrics.sensors.fingerprint.SemFpTspBlockStatusHandler;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;
import com.android.server.biometrics.sensors.fingerprint.Udfps;

import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemFingerprintEnrollClient extends EnrollClient
        implements SemFpPauseResumeHandler,
                SemUdfpsConstraintStatusListener,
                SemFpTspBlockStatusHandler,
                Udfps,
                PowerPressHandler {
    public final CallbackWithProbe mALSProbeCallback;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public ICancellationSignal mCancellationSignal;
    public int mDuplicatedImgCnt;
    public final int mEnrollReason;
    public int mLastErrorCode;
    public final int mMaxTemplatesPerUser;
    public final SensorOverlays mSensorOverlays;
    public final FingerprintSensorPropertiesInternal mSensorProps;
    public int mTotalQualityErrorCount;
    public final SemUdfpsSysUiImpl mUdfpsImpl;

    public SemFingerprintEnrollClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            long j,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            byte[] bArr,
            String str,
            FingerprintUtils fingerprintUtils,
            int i2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal,
            AuthenticationStateListeners authenticationStateListeners,
            int i3,
            int i4,
            FingerprintEnrollOptions fingerprintEnrollOptions) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                bArr,
                str,
                fingerprintUtils,
                i2,
                true,
                biometricLogger,
                biometricContext,
                BiometricFingerprintConstants.reasonToMetric(
                        fingerprintEnrollOptions.getEnrollReason()));
        setRequestId(j);
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        this.mSensorOverlays = new SensorOverlays();
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mMaxTemplatesPerUser = i3;
        BiometricLogger biometricLogger2 = this.mLogger;
        ALSProbe aLSProbe = biometricLogger2.mALSProbe;
        this.mALSProbeCallback = new CallbackWithProbe(aLSProbe, true);
        this.mEnrollReason = i4;
        if (i4 == 1) {
            biometricLogger2.mShouldLogMetrics = false;
            aLSProbe.destroy();
        }
        Slog.w(
                "FingerprintEnrollClient",
                "EnrollOptions "
                        + FingerprintEnrollOptions.enrollReasonToString(
                                fingerprintEnrollOptions.getEnrollReason()));
        if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
            SemUdfpsSysUiImpl createUdfpsSysUiImpl = createUdfpsSysUiImpl();
            this.mUdfpsImpl = createUdfpsSysUiImpl;
            createUdfpsSysUiImpl.mSysUiType = 64;
            createUdfpsSysUiImpl.setSysUiListener(
                    new SemUdfpsSysUiImpl
                            .SysUiCallback() { // from class:
                                               // com.android.server.biometrics.sensors.fingerprint.aidl.SemFingerprintEnrollClient.1
                        @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
                        public final void onSysUiDismissed() {
                            SemFingerprintEnrollClient.this.onUserCanceled();
                        }

                        @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl.SysUiCallback
                        public final void onSysUiError(int i5, int i6) {
                            SemFingerprintEnrollClient semFingerprintEnrollClient =
                                    SemFingerprintEnrollClient.this;
                            semFingerprintEnrollClient.onError(5, 0);
                            semFingerprintEnrollClient.cancel();
                        }
                    });
        }
    }

    public SemUdfpsSysUiImpl createUdfpsSysUiImpl() {
        return new SemUdfpsSysUiImpl(
                this.mContext, this.mToken, this.mOwner, this.mTargetUserId, true, false);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void destroy() {
        super.destroy();
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.destroy();
        }
        int i = (int) this.mRequestId;
        int i2 = this.mTotalQualityErrorCount;
        int i3 = this.mLastErrorCode;
        SemBioLoggingManager bioLoggingManager = getBioLoggingManager();
        bioLoggingManager
                .getFpHandler()
                .post(
                        new SemBioLoggingManager$$ExternalSyntheticLambda5(
                                bioLoggingManager, i, i3, "U", 0, i2));
    }

    public final void doEnroll() {
        final AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
        final HardwareAuthToken hardwareAuthToken =
                HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
        if (!aidlSession.hasContextMethods()) {
            this.mCancellationSignal = aidlSession.mSession.enroll(hardwareAuthToken);
            return;
        }
        OperationContextExt operationContext = getOperationContext();
        ((BiometricContextProvider) this.mBiometricContext)
                .subscribe(
                        operationContext,
                        new Consumer() { // from class:
                                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SemFingerprintEnrollClient semFingerprintEnrollClient =
                                        SemFingerprintEnrollClient.this;
                                AidlSession aidlSession2 = aidlSession;
                                HardwareAuthToken hardwareAuthToken2 = hardwareAuthToken;
                                OperationContext operationContext2 = (OperationContext) obj;
                                semFingerprintEnrollClient.getClass();
                                try {
                                    semFingerprintEnrollClient.mCancellationSignal =
                                            aidlSession2.mSession.enrollWithContext(
                                                    hardwareAuthToken2, operationContext2);
                                } catch (RemoteException e) {
                                    Slog.e(
                                            "FingerprintEnrollClient",
                                            "Remote exception when requesting enroll",
                                            e);
                                    semFingerprintEnrollClient.onError(2, 0);
                                    semFingerprintEnrollClient.mCallback.onClientFinished(
                                            semFingerprintEnrollClient, false);
                                }
                            }
                        },
                        new Consumer() { // from class:
                                         // com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                try {
                                    AidlSession.this.mSession.onContextChanged(
                                            (OperationContext) obj);
                                } catch (RemoteException e) {
                                    Slog.e(
                                            "FingerprintEnrollClient",
                                            "Unable to notify context changed",
                                            e);
                                }
                            }
                        },
                        null);
    }

    public SemBioAnalyticsManager getBioAnalyticsManager() {
        return SemBioAnalyticsManager.get();
    }

    public SemBioLoggingManager getBioLoggingManager() {
        return SemBioLoggingManager.get();
    }

    public int getLastErrorCode() {
        return this.mLastErrorCode;
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public final boolean hasReachedEnrollmentLimit() {
        return this.mBiometricUtils.getBiometricsForUser(this.mContext, this.mTargetUserId).size()
                >= this.mMaxTemplatesPerUser;
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void onAcquired(int i, int i2) {
        if (i != 7) {
            this.mAuthenticationStateListeners.onAuthenticationAcquired(
                    new AuthenticationAcquiredInfo.Builder(
                                    BiometricSourceType.FINGERPRINT,
                                    EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                            this.mEnrollReason),
                                    i)
                            .build());
        }
        String acquiredString = FingerprintManager.getAcquiredString(this.mContext, i, i2);
        if (acquiredString != null) {
            this.mAuthenticationStateListeners.onAuthenticationHelp(
                    new AuthenticationHelpInfo.Builder(
                                    BiometricSourceType.FINGERPRINT,
                                    EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                            this.mEnrollReason),
                                    acquiredString,
                                    i == 6 ? i2 + 1000 : i)
                            .build());
        }
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.mSensorProps;
        if (fingerprintSensorPropertiesInternal != null
                && fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
            SensorOverlays sensorOverlays = this.mSensorOverlays;
            if (sensorOverlays.mUdfpsOverlayController.isPresent()) {
                try {
                    ((IUdfpsOverlayController) sensorOverlays.mUdfpsOverlayController.get())
                            .onAcquired(this.mSensorId, i);
                } catch (RemoteException e) {
                    Slog.e("SensorOverlays", "Remote exception using overlay controller", e);
                }
            }
        }
        SensorOverlays sensorOverlays2 = this.mSensorOverlays;
        if (sensorOverlays2.mUdfpsOverlayController.isPresent()) {
            try {
                IUdfpsOverlayController iUdfpsOverlayController =
                        (IUdfpsOverlayController) sensorOverlays2.mUdfpsOverlayController.get();
                if (FingerprintManager.getAcquiredString(this.mContext, i, i2) != null) {
                    iUdfpsOverlayController.onEnrollmentHelp(this.mSensorId);
                }
            } catch (RemoteException e2) {
                Slog.e("SensorOverlays", "Remote exception using overlay controller", e2);
            }
        }
        this.mCallback.onBiometricAction();
        onAcquiredInternal(i, i2, true);
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null && i == 6 && i2 == 10003) {
            semUdfpsSysUiImpl.handleOnAcquired(i, i2);
        }
        if (i == 6 && i2 == 1002) {
            this.mDuplicatedImgCnt++;
        }
        if (FingerprintUtils.semIsQualityFailedEvent(i, i2)) {
            this.mTotalQualityErrorCount++;
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public final void onEnrollResult(BiometricAuthenticator.Identifier identifier, int i) {
        if (i == 0) {
            SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
            if (semUdfpsSysUiImpl != null) {
                semUdfpsSysUiImpl.stop();
            }
            ((Fingerprint) identifier).semSetDuplicatedImgCount(this.mDuplicatedImgCnt);
            getBioAnalyticsManager()
                    .fpInsertLog(3, "FPEN", Integer.toString(identifier.getBiometricId()));
            int i2 = (int) this.mRequestId;
            int i3 = this.mDuplicatedImgCnt;
            int i4 = this.mTotalQualityErrorCount;
            SemBioLoggingManager bioLoggingManager = getBioLoggingManager();
            bioLoggingManager
                    .getFpHandler()
                    .post(
                            new SemBioLoggingManager$$ExternalSyntheticLambda5(
                                    bioLoggingManager, i2, 0, "S", i3, i4));
        }
        super.onEnrollResult(identifier, i);
        SensorOverlays sensorOverlays = this.mSensorOverlays;
        if (sensorOverlays.mUdfpsOverlayController.isPresent()) {
            try {
                ((IUdfpsOverlayController) sensorOverlays.mUdfpsOverlayController.get())
                        .onEnrollmentProgress(this.mSensorId, i);
            } catch (RemoteException e) {
                Slog.e("SensorOverlays", "Remote exception using overlay controller", e);
            }
        }
        if (i == 0) {
            this.mSensorOverlays.hide(this.mSensorId);
            this.mAuthenticationStateListeners.onAuthenticationStopped(
                    new AuthenticationStoppedInfo.Builder(
                                    BiometricSourceType.FINGERPRINT,
                                    EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                            this.mEnrollReason))
                            .build());
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient,
              // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        if (i == 8) {
            this.mLastErrorCode = i2;
        } else {
            this.mLastErrorCode = i;
        }
        AuthenticationStateListeners authenticationStateListeners =
                this.mAuthenticationStateListeners;
        BiometricSourceType biometricSourceType = BiometricSourceType.FINGERPRINT;
        authenticationStateListeners.onAuthenticationError(
                new AuthenticationErrorInfo.Builder(
                                biometricSourceType,
                                EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                        this.mEnrollReason),
                                FingerprintManager.getErrorString(this.mContext, i, i2),
                                i)
                        .build());
        this.mLogger.logOnEnrolled(
                this.mTargetUserId,
                super.mEnrollReason,
                false,
                System.currentTimeMillis() - this.mEnrollmentStartTimeMs);
        onErrorInternal(i, i2, true);
        this.mSensorOverlays.hide(this.mSensorId);
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(
                                biometricSourceType,
                                EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                        this.mEnrollReason))
                        .build());
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.handleOnError(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemUdfpsConstraintStatusListener
    public final void onOneHandModeEnabled() {
        onError(8, 5001);
        cancel();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void onPointerDown(PointerContext pointerContext) {
        try {
            AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            if (aidlSession.hasContextMethods()) {
                aidlSession.mSession.onPointerDownWithContext(pointerContext);
            } else {
                aidlSession.mSession.onPointerDown(
                        pointerContext.pointerId,
                        (int) pointerContext.x,
                        (int) pointerContext.y,
                        pointerContext.minor,
                        pointerContext.major);
            }
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i = this.mSensorId;
            IFingerprintServiceReceiver iFingerprintServiceReceiver =
                    clientMonitorCallbackConverter.mFingerprintServiceReceiver;
            if (iFingerprintServiceReceiver != null) {
                iFingerprintServiceReceiver.onUdfpsPointerDown(i);
            }
        } catch (RemoteException e) {
            Slog.e("FingerprintEnrollClient", "Unable to send pointer down", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void onPointerUp(PointerContext pointerContext) {
        try {
            AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            if (aidlSession.hasContextMethods()) {
                aidlSession.mSession.onPointerUpWithContext(pointerContext);
            } else {
                aidlSession.mSession.onPointerUp(pointerContext.pointerId);
            }
            ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.mListener;
            int i = this.mSensorId;
            IFingerprintServiceReceiver iFingerprintServiceReceiver =
                    clientMonitorCallbackConverter.mFingerprintServiceReceiver;
            if (iFingerprintServiceReceiver != null) {
                iFingerprintServiceReceiver.onUdfpsPointerUp(i);
            }
        } catch (RemoteException e) {
            Slog.e("FingerprintEnrollClient", "Unable to send pointer up", e);
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
        try {
            if (i == 1) {
                IFingerprintServiceReceiver iFingerprintServiceReceiver =
                        this.mListener.mFingerprintServiceReceiver;
                if (iFingerprintServiceReceiver != null) {
                    iFingerprintServiceReceiver.onUdfpsOverlayShown();
                }
            } else if (i != 2) {
                Slog.w("FingerprintEnrollClient", "No matching event for onUdfpsUiEvent");
            } else {
                ((AidlSession) this.mLazyDaemon.get()).mSession.onUiReady();
            }
        } catch (RemoteException e) {
            Slog.e("FingerprintEnrollClient", "Unable to send onUdfpsUiEvent", e);
        }
    }

    public final void request(int i) {
        if (((AidlSession) this.mLazyDaemon.get()) == null) {
            return;
        }
        new SemFpBaseRequestClient(
                        this.mContext,
                        this.mBiometricContext,
                        this.mLazyDaemon,
                        null,
                        null,
                        this.mSensorId,
                        0,
                        "FingerprintRequestClient",
                        false,
                        i,
                        0,
                        null,
                        null)
                .startWithoutScheduler();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public final void setIgnoreDisplayTouches(boolean z) {
        try {
            ((AidlSession) this.mLazyDaemon.get()).mSession.setIgnoreDisplayTouches(z);
        } catch (RemoteException e) {
            Slog.e("FingerprintEnrollClient", "Unable to send setIgnoreDisplayTouches", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        Context context = this.mContext;
        Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .cancelAsUser("FingerprintEnroll", 1, UserHandle.CURRENT);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        boolean z = SemBiometricFeature.FEATURE_LOGGING_MODE;
        request(18);
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.start();
        }
        int i = (int) this.mRequestId;
        String str = this.mOwner;
        SemBioLoggingManager bioLoggingManager = getBioLoggingManager();
        bioLoggingManager
                .getFpHandler()
                .post(
                        new SemBioLoggingManager$$ExternalSyntheticLambda4(
                                bioLoggingManager, "E", str, i));
        this.mSensorOverlays.show(
                this.mSensorId,
                EnrollClient.getRequestReasonFromFingerprintEnrollReason(this.mEnrollReason),
                this);
        this.mAuthenticationStateListeners.onAuthenticationStarted(
                new AuthenticationStartedInfo.Builder(
                                BiometricSourceType.FINGERPRINT,
                                EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                        this.mEnrollReason))
                        .build());
        Context context = this.mContext;
        Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .cancelAsUser("FingerprintBadCalibration", 1, UserHandle.CURRENT);
        try {
            doEnroll();
        } catch (RemoteException e) {
            Slog.e("FingerprintEnrollClient", "Remote exception when requesting enroll", e);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void stopHalOperation() {
        SemUdfpsSysUiImpl semUdfpsSysUiImpl = this.mUdfpsImpl;
        if (semUdfpsSysUiImpl != null) {
            semUdfpsSysUiImpl.stop();
        }
        this.mSensorOverlays.hide(this.mSensorId);
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(
                                BiometricSourceType.FINGERPRINT,
                                EnrollClient.getRequestReasonFromFingerprintEnrollReason(
                                        this.mEnrollReason))
                        .build());
        unsubscribeBiometricContext();
        ICancellationSignal iCancellationSignal = this.mCancellationSignal;
        if (iCancellationSignal == null) {
            Slog.e("FingerprintEnrollClient", "cancellation signal was null");
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (RemoteException e) {
            Slog.e("FingerprintEnrollClient", "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void vibrateSuccess() {
        Utils.semVibrate(this.mContext, 1);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final ClientMonitorCallback wrapCallbackForStart(
            ClientMonitorCallback clientMonitorCallback) {
        return new ClientMonitorCompositeCallback(
                this.mALSProbeCallback,
                new HalClientMonitor.AnonymousClass1(),
                clientMonitorCallback);
    }
}
