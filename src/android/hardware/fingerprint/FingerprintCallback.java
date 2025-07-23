package android.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Slog;

/* loaded from: classes2.dex */
public class FingerprintCallback {
    public static final int REMOVE_ALL = 2;
    public static final int REMOVE_SINGLE = 1;
    private static final String TAG = "FingerprintCallback";
    private FingerprintManager.AuthenticationCallback mAuthenticationCallback;
    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintManager.EnrollmentCallback mEnrollmentCallback;
    private FingerprintManager.FingerprintDetectionCallback mFingerprintDetectionCallback;
    private FingerprintManager.GenerateChallengeCallback mGenerateChallengeCallback;
    private FingerprintManager.RemovalCallback mRemovalCallback;
    private Fingerprint mRemoveFingerprint;
    private int mRemoveRequest;

    public @interface RemoveRequest {
    }

    FingerprintCallback(FingerprintManager.AuthenticationCallback authenticationCallback, FingerprintManager.CryptoObject cryptoObject) {
        this.mAuthenticationCallback = authenticationCallback;
        this.mCryptoObject = cryptoObject;
    }

    FingerprintCallback(FingerprintManager.FingerprintDetectionCallback fingerprintDetectionCallback) {
        this.mFingerprintDetectionCallback = fingerprintDetectionCallback;
    }

    FingerprintCallback(FingerprintManager.EnrollmentCallback enrollmentCallback) {
        this.mEnrollmentCallback = enrollmentCallback;
    }

    FingerprintCallback(FingerprintManager.GenerateChallengeCallback generateChallengeCallback) {
        this.mGenerateChallengeCallback = generateChallengeCallback;
    }

    FingerprintCallback(FingerprintManager.RemovalCallback removalCallback, int i, Fingerprint fingerprint) {
        this.mRemovalCallback = removalCallback;
        this.mRemoveRequest = i;
        this.mRemoveFingerprint = fingerprint;
    }

    public void sendEnrollResult(int i) {
        FingerprintManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onEnrollmentProgress(i);
        }
    }

    public void sendRemovedResult(Fingerprint fingerprint, int i) {
        if (this.mRemovalCallback == null) {
            return;
        }
        if (this.mRemoveRequest == 1) {
            if (fingerprint == null) {
                Slog.e(TAG, "Received MSG_REMOVED, but fingerprint is null");
                return;
            }
            if (this.mRemoveFingerprint == null) {
                Slog.e(TAG, "Missing fingerprint");
                return;
            }
            int biometricId = fingerprint.getBiometricId();
            int biometricId2 = this.mRemoveFingerprint.getBiometricId();
            if (biometricId2 != 0 && biometricId != 0 && biometricId != biometricId2) {
                Slog.w(TAG, "Finger id didn't match: " + biometricId + " != " + biometricId2);
                return;
            }
        }
        this.mRemovalCallback.onRemovalSucceeded(fingerprint, i);
    }

    public void sendAuthenticatedSucceeded(Fingerprint fingerprint, int i, boolean z) {
        if (this.mAuthenticationCallback == null) {
            Slog.e(TAG, "Authentication succeeded but callback is null.");
        } else {
            this.mAuthenticationCallback.onAuthenticationSucceeded(new FingerprintManager.AuthenticationResult(this.mCryptoObject, fingerprint, i, z));
        }
    }

    public void sendAuthenticatedFailed() {
        FingerprintManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationFailed();
        }
    }

    public void sendAcquiredResult(Context context, int i, int i2) {
        FingerprintManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null && i != 7) {
            enrollmentCallback.onAcquired(i == 0);
        }
        String acquiredString = FingerprintManager.getAcquiredString(context, i, i2);
        if (i != 6) {
            i2 = i;
        }
        FingerprintManager.EnrollmentCallback enrollmentCallback2 = this.mEnrollmentCallback;
        if (enrollmentCallback2 != null) {
            enrollmentCallback2.onEnrollmentHelp(i2, acquiredString);
            return;
        }
        FingerprintManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationAcquired(i2);
            if (i == 7 || acquiredString == null) {
                return;
            }
            this.mAuthenticationCallback.onAuthenticationHelp(i2, acquiredString);
        }
    }

    public void sendErrorResult(Context context, int i, int i2) {
        int i3 = i == 8 ? i2 : i;
        FingerprintManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onEnrollmentError(i3, FingerprintManager.getErrorString(context, i, i2));
            return;
        }
        FingerprintManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationError(i3, FingerprintManager.getErrorString(context, i, i2));
            return;
        }
        FingerprintManager.RemovalCallback removalCallback = this.mRemovalCallback;
        if (removalCallback != null) {
            removalCallback.onRemovalError(this.mRemoveFingerprint, i3, FingerprintManager.getErrorString(context, i, i2));
            return;
        }
        FingerprintManager.FingerprintDetectionCallback fingerprintDetectionCallback = this.mFingerprintDetectionCallback;
        if (fingerprintDetectionCallback != null) {
            fingerprintDetectionCallback.onDetectionError(i);
            this.mFingerprintDetectionCallback = null;
        }
    }

    public void sendChallengeGenerated(long j, int i, int i2) {
        FingerprintManager.GenerateChallengeCallback generateChallengeCallback = this.mGenerateChallengeCallback;
        if (generateChallengeCallback == null) {
            Slog.e(TAG, "sendChallengeGenerated, callback null");
        } else {
            generateChallengeCallback.onChallengeGenerated(i, i2, j);
        }
    }

    public void sendFingerprintDetected(int i, int i2, boolean z) {
        FingerprintManager.FingerprintDetectionCallback fingerprintDetectionCallback = this.mFingerprintDetectionCallback;
        if (fingerprintDetectionCallback == null) {
            Slog.e(TAG, "sendFingerprintDetected, callback null");
        } else {
            fingerprintDetectionCallback.onFingerprintDetected(i, i2, z);
        }
    }

    public void sendUdfpsPointerDown(int i) {
        FingerprintManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback == null) {
            Slog.e(TAG, "sendUdfpsPointerDown, callback null");
        } else {
            authenticationCallback.onUdfpsPointerDown(i);
        }
        FingerprintManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onUdfpsPointerDown(i);
        }
    }

    public void sendUdfpsPointerUp(int i) {
        FingerprintManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback == null) {
            Slog.e(TAG, "sendUdfpsPointerUp, callback null");
        } else {
            authenticationCallback.onUdfpsPointerUp(i);
        }
        FingerprintManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onUdfpsPointerUp(i);
        }
    }

    public void sendUdfpsOverlayShown() {
        FingerprintManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onUdfpsOverlayShown();
        }
    }
}
