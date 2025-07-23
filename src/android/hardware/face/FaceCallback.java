package android.hardware.face;

import android.content.Context;
import android.hardware.biometrics.CryptoObject;
import android.hardware.face.FaceManager;
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

    private static int getHelpCode(int i, int i2) {
        return i == 22 ? i2 : i;
    }

    FaceCallback(FaceManager.AuthenticationCallback authenticationCallback, CryptoObject cryptoObject) {
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

    FaceCallback(FaceManager.RemovalCallback removalCallback, Face face) {
        this.mRemovalCallback = removalCallback;
        this.mRemovalFace = face;
    }

    FaceCallback(FaceManager.RemovalCallback removalCallback) {
        this.mRemovalCallback = removalCallback;
    }

    public void sendSetFeatureCompleted(boolean z, int i) {
        FaceManager.SetFeatureCallback setFeatureCallback = this.mSetFeatureCallback;
        if (setFeatureCallback == null) {
            return;
        }
        setFeatureCallback.onCompleted(z, i);
    }

    public void sendGetFeatureCompleted(boolean z, int[] iArr, boolean[] zArr) {
        FaceManager.GetFeatureCallback getFeatureCallback = this.mGetFeatureCallback;
        if (getFeatureCallback == null) {
            return;
        }
        getFeatureCallback.onCompleted(z, iArr, zArr);
    }

    public void sendChallengeGenerated(int i, int i2, long j) {
        FaceManager.GenerateChallengeCallback generateChallengeCallback = this.mGenerateChallengeCallback;
        if (generateChallengeCallback == null) {
            return;
        }
        generateChallengeCallback.onGenerateChallengeResult(i, i2, j);
    }

    public void sendFaceDetected(int i, int i2, boolean z) {
        FaceManager.FaceDetectionCallback faceDetectionCallback = this.mFaceDetectionCallback;
        if (faceDetectionCallback == null) {
            Slog.e(TAG, "sendFaceDetected, callback null");
        } else {
            faceDetectionCallback.onFaceDetected(i, i2, z);
        }
    }

    public void sendRemovedResult(Face face, int i) {
        FaceManager.RemovalCallback removalCallback = this.mRemovalCallback;
        if (removalCallback == null) {
            return;
        }
        removalCallback.onRemovalSucceeded(face, i);
    }

    public void sendErrorResult(Context context, int i, int i2) {
        int i3 = i == 8 ? i2 : i;
        FaceManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onEnrollmentError(i3, FaceManager.getErrorString(context, i, i2));
            return;
        }
        FaceManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationError(i3, FaceManager.getErrorString(context, i, i2));
            return;
        }
        FaceManager.RemovalCallback removalCallback = this.mRemovalCallback;
        if (removalCallback != null) {
            removalCallback.onRemovalError(this.mRemovalFace, i3, FaceManager.getErrorString(context, i, i2));
            return;
        }
        FaceManager.FaceDetectionCallback faceDetectionCallback = this.mFaceDetectionCallback;
        if (faceDetectionCallback != null) {
            faceDetectionCallback.onDetectionError(i);
            this.mFaceDetectionCallback = null;
        }
    }

    public void sendEnrollResult(int i) {
        FaceManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onEnrollmentProgress(i);
        }
    }

    public void sendAuthenticatedSucceeded(Face face, int i, boolean z) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(new FaceManager.AuthenticationResult(this.mCryptoObject, face, i, z));
        }
    }

    public void sendAuthenticatedSucceeded(Face face, int i, boolean z, byte[] bArr) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(new FaceManager.AuthenticationResult(this.mCryptoObject, face, i, z), bArr);
        }
    }

    public void sendAuthenticatedSucceeded(Face face, int i, boolean z, Bundle bundle) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceededWithBundle(new FaceManager.AuthenticationResult(this.mCryptoObject, face, i, z), bundle);
        }
    }

    public void sendImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
        FaceManager.EnrollmentCallback enrollmentCallback = this.mEnrollmentCallback;
        if (enrollmentCallback != null) {
            enrollmentCallback.onImageProcessed(bArr, i, i2, i3, i4, bundle);
            return;
        }
        FaceManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onImageProcessed(i, i2, i3, i4, bundle);
        }
    }

    public void sendAuthenticatedFailed() {
        FaceManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationFailed();
        }
    }

    public void sendAcquiredResult(Context context, int i, int i2) {
        if (this.mAuthenticationCallback != null) {
            sendAuthenticationFrame(context, new FaceAuthenticationFrame(new FaceDataFrame(i, i2)));
        } else if (this.mEnrollmentCallback != null) {
            sendEnrollmentFrame(context, new FaceEnrollFrame(null, 0, new FaceDataFrame(i, i2)));
        }
    }

    public void sendAuthenticationFrame(Context context, FaceAuthenticationFrame faceAuthenticationFrame) {
        if (faceAuthenticationFrame == null) {
            Slog.w(TAG, "Received null authentication frame");
            return;
        }
        if (this.mAuthenticationCallback != null) {
            int acquiredInfo = faceAuthenticationFrame.getData().getAcquiredInfo();
            int vendorCode = faceAuthenticationFrame.getData().getVendorCode();
            int helpCode = getHelpCode(acquiredInfo, vendorCode);
            String authHelpMessage = FaceManager.getAuthHelpMessage(context, acquiredInfo, vendorCode);
            FaceManager.AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
            if (acquiredInfo == 22) {
                acquiredInfo = vendorCode;
            }
            authenticationCallback.onAuthenticationAcquired(acquiredInfo);
            if (authHelpMessage != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(helpCode, authHelpMessage);
            }
        }
    }

    public void sendEnrollmentFrame(Context context, FaceEnrollFrame faceEnrollFrame) {
        if (faceEnrollFrame == null) {
            Slog.w(TAG, "Received null enrollment frame");
            return;
        }
        if (this.mEnrollmentCallback != null) {
            FaceDataFrame data = faceEnrollFrame.getData();
            int acquiredInfo = data.getAcquiredInfo();
            int vendorCode = data.getVendorCode();
            this.mEnrollmentCallback.onEnrollmentFrame(getHelpCode(acquiredInfo, vendorCode), FaceManager.getEnrollHelpMessage(context, acquiredInfo, vendorCode), faceEnrollFrame.getCell(), faceEnrollFrame.getStage(), data.getPan(), data.getTilt(), data.getDistance());
        }
    }
}
