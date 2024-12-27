package com.android.server.biometrics;

import android.os.Handler;
import android.os.HandlerThread;

public final class BiometricHandlerProvider {
    public static final BiometricHandlerProvider sBiometricHandlerProvider =
            new BiometricHandlerProvider();
    public Handler mBiometricsCallbackHandler;
    public Handler mFaceHandler;
    public Handler mFingerprintHandler;
    public Handler mTestHandler;

    public static Handler getNewHandler(int i, String str) {
        HandlerThread handlerThread = new HandlerThread(str, i);
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    public final synchronized Handler getBiometricCallbackHandler() {
        try {
            Handler handler = this.mTestHandler;
            if (handler != null) {
                return handler;
            }
            if (this.mBiometricsCallbackHandler == null) {
                this.mBiometricsCallbackHandler = getNewHandler(-4, "BiometricsCallbackHandler");
            }
            return this.mBiometricsCallbackHandler;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized Handler getFaceHandler() {
        try {
            Handler handler = this.mTestHandler;
            if (handler != null) {
                return handler;
            }
            if (this.mFaceHandler == null) {
                this.mFaceHandler = getNewHandler(-2, "FaceHandler");
            }
            return this.mFaceHandler;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized Handler getFingerprintHandler() {
        try {
            Handler handler = this.mTestHandler;
            if (handler != null) {
                return handler;
            }
            if (this.mFingerprintHandler == null) {
                this.mFingerprintHandler = getNewHandler(-2, "FingerprintHandler");
            }
            return this.mFingerprintHandler;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setTestHandler(Handler handler) {
        this.mTestHandler = handler;
    }
}
