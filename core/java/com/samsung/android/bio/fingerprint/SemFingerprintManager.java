package com.samsung.android.bio.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes5.dex */
public class SemFingerprintManager {
    public static final String EXTRA_KEY_ALLOW_AUTH_EVEN_IF_ENCRYPTED_OR_LOCKDOWN = "EXTRA_KEY_ALLOW_EVEN_IF_ENCRYPTED_OR_LOCKDOWN";
    public static final String EXTRA_KEY_AUTH_FLAG = "EXTRA_KEY_AUTH_FLAG";
    public static final String EXTRA_KEY_DISPLAY_ID = "EXTRA_KEY_DISPLAY_ID";
    public static final String EXTRA_KEY_ICON_COLOR = "EXTRA_KEY_ICON_COLOR";
    public static final String EXTRA_KEY_ICON_CONTAINER_COLOR = "EXTRA_KEY_ICON_CONTAINER_COLOR";
    public static final String EXTRA_KEY_PRIVILEGED_FLAG = "sem_privileged_attr";
    public static final String EXTRA_KEY_TASK_ID = "EXTRA_KEY_TASK_ID";
    public static final int FEATURE_GESTURE = 1;
    public static final int FINGERPRINT_ACQUIRED_GOOD = 0;
    public static final int FINGERPRINT_ACQUIRED_IMAGER_DIRTY = 3;
    public static final int FINGERPRINT_ACQUIRED_INSUFFICIENT = 2;
    public static final int FINGERPRINT_ACQUIRED_PARTIAL = 1;
    public static final int FINGERPRINT_ACQUIRED_TOO_FAST = 5;
    public static final int FINGERPRINT_ACQUIRED_TOO_SLOW = 4;
    public static final int FINGERPRINT_ACQUIRED_VENDOR = 6;
    public static final int FINGERPRINT_ERROR_CANCELED = 5;
    public static final int FINGERPRINT_ERROR_HW_UNAVAILABLE = 1;
    public static final int FINGERPRINT_ERROR_LOCKOUT = 7;
    public static final int FINGERPRINT_ERROR_LOCKOUT_PERMANENT = 9;
    public static final int FINGERPRINT_ERROR_TEMPLATE_CORRUPTED = 1001;
    public static final int FINGERPRINT_ERROR_TIMEOUT = 3;
    public static final int FINGERPRINT_ERROR_UNABLE_TO_PROCESS = 2;
    public static final int FINGERPRINT_ERROR_USER_CANCELED = 10;
    private static final int MSG_ACQUIRED = 100;
    private static final int MSG_AUTHENTICATION_FAILED = 102;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 101;
    private static final int MSG_ERROR = 103;
    public static final int PRIVILEGED_FLAG_ALLOW_BACKGROUND = 4;
    public static final int PRIVILEGED_FLAG_AVOID_LOCKOUT = 1;
    public static final int PRIVILEGED_FLAG_HIDE_AUTHENTICATION_GUIDE_LAYER = 16;
    public static final int PRIVILEGED_FLAG_NO_VIBRATION_EFFECT = 8;
    public static final int PRIVILEGED_FLAG_RECEIVE_VENDOR_EVENT = 2;
    public static final int PRIVILEGED_FLAG_USE_KEYGUARD_ICON = 32;
    private static final String TAG = "SemFingerprintManager";
    private AuthenticationCallback mAuthenticationCallback;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private final FingerprintManager mFingerprintManager;
    private Handler mHandler;
    private final IFingerprintService mService;
    private final IBinder mToken = new Binder();
    private final IFingerprintServiceReceiver mServiceReceiver = new IFingerprintServiceReceiver.Stub() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager.1
        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int acquireInfo, int vendorCode) {
            SemFingerprintManager.this.mHandler.obtainMessage(100, acquireInfo, vendorCode).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(Fingerprint fp, int userId, boolean isStrongBiometric) {
            SemFingerprintManager.this.mHandler.obtainMessage(101, userId, 0, fp).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
            SemFingerprintManager.this.mHandler.obtainMessage(102).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int fpErrorCode, int vendor2) {
            int errorCode = fpErrorCode;
            switch (fpErrorCode) {
                case 7:
                    errorCode = 7;
                    break;
                case 9:
                    errorCode = 9;
                    break;
                case 10:
                    errorCode = 10;
                    break;
            }
            SemFingerprintManager.this.mHandler.obtainMessage(103, errorCode, 0, FingerprintManager.getErrorString(SemFingerprintManager.this.mContext, fpErrorCode, vendor2)).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fp, int remaining) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(Fingerprint fp, int remaining) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(int sensorId, int userId, long challenge) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int sensorId) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int sensorId) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() {
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtraKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintAcquired {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FingerprintError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivilegedFlag {
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
            Slog.i(SemFingerprintManager.TAG, "handleMessage = " + msg.what + ", " + msg.arg1 + ", " + msg.arg2);
            switch (msg.what) {
                case 100:
                    SemFingerprintManager.this.sendAcquiredResult(msg.arg1, msg.arg2);
                    break;
                case 101:
                    SemFingerprintManager.this.sendAuthenticatedSucceeded((Fingerprint) msg.obj, msg.arg1, null);
                    break;
                case 102:
                    SemFingerprintManager.this.sendAuthenticatedFailed();
                    break;
                case 103:
                    SemFingerprintManager.this.sendErrorResult(msg.arg1, (String) msg.obj);
                    break;
                default:
                    Slog.w(SemFingerprintManager.TAG, "handleMessage : Unknown msg");
                    break;
            }
        }
    }

    public static final class CryptoObject {
        private final android.hardware.biometrics.CryptoObject mBioCryptoObject;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData = null;

        public CryptoObject(Signature signature, byte[] fidoRequestData) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(signature);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Cipher cipher, byte[] fidoRequestData) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(cipher);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Mac mac, byte[] fidoRequestData) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(mac);
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

        /* JADX INFO: Access modifiers changed from: private */
        public long getOpId() {
            return this.mBioCryptoObject.getOpId();
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
        private final CryptoObject mCryptoObject;
        private final Fingerprint mFingerprint;

        public AuthenticationResult(CryptoObject crypto, Fingerprint fp) {
            this.mCryptoObject = crypto;
            this.mFingerprint = fp;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Fingerprint getFingerprint() {
            return this.mFingerprint;
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errorCode, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationAcquired(int event) {
        }
    }

    public static class Characteristics {
        public static final int SENSOR_POSITION_HOME_KEY = 1;
        public static final int SENSOR_POSITION_IN_DISPLAY = 2;
        public static final int SENSOR_POSITION_POWER_KEY = 4;
        public static final int SENSOR_POSITION_REAR = 3;
        public static final int SENSOR_TYPE_CAPACITANCE = 1;
        public static final int SENSOR_TYPE_OPTICAL = 2;
        public static final int SENSOR_TYPE_ULTRASONIC = 3;
        private static final String mConfig = "google_touch_display_ultrasonic";
        private final FingerprintManager mFingerprintManager;

        private Characteristics(FingerprintManager fm) {
            this.mFingerprintManager = fm;
        }

        public int getSensorType() {
            if (mConfig.contains("ultrasonic")) {
                return 3;
            }
            if (mConfig.contains("optical")) {
                return 2;
            }
            return 1;
        }

        public int getSensorPosition() {
            return FingerprintManager.semGetSensorPosition();
        }

        public int getMaxFingerprintCount() {
            return this.mFingerprintManager.semGetMaxEnrollmentNumber();
        }

        public Rect getSensorAreaInDisplay() {
            return this.mFingerprintManager.semGetFingerIconRectInDisplay();
        }
    }

    public static SemFingerprintManager createInstance(Context context) {
        FingerprintManager fpMgr = (FingerprintManager) context.getSystemService(FingerprintManager.class);
        if (fpMgr != null) {
            return new SemFingerprintManager(context, fpMgr);
        }
        return null;
    }

    private SemFingerprintManager(Context context, FingerprintManager fm) {
        this.mContext = context;
        this.mHandler = new MyHandler(context);
        this.mFingerprintManager = fm;
        this.mService = this.mFingerprintManager.semGetService();
    }

    public void authenticate(CryptoObject crypto, CancellationSignal cancel, AuthenticationCallback callback, Handler handler, int userId, Bundle attr) {
        Bundle attr2;
        final long authId;
        if (callback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancel.isCanceled()) {
            Slog.w(TAG, "authentication : already canceled");
            handleDefaultError(callback);
            return;
        }
        if (this.mService != null) {
            try {
                useHandler(handler);
                this.mAuthenticationCallback = callback;
                this.mCryptoObject = crypto;
                long operationId = crypto != null ? crypto.getOpId() : 0L;
                if (attr != null) {
                    attr2 = attr;
                } else {
                    attr2 = new Bundle();
                }
                try {
                    setExtraInfo(this.mContext, attr2);
                    try {
                        FingerprintAuthenticateOptions options = new FingerprintAuthenticateOptions.Builder().setSensorId(-1).setUserId(userId).setOpPackageName(this.mContext.getOpPackageName()).setAttributionTag(this.mContext.getAttributionTag()).build();
                        authId = this.mService.semAuthenticate(this.mToken, operationId, this.mServiceReceiver, options, attr2);
                        if (authId < 0) {
                            this.mHandler.obtainMessage(103, 5, 0, FingerprintManager.getErrorString(this.mContext, 5, 0)).sendToTarget();
                        }
                    } catch (RemoteException e) {
                        e = e;
                    }
                    try {
                        cancel.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager$$ExternalSyntheticLambda1
                            @Override // android.os.CancellationSignal.OnCancelListener
                            public final void onCancel() {
                                SemFingerprintManager.this.lambda$authenticate$0(authId);
                            }
                        });
                    } catch (RemoteException e2) {
                        e = e2;
                        Slog.w(TAG, "Remote exception while authenticating: ", e);
                        handleDefaultError(callback);
                    }
                } catch (RemoteException e3) {
                    e = e3;
                }
            } catch (RemoteException e4) {
                e = e4;
            }
        } else {
            Slog.w(TAG, "authentication : Service is NULL");
            handleDefaultError(callback);
        }
    }

    public Characteristics getCharacteristics() {
        return new Characteristics(this.mFingerprintManager);
    }

    public boolean hasDisabledFingerprints() {
        return false;
    }

    public List<String> getEnrolledFingerprintNames() {
        checkPermission(Manifest.permission.BIOMETRICS_PRIVILEGED);
        List<Fingerprint> fpList = this.mFingerprintManager.getEnrolledFingerprints();
        if (fpList == null) {
            return new ArrayList();
        }
        ArrayList<String> nameList = new ArrayList<>(fpList.size());
        for (Fingerprint f : fpList) {
            nameList.add(f.getName().toString());
        }
        return nameList;
    }

    public boolean hasEnrolledFingerprints() {
        return this.mFingerprintManager.hasEnrolledFingerprints();
    }

    public boolean hasEnrolledFingerprints(int userId) {
        return this.mFingerprintManager.hasEnrolledFingerprints(userId);
    }

    public boolean hasFeature(int feature) {
        return this.mFingerprintManager.semHasFeature(feature);
    }

    public static void setExtraInfo(Context context, Bundle b) {
        int displayId;
        if (b == null) {
            return;
        }
        try {
            displayId = context.getDisplayId();
        } catch (Exception e) {
            Slog.w(TAG, "setExtraInfo: " + e.getMessage());
            displayId = 0;
        }
        b.putInt(EXTRA_KEY_DISPLAY_ID, displayId);
        if (context instanceof Activity) {
            b.putInt(EXTRA_KEY_TASK_ID, ((Activity) context).getTaskId());
        }
    }

    public static String getProductFeatureValue(Context context) {
        if (context.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
            throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
        }
        return "google_touch_display_ultrasonic";
    }

    public static int getMaxTemplateNumberFromSPF() {
        String[] params = "google_touch_display_ultrasonic".split(",");
        int length = params.length;
        for (int i = 0; i < length; i++) {
            String p = params[i];
            if (p.startsWith("settings=")) {
                try {
                    return Integer.parseInt(p.substring("settings=".length()));
                } catch (Exception e) {
                    Slog.e(TAG, "getMaxTemplateNumberFromSPF: failed to read sensor config", e);
                }
            }
        }
        return 4;
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    private void checkPermission(String per) {
        if (this.mContext.checkSelfPermission(per) == -1) {
            throw new SecurityException("Must have " + per + " permission.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelAuthentication, reason: merged with bridge method [inline-methods] */
    public void lambda$authenticate$0(long requestId) {
        if (this.mService != null) {
            try {
                this.mService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), requestId);
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception while canceling authentication : " + e.getMessage());
            }
        }
    }

    private void handleDefaultError(final AuthenticationCallback callback) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFingerprintManager.this.lambda$handleDefaultError$1(callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDefaultError$1(AuthenticationCallback callback) {
        callback.onAuthenticationError(2, FingerprintManager.getErrorString(this.mContext, 2, 0));
    }

    private int convertAcquiredCode(int code) {
        switch (code) {
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
            default:
                return code;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(int errMsgId, String errMsg) {
        if (this.mAuthenticationCallback != null) {
            if (errMsg == null) {
                errMsg = "";
            }
            this.mAuthenticationCallback.onAuthenticationError(errMsgId, errMsg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedFailed() {
        if (this.mAuthenticationCallback != null) {
            this.mAuthenticationCallback.onAuthenticationFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAcquiredResult(int acquireInfo, int vendor2) {
        if (this.mAuthenticationCallback != null) {
            String helpMsg = FingerprintManager.getAcquiredString(this.mContext, acquireInfo, vendor2);
            int clientInfo = convertAcquiredCode(acquireInfo);
            if (acquireInfo == 6) {
                clientInfo = helpMsg == null ? vendor2 : acquireInfo;
            }
            if (helpMsg == null) {
                this.mAuthenticationCallback.onAuthenticationAcquired(clientInfo);
            } else {
                this.mAuthenticationCallback.onAuthenticationHelp(clientInfo, helpMsg);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(Fingerprint fp, int userId, Bundle data) {
        if (this.mAuthenticationCallback != null) {
            if (this.mCryptoObject != null && data != null) {
                this.mCryptoObject.setFidoResultData(data.getByteArray("fidoResult"));
            }
            AuthenticationResult result = new AuthenticationResult(this.mCryptoObject, fp);
            this.mAuthenticationCallback.onAuthenticationSucceeded(result);
        }
    }
}
