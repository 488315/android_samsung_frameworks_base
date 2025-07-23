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
import com.samsung.android.bio.face.SemBioFaceManager;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes6.dex */
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
    public static final boolean IS_SUPPORTED_ALTERNATIVE_ENROLLMENT_AND_CLOSED_EYES_DETECTION = true;
    private IBinder mToken = new Binder();
    private long mAuthRequestId = 0;

    public static abstract class AuthenticationCallback {
        public void onAuthenticationAcquired(int i) {
        }

        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static abstract class ChallengeCallback {
        public void onPreEnroll(long j) {
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
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(SemBioFace semBioFace, int i, CharSequence charSequence) {
        }

        public void onRemovalSucceeded(SemBioFace semBioFace) {
        }
    }

    public void authenticate(CancellationSignal cancellationSignal, Handler handler, int i, Surface surface, byte[] bArr, AuthenticationCallback authenticationCallback) {
    }

    public String getTaVersionCode() {
        return null;
    }

    public void resume() {
    }

    public static final class CryptoObject {
        private BiometricPrompt.CryptoObject mBioCryptoObject;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData;

        CryptoObject(BiometricPrompt.CryptoObject cryptoObject) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = cryptoObject;
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

        public CryptoObject(Signature signature, byte[] bArr) {
            this.mFidoResultData = null;
            if (signature != null) {
                this.mBioCryptoObject = new BiometricPrompt.CryptoObject(signature);
            }
            this.mFidoRequestData = bArr;
        }

        public CryptoObject(Cipher cipher, byte[] bArr) {
            this.mFidoResultData = null;
            if (cipher != null) {
                this.mBioCryptoObject = new BiometricPrompt.CryptoObject(cipher);
            }
            this.mFidoRequestData = bArr;
        }

        public CryptoObject(Mac mac, byte[] bArr) {
            this.mFidoResultData = null;
            if (mac != null) {
                this.mBioCryptoObject = new BiometricPrompt.CryptoObject(mac);
            }
            this.mFidoRequestData = bArr;
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
        public void setFidoResultData(byte[] bArr) {
            this.mFidoResultData = bArr;
        }
    }

    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private SemBioFace mFace;
        private boolean mIsStrongBiometric;
        private int mUserId;

        public Bundle getSecureInfo() {
            return null;
        }

        public AuthenticationResult(CryptoObject cryptoObject, SemBioFace semBioFace, int i, boolean z) {
            this.mCryptoObject = cryptoObject;
            this.mFace = semBioFace;
            this.mUserId = i;
            this.mIsStrongBiometric = z;
        }

        public AuthenticationResult(CryptoObject cryptoObject, SemBioFace semBioFace) {
            this.mCryptoObject = cryptoObject;
            this.mFace = semBioFace;
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler, View view) {
        Bundle bundle;
        if (view != null) {
            Object tag = view.getTag();
            if (tag instanceof Bundle) {
                bundle = (Bundle) tag;
                authenticate(cryptoObject, cancellationSignal, i, authenticationCallback, handler, this.mContext.getUserId(), bundle, view);
            }
        }
        bundle = null;
        authenticate(cryptoObject, cancellationSignal, i, authenticationCallback, handler, this.mContext.getUserId(), bundle, view);
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler, int i2, Bundle bundle, View view) {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            this.mFaceManagerCompat.hAuthenticate(cryptoObject, cancellationSignal, i, authenticationCallback, handler, i2, bundle);
        }
    }

    public void enroll(byte[] bArr, CancellationSignal cancellationSignal, int i, EnrollmentCallback enrollmentCallback, View view) {
        Log.e(TAG, "enroll() : this is not used");
    }

    public void enroll(byte[] bArr, CancellationSignal cancellationSignal, int i, int i2, EnrollmentCallback enrollmentCallback, Bundle bundle, View view) {
        Log.e(TAG, "enroll() : this is not used.");
    }

    public long preEnroll() {
        Log.e(TAG, "preEnroll() : this is not used");
        return 0L;
    }

    public long preEnroll(ChallengeCallback challengeCallback) {
        Log.e(TAG, "preEnroll() : this is not used.");
        return 0L;
    }

    public int postEnroll() {
        Log.e(TAG, "postEnroll() : this is not used.");
        return 0;
    }

    public void setActiveUser(int i) {
        Log.e(TAG, "setActiveUser() : this is not used");
    }

    public void remove(SemBioFace semBioFace, int i, RemovalCallback removalCallback) {
        Log.e(TAG, "remove() : this is not used");
    }

    public void remove(SemBioFace semBioFace, RemovalCallback removalCallback) {
        Log.e(TAG, "remove() : this is not used.");
    }

    public void rename(int i, int i2, String str) {
        Log.e(TAG, "rename() : this is not used");
    }

    public List<SemBioFace> getEnrolledFaces(int i) {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.hGetEnrolledFaces(i);
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

    public boolean hasEnrolledFaces(int i) {
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.hasEnrolledTemplates(i);
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
        if (this.mFaceManagerCompat.mHasFaceHAL) {
            return this.mFaceManagerCompat.hGetSecurityLevel(isKeyguard);
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getSepMappedAcquiredInfo(int r8, int r9) {
        /*
            r0 = 1009(0x3f1, float:1.414E-42)
            r1 = 1011(0x3f3, float:1.417E-42)
            r2 = 1013(0x3f5, float:1.42E-42)
            r3 = 1007(0x3ef, float:1.411E-42)
            r4 = 1015(0x3f7, float:1.422E-42)
            r5 = 2
            r6 = 3
            r7 = 7
            switch(r8) {
                case 0: goto L7f;
                case 1: goto L7e;
                case 2: goto L7e;
                case 3: goto L7d;
                case 4: goto L7b;
                case 5: goto L79;
                case 6: goto L78;
                case 7: goto L77;
                case 8: goto L76;
                case 9: goto L75;
                case 10: goto L74;
                case 11: goto L74;
                case 12: goto L73;
                case 13: goto L71;
                case 14: goto L70;
                case 15: goto L70;
                case 16: goto L6f;
                case 17: goto L6f;
                case 18: goto L6f;
                case 19: goto L6e;
                case 20: goto L6d;
                case 21: goto L6c;
                case 22: goto L2c;
                default: goto L10;
            }
        L10:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "getSepMappedAcquiredInfo: No data, "
            r0.<init>(r1)
            r0.append(r8)
            java.lang.String r1 = ", "
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            java.lang.String r0 = "SemBioFaceManager"
            android.util.Log.d(r0, r9)
            return r8
        L2c:
            r5 = 1001(0x3e9, float:1.403E-42)
            if (r9 == r5) goto L6b
            switch(r9) {
                case 1005: goto L69;
                case 1006: goto L66;
                case 1007: goto L65;
                case 1008: goto L62;
                case 1009: goto L61;
                default: goto L33;
            }
        L33:
            switch(r9) {
                case 1011: goto L60;
                case 1012: goto L5d;
                case 1013: goto L5c;
                case 1014: goto L59;
                case 1015: goto L58;
                case 1016: goto L55;
                case 1017: goto L52;
                default: goto L36;
            }
        L36:
            switch(r9) {
                case 100001: goto L4e;
                case 100002: goto L4a;
                case 100003: goto L46;
                case 100004: goto L42;
                case 100005: goto L3e;
                case 100006: goto L3a;
                default: goto L39;
            }
        L39:
            goto L6d
        L3a:
            r8 = 100006(0x186a6, float:1.40138E-40)
            return r8
        L3e:
            r8 = 100005(0x186a5, float:1.40137E-40)
            return r8
        L42:
            r8 = 100004(0x186a4, float:1.40135E-40)
            return r8
        L46:
            r8 = 100003(0x186a3, float:1.40134E-40)
            return r8
        L4a:
            r8 = 100002(0x186a2, float:1.40133E-40)
            return r8
        L4e:
            r8 = 100001(0x186a1, float:1.40131E-40)
            return r8
        L52:
            r8 = 1017(0x3f9, float:1.425E-42)
            return r8
        L55:
            r8 = 1016(0x3f8, float:1.424E-42)
            return r8
        L58:
            return r4
        L59:
            r8 = 1014(0x3f6, float:1.421E-42)
            return r8
        L5c:
            return r2
        L5d:
            r8 = 1012(0x3f4, float:1.418E-42)
            return r8
        L60:
            return r1
        L61:
            return r0
        L62:
            r8 = 1008(0x3f0, float:1.413E-42)
            return r8
        L65:
            return r3
        L66:
            r8 = 1006(0x3ee, float:1.41E-42)
            return r8
        L69:
            r8 = 4
            return r8
        L6b:
            return r5
        L6c:
            return r6
        L6d:
            return r8
        L6e:
            return r5
        L6f:
            return r7
        L70:
            return r9
        L71:
            r8 = 1
            return r8
        L73:
            return r7
        L74:
            return r5
        L75:
            return r0
        L76:
            return r1
        L77:
            return r2
        L78:
            return r3
        L79:
            r8 = 6
            return r8
        L7b:
            r8 = 5
            return r8
        L7d:
            return r4
        L7e:
            return r6
        L7f:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.bio.face.SemBioFaceManager.getSepMappedAcquiredInfo(int, int):int");
    }

    public static int getSepMappedError(int i, int i2) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 2;
            case 7:
                return 10001;
            case 8:
                switch (i2) {
                    case 1001:
                        return 1004;
                    case 1002:
                        return 2;
                    case 1003:
                        return 10003;
                    case 1004:
                        return 10005;
                    case 1005:
                        return 1005;
                    case 1006:
                        return 1006;
                    case 1007:
                        return 1007;
                    default:
                        switch (i2) {
                            case 100001:
                                return 100001;
                            case 100002:
                                return 100002;
                            case 100003:
                                return 100003;
                            default:
                                return i;
                        }
                }
            case 9:
                return 10002;
            case 10:
                return 10;
            case 11:
                return 2;
            case 12:
                return 1;
            case 13:
                return 10;
            case 14:
                return 2;
            default:
                Log.d(TAG, "getSepMappedError: No data, " + i + ", " + i2);
                return i;
        }
    }

    private class MyHandler extends Handler {
        private void sendEnrollResult(SemBioFace semBioFace, int i) {
        }

        private void sendRemovedResult(long j, int i, int i2) {
        }

        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(SemBioFaceManager.TAG, "handleMessage = " + message.what + ", " + message.arg1 + ", " + message.arg2);
            switch (message.what) {
                case 101:
                    SemBioFaceManager.this.sendAcquiredResult(message.arg1, (String) message.obj);
                    break;
                case 102:
                    SemBioFaceManager.this.sendAuthenticatedSucceeded((AuthenticationResult) message.obj);
                    break;
                case 103:
                    SemBioFaceManager.this.sendAuthenticatedFailed();
                    break;
                case 104:
                    SemBioFaceManager.this.sendErrorResult(message.arg1, (String) message.obj);
                    break;
                default:
                    Log.w(SemBioFaceManager.TAG, "handleMessage : Unknown msg");
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(int i, String str) {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationError(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(AuthenticationResult authenticationResult) {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationSucceeded(authenticationResult);
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
    public void sendAcquiredResult(int i, String str) {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationAcquired(i);
            if (str != null) {
                this.mAuthenticationCallback.onAuthenticationHelp(i, str);
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

    public static void setExtraInfo(Context context, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            bundle.putInt("DISPLAY_TYPE", context.getDisplay().getDisplayId());
        } catch (Exception e) {
            Log.w(TAG, "setExtraInfo: " + e.getMessage());
        }
    }

    private boolean isKeyguard(String str) {
        return "com.android.systemui".equals(str) || PKG_NAME_DESKTOP_KEYGUARD.equals(str);
    }

    public class FaceManagerCompat extends BiometricFaceConstants implements BiometricAuthenticator {
        private FaceManager mFaceManagerHAL;
        private boolean mHasFaceHAL;
        private IFaceService mServiceHAL;
        private IFaceServiceReceiver mServiceReceiverHAL;

        FaceManagerCompat(Context context) {
            try {
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FACE)) {
                    this.mHasFaceHAL = true;
                    this.mServiceHAL = IFaceService.Stub.asInterface(ServiceManager.getService(Context.FACE_SERVICE));
                    this.mFaceManagerHAL = new FaceManager(context, this.mServiceHAL);
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

        public boolean hasEnrolledTemplates(int i) {
            return this.mFaceManagerHAL.hasEnrolledTemplates(i);
        }

        public void authenticate(android.hardware.biometrics.CryptoObject cryptoObject, CancellationSignal cancellationSignal, Executor executor, BiometricAuthenticator.AuthenticationCallback authenticationCallback) {
            Log.e(SemBioFaceManager.TAG, "authenticate: No impl");
        }

        public List<SemBioFace> hGetEnrolledFaces(int i) {
            if (this.mHasFaceHAL) {
                List<Face> enrolledFaces = this.mFaceManagerHAL.getEnrolledFaces(i);
                ArrayList arrayList = new ArrayList();
                if (enrolledFaces != null) {
                    Iterator<Face> it = enrolledFaces.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new SemBioFace(it.next()));
                    }
                }
                return arrayList;
            }
            return SemBioFaceManager.this.getEnrolledFaces(i);
        }

        /* renamed from: halCancelAuthentication, reason: merged with bridge method [inline-methods] */
        public void lambda$hAuthenticate$0() {
            IFaceService iFaceService;
            if (!this.mHasFaceHAL || (iFaceService = this.mServiceHAL) == null) {
                return;
            }
            try {
                iFaceService.cancelAuthentication(SemBioFaceManager.this.mToken, SemBioFaceManager.this.mContext.getOpPackageName(), SemBioFaceManager.this.mAuthRequestId);
            } catch (RemoteException e) {
                Log.e(SemBioFaceManager.TAG, "halCancelAuthentication: ", e);
            }
        }

        public void hAuthenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int i, AuthenticationCallback authenticationCallback, Handler handler, int i2, Bundle bundle) {
            if (!this.mHasFaceHAL) {
                Log.w(SemBioFaceManager.TAG, "hAuthenticate: Not support Face HAL");
                sendAuthError(authenticationCallback, 1);
                return;
            }
            if (authenticationCallback == null) {
                throw new IllegalArgumentException("Must supply an authentication callback");
            }
            if (cancellationSignal != null) {
                if (cancellationSignal.isCanceled()) {
                    Log.w(SemBioFaceManager.TAG, "authentication already canceled");
                    return;
                }
                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.samsung.android.bio.face.SemBioFaceManager$FaceManagerCompat$$ExternalSyntheticLambda0
                    @Override // android.os.CancellationSignal.OnCancelListener
                    public final void onCancel() {
                        SemBioFaceManager.FaceManagerCompat.this.lambda$hAuthenticate$0();
                    }
                });
            }
            if (this.mServiceHAL != null) {
                SemBioFaceManager.this.useHandler(handler);
                SemBioFaceManager.this.mAuthenticationCallback = authenticationCallback;
                SemBioFaceManager.this.mCryptoObject = cryptoObject;
                long opId = SemBioFaceManager.this.mCryptoObject != null ? SemBioFaceManager.this.mCryptoObject.getOpId() : 0L;
                byte[] fidoRequestData = cryptoObject != null ? cryptoObject.getFidoRequestData() : null;
                Bundle bundle2 = bundle == null ? new Bundle() : bundle;
                SemBioFaceManager.setExtraInfo(SemBioFaceManager.this.mContext, bundle2);
                try {
                    try {
                        Trace.beginSection("SemBioFaceManager#hAuthenticate");
                        FaceAuthenticateOptions build = new FaceAuthenticateOptions.Builder().setUserId(i2).setOpPackageName(SemBioFaceManager.this.mContext.getOpPackageName()).build();
                        SemBioFaceManager semBioFaceManager = SemBioFaceManager.this;
                        semBioFaceManager.mAuthRequestId = this.mServiceHAL.semAuthenticate(semBioFaceManager.mToken, opId, this.mServiceReceiverHAL, build, bundle2, fidoRequestData);
                    } catch (Exception e) {
                        Log.w(SemBioFaceManager.TAG, "hAuthenticate: " + e.getMessage());
                        sendAuthError(authenticationCallback, 5);
                    }
                } finally {
                    Trace.endSection();
                }
            }
        }

        public int hGetSecurityLevel(boolean z) {
            if (!this.mHasFaceHAL) {
                return 0;
            }
            try {
                IFaceService iFaceService = this.mServiceHAL;
                if (iFaceService != null) {
                    return iFaceService.semGetSecurityLevel(z);
                }
                return 0;
            } catch (Exception e) {
                Log.w(SemBioFaceManager.TAG, "hGetSecurityLevel: " + e.getMessage());
                return 0;
            }
        }

        private void sendAuthError(final AuthenticationCallback authenticationCallback, final int i) {
            if (SemBioFaceManager.this.mHandler != null) {
                SemBioFaceManager.this.mHandler.post(new Runnable() { // from class: com.samsung.android.bio.face.SemBioFaceManager$FaceManagerCompat$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemBioFaceManager.AuthenticationCallback.this.onAuthenticationError(i, null);
                    }
                });
            }
        }

        private void initHAL() {
            this.mServiceReceiverHAL = new IFaceServiceReceiver.Stub() { // from class: com.samsung.android.bio.face.SemBioFaceManager.FaceManagerCompat.1
                @Override // android.hardware.face.IFaceServiceReceiver
                public void onChallengeGenerated(int i, int i2, long j) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onEnrollResult(Face face, int i) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onFaceDetected(int i, int i2, boolean z) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onFeatureSet(boolean z, int i) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onRemoved(Face face, int i) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onSemStatusUpdate(int i, String str) {
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onAcquired(int i, int i2) {
                    String helpMessage = FaceManager.getHelpMessage(SemBioFaceManager.this.mContext, i, i2);
                    int sepMappedAcquiredInfo = SemBioFaceManager.getSepMappedAcquiredInfo(i, i2);
                    Log.d(SemBioFaceManager.TAG, "help = " + helpMessage);
                    SemBioFaceManager.this.mHandler.obtainMessage(101, sepMappedAcquiredInfo, 0, helpMessage).sendToTarget();
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onAuthenticationSucceeded(Face face, int i, boolean z) {
                    SemBioFaceManager.this.mHandler.obtainMessage(102, new AuthenticationResult(SemBioFaceManager.this.mCryptoObject, face == null ? null : new SemBioFace(face), i, z)).sendToTarget();
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onAuthenticationFailed() {
                    SemBioFaceManager.this.mHandler.obtainMessage(103).sendToTarget();
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onError(int i, int i2) {
                    String errorString = FaceManager.getErrorString(SemBioFaceManager.this.mContext, i, i2);
                    SemBioFaceManager.this.mHandler.obtainMessage(104, SemBioFaceManager.getSepMappedError(i, i2), 0, errorString).sendToTarget();
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) {
                    onAcquired(faceAuthenticationFrame.getData().getAcquiredInfo(), faceAuthenticationFrame.getData().getVendorCode());
                }

                @Override // android.hardware.face.IFaceServiceReceiver
                public void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) {
                    if (SemBioFaceManager.this.mCryptoObject != null) {
                        SemBioFaceManager.this.mCryptoObject.setFidoResultData(bArr);
                    }
                    onAuthenticationSucceeded(face, i, z);
                }
            };
        }
    }
}
