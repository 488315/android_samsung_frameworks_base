package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.content.pm.UserInfo;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricStateCallback;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.LockoutCache;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.StopUserClient;
import com.android.server.biometrics.sensors.UserAwareBiometricScheduler;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx;
import com.android.server.biometrics.sensors.fingerprint.SemFpRequestCommands;
import com.android.server.biometrics.sensors.fingerprint.SemFpUserAwareScheduler;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlLockoutHalImpl;
import com.android.server.biometrics.sensors.fingerprint.aidl.Sensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class Sensor {
    public final Map mAuthenticatorIds;
    public final Context mContext;
    public AidlSession mCurrentSession;
    public final Handler mHandler;
    public final Supplier mLazySession;
    public final LockoutCache mLockoutCache;
    public SemFpAidlLockoutHalImpl mLockoutHalImpl;
    public final FingerprintProvider mProvider;
    public final UserAwareBiometricScheduler mScheduler;
    public final FingerprintSensorPropertiesInternal mSensorProperties;
    public final String mTag;
    public boolean mTestHalEnabled;
    public final IBinder mToken;

    public class HalSessionCallback extends ISessionCallback.Stub {
        public AuthSessionCoordinator mAuthSessionCoordinator;
        public final Callback mCallback;
        public final Context mContext;
        public SemFpHalCallbackEx mHalListener;
        public final Handler mHandler;
        public final LockoutCache mLockoutCache;
        public SemFpAidlLockoutHalImpl mLockoutHalImpl;
        public final LockoutResetDispatcher mLockoutResetDispatcher;
        public final UserAwareBiometricScheduler mScheduler;
        public final int mSensorId;
        public final String mTag;
        public final int mUserId;

        public interface Callback {
            void onHardwareUnavailable();
        }

        public String getInterfaceHash() {
            return "637371b53fb7faf9bd43aa51b72c23852d6e6d96";
        }

        public int getInterfaceVersion() {
            return 3;
        }

        public HalSessionCallback(Context context, Handler handler, String str, UserAwareBiometricScheduler userAwareBiometricScheduler, int i, int i2, LockoutCache lockoutCache, LockoutResetDispatcher lockoutResetDispatcher, AuthSessionCoordinator authSessionCoordinator, Callback callback) {
            this.mContext = context;
            this.mHandler = handler;
            this.mTag = str;
            this.mScheduler = userAwareBiometricScheduler;
            this.mSensorId = i;
            this.mUserId = i2;
            this.mLockoutCache = lockoutCache;
            this.mLockoutResetDispatcher = lockoutResetDispatcher;
            this.mAuthSessionCoordinator = authSessionCoordinator;
            this.mCallback = callback;
        }

        public void setHalListener(SemFpHalCallbackEx semFpHalCallbackEx) {
            this.mHalListener = semFpHalCallbackEx;
        }

        public void setLockoutImpl(SemFpAidlLockoutHalImpl semFpAidlLockoutHalImpl) {
            this.mLockoutHalImpl = semFpAidlLockoutHalImpl;
        }

        public void onChallengeGenerated(final long j) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onChallengeGenerated$0(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChallengeGenerated$0(long j) {
            Slog.i("FingerprintService", "handleChallengeGenerated: onChallengeGenerated");
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintGenerateChallengeClient)) {
                Slog.e(this.mTag, "onChallengeGenerated for wrong client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FingerprintGenerateChallengeClient) currentClient).onChallengeGenerated(this.mSensorId, this.mUserId, j);
            SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
            if (semFpHalCallbackEx != null) {
                semFpHalCallbackEx.onChallengeGenerated(this.mSensorId, this.mUserId, j);
            }
        }

        public void onChallengeRevoked(final long j) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onChallengeRevoked$1(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChallengeRevoked$1(long j) {
            Slog.i("FingerprintService", "handleChallengeRevoked: onChallengeRevoked");
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintRevokeChallengeClient)) {
                Slog.e(this.mTag, "onChallengeRevoked for wrong client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FingerprintRevokeChallengeClient) currentClient).onChallengeRevoked(this.mSensorId, this.mUserId, j);
            SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
            if (semFpHalCallbackEx != null) {
                semFpHalCallbackEx.onChallengeRevoked(this.mSensorId, this.mUserId, j);
            }
        }

        public void onAcquired(final byte b, final int i) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAcquired$2(b, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$2(byte b, int i) {
            int frameworkAcquiredInfo = AidlConversionUtils.toFrameworkAcquiredInfo(b);
            Slog.i("FingerprintService", "handleAcquired: acquiredInfo=" + frameworkAcquiredInfo + ", vendor=" + i);
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                if (FingerprintUtils.semIsSkipEvent(frameworkAcquiredInfo, i)) {
                    return;
                } else {
                    i = FingerprintUtils.semUpdateVendorInfo(frameworkAcquiredInfo, i);
                }
            }
            int i2 = i;
            if (FingerprintUtils.semIsInternalClientFreeEvent(frameworkAcquiredInfo, i2)) {
                handleInternalClientFreeEvent(i2);
                return;
            }
            if (FingerprintUtils.semIsRequestCommandEvent(frameworkAcquiredInfo, i2)) {
                handleRequestEvent(i2);
                return;
            }
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof AcquisitionClient)) {
                Slog.e(this.mTag, "onAcquired for non-acquisition client: " + Utils.getClientName(currentClient));
                return;
            }
            AcquisitionClient acquisitionClient = (AcquisitionClient) currentClient;
            acquisitionClient.onAcquired(frameworkAcquiredInfo, i2);
            SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
            if (semFpHalCallbackEx != null) {
                semFpHalCallbackEx.onAcquire(this.mSensorId, this.mUserId, acquisitionClient.getProtoEnum(), frameworkAcquiredInfo, i2);
            }
        }

        public final void handleInternalClientFreeEvent(int i) {
            SemFpHalCallbackEx semFpHalCallbackEx;
            SemFpHalCallbackEx semFpHalCallbackEx2;
            if (i != 20001 && i != 20002) {
                if ((i == 30001 || i == 30002) && SemBiometricFeature.FP_FEATURE_SENSOR_LIMITATION_SPEN_CHARGER && (semFpHalCallbackEx2 = this.mHalListener) != null) {
                    semFpHalCallbackEx2.onSpenEvent(this.mSensorId, i);
                    return;
                }
                return;
            }
            if (!SemBiometricFeature.FP_FEATURE_GESTURE_MODE || (semFpHalCallbackEx = this.mHalListener) == null) {
                return;
            }
            semFpHalCallbackEx.onGestureEvent(this.mSensorId, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void handleRequestEvent(int i) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof SemFpRequestCommands)) {
                Slog.e(this.mTag, "SensorTestEvent for non-request client: " + Utils.getClientName(currentClient));
                return;
            }
            ((SemFpRequestCommands) currentClient).onRequestResult(i);
        }

        public void onError(final byte b, final int i) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onError$3(b, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onError$3(byte b, int i) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            Slog.d(this.mTag, "onError, client: " + Utils.getClientName(currentClient) + ", error: " + ((int) b) + ", vendorCode: " + i);
            if (!(currentClient instanceof ErrorConsumer)) {
                Slog.e(this.mTag, "onError for non-error consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((ErrorConsumer) currentClient).onError(AidlConversionUtils.toFrameworkError(b), i);
            if (b == 1) {
                this.mCallback.onHardwareUnavailable();
            }
            SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
            if (semFpHalCallbackEx != null) {
                semFpHalCallbackEx.onError(this.mSensorId, this.mUserId, currentClient.getProtoEnum(), AidlConversionUtils.toFrameworkError(b), i);
            }
        }

        public void onEnrollmentProgress(final int i, final int i2) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentProgress$4(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollmentProgress$4(int i, int i2) {
            SemFpHalCallbackEx semFpHalCallbackEx;
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintEnrollClient)) {
                Slog.e(this.mTag, "onEnrollmentProgress for non-enroll client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FingerprintEnrollClient) currentClient).onEnrollResult(new Fingerprint(FingerprintUtils.getInstance(this.mSensorId).getUniqueName(this.mContext, currentClient.getTargetUserId()), i, this.mSensorId), i2);
            if (i2 != 0 || (semFpHalCallbackEx = this.mHalListener) == null) {
                return;
            }
            semFpHalCallbackEx.onEnrolled(this.mSensorId, this.mUserId);
        }

        public void onAuthenticationSucceeded(final int i, final HardwareAuthToken hardwareAuthToken) {
            long j;
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
                BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
                if (currentClient instanceof FingerprintAuthenticationClient) {
                    FingerprintAuthenticationClient fingerprintAuthenticationClient = (FingerprintAuthenticationClient) currentClient;
                    if (fingerprintAuthenticationClient.useEarlyAuthenticationResult()) {
                        fingerprintAuthenticationClient.handleEarlyAuthenticationResult();
                        j = 10;
                        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                Sensor.HalSessionCallback.this.lambda$onAuthenticationSucceeded$5(i, hardwareAuthToken);
                            }
                        }, j);
                    }
                }
            }
            j = 0;
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticationSucceeded$5(i, hardwareAuthToken);
                }
            }, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$5(int i, HardwareAuthToken hardwareAuthToken) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof AuthenticationConsumer)) {
                Slog.e(this.mTag, "onAuthenticationSucceeded for non-authentication consumer: " + Utils.getClientName(currentClient));
                return;
            }
            Slog.i("FingerprintService", "handleAuthenticated: true");
            this.mLockoutHalImpl.resetFailedAttemptsForUser(true, this.mUserId);
            AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) currentClient;
            Fingerprint fingerprint = new Fingerprint(FingerprintUtils.getLegacyInstance(this.mSensorId).getFingerprintName(this.mContext, i, this.mUserId), i, this.mSensorId);
            byte[] byteArray = HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
            ArrayList arrayList = new ArrayList();
            for (byte b : byteArray) {
                arrayList.add(Byte.valueOf(b));
            }
            authenticationConsumer.onAuthenticated(fingerprint, true, arrayList);
            SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
            if (semFpHalCallbackEx != null) {
                semFpHalCallbackEx.onAuthenticated(this.mSensorId, this.mUserId, i);
            }
        }

        public void onAuthenticationFailed() {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticationFailed$6();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onAuthenticationFailed$6() {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof AuthenticationConsumer)) {
                Slog.e(this.mTag, "onAuthenticationFailed for non-authentication consumer: " + Utils.getClientName(currentClient));
                return;
            }
            Slog.i("FingerprintService", "handleAuthenticated: false");
            AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) currentClient;
            authenticationConsumer.onAuthenticated(new Fingerprint("", 0, this.mSensorId), false, null);
            SemFpHalCallbackEx semFpHalCallbackEx = this.mHalListener;
            if (semFpHalCallbackEx != null) {
                semFpHalCallbackEx.onAuthenticated(this.mSensorId, this.mUserId, 0);
            }
            if (authenticationConsumer.canIgnoreLockout()) {
                return;
            }
            this.mLockoutHalImpl.addFailedAttemptForUser(this.mUserId);
            int lockoutModeForUser = this.mLockoutHalImpl.getLockoutModeForUser(this.mUserId);
            if (lockoutModeForUser == 2) {
                onLockoutPermanent();
            } else if (lockoutModeForUser == 1) {
                onLockoutTimed(30000L);
            }
        }

        public void onLockoutTimed(final long j) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onLockoutTimed$7(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onLockoutTimed$7(long j) {
            Slog.i(this.mTag, "onLockoutTimed: " + j);
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof LockoutConsumer)) {
                Slog.e(this.mTag, "onLockoutTimed for non-lockout consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((LockoutConsumer) currentClient).onLockoutTimed(j);
        }

        public void onLockoutPermanent() {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onLockoutPermanent$8();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onLockoutPermanent$8() {
            Slog.i(this.mTag, "onLockoutPermanent");
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof LockoutConsumer)) {
                Slog.e(this.mTag, "onLockoutPermanent for non-lockout consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((LockoutConsumer) currentClient).onLockoutPermanent();
        }

        public void onLockoutCleared() {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onLockoutCleared$9();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLockoutCleared$9() {
            Slog.i(this.mTag, "onLockoutCleared");
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintResetLockoutClient)) {
                Slog.d(this.mTag, "onLockoutCleared outside of resetLockout by HAL");
                int i = this.mSensorId;
                FingerprintResetLockoutClient.resetLocalLockoutStateToNone(i, this.mUserId, this.mLockoutCache, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, Utils.getCurrentStrength(i), -1L);
                SemFpAidlLockoutHalImpl semFpAidlLockoutHalImpl = this.mLockoutHalImpl;
                if (semFpAidlLockoutHalImpl != null) {
                    semFpAidlLockoutHalImpl.resetFailedAttemptsForUser(false, this.mUserId);
                    return;
                }
                return;
            }
            Slog.d(this.mTag, "onLockoutCleared after resetLockout");
            ((FingerprintResetLockoutClient) currentClient).onLockoutCleared();
            SemFpAidlLockoutHalImpl semFpAidlLockoutHalImpl2 = this.mLockoutHalImpl;
            if (semFpAidlLockoutHalImpl2 != null) {
                semFpAidlLockoutHalImpl2.resetFailedAttemptsForUser(true, this.mUserId);
            }
        }

        public void onInteractionDetected() {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onInteractionDetected$10();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInteractionDetected$10() {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintDetectClient)) {
                Slog.e(this.mTag, "onInteractionDetected for non-detect client: " + Utils.getClientName(currentClient));
                return;
            }
            Slog.i("FingerprintService", "onInteractionDetected");
            ((FingerprintDetectClient) currentClient).onInteractionDetected();
        }

        public void onEnrollmentsEnumerated(final int[] iArr) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentsEnumerated$11(iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onEnrollmentsEnumerated$11(int[] iArr) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof EnumerateConsumer)) {
                Slog.e(this.mTag, "onEnrollmentsEnumerated for non-enumerate consumer: " + Utils.getClientName(currentClient));
                return;
            }
            Slog.i("FingerprintService", "onEnrollmentsEnumerated: " + iArr.length);
            EnumerateConsumer enumerateConsumer = (EnumerateConsumer) currentClient;
            if (iArr.length > 0) {
                for (int i = 0; i < iArr.length; i++) {
                    enumerateConsumer.onEnumerationResult(new Fingerprint("", iArr[i], this.mSensorId), (iArr.length - i) - 1);
                }
                return;
            }
            enumerateConsumer.onEnumerationResult(null, 0);
        }

        public void onEnrollmentsRemoved(final int[] iArr) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentsRemoved$12(iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onEnrollmentsRemoved$12(int[] iArr) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof RemovalConsumer)) {
                Slog.e(this.mTag, "onRemoved for non-removal consumer: " + Utils.getClientName(currentClient));
                return;
            }
            Slog.i("FingerprintService", "onEnrollmentsRemoved: " + iArr.length);
            RemovalConsumer removalConsumer = (RemovalConsumer) currentClient;
            if (iArr.length > 0) {
                for (int i = 0; i < iArr.length; i++) {
                    removalConsumer.onRemoved(new Fingerprint("", iArr[i], this.mSensorId), (iArr.length - i) - 1);
                }
                return;
            }
            removalConsumer.onRemoved(null, 0);
        }

        public void onAuthenticatorIdRetrieved(final long j) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticatorIdRetrieved$13(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticatorIdRetrieved$13(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintGetAuthenticatorIdClient)) {
                Slog.e(this.mTag, "onAuthenticatorIdRetrieved for wrong consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((FingerprintGetAuthenticatorIdClient) currentClient).onAuthenticatorIdRetrieved(j);
        }

        public void onAuthenticatorIdInvalidated(final long j) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticatorIdInvalidated$14(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticatorIdInvalidated$14(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FingerprintInvalidationClient)) {
                Slog.e(this.mTag, "onAuthenticatorIdInvalidated for wrong consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((FingerprintInvalidationClient) currentClient).onAuthenticatorIdInvalidated(j);
        }

        public void onSessionClosed() {
            Slog.d("FingerprintService", "onSessionClosed");
            Handler handler = this.mHandler;
            UserAwareBiometricScheduler userAwareBiometricScheduler = this.mScheduler;
            Objects.requireNonNull(userAwareBiometricScheduler);
            handler.post(new com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda2(userAwareBiometricScheduler));
        }
    }

    public Sensor(String str, FingerprintProvider fingerprintProvider, Context context, Handler handler, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, LockoutResetDispatcher lockoutResetDispatcher, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, BiometricContext biometricContext, AidlSession aidlSession) {
        this.mTag = str;
        this.mProvider = fingerprintProvider;
        this.mContext = context;
        this.mToken = new Binder();
        this.mHandler = handler;
        this.mSensorProperties = fingerprintSensorPropertiesInternal;
        this.mLockoutCache = new LockoutCache();
        this.mLockoutHalImpl = new SemFpAidlLockoutHalImpl(context, new SemFpAidlLockoutHalImpl.LockoutResetCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlLockoutHalImpl.LockoutResetCallback
            public final void onLockoutReset(int i) {
                Sensor.this.lambda$new$0(i);
            }
        });
        this.mScheduler = new SemFpUserAwareScheduler(str, gestureAvailabilityDispatcher, new UserAwareBiometricScheduler.CurrentUserRetriever() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda1
            @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever
            public final int getCurrentUserId() {
                int lambda$new$1;
                lambda$new$1 = Sensor.this.lambda$new$1();
                return lambda$new$1;
            }
        }, new C09301(biometricContext, lockoutResetDispatcher, fingerprintProvider));
        this.mAuthenticatorIds = new HashMap();
        this.mLazySession = new Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                AidlSession lambda$new$2;
                lambda$new$2 = Sensor.this.lambda$new$2();
                return lambda$new$2;
            }
        };
        this.mCurrentSession = aidlSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession == null || aidlSession.getUserId() != i) {
            return;
        }
        this.mCurrentSession.getHalSessionCallback().onLockoutCleared();
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1 */
    public class C09301 implements UserAwareBiometricScheduler.UserSwitchCallback {
        public final /* synthetic */ BiometricContext val$biometricContext;
        public final /* synthetic */ LockoutResetDispatcher val$lockoutResetDispatcher;
        public final /* synthetic */ FingerprintProvider val$provider;

        public C09301(BiometricContext biometricContext, LockoutResetDispatcher lockoutResetDispatcher, FingerprintProvider fingerprintProvider) {
            this.val$biometricContext = biometricContext;
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
            this.val$provider = fingerprintProvider;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        public StopUserClient getStopUserClient(int i) {
            return new FingerprintStopUserClient(Sensor.this.mContext, Sensor.this.mLazySession, Sensor.this.mToken, i, Sensor.this.mSensorProperties.sensorId, BiometricLogger.ofUnknown(Sensor.this.mContext), this.val$biometricContext, new StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda0
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    Sensor.C09301.this.lambda$getStopUserClient$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$0() {
            Sensor.this.mCurrentSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        public StartUserClient getStartUserClient(int i) {
            final int i2 = Sensor.this.mSensorProperties.sensorId;
            final HalSessionCallback halSessionCallback = new HalSessionCallback(Sensor.this.mContext, Sensor.this.mHandler, Sensor.this.mTag, Sensor.this.mScheduler, i2, i, Sensor.this.mLockoutCache, this.val$lockoutResetDispatcher, this.val$biometricContext.getAuthSessionCoordinator(), new HalSessionCallback.Callback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda1
                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.HalSessionCallback.Callback
                public final void onHardwareUnavailable() {
                    Sensor.C09301.this.lambda$getStartUserClient$1();
                }
            });
            halSessionCallback.setHalListener(this.val$provider.getHalListener());
            halSessionCallback.setLockoutImpl(Sensor.this.mLockoutHalImpl);
            final FingerprintProvider fingerprintProvider = this.val$provider;
            StartUserClient.UserStartedCallback userStartedCallback = new StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda2
                @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
                public final void onUserStarted(int i3, Object obj, int i4) {
                    Sensor.C09301.this.lambda$getStartUserClient$2(halSessionCallback, fingerprintProvider, i2, i3, (ISession) obj, i4);
                }
            };
            Context context = Sensor.this.mContext;
            final FingerprintProvider fingerprintProvider2 = this.val$provider;
            Objects.requireNonNull(fingerprintProvider2);
            return new FingerprintStartUserClient(context, new Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return FingerprintProvider.this.getHalInstance();
                }
            }, Sensor.this.mToken, i, Sensor.this.mSensorProperties.sensorId, BiometricLogger.ofUnknown(Sensor.this.mContext), this.val$biometricContext, halSessionCallback, userStartedCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStartUserClient$1() {
            Slog.e(Sensor.this.mTag, "Got ERROR_HW_UNAVAILABLE");
            Sensor.this.mCurrentSession = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStartUserClient$2(HalSessionCallback halSessionCallback, FingerprintProvider fingerprintProvider, int i, int i2, ISession iSession, int i3) {
            Slog.d(Sensor.this.mTag, "New session created for user: " + i2 + " with hal version: " + i3);
            Sensor.this.mCurrentSession = new AidlSession(i3, iSession, i2, halSessionCallback);
            AidlSession aidlSession = Sensor.this.mCurrentSession;
            Objects.requireNonNull(fingerprintProvider);
            aidlSession.setLazySehFingerprint(new FingerprintProvider$$ExternalSyntheticLambda0(fingerprintProvider));
            if (FingerprintUtils.getInstance(i).isInvalidationInProgress(Sensor.this.mContext, i2)) {
                Slog.w(Sensor.this.mTag, "Scheduling unfinished invalidation request for sensor: " + i + ", user: " + i2);
                fingerprintProvider.scheduleInvalidationRequest(i, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$1() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            return aidlSession.getUserId();
        }
        return -10000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AidlSession lambda$new$2() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            return aidlSession;
        }
        return null;
    }

    public Sensor(String str, FingerprintProvider fingerprintProvider, Context context, Handler handler, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, LockoutResetDispatcher lockoutResetDispatcher, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, BiometricContext biometricContext) {
        this(str, fingerprintProvider, context, handler, fingerprintSensorPropertiesInternal, lockoutResetDispatcher, gestureAvailabilityDispatcher, biometricContext, null);
    }

    public Supplier getLazySession() {
        return this.mLazySession;
    }

    public FingerprintSensorPropertiesInternal getSensorProperties() {
        return this.mSensorProperties;
    }

    public AidlSession getSessionForUser(int i) {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession == null || aidlSession.getUserId() != i) {
            return null;
        }
        return this.mCurrentSession;
    }

    public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, BiometricStateCallback biometricStateCallback) {
        return new BiometricTestSessionImpl(this.mContext, this.mSensorProperties.sensorId, iTestSessionCallback, biometricStateCallback, this.mProvider, this);
    }

    public SemFpUserAwareScheduler getScheduler() {
        return (SemFpUserAwareScheduler) this.mScheduler;
    }

    public LockoutCache getLockoutCache() {
        return this.mLockoutCache;
    }

    public Map getAuthenticatorIds() {
        return this.mAuthenticatorIds;
    }

    public void setTestHalEnabled(boolean z) {
        Slog.w(this.mTag, "setTestHalEnabled: " + z);
        if (z != this.mTestHalEnabled) {
            try {
                if (this.mCurrentSession != null) {
                    Slog.d(this.mTag, "Closing old session");
                    this.mCurrentSession.getSession().close();
                }
            } catch (RemoteException e) {
                Slog.e(this.mTag, "RemoteException", e);
            }
            this.mCurrentSession = null;
        }
        this.mTestHalEnabled = z;
    }

    public void dumpProtoState(int i, ProtoOutputStream protoOutputStream, boolean z) {
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1120986464257L, this.mSensorProperties.sensorId);
        protoOutputStream.write(1159641169922L, 1);
        if (this.mSensorProperties.isAnyUdfpsType()) {
            protoOutputStream.write(2259152797704L, 0);
        }
        protoOutputStream.write(1120986464259L, Utils.getCurrentStrength(this.mSensorProperties.sensorId));
        protoOutputStream.write(1146756268036L, this.mScheduler.dumpProtoState(z));
        Iterator it = UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            long start2 = protoOutputStream.start(2246267895813L);
            protoOutputStream.write(1120986464257L, identifier);
            protoOutputStream.write(1120986464258L, FingerprintUtils.getInstance(this.mSensorProperties.sensorId).getBiometricsForUser(this.mContext, identifier).size());
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
        protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
        protoOutputStream.end(start);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onBinderDied() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof ErrorConsumer) {
            Slog.e(this.mTag, "Sending ERROR_HW_UNAVAILABLE for client: " + currentClient);
            ((ErrorConsumer) currentClient).onError(1, 0);
            FrameworkStatsLog.write(148, 1, 1, -1);
        } else if (currentClient != 0) {
            currentClient.cancel();
        }
        this.mScheduler.recordCrashState();
        this.mScheduler.reset();
        this.mCurrentSession = null;
    }

    public int getRemainingLockoutTime(int i) {
        SemFpAidlLockoutHalImpl semFpAidlLockoutHalImpl = this.mLockoutHalImpl;
        if (semFpAidlLockoutHalImpl != null) {
            return semFpAidlLockoutHalImpl.getRemainingLockoutTime(i);
        }
        return 0;
    }

    public int getCurrentSessionUserId() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            return aidlSession.getUserId();
        }
        return 0;
    }

    public void generateEvent(int i) {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            aidlSession.getHalSessionCallback().onAcquired((byte) 7, i);
        }
    }
}
