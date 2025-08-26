package android.hardware.fingerprint;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
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
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;

@Deprecated
/* loaded from: classes2.dex */
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

    @Deprecated
    public static abstract class AuthenticationCallback extends BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int i) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }

        public void onUdfpsPointerDown(int i) {
        }

        public void onUdfpsPointerUp(int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticationFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnrollReason {
    }

    public static abstract class EnrollmentCallback {
        public void onAcquired(boolean z) {
        }

        public void onEnrollmentError(int i, CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int i, CharSequence charSequence) {
        }

        public void onEnrollmentProgress(int i) {
        }

        public void onUdfpsOverlayShown() {
        }

        public void onUdfpsPointerDown(int i) {
        }

        public void onUdfpsPointerUp(int i) {
        }
    }

    public interface FingerprintDetectionCallback {
        default void onDetectionError(int i) {
        }

        void onFingerprintDetected(int i, int i2, boolean z);
    }

    public interface GenerateChallengeCallback {
        void onChallengeGenerated(int i, int i2, long j);
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset(int i) {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Fingerprint fingerprint, int i, CharSequence charSequence) {
        }

        public void onRemovalSucceeded(Fingerprint fingerprint, int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityLevel {
    }

    public static abstract class SemRequestCallback {
        public void onRequested(int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorPosition {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UdfpsUiEvent {
    }

    public static int semGetSensorPosition() {
        return 2;
    }

    public static int semGetTransitionEffectValue() {
        return -1;
    }

    public List<SensorProperties> getSensorProperties() {
        ArrayList arrayList = new ArrayList();
        Iterator<FingerprintSensorPropertiesInternal> it = getSensorPropertiesInternal().iterator();
        while (it.hasNext()) {
            arrayList.add(FingerprintSensorProperties.from(it.next()));
        }
        return arrayList;
    }

    public BiometricTestSession createTestSession(int i) {
        try {
            return new BiometricTestSession(this.mContext, getSensorProperties(), i, new BiometricTestSession.TestSessionProvider() { // from class: android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda2
                @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
                public final ITestSession createTestSession(Context context, int i2, ITestSessionCallback iTestSessionCallback) {
                    return this.f$0.lambda$createTestSession$0(context, i2, iTestSessionCallback);
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ITestSession lambda$createTestSession$0(Context context, int i, ITestSessionCallback iTestSessionCallback) throws RemoteException {
        return this.mService.createTestSession(i, iTestSessionCallback, context.getOpPackageName());
    }

    private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        private OnEnrollCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FingerprintManager.TAG, "Cancel fingerprint enrollment requested for: " + this.mAuthRequestId);
            FingerprintManager.this.cancelEnrollment(this.mAuthRequestId);
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FingerprintManager.TAG, "Cancel fingerprint authentication requested for: " + this.mAuthRequestId);
            FingerprintManager.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    private class OnFingerprintDetectionCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnFingerprintDetectionCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FingerprintManager.TAG, "Cancel fingerprint detect requested for: " + this.mAuthRequestId);
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

        public AuthenticationResult(CryptoObject cryptoObject, Fingerprint fingerprint, int i, boolean z) {
            this.mCryptoObject = cryptoObject;
            this.mFingerprint = fingerprint;
            this.mUserId = i;
            this.mIsStrongBiometric = z;
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
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, -1, this.mContext.getUserId(), i);
    }

    @Deprecated
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, int i) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, -1, i, 0);
    }

    @Deprecated
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, int i, int i2, int i3) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, new FingerprintAuthenticateOptions.Builder().setSensorId(i).setUserId(i2).setIgnoreEnrollmentState(i3 != 0).build());
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, fingerprintAuthenticateOptions, false);
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, boolean z) {
        FrameworkStatsLog.write(356, 1, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Slog.w(TAG, "authentication already canceled");
            return;
        }
        fingerprintAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        fingerprintAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        if (this.mService != null) {
            try {
                final FingerprintCallback fingerprintCallback = new FingerprintCallback(authenticationCallback, cryptoObject);
                useHandler(handler);
                long opId = cryptoObject != null ? cryptoObject.getOpId() : 0L;
                Bundle bundle = new Bundle();
                SemFingerprintManager.setExtraInfo(this.mContext, bundle);
                bundle.putBoolean(SemFingerprintManager.EXTRA_KEY_ALLOW_AUTH_EVEN_IF_ENCRYPTED_OR_LOCKDOWN, z);
                long jSemAuthenticate = this.mService.semAuthenticate(this.mToken, opId, new FingerprintServiceReceiver(fingerprintCallback), fingerprintAuthenticateOptions, bundle);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(jSemAuthenticate));
                }
                if (jSemAuthenticate < 0) {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$authenticate$1(fingerprintCallback);
                        }
                    });
                }
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception while authenticating: ", e);
                authenticationCallback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticate$1(FingerprintCallback fingerprintCallback) {
        fingerprintCallback.sendErrorResult(this.mContext, 5, 0);
    }

    public void detectFingerprint(CancellationSignal cancellationSignal, FingerprintDetectionCallback fingerprintDetectionCallback, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        if (this.mService == null) {
            return;
        }
        if (cancellationSignal.isCanceled()) {
            Slog.w(TAG, "Detection already cancelled");
            return;
        }
        fingerprintAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        fingerprintAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        try {
            cancellationSignal.setOnCancelListener(new OnFingerprintDetectionCancelListener(this.mService.detectFingerprint(this.mToken, new FingerprintServiceReceiver(new FingerprintCallback(fingerprintDetectionCallback)), fingerprintAuthenticateOptions)));
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void setIgnoreDisplayTouches(long j, int i, boolean z) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            Slog.w(TAG, "setIgnoreDisplayTouches: no fingerprint service");
            return;
        }
        try {
            iFingerprintService.setIgnoreDisplayTouches(j, i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enroll(byte[] bArr, CancellationSignal cancellationSignal, int i, EnrollmentCallback enrollmentCallback, int i2, FingerprintEnrollOptions fingerprintEnrollOptions) {
        int currentUserId = i == -2 ? getCurrentUserId() : i;
        if (enrollmentCallback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Slog.w(TAG, "enrollment already canceled");
            return;
        }
        if (bArr == null) {
            enrollmentCallback.onEnrollmentError(2, getErrorString(this.mContext, 2, 0));
            return;
        }
        if (this.mService != null) {
            try {
                final FingerprintCallback fingerprintCallback = new FingerprintCallback(enrollmentCallback);
                long jEnroll = this.mService.enroll(this.mToken, bArr, currentUserId, new FingerprintServiceReceiver(fingerprintCallback), this.mContext.getOpPackageName(), i2, fingerprintEnrollOptions);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new OnEnrollCancelListener(jEnroll));
                }
                if (jEnroll < 0) {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$enroll$2(fingerprintCallback);
                        }
                    });
                }
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception in enroll: ", e);
                enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enroll$2(FingerprintCallback fingerprintCallback) {
        fingerprintCallback.sendErrorResult(this.mContext, 5, 0);
    }

    public void generateChallenge(int i, int i2, GenerateChallengeCallback generateChallengeCallback) {
        if (this.mService != null) {
            try {
                this.mService.generateChallenge(this.mToken, i, i2, new FingerprintServiceReceiver(new FingerprintCallback(generateChallengeCallback)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int i, GenerateChallengeCallback generateChallengeCallback) {
        FingerprintSensorPropertiesInternal firstFingerprintSensor = getFirstFingerprintSensor();
        if (firstFingerprintSensor == null) {
            Slog.e(TAG, "No sensors");
        } else {
            generateChallenge(firstFingerprintSensor.sensorId, i, generateChallengeCallback);
        }
    }

    public void revokeChallenge(int i, long j) {
        if (this.mService != null) {
            try {
                FingerprintSensorPropertiesInternal firstFingerprintSensor = getFirstFingerprintSensor();
                if (firstFingerprintSensor == null) {
                    Slog.e(TAG, "No sensors");
                } else {
                    this.mService.revokeChallenge(this.mToken, firstFingerprintSensor.sensorId, i, this.mContext.getOpPackageName(), j);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int i, int i2, byte[] bArr) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.resetLockout(this.mToken, i, i2, bArr, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(Fingerprint fingerprint, int i, RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mService.remove(this.mToken, fingerprint.getBiometricId(), i, new FingerprintServiceReceiver(new FingerprintCallback(removalCallback, 1, fingerprint)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int i, RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mService.removeAll(this.mToken, i, new FingerprintServiceReceiver(new FingerprintCallback(removalCallback, 2, null)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void rename(int i, int i2, String str) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.rename(i, i2, str);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "rename(): Service not connected!");
    }

    public List<Fingerprint> getEnrolledFingerprints(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return null;
        }
        try {
            return iFingerprintService.getEnrolledFingerprints(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Fingerprint> getEnrolledFingerprints() {
        return getEnrolledFingerprints(this.mContext.getUserId());
    }

    public boolean hasEnrolledTemplates() {
        return hasEnrolledFingerprints();
    }

    public boolean hasEnrolledTemplates(int i) {
        return hasEnrolledFingerprints(i);
    }

    public void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            Slog.w(TAG, "setUdfpsOverlayController: no fingerprint service");
            return;
        }
        try {
            iFingerprintService.setUdfpsOverlayController(iUdfpsOverlayController);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerBiometricStateListener(BiometricStateListener biometricStateListener) {
        try {
            this.mService.registerBiometricStateListener(biometricStateListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerDown(long j, int i, int i2, int i3, float f, float f2) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerDown: no fingerprint service");
            return;
        }
        PointerContext pointerContext = new PointerContext();
        pointerContext.x = i2;
        pointerContext.y = i3;
        pointerContext.minor = f;
        pointerContext.major = f2;
        try {
            this.mService.onPointerDown(j, i, pointerContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerUp(long j, int i) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerUp: no fingerprint service");
            return;
        }
        try {
            this.mService.onPointerUp(j, i, new PointerContext());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerDown(long j, int i, int i2, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerDown: no fingerprint service");
            return;
        }
        PointerContext pointerContext = new PointerContext();
        pointerContext.pointerId = i2;
        pointerContext.x = f;
        pointerContext.y = f2;
        pointerContext.minor = f3;
        pointerContext.major = f4;
        pointerContext.orientation = f5;
        pointerContext.time = j2;
        pointerContext.gestureStart = j3;
        pointerContext.isAod = z;
        try {
            this.mService.onPointerDown(j, i, pointerContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPointerUp(long j, int i, int i2, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        if (this.mService == null) {
            Slog.w(TAG, "onPointerUp: no fingerprint service");
            return;
        }
        PointerContext pointerContext = new PointerContext();
        pointerContext.pointerId = i2;
        pointerContext.x = f;
        pointerContext.y = f2;
        pointerContext.minor = f3;
        pointerContext.major = f4;
        pointerContext.orientation = f5;
        pointerContext.time = j2;
        pointerContext.gestureStart = j3;
        pointerContext.isAod = z;
        try {
            this.mService.onPointerUp(j, i, pointerContext);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onUdfpsUiEvent(int i, long j, int i2) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            Slog.w(TAG, "onUdfpsUiEvent: no fingerprint service");
            return;
        }
        try {
            iFingerprintService.onUdfpsUiEvent(i, j, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onPowerPressed() {
        Slog.i(TAG, "onPowerPressed");
        this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onPowerPressed$3();
            }
        });
    }

    public void onPowerPressed(boolean z) {
        if (z) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPowerPressed$4();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPowerPressed$4() {
        try {
            this.mService.onPowerSinglePressed();
        } catch (RemoteException e) {
            Slog.e(TAG, "Error sending power press", e);
        }
    }

    @Deprecated
    public boolean hasEnrolledFingerprints() {
        FrameworkStatsLog.write(356, 2, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        return hasEnrolledFingerprints(UserHandle.myUserId());
    }

    public boolean hasEnrolledFingerprints(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.hasEnrolledFingerprintsDeprecated(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isHardwareDetected() {
        FrameworkStatsLog.write(356, 3, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                return iFingerprintService.isHardwareDetectedDeprecated(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "isFingerprintHardwareDetected(): Service not connected!");
        return false;
    }

    public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal() {
        IFingerprintService iFingerprintService;
        try {
            if (this.mProps.isEmpty() && (iFingerprintService = this.mService) != null) {
                return iFingerprintService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
            }
            return this.mProps;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPowerbuttonFps() {
        FingerprintSensorPropertiesInternal firstFingerprintSensor = getFirstFingerprintSensor();
        return firstFingerprintSensor != null && firstFingerprintSensor.sensorType == 4;
    }

    public void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.addAuthenticatorsRegisteredCallback(iFingerprintAuthenticatorsRegisteredCallback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "addProvidersAvailableCallback(): Service not connected!");
    }

    public int getLockoutModeForUser(int i, int i2) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.getLockoutModeForUser(i, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    public void scheduleWatchdog() {
        try {
            this.mService.scheduleWatchdog();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addLockoutResetCallback(LockoutResetCallback lockoutResetCallback) {
        if (this.mService != null) {
            try {
                this.mService.addLockoutResetCallback(new AnonymousClass1((PowerManager) this.mContext.getSystemService(PowerManager.class), lockoutResetCallback), this.mContext.getOpPackageName());
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
        public void onLockoutReset(final int i, IRemoteCallback iRemoteCallback) throws RemoteException {
            try {
                final PowerManager.WakeLock wakeLockNewWakeLock = this.val$powerManager.newWakeLock(1, "lockoutResetCallback");
                wakeLockNewWakeLock.acquire();
                Handler handler = FingerprintManager.this.mHandler;
                final LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintManager.AnonymousClass1.lambda$onLockoutReset$0(lockoutResetCallback, i, wakeLockNewWakeLock);
                    }
                });
            } finally {
                iRemoteCallback.sendResult(null);
            }
        }

        static /* synthetic */ void lambda$onLockoutReset$0(LockoutResetCallback lockoutResetCallback, int i, PowerManager.WakeLock wakeLock) {
            try {
                lockoutResetCallback.onLockoutReset(i);
            } finally {
                wakeLock.release();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendPowerPressed, reason: merged with bridge method [inline-methods] */
    public void lambda$onPowerPressed$3() {
        try {
            this.mService.onPowerPressed();
        } catch (RemoteException e) {
            Slog.e(TAG, "Error sending power press", e);
        }
    }

    public FingerprintManager(Context context, IFingerprintService iFingerprintService) {
        this.mContext = context;
        this.mService = iFingerprintService;
        if (iFingerprintService == null) {
            Slog.v(TAG, "FingerprintService was null");
        }
        if (context.checkCallingOrSelfPermission(Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            try {
                addAuthenticatorsRegisteredCallback(new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: android.hardware.fingerprint.FingerprintManager.2
                    @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
                    public void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> list) {
                        FingerprintManager.this.mProps = list;
                    }
                });
            } catch (SecurityException e) {
                e.printStackTrace();
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
        List<FingerprintSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            return null;
        }
        return sensorPropertiesInternal.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment(long j) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.cancelEnrollment(this.mToken, j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long j) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelFingerprintDetect(long j) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return;
        }
        try {
            iFingerprintService.cancelFingerprintDetect(this.mToken, this.mContext.getOpPackageName(), j);
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

    public float getEnrollStageThreshold(int i) {
        if (this.mEnrollStageThresholds == null) {
            this.mEnrollStageThresholds = createEnrollStageThresholds(this.mContext);
        }
        if (i >= 0) {
            float[] fArr = this.mEnrollStageThresholds;
            if (i <= fArr.length) {
                if (i == fArr.length) {
                    return 1.0f;
                }
                return fArr[i];
            }
        }
        Slog.w(TAG, "Unsupported enroll stage index: " + i);
        return i < 0 ? 0.0f : 1.0f;
    }

    private float[] createEnrollStageThresholds(Context context) throws Resources.NotFoundException {
        String[] stringArray;
        if (isPowerbuttonFps()) {
            stringArray = context.getResources().getStringArray(R.array.config_sfps_enroll_stage_thresholds);
        } else {
            stringArray = context.getResources().getStringArray(R.array.config_udfps_enroll_stage_thresholds);
        }
        int length = stringArray.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = Float.parseFloat(stringArray[i]);
        }
        return fArr;
    }

    public static String getErrorString(Context context, int i, int i2) {
        String strSemGetErrorString = semGetErrorString(context, i, i2);
        if (strSemGetErrorString != null) {
            return strSemGetErrorString;
        }
        switch (i) {
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
                Slog.w(TAG, "Invalid error message: " + i + ", " + i2);
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

    public static String getAcquiredString(Context context, int i, int i2) throws Resources.NotFoundException {
        String strSemGetAcquiredString = semGetAcquiredString(context, i, i2);
        if (strSemGetAcquiredString == null && i != 6) {
            strSemGetAcquiredString = null;
            switch (i) {
                case 0:
                case 7:
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
                    String[] stringArray = context.getResources().getStringArray(R.array.fingerprint_acquired_vendor);
                    if (i2 < stringArray.length && !stringArray[i2].isEmpty()) {
                        return stringArray[i2];
                    }
                    break;
                case 8:
                default:
                    Slog.w(TAG, "Invalid acquired message: " + i + ", " + i2);
                    break;
                case 9:
                    return context.getString(R.string.fingerprint_acquired_immobile);
                case 10:
                    return context.getString(R.string.fingerprint_acquired_too_bright);
                case 11:
                    return context.getString(R.string.fingerprint_acquired_power_press);
            }
        }
        return strSemGetAcquiredString;
    }

    class FingerprintServiceReceiver extends IFingerprintServiceReceiver.Stub {
        private final FingerprintCallback mFingerprintCallback;

        FingerprintServiceReceiver(FingerprintCallback fingerprintCallback) {
            this.mFingerprintCallback = fingerprintCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollResult$0(int i) {
            this.mFingerprintCallback.sendEnrollResult(i);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fingerprint, final int i) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onEnrollResult$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$1(int i, int i2) {
            this.mFingerprintCallback.sendAcquiredResult(FingerprintManager.this.mContext, i, i2);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(final int i, final int i2) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAcquired$1(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$2(Fingerprint fingerprint, int i, boolean z) {
            this.mFingerprintCallback.sendAuthenticatedSucceeded(fingerprint, i, z);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(final Fingerprint fingerprint, final int i, final boolean z) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAuthenticationSucceeded$2(fingerprint, i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFingerprintDetected$3(int i, int i2, boolean z) {
            this.mFingerprintCallback.sendFingerprintDetected(i, i2, z);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(final int i, final int i2, final boolean z) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFingerprintDetected$3(i, i2, z);
                }
            });
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
            HandlerExecutor handlerExecutor = FingerprintManager.this.mExecutor;
            final FingerprintCallback fingerprintCallback = this.mFingerprintCallback;
            Objects.requireNonNull(fingerprintCallback);
            handlerExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    fingerprintCallback.sendAuthenticatedFailed();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$4(int i, int i2) {
            this.mFingerprintCallback.sendErrorResult(FingerprintManager.this.mContext, i, i2);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(final int i, final int i2) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onError$4(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRemoved$5(Fingerprint fingerprint, int i) {
            this.mFingerprintCallback.sendRemovedResult(fingerprint, i);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(final Fingerprint fingerprint, final int i) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRemoved$5(fingerprint, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChallengeGenerated$6(long j, int i, int i2) {
            this.mFingerprintCallback.sendChallengeGenerated(j, i, i2);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(final int i, final int i2, final long j) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onChallengeGenerated$6(j, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUdfpsPointerDown$7(int i) {
            this.mFingerprintCallback.sendUdfpsPointerDown(i);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(final int i) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUdfpsPointerDown$7(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUdfpsPointerUp$8(int i) {
            this.mFingerprintCallback.sendUdfpsPointerUp(i);
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(final int i) {
            FingerprintManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUdfpsPointerUp$8(i);
                }
            });
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() {
            HandlerExecutor handlerExecutor = FingerprintManager.this.mExecutor;
            final FingerprintCallback fingerprintCallback = this.mFingerprintCallback;
            Objects.requireNonNull(fingerprintCallback);
            handlerExecutor.execute(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$FingerprintServiceReceiver$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    fingerprintCallback.sendUdfpsOverlayShown();
                }
            });
        }
    }

    public int semGetMaxEnrollmentNumber() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 3;
        }
        try {
            return iFingerprintService.semGetMaxEnrollmentNumber();
        } catch (RemoteException e) {
            Slog.w(TAG, "semGetMaxEnrollmentNumber: " + e.getMessage());
            return 3;
        }
    }

    public boolean semHasFeature(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.semHasFeature(i);
        } catch (RemoteException e) {
            Slog.w(TAG, "semHasFeature: " + e.getMessage());
            return false;
        }
    }

    public IFingerprintService semGetService() {
        return this.mService;
    }

    public int semForceCBGE() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return -2;
        }
        try {
            iFingerprintService.semForceCBGE();
            return 0;
        } catch (RemoteException e) {
            Slog.w(TAG, "semForceCBGE: " + e.getMessage());
            return -2;
        }
    }

    public boolean semIsEnrollSession() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.semIsEnrollSession();
        } catch (RemoteException e) {
            Slog.w(TAG, "semIsEnrollSession: " + e.getMessage());
            return false;
        }
    }

    public boolean semIsTemplateDbCorrupted() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.semIsTemplateDbCorrupted();
        } catch (RemoteException e) {
            Slog.w(TAG, "semIsTemplateDbCorrupted: " + e.getMessage());
            return false;
        }
    }

    public int semGetSensorStatus() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.semGetSensorStatus();
        } catch (RemoteException e) {
            Slog.w(TAG, "semGetSensorStatus: " + e.getMessage());
            return 0;
        }
    }

    public boolean semPauseEnroll() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.semPauseEnroll();
        } catch (RemoteException e) {
            Slog.w(TAG, "semPauseEnroll: " + e.getMessage());
            return false;
        }
    }

    public boolean semResumeEnroll() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.semResumeEnroll();
        } catch (RemoteException e) {
            Slog.w(TAG, "semResumeEnroll: " + e.getMessage());
            return false;
        }
    }

    public boolean requestSessionOpen() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return false;
        }
        try {
            return iFingerprintService.semOpenSession();
        } catch (RemoteException e) {
            Slog.w(TAG, "requestSessionOpen: " + e.getMessage());
            return false;
        }
    }

    public String semGetSensorInfo() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                return iFingerprintService.semGetSensorInfo();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetSensorInfo: " + e.getMessage());
                return "";
            }
        }
        return "";
    }

    public String[] semGetUserIdList() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                return iFingerprintService.semGetUserIdList();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetUserIdList: " + e.getMessage());
            }
        }
        return new String[0];
    }

    public String semGetDaemonVersion() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                return iFingerprintService.semGetDaemonVersion();
            } catch (RemoteException e) {
                Slog.w(TAG, "semGetDaemonVersion: " + e.getMessage());
                return "";
            }
        }
        return "";
    }

    public int semRunSensorTest(int i, int i2, SemRequestCallback semRequestCallback) {
        if (this.mService == null) {
            return -2;
        }
        if (semRequestCallback == null) {
            throw new IllegalArgumentException("Must supply an Request callback");
        }
        try {
            return this.mService.semRunSensorTest(this.mToken, i, i2, new AnonymousClass3(semRequestCallback));
        } catch (RemoteException e) {
            Slog.w(TAG, "semRunSensorTest: " + e.getMessage());
            return -2;
        }
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$3, reason: invalid class name */
    class AnonymousClass3 extends ISemFingerprintRequestCallback.Stub {
        final /* synthetic */ SemRequestCallback val$callback;

        AnonymousClass3(SemRequestCallback semRequestCallback) {
            this.val$callback = semRequestCallback;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(final int i) {
            Handler handler = FingerprintManager.this.mHandler;
            final SemRequestCallback semRequestCallback = this.val$callback;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    semRequestCallback.onRequested(i);
                }
            });
        }
    }

    public int semGetSensorTestResult(byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return -2;
        }
        try {
            return iFingerprintService.semGetSensorTestResult(bArr);
        } catch (RemoteException e) {
            Slog.w(TAG, "semGetSensorTestResult: " + e.getMessage());
            return -2;
        }
    }

    public int semSetScreenStatus(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.semSetScreenStatus(i);
        } catch (RemoteException e) {
            Slog.w(TAG, "semSetScreenStatus: " + e.getMessage());
            return 0;
        }
    }

    public int semShowBouncerScreen(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.semShowBouncerScreen(i);
        } catch (RemoteException e) {
            Slog.w(TAG, "semShowBouncerScreen: " + e.getMessage());
            return 0;
        }
    }

    public IBinder semAddMaskView() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return null;
        }
        try {
            return iFingerprintService.semAddMaskView(this.mToken, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            Slog.w(TAG, "semAddMaskView: " + e.getMessage());
            return null;
        }
    }

    public int semRemoveMaskView(IBinder iBinder) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return -2;
        }
        try {
            return iFingerprintService.semRemoveMaskView(iBinder, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            Slog.w(TAG, "semRemoveMaskView: " + e.getMessage());
            return -2;
        }
    }

    public IBinder semRegisterFingerprintViewListener(SemFingerprintViewListener semFingerprintViewListener) {
        if (semFingerprintViewListener == null) {
            throw new IllegalArgumentException("Must supply an listener");
        }
        if (this.mService != null) {
            try {
                this.mService.semRegisterAodController(this.mToken, new AnonymousClass4(semFingerprintViewListener));
                return this.mToken;
            } catch (RemoteException e) {
                Slog.w(TAG, "semRegisterAodController : ", e);
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
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintManager.AnonymousClass4.lambda$turnOnDozeMode$0(semFingerprintViewListener);
                }
            });
        }

        static /* synthetic */ void lambda$turnOnDozeMode$0(SemFingerprintViewListener semFingerprintViewListener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOnDozeMode");
            semFingerprintViewListener.onStarted();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintManager.AnonymousClass4.lambda$turnOffDozeMode$1(semFingerprintViewListener);
                }
            });
        }

        static /* synthetic */ void lambda$turnOffDozeMode$1(SemFingerprintViewListener semFingerprintViewListener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOffDozeMode");
            semFingerprintViewListener.onStopped();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeHlpmMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintManager.AnonymousClass4.lambda$turnOnDozeHlpmMode$2(semFingerprintViewListener);
                }
            });
        }

        static /* synthetic */ void lambda$turnOnDozeHlpmMode$2(SemFingerprintViewListener semFingerprintViewListener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOnDozeHlpmMode");
            semFingerprintViewListener.onShow();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeHlpmMode() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintManager.AnonymousClass4.lambda$turnOffDozeHlpmMode$3(semFingerprintViewListener);
                }
            });
        }

        static /* synthetic */ void lambda$turnOffDozeHlpmMode$3(SemFingerprintViewListener semFingerprintViewListener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: turnOffDozeHlpmMode");
            semFingerprintViewListener.onDismiss();
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void hideAodScreen() {
            Handler handler = FingerprintManager.this.mHandler;
            final SemFingerprintViewListener semFingerprintViewListener = this.val$listener;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$4$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintManager.AnonymousClass4.lambda$hideAodScreen$4(semFingerprintViewListener);
                }
            });
        }

        static /* synthetic */ void lambda$hideAodScreen$4(SemFingerprintViewListener semFingerprintViewListener) {
            Slog.i(FingerprintManager.TAG, "deliver event to AOD: hideAodScreen");
            semFingerprintViewListener.onAuthenticationSucceeded();
        }
    }

    public void semUnregisterFingerprintViewListener(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("Must supply an token");
        }
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.semUnregisterAodController(iBinder);
                return;
            } catch (RemoteException e) {
                Slog.w(TAG, "semUnregisterAodController : ", e);
                return;
            }
        }
        Slog.w(TAG, "semUnregisterFingerprintViewListener : Service not connected!");
    }

    public Rect semGetSensorAreaInDisplay() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                return iFingerprintService.semGetSensorAreaInDisplay(0, 0, null);
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetSensorAreaInDisplay: ", e);
            }
        }
        return new Rect();
    }

    public void semShowUdfpsIcon() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.semShowUdfpsIcon();
            } catch (RemoteException e) {
                Slog.e(TAG, "semShowUdfpsIcon: ", e);
            }
        }
    }

    public Rect semGetFingerIconRectInDisplay() {
        if (this.mService != null) {
            try {
                WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
                Point point = new Point();
                windowManager.getDefaultDisplay().getRealSize(point);
                return this.mService.semGetSensorAreaInDisplay(1, windowManager.getDefaultDisplay().getRotation(), point);
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetFingerIconRectInDisplay: ", e);
            }
        }
        return new Rect();
    }

    public int semGetIconBottomMargin() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.semGetIconBottomMargin();
        } catch (RemoteException e) {
            Slog.e(TAG, "semGetIconBottomMargin: ", e);
            return 0;
        }
    }

    public void semMoveSensorIconInDisplay(int i, int i2) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.semMoveSensorIconInDisplay(i, i2);
            } catch (RemoteException e) {
                Slog.e(TAG, "semMoveSensorIconInDisplay: ", e);
            }
        }
    }

    public void semSetFlagForIFAA(int i, String str) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.semSetFlagForIFAA(i, str);
            } catch (RemoteException e) {
                Slog.e(TAG, "semSetFlagForIFAA: ", e);
            }
        }
    }

    public int semGetSecurityLevel() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.semGetSecurityLevel();
        } catch (RemoteException e) {
            Slog.e(TAG, "semGetSecurityLevel: ", e);
            return 0;
        }
    }

    public String semGetTrustAppVersion() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                return iFingerprintService.semGetTrustAppVersion();
            } catch (RemoteException e) {
                Slog.e(TAG, "semGetTrustAppVersion: ", e);
                return "";
            }
        }
        return "";
    }

    public void semUpdateTrustApp(String str, SemRequestCallback semRequestCallback) {
        if (this.mService != null) {
            if (semRequestCallback == null) {
                throw new IllegalArgumentException("Must supply an Request callback");
            }
            try {
                this.mService.semUpdateTrustApp(str, new AnonymousClass5(semRequestCallback), this.mContext.getOpPackageName());
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
        public void onResult(final int i) {
            Handler handler = FingerprintManager.this.mHandler;
            final SemRequestCallback semRequestCallback = this.val$callback;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    semRequestCallback.onRequested(i);
                }
            });
        }
    }

    public void semSetFodStrictMode(boolean z) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.semSetFodStrictMode(z);
            } catch (RemoteException e) {
                Slog.e(TAG, "semSetFodStrictMode: ", e);
            }
        }
    }

    public int semSetCalibrationMode(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return -1;
        }
        try {
            iFingerprintService.semSetCalibrationMode(this.mToken, i, this.mContext.getOpPackageName());
            return -1;
        } catch (RemoteException e) {
            Slog.e(TAG, "semSetCalibrationMode: ", e);
            return -1;
        }
    }

    public int semProcessFido(int i, byte[] bArr, byte[] bArr2) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return -1;
        }
        try {
            return iFingerprintService.semProcessFido(i, bArr, bArr2, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            Slog.e(TAG, "semProcessFido: ", e);
            return -1;
        }
    }

    public int semGetRemainingLockoutTime(int i) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return 0;
        }
        try {
            return iFingerprintService.semGetRemainingLockoutTime(i);
        } catch (RemoteException e) {
            Slog.e(TAG, "semGetRemainingLockoutTime: ", e);
            return 0;
        }
    }

    public boolean semCanChangeDeviceColorMode() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService == null) {
            return true;
        }
        try {
            return iFingerprintService.semCanChangeDeviceColorMode();
        } catch (RemoteException e) {
            Slog.e(TAG, "semCanChangeDeviceColorMode: ", e);
            return true;
        }
    }

    public int request(int i, byte[] bArr, byte[] bArr2, int i2, SemRequestCallback semRequestCallback) {
        return request(i, bArr, bArr2, i2, semRequestCallback, this.mContext.getUserId());
    }

    public int request(int i, byte[] bArr, byte[] bArr2, int i2, SemRequestCallback semRequestCallback, int i3) {
        if (i == 9) {
            return semProcessFido(i3, bArr, bArr2);
        }
        if (i == 1000) {
            semRemove(i3, i2, semRequestCallback);
            return 0;
        }
        if (i == 1016) {
            return semSetCalibrationMode(i2);
        }
        if (i == 10000) {
            if (bArr2 == null || bArr2.length <= 0) {
                return -1;
            }
            byte[] bytes = semGetTrustAppVersion().getBytes(StandardCharsets.UTF_8);
            int iMin = Math.min(bytes.length, bArr2.length);
            System.arraycopy(bytes, 0, bArr2, 0, iMin);
            return iMin;
        }
        if (i == 10001) {
            if (bArr == null || bArr.length <= 0) {
                return -1;
            }
            semUpdateTrustApp(new String(bArr, StandardCharsets.UTF_8), semRequestCallback);
            return 0;
        }
        if (this.mService != null) {
            if (bArr == null) {
                try {
                    bArr = new byte[0];
                } catch (RemoteException e) {
                    Slog.v(TAG, "Remote exception in request : ", e);
                    return -2;
                }
            }
            byte[] bArr3 = bArr;
            if (bArr2 == null) {
                bArr2 = new byte[0];
            }
            return this.mService.semRequest(this.mToken, i, bArr3, bArr2, i2, i3, this.mContext.getOpPackageName(), semRequestCallback != null ? new AnonymousClass6(semRequestCallback) : null);
        }
        Slog.w(TAG, "request : Service not connected!");
        return -2;
    }

    /* renamed from: android.hardware.fingerprint.FingerprintManager$6, reason: invalid class name */
    class AnonymousClass6 extends ISemFingerprintRequestCallback.Stub {
        final /* synthetic */ SemRequestCallback val$callback;

        AnonymousClass6(SemRequestCallback semRequestCallback) {
            this.val$callback = semRequestCallback;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(final int i) {
            Handler handler = FingerprintManager.this.mHandler;
            final SemRequestCallback semRequestCallback = this.val$callback;
            handler.post(new Runnable() { // from class: android.hardware.fingerprint.FingerprintManager$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    semRequestCallback.onRequested(i);
                }
            });
        }
    }

    private void semRemove(int i, int i2, final SemRequestCallback semRequestCallback) {
        if (this.mContext.checkSelfPermission(Manifest.permission.MANAGE_FINGERPRINT) == -1) {
            throw new SecurityException("Must have android.permission.MANAGE_FINGERPRINT permission.");
        }
        RemovalCallback removalCallback = new RemovalCallback(this) { // from class: android.hardware.fingerprint.FingerprintManager.7
            @Override // android.hardware.fingerprint.FingerprintManager.RemovalCallback
            public void onRemovalError(Fingerprint fingerprint, int i3, CharSequence charSequence) {
                Slog.d(FingerprintManager.TAG, "semRemove: removal error");
                SemRequestCallback semRequestCallback2 = semRequestCallback;
                if (semRequestCallback2 != null) {
                    semRequestCallback2.onRequested(6);
                }
            }

            @Override // android.hardware.fingerprint.FingerprintManager.RemovalCallback
            public void onRemovalSucceeded(Fingerprint fingerprint, int i3) {
                Slog.d(FingerprintManager.TAG, "semRemove: removal succeeded");
                SemRequestCallback semRequestCallback2 = semRequestCallback;
                if (semRequestCallback2 != null) {
                    semRequestCallback2.onRequested(0);
                }
            }
        };
        if (i2 == -1) {
            removeAll(i, removalCallback);
        } else {
            remove(new Fingerprint("", i2, 0L), i, removalCallback);
        }
    }

    private static String semGetAcquiredString(Context context, int i, int i2) {
        try {
            if (i == 1) {
                return context.getString(R.string.sem_fingerprint_acquired_partial);
            }
            if (i == 2) {
                return context.getString(R.string.sem_fingerprint_acquired_insufficient);
            }
            if (i == 3) {
                return context.getString(R.string.sem_fingerprint_acquired_image_dirty);
            }
            if (i == 5) {
                return context.getString(R.string.sem_fingerprint_acquired_too_fast);
            }
            if (i != 6) {
                return null;
            }
            if (i2 == 1001) {
                return context.getString(R.string.sem_fingerprint_acquired_too_wet);
            }
            if (i2 == 1003) {
                return context.getString(R.string.sem_fingerprint_acquired_light);
            }
            if (i2 == 1004) {
                return context.getString(R.string.sem_fingerprint_acquired_tsp_block);
            }
            return null;
        } catch (Exception e) {
            Slog.w(TAG, "semGetAcquiredString : Exception = " + e);
            return null;
        }
    }

    private static String semGetErrorString(Context context, int i, int i2) {
        if (i == 7) {
            return context.getString(R.string.sem_fingerprint_error_lockout);
        }
        if (i == 9) {
            return context.getString(R.string.sem_fingerprint_error_lockout_permanent);
        }
        if (i != 5 && i != 8) {
            return null;
        }
        switch (i2) {
            case 1002:
            case 1003:
                return context.getString(R.string.sem_fingerprint_error_system_failure);
            case 1004:
                return context.getString(R.string.sem_fingerprint_error_template_corrupt);
            default:
                switch (i2) {
                    case 5001:
                        return context.getString(R.string.sem_fingerprint_error_onehand_mode);
                    case 5002:
                        return context.getString(R.string.sem_fingerprint_error_insecure_biometrics);
                    case 5003:
                        return context.getString(R.string.sem_fingerprint_error_smart_view, context.getString(R.string.sem_fingerprint_app_smart_view));
                    default:
                        return context.getString(R.string.fingerprint_error_canceled);
                }
        }
    }
}
