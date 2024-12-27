package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Handler;
import android.util.Slog;

import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AcquisitionClient;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda20;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpCallbackDispatcher;
import com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener;
import com.android.server.biometrics.sensors.fingerprint.SemFpEventListener;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsHelper;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class SemFpAidlResponseHandler extends ISessionCallback.Stub {
    public final AidlResponseHandler$AidlResponseHandlerCallback mAidlResponseHandlerCallback;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final Context mContext;
    public final SemFpCallbackDispatcher mHalCallbackEx;
    public final SemFpAidlLockoutHalImpl mLockoutHalImpl;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final LockoutTracker mLockoutTracker;
    public final FingerprintSensorPropertiesInternal mProperties;
    public final BiometricScheduler mScheduler;
    public final int mSensorId;
    public final int mUserId;

    public SemFpAidlResponseHandler(
            Context context,
            BiometricScheduler biometricScheduler,
            int i,
            int i2,
            LockoutTracker lockoutTracker,
            LockoutResetDispatcher lockoutResetDispatcher,
            AuthSessionCoordinator authSessionCoordinator,
            AidlResponseHandler$AidlResponseHandlerCallback
                    aidlResponseHandler$AidlResponseHandlerCallback,
            SemFpCallbackDispatcher semFpCallbackDispatcher,
            SemFpAidlLockoutHalImpl semFpAidlLockoutHalImpl,
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        this.mContext = context;
        this.mScheduler = biometricScheduler;
        this.mSensorId = i;
        this.mUserId = i2;
        this.mLockoutTracker = lockoutTracker;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mAidlResponseHandlerCallback = aidlResponseHandler$AidlResponseHandlerCallback;
        this.mHalCallbackEx = semFpCallbackDispatcher;
        this.mLockoutHalImpl = semFpAidlLockoutHalImpl;
        this.mProperties = fingerprintSensorPropertiesInternal;
    }

    public final String getInterfaceHash() {
        return "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
    }

    public final int getInterfaceVersion() {
        return 4;
    }

    public final void handleResponse(
            final Class cls,
            final Consumer consumer,
            final AidlResponseHandler$$ExternalSyntheticLambda8
                    aidlResponseHandler$$ExternalSyntheticLambda8) {
        this.mScheduler.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemFpAidlResponseHandler semFpAidlResponseHandler =
                                SemFpAidlResponseHandler.this;
                        Class cls2 = cls;
                        Consumer consumer2 = consumer;
                        Consumer consumer3 = aidlResponseHandler$$ExternalSyntheticLambda8;
                        BaseClientMonitor currentClient =
                                semFpAidlResponseHandler.mScheduler.getCurrentClient();
                        if (cls2.isInstance(currentClient)) {
                            consumer2.accept(currentClient);
                            return;
                        }
                        Slog.e(
                                "AidlResponseHandler",
                                "Client monitor is not an instance of ".concat(cls2.getName()));
                        if (consumer3 != null) {
                            consumer3.accept(currentClient);
                        }
                    }
                });
    }

    public final void handleResponse(
            final Class cls, final Consumer consumer, final Consumer consumer2, long j) {
        this.mScheduler.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemFpAidlResponseHandler semFpAidlResponseHandler =
                                SemFpAidlResponseHandler.this;
                        Class cls2 = cls;
                        Consumer consumer3 = consumer;
                        Consumer consumer4 = consumer2;
                        BaseClientMonitor currentClient =
                                semFpAidlResponseHandler.mScheduler.getCurrentClient();
                        if (cls2.isInstance(currentClient)) {
                            consumer3.accept(currentClient);
                            return;
                        }
                        Slog.e(
                                "AidlResponseHandler",
                                "Client monitor is not an instance of ".concat(cls2.getName()));
                        if (consumer4 != null) {
                            consumer4.accept(currentClient);
                        }
                    }
                },
                j);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x003d, code lost:

       if (r4 != 14) goto L43;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAcquired(byte r4, int r5) {
        /*
            r3 = this;
            r0 = 8
            if (r4 != 0) goto L5
            goto L40
        L5:
            r1 = 1
            if (r4 != r1) goto La
            r0 = 0
            goto L40
        La:
            r2 = 2
            if (r4 != r2) goto Lf
        Ld:
            r0 = r1
            goto L40
        Lf:
            r1 = 3
            if (r4 != r1) goto L14
        L12:
            r0 = r2
            goto L40
        L14:
            r2 = 4
            if (r4 != r2) goto L18
            goto Ld
        L18:
            r1 = 5
            if (r4 != r1) goto L1c
            goto L12
        L1c:
            r2 = 6
            if (r4 != r2) goto L20
            goto Ld
        L20:
            r1 = 7
            if (r4 != r1) goto L24
            goto L12
        L24:
            if (r4 != r0) goto L27
            goto Ld
        L27:
            r1 = 9
            if (r4 != r1) goto L2c
            goto L40
        L2c:
            r2 = 10
            if (r4 != r2) goto L31
            goto L12
        L31:
            r2 = 11
            if (r4 != r2) goto L36
            goto Ld
        L36:
            r1 = 12
            if (r4 != r1) goto L3b
            goto L40
        L3b:
            r1 = 14
            if (r4 != r1) goto L40
            goto L12
        L40:
            r3.onAcquired(r0, r5)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler.onAcquired(byte,"
                    + " int):void");
    }

    public final void onAcquired(final int i, int i2) {
        Slog.i("FingerprintService", "handleAcquired: acquiredInfo=" + i + ", vendor=" + i2);
        if (this.mProperties.isOpticalType() && i == 6 && i2 == 10004) {
            return;
        }
        final int i3 = (this.mProperties.isOpticalType() && i == 6 && i2 == 70001) ? 10004 : i2;
        if (i == 6 && (i3 == 20001 || i3 == 20002 || i3 == 30001 || i3 == 30002)) {
            this.mScheduler.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFpAidlResponseHandler semFpAidlResponseHandler =
                                    SemFpAidlResponseHandler.this;
                            int i4 = i3;
                            semFpAidlResponseHandler.getClass();
                            if (i4 == 20001 || i4 == 20002) {
                                Iterator it =
                                        ((ArrayList)
                                                        semFpAidlResponseHandler
                                                                .mHalCallbackEx
                                                                .mEventListeners)
                                                .iterator();
                                while (it.hasNext()) {
                                    ((SemFpEventListener) it.next()).getClass();
                                }
                            } else if (i4 == 30001 || i4 == 30002) {
                                Iterator it2 =
                                        ((ArrayList)
                                                        semFpAidlResponseHandler
                                                                .mHalCallbackEx
                                                                .mEventListeners)
                                                .iterator();
                                while (it2.hasNext()) {
                                    ((SemFpEventListener) it2.next()).onSpenEvent(i4);
                                }
                            }
                        }
                    });
            return;
        }
        if (i == 6 && (i3 == 10008 || i3 == 10009)) {
            handleResponse(
                    SemFpBaseRequestClient.class,
                    new Consumer() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda9
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SemFpBaseRequestClient) obj).onRequestResult(i3);
                        }
                    },
                    null,
                    0L);
        } else {
            this.mScheduler.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFpAidlResponseHandler semFpAidlResponseHandler =
                                    SemFpAidlResponseHandler.this;
                            int i4 = i;
                            int i5 = i3;
                            semFpAidlResponseHandler.getClass();
                            if (i4 != 6) {
                                return;
                            }
                            if (i5 == 10002) {
                                Iterator it =
                                        ((ArrayList)
                                                        semFpAidlResponseHandler
                                                                .mHalCallbackEx
                                                                .mCaptureEventListeners)
                                                .iterator();
                                if (it.hasNext()) {
                                    DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                                    throw null;
                                }
                                return;
                            }
                            if (i5 == 10003) {
                                Iterator it2 =
                                        ((ArrayList)
                                                        semFpAidlResponseHandler
                                                                .mHalCallbackEx
                                                                .mCaptureEventListeners)
                                                .iterator();
                                if (it2.hasNext()) {
                                    DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                            it2.next());
                                    throw null;
                                }
                            }
                        }
                    });
            handleResponse(
                    AcquisitionClient.class,
                    new SemFpAidlResponseHandler$$ExternalSyntheticLambda11(this, i, i3, 0),
                    null,
                    0L);
        }
    }

    public final void onAuthenticationFailed() {
        handleResponse(
                AuthenticationConsumer.class,
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda1(this, 3),
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda1(this, 4),
                0L);
    }

    public final void onAuthenticationSucceeded(
            final int i, final HardwareAuthToken hardwareAuthToken) {
        long j = 0;
        if (this.mProperties.isAnyUdfpsType()) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (currentClient instanceof SemFingerprintAuthenticationClient) {
                SemFingerprintAuthenticationClient semFingerprintAuthenticationClient =
                        (SemFingerprintAuthenticationClient) currentClient;
                if (semFingerprintAuthenticationClient.mIsKeyguard
                        && !semFingerprintAuthenticationClient.isInteractive()) {
                    SemUdfpsSysUiImpl semUdfpsSysUiImpl =
                            semFingerprintAuthenticationClient.mUdfpsImpl;
                    if (semUdfpsSysUiImpl != null) {
                        semUdfpsSysUiImpl.handleOnAuthenticated(true);
                    }
                    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                        boolean z = SemUdfpsHelper.DEBUG;
                        SemUdfpsOpticalHelper semUdfpsOpticalHelper =
                                SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl;
                        if (!semUdfpsOpticalHelper.mIsSupportHwLightSource) {
                            semUdfpsOpticalHelper
                                    .getBgHandler()
                                    .removeCallbacks(
                                            semUdfpsOpticalHelper
                                                    .mRunnableDisableFunctionForLightSource);
                            semUdfpsOpticalHelper
                                    .getBgHandler()
                                    .postDelayed(
                                            semUdfpsOpticalHelper
                                                    .mRunnableRestoreFunctionForLightSource,
                                            0L);
                        }
                    }
                    j = 10;
                }
            }
        }
        handleResponse(
                AuthenticationConsumer.class,
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        CharSequence charSequence;
                        SemFpAidlResponseHandler semFpAidlResponseHandler =
                                SemFpAidlResponseHandler.this;
                        int i2 = i;
                        HardwareAuthToken hardwareAuthToken2 = hardwareAuthToken;
                        AuthenticationConsumer authenticationConsumer =
                                (AuthenticationConsumer) obj;
                        List<Fingerprint> biometricsForUser =
                                FingerprintUtils.getInstance(semFpAidlResponseHandler.mSensorId)
                                        .getBiometricsForUser(
                                                semFpAidlResponseHandler.mContext,
                                                semFpAidlResponseHandler.mUserId);
                        if (biometricsForUser != null) {
                            for (Fingerprint fingerprint : biometricsForUser) {
                                if (fingerprint.getBiometricId() == i2) {
                                    charSequence = fingerprint.getName();
                                    break;
                                }
                            }
                        }
                        charSequence = "";
                        Fingerprint fingerprint2 =
                                new Fingerprint(
                                        charSequence, i2, semFpAidlResponseHandler.mSensorId);
                        byte[] byteArray = HardwareAuthTokenUtils.toByteArray(hardwareAuthToken2);
                        ArrayList arrayList = new ArrayList();
                        for (int i3 = 0; i3 < 69; i3++) {
                            arrayList.add(Byte.valueOf(byteArray[i3]));
                        }
                        semFpAidlResponseHandler.mLockoutHalImpl.resetFailedAttemptsForUser(
                                semFpAidlResponseHandler.mUserId, true);
                        authenticationConsumer.onAuthenticated(fingerprint2, true, arrayList);
                        Iterator it =
                                ((ArrayList)
                                                semFpAidlResponseHandler
                                                        .mHalCallbackEx
                                                        .mAuthenticationListeners)
                                        .iterator();
                        while (it.hasNext()) {
                            ((SemFpAuthenticationListener) it.next()).onAuthenticationResult(i2);
                        }
                    }
                },
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda1(this, 2),
                j);
    }

    public final void onAuthenticatorIdInvalidated(long j) {
        handleResponse(
                FingerprintInvalidationClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda2(j, 2),
                null);
    }

    public final void onAuthenticatorIdRetrieved(long j) {
        handleResponse(
                FingerprintGetAuthenticatorIdClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda2(j, 0),
                null);
    }

    public final void onChallengeGenerated(long j) {
        handleResponse(
                FingerprintGenerateChallengeClient.class,
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda3(this, j, 1),
                null,
                0L);
    }

    public final void onChallengeRevoked(long j) {
        handleResponse(
                FingerprintRevokeChallengeClient.class,
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda3(this, j, 0),
                null,
                0L);
    }

    public final void onEnrollmentProgress(int i, int i2) {
        onEnrollmentProgress$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler(
                i, i2);
        if (i2 == 0) {
            this.mScheduler.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFpAidlResponseHandler semFpAidlResponseHandler =
                                    SemFpAidlResponseHandler.this;
                            SemFpCallbackDispatcher semFpCallbackDispatcher =
                                    semFpAidlResponseHandler.mHalCallbackEx;
                            int i3 = semFpAidlResponseHandler.mSensorId;
                            int i4 = semFpAidlResponseHandler.mUserId;
                            Iterator it =
                                    ((ArrayList) semFpCallbackDispatcher.mEnrollListeners)
                                            .iterator();
                            while (it.hasNext()) {
                                ((SemFpEnrollmentListener) it.next()).onEnrollFinished(i3, i4);
                            }
                        }
                    });
        }
    }

    public final void
            onEnrollmentProgress$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler(
                    int i, final int i2) {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            return;
        }
        int i3 = currentClient.mTargetUserId;
        final Fingerprint fingerprint =
                new Fingerprint(
                        FingerprintUtils.getInstance(this.mSensorId)
                                .getStateForUser(this.mContext, i3)
                                .getUniqueName(),
                        i3,
                        i,
                        this.mSensorId);
        handleResponse(
                SemFingerprintEnrollClient.class,
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SemFpAidlResponseHandler semFpAidlResponseHandler =
                                SemFpAidlResponseHandler.this;
                        BiometricAuthenticator.Identifier identifier = fingerprint;
                        int i4 = i2;
                        semFpAidlResponseHandler.getClass();
                        ((SemFingerprintEnrollClient) obj).onEnrollResult(identifier, i4);
                        if (i4 == 0) {
                            semFpAidlResponseHandler.mAidlResponseHandlerCallback.onEnrollSuccess();
                        }
                    }
                },
                null);
    }

    public final void onEnrollmentsEnumerated(int[] iArr) {
        Slog.i("FingerprintService", "onEnrollmentsEnumerated: " + iArr.length);
        onEnrollmentsEnumerated$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler(
                iArr);
    }

    public final void
            onEnrollmentsEnumerated$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler(
                    int[] iArr) {
        if (iArr.length <= 0) {
            handleResponse(
                    EnumerateConsumer.class,
                    new AidlResponseHandler$$ExternalSyntheticLambda0(2),
                    null);
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            handleResponse(
                    EnumerateConsumer.class,
                    new AidlResponseHandler$$ExternalSyntheticLambda10(
                            new Fingerprint("", iArr[i], this.mSensorId), (iArr.length - i) - 1, 1),
                    null);
        }
    }

    public final void onEnrollmentsRemoved(int[] iArr) {
        if (iArr.length <= 0) {
            handleResponse(
                    RemovalConsumer.class,
                    new AidlResponseHandler$$ExternalSyntheticLambda0(1),
                    null);
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            handleResponse(
                    RemovalConsumer.class,
                    new AidlResponseHandler$$ExternalSyntheticLambda10(
                            new Fingerprint("", iArr[i], this.mSensorId), (iArr.length - i) - 1, 0),
                    null);
        }
    }

    public final void onError(byte b, int i) {
        int i2 = 17;
        if (b != 0) {
            int i3 = 1;
            if (b != 1) {
                i3 = 2;
                if (b != 2) {
                    i3 = 3;
                    if (b != 3) {
                        i3 = 4;
                        if (b != 4) {
                            i3 = 5;
                            if (b != 5) {
                                i3 = 6;
                                if (b != 6) {
                                    if (b == 7) {
                                        i2 = 8;
                                    } else if (b == 8) {
                                        i2 = 18;
                                    } else if (b == 9) {
                                        i2 = 19;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            i2 = i3;
        }
        handleResponse(
                ErrorConsumer.class,
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda11(this, i2, i, 1),
                null,
                0L);
    }

    public final void onInteractionDetected() {
        Slog.i("FingerprintService", "onInteractionDetected");
        onInteractionDetected$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler();
    }

    public final void
            onInteractionDetected$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler() {
        handleResponse(
                FingerprintDetectClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda0(0),
                null);
    }

    public final void onLockoutCleared() {
        Slog.i("AidlResponseHandler", "onLockoutCleared");
        handleResponse(
                FingerprintResetLockoutClient.class,
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda1(this, 0),
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda1(this, 1),
                0L);
        onLockoutCleared$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler();
    }

    public final void
            onLockoutCleared$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler() {
        handleResponse(
                FingerprintResetLockoutClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda0(4),
                new Consumer() { // from class:
                                 // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SemFpAidlResponseHandler semFpAidlResponseHandler =
                                SemFpAidlResponseHandler.this;
                        int i = semFpAidlResponseHandler.mSensorId;
                        int i2 = semFpAidlResponseHandler.mUserId;
                        LockoutTracker lockoutTracker = semFpAidlResponseHandler.mLockoutTracker;
                        LockoutResetDispatcher lockoutResetDispatcher =
                                semFpAidlResponseHandler.mLockoutResetDispatcher;
                        AuthSessionCoordinator authSessionCoordinator =
                                semFpAidlResponseHandler.mAuthSessionCoordinator;
                        int currentStrength = Utils.getCurrentStrength(i);
                        lockoutTracker.resetFailedAttemptsForUser(i2, true);
                        lockoutTracker.setLockoutModeForUser(i2, 0);
                        lockoutResetDispatcher.notifyLockoutResetCallbacks(i);
                        authSessionCoordinator.resetLockoutFor(i2, currentStrength, -1L);
                    }
                });
    }

    public final void onLockoutPermanent() {
        Slog.i("AidlResponseHandler", "onLockoutPermanent");
        onLockoutPermanent$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler();
    }

    public final void
            onLockoutPermanent$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler() {
        handleResponse(
                LockoutConsumer.class,
                new com.android.server.biometrics.sensors.face.aidl
                        .AidlResponseHandler$$ExternalSyntheticLambda9(1),
                null);
    }

    public final void onLockoutTimed(long j) {
        Slog.i("AidlResponseHandler", "onLockoutTimed: " + j);
        onLockoutTimed$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler(
                j);
    }

    public final void
            onLockoutTimed$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler(
                    long j) {
        handleResponse(
                LockoutConsumer.class,
                new AidlResponseHandler$$ExternalSyntheticLambda2(j, 1),
                null);
    }

    public final void onSessionClosed() {
        Slog.d("FingerprintService", "onSessionClosed");
        onSessionClosed$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler();
    }

    public final void
            onSessionClosed$com$android$server$biometrics$sensors$fingerprint$aidl$AidlResponseHandler() {
        BiometricScheduler biometricScheduler = this.mScheduler;
        Handler handler = biometricScheduler.mHandler;
        Objects.requireNonNull(biometricScheduler);
        handler.post(new AidlResponseHandler$$ExternalSyntheticLambda20(biometricScheduler));
    }
}
