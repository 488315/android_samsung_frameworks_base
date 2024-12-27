package com.samsung.android.bio.face;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFaceConstants;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceAuthenticationFrame;
import android.hardware.face.FaceEnrollFrame;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceService;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Trace;
import android.util.Log;
import android.view.Surface;
import android.view.View;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes5.dex */
public class SemBioFaceManager {
    public static final String BUNDLE_AUTH_COORDINATE_H = "auth_coordinate_h";
    public static final String BUNDLE_AUTH_COORDINATE_TOKEN = "auth_coordinate_token";
    public static final String BUNDLE_AUTH_COORDINATE_W = "auth_coordinate_w";
    public static final String BUNDLE_AUTH_COORDINATE_X = "auth_coordinate_x";
    public static final String BUNDLE_AUTH_COORDINATE_Y = "auth_coordinate_y";
    public static final String BUNDLE_PREVIEW_ON_TOP = "preview_on_top";
    public static final String BUNDLE_SET_SECURITY_LEVEL = "security_level";
    public static final String BUNDLE_SET_TIMEOUT = "set_timeout";
    public static final String BUNDLE_SKIP_WAKELOCK = "skip_wakelock";
    public static final String BUNDLE_SUPPORT_AUTH_COORDINATE = "support_auth_coordinate";
    public static final String EXTRA_KEY_PRIVILEGED_FLAG = "sem_privileged_attr";
    public static final int FACE_ACQUIRED_FABK = 100005;
    public static final int FACE_ACQUIRED_FAKE = 4;
    public static final int FACE_ACQUIRED_FALI_FATO = 100002;
    public static final int FACE_ACQUIRED_FALQ_FMLQ = 100003;
    public static final int FACE_ACQUIRED_FAMK = 100001;
    public static final int FACE_ACQUIRED_FAMO = 100006;
    public static final int FACE_ACQUIRED_FANM_FMNM = 100004;
    public static final int FACE_ACQUIRED_GOOD = 0;
    public static final int FACE_ACQUIRED_INVALID = 2;
    public static final int FACE_ACQUIRED_LOW_QUALITY = 3;
    public static final int FACE_ACQUIRED_MISALIGNED = 7;
    public static final int FACE_ACQUIRED_MISALIGNED_BOTTOM = 1013;
    public static final int FACE_ACQUIRED_MISALIGNED_BOTTOM_LEFT = 1012;
    public static final int FACE_ACQUIRED_MISALIGNED_BOTTOM_RIGHT = 1014;
    public static final int FACE_ACQUIRED_MISALIGNED_LEFT = 1009;
    public static final int FACE_ACQUIRED_MISALIGNED_MIDDLE = 1010;
    public static final int FACE_ACQUIRED_MISALIGNED_RIGHT = 1011;
    public static final int FACE_ACQUIRED_MISALIGNED_TOP = 1007;
    public static final int FACE_ACQUIRED_MISALIGNED_TOP_LEFT = 1006;
    public static final int FACE_ACQUIRED_MISALIGNED_TOP_RIGHT = 1008;
    public static final int FACE_ACQUIRED_ON_MASK = 1017;
    public static final int FACE_ACQUIRED_PROCESS_FAIL = 1;
    public static final int FACE_ACQUIRED_PROXIMITY_ALERT = 1001;
    public static final int FACE_ACQUIRED_REVERSE_ORIENTATION = 1002;
    public static final int FACE_ACQUIRED_SURFACE_UPDATED = 2001;
    public static final int FACE_ACQUIRED_TOO_BIG = 5;
    public static final int FACE_ACQUIRED_TOO_DARK = 1015;
    public static final int FACE_ACQUIRED_TOO_SMALL = 6;
    public static final int FACE_ACQUIRED_WITH_GLASSES = 1016;
    public static final int FACE_ERROR_CAMERA_ACCESS_SETTING_OFF = 100003;
    public static final int FACE_ERROR_CAMERA_FAILURE = 10003;
    public static final int FACE_ERROR_CAMERA_UNAVAILABLE = 10005;
    public static final int FACE_ERROR_CANCELED = 5;
    public static final int FACE_ERROR_HW_UNAVAILABLE = 1;
    public static final int FACE_ERROR_IDENTIFY_FAILURE_BROKEN_DATABASE = 1004;
    public static final int FACE_ERROR_LOCKOUT = 10001;
    public static final int FACE_ERROR_LOCKOUT_PERMANENT = 10002;
    public static final int FACE_ERROR_NO_SPACE = 4;
    public static final int FACE_ERROR_ON_MASK = 1006;
    public static final int FACE_ERROR_PPP_TIMEOUT = 1005;
    public static final int FACE_ERROR_SESSION_CLOSED = 1007;
    public static final int FACE_ERROR_TEMPLATE_CORRUPTED = 1004;
    public static final int FACE_ERROR_TIMEOUT = 3;
    public static final int FACE_ERROR_TOO_DARK = 100001;
    public static final int FACE_ERROR_TOO_DARK_TO_ENROLL = 100002;
    public static final int FACE_ERROR_UNABLE_TO_PROCESS = 2;
    public static final int FACE_ERROR_USER_CANCELED = 10;
    public static final int FACE_OK = 0;
    public static final int FLAG_ENROLL_WITHOUT_TOKEN = 1;
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_ERROR = 104;
    private static final String PKG_NAME_DESKTOP_KEYGUARD = "com.samsung.desktopsystemui";
    private static final String PKG_NAME_KEYGUARD = "com.android.systemui";
    public static final int PRIVILEGED_FLAG_ALLOW_BACKGROUND = 4;
    public static final int PRIVILEGED_FLAG_AVOID_LOCKOUT = 1;
    public static final int PRIVILEGED_FLAG_USE_SETTING_FOR_SECURITY_LEVEL = 2;
    public static final int SECURITY_LEVEL_CONVENIENCE = 3;
    public static final int SECURITY_LEVEL_NONE = 0;
    public static final int SECURITY_LEVEL_STRONG = 1;
    public static final int SECURITY_LEVEL_WEAK = 2;
    public static final int SEM_FACE_GET_TA_VERSION = 1;
    private static final String TAG = "SemBioFaceManager";
    private AuthenticationCallback mAuthenticationCallback;
    private Context mContext;
    private CryptoObject mCryptoObject;
    private FaceManagerCompat mFaceManagerCompat;
    private Handler mHandler;
    private static final boolean DEBUG = Debug.semIsProductDev();
    public static final boolean IS_SUPPORTED_ALTERNATIVE_ENROLLMENT_AND_CLOSED_EYES_DETECTION =
            !"in_house".contains("jdm");
    private IBinder mToken = new Binder();
    private long mAuthRequestId = 0;

    public static final class CryptoObject {
        private BiometricPrompt.CryptoObject mBioCryptoObject;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData;

        CryptoObject(BiometricPrompt.CryptoObject cry) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = cry;
            this.mFidoRequestData = null;
        }

        public BiometricPrompt.CryptoObject getBiometricCryptoObject() {
            return this.mBioCryptoObject;
        }

        public long getOpId() {
            if (this.mFidoRequestData != null) {
                return 0L;
            }
            return this.mBioCryptoObject.getOpId();
        }

        public CryptoObject(Signature signature, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            if (signature != null) {
                this.mBioCryptoObject = new BiometricPrompt.CryptoObject(signature);
            }
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Cipher cipher, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            if (cipher != null) {
                this.mBioCryptoObject = new BiometricPrompt.CryptoObject(cipher);
            }
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Mac mac, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            if (mac != null) {
                this.mBioCryptoObject = new BiometricPrompt.CryptoObject(mac);
            }
            this.mFidoRequestData = fidoRequestData;
        }

        public Signature getSignature() {
            return this.mBioCryptoObject.getSignature();
        }

        public Cipher getCipher() {
            return this.mBioCryptoObject.getCipher();
        }

        public Mac getMac() {
            return this.mBioCryptoObject.getMac();
        }

        public byte[] getFidoRequestData() {
            return this.mFidoRequestData;
        }

        public byte[] getFidoResultData() {
            return this.mFidoResultData;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFidoResultData(byte[] fidoResultData) {
            this.mFidoResultData = fidoResultData;
        }
    }

    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private SemBioFace mFace;
        private boolean mIsStrongBiometric;
        private int mUserId;

        public AuthenticationResult(
                CryptoObject crypto, SemBioFace face, int userId, boolean isStrongBiometric) {
            this.mCryptoObject = crypto;
            this.mFace = face;
            this.mUserId = userId;
            this.mIsStrongBiometric = isStrongBiometric;
        }

        public AuthenticationResult(CryptoObject crypto, SemBioFace face) {
            this.mCryptoObject = crypto;
            this.mFace = face;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public SemBioFace getFace() {
            return this.mFace;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public boolean isStrongBiometric() {
            if (SemBioFaceManager.DEBUG) {
                Log.i(SemBioFaceManager.TAG, "isStrong = " + this.mIsStrongBiometric);
            }
            return this.mIsStrongBiometric;
        }

        public Bundle getSecureInfo() {
            return null;
        }
    }

    public abstract static class AuthenticationCallback {
        public void onAuthenticationError(int errorCode, CharSequence errString) {}

        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {}

        public void onAuthenticationSucceeded(AuthenticationResult result) {}

        public void onAuthenticationFailed() {}

        public void onAuthenticationAcquired(int acquireInfo) {}
    }

    public abstract static class EnrollmentCallback {
        public void onEnrollmentError(int errMsgId, CharSequence errString) {}

        public void onEnrollmentHelp(int helpMsgId, CharSequence helpString) {}

        public void onEnrollmentProgress(int remaining) {}

        public void onImageProcessed(
                byte[] data, int width, int height, int orientation, int imageFormat, Bundle b) {}
    }

    public abstract static class RemovalCallback {
        public void onRemovalError(SemBioFace face, int errMsgId, CharSequence errString) {}

        public void onRemovalSucceeded(SemBioFace face) {}
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            int flags,
            AuthenticationCallback callback,
            Handler handler,
            View faceView) {
        Bundle bundle = null;
        if (faceView != null) {
            Object tag = faceView.getTag();
            if (tag instanceof Bundle) {
                bundle = (Bundle) tag;
            }
        }
        authenticate(
                crypto,
                cancel,
                flags,
                callback,
                handler,
                this.mContext.getUserId(),
                bundle,
                faceView);
    }

    public void authenticate(
            CryptoObject crypto,
            CancellationSignal cancel,
            int flags,
            AuthenticationCallback callback,
            Handler handler,
            int userId,
            Bundle attr,
            View faceView) {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            this.mFaceManagerCompat.hAuthenticate(
                    crypto, cancel, flags, callback, handler, userId, attr);
        }
    }

    public void authenticate(
            CancellationSignal cancel,
            Handler handler,
            int userId,
            Surface previewSurface,
            byte[] requestData,
            AuthenticationCallback callback) {}

    public void resume() {}

    public String getTaVersionCode() {
        return null;
    }

    public void enroll(
            byte[] token,
            CancellationSignal cancel,
            int flags,
            EnrollmentCallback callback,
            View faceView) {
        Log.e(TAG, "enroll() : this is not used");
    }

    public void enroll(
            byte[] token,
            CancellationSignal cancel,
            int flags,
            int userId,
            EnrollmentCallback callback,
            Bundle attr,
            View faceView) {
        Log.e(TAG, "enroll() : this is not used.");
    }

    public long preEnroll() {
        Log.e(TAG, "preEnroll() : this is not used");
        return 0L;
    }

    public abstract static class ChallengeCallback {
        public void onPreEnroll(long hat) {}
    }

    public long preEnroll(ChallengeCallback callback) {
        Log.e(TAG, "preEnroll() : this is not used.");
        return 0L;
    }

    public int postEnroll() {
        Log.e(TAG, "postEnroll() : this is not used.");
        return 0;
    }

    public void setActiveUser(int userId) {
        Log.e(TAG, "setActiveUser() : this is not used");
    }

    public void remove(SemBioFace face, int userId, RemovalCallback callback) {
        Log.e(TAG, "remove() : this is not used");
    }

    public void remove(SemBioFace face, RemovalCallback callback) {
        Log.e(TAG, "remove() : this is not used.");
    }

    public void rename(int faceId, int userId, String newName) {
        Log.e(TAG, "rename() : this is not used");
    }

    public List<SemBioFace> getEnrolledFaces(int userId) {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.hGetEnrolledFaces(userId);
        }
        return null;
    }

    public List<SemBioFace> getEnrolledFaces() {
        return getEnrolledFaces(this.mContext.getUserId());
    }

    public boolean hasEnrolledFaces() {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.hasEnrolledTemplates();
        }
        return false;
    }

    public boolean hasDisabledFaces() {
        boolean unused = this.mFaceManagerCompat.mHasFaceHAL;
        return false;
    }

    public boolean hasEnrolledFaces(int userId) {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.hasEnrolledTemplates(userId);
        }
        return false;
    }

    public boolean isHardwareDetected() {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.isHardwareDetected();
        }
        return true;
    }

    public boolean resetAuthenticationTimeout() {
        Log.e(TAG, "resetAuthenticationTimeout() : this is not used");
        return false;
    }

    public int getSecurityLevel() {
        return getSecurityLevel(null);
    }

    public int getSecurityLevel(Context context) {
        boolean isKeyguard = context == null ? false : isKeyguard(context.getOpPackageName());
        if (!this.mFaceManagerCompat.mHasFaceHAL) {
            return 0;
        }
        return this.mFaceManagerCompat.hGetSecurityLevel(isKeyguard);
    }

    public static int getSepMappedAcquiredInfo(int acquireInfo, int vendorCode) {
        switch (acquireInfo) {
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 3;
            case 3:
                return 1015;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 1007;
            case 7:
                return 1013;
            case 8:
                return 1011;
            case 9:
                return 1009;
            case 10:
                return 2;
            case 11:
                return 2;
            case 12:
                return 7;
            case 13:
                return 1;
            case 14:
                return vendorCode;
            case 15:
                return vendorCode;
            case 16:
                return 7;
            case 17:
                return 7;
            case 18:
                return 7;
            case 19:
                return 2;
            case 20:
                return acquireInfo;
            case 21:
                return 3;
            case 22:
                switch (vendorCode) {
                    case 1001:
                        return 1001;
                    case 1005:
                        return 4;
                    case 1006:
                        return 1006;
                    case 1007:
                        return 1007;
                    case 1008:
                        return 1008;
                    case 1009:
                        return 1009;
                    case 1011:
                        return 1011;
                    case 1012:
                        return 1012;
                    case 1013:
                        return 1013;
                    case 1014:
                        return 1014;
                    case 1015:
                        return 1015;
                    case 1016:
                        return 1016;
                    case 1017:
                        return 1017;
                    case 100001:
                        return 100001;
                    case 100002:
                        return 100002;
                    case 100003:
                        return 100003;
                    case 100004:
                        return 100004;
                    case 100005:
                        return 100005;
                    case 100006:
                        return 100006;
                    default:
                        return acquireInfo;
                }
            default:
                Log.d(TAG, "getSepMappedAcquiredInfo: No data, " + acquireInfo + ", " + vendorCode);
                return acquireInfo;
        }
    }

    public static int getSepMappedError(int errCode, int vendorCode) {
        switch (errCode) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                switch (vendorCode) {
                    case 1001:
                        break;
                    case 1002:
                        break;
                    case 1003:
                        break;
                    case 1004:
                        break;
                    case 1005:
                        break;
                    case 1006:
                        break;
                    case 1007:
                        break;
                    case 100001:
                        break;
                    case 100002:
                        break;
                    case 100003:
                        break;
                }
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            default:
                Log.d(TAG, "getSepMappedError: No data, " + errCode + ", " + vendorCode);
                break;
        }
        return errCode;
    }

    private class MyHandler extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.i(
                    SemBioFaceManager.TAG,
                    "handleMessage = " + msg.what + ", " + msg.arg1 + ", " + msg.arg2);
            switch (msg.what) {
                case 101:
                    SemBioFaceManager.this.sendAcquiredResult(msg.arg1, (String) msg.obj);
                    break;
                case 102:
                    SemBioFaceManager.this.sendAuthenticatedSucceeded(
                            (AuthenticationResult) msg.obj);
                    break;
                case 103:
                    SemBioFaceManager.this.sendAuthenticatedFailed();
                    break;
                case 104:
                    SemBioFaceManager.this.sendErrorResult(msg.arg1, (String) msg.obj);
                    break;
                default:
                    Log.w(SemBioFaceManager.TAG, "handleMessage : Unknown msg");
                    break;
            }
        }

        private void sendRemovedResult(long deviceId, int faceId, int groupId) {}

        private void sendEnrollResult(SemBioFace face, int progress) {}
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(int errMsgId, String errMsg) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationError(errMsgId, errMsg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(AuthenticationResult result) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAcquiredResult(int acquireInfo, String helpMsg) {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationAcquired(acquireInfo);
            if (helpMsg != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(acquireInfo, helpMsg);
            }
        }
    }

    public static SemBioFaceManager getInstance(Context context) {
        return createInstance(context);
    }

    public static SemBioFaceManager createInstance(Context context) {
        return new SemBioFaceManager(context);
    }

    private SemBioFaceManager(Context context) {
        this.mContext = context;
        this.mHandler = new MyHandler(context);
        this.mFaceManagerCompat = new FaceManagerCompat(context);
    }

    public static void setExtraInfo(Context context, Bundle b) {
        if (b == null) {
            return;
        }
        try {
            b.putInt("DISPLAY_TYPE", context.getDisplay().getDisplayId());
        } catch (Exception e) {
            Log.w(TAG, "setExtraInfo: " + e.getMessage());
        }
    }

    private boolean isKeyguard(String clientPackage) {
        return "com.android.systemui".equals(clientPackage)
                || PKG_NAME_DESKTOP_KEYGUARD.equals(clientPackage);
    }

    public class FaceManagerCompat extends BiometricFaceConstants
            implements BiometricAuthenticator {
        private FaceManager mFaceManagerHAL;
        private boolean mHasFaceHAL;
        private IFaceService mServiceHAL;
        private IFaceServiceReceiver mServiceReceiverHAL;

        FaceManagerCompat(Context ctx) {
            try {
                PackageManager pm = ctx.getPackageManager();
                if (pm.hasSystemFeature(PackageManager.FEATURE_FACE)) {
                    this.mHasFaceHAL = true;
                    this.mServiceHAL =
                            IFaceService.Stub.asInterface(
                                    ServiceManager.getService(Context.FACE_SERVICE));
                    this.mFaceManagerHAL = new FaceManager(ctx, this.mServiceHAL);
                    initHAL();
                }
            } catch (Exception e) {
                Log.w(SemBioFaceManager.TAG, "FaceManagerCompat: " + e.getMessage());
            }
        }

        public boolean isHardwareDetected() {
            return this.mFaceManagerHAL.isHardwareDetected();
        }

        public boolean hasEnrolledTemplates() {
            return this.mFaceManagerHAL.hasEnrolledTemplates();
        }

        public boolean hasEnrolledTemplates(int userId) {
            return this.mFaceManagerHAL.hasEnrolledTemplates(userId);
        }

        public void authenticate(
                android.hardware.biometrics.CryptoObject crypto,
                CancellationSignal cancel,
                Executor executor,
                BiometricAuthenticator.AuthenticationCallback callback) {
            Log.e(SemBioFaceManager.TAG, "authenticate: No impl");
        }

        public List<SemBioFace> hGetEnrolledFaces(int userId) {
            if (this.mHasFaceHAL) {
                List<Face> faceList = this.mFaceManagerHAL.getEnrolledFaces(userId);
                List<SemBioFace> retFaceList = new ArrayList<>();
                if (faceList != null) {
                    for (Face face : faceList) {
                        retFaceList.add(new SemBioFace(face));
                    }
                }
                return retFaceList;
            }
            return SemBioFaceManager.this.getEnrolledFaces(userId);
        }

        /* renamed from: halCancelAuthentication, reason: merged with bridge method [inline-methods] */
        public void lambda$hAuthenticate$0() {
            if (this.mHasFaceHAL && this.mServiceHAL != null) {
                try {
                    this.mServiceHAL.cancelAuthentication(
                            SemBioFaceManager.this.mToken,
                            SemBioFaceManager.this.mContext.getOpPackageName(),
                            SemBioFaceManager.this.mAuthRequestId);
                } catch (RemoteException e) {
                    Log.e(SemBioFaceManager.TAG, "halCancelAuthentication: ", e);
                }
            }
        }

        public void hAuthenticate(
                CryptoObject crypto,
                CancellationSignal cancel,
                int flags,
                AuthenticationCallback callback,
                Handler handler,
                int userId,
                Bundle b) {
            Bundle b2;
            if (!this.mHasFaceHAL) {
                Log.w(SemBioFaceManager.TAG, "hAuthenticate: Not support Face HAL");
                sendAuthError(callback, 1);
            } else {
                if (callback == null) {
                    throw new IllegalArgumentException("Must supply an authentication callback");
                }
                if (cancel != null) {
                    if (!cancel.isCanceled()) {
                        cancel.setOnCancelListener(
                                new CancellationSignal
                                        .OnCancelListener() { // from class:
                                                              // com.samsung.android.bio.face.SemBioFaceManager$FaceManagerCompat$$ExternalSyntheticLambda0
                                    @Override // android.os.CancellationSignal.OnCancelListener
                                    public final void onCancel() {
                                        SemBioFaceManager.FaceManagerCompat.this
                                                .lambda$hAuthenticate$0();
                                    }
                                });
                    } else {
                        Log.w(SemBioFaceManager.TAG, "authentication already canceled");
                        return;
                    }
                }
                if (this.mServiceHAL != null) {
                    SemBioFaceManager.this.useHandler(handler);
                    SemBioFaceManager.this.mAuthenticationCallback = callback;
                    SemBioFaceManager.this.mCryptoObject = crypto;
                    long sessionId =
                            SemBioFaceManager.this.mCryptoObject != null
                                    ? SemBioFaceManager.this.mCryptoObject.getOpId()
                                    : 0L;
                    byte[] fidoRequestData = crypto != null ? crypto.getFidoRequestData() : null;
                    if (b != null) {
                        b2 = b;
                    } else {
                        b2 = new Bundle();
                    }
                    SemBioFaceManager.setExtraInfo(SemBioFaceManager.this.mContext, b2);
                    try {
                        try {
                            Trace.beginSection("SemBioFaceManager#hAuthenticate");
                        } catch (Exception e) {
                            e = e;
                        } catch (Throwable th) {
                            th = th;
                            Trace.endSection();
                            throw th;
                        }
                        try {
                            FaceAuthenticateOptions options =
                                    new FaceAuthenticateOptions.Builder()
                                            .setUserId(userId)
                                            .setOpPackageName(
                                                    SemBioFaceManager.this.mContext
                                                            .getOpPackageName())
                                            .build();
                            SemBioFaceManager.this.mAuthRequestId =
                                    this.mServiceHAL.semAuthenticate(
                                            SemBioFaceManager.this.mToken,
                                            sessionId,
                                            this.mServiceReceiverHAL,
                                            options,
                                            b2,
                                            fidoRequestData);
                        } catch (Exception e2) {
                            e = e2;
                            Log.w(SemBioFaceManager.TAG, "hAuthenticate: " + e.getMessage());
                            sendAuthError(callback, 5);
                            Trace.endSection();
                        }
                        Trace.endSection();
                    } catch (Throwable th2) {
                        th = th2;
                        Trace.endSection();
                        throw th;
                    }
                }
            }
        }

        public int hGetSecurityLevel(boolean isKeyguard) {
            if (this.mHasFaceHAL) {
                try {
                    if (this.mServiceHAL != null) {
                        return this.mServiceHAL.semGetSecurityLevel(isKeyguard);
                    }
                    return 0;
                } catch (Exception e) {
                    Log.w(SemBioFaceManager.TAG, "hGetSecurityLevel: " + e.getMessage());
                    return 0;
                }
            }
            return 0;
        }

        private void sendAuthError(final AuthenticationCallback callback, final int errCode) {
            if (SemBioFaceManager.this.mHandler != null) {
                SemBioFaceManager.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.bio.face.SemBioFaceManager$FaceManagerCompat$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemBioFaceManager.AuthenticationCallback.this.onAuthenticationError(
                                        errCode, null);
                            }
                        });
            }
        }

        private void initHAL() {
            this.mServiceReceiverHAL =
                    new IFaceServiceReceiver
                            .Stub() { // from class:
                                      // com.samsung.android.bio.face.SemBioFaceManager.FaceManagerCompat.1
                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onEnrollResult(Face face, int remaining) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onAcquired(int acquireInfo, int vendorCode) {
                            String helpMsg =
                                    FaceManager.getHelpMessage(
                                            SemBioFaceManager.this.mContext,
                                            acquireInfo,
                                            vendorCode);
                            int acquireInfo2 =
                                    SemBioFaceManager.getSepMappedAcquiredInfo(
                                            acquireInfo, vendorCode);
                            Log.d(SemBioFaceManager.TAG, "help = " + helpMsg);
                            SemBioFaceManager.this
                                    .mHandler
                                    .obtainMessage(101, acquireInfo2, 0, helpMsg)
                                    .sendToTarget();
                        }

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onAuthenticationSucceeded(
                                Face face, int userId, boolean isStrongBiometric) {
                            AuthenticationResult result =
                                    new AuthenticationResult(
                                            SemBioFaceManager.this.mCryptoObject,
                                            face == null ? null : new SemBioFace(face),
                                            userId,
                                            isStrongBiometric);
                            SemBioFaceManager.this
                                    .mHandler
                                    .obtainMessage(102, result)
                                    .sendToTarget();
                        }

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onFaceDetected(
                                int sensorId, int userId, boolean isStrongBiometric) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onAuthenticationFailed() {
                            SemBioFaceManager.this.mHandler.obtainMessage(103).sendToTarget();
                        }

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onError(int error, int vendorCode) {
                            String errMsg =
                                    FaceManager.getErrorString(
                                            SemBioFaceManager.this.mContext, error, vendorCode);
                            SemBioFaceManager.this
                                    .mHandler
                                    .obtainMessage(
                                            104,
                                            SemBioFaceManager.getSepMappedError(error, vendorCode),
                                            0,
                                            errMsg)
                                    .sendToTarget();
                        }

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onRemoved(Face face, int remaining) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onFeatureSet(boolean success, int feature) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onFeatureGet(boolean success, int[] feature, boolean[] value) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onChallengeGenerated(
                                int sensorId, int userId, long challenge) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onAuthenticationFrame(FaceAuthenticationFrame frame) {
                            onAcquired(
                                    frame.getData().getAcquiredInfo(),
                                    frame.getData().getVendorCode());
                        }

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onEnrollmentFrame(FaceEnrollFrame frame) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onSemAuthenticationSucceeded(
                                Face face,
                                int userId,
                                boolean isStrongBiometric,
                                byte[] fidoResultData) {
                            if (SemBioFaceManager.this.mCryptoObject != null) {
                                SemBioFaceManager.this.mCryptoObject.setFidoResultData(
                                        fidoResultData);
                            }
                            onAuthenticationSucceeded(face, userId, isStrongBiometric);
                        }

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onSemAuthenticationSucceededWithBundle(
                                Face face, int userId, boolean isStrongBiometric, Bundle b) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onSemImageProcessed(
                                byte[] data,
                                int width,
                                int height,
                                int orientation,
                                int imageFormat,
                                Bundle b) {}

                        @Override // android.hardware.face.IFaceServiceReceiver
                        public void onSemStatusUpdate(int status, String msg) {}
                    };
        }
    }
}
