package android.hardware.face;

import android.content.Context;
import android.hardware.biometrics.CryptoObject;
import android.os.Bundle;
import android.util.Slog;

/* loaded from: classes2.dex */
public class FaceCallback {
    private static final String TAG = " FaceCallback";
    private FaceManager.AuthenticationCallback mAuthenticationCallback;
    private CryptoObject mCryptoObject;
    private FaceManager.EnrollmentCallback mEnrollmentCallback;
    private FaceManager.FaceDetectionCallback mFaceDetectionCallback;
    private FaceManager.GenerateChallengeCallback mGenerateChallengeCallback;
    private FaceManager.GetFeatureCallback mGetFeatureCallback;
    private FaceManager.RemovalCallback mRemovalCallback;
    private Face mRemovalFace;
    private FaceManager.SetFeatureCallback mSetFeatureCallback;

    FaceCallback(
            FaceManager.AuthenticationCallback authenticationCallback, CryptoObject cryptoObject) {
        this.mAuthenticationCallback = authenticationCallback;
        this.mCryptoObject = cryptoObject;
    }

    FaceCallback(FaceManager.FaceDetectionCallback faceDetectionCallback) {
        this.mFaceDetectionCallback = faceDetectionCallback;
    }

    FaceCallback(FaceManager.EnrollmentCallback enrollmentCallback) {
        this.mEnrollmentCallback = enrollmentCallback;
    }

    FaceCallback(FaceManager.GenerateChallengeCallback generateChallengeCallback) {
        this.mGenerateChallengeCallback = generateChallengeCallback;
    }

    FaceCallback(FaceManager.SetFeatureCallback setFeatureCallback) {
        this.mSetFeatureCallback = setFeatureCallback;
    }

    FaceCallback(FaceManager.GetFeatureCallback getFeatureCallback) {
        this.mGetFeatureCallback = getFeatureCallback;
    }

    FaceCallback(FaceManager.RemovalCallback removalCallback, Face removalFace) {
        this.mRemovalCallback = removalCallback;
        this.mRemovalFace = removalFace;
    }

    FaceCallback(FaceManager.RemovalCallback removalCallback) {
        this.mRemovalCallback = removalCallback;
    }

    public void sendSetFeatureCompleted(boolean success, int feature) {
        if (this.mSetFeatureCallback == null) {
            return;
        }
        this.mSetFeatureCallback.onCompleted(success, feature);
    }

    public void sendGetFeatureCompleted(boolean success, int[] features, boolean[] featureState) {
        if (this.mGetFeatureCallback == null) {
            return;
        }
        this.mGetFeatureCallback.onCompleted(success, features, featureState);
    }

    public void sendChallengeGenerated(int sensorId, int userId, long challenge) {
        if (this.mGenerateChallengeCallback == null) {
            return;
        }
        this.mGenerateChallengeCallback.onGenerateChallengeResult(sensorId, userId, challenge);
    }

    public void sendFaceDetected(int sensorId, int userId, boolean isStrongBiometric) {
        if (this.mFaceDetectionCallback == null) {
            Slog.e(TAG, "sendFaceDetected, callback null");
        } else {
            this.mFaceDetectionCallback.onFaceDetected(sensorId, userId, isStrongBiometric);
        }
    }

    public void sendRemovedResult(Face face, int remaining) {
        if (this.mRemovalCallback == null) {
            return;
        }
        this.mRemovalCallback.onRemovalSucceeded(face, remaining);
    }

    public void sendErrorResult(Context context, int errMsgId, int vendorCode) {
        int clientErrMsgId = errMsgId == 8 ? vendorCode : errMsgId;
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentError(
                    clientErrMsgId, FaceManager.getErrorString(context, errMsgId, vendorCode));
            return;
        }
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationError(
                    clientErrMsgId, FaceManager.getErrorString(context, errMsgId, vendorCode));
            return;
        }
        if (this.mRemovalCallback != null) {
            this.mRemovalCallback.onRemovalError(
                    this.mRemovalFace,
                    clientErrMsgId,
                    FaceManager.getErrorString(context, errMsgId, vendorCode));
        } else if (this.mFaceDetectionCallback != null) {
            this.mFaceDetectionCallback.onDetectionError(errMsgId);
            this.mFaceDetectionCallback = null;
        }
    }

    public void sendEnrollResult(int remaining) {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onEnrollmentProgress(remaining);
        }
    }

    public void sendAuthenticatedSucceeded(Face face, int userId, boolean isStrongBiometric) {
        if (this.mAuthenticationCallback != null) {
            FaceManager.AuthenticationResult result =
                    new FaceManager.AuthenticationResult(
                            this.mCryptoObject, face, userId, isStrongBiometric);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    public void sendAuthenticatedSucceeded(
            Face face, int userId, boolean isStrongBiometric, byte[] fidoResultData) {
        if (this.mAuthenticationCallback != null) {
            FaceManager.AuthenticationResult result =
                    new FaceManager.AuthenticationResult(
                            this.mCryptoObject, face, userId, isStrongBiometric);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result, fidoResultData);
        }
    }

    public void sendAuthenticatedSucceeded(
            Face face, int userId, boolean isStrongBiometric, Bundle b) {
        if (this.mAuthenticationCallback != null) {
            FaceManager.AuthenticationResult result =
                    new FaceManager.AuthenticationResult(
                            this.mCryptoObject, face, userId, isStrongBiometric);
            this.mAuthenticationCallback.onAuthenticationSucceededWithBundle(result, b);
        }
    }

    public void sendImageProcessed(
            byte[] data, int width, int height, int orientation, int imageFormat, Bundle b) {
        if (this.mEnrollmentCallback != null) {
            this.mEnrollmentCallback.onImageProcessed(
                    data, width, height, orientation, imageFormat, b);
        } else if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onImageProcessed(
                    width, height, orientation, imageFormat, b);
        }
    }

    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    public void sendAcquiredResult(Context context, int acquireInfo, int vendorCode) {
        if (this.mAuthenticationCallback != null) {
            FaceAuthenticationFrame frame =
                    new FaceAuthenticationFrame(new FaceDataFrame(acquireInfo, vendorCode));
            sendAuthenticationFrame(context, frame);
        } else if (this.mEnrollmentCallback != null) {
            FaceEnrollFrame frame2 =
                    new FaceEnrollFrame(null, 0, new FaceDataFrame(acquireInfo, vendorCode));
            sendEnrollmentFrame(context, frame2);
        }
    }

    public void sendAuthenticationFrame(Context context, FaceAuthenticationFrame frame) {
        if (frame == null) {
            Slog.w(TAG, "Received null authentication frame");
            return;
        }
        if (this.mAuthenticationCallback != null) {
            int acquireInfo = frame.getData().getAcquiredInfo();
            int vendorCode = frame.getData().getVendorCode();
            int helpCode = getHelpCode(acquireInfo, vendorCode);
            String helpMessage = FaceManager.getAuthHelpMessage(context, acquireInfo, vendorCode);
            this.mAuthenticationCallback.onAuthenticationAcquired(
                    acquireInfo == 22 ? vendorCode : acquireInfo);
            if (helpMessage != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(helpCode, helpMessage);
            }
        }
    }

    public void sendEnrollmentFrame(Context context, FaceEnrollFrame frame) {
        if (frame == null) {
            Slog.w(TAG, "Received null enrollment frame");
            return;
        }
        if (this.mEnrollmentCallback != null) {
            FaceDataFrame data = frame.getData();
            int acquireInfo = data.getAcquiredInfo();
            int vendorCode = data.getVendorCode();
            int helpCode = getHelpCode(acquireInfo, vendorCode);
            String helpMessage = FaceManager.getEnrollHelpMessage(context, acquireInfo, vendorCode);
            this.mEnrollmentCallback.onEnrollmentFrame(
                    helpCode,
                    helpMessage,
                    frame.getCell(),
                    frame.getStage(),
                    data.getPan(),
                    data.getTilt(),
                    data.getDistance());
        }
    }

    private static int getHelpCode(int acquireInfo, int vendorCode) {
        if (acquireInfo == 22) {
            return vendorCode;
        }
        return acquireInfo;
    }
}
