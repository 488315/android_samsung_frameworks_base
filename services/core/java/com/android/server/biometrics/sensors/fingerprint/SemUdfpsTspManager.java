package com.android.server.biometrics.sensors.fingerprint;

import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBiometricFeature;
import com.samsung.android.hardware.secinputdev.ISemInputDeviceManager;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemUdfpsTspManager {
    static final int CMD_CONTROL_TIME = 10;
    static final int CMD_DISABLE = 0;
    static final int CMD_ENABLE = 1;
    static final int CMD_FOD_DISABLE = 6;
    static final int CMD_FOD_ENABLE = 2;
    static final int CMD_FOD_ENABLE_50 = 3;
    static final int CMD_FOD_RECT = 7;
    static final int CMD_FOD_STRICT_ENABLE = 4;
    static final int CMD_FOD_STRICT_ENABLE_50 = 5;
    static final int CMD_LP_MODE = 8;
    static final int CMD_TEMPERATURE = 9;
    public Rect mFodRect;
    public int mInterruptDelay;
    public boolean mIsEnable;
    public boolean mIsHalfMode;
    public boolean mIsHalfModeBlocked;
    public boolean mIsInterruptDelayModeOn;
    public boolean mIsLpMode;
    public boolean mIsStrictMode;
    public int mLastCmd;
    public final SparseBooleanArray mEnableClient = new SparseBooleanArray();
    public final SparseArray mCommands = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class InstanceHolder {
        public static final SemUdfpsTspManager INSTANCE = new SemUdfpsTspManager();
    }

    public static synchronized SemUdfpsTspManager get() {
        SemUdfpsTspManager semUdfpsTspManager;
        synchronized (SemUdfpsTspManager.class) {
            semUdfpsTspManager = InstanceHolder.INSTANCE;
        }
        return semUdfpsTspManager;
    }

    public final synchronized void disable(int i) {
        this.mEnableClient.delete(i);
        if (this.mIsEnable && this.mEnableClient.size() == 0) {
            sendCommand(6);
            this.mIsEnable = false;
        }
    }

    public final synchronized void enable(int i) {
        this.mEnableClient.put(i, true);
        if (this.mIsEnable) {
            return;
        }
        this.mIsEnable = true;
        setFodEnable();
    }

    public Handler getHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    }

    public ISemInputDeviceManager getInputDeviceManager() {
        final AtomicReference atomicReference = new AtomicReference(ISemInputDeviceManager.Stub.asInterface(ServiceManager.getService("SemInputDeviceManagerService")));
        if (atomicReference.get() == null) {
            ServiceThread serviceThread = new ServiceThread(10, "FingerprintService", true);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            serviceThread.start();
            serviceThread.getThreadHandler().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemUdfpsTspManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AtomicReference atomicReference2 = atomicReference;
                    CountDownLatch countDownLatch2 = countDownLatch;
                    atomicReference2.set(ISemInputDeviceManager.Stub.asInterface(ServiceManager.waitForService("SemInputDeviceManagerService")));
                    countDownLatch2.countDown();
                }
            });
            try {
                Slog.d("FingerprintService", "getInputDeviceManager: wait for service result = " + countDownLatch.await(2L, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serviceThread.quit();
        }
        return (ISemInputDeviceManager) atomicReference.get();
    }

    public boolean isFirstApiLevel31orGreater() {
        return Build.VERSION.DEVICE_INITIAL_SDK_INT > 30;
    }

    public final synchronized void screenOff() {
        try {
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL && SemBiometricFeature.FEATURE_SUPPORT_AOD) {
                setHalfMode(true);
            }
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
                if (!this.mIsHalfModeBlocked) {
                    setHalfMode(true);
                }
                this.mIsHalfModeBlocked = false;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void sendCommand(final int i) {
        getHandler().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemUdfpsTspManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemUdfpsTspManager semUdfpsTspManager = SemUdfpsTspManager.this;
                int i2 = i;
                ISemInputDeviceManager inputDeviceManager = semUdfpsTspManager.getInputDeviceManager();
                if (inputDeviceManager == null) {
                    Slog.w("FingerprintService", "sendCommand fail : InputDeviceManager is null");
                    return;
                }
                semUdfpsTspManager.mLastCmd = i2;
                try {
                    switch (i2) {
                        case 2:
                            inputDeviceManager.setFodEnable(1, 1, 0, 0);
                            break;
                        case 3:
                            inputDeviceManager.setFodEnable(1, 0, 0, 0);
                            break;
                        case 4:
                            inputDeviceManager.setFodEnable(1, 1, 1, 0);
                            break;
                        case 5:
                            inputDeviceManager.setFodEnable(1, 0, 1, 0);
                            break;
                        case 6:
                            inputDeviceManager.setFodEnable(0, 0, 0, 0);
                            break;
                        case 7:
                            Rect rect = semUdfpsTspManager.mFodRect;
                            if (rect != null && !rect.isEmpty()) {
                                Rect rect2 = semUdfpsTspManager.mFodRect;
                                inputDeviceManager.setFodRect(rect2.left, rect2.top, rect2.right, rect2.bottom);
                                break;
                            }
                            break;
                        case 8:
                            inputDeviceManager.setFodLpMode(semUdfpsTspManager.mIsLpMode ? 1 : 0);
                            break;
                        case 9:
                            inputDeviceManager.setTemperature(1);
                            break;
                        case 10:
                            inputDeviceManager.setFodEnable(1, 0, 0, semUdfpsTspManager.mInterruptDelay);
                            break;
                    }
                } catch (RemoteException e) {
                    Slog.w("FingerprintService", "sendCommand fail with e : " + e.getMessage());
                }
                BootReceiver$$ExternalSyntheticOutline0.m(i2, "setTspMode: [", "] done", "FingerprintService");
            }
        });
    }

    public final void setFodEnable() {
        if (!this.mIsInterruptDelayModeOn && this.mIsEnable) {
            sendCommand(this.mIsStrictMode ? this.mIsHalfMode ? 5 : 4 : this.mIsHalfMode ? 3 : 2);
        }
    }

    public final synchronized void setFodRect(Rect rect) {
        this.mFodRect = rect;
        Locale locale = Locale.ENGLISH;
        this.mCommands.put(7, "set_fod_rect," + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom);
        sendCommand(7);
    }

    public final synchronized void setHalfMode(boolean z) {
        if (this.mIsHalfMode == z) {
            return;
        }
        this.mIsHalfMode = z;
        setFodEnable();
    }
}
