package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.events.AuthenticationAcquiredInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.face.AuthenticationFrame;
import android.hardware.biometrics.face.BaseFrame;
import android.hardware.biometrics.face.EnrollmentFrame;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.common.Ashmem;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticationFrame;
import android.hardware.face.FaceDataFrame;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.SemFaceUtils;

import vendor.samsung.hardware.biometrics.face.ISehSession;

import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AidlResponseHandler extends ISessionCallback.Stub {
    public final AidlResponseHandlerCallback mAidlResponseHandlerCallback;
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final Context mContext;
    public SemFaceAidlLockoutHalImpl mLockoutHalImpl;
    public final LockoutResetDispatcher mLockoutResetDispatcher;
    public final LockoutTracker mLockoutTracker;
    public final BiometricScheduler mScheduler;
    public final int mSensorId;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AidlResponseHandlerCallback {
        void onEnrollSuccess();

        void onHardwareUnavailable();
    }

    public AidlResponseHandler(
            Context context,
            BiometricScheduler biometricScheduler,
            int i,
            int i2,
            LockoutTracker lockoutTracker,
            LockoutResetDispatcher lockoutResetDispatcher,
            AuthSessionCoordinator authSessionCoordinator,
            AidlResponseHandlerCallback aidlResponseHandlerCallback) {
        this.mContext = context;
        this.mScheduler = biometricScheduler;
        this.mSensorId = i;
        this.mUserId = i2;
        this.mLockoutTracker = lockoutTracker;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mAidlResponseHandlerCallback = aidlResponseHandlerCallback;
    }

    public final String getInterfaceHash() {
        return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    }

    public final int getInterfaceVersion() {
        return 4;
    }

    public final void handleResponse(
            final Class cls,
            final Consumer consumer,
            final AidlResponseHandler$$ExternalSyntheticLambda0
                    aidlResponseHandler$$ExternalSyntheticLambda0) {
        this.mScheduler.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        AidlResponseHandler aidlResponseHandler = AidlResponseHandler.this;
                        Class cls2 = cls;
                        Consumer consumer2 = consumer;
                        Consumer consumer3 = aidlResponseHandler$$ExternalSyntheticLambda0;
                        BaseClientMonitor currentClient =
                                aidlResponseHandler.mScheduler.getCurrentClient();
                        if (cls2.isInstance(currentClient)) {
                            consumer2.accept(currentClient);
                            return;
                        }
                        Slog.d(
                                "AidlResponseHandler",
                                "Current client is not an instance of ".concat(cls2.getName()));
                        if (consumer3 != null) {
                            consumer3.accept(currentClient);
                        }
                    }
                });
    }

    public final void onAuthenticationFailed() {
        SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl;
        Slog.d("AidlResponseHandler", "onAuthenticationFailed");
        SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.getInstance();
        StringBuilder sb = new StringBuilder("e=");
        sb.append(semFaceServiceExImpl.mIsEarlyStop);
        sb.append(", n=");
        sb.append(semFaceServiceExImpl.mNoMatchMaxCountNum);
        sb.append(", t=");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "SemFace", sb, semFaceServiceExImpl.mIsTimeout);
        if (!semFaceServiceExImpl.mSensor.mTestHalEnabled) {
            if (!semFaceServiceExImpl.mIsOperationStarted) {
                Slog.d("SemFace", "onAuthenticated: skip events after stop()");
                return;
            }
            semFaceServiceExImpl.mIsAuthenticateResult = true;
            if (!semFaceServiceExImpl.mIsEarlyStop
                    && semFaceServiceExImpl.mNoMatchMaxCountNum <= 0
                    && !semFaceServiceExImpl.mIsTimeout) {
                return;
            }
        }
        SemFaceServiceExImpl semFaceServiceExImpl2 = SemFaceServiceExImpl.getInstance();
        semFaceServiceExImpl2.getClass();
        Slog.i("SemFace", "no match BILG ");
        semFaceServiceExImpl2.onExtended(2, -1);
        semFaceServiceExImpl2.daemonCancelInternal();
        IBinder.DeathRecipient currentClient = this.mScheduler.getCurrentClient();
        if (currentClient != null
                && !((AuthenticationConsumer) currentClient).canIgnoreLockout()
                && (semFaceAidlLockoutHalImpl = this.mLockoutHalImpl) != null) {
            semFaceAidlLockoutHalImpl.addFailedAttemptForUser(this.mUserId);
            int lockoutModeForUser = this.mLockoutHalImpl.getLockoutModeForUser(this.mUserId);
            if (lockoutModeForUser == 2) {
                onLockoutPermanent();
                return;
            } else if (lockoutModeForUser == 1) {
                onLockoutTimed(30000L);
                return;
            }
        }
        handleResponse(
                AuthenticationConsumer.class,
                new AidlResponseHandler$$ExternalSyntheticLambda0(
                        0, new Face("", 0, this.mSensorId)),
                null);
    }

    public final void onAuthenticationFrame(final AuthenticationFrame authenticationFrame) {
        final int frameworkAcquiredInfo =
                AidlConversionUtils.toFrameworkAcquiredInfo(authenticationFrame.data.acquiredInfo);
        if (SemFaceServiceExImpl.getInstance()
                        .onPreAcquired(
                                frameworkAcquiredInfo, authenticationFrame.data.vendorCode, false)
                == 1) {
            return;
        }
        handleResponse(
                FaceAuthenticationClient.class,
                new Consumer() { // from class:
                    // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda19
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AuthenticationFrame authenticationFrame2 = authenticationFrame;
                        int i = frameworkAcquiredInfo;
                        FaceAuthenticationClient faceAuthenticationClient =
                                (FaceAuthenticationClient) obj;
                        if (authenticationFrame2 == null) {
                            Slog.e(
                                    "AidlResponseHandler",
                                    "Received null enrollment frame for face authentication"
                                        + " client.");
                            return;
                        }
                        SemFaceServiceExImpl semFaceServiceExImpl =
                                SemFaceServiceExImpl.getInstance();
                        int i2 = authenticationFrame2.data.vendorCode;
                        SemBioAnalyticsManager semBioAnalyticsManager =
                                semFaceServiceExImpl.mSemAnalyticsManager;
                        if (semBioAnalyticsManager != null) {
                            if (i == 4) {
                                semBioAnalyticsManager.mFaceQualityBigFace++;
                            } else if (i == 5) {
                                semBioAnalyticsManager.mFaceQualitySmallFace++;
                            } else if (i == 11) {
                                semBioAnalyticsManager.mFaceQualityNoFace++;
                            }
                        }
                        BaseFrame baseFrame = authenticationFrame2.data;
                        FaceAuthenticationFrame faceAuthenticationFrame =
                                new FaceAuthenticationFrame(
                                        new FaceDataFrame(
                                                AidlConversionUtils.toFrameworkAcquiredInfo(
                                                        baseFrame.acquiredInfo),
                                                baseFrame.vendorCode,
                                                baseFrame.pan,
                                                baseFrame.tilt,
                                                baseFrame.distance,
                                                baseFrame.isCancellable));
                        faceAuthenticationClient.getClass();
                        int acquiredInfo = faceAuthenticationFrame.getData().getAcquiredInfo();
                        int vendorCode = faceAuthenticationFrame.getData().getVendorCode();
                        faceAuthenticationClient.onAcquiredInternal(
                                acquiredInfo, vendorCode, false);
                        boolean shouldSendAcquiredMessage$1 =
                                faceAuthenticationClient.shouldSendAcquiredMessage$1(
                                        acquiredInfo, vendorCode);
                        if (shouldSendAcquiredMessage$1) {
                            if (shouldSendAcquiredMessage$1) {
                                try {
                                    AuthenticationStateListeners authenticationStateListeners =
                                            faceAuthenticationClient.mAuthenticationStateListeners;
                                    BiometricSourceType biometricSourceType =
                                            BiometricSourceType.FACE;
                                    authenticationStateListeners.onAuthenticationAcquired(
                                            new AuthenticationAcquiredInfo.Builder(
                                                            biometricSourceType,
                                                            faceAuthenticationClient
                                                                    .getRequestReason(),
                                                            acquiredInfo)
                                                    .build());
                                    String authHelpMessage =
                                            FaceManager.getAuthHelpMessage(
                                                    faceAuthenticationClient.mContext,
                                                    acquiredInfo,
                                                    vendorCode);
                                    if (authHelpMessage != null) {
                                        if (acquiredInfo == 22) {
                                            acquiredInfo = vendorCode + 1000;
                                        }
                                        faceAuthenticationClient.mAuthenticationStateListeners
                                                .onAuthenticationHelp(
                                                        new AuthenticationHelpInfo.Builder(
                                                                        biometricSourceType,
                                                                        faceAuthenticationClient
                                                                                .getRequestReason(),
                                                                        authHelpMessage,
                                                                        acquiredInfo)
                                                                .build());
                                    }
                                } catch (RemoteException e) {
                                    Slog.w(
                                            "FaceAuthenticationClient",
                                            "Failed to send authentication frame",
                                            e);
                                    faceAuthenticationClient.mAuthenticationStateListeners
                                            .onAuthenticationStopped(
                                                    new AuthenticationStoppedInfo.Builder(
                                                                    BiometricSourceType.FACE,
                                                                    faceAuthenticationClient
                                                                            .getRequestReason())
                                                            .build());
                                    faceAuthenticationClient.mCallback.onClientFinished(
                                            faceAuthenticationClient, false);
                                    return;
                                }
                            }
                            IFaceServiceReceiver iFaceServiceReceiver =
                                    faceAuthenticationClient.mListener.mFaceServiceReceiver;
                            if (iFaceServiceReceiver != null) {
                                iFaceServiceReceiver.onAuthenticationFrame(faceAuthenticationFrame);
                            }
                        }
                    }
                },
                null);
    }

    public final void onAuthenticationSucceeded(final int i, HardwareAuthToken hardwareAuthToken) {
        Slog.d("AidlResponseHandler", "onAuthenticationSucceeded");
        final Face face = new Face("", i, this.mSensorId);
        byte[] byteArray =
                hardwareAuthToken == null
                        ? new byte[0]
                        : HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
        final ArrayList arrayList = new ArrayList();
        for (byte b : byteArray) {
            arrayList.add(Byte.valueOf(b));
        }
        handleResponse(
                AuthenticationConsumer.class,
                new Consumer() { // from class:
                    // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str;
                        long currentTimeMillis;
                        Ashmem wrappedDataFromMemory;
                        AidlResponseHandler aidlResponseHandler = AidlResponseHandler.this;
                        int i2 = i;
                        BiometricAuthenticator.Identifier identifier = face;
                        ArrayList arrayList2 = arrayList;
                        AuthenticationConsumer authenticationConsumer =
                                (AuthenticationConsumer) obj;
                        aidlResponseHandler.getClass();
                        SemFaceServiceExImpl semFaceServiceExImpl =
                                SemFaceServiceExImpl.getInstance();
                        SemBioAnalyticsManager semBioAnalyticsManager =
                                semFaceServiceExImpl.mSemAnalyticsManager;
                        int[] iArr = {
                            semBioAnalyticsManager.mFaceQualityNoFace,
                            semBioAnalyticsManager.mFaceQualityBigFace,
                            semBioAnalyticsManager.mFaceQualitySmallFace
                        };
                        StringBuilder sb = new StringBuilder("match BILG ");
                        sb.append(System.currentTimeMillis() - semFaceServiceExImpl.mAuthStartTime);
                        sb.append(":");
                        sb.append(iArr[0]);
                        sb.append(":");
                        sb.append(iArr[1]);
                        sb.append(":");
                        SystemServiceManager$$ExternalSyntheticOutline0.m(sb, iArr[2], "SemFace");
                        if (!semFaceServiceExImpl.mSensor.mTestHalEnabled) {
                            if (semFaceServiceExImpl.mIsAuthenticationExtOperation) {
                                if (semFaceServiceExImpl.mIsHIDL) {
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                if (semFaceServiceExImpl.mISehSession == null) {
                                    Slog.w(
                                            "SemFace",
                                            "daemonGetWrappedDataFromMemory(): no seh face HAL!");
                                } else {
                                    ParcelFileDescriptor parcelFileDescriptor = null;
                                    try {
                                        Slog.i("SemFace", "getWrappedDataFromMemory START");
                                        currentTimeMillis = System.currentTimeMillis();
                                        wrappedDataFromMemory =
                                                ((ISehSession.Stub.Proxy)
                                                                semFaceServiceExImpl.mISehSession)
                                                        .getWrappedDataFromMemory();
                                    } catch (Exception e) {
                                        Log.w("SemFace", "Unable to read statistics stream", e);
                                    }
                                    if (wrappedDataFromMemory == null) {
                                        Slog.e("SemFace", "getWrappedDataFromMemory: ash is null");
                                    } else {
                                        Slog.i(
                                                "SemFace",
                                                "getWrappedDataFromMemory FINISH ("
                                                        + (System.currentTimeMillis()
                                                                - currentTimeMillis)
                                                        + "ms)");
                                        ByteBuffer mapReadOnly =
                                                SharedMemory.fromFileDescriptor(
                                                                wrappedDataFromMemory.fd)
                                                        .mapReadOnly();
                                        if (mapReadOnly == null) {
                                            Slog.e(
                                                    "SemFace",
                                                    "getWrappedDataFromMemory: dataBuffer is null");
                                        } else {
                                            int remaining = mapReadOnly.remaining();
                                            byte[] bArr = new byte[remaining];
                                            mapReadOnly.get(
                                                    bArr, 0, (int) wrappedDataFromMemory.size);
                                            Slog.i(
                                                    "SemFace",
                                                    "getWrappedDataFromMemory read " + remaining);
                                            if (Utils.DEBUG) {
                                                int i3 = 128;
                                                if (remaining <= 128) {
                                                    i3 = remaining;
                                                }
                                                Slog.i(
                                                        "SemFace",
                                                        "data = "
                                                                + Arrays.toString(
                                                                        Arrays.copyOf(bArr, i3)));
                                            }
                                            if (semFaceServiceExImpl.mMemoryFileForAuthPreviewResult
                                                    == null) {
                                                semFaceServiceExImpl
                                                                .mMemoryFileForAuthPreviewResult =
                                                        new MemoryFile("auth_preview", remaining);
                                            }
                                            semFaceServiceExImpl.mMemoryFileForAuthPreviewResult
                                                    .writeBytes(bArr, 0, 0, remaining);
                                            Class[] clsArr = new Class[0];
                                            parcelFileDescriptor =
                                                    ParcelFileDescriptor.dup(
                                                            (FileDescriptor)
                                                                    MemoryFile.class
                                                                            .getDeclaredMethod(
                                                                                    "getFileDescriptor",
                                                                                    null)
                                                                            .invoke(
                                                                                    semFaceServiceExImpl
                                                                                            .mMemoryFileForAuthPreviewResult,
                                                                                    null));
                                            Slog.i("SemFace", "getWrappedDataFromMemory save");
                                            bundle.putParcelable(
                                                    "memoryfile_descriptor", parcelFileDescriptor);
                                        }
                                    }
                                }
                                semFaceServiceExImpl.sendSucceeded(bundle);
                                return;
                            }
                            if (!semFaceServiceExImpl.mIsHIDL) {
                                Slog.i("SemFace", "getWrappedData START");
                                long currentTimeMillis2 = System.currentTimeMillis();
                                try {
                                    ISehSession iSehSession = semFaceServiceExImpl.mISehSession;
                                    if (iSehSession == null) {
                                        Slog.w(
                                                "SemFace",
                                                "daemonGetWrappedData(): no seh face HAL!");
                                    } else {
                                        byte[] wrappedData =
                                                ((ISehSession.Stub.Proxy) iSehSession)
                                                        .getWrappedData();
                                        Slog.i(
                                                "SemFace",
                                                "getWrappedData FINISH ("
                                                        + (System.currentTimeMillis()
                                                                - currentTimeMillis2)
                                                        + "ms)");
                                        if (wrappedData == null || wrappedData.length <= 0) {
                                            Slog.w("SemFace", "getWrappedData : data is null or 0");
                                        } else {
                                            StringBuilder sb2 =
                                                    new StringBuilder("getWrappedData size: ");
                                            sb2.append(wrappedData.length);
                                            if (Utils.DEBUG) {
                                                str =
                                                        ", data: "
                                                                + SemFaceUtils.byteArrayToHex(
                                                                        wrappedData);
                                            } else {
                                                str = "";
                                            }
                                            sb2.append(str);
                                            Slog.d("SemFace", sb2.toString());
                                            SemFaceUtils.setFidoResultData(wrappedData);
                                        }
                                    }
                                } catch (Exception e2) {
                                    Slog.e("SemFace", "getWrappedData: ", e2);
                                }
                            }
                        }
                        SemFaceServiceExImpl semFaceServiceExImpl2 =
                                SemFaceServiceExImpl.getInstance();
                        semFaceServiceExImpl2.onExtended(1, i2);
                        semFaceServiceExImpl2.stopOperation();
                        SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl =
                                aidlResponseHandler.mLockoutHalImpl;
                        if (semFaceAidlLockoutHalImpl != null) {
                            semFaceAidlLockoutHalImpl.resetFailedAttemptsForUser(
                                    aidlResponseHandler.mUserId, true);
                        }
                        authenticationConsumer.onAuthenticated(identifier, true, arrayList2);
                    }
                },
                null);
    }

    public final void onAuthenticatorIdInvalidated(long j) {
        Slog.d("AidlResponseHandler", "onAuthenticatorIdInvalidated");
        handleResponse(
                FaceInvalidationClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda3(j, 1),
                null);
    }

    public final void onAuthenticatorIdRetrieved(long j) {
        StringBuilder sb = new StringBuilder("onAuthenticatorIdRetrieved ");
        sb.append(Utils.DEBUG ? Long.valueOf(j) : "");
        Slog.d("AidlResponseHandler", sb.toString());
        handleResponse(
                FaceGetAuthenticatorIdClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda3(j, 0),
                null);
    }

    public final void onChallengeGenerated(long j) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m("onChallengeGenerated ", j, ", ");
        m.append(this.mSensorId);
        m.append(", ");
        m.append(this.mUserId);
        Slog.d("AidlResponseHandler", m.toString());
        handleResponse(
                FaceGenerateChallengeClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda6(this, j, 0),
                null);
    }

    public final void onChallengeRevoked(long j) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m("onChallengeRevoked ", j, ", ");
        m.append(this.mSensorId);
        m.append(", ");
        m.append(this.mUserId);
        Slog.d("AidlResponseHandler", m.toString());
        handleResponse(
                FaceRevokeChallengeClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda6(this, j, 1),
                null);
    }

    public final void onEnrollmentFrame(EnrollmentFrame enrollmentFrame) {
        if (SemFaceServiceExImpl.getInstance()
                        .onPreAcquired(
                                AidlConversionUtils.toFrameworkAcquiredInfo(
                                        enrollmentFrame.data.acquiredInfo),
                                enrollmentFrame.data.vendorCode,
                                true)
                == 1) {
            return;
        }
        handleResponse(
                FaceEnrollClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda0(3, enrollmentFrame),
                null);
    }

    public final void onEnrollmentProgress(final int i, final int i2) {
        ASKSManagerService$$ExternalSyntheticOutline0.m(
                i, i2, "onEnrollmentProgress id=", ", remaining=", "AidlResponseHandler");
        if (i2 == 0) {
            Slog.d("AidlResponseHandler", "onEnrollmentProgress success BILG ");
        }
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            return;
        }
        final Face face =
                new Face(
                        FaceUtils.getInstance(this.mSensorId, null)
                                .getStateForUser(this.mContext, currentClient.mTargetUserId)
                                .getUniqueName(),
                        i,
                        this.mSensorId);
        handleResponse(
                FaceEnrollClient.class,
                new Consumer() { // from class:
                    // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda2
                    /* JADX WARN: Removed duplicated region for block: B:84:0x00c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:97:0x014a  */
                    /* JADX WARN: Removed duplicated region for block: B:99:? A[RETURN, SYNTHETIC] */
                    @Override // java.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void accept(java.lang.Object r14) {
                        /*
                            Method dump skipped, instructions count: 336
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda2.accept(java.lang.Object):void");
                    }
                },
                null);
    }

    public final void onEnrollmentsEnumerated(int[] iArr) {
        StringBuilder sb = new StringBuilder("onEnrollmentsEnumerated: ");
        sb.append(iArr == null ? "null" : Integer.valueOf(iArr.length));
        Slog.d("AidlResponseHandler", sb.toString());
        if (iArr == null || iArr.length != 1 || iArr[0] != -1) {
            if (iArr.length <= 0) {
                handleResponse(
                        EnumerateConsumer.class,
                        new AidlResponseHandler$$ExternalSyntheticLambda9(4),
                        null);
                return;
            }
            for (int i = 0; i < iArr.length; i++) {
                handleResponse(
                        EnumerateConsumer.class,
                        new AidlResponseHandler$$ExternalSyntheticLambda8(
                                new Face("", iArr[i], this.mSensorId), iArr, i, 1),
                        null);
            }
            return;
        }
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof FaceProvider.AnonymousClass4) {
            FaceProvider.AnonymousClass4 anonymousClass4 =
                    (FaceProvider.AnonymousClass4) currentClient;
            anonymousClass4.getClass();
            Slog.d("FaceInternalCleanupClient", "onEnumerationError");
            anonymousClass4.mCallback.onClientFinished(anonymousClass4, false);
        }
    }

    public final void onEnrollmentsRemoved(int[] iArr) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("onEnrollmentsRemoved: "), iArr.length, "AidlResponseHandler");
        if (iArr.length <= 0) {
            handleResponse(
                    RemovalConsumer.class,
                    new AidlResponseHandler$$ExternalSyntheticLambda9(0),
                    null);
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            handleResponse(
                    RemovalConsumer.class,
                    new AidlResponseHandler$$ExternalSyntheticLambda8(
                            new Face("", iArr[i], this.mSensorId), iArr, i, 0),
                    null);
        }
    }

    public final void onError(byte b, int i) {
        int i2;
        switch (b) {
            case 1:
                i2 = 1;
                break;
            case 2:
                i2 = 2;
                break;
            case 3:
                i2 = 3;
                break;
            case 4:
                i2 = 4;
                break;
            case 5:
                i2 = 5;
                break;
            case 6:
                i2 = 6;
                break;
            case 7:
                i2 = 8;
                break;
            case 8:
                i2 = 16;
                break;
            default:
                i2 = 17;
                break;
        }
        onError(i2, i);
    }

    public final void onError(final int i, final int i2) {
        SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.getInstance();
        if (!semFaceServiceExImpl.mSensor.mTestHalEnabled) {
            if (i == 5) {
                Slog.d("SemFace", "onError: skip error (5:cancel) from daemon");
                return;
            }
            BaseClientMonitor currentClient = semFaceServiceExImpl.mScheduler.getCurrentClient();
            if (((currentClient instanceof FaceAuthenticationClient)
                            || (currentClient instanceof FaceEnrollClient))
                    && !semFaceServiceExImpl.mIsOperationStarted) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "onError: skip (", ") after stop()", "SemFace");
                return;
            }
        }
        handleResponse(
                ErrorConsumer.class,
                new Consumer() { // from class:
                    // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda21
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str;
                        String str2;
                        AidlResponseHandler aidlResponseHandler = AidlResponseHandler.this;
                        int i3 = i;
                        int i4 = i2;
                        ErrorConsumer errorConsumer = (ErrorConsumer) obj;
                        BaseClientMonitor currentClient2 =
                                aidlResponseHandler.mScheduler.getCurrentClient();
                        StringBuilder sb = new StringBuilder("onError, client: ");
                        sb.append(Utils.getClientName(currentClient2));
                        sb.append(", error: ");
                        sb.append(i3);
                        boolean z = Utils.DEBUG;
                        if (z) {
                            str = "(" + FaceManager.getErrorName(i3) + ")";
                        } else {
                            str = "";
                        }
                        sb.append(str);
                        sb.append(", vendorCode: ");
                        sb.append(i4);
                        if (z) {
                            str2 = "(" + FaceManager.getErrorName(i4) + ")";
                        } else {
                            str2 = "";
                        }
                        sb.append(str2);
                        Slog.d("AidlResponseHandler", sb.toString());
                        SemFaceServiceExImpl semFaceServiceExImpl2 =
                                SemFaceServiceExImpl.getInstance();
                        semFaceServiceExImpl2.getClass();
                        SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
                        Context context = semFaceServiceExImpl2.mContext;
                        int currentClientHashID = semFaceServiceExImpl2.getCurrentClientHashID();
                        System.currentTimeMillis();
                        if (semBioLoggingManager.mIsFpBioStarEnabled) {
                            synchronized (semBioLoggingManager.mFaceLoggingInfo) {
                                SemBioLoggingManager.LoggingInfo loggingInfo =
                                        (SemBioLoggingManager.LoggingInfo)
                                                semBioLoggingManager.mFaceLoggingInfo.get(
                                                        currentClientHashID);
                                if (loggingInfo != null) {
                                    Intent intent =
                                            new Intent(
                                                    "com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
                                    intent.putExtra("action_type", 14);
                                    intent.putExtra("error", i3);
                                    intent.putExtra("vendor", i4);
                                    loggingInfo.sendBroadcastFaceInfo(context, intent);
                                }
                            }
                        }
                        if (i3 != 5 || i3 != 3) {
                            Slog.i("SemFace", "error BILG " + i3 + ", " + i4);
                            SemBioLoggingManager.get()
                                    .faceStop(
                                            semFaceServiceExImpl2.getCurrentClientHashID(),
                                            i3 == 8 ? i4 : i3,
                                            System.currentTimeMillis()
                                                    - semFaceServiceExImpl2.mStartOperationTime,
                                            "E");
                        }
                        SemBioAnalyticsManager semBioAnalyticsManager =
                                semFaceServiceExImpl2.mSemAnalyticsManager;
                        if (semBioAnalyticsManager != null) {
                            String currentClientOwnerString =
                                    semFaceServiceExImpl2.getCurrentClientOwnerString();
                            if (i3 == 8 && i4 == 1006) {
                                semBioAnalyticsManager.faceInsertLog(
                                        new SemBioAnalyticsManager.EventData(
                                                -1, 3, "FAMK", currentClientOwnerString));
                            } else if (i3 == 7 || i3 == 9) {
                                semBioAnalyticsManager.faceInsertLog(
                                        new SemBioAnalyticsManager.EventData(
                                                -1, 3, "FAIB", currentClientOwnerString));
                            }
                        }
                        if (i3 == 8 && i4 == 1006 && semFaceServiceExImpl2.mOperationType == 2) {
                            semFaceServiceExImpl2.sendAcquired(
                                    FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED);
                        }
                        errorConsumer.onError(i3, i4);
                        if (i3 == 1) {
                            aidlResponseHandler.mAidlResponseHandlerCallback
                                    .onHardwareUnavailable();
                        }
                        SemFaceServiceExImpl semFaceServiceExImpl3 =
                                SemFaceServiceExImpl.getInstance();
                        semFaceServiceExImpl3.getClass();
                        if (i3 == 8 && i4 == 1001) {
                            Slog.e("SemFace", "onError : TEMPLATE_CORRUPTED");
                            semFaceServiceExImpl3.daemonEnumerateUser();
                        }
                        semFaceServiceExImpl3.stopOperation();
                    }
                },
                null);
    }

    public final void onFeatureSet(byte b) {
        Slog.d("AidlResponseHandler", "onFeatureSet");
        handleResponse(
                FaceSetFeatureClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda9(5),
                null);
    }

    public final void onFeaturesRetrieved(byte[] bArr) {
        Slog.d("AidlResponseHandler", "onFeaturesRetrieved");
        handleResponse(
                FaceGetFeatureClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda0(2, bArr),
                null);
    }

    public final void onInteractionDetected() {
        Slog.d("AidlResponseHandler", "onInteractionDetected");
        handleResponse(
                FaceDetectClient.class, new AidlResponseHandler$$ExternalSyntheticLambda9(2), null);
    }

    public final void onLockoutCleared() {
        Slog.d("AidlResponseHandler", "onLockoutCleared()");
        if (this.mLockoutHalImpl != null) {
            if (this.mScheduler.getCurrentClient() instanceof FaceResetLockoutClient) {
                this.mLockoutHalImpl.resetFailedAttemptsForUser(this.mUserId, true);
            } else {
                this.mLockoutHalImpl.resetFailedAttemptsForUser(this.mUserId, false);
            }
        }
        handleResponse(
                FaceResetLockoutClient.class,
                new AidlResponseHandler$$ExternalSyntheticLambda9(3),
                new AidlResponseHandler$$ExternalSyntheticLambda0(1, this));
    }

    public final void onLockoutPermanent() {
        Slog.d("AidlResponseHandler", "onLockoutPermanent()");
        handleResponse(
                LockoutConsumer.class, new AidlResponseHandler$$ExternalSyntheticLambda9(1), null);
    }

    public final void onLockoutTimed(long j) {
        Slog.d("AidlResponseHandler", "onLockoutTimed()");
        handleResponse(
                LockoutConsumer.class,
                new AidlResponseHandler$$ExternalSyntheticLambda3(j, 2),
                null);
    }

    public final void onSessionClosed() {
        BiometricScheduler biometricScheduler = this.mScheduler;
        Handler handler = biometricScheduler.mHandler;
        Objects.requireNonNull(biometricScheduler);
        handler.post(new AidlResponseHandler$$ExternalSyntheticLambda20(biometricScheduler));
    }
}
