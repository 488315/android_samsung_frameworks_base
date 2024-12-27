package android.hardware.fingerprint;

import android.content.Context;
import android.util.Slog;

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

    public @interface RemoveRequest {}

    FingerprintCallback(
            FingerprintManager.AuthenticationCallback authenticationCallback,
            FingerprintManager.CryptoObject cryptoObject) {
        this.mAuthenticationCallback = authenticationCallback;
        this.mCryptoObject = cryptoObject;
    }

    FingerprintCallback(
            FingerprintManager.FingerprintDetectionCallback fingerprintDetectionCallback) {
        this.mFingerprintDetectionCallback = fingerprintDetectionCallback;
    }

    FingerprintCallback(FingerprintManager.EnrollmentCallback enrollmentCallback) {
        this.mEnrollmentCallback = enrollmentCallback;
    }

    FingerprintCallback(FingerprintManager.GenerateChallengeCallback generateChallengeCallback) {
        this.mGenerateChallengeCallback = generateChallengeCallback;
    }

    FingerprintCallback(
            FingerprintManager.RemovalCallback removalCallback,
            int removeRequest,
            Fingerprint removeFingerprint) {
        this.mRemovalCallback = removalCallback;
        this.mRemoveRequest = removeRequest;
        this.mRemoveFingerprint = removeFingerprint;
    }

    public void sendEnrollResult(int remaining) {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentProgress(remaining);
        }
    }

    public void sendRemovedResult(Fingerprint fingerprint, int remaining) {
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
            int fingerId = fingerprint.getBiometricId();
            int reqFingerId = this.mRemoveFingerprint.getBiometricId();
            if (reqFingerId != 0 && fingerId != 0 && fingerId != reqFingerId) {
                Slog.w(TAG, "Finger id didn't match: " + fingerId + " != " + reqFingerId);
                return;
            }
        }
        this.mRemovalCallback.onRemovalSucceeded(fingerprint, remaining);
    }

    public void sendAuthenticatedSucceeded(
            Fingerprint fingerprint, int userId, boolean isStrongBiometric) {
        if (this.mAuthenticationCallback == null) {
            Slog.e(TAG, "Authentication succeeded but callback is null.");
        } else {
            FingerprintManager.AuthenticationResult result =
                    new FingerprintManager.AuthenticationResult(
                            this.mCryptoObject, fingerprint, userId, isStrongBiometric);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    public void sendAcquiredResult(Context context, int acquireInfo, int vendorCode) {
        if (this.mEnrollmentCallback != null && acquireInfo != 7) {
            this.mEnrollmentCallback.onAcquired(acquireInfo == 0);
        }
        String msg = FingerprintManager.getAcquiredString(context, acquireInfo, vendorCode);
        int clientInfo = acquireInfo == 6 ? vendorCode : acquireInfo;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentHelp(clientInfo, msg);
            return;
        }
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationAcquired(clientInfo);
            if (acquireInfo != 7 && msg != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(clientInfo, msg);
            }
        }
    }

    public void sendErrorResult(Context context, int errMsgId, int vendorCode) {
        int clientErrMsgId = errMsgId == 8 ? vendorCode : errMsgId;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentError(
                    clientErrMsgId,
                    FingerprintManager.getErrorString(context, errMsgId, vendorCode));
            return;
        }
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationError(
                    clientErrMsgId,
                    FingerprintManager.getErrorString(context, errMsgId, vendorCode));
            return;
        }
        if (this.mRemovalCallback != null) {
            this.mRemovalCallback.onRemovalError(
                    this.mRemoveFingerprint,
                    clientErrMsgId,
                    FingerprintManager.getErrorString(context, errMsgId, vendorCode));
        } else if (this.mFingerprintDetectionCallback != null) {
            this.mFingerprintDetectionCallback.onDetectionError(errMsgId);
            this.mFingerprintDetectionCallback = null;
        }
    }

    public void sendChallengeGenerated(long challenge, int sensorId, int userId) {
        if (this.mGenerateChallengeCallback == null) {
            Slog.e(TAG, "sendChallengeGenerated, callback null");
        } else {
            this.mGenerateChallengeCallback.onChallengeGenerated(sensorId, userId, challenge);
        }
    }

    public void sendFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric) {
        if (this.mFingerprintDetectionCallback == null) {
            Slog.e(TAG, "sendFingerprintDetected, callback null");
        } else {
            this.mFingerprintDetectionCallback.onFingerprintDetected(
                    sensorId, userId, isStrongBiometric);
        }
    }

    public void sendUdfpsPointerDown(int sensorId) {
        if (this.mAuthenticationCallback == null) {
            Slog.e(TAG, "sendUdfpsPointerDown, callback null");
        } else {
            this.mAuthenticationCallback.onUdfpsPointerDown(sensorId);
        }
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onUdfpsPointerDown(sensorId);
        }
    }

    public void sendUdfpsPointerUp(int sensorId) {
        if (this.mAuthenticationCallback == null) {
            Slog.e(TAG, "sendUdfpsPointerUp, callback null");
        } else {
            this.mAuthenticationCallback.onUdfpsPointerUp(sensorId);
        }
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onUdfpsPointerUp(sensorId);
        }
    }

    public void sendUdfpsOverlayShown() {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onUdfpsOverlayShown();
        }
    }
}
