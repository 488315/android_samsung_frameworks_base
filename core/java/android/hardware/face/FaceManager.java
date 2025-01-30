package android.hardware.face;

import android.Manifest;
import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFaceConstants;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.CryptoObject;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.face.IFaceServiceReceiver;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.CancellationSignal;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.IRemoteCallback;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.PowerManager;
import android.p009os.RemoteException;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.Surface;
import com.android.internal.C4337R;
import com.android.internal.p029os.SomeArgs;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class FaceManager implements BiometricAuthenticator, BiometricFaceConstants {
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_FRAME = 112;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_CHALLENGE_GENERATED = 108;
    private static final int MSG_ENROLLMENT_FRAME = 113;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ERROR = 104;
    private static final int MSG_FACE_DETECTED = 109;
    private static final int MSG_GET_FEATURE_COMPLETED = 106;
    private static final int MSG_REMOVED = 105;
    private static final int MSG_SET_FEATURE_COMPLETED = 107;
    private static final String TAG = "FaceManager";
    private static String mDeviceType = null;
    private AuthenticationCallback mAuthenticationCallback;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private EnrollmentCallback mEnrollmentCallback;
    private FaceDetectionCallback mFaceDetectionCallback;
    private GenerateChallengeCallback mGenerateChallengeCallback;
    private GetFeatureCallback mGetFeatureCallback;
    private Handler mHandler;
    private RemovalCallback mRemovalCallback;
    private Face mRemovalFace;
    private final IFaceService mService;
    private SetFeatureCallback mSetFeatureCallback;
    private final IBinder mToken = new Binder();
    private List<FaceSensorPropertiesInternal> mProps = new ArrayList();
    private final IFaceServiceReceiver mServiceReceiver = new BinderC11471();
    private Bundle mBundle = null;
    private byte[] mFidoRequestData = null;
    private Surface mSurface = null;
    private boolean mNeedtoAuthenticateExt = false;

    public interface FaceDetectionCallback {
        void onFaceDetected(int i, int i2, boolean z);
    }

    public interface GenerateChallengeCallback {
        void onGenerateChallengeResult(int i, int i2, long j);
    }

    public static abstract class GetFeatureCallback {
        public abstract void onCompleted(boolean z, int[] iArr, boolean[] zArr);
    }

    public static abstract class SetFeatureCallback {
        public abstract void onCompleted(boolean z, int i);
    }

    /* renamed from: android.hardware.face.FaceManager$1 */
    class BinderC11471 extends IFaceServiceReceiver.Stub {
        BinderC11471() {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollResult(Face face, int remaining) {
            FaceManager.this.mHandler.obtainMessage(100, remaining, 0, face).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAcquired(int acquireInfo, int vendorCode) {
            FaceManager.this.mHandler.obtainMessage(101, acquireInfo, vendorCode).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationSucceeded(Face face, int i, boolean z) {
            FaceManager.this.mHandler.obtainMessage(102, i, z ? 1 : 0, face).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFaceDetected(int sensorId, int userId, boolean isStrongBiometric) {
            FaceManager.this.mHandler.obtainMessage(109, sensorId, userId, Boolean.valueOf(isStrongBiometric)).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFailed() {
            FaceManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onError(int error, int vendorCode) {
            FaceManager.this.mHandler.obtainMessage(104, error, vendorCode).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onRemoved(Face face, int remaining) {
            FaceManager.this.mHandler.obtainMessage(105, remaining, 0, face).sendToTarget();
            if (remaining == 0) {
                Settings.Secure.putIntForUser(FaceManager.this.mContext.getContentResolver(), Settings.Secure.FACE_UNLOCK_RE_ENROLL, 0, -2);
            }
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureSet(boolean success, int feature) {
            FaceManager.this.mHandler.obtainMessage(107, feature, 0, Boolean.valueOf(success)).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureGet(boolean success, int[] features, boolean[] featureState) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = Boolean.valueOf(success);
            args.arg2 = features;
            args.arg3 = featureState;
            FaceManager.this.mHandler.obtainMessage(106, args).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onChallengeGenerated(int sensorId, int userId, long challenge) {
            FaceManager.this.mHandler.obtainMessage(108, sensorId, userId, Long.valueOf(challenge)).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFrame(FaceAuthenticationFrame frame) {
            FaceManager.this.mHandler.obtainMessage(112, frame).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollmentFrame(FaceEnrollFrame frame) {
            FaceManager.this.mHandler.obtainMessage(113, frame).sendToTarget();
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceeded(final Face face, final int userId, final boolean isStrongBiometric, final byte[] fidoResultData) {
            FaceManager.this.mHandler.post(new Runnable() { // from class: android.hardware.face.FaceManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.BinderC11471.this.lambda$onSemAuthenticationSucceeded$0(face, userId, isStrongBiometric, fidoResultData);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemAuthenticationSucceeded$0(Face face, int userId, boolean isStrongBiometric, byte[] fidoResultData) {
            if (FaceManager.this.mAuthenticationCallback != null) {
                AuthenticationResult result = new AuthenticationResult(FaceManager.this.mCryptoObject, face, userId, isStrongBiometric);
                FaceManager.this.mAuthenticationCallback.onAuthenticationSucceeded(result, fidoResultData);
            }
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceededWithBundle(final Face face, final int userId, final boolean isStrongBiometric, final Bundle b) {
            FaceManager.this.mHandler.post(new Runnable() { // from class: android.hardware.face.FaceManager$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.BinderC11471.this.lambda$onSemAuthenticationSucceededWithBundle$1(face, userId, isStrongBiometric, b);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemAuthenticationSucceededWithBundle$1(Face face, int userId, boolean isStrongBiometric, Bundle b) {
            if (FaceManager.this.mAuthenticationCallback != null) {
                AuthenticationResult result = new AuthenticationResult(null, face, userId, isStrongBiometric);
                FaceManager.this.mAuthenticationCallback.onAuthenticationSucceededWithBundle(result, b);
            }
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemImageProcessed(final byte[] data, final int width, final int height, final int orientation, final int imageFormat, final Bundle b) throws RemoteException {
            FaceManager.this.mHandler.post(new Runnable() { // from class: android.hardware.face.FaceManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.BinderC11471.this.lambda$onSemImageProcessed$2(data, width, height, orientation, imageFormat, b);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemImageProcessed$2(byte[] data, int width, int height, int orientation, int imageFormat, Bundle b) {
            if (FaceManager.this.mEnrollmentCallback != null) {
                FaceManager.this.mEnrollmentCallback.onImageProcessed(data, width, height, orientation, imageFormat, b);
            } else if (FaceManager.this.mAuthenticationCallback != null) {
                FaceManager.this.mAuthenticationCallback.onImageProcessed(width, height, orientation, imageFormat, b);
            }
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemStatusUpdate(int status, String msg) {
            FaceManager.this.mHandler.post(new Runnable() { // from class: android.hardware.face.FaceManager$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.BinderC11471.lambda$onSemStatusUpdate$3();
                }
            });
        }

        static /* synthetic */ void lambda$onSemStatusUpdate$3() {
        }
    }

    public FaceManager(Context context, IFaceService service) {
        this.mContext = context;
        this.mService = service;
        if (service == null) {
            Slog.m119v(TAG, "FaceAuthenticationManagerService was null");
        }
        this.mHandler = new MyHandler(context);
        if (context.checkCallingOrSelfPermission(Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            addAuthenticatorsRegisteredCallback(new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: android.hardware.face.FaceManager.2
                @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
                public void onAllAuthenticatorsRegistered(List<FaceSensorPropertiesInternal> sensors) {
                    FaceManager.this.mProps = sensors;
                }
            });
        }
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    @Deprecated
    public void authenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, int userId) {
        authenticate(crypto, cancel, callback, handler, new FaceAuthenticateOptions.Builder().setUserId(userId).build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, FaceAuthenticateOptions faceAuthenticateOptions) {
        ?? r5;
        long j;
        Object obj;
        long semAuthenticate;
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Slog.m121w(TAG, "authentication already canceled");
            return;
        }
        faceAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        faceAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        if (this.mService != null) {
            try {
                try {
                    useHandler(handler);
                    this.mAuthenticationCallback = authenticationCallback;
                    this.mCryptoObject = cryptoObject;
                    r5 = cryptoObject != null ? cryptoObject.getOpId() : 0;
                    j = r5;
                    Trace.beginSection("FaceManager#authenticate");
                } catch (RemoteException e) {
                    e = e;
                    r5 = TAG;
                }
                try {
                    if (this.mNeedtoAuthenticateExt) {
                        semAuthenticate = this.mService.semAuthenticateExt(this.mToken, j, this.mServiceReceiver, faceAuthenticateOptions, this.mSurface, this.mFidoRequestData);
                        r5 = TAG;
                    } else {
                        Bundle bundle = this.mBundle;
                        if (bundle == null) {
                            if (this.mFidoRequestData != null) {
                                obj = TAG;
                            } else {
                                IFaceService iFaceService = this.mService;
                                IBinder iBinder = this.mToken;
                                IFaceServiceReceiver iFaceServiceReceiver = this.mServiceReceiver;
                                r5 = TAG;
                                semAuthenticate = iFaceService.authenticate(iBinder, j, iFaceServiceReceiver, faceAuthenticateOptions);
                            }
                        } else {
                            obj = TAG;
                        }
                        semAuthenticate = this.mService.semAuthenticate(this.mToken, j, this.mServiceReceiver, faceAuthenticateOptions, bundle, this.mFidoRequestData);
                        r5 = obj;
                    }
                    if (cancellationSignal != null) {
                        cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(semAuthenticate));
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    Slog.m122w(r5, "Remote exception while authenticating: ", e);
                    authenticationCallback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    public void detectFace(CancellationSignal cancel, FaceDetectionCallback callback, FaceAuthenticateOptions options) {
        if (this.mService == null) {
            return;
        }
        if (cancel.isCanceled()) {
            Slog.m121w(TAG, "Detection already cancelled");
            return;
        }
        options.setOpPackageName(this.mContext.getOpPackageName());
        options.setAttributionTag(this.mContext.getAttributionTag());
        this.mFaceDetectionCallback = callback;
        try {
            long authId = this.mService.detectFace(this.mToken, this.mServiceReceiver, options);
            cancel.setOnCancelListener(new OnFaceDetectionCancelListener(authId));
        } catch (RemoteException e) {
            Slog.m122w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void enroll(int userId, byte[] hardwareAuthToken, CancellationSignal cancel, EnrollmentCallback callback, int[] disabledFeatures) {
        enroll(userId, hardwareAuthToken, cancel, callback, disabledFeatures, null, false);
    }

    public void enroll(int userId, byte[] hardwareAuthToken, CancellationSignal cancel, EnrollmentCallback callback, int[] disabledFeatures, Surface previewSurface, boolean debugConsent) {
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancel != null && cancel.isCanceled()) {
            Slog.m121w(TAG, "enrollment already canceled");
            return;
        }
        if (hardwareAuthToken == null) {
            callback.onEnrollmentError(2, getErrorString(this.mContext, 2, 0));
            return;
        }
        try {
            if (this.mService != null) {
                try {
                    this.mEnrollmentCallback = callback;
                    Trace.beginSection("FaceManager#enroll");
                    long enrollId = this.mService.enroll(userId, this.mToken, hardwareAuthToken, this.mServiceReceiver, this.mContext.getOpPackageName(), disabledFeatures, previewSurface, debugConsent);
                    if (cancel != null) {
                        cancel.setOnCancelListener(new OnEnrollCancelListener(enrollId));
                    }
                } catch (RemoteException e) {
                    Slog.m122w(TAG, "Remote exception in enroll: ", e);
                    callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    public void enrollRemotely(int userId, byte[] hardwareAuthToken, CancellationSignal cancel, EnrollmentCallback callback, int[] disabledFeatures) {
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancel != null && cancel.isCanceled()) {
            Slog.m121w(TAG, "enrollRemotely is already canceled.");
            return;
        }
        if (this.mService != null) {
            try {
                try {
                    this.mEnrollmentCallback = callback;
                    Trace.beginSection("FaceManager#enrollRemotely");
                    long enrolId = this.mService.enrollRemotely(userId, this.mToken, hardwareAuthToken, this.mServiceReceiver, this.mContext.getOpPackageName(), disabledFeatures);
                    if (cancel != null) {
                        cancel.setOnCancelListener(new OnEnrollCancelListener(enrolId));
                    }
                } catch (RemoteException e) {
                    Slog.m122w(TAG, "Remote exception in enrollRemotely: ", e);
                    callback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    public void generateChallenge(int sensorId, int userId, GenerateChallengeCallback callback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mGenerateChallengeCallback = callback;
                iFaceService.generateChallenge(this.mToken, sensorId, userId, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int userId, GenerateChallengeCallback callback) {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.m115e(TAG, "No sensors");
        } else {
            int sensorId = faceSensorProperties.get(0).sensorId;
            generateChallenge(sensorId, userId, callback);
        }
    }

    public void revokeChallenge(int sensorId, int userId, long challenge) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.revokeChallenge(this.mToken, sensorId, userId, this.mContext.getOpPackageName(), challenge);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int sensorId, int userId, byte[] hardwareAuthToken) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.resetLockout(this.mToken, sensorId, userId, hardwareAuthToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setFeature(int userId, int feature, boolean enabled, byte[] hardwareAuthToken, SetFeatureCallback callback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mSetFeatureCallback = callback;
                iFaceService.setFeature(this.mToken, userId, feature, enabled, hardwareAuthToken, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void getFeature(int userId, int feature, GetFeatureCallback callback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mGetFeatureCallback = callback;
                iFaceService.getFeature(this.mToken, userId, feature, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(Face face, int userId, RemovalCallback callback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mRemovalCallback = callback;
                this.mRemovalFace = face;
                iFaceService.remove(this.mToken, face.getBiometricId(), userId, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int userId, RemovalCallback callback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mRemovalCallback = callback;
                iFaceService.removeAll(this.mToken, userId, this.mServiceReceiver, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<Face> getEnrolledFaces(int userId) {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.m115e(TAG, "No sensors");
            return new ArrayList();
        }
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.getEnrolledFaces(faceSensorProperties.get(0).sensorId, userId, this.mContext.getOpPackageName());
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
            Slog.m115e(TAG, "No sensors");
            return false;
        }
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.hasEnrolledFaces(faceSensorProperties.get(0).sensorId, userId, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isHardwareDetected() {
        List<FaceSensorPropertiesInternal> faceSensorProperties = getSensorPropertiesInternal();
        if (faceSensorProperties.isEmpty()) {
            Slog.m115e(TAG, "No sensors");
            return false;
        }
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.isHardwareDetected(faceSensorProperties.get(0).sensorId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m121w(TAG, "isFaceHardwareDetected(): Service not connected!");
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
        IFaceService iFaceService;
        try {
            if (this.mProps.isEmpty() && (iFaceService = this.mService) != null) {
                return iFaceService.getSensorPropertiesInternal(this.mContext.getOpPackageName());
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
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.addAuthenticatorsRegisteredCallback(callback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m121w(TAG, "addAuthenticatorsRegisteredCallback(): Service not connected!");
    }

    public int getLockoutModeForUser(int sensorId, int userId) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.getLockoutModeForUser(sensorId, userId);
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
                this.mService.addLockoutResetCallback(new BinderC11493(powerManager, callback), this.mContext.getOpPackageName());
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.m121w(TAG, "addLockoutResetCallback(): Service not connected!");
    }

    /* renamed from: android.hardware.face.FaceManager$3 */
    class BinderC11493 extends IBiometricServiceLockoutResetCallback.Stub {
        final /* synthetic */ LockoutResetCallback val$callback;
        final /* synthetic */ PowerManager val$powerManager;

        BinderC11493(PowerManager powerManager, LockoutResetCallback lockoutResetCallback) {
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
                handler.post(new Runnable() { // from class: android.hardware.face.FaceManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FaceManager.BinderC11493.lambda$onLockoutReset$0(FaceManager.LockoutResetCallback.this, sensorId, wakeLock);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment(long requestId) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.cancelEnrollment(this.mToken, requestId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long requestId) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), requestId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelFaceDetect(long requestId) {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return;
        }
        try {
            iFaceService.cancelFaceDetect(this.mToken, this.mContext.getOpPackageName(), requestId);
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
                    return context.getString(C4337R.string.sem_face_error_unable_to_process_tablet);
                }
                return context.getString(C4337R.string.sem_face_error_unable_to_process);
            case 3:
                return context.getString(C4337R.string.sem_face_acquired_non_face);
            case 5:
            case 10:
                return "";
            case 7:
                return context.getString(C4337R.string.face_error_lockout);
            case 8:
                switch (vendorCode) {
                    case 1001:
                        return context.getString(C4337R.string.sem_face_error_DB_corrupted);
                    case 1002:
                        if (isTablet()) {
                            return context.getString(C4337R.string.sem_face_error_unable_to_process_tablet);
                        }
                        return context.getString(C4337R.string.sem_face_error_unable_to_process);
                    case 1003:
                        if (isVTCallOngoing(context)) {
                            return context.getString(C4337R.string.sem_face_error_already_in_use_by_VT);
                        }
                        return context.getString(C4337R.string.sem_face_error_camera_fail);
                    case 1004:
                        if (isVTCallOngoing(context)) {
                            return context.getString(C4337R.string.sem_face_error_already_in_use_by_VT);
                        }
                        return context.getString(C4337R.string.sem_face_error_while_camera_in_use);
                    case 1005:
                        return context.getString(C4337R.string.sem_face_error_ppp_timeout);
                    case 1006:
                        return context.getString(C4337R.string.sem_face_acquired_non_face);
                    case 100001:
                        return context.getString(C4337R.string.sem_face_error_too_dark);
                    case 100002:
                        return context.getString(C4337R.string.sem_face_error_too_dark_to_enroll);
                    case 100003:
                        return context.getString(C4337R.string.sem_face_error_camera_access_off);
                }
            case 9:
                return context.getString(C4337R.string.face_error_lockout_permanent);
            case 11:
                return context.getString(C4337R.string.face_error_not_enrolled);
            case 15:
                return context.getString(C4337R.string.face_error_security_update_required);
            case 16:
                return context.getString(C4337R.string.sem_face_error_DB_corrupted);
        }
        Slog.m121w(TAG, "Invalid error message: " + errMsg + ", " + vendorCode);
        return context.getString(C4337R.string.face_error_vendor_unknown);
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
            Slog.m113d(FaceManager.TAG, "Cancel face enrollment requested for: " + this.mAuthRequestId);
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
            Slog.m113d(FaceManager.TAG, "Cancel face authentication requested for: " + this.mAuthRequestId);
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
            Slog.m113d(FaceManager.TAG, "Cancel face detect requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelFaceDetect(this.mAuthRequestId);
        }
    }

    private class MyHandler extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            Log.m98i(FaceManager.TAG, "handleMessage = " + msg.what + ", " + msg.arg1 + ", " + msg.arg2);
            Trace.beginSection("FaceManager#handleMessage: " + Integer.toString(msg.what));
            switch (msg.what) {
                case 100:
                    FaceManager.this.sendEnrollResult((Face) msg.obj, msg.arg1);
                    break;
                case 101:
                    FaceManager.this.sendAcquiredResult(msg.arg1, msg.arg2);
                    break;
                case 102:
                    FaceManager.this.sendAuthenticatedSucceeded((Face) msg.obj, msg.arg1, msg.arg2 == 1);
                    break;
                case 103:
                    FaceManager.this.sendAuthenticatedFailed();
                    break;
                case 104:
                    FaceManager.this.sendErrorResult(msg.arg1, msg.arg2);
                    break;
                case 105:
                    FaceManager.this.sendRemovedResult((Face) msg.obj, msg.arg1);
                    break;
                case 106:
                    SomeArgs args = (SomeArgs) msg.obj;
                    FaceManager.this.sendGetFeatureCompleted(((Boolean) args.arg1).booleanValue(), (int[]) args.arg2, (boolean[]) args.arg3);
                    args.recycle();
                    break;
                case 107:
                    FaceManager.this.sendSetFeatureCompleted(((Boolean) msg.obj).booleanValue(), msg.arg1);
                    break;
                case 108:
                    FaceManager.this.sendChallengeGenerated(msg.arg1, msg.arg2, ((Long) msg.obj).longValue());
                    break;
                case 109:
                    FaceManager.this.sendFaceDetected(msg.arg1, msg.arg2, ((Boolean) msg.obj).booleanValue());
                    break;
                case 110:
                case 111:
                default:
                    Slog.m121w(FaceManager.TAG, "Unknown message: " + msg.what);
                    break;
                case 112:
                    FaceManager.this.sendAuthenticationFrame((FaceAuthenticationFrame) msg.obj);
                    break;
                case 113:
                    FaceManager.this.sendEnrollmentFrame((FaceEnrollFrame) msg.obj);
                    break;
            }
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSetFeatureCompleted(boolean success, int feature) {
        SetFeatureCallback setFeatureCallback = this.mSetFeatureCallback;
        if (setFeatureCallback == null) {
            return;
        }
        setFeatureCallback.onCompleted(success, feature);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendGetFeatureCompleted(boolean success, int[] features, boolean[] featureState) {
        GetFeatureCallback getFeatureCallback = this.mGetFeatureCallback;
        if (getFeatureCallback == null) {
            return;
        }
        getFeatureCallback.onCompleted(success, features, featureState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChallengeGenerated(int sensorId, int userId, long challenge) {
        GenerateChallengeCallback generateChallengeCallback = this.mGenerateChallengeCallback;
        if (generateChallengeCallback == null) {
            return;
        }
        generateChallengeCallback.onGenerateChallengeResult(sensorId, userId, challenge);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFaceDetected(int sensorId, int userId, boolean isStrongBiometric) {
        FaceDetectionCallback faceDetectionCallback = this.mFaceDetectionCallback;
        if (faceDetectionCallback == null) {
            Slog.m115e(TAG, "sendFaceDetected, callback null");
        } else {
            faceDetectionCallback.onFaceDetected(sensorId, userId, isStrongBiometric);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemovedResult(Face face, int remaining) {
        RemovalCallback removalCallback = this.mRemovalCallback;
        if (removalCallback == null) {
            return;
        }
        removalCallback.onRemovalSucceeded(face, remaining);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(int errMsgId, int vendorCode) {
        int clientErrMsgId = errMsgId == 8 ? vendorCode : errMsgId;
        EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onEnrollmentError(clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
            return;
        }
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationError(clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
            return;
        }
        RemovalCallback removalCallback = this.mRemovalCallback;
        if (removalCallback != null) {
            removalCallback.onRemovalError(this.mRemovalFace, clientErrMsgId, getErrorString(this.mContext, errMsgId, vendorCode));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnrollResult(Face face, int remaining) {
        EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onEnrollmentProgress(remaining);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(Face face, int userId, boolean isStrongBiometric) {
        if (this.mAuthenticationCallback != null) {
            AuthenticationResult result = new AuthenticationResult(this.mCryptoObject, face, userId, isStrongBiometric);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedFailed() {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAcquiredResult(int acquireInfo, int vendorCode) {
        if (this.mAuthenticationCallback != null) {
            FaceAuthenticationFrame frame = new FaceAuthenticationFrame(new FaceDataFrame(acquireInfo, vendorCode));
            sendAuthenticationFrame(frame);
        } else if (this.mEnrollmentCallback != null) {
            FaceEnrollFrame frame2 = new FaceEnrollFrame(null, 0, new FaceDataFrame(acquireInfo, vendorCode));
            sendEnrollmentFrame(frame2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticationFrame(FaceAuthenticationFrame frame) {
        if (frame == null) {
            Slog.m121w(TAG, "Received null authentication frame");
            return;
        }
        if (this.mAuthenticationCallback != null) {
            int acquireInfo = frame.getData().getAcquiredInfo();
            int vendorCode = frame.getData().getVendorCode();
            int helpCode = getHelpCode(acquireInfo, vendorCode);
            String helpMessage = getAuthHelpMessage(this.mContext, acquireInfo, vendorCode);
            this.mAuthenticationCallback.onAuthenticationAcquired(acquireInfo == 22 ? vendorCode : acquireInfo);
            if (helpMessage != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(helpCode, helpMessage);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnrollmentFrame(FaceEnrollFrame frame) {
        if (frame == null) {
            Slog.m121w(TAG, "Received null enrollment frame");
            return;
        }
        if (this.mEnrollmentCallback != null) {
            FaceDataFrame data = frame.getData();
            int acquireInfo = data.getAcquiredInfo();
            int vendorCode = data.getVendorCode();
            int helpCode = getHelpCode(acquireInfo, vendorCode);
            String helpMessage = getEnrollHelpMessage(this.mContext, acquireInfo, vendorCode);
            this.mEnrollmentCallback.onEnrollmentFrame(helpCode, helpMessage, frame.getCell(), frame.getStage(), data.getPan(), data.getTilt(), data.getDistance());
        }
    }

    private static int getHelpCode(int acquireInfo, int vendorCode) {
        if (acquireInfo == 22) {
            return vendorCode;
        }
        return acquireInfo;
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
                return context.getString(C4337R.string.sem_face_acquired_low_quality);
            case 2:
                return context.getString(C4337R.string.sem_face_acquired_low_quality);
            case 3:
                return context.getString(C4337R.string.sem_face_acquired_too_dark);
            case 4:
                if (isTablet()) {
                    return context.getString(C4337R.string.sem_face_acquired_big_face_tablet);
                }
                return context.getString(C4337R.string.sem_face_acquired_big_face);
            case 5:
                if (isTablet()) {
                    return context.getString(C4337R.string.sem_face_acquired_small_face_tablet);
                }
                return context.getString(C4337R.string.sem_face_acquired_small_face);
            case 6:
            case 7:
            case 8:
            case 9:
                return context.getString(C4337R.string.sem_face_acquired_misaligned_face);
            case 10:
                return context.getString(C4337R.string.sem_face_acquired_non_face);
            case 11:
                return context.getString(C4337R.string.sem_face_acquired_non_face);
            case 12:
                return context.getString(C4337R.string.sem_face_acquired_misaligned_face);
            case 13:
                return context.getString(C4337R.string.face_acquired_recalibrate);
            case 14:
                return context.getString(C4337R.string.face_acquired_too_different);
            case 15:
                return context.getString(C4337R.string.face_acquired_too_similar);
            case 16:
                return context.getString(C4337R.string.sem_face_acquired_misaligned_face);
            case 17:
                return context.getString(C4337R.string.sem_face_acquired_misaligned_face);
            case 18:
                return context.getString(C4337R.string.sem_face_acquired_misaligned_face);
            case 19:
                return context.getString(C4337R.string.sem_face_acquired_non_face);
            case 20:
                return "";
            case 21:
                return context.getString(C4337R.string.sem_face_acquired_low_quality);
            case 22:
                switch (vendorCode) {
                    case 1001:
                        return context.getString(C4337R.string.sem_face_acquired_proximity_alert);
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
                        return context.getString(C4337R.string.sem_face_acquired_misaligned_face);
                    case 1015:
                        return context.getString(C4337R.string.sem_face_acquired_too_dark);
                    case 1017:
                        return context.getString(C4337R.string.sem_face_acquired_non_face);
                    case 100001:
                    case 100002:
                    case 100003:
                    case 100004:
                        return "";
                }
        }
        Slog.m121w(TAG, "Unknown enrollment acquired message: " + acquireInfo + ", " + vendorCode);
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
        String str = mDeviceType;
        if (str != null && str.length() > 0) {
            return mDeviceType.contains(BnRConstants.DEVICETYPE_TABLET);
        }
        String str2 = SystemProperties.get("ro.build.characteristics");
        mDeviceType = str2;
        return str2 != null && str2.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    private static boolean isVTCallOngoing(Context context) {
        TelephonyManager phone = (TelephonyManager) context.getSystemService("phone");
        if (phone != null) {
            boolean isVTCall = phone.semIsVideoCall();
            Log.m98i(TAG, "isVTCallOngoing = " + isVTCall);
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
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semIsEnrollSession();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void semPauseEnroll() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.semPauseEnroll();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semResumeEnroll() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.semResumeEnroll();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semPauseAuth() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.semPauseAuth();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semResumeAuth() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.semResumeAuth();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String semGetInfo(int type) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semGetInfo(type);
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
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semResetAuthenticationTimeout();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void semSessionOpen() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.semSessionOpen();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void semSessionClose() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.semSessionClose();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean semIsSessionClose() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semIsSessionClose();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int semGetSecurityLevel(boolean isKeyguard) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semGetSecurityLevel(isKeyguard);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean semIsFrameworkHandleLockout() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semIsFrameworkHandleLockout();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int semGetRemainingLockoutTime(int userId) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semGetRemainingLockoutTime(userId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public static boolean semIsSupportOnMask() {
        if ("landscape".contains("with_mask=true")) {
            return true;
        }
        "landscape".contains("with_mask=false");
        return false;
    }

    public boolean semShouldRemoveTemplate() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.semShouldRemoveTemplate();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }
}
