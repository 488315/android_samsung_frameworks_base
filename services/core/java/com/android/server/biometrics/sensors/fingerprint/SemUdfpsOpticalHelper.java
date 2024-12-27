package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.display.DisplayManagerInternal;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemBioSysFsProvider;
import com.android.server.display.color.ColorDisplayService;
import com.samsung.android.displaysolution.ISemDisplaySolutionManager;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemUdfpsOpticalHelper {
    static final String DISPLAY_PANEL_TYPE_PATH = "sys/class/lcd/panel/window_type";
    static final String HW_LIGHT_CIRCLE_DRAW = "1";
    static final String HW_LIGHT_CIRCLE_HIDE = "0";
    static final String HW_LIGHT_SOURCE_PATH = "/sys/class/lcd/panel/fp_green_circle";
    static final String MAX_BRIGHTNESS_PATH = "/sys/class/lcd/panel/mask_brightness";
    static final int OPTICAL_CALIBRATION_LOW_BRIGHTNESS = 2;
    static final int OPTICAL_CALIBRATION_NORMAL = 1;
    public String mBrightnessColor;
    public String mBrightnessColorForLowBrightness;
    public SemFpOpticalClient mCalibrationClient;
    public DisplayAdjustmentManager mDisplayAdjManager;
    public DisplayManagerInternal mDisplayManagerInternal;
    public String mDisplayPanelType;
    public final Supplier mGetDisplayStateMonitor;
    public boolean mIsLimitedDisplayOn;
    public final boolean mIsSupportHwLightSource;
    public final SemUdfpsOpticalHelper$$ExternalSyntheticLambda1 mRunnableDisableFunctionForLightSource;
    public final SemUdfpsOpticalHelper$$ExternalSyntheticLambda1 mRunnableRestoreFunctionForLightSource;
    public final SemBioSysFsProvider mSysFsProvider;
    public float mMaxBrightness = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public int mNits = 0;
    public String mLatestHwLightMode = "";
    public final Map mMaskClientList = new HashMap(3);
    public final IBinder mBinderForDisplayStateLimit = new Binder();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class DisplayAdjustmentManager {
        public ColorDisplayService.ColorDisplayServiceInternal mColorDisplayServiceInternal;
        public boolean mIsDisabled;
    }

    public SemUdfpsOpticalHelper(SemBioSysFsProvider semBioSysFsProvider, Supplier supplier, boolean z) {
        this.mIsSupportHwLightSource = z;
        this.mSysFsProvider = semBioSysFsProvider;
        this.mGetDisplayStateMonitor = supplier;
        if (z) {
            return;
        }
        this.mRunnableDisableFunctionForLightSource = new SemUdfpsOpticalHelper$$ExternalSyntheticLambda1(this, 0);
        this.mRunnableRestoreFunctionForLightSource = new SemUdfpsOpticalHelper$$ExternalSyntheticLambda1(this, 1);
    }

    public Handler getBgHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getBiometricCallbackHandler();
    }

    public ISemDisplaySolutionManager getDisplaySolutionManager() {
        final AtomicReference atomicReference = new AtomicReference(ISemDisplaySolutionManager.Stub.asInterface(ServiceManager.getService("DisplaySolution")));
        if (atomicReference.get() == null) {
            ServiceThread serviceThread = new ServiceThread(10, "FingerprintService", true);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            serviceThread.start();
            serviceThread.getThreadHandler().post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AtomicReference atomicReference2 = atomicReference;
                    CountDownLatch countDownLatch2 = countDownLatch;
                    atomicReference2.set(ISemDisplaySolutionManager.Stub.asInterface(ServiceManager.waitForService("DisplaySolution")));
                    countDownLatch2.countDown();
                }
            });
            try {
                Slog.d("FingerprintService", "getDisplaySolutionManager: wait for service result = " + countDownLatch.await(3L, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serviceThread.quit();
        }
        return (ISemDisplaySolutionManager) atomicReference.get();
    }

    public final void removeMaskView(IBinder iBinder) {
        synchronized (this.mMaskClientList) {
            try {
                SemFpOpticalClient semFpOpticalClient = (SemFpOpticalClient) ((HashMap) this.mMaskClientList).get(iBinder);
                if (semFpOpticalClient == null) {
                    Slog.i("FingerprintService", "removeMaskView: No registered client:  " + iBinder);
                } else {
                    ((HashMap) this.mMaskClientList).remove(iBinder);
                    semFpOpticalClient.stop();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDisplayStateLimit(boolean z) {
        synchronized (this.mBinderForDisplayStateLimit) {
            try {
                try {
                    if (this.mDisplayManagerInternal == null) {
                        this.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
                    }
                    if (z) {
                        if (!this.mIsLimitedDisplayOn) {
                            this.mIsLimitedDisplayOn = true;
                            this.mDisplayManagerInternal.setDisplayStateOverride(this.mBinderForDisplayStateLimit, 2);
                        }
                    } else if (this.mIsLimitedDisplayOn) {
                        this.mIsLimitedDisplayOn = false;
                        this.mDisplayManagerInternal.setDisplayStateOverride(this.mBinderForDisplayStateLimit, 0);
                    }
                } catch (Exception e) {
                    Slog.e("FingerprintService", "setDisplayStateLimit: ", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setHwLightMode(String str) {
        if (this.mLatestHwLightMode.equals(str)) {
            return;
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        this.mSysFsProvider.getClass();
        Utils.writeFile(new File(HW_LIGHT_SOURCE_PATH), bytes);
        if (Utils.DEBUG) {
            BootReceiver$$ExternalSyntheticOutline0.m58m("setHwLightMode: [", str, "] done", "FingerprintService");
        }
        this.mLatestHwLightMode = str;
    }
}
