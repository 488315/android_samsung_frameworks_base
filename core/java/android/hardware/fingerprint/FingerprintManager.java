package android.hardware.fingerprint;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.BiometricTestSession;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.SensorProperties;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.identity.IdentityCredential;
import android.security.identity.PresentationSession;
import android.util.Slog;
import android.view.WindowManager;

import com.android.internal.R;
import com.android.internal.util.FrameworkStatsLog;

import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.bio.fingerprint.SemFingerprintManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;

@Deprecated
public class FingerprintManager implements BiometricAuthenticator, BiometricFingerprintConstants {
    public static final int ENROLL_ENROLL = 2;
    public static final int ENROLL_FIND_SENSOR = 1;
    public static final int SECURITY_LEVEL_CONVENIENCE = 3;
    public static final int SECURITY_LEVEL_NONE = 0;
    public static final int SECURITY_LEVEL_STRONG = 1;
    public static final int SECURITY_LEVEL_WEAK = 2;
    public static final int SEM_FLAG_AUTHENTICATION_NO_SYSTEM_UI = 32768;
    public static final int SEM_SENSOR_POSITION_DISPLAY = 2;
    public static final int SEM_SENSOR_POSITION_HOME_KEY = 1;
    public static final int SEM_SENSOR_POSITION_POWER_KEY = 4;
    public static final int SEM_SENSOR_POSITION_REAR = 3;
    public static final int SEM_SENSOR_POSITION_UNKNOWN = 0;
    public static final int SENSOR_ID_ANY = -1;
    private static final String TAG = "FingerprintManager";
    public static final int UDFPS_UI_OVERLAY_SHOWN = 1;
    public static final int UDFPS_UI_READY = 2;
    private final Context mContext;
    private float[] mEnrollStageThresholds;
    private HandlerExecutor mExecutor;
    private Handler mHandler;
    private final IFingerprintService mService;
    private final IBinder mToken = new Binder();
    private List<FingerprintSensorPropertiesInternal> mProps = new ArrayList();

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticationFlag {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnrollReason {}

    public interface GenerateChallengeCallback {
        void onChallengeGenerated(int i, int i2, long j);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityLevel {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorPosition {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface UdfpsUiEvent {}

    public List<SensorProperties> getSensorProperties() {
        List<SensorProperties> properties = new ArrayList<>();
        List<FingerprintSensorPropertiesInternal> internalProperties =
                getSensorPropertiesInternal();
        for (FingerprintSensorPropertiesInternal internalProp : internalProperties) {
            properties.add(FingerprintSensorProperties.from(internalProp));
        }
        return properties;
    }

    public BiometricTestSession createTestSession(int sensorId) {
        try {
            return new BiometricTestSession(
                    this.mContext,
                    sensorId,
                    new BiometricTestSession.TestSessionProvider() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda2
                        @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
                        public final ITestSession createTestSession(
                                Context context, int i, ITestSessionCallback iTestSessionCallback) {
                            ITestSession lambda$createTestSession$0;
                            lambda$createTestSession$0 =
                                    FingerprintManager.this.lambda$createTestSession$0(
                                            context, i, iTestSessionCallback);
                            return lambda$createTestSession$0;
                        }
                    });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public /* synthetic */ ITestSession lambda$createTestSession$0(
            Context context, int sensorId1, ITestSessionCallback callback) throws RemoteException {
        return this.mService.createTestSession(sensorId1, callback, context.getOpPackageName());
    }

    private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        private OnEnrollCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(
                    FingerprintManager.TAG,
                    "Cancel fingerprint enrollment requested for: " + this.mAuthRequestId);
            FingerprintManager.this.cancelEnrollment(this.mAuthRequestId);
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(
                    FingerprintManager.TAG,
                    "Cancel fingerprint authentication requested for: " + this.mAuthRequestId);
            FingerprintManager.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    private class OnFingerprintDetectionCancelListener
            implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnFingerprintDetectionCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(
                    FingerprintManager.TAG,
                    "Cancel fingerprint detect requested for: " + this.mAuthRequestId);
            FingerprintManager.this.cancelFingerprintDetect(this.mAuthRequestId);
        }
    }

    @Deprecated
    public static final class CryptoObject extends android.hardware.biometrics.CryptoObject {
        public CryptoObject(Signature signature) {
            super(signature);
        }

        public CryptoObject(Cipher cipher) {
            super(cipher);
        }

        public CryptoObject(Mac mac) {
            super(mac);
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Signature getSignature() {
            return super.getSignature();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Cipher getCipher() {
            return super.getCipher();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public Mac getMac() {
            return super.getMac();
        }

        @Override // android.hardware.biometrics.CryptoObject
        @Deprecated
        public IdentityCredential getIdentityCredential() {
            return super.getIdentityCredential();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public PresentationSession getPresentationSession() {
            return super.getPresentationSession();
        }

        @Override // android.hardware.biometrics.CryptoObject
        public KeyAgreement getKeyAgreement() {
            return super.getKeyAgreement();
        }
    }

    @Deprecated
    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Fingerprint mFingerprint;
        private boolean mIsStrongBiometric;
        private int mUserId;

        public AuthenticationResult(
                CryptoObject crypto,
                Fingerprint fingerprint,
                int userId,
                boolean isStrongBiometric) {
            this.mCryptoObject = crypto;
            this.mFingerprint = fingerprint;
            this.mUserId = userId;
            this.mIsStrongBiometric = isStrongBiometric;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Fingerprint getFingerprint() {
            return this.mFingerprint;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public boolean isStrongBiometric() {
            return this.mIsStrongBiometric;
        }
    }

    @Deprecated
    public abstract static class AuthenticationCallback
            extends BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {}

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {}

        public void onAuthenticationSucceeded(AuthenticationResult result) {}

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {}

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int acquireInfo) {}

        public void onUdfpsPointerDown(int sensorId) {}

        public void onUdfpsPointerUp(int sensorId) {}
    }

    public interface FingerprintDetectionCallback {
        void onFingerprintDetected(int i, int i2, boolean z);

        default void onDetectionError(int errorMsgId) {}
    }

    public abstract static class EnrollmentCallback {
        public void onEnrollmentError(int errMsgId, CharSequence errString) {}

        public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {}

        public void onEnrollmentProgress(int remaining) {}

        public void onAcquired(boolean isAcquiredGood) {}

        public void onUdfpsPointerDown(int sensorId) {}

        public void onUdfpsPointerUp(int sensorId) {}

        public void onUdfpsOverlayShown() {}
    }

    public abstract static class RemovalCallback {
        public void onRemovalError(Fingerprint fp, int errMsgId, CharSequence errString) {}

        public void onRemovalSucceeded(Fingerprint fp, int remaining) {}
    }

    public abstract static class LockoutResetCallback {
        public void onLockoutReset(int sensorId) {}
    }

    public abstract static class SemRequestCallback {
        public void onRequested(int msgId) {}
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = handler;
            this.mExecutor = new HandlerExecutor(this.mHandler);
        } else if (this.mHandler != this.mContext.getMainThreadHandler()) {
            this.mHandler = this.mContext.getMainThreadHandler();
            this.mExecutor = new HandlerExecutor(this.mHandler);
        }
    }

    @Deprecated
    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            int flags,
            AuthenticationCallback callback,
            Handler handler) {
        authenticate(crypto, cancel, callback, handler, -1, this.mContext.getUserId(), flags);
    }

    @Deprecated
    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            AuthenticationCallback callback,
            Handler handler,
            int userId) {
        authenticate(crypto, cancel, callback, handler, -1, userId, 0);
    }

    @Deprecated
    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            AuthenticationCallback callback,
            Handler handler,
            int sensorId,
            int userId,
            int flags) {
        authenticate(
                crypto,
                cancel,
                callback,
                handler,
                new FingerprintAuthenticateOptions.Builder()
                        .setSensorId(sensorId)
                        .setUserId(userId)
                        .setIgnoreEnrollmentState(flags != 0)
                        .build());
    }

    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            AuthenticationCallback callback,
            Handler handler,
            FingerprintAuthenticateOptions options) {
        authenticate(crypto, cancel, callback, handler, options, false);
    }

    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            AuthenticationCallback callback,
            Handler handler,
            FingerprintAuthenticateOptions options,
            boolean allowEvenIfEncryptedOrLockdown) {
        FrameworkStatsLog.write(
                356,
                1,
                this.mContext.getApplicationInfo().uid,
                this.mContext.getApplicationInfo().targetSdkVersion);
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancel != null && cancel.isCanceled()) {
            Slog.w(TAG, "authentication already canceled");
            return;
        }
        options.setOpPackageName(this.mContext.getOpPackageName());
        options.setAttributionTag(this.mContext.getAttributionTag());
        if (this.mService != null) {
            try {
                final FingerprintCallback fingerprintCallback =
                        new FingerprintCallback(callback, crypto);
                try {
                    useHandler(handler);
                    long operationId = crypto != null ? crypto.getOpId() : 0L;
                    Bundle extraInfo = new Bundle();
                    SemFingerprintManager.setExtraInfo(this.mContext, extraInfo);
                    extraInfo.putBoolean(
                            SemFingerprintManager
                                    .EXTRA_KEY_ALLOW_AUTH_EVEN_IF_ENCRYPTED_OR_LOCKDOWN,
                            allowEvenIfEncryptedOrLockdown);
                    long authId =
                            this.mService.semAuthenticate(
                                    this.mToken,
                                    operationId,
                                    new FingerprintServiceReceiver(fingerprintCallback),
                                    options,
                                    extraInfo);
                    if (cancel != null) {
                        cancel.setOnCancelListener(new OnAuthenticationCancelListener(authId));
                    }
                    if (authId < 0) {
                        this.mExecutor.execute(
                                new Runnable() { // from class:
                                    // android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        FingerprintManager.this.lambda$authenticate$1(
                                                fingerprintCallback);
                                    }
                                });
                    }
                } catch (RemoteException e) {
                    e = e;
                    Slog.w(TAG, "Remote exception while authenticating: ", e);
                    callback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
                }
            } catch (RemoteException e2) {
                e = e2;
            }
        }
    }

    public /* synthetic */ void lambda$authenticate$1(FingerprintCallback fingerprintCallback) {
        fingerprintCallback.sendErrorResult(this.mContext, 5, 0);
    }

    public void detectFingerprint(
            CancellationSignal cancel,
            FingerprintDetectionCallback callback,
            FingerprintAuthenticateOptions options) {
        if (this.mService == null) {
            return;
        }
        if (cancel.isCanceled()) {
            Slog.w(TAG, "Detection already cancelled");
            return;
        }
        options.setOpPackageName(this.mContext.getOpPackageName());
        options.setAttributionTag(this.mContext.getAttributionTag());
        FingerprintCallback fingerprintCallback = new FingerprintCallback(callback);
        try {
            long authId =
                    this.mService.detectFingerprint(
                            this.mToken,
                            new FingerprintServiceReceiver(fingerprintCallback),
                            options);
            cancel.setOnCancelListener(new OnFingerprintDetectionCancelListener(authId));
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void setIgnoreDisplayTouches(long requestId, int sensorId, boolean ignoreTouch) {
        if (this.mService == null) {
            Slog.w(TAG, "setIgnoreDisplayTouches: no fingerprint service");
            return;
        }
        try {
            this.mService.setIgnoreDisplayTouches(requestId, sensorId, ignoreTouch);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enroll(
            byte[] hardwareAuthToken,
            CancellationSignal cancel,
            int userId,
            EnrollmentCallback callback,
            int enrollReason,
            FingerprintEnrollOptions options) {
        int userId2;
        if (userId != -2) {
            userId2 = userId;
        } else {
            userId2 = getCurrentUserId();
        }
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancel != null && cancel.isCanceled()) {
            Slog.w(TAG, "enrollment already canceled");
            return;
        }
        if (hardwareAuthToken == null) {
            callback.onEnrollmentError(2, getErrorString(this.mContext, 2, 0));
            return;
        }
        if (this.mService != null) {
            try {
                final FingerprintCallback fingerprintCallback = new FingerprintCallback(callback);
                long enrollId =
                        this.mService.enroll(
                                this.mToken,
                                hardwareAuthToken,
                                userId2,
                                new FingerprintServiceReceiver(fingerprintCallback),
                                this.mContext.getOpPackageName(),
                                enrollReason,
                                options);
                if (cancel != null) {
                    cancel.setOnCancelListener(new OnEnrollCancelListener(enrollId));
                }
                if (enrollId < 0) {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FingerprintManager.this.lambda$enroll$2(fingerprintCallback);
                                }
                            });
                }
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception in enroll: ", e);
                callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
            }
        }
    }

    public /* synthetic */ void lambda$enroll$2(FingerprintCallback fingerprintCallback) {
        fingerprintCallback.sendErrorResult(this.mContext, 5, 0);
    }

    public void generateChallenge(int sensorId, int userId, GenerateChallengeCallback callback) {
        if (this.mService != null) {
            try {
                FingerprintCallback fingerprintCallback = new FingerprintCallback(callback);
                this.mService.generateChallenge(
                        this.mToken,
                        sensorId,
                        userId,
                        new FingerprintServiceReceiver(fingerprintCallback),
                        this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int userId, GenerateChallengeCallback callback) {
        FingerprintSensorPropertiesInternal sensorProps = getFirstFingerprintSensor();
        if (sensorProps == null) {
            Slog.e(TAG, "No sensors");
        } else {
            generateChallenge(sensorProps.sensorId, userId, callback);
        }
    }

    public void revokeChallenge(int userId, long challenge) {
        if (this.mService != null) {
            try {
                FingerprintSensorPropertiesInternal sensorProps = getFirstFingerprintSensor();
                if (sensorProps == null) {
                    Slog.e(TAG, "No sensors");
                } else {
                    this.mService.revokeChallenge(
                            this.mToken,
                            sensorProps.sensorId,
                            userId,
                            this.mContext.getOpPackageName(),
                            challenge);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int sensorId, int userId, byte[] hardwareAuthToken) {
        if (this.mService != null) {
            try {
                this.mService.resetLockout(
                        this.mToken,
                        sensorId,
                        userId,
                        hardwareAuthToken,
                        this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(Fingerprint fp, int userId, RemovalCallback callback) {
        if (this.mService != null) {
            try {
                FingerprintCallback fingerprintCallback = new FingerprintCallback(callback, 1, fp);
                this.mService.remove(
                        this.mToken,
                        fp.getBiometricId(),
                        userId,
                        new FingerprintServiceReceiver(fingerprintCallback),
                        this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int userId, RemovalCallback callback) {
        if (this.mService != null) {
            try {
                FingerprintCallback fingerprintCallback =
                        new FingerprintCallback(callback, 2, null);
                this.mService.removeAll(
                        this.mToken,
                        userId,
                        new FingerprintServiceReceiver(fingerprintCallback),
                        this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void rename(int fpId, int userId, String newName) {
        if (this.mService != null) {
            try {
                this.mService.rename(fpId, userId, newName);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "rename(): Service not connected!");
    }

    public List<Fingerprint> getEnrolledFingerprints(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.getEnrolledFingerprints(
                        userId,
                        this.mContext.getOpPackageName(),
                        this.mContext.getAttributionTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<Fingerprint> getEnrolledFingerprints() {
        return getEnrolledFingerprints(this.mContext.getUserId());
    }

    public boolean hasEnrolledTemplates() {
        return hasEnrolledFingerprints();
    }

    public boolean hasEnrolledTemplates(int userId) {
        return hasEnrolledFingerprints(userId);
    }

    public void setUdfpsOverlayController(IUdfpsOverlayController controller) {
        if (this.mService == null) {
            Slog.w(TAG, "setUdfpsOverlayController: no fingerprint service");
            return;
        }
        try {
            this.mService.setUdfpsOverlayController(controller);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerBiometricStateListener(BiometricStateListener listener) {
        try {
            this.mService.registerBiometricStateListener(listener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerDown(
            long requestId, int sensorId, int x, int y, float minor, float major) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerDown: no fingerprint service");
            return;
        }
        PointerContext pc = new PointerContext();
        pc.x = x;
        pc.y = y;
        pc.minor = minor;
        pc.major = major;
        try {
            this.mService.onPointerDown(requestId, sensorId, pc);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerUp(long requestId, int sensorId) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerUp: no fingerprint service");
            return;
        }
        PointerContext pc = new PointerContext();
        try {
            this.mService.onPointerUp(requestId, sensorId, pc);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerDown(
            long requestId,
            int sensorId,
            int pointerId,
            float x,
            float y,
            float minor,
            float major,
            float orientation,
            long time,
            long gestureStart,
            boolean isAod) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerDown: no fingerprint service");
            return;
        }
        PointerContext pc = new PointerContext();
        pc.pointerId = pointerId;
        pc.x = x;
        pc.y = y;
        pc.minor = minor;
        pc.major = major;
        pc.orientation = orientation;
        pc.time = time;
        pc.gestureStart = gestureStart;
        pc.isAod = isAod;
        try {
        } catch (RemoteException e) {
            e = e;
        }
        try {
            this.mService.onPointerDown(requestId, sensorId, pc);
        } catch (RemoteException e2) {
            e = e2;
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerUp(
            long requestId,
            int sensorId,
            int pointerId,
            float x,
            float y,
            float minor,
            float major,
            float orientation,
            long time,
            long gestureStart,
            boolean isAod) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerUp: no fingerprint service");
            return;
        }
        PointerContext pc = new PointerContext();
        pc.pointerId = pointerId;
        pc.x = x;
        pc.y = y;
        pc.minor = minor;
        pc.major = major;
        pc.orientation = orientation;
        pc.time = time;
        pc.gestureStart = gestureStart;
        pc.isAod = isAod;
        try {
        } catch (RemoteException e) {
            e = e;
        }
        try {
            this.mService.onPointerUp(requestId, sensorId, pc);
        } catch (RemoteException e2) {
            e = e2;
            throw e.rethrowFromSystemServer();
        }
    }

    public void onUdfpsUiEvent(int event, long requestId, int sensorId) {
        if (this.mService == null) {
            Slog.w(TAG, "onUdfpsUiEvent: no fingerprint service");
            return;
        }
        try {
            this.mService.onUdfpsUiEvent(event, requestId, sensorId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPowerPressed() {
        Slog.i(TAG, "onPowerPressed");
        this.mExecutor.execute(
                new Runnable() { // from class:
                    // android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintManager.this.lambda$onPowerPressed$3();
                    }
                });
    }

    public void onPowerPressed(boolean goToSleep) {
        if (!goToSleep) {
            return;
        }
        this.mExecutor.execute(
                new Runnable() { // from class:
                    // android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintManager.this.lambda$onPowerPressed$4();
                    }
                });
    }

    public /* synthetic */ void lambda$onPowerPressed$4() {
        try {
            this.mService.onPowerSinglePressed();
        } catch (RemoteException e) {
            Slog.e(TAG, "Error sending power press", e);
        }
    }

    @Deprecated
    public boolean hasEnrolledFingerprints() {
        FrameworkStatsLog.write(
                356,
                2,
                this.mContext.getApplicationInfo().uid,
                this.mContext.getApplicationInfo().targetSdkVersion);
        return hasEnrolledFingerprints(UserHandle.myUserId());
    }

    public boolean hasEnrolledFingerprints(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.hasEnrolledFingerprintsDeprecated(
                        userId,
                        this.mContext.getOpPackageName(),
                        this.mContext.getAttributionTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Deprecated
    public boolean isHardwareDetected() {
        FrameworkStatsLog.write(
                356,
                3,
                this.mContext.getApplicationInfo().uid,
                this.mContext.getApplicationInfo().targetSdkVersion);
        if (this.mService != null) {
            try {
                return this.mService.isHardwareDetectedDeprecated(
                        this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "isFingerprintHardwareDetected(): Service not connected!");
        return false;
    }

    public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal() {
        try {
            if (this.mProps.isEmpty() && this.mService != null) {
                return this.mService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
            }
            return this.mProps;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPowerbuttonFps() {
        FingerprintSensorPropertiesInternal sensorProps = getFirstFingerprintSensor();
        return sensorProps != null && sensorProps.sensorType == 4;
    }

    public void addAuthenticatorsRegisteredCallback(
            IFingerprintAuthenticatorsRegisteredCallback callback) {
        if (this.mService != null) {
            try {
                this.mService.addAuthenticatorsRegisteredCallback(callback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "addProvidersAvailableCallback(): Service not connected!");
    }

    public int getLockoutModeForUser(int sensorId, int userId) {
        if (this.mService != null) {
            try {
                return this.mService.getLockoutModeForUser(sensorId, userId);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return 0;
            }
        }
        return 0;
    }

    public void scheduleWatchdog() {
        try {
            this.mService.scheduleWatchdog();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addLockoutResetCallback(LockoutResetCallback callback) {
        if (this.mService != null) {
            try {
                PowerManager powerManager =
                        (PowerManager) this.mContext.getSystemService(PowerManager.class);
                this.mService.addLockoutResetCallback(
                        new AnonymousClass1(powerManager, callback),
                        this.mContext.getOpPackageName());
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$1, reason: invalid class name */
    class AnonymousClass1 extends IBiometricServiceLockoutResetCallback.Stub {
        final /* synthetic */ LockoutResetCallback val$callback;
        final /* synthetic */ PowerManager val$powerManager;

        AnonymousClass1(PowerManager powerManager, LockoutResetCallback lockoutResetCallback) {
            this.val$powerManager = powerManager;
            this.val$callback = lockoutResetCallback;
        }

        @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
        public void onLockoutReset(final int sensorId, IRemoteCallback serverCallback)
                throws RemoteException {
            try {
                final PowerManager.WakeLock wakeLock =
                        this.val$powerManager.newWakeLock(1, "lockoutResetCallback");
                wakeLock.acquire();
                Handler handler = FingerprintManager.this.mHandler;
                final LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(
                        new Runnable() { // from class:
                            // android.hardware.fingerprint.FingerprintManager$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                FingerprintManager.AnonymousClass1.lambda$onLockoutReset$0(
                                        FingerprintManager.LockoutResetCallback.this,
                                        sensorId,
                                        wakeLock);
                            }
                        });
            } finally {
                serverCallback.sendResult(null);
            }
        }

        static /* synthetic */ void lambda$onLockoutReset$0(
                LockoutResetCallback callback, int sensorId, PowerManager.WakeLock wakeLock) {
            try {
                callback.onLockoutReset(sensorId);
            } finally {
                wakeLock.release();
            }
        }
    }

    /* renamed from: sendPowerPressed, reason: merged with bridge method [inline-methods] */
    public void lambda$onPowerPressed$3() {
        try {
            this.mService.onPowerPressed();
        } catch (RemoteException e) {
            Slog.e(TAG, "Error sending power press", e);
        }
    }

    public FingerprintManager(Context context, IFingerprintService service) {
        this.mContext = context;
        this.mService = service;
        if (this.mService == null) {
            Slog.v(TAG, "FingerprintService was null");
        }
        if (context.checkCallingOrSelfPermission(Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            try {
                addAuthenticatorsRegisteredCallback(
                        new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class:
                            // android.hardware.fingerprint.FingerprintManager.2
                            @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
                            public void onAllAuthenticatorsRegistered(
                                    List<FingerprintSensorPropertiesInternal> sensors) {
                                FingerprintManager.this.mProps = sensors;
                            }
                        });
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        this.mHandler = context.getMainThreadHandler();
        this.mExecutor = new HandlerExecutor(this.mHandler);
    }

    private int getCurrentUserId() {
        try {
            return ActivityManager.getService().getCurrentUser().id;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private FingerprintSensorPropertiesInternal getFirstFingerprintSensor() {
        List<FingerprintSensorPropertiesInternal> allSensors = getSensorPropertiesInternal();
        if (allSensors.isEmpty()) {
            return null;
        }
        return allSensors.get(0);
    }

    public void cancelEnrollment(long requestId) {
        if (this.mService != null) {
            try {
                this.mService.cancelEnrollment(this.mToken, requestId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelAuthentication(long requestId) {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(
                        this.mToken,
                        this.mContext.getOpPackageName(),
                        this.mContext.getAttributionTag(),
                        requestId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelFingerprintDetect(long requestId) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.cancelFingerprintDetect(
                    this.mToken, this.mContext.getOpPackageName(), requestId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getEnrollStageCount() {
        if (this.mEnrollStageThresholds == null) {
            this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
        }
        return this.mEnrollStageThresholds.length + 1;
    }

    public float getEnrollStageThreshold(int index) {
        if (this.mEnrollStageThresholds == null) {
            this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
        }
        if (index < 0 || index > this.mEnrollStageThresholds.length) {
            Slog.w(TAG, "Unsupported enroll stage index: " + index);
            return index < 0 ? 0.0f : 1.0f;
        }
        if (index == this.mEnrollStageThresholds.length) {
            return 1.0f;
        }
        return this.mEnrollStageThresholds[index];
    }

    private float[] createEnrollStageThresholds(Context context) {
        String[] enrollStageThresholdStrings;
        if (isPowerbuttonFps()) {
            enrollStageThresholdStrings =
                    context.getResources()
                            .getStringArray(R.array.config_sfps_enroll_stage_thresholds);
        } else {
            enrollStageThresholdStrings =
                    context.getResources()
                            .getStringArray(R.array.config_udfps_enroll_stage_thresholds);
        }
        float[] enrollStageThresholds = new float[enrollStageThresholdStrings.length];
        for (int i = 0; i < enrollStageThresholds.length; i++) {
            enrollStageThresholds[i] = Float.parseFloat(enrollStageThresholdStrings[i]);
        }
        return enrollStageThresholds;
    }

    public static String getErrorString(Context context, int errMsg, int vendorCode) {
        String msg = semGetErrorString(context, errMsg, vendorCode);
        if (msg != null) {
            return msg;
        }
        switch (errMsg) {
            case 1:
                return context.getString(R.string.fingerprint_error_hw_not_available);
            case 2:
                return context.getString(R.string.fingerprint_error_unable_to_process);
            case 3:
                return context.getString(R.string.fingerprint_error_timeout);
            case 4:
                return context.getString(R.string.fingerprint_error_no_space);
            case 5:
                return context.getString(R.string.fingerprint_error_canceled);
            case 6:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
                Slog.w(TAG, "Invalid error message: " + errMsg + ", " + vendorCode);
                return context.getString(R.string.fingerprint_error_vendor_unknown);
            case 7:
                return context.getString(R.string.fingerprint_error_lockout);
            case 8:
                return context.getString(R.string.fingerprint_error_unable_to_process);
            case 9:
                return context.getString(R.string.fingerprint_error_lockout_permanent);
            case 10:
                return context.getString(R.string.fingerprint_error_user_canceled);
            case 11:
                return context.getString(R.string.fingerprint_error_no_fingerprints);
            case 12:
                return context.getString(R.string.fingerprint_error_hw_not_present);
            case 15:
                return context.getString(R.string.fingerprint_error_security_update_required);
            case 18:
                return context.getString(R.string.fingerprint_error_bad_calibration);
            case 19:
                return context.getString(R.string.fingerprint_error_power_pressed);
        }
    }

    public static String getAcquiredString(Context context, int acquireInfo, int vendorCode) {
        String msg = semGetAcquiredString(context, acquireInfo, vendorCode);
        if (msg != null || acquireInfo == 6) {
            return msg;
        }
        switch (acquireInfo) {
            case 0:
                return null;
            case 1:
                return context.getString(R.string.fingerprint_acquired_partial);
            case 2:
                return context.getString(R.string.fingerprint_acquired_insufficient);
            case 3:
                return context.getString(R.string.fingerprint_acquired_imager_dirty);
            case 4:
                return context.getString(R.string.fingerprint_acquired_too_slow);
            case 5:
                return context.getString(R.string.fingerprint_acquired_too_fast);
            case 6:
                String[] msgArray =
                        context.getResources().getStringArray(R.array.fingerprint_acquired_vendor);
                if (vendorCode < msgArray.length) {
                    return msgArray[vendorCode];
                }
                break;
            case 7:
                return null;
            case 9:
                return context.getString(R.string.fingerprint_acquired_immobile);
            case 10:
                return context.getString(R.string.fingerprint_acquired_too_bright);
            case 11:
                return context.getString(R.string.fingerprint_acquired_power_press);
        }
        Slog.w(TAG, "Invalid acquired message: " + acquireInfo + ", " + vendorCode);
        return null;
    }

    class FingerprintServiceReceiver extends IFingerprintServiceReceiver.Stub {
        private final FingerprintCallback mFingerprintCallback;

        FingerprintServiceReceiver(FingerprintCallback fingerprintCallback) {
            this.mFingerprintCallback = fingerprintCallback;
        }

        public /* synthetic */ void lambda$onEnrollResult$0(int remaining) {
            this.mFingerprintCallback.sendEnrollResult(remaining);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fp, final int remaining) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this
                                    .lambda$onEnrollResult$0(remaining);
                        }
                    });
        }

        public /* synthetic */ void lambda$onAcquired$1(int acquireInfo, int vendorCode) {
            this.mFingerprintCallback.sendAcquiredResult(
                    FingerprintManager.this.mContext, acquireInfo, vendorCode);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(final int acquireInfo, final int vendorCode) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this.lambda$onAcquired$1(
                                    acquireInfo, vendorCode);
                        }
                    });
        }

        public /* synthetic */ void lambda$onAuthenticationSucceeded$2(
                Fingerprint fp, int userId, boolean isStrongBiometric) {
            this.mFingerprintCallback.sendAuthenticatedSucceeded(fp, userId, isStrongBiometric);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(
                final Fingerprint fp, final int userId, final boolean isStrongBiometric) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this
                                    .lambda$onAuthenticationSucceeded$2(
                                            fp, userId, isStrongBiometric);
                        }
                    });
        }

        public /* synthetic */ void lambda$onFingerprintDetected$3(
                int sensorId, int userId, boolean isStrongBiometric) {
            this.mFingerprintCallback.sendFingerprintDetected(sensorId, userId, isStrongBiometric);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(
                final int sensorId, final int userId, final boolean isStrongBiometric) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this
                                    .lambda$onFingerprintDetected$3(
                                            sensorId, userId, isStrongBiometric);
                        }
                    });
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
            HandlerExecutor handlerExecutor = FingerprintManager.this.mExecutor;
            final FingerprintCallback fingerprintCallback = this.mFingerprintCallback;
            Objects.requireNonNull(fingerprintCallback);
            handlerExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintCallback.this.sendAuthenticatedFailed();
                        }
                    });
        }

        public /* synthetic */ void lambda$onError$4(int error, int vendorCode) {
            this.mFingerprintCallback.sendErrorResult(
                    FingerprintManager.this.mContext, error, vendorCode);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(final int error, final int vendorCode) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this.lambda$onError$4(
                                    error, vendorCode);
                        }
                    });
        }

        public /* synthetic */ void lambda$onRemoved$5(Fingerprint fp, int remaining) {
            this.mFingerprintCallback.sendRemovedResult(fp, remaining);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(final Fingerprint fp, final int remaining) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this.lambda$onRemoved$5(
                                    fp, remaining);
                        }
                    });
        }

        public /* synthetic */ void lambda$onChallengeGenerated$6(
                long challenge, int sensorId, int userId) {
            this.mFingerprintCallback.sendChallengeGenerated(challenge, sensorId, userId);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(
                final int sensorId, final int userId, final long challenge) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this
                                    .lambda$onChallengeGenerated$6(challenge, sensorId, userId);
                        }
                    });
        }

        public /* synthetic */ void lambda$onUdfpsPointerDown$7(int sensorId) {
            this.mFingerprintCallback.sendUdfpsPointerDown(sensorId);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(final int sensorId) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this
                                    .lambda$onUdfpsPointerDown$7(sensorId);
                        }
                    });
        }

        public /* synthetic */ void lambda$onUdfpsPointerUp$8(int sensorId) {
            this.mFingerprintCallback.sendUdfpsPointerUp(sensorId);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(final int sensorId) {
            FingerprintManager.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.FingerprintServiceReceiver.this
                                    .lambda$onUdfpsPointerUp$8(sensorId);
                        }
                    });
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() {
            HandlerExecutor handlerExecutor = FingerprintManager.this.mExecutor;
            final FingerprintCallback fingerprintCallback = this.mFingerprintCallback;
            Objects.requireNonNull(fingerprintCallback);
            handlerExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintCallback.this.sendUdfpsOverlayShown();
                        }
                    });
        }
    }

    public static int semGetTransitionEffectValue() {
        if ("google_touch_display_ultrasonic".contains("transition_effect_on")) {
            return 1;
        }
        if ("google_touch_display_ultrasonic".contains("transition_effect_off")) {
            return 0;
        }
        return -1;
    }

    public static int semGetSensorPosition() {
        if ("google_touch_display_ultrasonic".contains("touch_display")) {
            return 2;
        }
        if ("google_touch_display_ultrasonic".contains("touch_rear")) {
            return 3;
        }
        if ("google_touch_display_ultrasonic".contains("touch_side")) {
            return 4;
        }
        return 1;
    }

    public int semGetMaxEnrollmentNumber() {
        if (this.mService != null) {
            try {
                return this.mService.semGetMaxEnrollmentNumber();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetMaxEnrollmentNumber: " + e.getMessage());
                return 3;
            }
        }
        return 3;
    }

    public boolean semHasFeature(int feature) {
        if (this.mService != null) {
            try {
                return this.mService.semHasFeature(feature);
            } catch (RemoteException e) {
                Slog.w(TAG, "semHasFeature: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public IFingerprintService semGetService() {
        return this.mService;
    }

    public int semForceCBGE() {
        if (this.mService != null) {
            try {
                this.mService.semForceCBGE();
                return 0;
            } catch (RemoteException e) {
                Slog.w(TAG, "semForceCBGE: " + e.getMessage());
                return -2;
            }
        }
        return -2;
    }

    public boolean semIsEnrollSession() {
        if (this.mService != null) {
            try {
                return this.mService.semIsEnrollSession();
            } catch (RemoteException e) {
                Slog.w(TAG, "semIsEnrollSession: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public boolean semIsTemplateDbCorrupted() {
        if (this.mService != null) {
            try {
                return this.mService.semIsTemplateDbCorrupted();
            } catch (RemoteException e) {
                Slog.w(TAG, "semIsTemplateDbCorrupted: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public int semGetSensorStatus() {
        if (this.mService != null) {
            try {
                return this.mService.semGetSensorStatus();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetSensorStatus: " + e.getMessage());
                return 0;
            }
        }
        return 0;
    }

    public boolean semPauseEnroll() {
        if (this.mService != null) {
            try {
                return this.mService.semPauseEnroll();
            } catch (RemoteException e) {
                Slog.w(TAG, "semPauseEnroll: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public boolean semResumeEnroll() {
        if (this.mService != null) {
            try {
                return this.mService.semResumeEnroll();
            } catch (RemoteException e) {
                Slog.w(TAG, "semResumeEnroll: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public boolean requestSessionOpen() {
        if (this.mService != null) {
            try {
                return this.mService.semOpenSession();
            } catch (RemoteException e) {
                Slog.w(TAG, "requestSessionOpen: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public String semGetSensorInfo() {
        if (this.mService != null) {
            try {
                return this.mService.semGetSensorInfo();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetSensorInfo: " + e.getMessage());
                return "";
            }
        }
        return "";
    }

    public String[] semGetUserIdList() {
        if (this.mService != null) {
            try {
                return this.mService.semGetUserIdList();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetUserIdList: " + e.getMessage());
            }
        }
        return new String[0];
    }

    public String semGetDaemonVersion() {
        if (this.mService != null) {
            try {
                return this.mService.semGetDaemonVersion();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetDaemonVersion: " + e.getMessage());
                return "";
            }
        }
        return "";
    }

    public int semRunSensorTest(int cmd, int param, SemRequestCallback callback) {
        if (this.mService != null) {
            if (callback == null) {
                throw new IllegalArgumentException("Must supply an Request callback");
            }
            try {
                ISemFingerprintRequestCallback requestCallback = new AnonymousClass3(callback);
                return this.mService.semRunSensorTest(this.mToken, cmd, param, requestCallback);
            } catch (RemoteException e) {
                Slog.w(TAG, "semRunSensorTest: " + e.getMessage());
                return -2;
            }
        }
        return -2;
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$3, reason: invalid class name */
    class AnonymousClass3 extends ISemFingerprintRequestCallback.Stub {
        final /* synthetic */ SemRequestCallback val$callback;

        AnonymousClass3(SemRequestCallback semRequestCallback) {
            this.val$callback = semRequestCallback;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(final int code) {
            Handler handler = FingerprintManager.this.mHandler;
            final SemRequestCallback semRequestCallback = this.val$callback;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.SemRequestCallback.this.onRequested(code);
                        }
                    });
        }
    }

    public int semGetSensorTestResult(byte[] outBuffer) {
        if (outBuffer == null) {
            return -1;
        }
        if (this.mService != null) {
            try {
                return this.mService.semGetSensorTestResult(outBuffer);
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetSensorTestResult: " + e.getMessage());
                return -2;
            }
        }
        return -2;
    }

    public int semSetScreenStatus(int screenStatus) {
        if (this.mService != null) {
            try {
                return this.mService.semSetScreenStatus(screenStatus);
            } catch (RemoteException e) {
                Slog.w(TAG, "semSetScreenStatus: " + e.getMessage());
                return 0;
            }
        }
        return 0;
    }

    public int semShowBouncerScreen(int showStatus) {
        if (this.mService != null) {
            try {
                return this.mService.semShowBouncerScreen(showStatus);
            } catch (RemoteException e) {
                Slog.w(TAG, "semShowBouncerScreen: " + e.getMessage());
                return 0;
            }
        }
        return 0;
    }

    public IBinder semAddMaskView() {
        if (this.mService != null) {
            try {
                return this.mService.semAddMaskView(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.w(TAG, "semAddMaskView: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    public int semRemoveMaskView(IBinder token) {
        if (this.mService != null) {
            try {
                return this.mService.semRemoveMaskView(token, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.w(TAG, "semRemoveMaskView: " + e.getMessage());
                return -2;
            }
        }
        return -2;
    }

    public IBinder semRegisterFingerprintViewListener(SemFingerprintViewListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Must supply an listener");
        }
        if (this.mService != null) {
            try {
                ISemFingerprintAodController aodController = new AnonymousClass4(listener);
                this.mService.semRegisterAodController(this.mToken, aodController);
                return this.mToken;
            } catch (RemoteException re) {
                Slog.w(TAG, "semRegisterAodController : ", re);
            }
        } else {
            Slog.w(TAG, "semRegisterFingerprintViewListener : Service not connected!");
        }
        return this.mToken;
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$4, reason: invalid class name */
    class AnonymousClass4 extends ISemFingerprintAodController.Stub {
        final /* synthetic */ SemFingerprintViewListener val$listener;

        AnonymousClass4(SemFingerprintViewListener semFingerprintViewListener) {
            this.val$listener = semFingerprintViewListener;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.AnonymousClass4.lambda$turnOnDozeMode$0(
                                    SemFingerprintViewListener.this);
                        }
                    });
        }

        static /* synthetic */ void lambda$turnOnDozeMode$0(SemFingerprintViewListener listener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOnDozeMode");
            listener.onStarted();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.AnonymousClass4.lambda$turnOffDozeMode$1(
                                    SemFingerprintViewListener.this);
                        }
                    });
        }

        static /* synthetic */ void lambda$turnOffDozeMode$1(SemFingerprintViewListener listener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOffDozeMode");
            listener.onStopped();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeHlpmMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.AnonymousClass4.lambda$turnOnDozeHlpmMode$2(
                                    SemFingerprintViewListener.this);
                        }
                    });
        }

        static /* synthetic */ void lambda$turnOnDozeHlpmMode$2(
                SemFingerprintViewListener listener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOnDozeHlpmMode");
            listener.onShow();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeHlpmMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.AnonymousClass4.lambda$turnOffDozeHlpmMode$3(
                                    SemFingerprintViewListener.this);
                        }
                    });
        }

        static /* synthetic */ void lambda$turnOffDozeHlpmMode$3(
                SemFingerprintViewListener listener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOffDozeHlpmMode");
            listener.onDismiss();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void hideAodScreen() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.AnonymousClass4.lambda$hideAodScreen$4(
                                    SemFingerprintViewListener.this);
                        }
                    });
        }

        static /* synthetic */ void lambda$hideAodScreen$4(SemFingerprintViewListener listener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: hideAodScreen");
            listener.onAuthenticationSucceeded();
        }
    }

    public void semUnregisterFingerprintViewListener(IBinder token) {
        if (token == null) {
            throw new IllegalArgumentException("Must supply an token");
        }
        if (this.mService != null) {
            try {
                this.mService.semUnregisterAodController(token);
                return;
            } catch (RemoteException re) {
                Slog.w(TAG, "semUnregisterAodController : ", re);
                return;
            }
        }
        Slog.w(TAG, "semUnregisterFingerprintViewListener : Service not connected!");
    }

    public Rect semGetSensorAreaInDisplay() {
        if (this.mService != null) {
            try {
                return this.mService.semGetSensorAreaInDisplay(0, 0, null);
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetSensorAreaInDisplay: ", e);
            }
        }
        return new Rect();
    }

    public void semShowUdfpsIcon() {
        if (this.mService != null) {
            try {
                this.mService.semShowUdfpsIcon();
            } catch (RemoteException e) {
                Slog.e(TAG, "semShowUdfpsIcon: ", e);
            }
        }
    }

    public Rect semGetFingerIconRectInDisplay() {
        if (this.mService != null) {
            try {
                WindowManager wm =
                        (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
                Point size = new Point();
                wm.getDefaultDisplay().getRealSize(size);
                return this.mService.semGetSensorAreaInDisplay(
                        1, wm.getDefaultDisplay().getRotation(), size);
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetFingerIconRectInDisplay: ", e);
            }
        }
        return new Rect();
    }

    public int semGetIconBottomMargin() {
        if (this.mService != null) {
            try {
                return this.mService.semGetIconBottomMargin();
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetIconBottomMargin: ", e);
                return 0;
            }
        }
        return 0;
    }

    public void semMoveSensorIconInDisplay(int x, int y) {
        if (this.mService != null) {
            try {
                this.mService.semMoveSensorIconInDisplay(x, y);
            } catch (RemoteException e) {
                Slog.e(TAG, "semMoveSensorIconInDisplay: ", e);
            }
        }
    }

    public void semSetFlagForIFAA(int flag, String targetAppPackageName) {
        if (this.mService != null) {
            try {
                this.mService.semSetFlagForIFAA(flag, targetAppPackageName);
            } catch (RemoteException e) {
                Slog.e(TAG, "semSetFlagForIFAA: ", e);
            }
        }
    }

    public int semGetSecurityLevel() {
        if (this.mService != null) {
            try {
                return this.mService.semGetSecurityLevel();
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetSecurityLevel: ", e);
                return 0;
            }
        }
        return 0;
    }

    public String semGetTrustAppVersion() {
        if (this.mService != null) {
            try {
                return this.mService.semGetTrustAppVersion();
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetTrustAppVersion: ", e);
                return "";
            }
        }
        return "";
    }

    public void semUpdateTrustApp(String path, SemRequestCallback callback) {
        if (this.mService != null) {
            if (callback == null) {
                throw new IllegalArgumentException("Must supply an Request callback");
            }
            try {
                ISemFingerprintRequestCallback requestCallback = new AnonymousClass5(callback);
                this.mService.semUpdateTrustApp(
                        path, requestCallback, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.e(TAG, "semUpdateTrustApp: ", e);
            }
        }
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$5, reason: invalid class name */
    class AnonymousClass5 extends ISemFingerprintRequestCallback.Stub {
        final /* synthetic */ SemRequestCallback val$callback;

        AnonymousClass5(SemRequestCallback semRequestCallback) {
            this.val$callback = semRequestCallback;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(final int code) {
            Handler handler = FingerprintManager.this.mHandler;
            final SemRequestCallback semRequestCallback = this.val$callback;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.SemRequestCallback.this.onRequested(code);
                        }
                    });
        }
    }

    public void semSetFodStrictMode(boolean isStrictMode) {
        if (this.mService != null) {
            try {
                this.mService.semSetFodStrictMode(isStrictMode);
            } catch (RemoteException e) {
                Slog.e(TAG, "semSetFodStrictMode: ", e);
            }
        }
    }

    public int semSetCalibrationMode(int param) {
        if (this.mService != null) {
            try {
                this.mService.semSetCalibrationMode(
                        this.mToken, param, this.mContext.getOpPackageName());
                return -1;
            } catch (RemoteException e) {
                Slog.e(TAG, "semSetCalibrationMode: ", e);
                return -1;
            }
        }
        return -1;
    }

    public int semProcessFido(int userId, byte[] in, byte[] out) {
        if (this.mService != null) {
            try {
                return this.mService.semProcessFido(
                        userId, in, out, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.e(TAG, "semProcessFido: ", e);
                return -1;
            }
        }
        return -1;
    }

    public int semGetRemainingLockoutTime(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.semGetRemainingLockoutTime(userId);
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetRemainingLockoutTime: ", e);
                return 0;
            }
        }
        return 0;
    }

    public boolean semCanChangeDeviceColorMode() {
        if (this.mService != null) {
            try {
                return this.mService.semCanChangeDeviceColorMode();
            } catch (RemoteException e) {
                Slog.e(TAG, "semCanChangeDeviceColorMode: ", e);
                return true;
            }
        }
        return true;
    }

    public int request(
            int cmd, byte[] inputBuf, byte[] outputBuf, int inParam, SemRequestCallback callback) {
        return request(cmd, inputBuf, outputBuf, inParam, callback, this.mContext.getUserId());
    }

    public int request(
            int cmd,
            byte[] inputBuf,
            byte[] outputBuf,
            int inParam,
            SemRequestCallback callback,
            int userId) {
        byte[] inputBuf2;
        byte[] outputBuf2;
        switch (cmd) {
            case 9:
                return semProcessFido(userId, inputBuf, outputBuf);
            case 1000:
                semRemove(userId, inParam, callback);
                return 0;
            case 1016:
                return semSetCalibrationMode(inParam);
            case 10000:
                if (outputBuf == null || outputBuf.length <= 0) {
                    return -1;
                }
                String taVersion = semGetTrustAppVersion();
                byte[] src = taVersion.getBytes(StandardCharsets.UTF_8);
                int size = Math.min(src.length, outputBuf.length);
                System.arraycopy(src, 0, outputBuf, 0, size);
                return size;
            case 10001:
                if (inputBuf == null || inputBuf.length <= 0) {
                    return -1;
                }
                semUpdateTrustApp(new String(inputBuf, StandardCharsets.UTF_8), callback);
                return 0;
            default:
                if (this.mService != null) {
                    if (inputBuf != null) {
                        inputBuf2 = inputBuf;
                    } else {
                        try {
                            inputBuf2 = new byte[0];
                        } catch (RemoteException e) {
                            e = e;
                            Slog.v(TAG, "Remote exception in request : ", e);
                            return -2;
                        }
                    }
                    if (outputBuf != null) {
                        outputBuf2 = outputBuf;
                    } else {
                        try {
                            outputBuf2 = new byte[0];
                        } catch (RemoteException e2) {
                            e = e2;
                            Slog.v(TAG, "Remote exception in request : ", e);
                            return -2;
                        }
                    }
                    ISemFingerprintRequestCallback requestCallback = null;
                    if (callback != null) {
                        try {
                            requestCallback = new AnonymousClass6(callback);
                        } catch (RemoteException e3) {
                            e = e3;
                            Slog.v(TAG, "Remote exception in request : ", e);
                            return -2;
                        }
                    }
                    return this.mService.semRequest(
                            this.mToken,
                            cmd,
                            inputBuf2,
                            outputBuf2,
                            inParam,
                            userId,
                            this.mContext.getOpPackageName(),
                            requestCallback);
                }
                Slog.w(TAG, "request : Service not connected!");
                return -2;
        }
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$6, reason: invalid class name */
    class AnonymousClass6 extends ISemFingerprintRequestCallback.Stub {
        final /* synthetic */ SemRequestCallback val$callback;

        AnonymousClass6(SemRequestCallback semRequestCallback) {
            this.val$callback = semRequestCallback;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(final int code) {
            Handler handler = FingerprintManager.this.mHandler;
            final SemRequestCallback semRequestCallback = this.val$callback;
            handler.post(
                    new Runnable() { // from class:
                        // android.hardware.fingerprint.FingerprintManager$6$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintManager.SemRequestCallback.this.onRequested(code);
                        }
                    });
        }
    }

    private void semRemove(int userId, int fingerId, final SemRequestCallback requestCallback) {
        if (this.mContext.checkSelfPermission(Manifest.permission.MANAGE_FINGERPRINT) == -1) {
            throw new SecurityException(
                    "Must have android.permission.MANAGE_FINGERPRINT permission.");
        }
        RemovalCallback removalCallback = new RemovalCallback() { // from class:
                    // android.hardware.fingerprint.FingerprintManager.7
                    @Override // android.hardware.fingerprint.FingerprintManager.RemovalCallback
                    public void onRemovalError(
                            Fingerprint fp, int errMsgId, CharSequence errString) {
                        Slog.d(FingerprintManager.TAG, "semRemove: removal error");
                        if (requestCallback != null) {
                            requestCallback.onRequested(6);
                        }
                    }

                    @Override // android.hardware.fingerprint.FingerprintManager.RemovalCallback
                    public void onRemovalSucceeded(Fingerprint fp, int remaining) {
                        Slog.d(FingerprintManager.TAG, "semRemove: removal succeeded");
                        if (requestCallback != null) {
                            requestCallback.onRequested(0);
                        }
                    }
                };
        if (fingerId == -1) {
            removeAll(userId, removalCallback);
        } else {
            remove(new Fingerprint("", fingerId, 0L), userId, removalCallback);
        }
    }

    private static String semGetAcquiredString(Context context, int acquireInfo, int vendorCode) {
        try {
        } catch (Exception e) {
            Slog.w(TAG, "semGetAcquiredString : Exception = " + e);
        }
        switch (acquireInfo) {
            case 0:
                return null;
            case 1:
                return context.getString(R.string.sem_fingerprint_acquired_partial);
            case 2:
                return context.getString(R.string.sem_fingerprint_acquired_insufficient);
            case 3:
                return context.getString(R.string.sem_fingerprint_acquired_image_dirty);
            case 4:
            default:
                return null;
            case 5:
                return context.getString(R.string.sem_fingerprint_acquired_too_fast);
            case 6:
                if (vendorCode == 1001) {
                    return context.getString(R.string.sem_fingerprint_acquired_too_wet);
                }
                if (vendorCode == 1003) {
                    return context.getString(R.string.sem_fingerprint_acquired_light);
                }
                if (vendorCode == 1004) {
                    return context.getString(R.string.sem_fingerprint_acquired_tsp_block);
                }
                return null;
        }
    }

    private static String semGetErrorString(Context context, int errMsg, int vendorCode) {
        if (errMsg == 7) {
            return context.getString(R.string.sem_fingerprint_error_lockout);
        }
        if (errMsg == 9) {
            return context.getString(R.string.sem_fingerprint_error_lockout_permanent);
        }
        if (errMsg == 5 || errMsg == 8) {
            switch (vendorCode) {
                case 1002:
                case 1003:
                    return context.getString(R.string.sem_fingerprint_error_system_failure);
                case 1004:
                    return context.getString(R.string.sem_fingerprint_error_template_corrupt);
                case 5001:
                    return context.getString(R.string.sem_fingerprint_error_onehand_mode);
                case 5002:
                    return context.getString(R.string.sem_fingerprint_error_insecure_biometrics);
                case 5003:
                    return context.getString(
                            R.string.sem_fingerprint_error_smart_view,
                            context.getString(R.string.sem_fingerprint_app_smart_view));
                default:
                    return context.getString(R.string.fingerprint_error_canceled);
            }
        }
        return null;
    }
}
