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
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes6.dex */
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
    public static final int PRIVILEGED_FLAG_USE_SIDE_FPS_INDICATOR = 64;
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
        public void onChallengeGenerated(int i, int i2, long j) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fingerprint, int i) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int i, int i2, boolean z) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(Fingerprint fingerprint, int i) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int i) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int i) {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int i, int i2) {
            SemFingerprintManager.this.mHandler.obtainMessage(100, i, i2).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) {
            SemFingerprintManager.this.mHandler.obtainMessage(101, i, 0, fingerprint).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() {
            SemFingerprintManager.this.mHandler.obtainMessage(102).sendToTarget();
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int i, int i2) {
            int i3 = 7;
            if (i != 7) {
                i3 = 9;
                if (i != 9) {
                    i3 = 10;
                    if (i != 10) {
                        i3 = i;
                    }
                }
            }
            SemFingerprintManager.this.mHandler.obtainMessage(103, i3, 0, FingerprintManager.getErrorString(SemFingerprintManager.this.mContext, i, i2)).sendToTarget();
        }
    };

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

    private int convertAcquiredCode(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        i2 = 5;
                        if (i != 5) {
                            return i;
                        }
                    }
                }
            }
        }
        return i2;
    }

    public boolean hasDisabledFingerprints() {
        return false;
    }

    private class MyHandler extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Slog.i(SemFingerprintManager.TAG, "handleMessage = " + message.what + ", " + message.arg1 + ", " + message.arg2);
            switch (message.what) {
                case 100:
                    SemFingerprintManager.this.sendAcquiredResult(message.arg1, message.arg2);
                    break;
                case 101:
                    SemFingerprintManager.this.sendAuthenticatedSucceeded((Fingerprint) message.obj, message.arg1, null);
                    break;
                case 102:
                    SemFingerprintManager.this.sendAuthenticatedFailed();
                    break;
                case 103:
                    SemFingerprintManager.this.sendErrorResult(message.arg1, (String) message.obj);
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

        public CryptoObject(Signature signature, byte[] bArr) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(signature);
            this.mFidoRequestData = bArr;
        }

        public CryptoObject(Cipher cipher, byte[] bArr) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(cipher);
            this.mFidoRequestData = bArr;
        }

        public CryptoObject(Mac mac, byte[] bArr) {
            this.mBioCryptoObject = new android.hardware.biometrics.CryptoObject(mac);
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

        /* JADX INFO: Access modifiers changed from: private */
        public long getOpId() {
            return this.mBioCryptoObject.getOpId();
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
        private final CryptoObject mCryptoObject;
        private final Fingerprint mFingerprint;

        public AuthenticationResult(CryptoObject cryptoObject, Fingerprint fingerprint) {
            this.mCryptoObject = cryptoObject;
            this.mFingerprint = fingerprint;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Fingerprint getFingerprint() {
            return this.mFingerprint;
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

        public int getSensorType() {
            return 3;
        }

        private Characteristics(FingerprintManager fingerprintManager) {
            this.mFingerprintManager = fingerprintManager;
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
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FingerprintManager.class);
        if (fingerprintManager != null) {
            return new SemFingerprintManager(context, fingerprintManager);
        }
        return null;
    }

    private SemFingerprintManager(Context context, FingerprintManager fingerprintManager) {
        this.mContext = context;
        this.mHandler = new MyHandler(context);
        this.mFingerprintManager = fingerprintManager;
        this.mService = fingerprintManager.semGetService();
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler, int i, Bundle bundle) {
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("Must supply an authentication callback");
        }
        if (cancellationSignal.isCanceled()) {
            Slog.w(TAG, "authentication : already canceled");
            handleDefaultError(authenticationCallback);
            return;
        }
        if (this.mService == null) {
            Slog.w(TAG, "authentication : Service is NULL");
            handleDefaultError(authenticationCallback);
            return;
        }
        try {
            useHandler(handler);
            this.mAuthenticationCallback = authenticationCallback;
            this.mCryptoObject = cryptoObject;
            long opId = cryptoObject != null ? cryptoObject.getOpId() : 0L;
            Bundle bundle2 = bundle == null ? new Bundle() : bundle;
            setExtraInfo(this.mContext, bundle2);
            final long semAuthenticate = this.mService.semAuthenticate(this.mToken, opId, this.mServiceReceiver, new FingerprintAuthenticateOptions.Builder().setSensorId(-1).setUserId(i).setOpPackageName(this.mContext.getOpPackageName()).setAttributionTag(this.mContext.getAttributionTag()).build(), bundle2);
            if (semAuthenticate < 0) {
                this.mHandler.obtainMessage(103, 5, 0, FingerprintManager.getErrorString(this.mContext, 5, 0)).sendToTarget();
            }
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager$$ExternalSyntheticLambda1
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    SemFingerprintManager.this.lambda$authenticate$0(semAuthenticate);
                }
            });
        } catch (RemoteException e) {
            Slog.w(TAG, "Remote exception while authenticating: ", e);
            handleDefaultError(authenticationCallback);
        }
    }

    public Characteristics getCharacteristics() {
        return new Characteristics(this.mFingerprintManager);
    }

    public List<String> getEnrolledFingerprintNames() {
        checkPermission(Manifest.permission.BIOMETRICS_PRIVILEGED);
        List<Fingerprint> enrolledFingerprints = this.mFingerprintManager.getEnrolledFingerprints();
        if (enrolledFingerprints == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(enrolledFingerprints.size());
        Iterator<Fingerprint> it = enrolledFingerprints.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName().toString());
        }
        return arrayList;
    }

    public boolean hasEnrolledFingerprints() {
        return this.mFingerprintManager.hasEnrolledFingerprints();
    }

    public boolean hasEnrolledFingerprints(int i) {
        return this.mFingerprintManager.hasEnrolledFingerprints(i);
    }

    public boolean hasFeature(int i) {
        return this.mFingerprintManager.semHasFeature(i);
    }

    public static void setExtraInfo(Context context, Bundle bundle) {
        int i;
        if (bundle == null) {
            return;
        }
        try {
            i = context.getDisplayId();
        } catch (Exception e) {
            Slog.w(TAG, "setExtraInfo: " + e.getMessage());
            i = 0;
        }
        bundle.putInt(EXTRA_KEY_DISPLAY_ID, i);
        if (context instanceof Activity) {
            bundle.putInt(EXTRA_KEY_TASK_ID, ((Activity) context).getTaskId());
        }
    }

    public static String getProductFeatureValue(Context context) {
        if (context.checkSelfPermission(Manifest.permission.BIOMETRICS_PRIVILEGED) == -1) {
            throw new SecurityException("Must have com.samsung.android.permission.BIOMETRICS_PRIVILEGED permission.");
        }
        return "google_touch_display_ultrasonic";
    }

    public static int getMaxTemplateNumberFromSPF() {
        String[] split = "google_touch_display_ultrasonic".split(",");
        int length = split.length;
        for (int i = 0; i < length; i++) {
            String str = split[i];
            if (str.startsWith("settings=")) {
                try {
                    return Integer.parseInt(str.substring(9));
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

    private void checkPermission(String str) {
        if (this.mContext.checkSelfPermission(str) != -1) {
            return;
        }
        throw new SecurityException("Must have " + str + " permission.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelAuthentication, reason: merged with bridge method [inline-methods] */
    public void lambda$authenticate$0(long j) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), j);
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception while canceling authentication : " + e.getMessage());
            }
        }
    }

    private void handleDefaultError(final AuthenticationCallback authenticationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.bio.fingerprint.SemFingerprintManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFingerprintManager.this.lambda$handleDefaultError$1(authenticationCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDefaultError$1(AuthenticationCallback authenticationCallback) {
        authenticationCallback.onAuthenticationError(2, FingerprintManager.getErrorString(this.mContext, 2, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorResult(int i, String str) {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            if (str == null) {
                str = "";
            }
            authenticationCallback.onAuthenticationError(i, str);
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
    public void sendAcquiredResult(int i, int i2) {
        if (this.mAuthenticationCallback != null) {
            String acquiredString = FingerprintManager.getAcquiredString(this.mContext, i, i2);
            int convertAcquiredCode = convertAcquiredCode(i);
            if (i == 6) {
                if (acquiredString == null) {
                    i = i2;
                }
                convertAcquiredCode = i;
            }
            if (acquiredString == null) {
                this.mAuthenticationCallback.onAuthenticationAcquired(convertAcquiredCode);
            } else {
                this.mAuthenticationCallback.onAuthenticationHelp(convertAcquiredCode, acquiredString);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthenticatedSucceeded(Fingerprint fingerprint, int i, Bundle bundle) {
        if (this.mAuthenticationCallback != null) {
            CryptoObject cryptoObject = this.mCryptoObject;
            if (cryptoObject != null && bundle != null) {
                cryptoObject.setFidoResultData(bundle.getByteArray("fidoResult"));
            }
            this.mAuthenticationCallback.onAuthenticationSucceeded(new AuthenticationResult(this.mCryptoObject, fingerprint));
        }
    }
}
