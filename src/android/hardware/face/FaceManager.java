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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class FaceManager extends BiometricFaceConstants implements BiometricAuthenticator {
    private static final String TAG = "FaceManager";
    private static String mDeviceType;
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

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult, byte[] bArr) {
        }

        public void onAuthenticationSucceededWithBundle(AuthenticationResult authenticationResult, Bundle bundle) {
        }

        public void onImageProcessed(int i, int i2, int i3, int i4, Bundle bundle) {
        }
    }

    public interface FaceDetectionCallback {
        default void onDetectionError(int i) {
        }

        void onFaceDetected(int i, int i2, boolean z);
    }

    public interface GenerateChallengeCallback {
        void onGenerateChallengeResult(int i, int i2, long j);
    }

    public static abstract class GetFeatureCallback {
        public abstract void onCompleted(boolean z, int[] iArr, boolean[] zArr);
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset(int i) {
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Face face, int i, CharSequence charSequence) {
        }

        public void onRemovalSucceeded(Face face, int i) {
        }
    }

    public static abstract class SetFeatureCallback {
        public abstract void onCompleted(boolean z, int i);
    }

    public static int getMappedAcquiredInfo(int i, int i2) {
        if (i == 22) {
            return i2 + 1000;
        }
        switch (i) {
        }
        return 2;
    }

    public static boolean semIsSupportOnMask() {
        return false;
    }

    public int semSetInfo(int i) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class FaceServiceReceiver extends IFaceServiceReceiver.Stub {
        private final FaceCallback mFaceCallback;

        static /* synthetic */ void lambda$onSemStatusUpdate$14() {
        }

        FaceServiceReceiver(FaceCallback faceCallback) {
            this.mFaceCallback = faceCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollResult$0(int i) {
            this.mFaceCallback.sendEnrollResult(i);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollResult(Face face, final int i) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onEnrollResult$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$1(int i, int i2) {
            this.mFaceCallback.sendAcquiredResult(FaceManager.this.mContext, i, i2);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAcquired(final int i, final int i2) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAcquired$1(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$2(Face face, int i, boolean z) {
            this.mFaceCallback.sendAuthenticatedSucceeded(face, i, z);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationSucceeded(final Face face, final int i, final boolean z) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAuthenticationSucceeded$2(face, i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFaceDetected$3(int i, int i2, boolean z) {
            this.mFaceCallback.sendFaceDetected(i, i2, z);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFaceDetected(final int i, final int i2, final boolean z) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFaceDetected$3(i, i2, z);
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
                    faceCallback.sendAuthenticatedFailed();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$4(int i, int i2) {
            this.mFaceCallback.sendErrorResult(FaceManager.this.mContext, i, i2);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onError(final int i, final int i2) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onError$4(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRemoved$5(Face face, int i) {
            this.mFaceCallback.sendRemovedResult(face, i);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onRemoved(final Face face, final int i) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRemoved$5(face, i);
                }
            });
            if (i == 0) {
                Settings.Secure.putIntForUser(FaceManager.this.mContext.getContentResolver(), Settings.Secure.FACE_UNLOCK_RE_ENROLL, 0, -2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFeatureSet$6(boolean z, int i) {
            this.mFaceCallback.sendSetFeatureCompleted(z, i);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureSet(final boolean z, final int i) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFeatureSet$6(z, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFeatureGet$7(boolean z, int[] iArr, boolean[] zArr) {
            this.mFaceCallback.sendGetFeatureCompleted(z, iArr, zArr);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureGet(final boolean z, final int[] iArr, final boolean[] zArr) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFeatureGet$7(z, iArr, zArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChallengeGenerated$8(int i, int i2, long j) {
            this.mFaceCallback.sendChallengeGenerated(i, i2, j);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onChallengeGenerated(final int i, final int i2, final long j) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onChallengeGenerated$8(i, i2, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationFrame$9(FaceAuthenticationFrame faceAuthenticationFrame) {
            this.mFaceCallback.sendAuthenticationFrame(FaceManager.this.mContext, faceAuthenticationFrame);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFrame(final FaceAuthenticationFrame faceAuthenticationFrame) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAuthenticationFrame$9(faceAuthenticationFrame);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollmentFrame$10(FaceEnrollFrame faceEnrollFrame) {
            this.mFaceCallback.sendEnrollmentFrame(FaceManager.this.mContext, faceEnrollFrame);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollmentFrame(final FaceEnrollFrame faceEnrollFrame) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onEnrollmentFrame$10(faceEnrollFrame);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemAuthenticationSucceeded$11(Face face, int i, boolean z, byte[] bArr) {
            this.mFaceCallback.sendAuthenticatedSucceeded(face, i, z, bArr);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceeded(final Face face, final int i, final boolean z, final byte[] bArr) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSemAuthenticationSucceeded$11(face, i, z, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemAuthenticationSucceededWithBundle$12(Face face, int i, boolean z, Bundle bundle) {
            this.mFaceCallback.sendAuthenticatedSucceeded(face, i, z, bundle);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceededWithBundle(final Face face, final int i, final boolean z, final Bundle bundle) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSemAuthenticationSucceededWithBundle$12(face, i, z, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemImageProcessed$13(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
            this.mFaceCallback.sendImageProcessed(bArr, i, i2, i3, i4, bundle);
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemImageProcessed(final byte[] bArr, final int i, final int i2, final int i3, final int i4, final Bundle bundle) throws RemoteException {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSemImageProcessed$13(bArr, i, i2, i3, i4, bundle);
                }
            });
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemStatusUpdate(int i, String str) {
            FaceManager.this.mExecutor.execute(new Runnable() { // from class: android.hardware.face.FaceManager$FaceServiceReceiver$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    FaceManager.FaceServiceReceiver.lambda$onSemStatusUpdate$14();
                }
            });
        }
    }

    public FaceManager(Context context, IFaceService iFaceService) {
        this.mContext = context;
        this.mService = iFaceService;
        if (iFaceService == null) {
            Slog.v(TAG, "FaceAuthenticationManagerService was null");
        }
        this.mHandler = context.getMainThreadHandler();
        this.mExecutor = new HandlerExecutor(this.mHandler);
        if (context.checkCallingOrSelfPermission(Manifest.permission.USE_BIOMETRIC_INTERNAL) == 0) {
            addAuthenticatorsRegisteredCallback(new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: android.hardware.face.FaceManager.1
                @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
                public void onAllAuthenticatorsRegistered(List<FaceSensorPropertiesInternal> list) {
                    FaceManager.this.mProps = list;
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
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, int i) {
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, new FaceAuthenticateOptions.Builder().setUserId(i).build());
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, FaceAuthenticateOptions faceAuthenticateOptions) {
        long jSemAuthenticate;
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
                FaceCallback faceCallback = new FaceCallback(authenticationCallback, cryptoObject);
                useHandler(handler);
                long opId = cryptoObject != null ? cryptoObject.getOpId() : 0L;
                Trace.beginSection("FaceManager#authenticate");
                if (this.mNeedtoAuthenticateExt) {
                    jSemAuthenticate = this.mService.semAuthenticateExt(this.mToken, opId, new FaceServiceReceiver(faceCallback), faceAuthenticateOptions, this.mSurface, this.mFidoRequestData);
                } else if (this.mBundle != null || this.mFidoRequestData != null) {
                    jSemAuthenticate = this.mService.semAuthenticate(this.mToken, opId, new FaceServiceReceiver(faceCallback), faceAuthenticateOptions, this.mBundle, this.mFidoRequestData);
                } else {
                    jSemAuthenticate = this.mService.authenticate(this.mToken, opId, new FaceServiceReceiver(faceCallback), faceAuthenticateOptions);
                }
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(jSemAuthenticate));
                }
            }
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception while authenticating: ", e);
            authenticationCallback.onAuthenticationError(1, getErrorString(this.mContext, 1, 0));
        } finally {
            Trace.endSection();
        }
    }

    public void detectFace(CancellationSignal cancellationSignal, FaceDetectionCallback faceDetectionCallback, FaceAuthenticateOptions faceAuthenticateOptions) {
        if (this.mService == null) {
            return;
        }
        if (cancellationSignal.isCanceled()) {
            Slog.w(TAG, "Detection already cancelled");
            return;
        }
        faceAuthenticateOptions.setOpPackageName(this.mContext.getOpPackageName());
        faceAuthenticateOptions.setAttributionTag(this.mContext.getAttributionTag());
        try {
            cancellationSignal.setOnCancelListener(new OnFaceDetectionCancelListener(this.mService.detectFace(this.mToken, new FaceServiceReceiver(new FaceCallback(faceDetectionCallback)), faceAuthenticateOptions)));
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception when requesting finger detect", e);
        }
    }

    public void enroll(int i, byte[] bArr, CancellationSignal cancellationSignal, EnrollmentCallback enrollmentCallback, int[] iArr) {
        enroll(i, bArr, cancellationSignal, enrollmentCallback, iArr, null, false, new FaceEnrollOptions.Builder().build());
    }

    public void enroll(int i, byte[] bArr, CancellationSignal cancellationSignal, EnrollmentCallback enrollmentCallback, int[] iArr, Surface surface, boolean z, FaceEnrollOptions faceEnrollOptions) {
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
                FaceCallback faceCallback = new FaceCallback(enrollmentCallback);
                Trace.beginSection("FaceManager#enroll");
                long jEnroll = this.mService.enroll(i, this.mToken, bArr, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName(), iArr, surface, z, faceEnrollOptions);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new OnEnrollCancelListener(jEnroll));
                }
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception in enroll: ", e);
                enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
            } finally {
                Trace.endSection();
            }
        }
    }

    public void enrollRemotely(int i, byte[] bArr, CancellationSignal cancellationSignal, EnrollmentCallback enrollmentCallback, int[] iArr) {
        if (enrollmentCallback == null) {
            throw new IllegalArgumentException("Must supply an enrollment callback");
        }
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            Slog.w(TAG, "enrollRemotely is already canceled.");
            return;
        }
        try {
            if (this.mService != null) {
                FaceCallback faceCallback = new FaceCallback(enrollmentCallback);
                Trace.beginSection("FaceManager#enrollRemotely");
                long jEnrollRemotely = this.mService.enrollRemotely(i, this.mToken, bArr, new FaceServiceReceiver(faceCallback), this.mContext.getOpPackageName(), iArr);
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(new OnEnrollCancelListener(jEnrollRemotely));
                }
            }
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception in enrollRemotely: ", e);
            enrollmentCallback.onEnrollmentError(1, getErrorString(this.mContext, 1, 0));
        } finally {
            Trace.endSection();
        }
    }

    public void generateChallenge(int i, int i2, GenerateChallengeCallback generateChallengeCallback) {
        if (this.mService != null) {
            try {
                this.mService.generateChallenge(this.mToken, i, i2, new FaceServiceReceiver(new FaceCallback(generateChallengeCallback)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void generateChallenge(int i, GenerateChallengeCallback generateChallengeCallback) {
        List<FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            Slog.e(TAG, "No sensors");
        } else {
            generateChallenge(sensorPropertiesInternal.get(0).sensorId, i, generateChallengeCallback);
        }
    }

    public void revokeChallenge(int i, int i2, long j) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.revokeChallenge(this.mToken, i, i2, this.mContext.getOpPackageName(), j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int i, int i2, byte[] bArr) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.resetLockout(this.mToken, i, i2, bArr, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setFeature(int i, int i2, boolean z, byte[] bArr, SetFeatureCallback setFeatureCallback) {
        if (this.mService != null) {
            try {
                this.mService.setFeature(this.mToken, i, i2, z, bArr, new FaceServiceReceiver(new FaceCallback(setFeatureCallback)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void getFeature(int i, int i2, GetFeatureCallback getFeatureCallback) {
        if (this.mService != null) {
            try {
                this.mService.getFeature(this.mToken, i, i2, new FaceServiceReceiver(new FaceCallback(getFeatureCallback)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void remove(Face face, int i, RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mService.remove(this.mToken, face.getBiometricId(), i, new FaceServiceReceiver(new FaceCallback(removalCallback, face)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAll(int i, RemovalCallback removalCallback) {
        if (this.mService != null) {
            try {
                this.mService.removeAll(this.mToken, i, new FaceServiceReceiver(new FaceCallback(removalCallback)), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<Face> getEnrolledFaces(int i) {
        List<FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            Slog.e(TAG, "No sensors");
            return new ArrayList();
        }
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return null;
        }
        try {
            return iFaceService.getEnrolledFaces(sensorPropertiesInternal.get(0).sensorId, i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Face> getEnrolledFaces() {
        return getEnrolledFaces(UserHandle.myUserId());
    }

    public boolean hasEnrolledTemplates() {
        return hasEnrolledTemplates(UserHandle.myUserId());
    }

    public boolean hasEnrolledTemplates(int i) {
        List<FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            Slog.e(TAG, "No sensors");
            return false;
        }
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.hasEnrolledFaces(sensorPropertiesInternal.get(0).sensorId, i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isHardwareDetected() {
        List<FaceSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal();
        if (sensorPropertiesInternal.isEmpty()) {
            Slog.e(TAG, "No sensors");
            return false;
        }
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                return iFaceService.isHardwareDetected(sensorPropertiesInternal.get(0).sensorId, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "isFaceHardwareDetected(): Service not connected!");
        return false;
    }

    public List<FaceSensorProperties> getSensorProperties() {
        ArrayList arrayList = new ArrayList();
        Iterator<FaceSensorPropertiesInternal> it = getSensorPropertiesInternal().iterator();
        while (it.hasNext()) {
            arrayList.add(FaceSensorProperties.from(it.next()));
        }
        return arrayList;
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

    public void registerBiometricStateListener(BiometricStateListener biometricStateListener) {
        try {
            this.mService.registerBiometricStateListener(biometricStateListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addAuthenticatorsRegisteredCallback(IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.addAuthenticatorsRegisteredCallback(iFaceAuthenticatorsRegisteredCallback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "addAuthenticatorsRegisteredCallback(): Service not connected!");
    }

    public int getLockoutModeForUser(int i, int i2) {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return 0;
        }
        try {
            return iFaceService.getLockoutModeForUser(i, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    public void addLockoutResetCallback(LockoutResetCallback lockoutResetCallback) {
        if (this.mService != null) {
            try {
                this.mService.addLockoutResetCallback(new AnonymousClass2((PowerManager) this.mContext.getSystemService(PowerManager.class), lockoutResetCallback), this.mContext.getOpPackageName());
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
        public void onLockoutReset(final int i, IRemoteCallback iRemoteCallback) throws RemoteException {
            try {
                final PowerManager.WakeLock wakeLockNewWakeLock = this.val$powerManager.newWakeLock(1, "faceLockoutResetCallback");
                wakeLockNewWakeLock.acquire();
                Handler handler = FaceManager.this.mHandler;
                final LockoutResetCallback lockoutResetCallback = this.val$callback;
                handler.post(new Runnable() { // from class: android.hardware.face.FaceManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FaceManager.AnonymousClass2.lambda$onLockoutReset$0(lockoutResetCallback, i, wakeLockNewWakeLock);
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

    public void scheduleWatchdog() {
        try {
            this.mService.scheduleWatchdog();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEnrollment(long j) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.cancelEnrollment(this.mToken, j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAuthentication(long j) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), j);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelFaceDetect(long j) {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return;
        }
        try {
            iFaceService.cancelFaceDetect(this.mToken, this.mContext.getOpPackageName(), j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String getErrorString(Context context, int i, int i2) {
        switch (i) {
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
                switch (i2) {
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
                    default:
                        switch (i2) {
                            case 100001:
                                return context.getString(R.string.sem_face_error_too_dark);
                            case 100002:
                                return context.getString(R.string.sem_face_error_too_dark_to_enroll);
                            case 100003:
                                return context.getString(R.string.sem_face_error_camera_access_off);
                        }
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
        Slog.w(TAG, "Invalid error message: " + i + ", " + i2);
        return context.getString(R.string.face_error_vendor_unknown);
    }

    public static class AuthenticationResult {
        private final CryptoObject mCryptoObject;
        private final Face mFace;
        private final boolean mIsStrongBiometric;
        private final int mUserId;

        public AuthenticationResult(CryptoObject cryptoObject, Face face, int i, boolean z) {
            this.mCryptoObject = cryptoObject;
            this.mFace = face;
            this.mUserId = i;
            this.mIsStrongBiometric = z;
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

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int i, CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int i, CharSequence charSequence) {
        }

        public void onEnrollmentProgress(int i) {
        }

        public void onImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
        }

        public void onEnrollmentFrame(int i, CharSequence charSequence, FaceEnrollCell faceEnrollCell, int i2, float f, float f2, float f3) {
            onEnrollmentHelp(i, charSequence);
        }
    }

    private class OnEnrollCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        private OnEnrollCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FaceManager.TAG, "Cancel face enrollment requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelEnrollment(this.mAuthRequestId);
        }
    }

    private class OnAuthenticationCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnAuthenticationCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FaceManager.TAG, "Cancel face authentication requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelAuthentication(this.mAuthRequestId);
        }
    }

    private class OnFaceDetectionCancelListener implements CancellationSignal.OnCancelListener {
        private final long mAuthRequestId;

        OnFaceDetectionCancelListener(long j) {
            this.mAuthRequestId = j;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            Slog.d(FaceManager.TAG, "Cancel face detect requested for: " + this.mAuthRequestId);
            FaceManager.this.cancelFaceDetect(this.mAuthRequestId);
        }
    }

    public static String getAuthHelpMessage(Context context, int i, int i2) {
        return getHelpMessage(context, i, i2);
    }

    public static String getEnrollHelpMessage(Context context, int i, int i2) {
        return getHelpMessage(context, i, i2);
    }

    public static String getHelpMessage(Context context, int i, int i2) {
        switch (i) {
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
                if (i2 == 1001) {
                    return context.getString(R.string.sem_face_acquired_proximity_alert);
                }
                if (i2 != 1017) {
                    switch (i2) {
                        case 1005:
                            return "";
                        default:
                            switch (i2) {
                                case 1011:
                                case 1012:
                                case 1013:
                                case 1014:
                                    break;
                                case 1015:
                                    return context.getString(R.string.sem_face_acquired_too_dark);
                                default:
                                    switch (i2) {
                                        case 100001:
                                        case 100002:
                                        case 100003:
                                        case 100004:
                                            return "";
                                    }
                            }
                        case 1006:
                        case 1007:
                        case 1008:
                        case 1009:
                            return context.getString(R.string.sem_face_acquired_misaligned_face);
                    }
                } else {
                    return context.getString(R.string.sem_face_acquired_non_face);
                }
        }
        Slog.w(TAG, "Unknown enrollment acquired message: " + i + ", " + i2);
        return null;
    }

    public static String getErrorName(int i) {
        switch (i) {
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
            default:
                switch (i) {
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
                    default:
                        switch (i) {
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
        }
    }

    public static String getAcquiredName(int i) {
        if (i != 1001) {
            switch (i) {
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
                default:
                    switch (i) {
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
        }
        return "FACE_ACQUIRED_PROXIMITY_ALERT";
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

    private static boolean isVTCallOngoing(Context context) throws RemoteException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return false;
        }
        boolean zSemIsVideoCall = telephonyManager.semIsVideoCall();
        Log.i(TAG, "isVTCallOngoing = " + zSemIsVideoCall);
        return zSemIsVideoCall;
    }

    public void semAuthenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, int i, boolean z, Bundle bundle, byte[] bArr) {
        this.mBundle = bundle;
        this.mFidoRequestData = bArr;
        authenticate(cryptoObject, cancellationSignal, authenticationCallback, handler, new FaceAuthenticateOptions.Builder().setUserId(i).build());
        this.mBundle = null;
        this.mFidoRequestData = null;
    }

    public void semAuthenticateExt(CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, int i, byte[] bArr, Surface surface) {
        this.mSurface = surface;
        this.mFidoRequestData = bArr;
        this.mNeedtoAuthenticateExt = true;
        authenticate((CryptoObject) null, cancellationSignal, authenticationCallback, handler, new FaceAuthenticateOptions.Builder().setUserId(i).build());
        this.mFidoRequestData = null;
        this.mSurface = null;
        this.mNeedtoAuthenticateExt = false;
    }

    public boolean semIsEnrollSession() {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.semIsEnrollSession();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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

    public String semGetInfo(int i) {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return null;
        }
        try {
            return iFaceService.semGetInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semResetAuthenticationTimeout() {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.semResetAuthenticationTimeout();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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
                iFaceService.semSessionClose(UserHandle.myUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean semIsSessionClose() {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.semIsSessionClose();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int semGetSecurityLevel(boolean z) {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return -1;
        }
        try {
            return iFaceService.semGetSecurityLevel(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semIsFrameworkHandleLockout() {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.semIsFrameworkHandleLockout();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int semGetRemainingLockoutTime(int i) {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return -1;
        }
        try {
            return iFaceService.semGetRemainingLockoutTime(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semShouldRemoveTemplate() {
        IFaceService iFaceService = this.mService;
        if (iFaceService == null) {
            return false;
        }
        try {
            return iFaceService.semShouldRemoveTemplate();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
