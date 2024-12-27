package com.samsung.android.biometrics;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated(forRemoval = true, since = "15.5")
public class SemBiometricsManager {
    public static final int ACQUIRED_GOOD = 0;
    public static final int ACQUIRED_HOLD_PHONE_CLOSER = 6;
    public static final int ACQUIRED_HOLD_PHONE_FARTHER_AWAY = 7;
    public static final int ACQUIRED_LIGHT_REFLECTION = 1;
    public static final int ACQUIRED_LOW_LUMINANCE = 9;
    public static final int ACQUIRED_MOVE_DOWN = 3;
    public static final int ACQUIRED_MOVE_LEFT = 4;
    public static final int ACQUIRED_MOVE_RIGHT = 5;
    public static final int ACQUIRED_MOVE_UP = 2;
    public static final int ACQUIRED_OPEN_EYES_FULLY = 8;
    public static final int ACQUIRED_REVERSE_ORIENTATION = 10;
    public static final int ERROR_CANCELED = 5;
    public static final int ERROR_FACE_CAMERA_FAILURE = 6;
    public static final int ERROR_FACE_CAMERA_UNAVAILABLE = 7;
    public static final int ERROR_HW_UNAVAILABLE = 1;
    public static final int ERROR_IRIS_CAMERA_FAILURE = 8;
    public static final int ERROR_IRIS_CAMERA_UNAVAILABLE = 9;
    public static final int ERROR_LOCKOUT = 12;
    public static final int ERROR_LOCKOUT_PERMANENT = 13;
    public static final int ERROR_NO_SPACE = 4;
    public static final int ERROR_PROXIMITY_ALERT = 11;
    public static final int ERROR_TEMPLATE_CORRUPTED = 10;
    public static final int ERROR_TIMEOUT = 3;
    public static final int ERROR_UNABLE_TO_PROCESS = 2;
    public static final int ERROR_USER_CANCELED = 14;
    public static final String EXTRA_KEY_PRIVILEGED_FLAG = "sem_privileged_attr";
    private static final int MSG_ACQUIRED = 100;
    private static final int MSG_AUTHENTICATION_FAILED = 102;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 101;
    private static final int MSG_ERROR = 103;
    public static final int PRIVILEGED_FLAG_ALLOW_BACKGROUND = 4;
    public static final int PRIVILEGED_FLAG_AVOID_LOCKOUT = 1;
    public static final int SECURITY_LEVEL_CONVENIENCE = 3;
    public static final int SECURITY_LEVEL_NONE = 0;
    public static final int SECURITY_LEVEL_STRONG = 1;
    public static final int SECURITY_LEVEL_WEAK = 2;
    private static final String TAG = "SemBiometricsManager";
    public static final int TYPE_HIGH_SECURITY = 15;
    public static final int TYPE_NORMAL_SECURITY = 5;
    public static final int TYPE_STRONG_SECURITY = 17;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityLevel {}

    public static final class CryptoObject {
        private BiometricPrompt.CryptoObject mBioCryptoObject;
        private final byte[] mFidoRequestData;
        private byte[] mFidoResultData;

        private CryptoObject(BiometricPrompt.CryptoObject cry) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = cry;
            this.mFidoRequestData = null;
        }

        public long getOpId() {
            return this.mBioCryptoObject.getOpId();
        }

        public BiometricPrompt.CryptoObject getBiometricCryptoObject() {
            return this.mBioCryptoObject;
        }

        public CryptoObject(Signature signature, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = new BiometricPrompt.CryptoObject(signature);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Cipher cipher, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = new BiometricPrompt.CryptoObject(cipher);
            this.mFidoRequestData = fidoRequestData;
        }

        public CryptoObject(Mac mac, byte[] fidoRequestData) {
            this.mFidoResultData = null;
            this.mBioCryptoObject = new BiometricPrompt.CryptoObject(mac);
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

        void setFidoResultData(byte[] fidoResultData) {
            this.mFidoResultData = fidoResultData;
        }
    }

    public static class AuthenticationResult {
        private final CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject crypto) {
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    public abstract static class AuthenticationCallback {
        public void onAuthenticationError(int errorCode, CharSequence errString) {}

        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {}

        public void onAuthenticationSucceeded(AuthenticationResult result) {}

        public void onAuthenticationFailed() {}

        public void onAuthenticationAcquired(int acquireInfo) {}
    }

    @Deprecated
    public void authenticate(
            int type,
            CryptoObject crypto,
            CancellationSignal cancel,
            AuthenticationCallback callback,
            Handler handler,
            int userId,
            Bundle attr) {}

    public void requestSessionOpen() {}

    public void requestSessionClose() {}

    public long getAuthenticatorId() {
        return 0L;
    }

    public void resetTimeout(byte[] token) {}

    public static SemBiometricsManager getInstance(Context context) {
        return createInstance(context);
    }

    @Deprecated
    public static SemBiometricsManager createInstance(Context context) {
        return null;
    }

    private SemBiometricsManager(Context context) {}
}
