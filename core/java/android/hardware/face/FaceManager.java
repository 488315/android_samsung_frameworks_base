package android.hardware.face;

import android.Manifest;
import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFaceConstants;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.CryptoObject;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.Surface;
import com.android.internal.R;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FaceManager extends BiometricFaceConstants implements BiometricAuthenticator {
    private static final String TAG = "FaceManager";
    private static String mDeviceType = null;
    private final Context mContext;
    private HandlerExecutor mExecutor;
    private Handler mHandler;
    private final IFaceService mService;
    private final IBinder mToken = new Binder();
    private List<FaceSensorPropertiesInternal> mProps = new ArrayList();
    private Bundle mBundle = null;
    private byte[] mFidoRequestData = null;
    private Surface mSurface = null;
    private boolean mNeedtoAuthenticateExt = false;

    public interface GenerateChallengeCallback {
        void onGenerateChallengeResult(int i, int i2, long j);
    }

    public static abstract class GetFeatureCallback {
        public abstract void onCompleted(boolean z, int[] iArr, boolean[] zArr);
    }

    public static abstract class SetFeatureCallback {
        public abstract void onCompleted(boolean z, int i);
    }

    class FaceServiceReceiver extends IFaceServiceReceiver.Stub {
        private final FaceCallback mFaceCallback;

        FaceServiceReceiver(FaceCallback faceCallback) {
            this.mFaceCallback = faceCallback;
        }

        public /* synthetic */ void lambda$onEnrollResult$0(int remaining) {
            this.mFaceCallback.sendEnrollResult(remaining);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollResult(Face face, final int remaining) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onEnrollResult$0(remaining);
                }
            });
        }

        public /* synthetic */ void lambda$onAcquired$1(int acquireInfo, int vendorCode) {
            this.mFaceCallback.sendAcquiredResult(FaceManager.this.mContext, acquireInfo, vendorCode);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAcquired(final int acquireInfo, final int vendorCode) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onAcquired$1(acquireInfo, vendorCode);
                }
            });
        }

        public /* synthetic */ void lambda$onAuthenticationSucceeded$2(Face face, int userId, boolean isStrongBiometric) {
            this.mFaceCallback.sendAuthenticatedSucceeded(face, userId, isStrongBiometric);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationSucceeded(final Face face, final int userId, final boolean isStrongBiometric) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onAuthenticationSucceeded$2(face, userId, isStrongBiometric);
                }
            });
        }

        public /* synthetic */ void lambda$onFaceDetected$3(int sensorId, int userId, boolean isStrongBiometric) {
            this.mFaceCallback.sendFaceDetected(sensorId, userId, isStrongBiometric);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFaceDetected(final int sensorId, final int userId, final boolean isStrongBiometric) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onFaceDetected$3(sensorId, userId, isStrongBiometric);
                }
            });
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFailed() {
            HandlerExecutor handlerExecutor = FaceManager.this.mExecutor;
            final FaceCallback faceCallback = this.mFaceCallback;
            Objects.requireNonNull(faceCallback);
            handlerExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FaceCallback.this.sendAuthenticatedFailed();
                }
            });
        }

        public /* synthetic */ void lambda$onError$4(int error, int vendorCode) {
            this.mFaceCallback.sendErrorResult(FaceManager.this.mContext, error, vendorCode);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onError(final int error, final int vendorCode) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onError$4(error, vendorCode);
                }
            });
        }

        public /* synthetic */ void lambda$onRemoved$5(Face face, int remaining) {
            this.mFaceCallback.sendRemovedResult(face, remaining);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onRemoved(final Face face, final int remaining) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onRemoved$5(face, remaining);
                }
            });
            if (remaining == 0) {
                Settings.Secure.putIntForUser(FaceManager.this.mContext.getContentResolver(), Settings.Secure.FACE_UNLOCK_RE_ENROLL, 0, -2);
            }
        }

        public /* synthetic */ void lambda$onFeatureSet$6(boolean success, int feature) {
            this.mFaceCallback.sendSetFeatureCompleted(success, feature);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureSet(final boolean success, final int feature) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onFeatureSet$6(success, feature);
                }
            });
        }

        public /* synthetic */ void lambda$onFeatureGet$7(boolean success, int[] features, boolean[] featureState) {
            this.mFaceCallback.sendGetFeatureCompleted(success, features, featureState);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureGet(final boolean success, final int[] features, final boolean[] featureState) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onFeatureGet$7(success, features, featureState);
                }
            });
        }

        public /* synthetic */ void lambda$onChallengeGenerated$8(int sensorId, int userId, long challenge) {
            this.mFaceCallback.sendChallengeGenerated(sensorId, userId, challenge);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onChallengeGenerated(final int sensorId, final int userId, final long challenge) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onChallengeGenerated$8(sensorId, userId, challenge);
                }
            });
        }

        public /* synthetic */ void lambda$onAuthenticationFrame$9(FaceAuthenticationFrame frame) {
            this.mFaceCallback.sendAuthenticationFrame(FaceManager.this.mContext, frame);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFrame(final FaceAuthenticationFrame frame) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onAuthenticationFrame$9(frame);
                }
            });
        }

        public /* synthetic */ void lambda$onEnrollmentFrame$10(FaceEnrollFrame frame) {
            this.mFaceCallback.sendEnrollmentFrame(FaceManager.this.mContext, frame);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollmentFrame(final FaceEnrollFrame frame) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onEnrollmentFrame$10(frame);
                }
            });
        }

        public /* synthetic */ void lambda$onSemAuthenticationSucceeded$11(Face face, int userId, boolean isStrongBiometric, byte[] fidoResultData) {
            this.mFaceCallback.sendAuthenticatedSucceeded(face, userId, isStrongBiometric, fidoResultData);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceeded(final Face face, final int userId, final boolean isStrongBiometric, final byte[] fidoResultData) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onSemAuthenticationSucceeded$11(face, userId, isStrongBiometric, fidoResultData);
                }
            });
        }

        public /* synthetic */ void lambda$onSemAuthenticationSucceededWithBundle$12(Face face, int userId, boolean isStrongBiometric, Bundle b) {
            this.mFaceCallback.sendAuthenticatedSucceeded(face, userId, isStrongBiometric, b);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceededWithBundle(final Face face, final int userId, final boolean isStrongBiometric, final Bundle b) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onSemAuthenticationSucceededWithBundle$12(face, userId, isStrongBiometric, b);
                }
            });
        }

        public /* synthetic */ void lambda$onSemImageProcessed$13(byte[] data, int width, int height, int orientation, int imageFormat, Bundle b) {
            this.mFaceCallback.sendImageProcessed(data, width, height, orientation, imageFormat, b);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemImageProcessed(final byte[] data, final int width, final int height, final int orientation, final int imageFormat, final Bundle b) throws RemoteException {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.this.lambda$onSemImageProcessed$13(data, width, height, orientation, imageFormat, b);
                }
            });
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemStatusUpdate(int status, String msg) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.lambda$onSemStatusUpdate$14();
                }
            });
        }

        static /* synthetic */ void lambda$onSemStatusUpdate$14() {
        }
    }

    public FaceManager(Context context, IFaceService service) {
        this.mContext = context;
        this.mService = service;
        if (this.mService == null) {
            Slog.v(TAG, "FaceAuthenticationManagerService was null");
        }
        this.mHandler = context.getMainThreadHandler();
        this.mExecutor = new HandlerExecutor(this.mHandler);
        if (context.checkCallingOrSelfPermission(Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            addAuthenticatorsRegisteredCallback(new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: android.hardware.face.FaceManager.1
                @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
                public void onAllAuthenticatorsRegistered(List<FaceSensorPropertiesInternal> sensors) {
                    FaceManager.this.mProps = sensors;
                }
            });
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
    public void authenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, int userId) {
        authenticate(crypto, cancel, callback, handler, new FaceAuthenticateOptions.Builder().setUserId(userId).build());
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, FaceAuthenticateOptions faceAuthenticateOptions) {
        ?? r5;
        Object obj;
        long semAuthenticate;
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Slog.w(TAG, "authentication already canceled");
            return;
        }
        faceAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        faceAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        try {
            if (this.mService != null) {
                try {
                    FaceCallback faceCallback = new FaceCallback(authenticationCallback, cryptoObject);
                    useHandler(handler);
                    long opId = cryptoObject != null ? cryptoObject.getOpId() : 0L;
                    Trace.beginSection("FaceManager#authenticate");
                    r5 = this.mNeedtoAuthenticateExt;
                    try {
                        if (r5 != 0) {
                            semAuthenticate = this.mService.semAuthenticateExt(this.mToken, opId, new FaceServiceReceiver(faceCallback), faceAuthenticateOptions, this.mSurface, this.mFidoRequestData);
                            r5 = TAG;
                        } else {
                            if (this.mBundle == null) {
                                if (this.mFidoRequestData != null) {
                                    obj = TAG;
                                } else {
                                    IFaceService iFaceService = this.mService;
                                    IBinder iBinder = this.mToken;
                                    FaceServiceReceiver faceServiceReceiver = new FaceServiceReceiver(faceCallback);
                                    r5 = TAG;
                                    semAuthenticate = iFaceService.authenticate(iBinder, opId, faceServiceReceiver, faceAuthenticateOptions);
                                }
                            } else {
                                obj = TAG;
                            }
                            semAuthenticate = this.mService.semAuthenticate(this.mToken, opId, new FaceServiceReceiver(faceCallback), faceAuthenticateOptions, this.mBundle, this.mFidoRequestData);
                            r5 = obj;
                        }
                        if (cancellationSignal != null) {
                            cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(semAuthenticate));
                        }
                    } catch (RemoteException e) {
                        e = e;
                        Slog.w(r5, "Remote exception while authenticating: ", e);
                        authenticationCallback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    r5 = TAG;
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    public void detectFace(CancellationSignal cancel, FaceDetectionCallback callback, FaceAuthenticateOptions options) {
        if (this.mService == null) {
            return;
        }
        if (cancel.isCanceled()) {
            Slog.w(TAG, "Detection already cancelled");
            return;
        }
        options.setOpPackageName(this.mContext.getOpPackageName());
        options.setAttributionTag(this.mContext.getAttributionTag());
        FaceCallback faceCallback = new FaceCallback(callback);
        try {
            long authId = this.mService.detectFace(this.mToken, new FaceServiceReceiver(faceCallback), options);
            cancel.setOnCancelListener(new OnFaceDetectionCancelListener(authId));
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void enroll(int userId, byte[] hardwareAuthToken, CancellationSignal cancel, EnrollmentCallback callback, int[] disabledFeatures) {
        enroll(userId, hardwareAuthToken, cancel, callback, disabledFeatures, null, false, new FaceEnrollOptions.Builder().build());
    }

    public void enroll(int userId, byte[] hardwareAuthToken, CancellationSignal cancel, EnrollmentCallback callback, int[] disabledFeatures, Surface previewSurface, boolean debugConsent, FaceEnrollOptions options) {
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
                try {
                    FaceCallback faceCallback = new FaceCallback(callback);
                    Trace.beginSection("FaceManager#enroll");
                    long enrollId = this.mService.enroll(userId, this.mToken, hardwareAuthToken, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName(), disabledFeatures, previewSurface, debugConsent, options);
                    if (cancel != null) {
                        cancel.setOnCancelListener(new OnEnrollCancelListener(enrollId));
                    }
                } catch (RemoteException e) {
                    Slog.w(TAG, "Remote exception in enroll: ", e);
                    callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    public void enrollRemotely(int userId, byte[] hardwareAuthToken, CancellationSignal cancel, EnrollmentCallback callback, int[] disabledFeatures) {
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancel != null && cancel.isCanceled()) {
            Slog.w(TAG, "enrollRemotely is already canceled.");
            return;
        }
        try {
            if (this.mService != null) {
                try {
                    FaceCallback faceCallback = new FaceCallback(callback);
                    Trace.beginSection("FaceManager#enrollRemotely");
                    long enrolId = this.mService.enrollRemotely(userId, this.mToken, hardwareAuthToken, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName(), disabledFeatures);
                    if (cancel != null) {
                        cancel.setOnCancelListener(new OnEnrollCancelListener(enrolId));
                    }
                } catch (RemoteException e) {
                    Slog.w(TAG, "Remote exception in enrollRemotely: ", e);
                    callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    public void generateChallenge(int sensorId, int userId, GenerateChallengeCallback callback) {
        if (this.mService != null) {
            try {
                FaceCallback faceCallback = new FaceCallback(callback);
                this.mService.generateChallenge(this.mToken, sensorId, userId, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int userId, GenerateChallengeCallback callback) {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.e(TAG, "No sensors");
        } else {
            int sensorId = faceSensorProperties.get(0).sensorId;
            generateChallenge(sensorId, userId, callback);
        }
    }

    public void revokeChallenge(int sensorId, int userId, long challenge) {
        if (this.mService != null) {
            try {
                this.mService.revokeChallenge(this.mToken, sensorId, userId, this.mContext.getOpPackageName(), challenge);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int sensorId, int userId, byte[] hardwareAuthToken) {
        if (this.mService != null) {
            try {
                this.mService.resetLockout(this.mToken, sensorId, userId, hardwareAuthToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setFeature(int userId, int feature, boolean enabled, byte[] hardwareAuthToken, SetFeatureCallback callback) {
        if (this.mService != null) {
            try {
                FaceCallback faceCallback = new FaceCallback(callback);
                this.mService.setFeature(this.mToken, userId, feature, enabled, hardwareAuthToken, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void getFeature(int userId, int feature, GetFeatureCallback callback) {
        if (this.mService != null) {
            try {
                FaceCallback faceCallback = new FaceCallback(callback);
                this.mService.getFeature(this.mToken, userId, feature, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(Face face, int userId, RemovalCallback callback) {
        if (this.mService != null) {
            try {
                FaceCallback faceCallback = new FaceCallback(callback, face);
                this.mService.remove(this.mToken, face.getBiometricId(), userId, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int userId, RemovalCallback callback) {
        if (this.mService != null) {
            try {
                FaceCallback faceCallback = new FaceCallback(callback);
                this.mService.removeAll(this.mToken, userId, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<Face> getEnrolledFaces(int userId) {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.e(TAG, "No sensors");
            return new ArrayList();
        }
        if (this.mService != null) {
            try {
                return this.mService.getEnrolledFaces(faceSensorProperties.get(0).sensorId, userId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<Face> getEnrolledFaces() {
        return getEnrolledFaces(UserHandle.myUserId());
    }

    public boolean hasEnrolledTemplates() {
        return hasEnrolledTemplates(UserHandle.myUserId());
    }

    public boolean hasEnrolledTemplates(int userId) {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.e(TAG, "No sensors");
            return false;
        }
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasEnrolledFaces(faceSensorProperties.get(0).sensorId, userId, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isHardwareDetected() {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.e(TAG, "No sensors");
            return false;
        }
        if (this.mService != null) {
            try {
                return this.mService.isHardwareDetected(faceSensorProperties.get(0).sensorId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "isFaceHardwareDetected(): Service not connected!");
        return false;
    }

    public List<FaceSensorProperties> getSensorProperties() {
        List<FaceSensorProperties> properties = new ArrayList<>();
        List<FaceSensorPropertiesInternal> internalProperties = getSensorPropertiesInternal();
        for (FaceSensorPropertiesInternal internalProp : internalProperties) {
            properties.add(FaceSensorProperties.from(internalProp));
        }
        return properties;
    }

    public List<FaceSensorPropertiesInternal> getSensorPropertiesInternal() {
        try {
            if (this.mProps.isEmpty() && this.mService != null) {
                return this.mService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
            }
            return this.mProps;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return this.mProps;
        }
    }

    public void registerBiometricStateListener(BiometricStateListener listener) {
        try {
            this.mService.registerBiometricStateListener(listener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback callback) {
        if (this.mService != null) {
            try {
                this.mService.addAuthenticatorsRegisteredCallback(callback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "addAuthenticatorsRegisteredCallback(): Service not connected!");
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

    public void addLockoutResetCallback(LockoutResetCallback callback) {
        if (this.mService != null) {
            try {
                PowerManager powerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
                this.mService.addLockoutResetCallback(new AnonymousClass2(powerManager, callback), this.mContext.getOpPackageName());
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* renamed from: android.hardware.face.FaceManager$2, reason: invalid class name */
    class AnonymousClass2 extends IBiometricServiceLockoutResetCallback.Stub {
        final /* synthetic */ LockoutResetCallback val$callback;
        final /* synthetic */ PowerManager val$powerManager;

        AnonymousClass2(PowerManager powerManager, LockoutResetCallback lockoutResetCallback) {
            this.val$powerManager = powerManager;
            this.val$callback = lockoutResetCallback;
        }

        @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
        public void onLockoutReset(final int sensorId, IRemoteCallback serverCallback) throws RemoteException {
            try {
                final PowerManager.WakeLock wakeLock = this.val$powerManager.newWakeLock(1, "faceLockoutResetCallback");
                wakeLock.acquire();
                Handler handler = FaceManager.this.mHandler;
                final LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(new Runnable() { // from class: android.hardware.face.FaceManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FaceManager.AnonymousClass2.lambda$onLockoutReset$0(FaceManager.LockoutResetCallback.this, sensorId, wakeLock);
                    }
                });
            } finally {
                serverCallback.sendResult(null);
            }
        }

        static /* synthetic */ void lambda$onLockoutReset$0(LockoutResetCallback callback, int sensorId, PowerManager.WakeLock wakeLock) {
            try {
                callback.onLockoutReset(sensorId);
            } finally {
                wakeLock.release();
            }
        }
    }

    public void scheduleWatchdog() {
        try {
            this.mService.scheduleWatchdog();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), requestId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelFaceDetect(long requestId) {
        if (this.mService == null) {
            return;
        }
        try {
            this.mService.cancelFaceDetect(this.mToken, this.mContext.getOpPackageName(), requestId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String getErrorString(Context context, int errMsg, int vendorCode) {
        switch (errMsg) {
            case 1:
            case 2:
            case 4:
            case 12:
                if (isTablet()) {
                    return context.getString(R.string.sem_face_error_unable_to_process_tablet);
                }
                return context.getString(R.string.sem_face_error_unable_to_process);
            case 3:
                return context.getString(R.string.sem_face_acquired_non_face);
            case 5:
            case 10:
                return "";
            case 7:
                return context.getString(R.string.sem_face_error_lockout);
            case 8:
                switch (vendorCode) {
                    case 1001:
                        return context.getString(R.string.sem_face_error_DB_corrupted);
                    case 1002:
                        if (isTablet()) {
                            return context.getString(R.string.sem_face_error_unable_to_process_tablet);
                        }
                        return context.getString(R.string.sem_face_error_unable_to_process);
                    case 1003:
                        if (isVTCallOngoing(context)) {
                            return context.getString(R.string.sem_face_error_already_in_use_by_VT);
                        }
                        return context.getString(R.string.sem_face_error_camera_fail);
                    case 1004:
                        if (isVTCallOngoing(context)) {
                            return context.getString(R.string.sem_face_error_already_in_use_by_VT);
                        }
                        return context.getString(R.string.sem_face_error_while_camera_in_use);
                    case 1005:
                        return context.getString(R.string.sem_face_error_ppp_timeout);
                    case 1006:
                        return context.getString(R.string.sem_face_acquired_non_face);
                    case 100001:
                        return context.getString(R.string.sem_face_error_too_dark);
                    case 100002:
                        return context.getString(R.string.sem_face_error_too_dark_to_enroll);
                    case 100003:
                        return context.getString(R.string.sem_face_error_camera_access_off);
                }
            case 9:
                return context.getString(R.string.sem_face_error_lockout_permanent);
            case 11:
                return context.getString(R.string.face_error_not_enrolled);
            case 15:
                return context.getString(R.string.face_error_security_update_required);
            case 16:
                return context.getString(R.string.sem_face_error_DB_corrupted);
        }
        Slog.w(TAG, "Invalid error message: " + errMsg + ", " + vendorCode);
        return context.getString(R.string.face_error_vendor_unknown);
    }

    public static int getMappedAcquiredInfo(int acquireInfo, int vendorCode) {
        switch (acquireInfo) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 3:
                return 2;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return 1;
            case 10:
            case 11:
            case 12:
            case 13:
                return 2;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            default:
                return 0;
            case 22:
                return vendorCode + 1000;
        }
    }

    public static class AuthenticationResult {
        private final CryptoObject mCryptoObject;
        private final Face mFace;
        private final boolean mIsStrongBiometric;
        private final int mUserId;

        public AuthenticationResult(CryptoObject crypto, Face face, int userId, boolean isStrongBiometric) {
            this.mCryptoObject = crypto;
            this.mFace = face;
            this.mUserId = userId;
            this.mIsStrongBiometric = isStrongBiometric;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Face getFace() {
            return this.mFace;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public boolean isStrongBiometric() {
            return this.mIsStrongBiometric;
        }
    }

    public static abstract class AuthenticationCallback extends BiometricAuthenticator.AuthenticationCallback {
        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result, byte[] fidoResultData) {
        }

        public void onAuthenticationSucceededWithBundle(AuthenticationResult result, Bundle b) {
        }

        public void onImageProcessed(int width, int height, int orientation, int imageFormat, Bundle b) {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
        }

        @Override // android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationAcquired(int acquireInfo) {
        }
    }

    public interface FaceDetectionCallback {
        void onFaceDetected(int i, int i2, boolean z);

        default void onDetectionError(int errorMsgId) {
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int errMsgId, CharSequence errString) {
        }

        public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onEnrollmentFrame(int helpCode, CharSequence helpMessage, FaceEnrollCell cell, int stage, float pan, float tilt, float distance) {
            onEnrollmentHelp(helpCode, helpMessage);
        }

        public void onEnrollmentProgress(int remaining) {
        }

        public void onImageProcessed(byte[] data, int width, int height, int orientation, int imageFormat, Bundle b) {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Face face, int errMsgId, CharSequence errString) {
        }

        public void onRemovalSucceeded(Face face, int remaining) {
        }
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset(int sensorId) {
        }
    }

    private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        private OnEnrollCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FaceManager.TAG, "Cancel face enrollment requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelEnrollment(this.mAuthRequestId);
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FaceManager.TAG, "Cancel face authentication requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    private class OnFaceDetectionCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnFaceDetectionCancelListener(long id) {
            this.mAuthRequestId = id;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FaceManager.TAG, "Cancel face detect requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelFaceDetect(this.mAuthRequestId);
        }
    }

    public static String getAuthHelpMessage(Context context, int acquireInfo, int vendorCode) {
        return getHelpMessage(context, acquireInfo, vendorCode);
    }

    public static String getEnrollHelpMessage(Context context, int acquireInfo, int vendorCode) {
        return getHelpMessage(context, acquireInfo, vendorCode);
    }

    public static String getHelpMessage(Context context, int acquireInfo, int vendorCode) {
        switch (acquireInfo) {
            case 0:
                return "";
            case 1:
                return context.getString(R.string.sem_face_acquired_low_quality);
            case 2:
                return context.getString(R.string.sem_face_acquired_low_quality);
            case 3:
                return context.getString(R.string.sem_face_acquired_too_dark);
            case 4:
                if (isTablet()) {
                    return context.getString(R.string.sem_face_acquired_big_face_tablet);
                }
                return context.getString(R.string.sem_face_acquired_big_face);
            case 5:
                if (isTablet()) {
                    return context.getString(R.string.sem_face_acquired_small_face_tablet);
                }
                return context.getString(R.string.sem_face_acquired_small_face);
            case 6:
            case 7:
            case 8:
            case 9:
                return context.getString(R.string.sem_face_acquired_misaligned_face);
            case 10:
                return context.getString(R.string.sem_face_acquired_non_face);
            case 11:
                return context.getString(R.string.sem_face_acquired_non_face);
            case 12:
                return context.getString(R.string.sem_face_acquired_misaligned_face);
            case 13:
                return context.getString(R.string.face_acquired_recalibrate);
            case 14:
                return context.getString(R.string.face_acquired_too_different);
            case 15:
                return context.getString(R.string.face_acquired_too_similar);
            case 16:
                return context.getString(R.string.sem_face_acquired_misaligned_face);
            case 17:
                return context.getString(R.string.sem_face_acquired_misaligned_face);
            case 18:
                return context.getString(R.string.sem_face_acquired_misaligned_face);
            case 19:
                return context.getString(R.string.sem_face_acquired_non_face);
            case 20:
                return "";
            case 21:
                return context.getString(R.string.sem_face_acquired_low_quality);
            case 22:
                switch (vendorCode) {
                    case 1001:
                        return context.getString(R.string.sem_face_acquired_proximity_alert);
                    case 1005:
                        return "";
                    case 1006:
                    case 1007:
                    case 1008:
                    case 1009:
                    case 1011:
                    case 1012:
                    case 1013:
                    case 1014:
                        return context.getString(R.string.sem_face_acquired_misaligned_face);
                    case 1015:
                        return context.getString(R.string.sem_face_acquired_too_dark);
                    case 1017:
                        return context.getString(R.string.sem_face_acquired_non_face);
                    case 100001:
                    case 100002:
                    case 100003:
                    case 100004:
                        return "";
                }
        }
        Slog.w(TAG, "Unknown enrollment acquired message: " + acquireInfo + ", " + vendorCode);
        return null;
    }

    public static String getErrorName(int error) {
        switch (error) {
            case 1:
                return "FACE_ERROR_HW_UNAVAILABLE";
            case 2:
                return "FACE_ERROR_UNABLE_TO_PROCESS";
            case 3:
                return "FACE_ERROR_TIMEOUT";
            case 4:
                return "FACE_ERROR_NO_SPACE";
            case 5:
                return "FACE_ERROR_CANCELED";
            case 6:
                return "FACE_ERROR_UNABLE_TO_REMOVE";
            case 7:
                return "FACE_ERROR_LOCKOUT";
            case 8:
                return "FACE_ERROR_VENDOR";
            case 9:
                return "FACE_ERROR_LOCKOUT_PERMANENT";
            case 10:
                return "FACE_ERROR_USER_CANCELED";
            case 11:
                return "FACE_ERROR_NOT_ENROLLED";
            case 12:
                return "FACE_ERROR_HW_NOT_PRESENT";
            case 13:
                return "FACE_ERROR_NEGATIVE_BUTTON";
            case 14:
                return "BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL";
            case 15:
                return "BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED";
            case 16:
                return "BIOMETRIC_ERROR_RE_ENROLL";
            case 1001:
                return "FACE_ERROR_TEMPLATE_CORRUPTED";
            case 1002:
                return "FACE_ERROR_GET_PREVIEW";
            case 1003:
                return "FACE_ERROR_CAMERA_FAILURE";
            case 1004:
                return "FACE_ERROR_CAMERA_UNAVAILABLE";
            case 1005:
                return "FACE_ERROR_PPP_TIMEOUT";
            case 1006:
                return "FACE_ERROR_ON_MASK";
            case 100001:
                return "FACE_ERROR_TOO_DARK";
            case 100002:
                return "FACE_ERROR_TOO_DARK_TO_ENROLL";
            case 100003:
                return "FACE_ERROR_CAMERA_ACCESS_SETTING_OFF";
            default:
                return "not defined";
        }
    }

    public static String getAcquiredName(int acquired) {
        switch (acquired) {
            case 0:
                return "FACE_ACQUIRED_GOOD";
            case 1:
                return "FACE_ACQUIRED_INSUFFICIENT";
            case 2:
                return "FACE_ACQUIRED_TOO_BRIGHT";
            case 3:
                return "FACE_ACQUIRED_TOO_DARK";
            case 4:
                return "FACE_ACQUIRED_TOO_CLOSE";
            case 5:
                return "FACE_ACQUIRED_TOO_FAR";
            case 6:
                return "FACE_ACQUIRED_TOO_HIGH";
            case 7:
                return "FACE_ACQUIRED_TOO_LOW";
            case 8:
                return "FACE_ACQUIRED_TOO_RIGHT";
            case 9:
                return "FACE_ACQUIRED_TOO_LEFT";
            case 10:
                return "FACE_ACQUIRED_POOR_GAZE";
            case 11:
                return "FACE_ACQUIRED_NOT_DETECTED";
            case 12:
                return "FACE_ACQUIRED_TOO_MUCH_MOTION";
            case 13:
                return "FACE_ACQUIRED_RECALIBRATE";
            case 14:
                return "FACE_ACQUIRED_TOO_DIFFERENT";
            case 15:
                return "FACE_ACQUIRED_TOO_SIMILAR";
            case 16:
                return "FACE_ACQUIRED_PAN_TOO_EXTREME";
            case 17:
                return "FACE_ACQUIRED_TILT_TOO_EXTREME";
            case 18:
                return "FACE_ACQUIRED_ROLL_TOO_EXTREME";
            case 19:
                return "FACE_ACQUIRED_FACE_OBSCURED";
            case 20:
                return "FACE_ACQUIRED_START";
            case 21:
                return "FACE_ACQUIRED_SENSOR_DIRTY";
            case 22:
                return "FACE_ACQUIRED_VENDOR";
            case 1001:
                return "FACE_ACQUIRED_PROXIMITY_ALERT";
            case 1005:
                return "FACE_ACQUIRED_FAKE_FACE";
            case 1006:
                return "FACE_ACQUIRED_MISALIGNED_TOP_LEFT";
            case 1007:
                return "FACE_ACQUIRED_MISALIGNED_TOP";
            case 1008:
                return "FACE_ACQUIRED_MISALIGNED_TOP_RIGHT";
            case 1009:
                return "FACE_ACQUIRED_MISALIGNED_LEFT";
            case 1010:
                return "FACE_ACQUIRED_MISALIGNED_MIDDLE";
            case 1011:
                return "FACE_ACQUIRED_MISALIGNED_RIGHT";
            case 1012:
                return "FACE_ACQUIRED_MISALIGNED_BOTTOM_LEFT";
            case 1013:
                return "FACE_ACQUIRED_MISALIGNED_BOTTOM";
            case 1014:
                return "FACE_ACQUIRED_MISALIGNED_BOTTOM_RIGHT";
            case 1015:
                return "FACE_ACQUIRED_SET_BRIGHTNESS_UP";
            case 1016:
                return "FACE_ACQUIRED_WITH_GLASSES";
            case 1017:
                return "FACE_ACQUIRED_ON_MASK";
            default:
                return "not defined";
        }
    }

    private static boolean isTablet() {
        if (mDeviceType != null && mDeviceType.length() > 0) {
            return mDeviceType.contains(BnRConstants.DEVICETYPE_TABLET);
        }
        mDeviceType = SystemProperties.get("ro.build.characteristics");
        return mDeviceType != null && mDeviceType.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    private static boolean isVTCallOngoing(Context context) {
        TelephonyManager phone = (TelephonyManager) context.getSystemService("phone");
        if (phone != null) {
            boolean isVTCall = phone.semIsVideoCall();
            Log.i(TAG, "isVTCallOngoing = " + isVTCall);
            return isVTCall;
        }
        return false;
    }

    public void semAuthenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, int userId, boolean isKeyguardBypassEnabled, Bundle bundle, byte[] fidoRequestData) {
        this.mBundle = bundle;
        this.mFidoRequestData = fidoRequestData;
        FaceAuthenticateOptions options = new FaceAuthenticateOptions.Builder().setUserId(userId).build();
        authenticate(crypto, cancel, callback, handler, options);
        this.mBundle = null;
        this.mFidoRequestData = null;
    }

    public void semAuthenticateExt(CancellationSignal cancel, AuthenticationCallback callback, Handler handler, int userId, byte[] requestData, Surface surface) {
        this.mSurface = surface;
        this.mFidoRequestData = requestData;
        this.mNeedtoAuthenticateExt = true;
        FaceAuthenticateOptions options = new FaceAuthenticateOptions.Builder().setUserId(userId).build();
        authenticate((CryptoObject) null, cancel, callback, handler, options);
        this.mFidoRequestData = null;
        this.mSurface = null;
        this.mNeedtoAuthenticateExt = false;
    }

    public boolean semIsEnrollSession() {
        if (this.mService != null) {
            try {
                return this.mService.semIsEnrollSession();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void semPauseEnroll() {
        if (this.mService != null) {
            try {
                this.mService.semPauseEnroll();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semResumeEnroll() {
        if (this.mService != null) {
            try {
                this.mService.semResumeEnroll();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semPauseAuth() {
        if (this.mService != null) {
            try {
                this.mService.semPauseAuth();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semResumeAuth() {
        if (this.mService != null) {
            try {
                this.mService.semResumeAuth();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String semGetInfo(int type) {
        if (this.mService != null) {
            try {
                return this.mService.semGetInfo(type);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public int semSetInfo(int type) {
        return 0;
    }

    public boolean semResetAuthenticationTimeout() {
        if (this.mService != null) {
            try {
                return this.mService.semResetAuthenticationTimeout();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void semSessionOpen() {
        if (this.mService != null) {
            try {
                this.mService.semSessionOpen();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSessionClose() {
        if (this.mService != null) {
            try {
                this.mService.semSessionClose();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean semIsSessionClose() {
        if (this.mService != null) {
            try {
                return this.mService.semIsSessionClose();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int semGetSecurityLevel(boolean isKeyguard) {
        if (this.mService != null) {
            try {
                return this.mService.semGetSecurityLevel(isKeyguard);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean semIsFrameworkHandleLockout() {
        if (this.mService != null) {
            try {
                return this.mService.semIsFrameworkHandleLockout();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int semGetRemainingLockoutTime(int userId) {
        if (this.mService != null) {
            try {
                return this.mService.semGetRemainingLockoutTime(userId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public static boolean semIsSupportOnMask() {
        if ("".contains("with_mask=true")) {
            return true;
        }
        "".contains("with_mask=false");
        return false;
    }

    public boolean semShouldRemoveTemplate() {
        if (this.mService != null) {
            try {
                return this.mService.semShouldRemoveTemplate();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }
}
