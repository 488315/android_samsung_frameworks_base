package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.content.pm.UserInfo;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.face.AuthenticationFrame;
import android.hardware.biometrics.face.EnrollmentFrame;
import android.hardware.biometrics.face.ISession;
import android.hardware.biometrics.face.ISessionCallback;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
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
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.BiometricScheduler;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.LockoutCache;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutResetDispatcher;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.StartUserClient;
import com.android.server.biometrics.sensors.StopUserClient;
import com.android.server.biometrics.sensors.UserAwareBiometricScheduler;
import com.android.server.biometrics.sensors.face.FaceUtils;
import com.android.server.biometrics.sensors.face.SemFaceUserAwareScheduler;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.aidl.SemFaceAidlLockoutHalImpl;
import com.android.server.biometrics.sensors.face.aidl.Sensor;
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
    public SemFaceAidlLockoutHalImpl mLockoutHalImpl;
    public final FaceProvider mProvider;
    public final UserAwareBiometricScheduler mScheduler;
    public final FaceSensorPropertiesInternal mSensorProperties;
    public final String mTag;
    public boolean mTestHalEnabled;
    public final IBinder mToken;

    public class HalSessionCallback extends ISessionCallback.Stub {
        public AuthSessionCoordinator mAuthSessionCoordinator;
        public final Callback mCallback;
        public final Context mContext;
        public final Handler mHandler;
        public final LockoutCache mLockoutCache;
        public SemFaceAidlLockoutHalImpl mLockoutHalImpl;
        public final LockoutResetDispatcher mLockoutResetDispatcher;
        public final UserAwareBiometricScheduler mScheduler;
        public final int mSensorId;
        public final String mTag;
        public final int mUserId;

        public interface Callback {
            void onHardwareUnavailable();
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public String getInterfaceHash() {
            return "74b0b7cb149ee205b12cd2254d216725c6e5429c";
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public int getInterfaceVersion() {
            return 2;
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

        public void setLockoutImpl(SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl) {
            this.mLockoutHalImpl = semFaceAidlLockoutHalImpl;
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onChallengeGenerated(final long j) {
            Slog.d(this.mTag, "onChallengeGenerated " + j);
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onChallengeGenerated$0(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChallengeGenerated$0(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceGenerateChallengeClient)) {
                Slog.e(this.mTag, "onChallengeGenerated for wrong client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceGenerateChallengeClient) currentClient).onChallengeGenerated(this.mSensorId, this.mUserId, j);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onChallengeRevoked(final long j) {
            Slog.d(this.mTag, "onChallengeRevoked");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onChallengeRevoked$1(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChallengeRevoked$1(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceRevokeChallengeClient)) {
                Slog.e(this.mTag, "onChallengeRevoked for wrong client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceRevokeChallengeClient) currentClient).onChallengeRevoked(this.mSensorId, this.mUserId, j);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationFrame(final AuthenticationFrame authenticationFrame) {
            final int frameworkAcquiredInfo = AidlConversionUtils.toFrameworkAcquiredInfo(authenticationFrame.data.acquiredInfo);
            if (SemFaceServiceExImpl.getInstance().onPreAcquired(frameworkAcquiredInfo, authenticationFrame.data.vendorCode, false) == 1) {
                return;
            }
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticationFrame$2(authenticationFrame, frameworkAcquiredInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationFrame$2(AuthenticationFrame authenticationFrame, int i) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceAuthenticationClient)) {
                Slog.e(this.mTag, "onAuthenticationFrame for incompatible client: " + Utils.getClientName(currentClient));
                return;
            }
            if (authenticationFrame == null) {
                Slog.e(this.mTag, "Received null authentication frame for client: " + Utils.getClientName(currentClient));
                return;
            }
            SemFaceServiceExImpl.getInstance().onAcquired(i, authenticationFrame.data.vendorCode);
            ((FaceAuthenticationClient) currentClient).onAuthenticationFrame(AidlConversionUtils.toFrameworkAuthenticationFrame(authenticationFrame));
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentFrame(final EnrollmentFrame enrollmentFrame) {
            if (SemFaceServiceExImpl.getInstance().onPreAcquired(AidlConversionUtils.toFrameworkAcquiredInfo(enrollmentFrame.data.acquiredInfo), enrollmentFrame.data.vendorCode, true) == 1) {
                return;
            }
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentFrame$3(enrollmentFrame);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollmentFrame$3(EnrollmentFrame enrollmentFrame) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceEnrollClient)) {
                Slog.e(this.mTag, "onEnrollmentFrame for incompatible client: " + Utils.getClientName(currentClient));
                return;
            }
            if (enrollmentFrame == null) {
                Slog.e(this.mTag, "Received null enrollment frame for client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceEnrollClient) currentClient).onEnrollmentFrame(AidlConversionUtils.toFrameworkEnrollmentFrame(enrollmentFrame));
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onError(final byte b, final int i) {
            final int frameworkError = AidlConversionUtils.toFrameworkError(b);
            if (SemFaceServiceExImpl.getInstance().onPreError(frameworkError, i)) {
                return;
            }
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onError$4(frameworkError, i, b);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onError$4(int i, int i2, byte b) {
            String str;
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            String str2 = this.mTag;
            StringBuilder sb = new StringBuilder();
            sb.append("onError, client: ");
            sb.append(Utils.getClientName(currentClient));
            sb.append(", error: ");
            sb.append(i);
            boolean z = Utils.DEBUG;
            String str3 = "";
            if (z) {
                str = "(" + FaceManager.getErrorName(i) + ")";
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(", vendorCode: ");
            sb.append(i2);
            if (z) {
                str3 = "(" + FaceManager.getErrorName(i2) + ")";
            }
            sb.append(str3);
            Slog.d(str2, sb.toString());
            if (!(currentClient instanceof ErrorConsumer)) {
                Slog.e(this.mTag, "onError for non-error consumer: " + Utils.getClientName(currentClient));
                return;
            }
            SemFaceServiceExImpl.getInstance().onErrorExt(i, i2);
            ((ErrorConsumer) currentClient).onError(AidlConversionUtils.toFrameworkError(b), i2);
            if (b == 1) {
                this.mCallback.onHardwareUnavailable();
            }
            SemFaceServiceExImpl.getInstance().onError(i, i2);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentProgress(final int i, final int i2) {
            Slog.d(this.mTag, "onEnrollmentProgress id=" + i + ", remaining=" + i2);
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentProgress$5(i2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollmentProgress$5(int i, int i2) {
            if (i == 0 && SemFaceServiceExImpl.getInstance().removeSavedFaceIdIfCancelled(new int[]{i2})) {
                return;
            }
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceEnrollClient)) {
                Slog.e(this.mTag, "onEnrollmentProgress for non-enroll client: " + Utils.getClientName(currentClient));
                return;
            }
            BiometricAuthenticator.Identifier face = new Face(FaceUtils.getInstance(this.mSensorId).getUniqueName(this.mContext, currentClient.getTargetUserId()), i2, this.mSensorId);
            SemFaceServiceExImpl.getInstance().onEnrollResult(i2, i);
            ((FaceEnrollClient) currentClient).onEnrollResult(face, i);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationSucceeded(final int i, final HardwareAuthToken hardwareAuthToken) {
            Slog.d(this.mTag, "onAuthenticationSucceeded");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticationSucceeded$6(i, hardwareAuthToken);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$6(int i, HardwareAuthToken hardwareAuthToken) {
            if (SemFaceServiceExImpl.getInstance().onPreAuthenticationSucceeded()) {
                return;
            }
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof AuthenticationConsumer)) {
                Slog.e(this.mTag, "onAuthenticationSucceeded for non-authentication consumer: " + Utils.getClientName(currentClient));
                return;
            }
            SemBioLoggingManager.get().faceMatch(this.mContext, currentClient.getHashID(), 0L, i, SemFaceUtils.getEnrolledPositionForFaceID(i, this.mUserId));
            SemFaceServiceExImpl.getInstance().onAuthenticationSucceeded();
            this.mLockoutHalImpl.resetFailedAttemptsForUser(true, this.mUserId);
            AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) currentClient;
            Face face = new Face("", i, this.mSensorId);
            byte[] byteArray = HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
            ArrayList arrayList = new ArrayList();
            for (byte b : byteArray) {
                arrayList.add(Byte.valueOf(b));
            }
            authenticationConsumer.onAuthenticated(face, true, arrayList);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationFailed() {
            Slog.d(this.mTag, "onAuthenticationFailed");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticationFailed$7();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onAuthenticationFailed$7() {
            if (SemFaceServiceExImpl.getInstance().onPreAuthenticationFailed()) {
                return;
            }
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof AuthenticationConsumer)) {
                Slog.e(this.mTag, "onAuthenticationFailed for non-authentication consumer: " + Utils.getClientName(currentClient));
                return;
            }
            SemFaceServiceExImpl.getInstance().onAuthenticationFailed();
            AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) currentClient;
            Slog.i(this.mTag, "handleAuthenticated: false");
            if (!authenticationConsumer.canIgnoreLockout()) {
                this.mLockoutHalImpl.addFailedAttemptForUser(this.mUserId);
                int lockoutModeForUser = this.mLockoutHalImpl.getLockoutModeForUser(this.mUserId);
                if (lockoutModeForUser == 2) {
                    onLockoutPermanent();
                    return;
                } else if (lockoutModeForUser == 1) {
                    onLockoutTimed(30000L);
                    return;
                }
            }
            authenticationConsumer.onAuthenticated(new Face("", 0, this.mSensorId), false, null);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutTimed(final long j) {
            Slog.d(this.mTag, "onLockoutTimed()");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onLockoutTimed$8(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onLockoutTimed$8(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof LockoutConsumer)) {
                Slog.e(this.mTag, "onLockoutTimed for non-lockout consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((LockoutConsumer) currentClient).onLockoutTimed(j);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutPermanent() {
            Slog.d(this.mTag, "onLockoutPermanent()");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onLockoutPermanent$9();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onLockoutPermanent$9() {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof LockoutConsumer)) {
                Slog.e(this.mTag, "onLockoutPermanent for non-lockout consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((LockoutConsumer) currentClient).onLockoutPermanent();
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutCleared() {
            Slog.d(this.mTag, "onLockoutCleared()");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onLockoutCleared$10();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLockoutCleared$10() {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceResetLockoutClient)) {
                Slog.d(this.mTag, "onLockoutCleared outside of resetLockout by HAL");
                int i = this.mSensorId;
                FaceResetLockoutClient.resetLocalLockoutStateToNone(i, this.mUserId, this.mLockoutCache, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, Utils.getCurrentStrength(i), -1L);
                SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl = this.mLockoutHalImpl;
                if (semFaceAidlLockoutHalImpl != null) {
                    semFaceAidlLockoutHalImpl.resetFailedAttemptsForUser(false, this.mUserId);
                    return;
                }
                return;
            }
            Slog.d(this.mTag, "onLockoutCleared after resetLockout");
            ((FaceResetLockoutClient) currentClient).onLockoutCleared();
            SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl2 = this.mLockoutHalImpl;
            if (semFaceAidlLockoutHalImpl2 != null) {
                semFaceAidlLockoutHalImpl2.resetFailedAttemptsForUser(true, this.mUserId);
            }
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onInteractionDetected() {
            Slog.d(this.mTag, "onInteractionDetected");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onInteractionDetected$11();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInteractionDetected$11() {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceDetectClient)) {
                Slog.e(this.mTag, "onInteractionDetected for wrong client: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceDetectClient) currentClient).onInteractionDetected();
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentsEnumerated(final int[] iArr) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentsEnumerated$12(iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onEnrollmentsEnumerated$12(int[] iArr) {
            String str = this.mTag;
            StringBuilder sb = new StringBuilder();
            sb.append("onEnrollmentsEnumerated: ");
            sb.append(iArr == null ? "null" : Integer.valueOf(iArr.length));
            Slog.d(str, sb.toString());
            boolean z = iArr != null && iArr.length == 1 && iArr[0] == -1;
            if (!z) {
                SemFaceServiceExImpl.getInstance().doTemplateSyncForUser(iArr);
            }
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof EnumerateConsumer)) {
                Slog.e(this.mTag, "onEnrollmentsEnumerated for non-enumerate consumer: " + Utils.getClientName(currentClient));
                return;
            }
            if (z) {
                ((FaceInternalCleanupClient) currentClient).onEnumerationError();
                return;
            }
            EnumerateConsumer enumerateConsumer = (EnumerateConsumer) currentClient;
            if (iArr.length > 0) {
                for (int i = 0; i < iArr.length; i++) {
                    enumerateConsumer.onEnumerationResult(new Face("", iArr[i], this.mSensorId), (iArr.length - i) - 1);
                }
                return;
            }
            enumerateConsumer.onEnumerationResult(null, 0);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onFeaturesRetrieved(final byte[] bArr) {
            Slog.d(this.mTag, "onFeaturesRetrieved");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onFeaturesRetrieved$13(bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFeaturesRetrieved$13(byte[] bArr) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceGetFeatureClient)) {
                Slog.e(this.mTag, "onFeaturesRetrieved for non-get feature consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceGetFeatureClient) currentClient).onFeatureGet(true, bArr);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onFeatureSet(byte b) {
            Slog.d(this.mTag, "onFeatureSet");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onFeatureSet$14();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFeatureSet$14() {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceSetFeatureClient)) {
                Slog.e(this.mTag, "onFeatureSet for non-set consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceSetFeatureClient) currentClient).onFeatureSet(true);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentsRemoved(final int[] iArr) {
            Slog.d(this.mTag, "onEnrollmentsRemoved: " + iArr.length);
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onEnrollmentsRemoved$15(iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onEnrollmentsRemoved$15(int[] iArr) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof RemovalConsumer)) {
                Slog.e(this.mTag, "onRemoved for non-removal consumer: " + Utils.getClientName(currentClient));
                return;
            }
            RemovalConsumer removalConsumer = (RemovalConsumer) currentClient;
            if (iArr.length > 0) {
                for (int i = 0; i < iArr.length; i++) {
                    removalConsumer.onRemoved(new Face("", iArr[i], this.mSensorId), (iArr.length - i) - 1);
                    Slog.d(this.mTag, "onEnrollmentsRemoved : faceId: " + iArr[i]);
                    SemFaceServiceExImpl.getInstance().onRemovedExt("", iArr[i]);
                }
                return;
            }
            removalConsumer.onRemoved(null, 0);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticatorIdRetrieved(final long j) {
            String str = this.mTag;
            StringBuilder sb = new StringBuilder();
            sb.append("onAuthenticatorIdRetrieved ");
            sb.append(Utils.DEBUG ? Long.valueOf(j) : "");
            Slog.d(str, sb.toString());
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticatorIdRetrieved$16(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticatorIdRetrieved$16(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceGetAuthenticatorIdClient)) {
                Slog.e(this.mTag, "onAuthenticatorIdRetrieved for wrong consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceGetAuthenticatorIdClient) currentClient).onAuthenticatorIdRetrieved(j);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticatorIdInvalidated(final long j) {
            Slog.d(this.mTag, "onAuthenticatorIdInvalidated");
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$HalSessionCallback$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Sensor.HalSessionCallback.this.lambda$onAuthenticatorIdInvalidated$17(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticatorIdInvalidated$17(long j) {
            BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof FaceInvalidationClient)) {
                Slog.e(this.mTag, "onAuthenticatorIdInvalidated for wrong consumer: " + Utils.getClientName(currentClient));
                return;
            }
            ((FaceInvalidationClient) currentClient).onAuthenticatorIdInvalidated(j);
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onSessionClosed() {
            Handler handler = this.mHandler;
            UserAwareBiometricScheduler userAwareBiometricScheduler = this.mScheduler;
            Objects.requireNonNull(userAwareBiometricScheduler);
            handler.post(new Sensor$HalSessionCallback$$ExternalSyntheticLambda2(userAwareBiometricScheduler));
        }
    }

    public Sensor(String str, FaceProvider faceProvider, Context context, Handler handler, FaceSensorPropertiesInternal faceSensorPropertiesInternal, LockoutResetDispatcher lockoutResetDispatcher, BiometricContext biometricContext, AidlSession aidlSession) {
        this.mTag = str;
        this.mProvider = faceProvider;
        this.mContext = context;
        this.mToken = new Binder();
        this.mHandler = handler;
        this.mSensorProperties = faceSensorPropertiesInternal;
        this.mScheduler = new SemFaceUserAwareScheduler(str, 1, null, new UserAwareBiometricScheduler.CurrentUserRetriever() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever
            public final int getCurrentUserId() {
                int lambda$new$0;
                lambda$new$0 = Sensor.this.lambda$new$0();
                return lambda$new$0;
            }
        }, new C08681(biometricContext, lockoutResetDispatcher, faceProvider));
        this.mLockoutCache = new LockoutCache();
        this.mLockoutHalImpl = new SemFaceAidlLockoutHalImpl(context, new SemFaceAidlLockoutHalImpl.LockoutResetCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda1
            @Override // com.android.server.biometrics.sensors.face.aidl.SemFaceAidlLockoutHalImpl.LockoutResetCallback
            public final void onLockoutReset(int i) {
                Sensor.this.lambda$new$1(i);
            }
        });
        this.mAuthenticatorIds = new HashMap();
        this.mLazySession = new Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                AidlSession lambda$new$2;
                lambda$new$2 = Sensor.this.lambda$new$2();
                return lambda$new$2;
            }
        };
    }

    /* renamed from: com.android.server.biometrics.sensors.face.aidl.Sensor$1 */
    public class C08681 implements UserAwareBiometricScheduler.UserSwitchCallback {
        public final /* synthetic */ BiometricContext val$biometricContext;
        public final /* synthetic */ LockoutResetDispatcher val$lockoutResetDispatcher;
        public final /* synthetic */ FaceProvider val$provider;

        public C08681(BiometricContext biometricContext, LockoutResetDispatcher lockoutResetDispatcher, FaceProvider faceProvider) {
            this.val$biometricContext = biometricContext;
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
            this.val$provider = faceProvider;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        public StopUserClient getStopUserClient(int i) {
            Slog.w(Sensor.this.mTag, "UserAwareBiometricScheduler.UserSwitchCallback getStopUserClient(" + i + ")");
            return new FaceStopUserClient(Sensor.this.mContext, Sensor.this.mLazySession, Sensor.this.mToken, i, Sensor.this.mSensorProperties.sensorId, BiometricLogger.ofUnknown(Sensor.this.mContext), this.val$biometricContext, new StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda0
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    Sensor.C08681.this.lambda$getStopUserClient$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$0() {
            Sensor.this.mCurrentSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        public StartUserClient getStartUserClient(int i) {
            Slog.w(Sensor.this.mTag, "UserAwareBiometricScheduler.UserSwitchCallback getStartUserClient(" + i + ")");
            final int i2 = Sensor.this.mSensorProperties.sensorId;
            final HalSessionCallback halSessionCallback = new HalSessionCallback(Sensor.this.mContext, Sensor.this.mHandler, Sensor.this.mTag, Sensor.this.mScheduler, i2, i, Sensor.this.mLockoutCache, this.val$lockoutResetDispatcher, this.val$biometricContext.getAuthSessionCoordinator(), new HalSessionCallback.Callback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda1
                @Override // com.android.server.biometrics.sensors.face.aidl.Sensor.HalSessionCallback.Callback
                public final void onHardwareUnavailable() {
                    Sensor.C08681.this.lambda$getStartUserClient$1();
                }
            });
            halSessionCallback.setLockoutImpl(Sensor.this.mLockoutHalImpl);
            final FaceProvider faceProvider = this.val$provider;
            StartUserClient.UserStartedCallback userStartedCallback = new StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda2
                @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
                public final void onUserStarted(int i3, Object obj, int i4) {
                    Sensor.C08681.this.lambda$getStartUserClient$2(halSessionCallback, i2, faceProvider, i3, (ISession) obj, i4);
                }
            };
            Context context = Sensor.this.mContext;
            final FaceProvider faceProvider2 = this.val$provider;
            Objects.requireNonNull(faceProvider2);
            return new FaceStartUserClient(context, new Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return FaceProvider.this.getHalInstance();
                }
            }, Sensor.this.mToken, i, Sensor.this.mSensorProperties.sensorId, BiometricLogger.ofUnknown(Sensor.this.mContext), this.val$biometricContext, halSessionCallback, userStartedCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStartUserClient$1() {
            Slog.e(Sensor.this.mTag, "Got ERROR_HW_UNAVAILABLE");
            Sensor.this.mCurrentSession = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStartUserClient$2(HalSessionCallback halSessionCallback, int i, FaceProvider faceProvider, int i2, ISession iSession, int i3) {
            Slog.d(Sensor.this.mTag, "New session created for user: " + i2 + " with hal version: " + i3);
            Sensor.this.mCurrentSession = new AidlSession(i3, iSession, i2, halSessionCallback);
            if (FaceUtils.getLegacyInstance(i).isInvalidationInProgress(Sensor.this.mContext, i2)) {
                Slog.w(Sensor.this.mTag, "Scheduling unfinished invalidation request for sensor: " + i + ", user: " + i2);
                faceProvider.scheduleInvalidationRequest(i, i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$0() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            return aidlSession.getUserId();
        }
        return -10000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(int i) {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession == null || aidlSession.getUserId() != i) {
            return;
        }
        this.mCurrentSession.getHalSessionCallback().onLockoutCleared();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AidlSession lambda$new$2() {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession != null) {
            return aidlSession;
        }
        return null;
    }

    public Sensor(String str, FaceProvider faceProvider, Context context, Handler handler, FaceSensorPropertiesInternal faceSensorPropertiesInternal, LockoutResetDispatcher lockoutResetDispatcher, BiometricContext biometricContext) {
        this(str, faceProvider, context, handler, faceSensorPropertiesInternal, lockoutResetDispatcher, biometricContext, null);
    }

    public Supplier getLazySession() {
        return this.mLazySession;
    }

    public FaceSensorPropertiesInternal getSensorProperties() {
        return this.mSensorProperties;
    }

    public AidlSession getSessionForUser(int i) {
        AidlSession aidlSession = this.mCurrentSession;
        if (aidlSession == null || aidlSession.getUserId() != i) {
            return null;
        }
        return this.mCurrentSession;
    }

    public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback) {
        return new BiometricTestSessionImpl(this.mContext, this.mSensorProperties.sensorId, iTestSessionCallback, this.mProvider, this);
    }

    public BiometricScheduler getScheduler() {
        return this.mScheduler;
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
        protoOutputStream.write(1159641169922L, 2);
        protoOutputStream.write(1120986464259L, Utils.getCurrentStrength(this.mSensorProperties.sensorId));
        protoOutputStream.write(1146756268036L, this.mScheduler.dumpProtoState(z));
        Iterator it = UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            long start2 = protoOutputStream.start(2246267895813L);
            protoOutputStream.write(1120986464257L, identifier);
            protoOutputStream.write(1120986464258L, FaceUtils.getInstance(this.mSensorProperties.sensorId).getBiometricsForUser(this.mContext, identifier).size());
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
        protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
        protoOutputStream.end(start);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onBinderDied() {
        BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient != 0 && currentClient.isInterruptable()) {
            Slog.e(this.mTag, "Sending ERROR_HW_UNAVAILABLE for client: " + currentClient);
            ((ErrorConsumer) currentClient).onError(1, 0);
            FrameworkStatsLog.write(148, 4, 1, -1);
        } else if (currentClient != 0) {
            currentClient.cancel();
        }
        this.mScheduler.recordCrashState();
        this.mScheduler.reset();
        this.mCurrentSession = null;
    }

    public boolean getTestHalEnabled() {
        return this.mTestHalEnabled;
    }

    public int getRemainingLockoutTime(int i) {
        SemFaceAidlLockoutHalImpl semFaceAidlLockoutHalImpl = this.mLockoutHalImpl;
        if (semFaceAidlLockoutHalImpl != null) {
            return semFaceAidlLockoutHalImpl.getRemainingLockoutTime(i);
        }
        return 0;
    }
}
