package com.samsung.android.biometrics.app.setting.fingerprint;

import android.hardware.fingerprint.IFingerprintService;
import android.os.Build;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.app.setting.Utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class DisplayBrightnessMonitor {
    public final AnonymousClass1 mBioSysUiDisplayBrightnessCallback;
    public final String mBrightnessFilePath;
    public int mCurrentBrightness;
    public final AnonymousClass2 mFileObserver;
    public final Supplier mGetFpService;
    public final Handler mH;
    public IFingerprintService mIFingerprintService;
    public final List mListenerList = new CopyOnWriteArrayList();

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public interface OnBrightnessListener {
        void onBrightnessChanged(int i);
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public abstract class sInstanceHolder {
        public static final DisplayBrightnessMonitor sInstance =
                new DisplayBrightnessMonitor(
                        Looper.getMainLooper(),
                        !Utils.Config.FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS,
                        new DisplayBrightnessMonitor$sInstanceHolder$$ExternalSyntheticLambda0());
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor$2] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor$1] */
    public DisplayBrightnessMonitor(Looper looper, boolean z, Supplier supplier) {
        this.mH = new Handler(looper);
        if ("qcom".equals(Build.HARDWARE)) {
            this.mBrightnessFilePath = "/sys/class/backlight/panel0-backlight/brightness";
        } else {
            this.mBrightnessFilePath = "/sys/class/lcd/panel/device/backlight/panel/brightness";
        }
        this.mGetFpService = supplier;
        if (z) {
            this.mBioSysUiDisplayBrightnessCallback = null;
            this.mFileObserver =
                    new FileObserver(
                            new File(
                                    this
                                            .mBrightnessFilePath)) { // from class:
                                                                     // com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor.2
                        @Override // android.os.FileObserver
                        public final void onEvent(int i, String str) {
                            float brightnessFromFile =
                                    DisplayBrightnessMonitor.this.getBrightnessFromFile();
                            if (brightnessFromFile != -1.0f) {
                                DisplayBrightnessMonitor displayBrightnessMonitor =
                                        DisplayBrightnessMonitor.this;
                                Handler handler = displayBrightnessMonitor.mH;
                                handler.removeCallbacksAndMessages(null);
                                handler.post(
                                        new DisplayBrightnessMonitor$$ExternalSyntheticLambda0(
                                                displayBrightnessMonitor, brightnessFromFile));
                            }
                        }
                    };
        } else {
            this.mFileObserver = null;
            this.mBioSysUiDisplayBrightnessCallback =
                    new ISemBiometricSysUiDisplayBrightnessCallback
                            .Stub() { // from class:
                                      // com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor.1
                        public final void onBrightnessChanged(float f) {
                            DisplayBrightnessMonitor displayBrightnessMonitor =
                                    DisplayBrightnessMonitor.this;
                            Handler handler = displayBrightnessMonitor.mH;
                            handler.removeCallbacksAndMessages(null);
                            handler.post(
                                    new DisplayBrightnessMonitor$$ExternalSyntheticLambda0(
                                            displayBrightnessMonitor, f));
                        }
                    };
        }
    }

    public ISemBiometricSysUiDisplayBrightnessCallback getBioSysUiDisplayBrightnessCallback() {
        return this.mBioSysUiDisplayBrightnessCallback;
    }

    public final int getBrightnessFromFile() {
        byte[] readFile = readFile(new File(this.mBrightnessFilePath));
        if (readFile == null) {
            return -1;
        }
        try {
            int parseInt = Integer.parseInt(new String(readFile, StandardCharsets.UTF_8).trim());
            return "4".equals(Utils.Config.FEATURE_CONFIG_CONTROL_AUTO_BRIGHTNESS)
                    ? parseInt / 100
                    : parseInt;
        } catch (NumberFormatException e) {
            Log.w("BSS_DisplayBrightnessMonitor", "getBrightnessFromFile: " + e.getMessage());
            return -1;
        }
    }

    public FileObserver getFileObserver() {
        return this.mFileObserver;
    }

    public void handleDisplayBrightnessChanged(float f) {
        int i = (int) f;
        if (Utils.DEBUG) {
            Log.d(
                    "BSS_DisplayBrightnessMonitor",
                    "handleDisplayBrightnessChanged: " + this.mCurrentBrightness + " -> " + i);
        }
        if (this.mCurrentBrightness == i) {
            return;
        }
        this.mCurrentBrightness = i;
        Iterator it = ((CopyOnWriteArrayList) this.mListenerList).iterator();
        while (it.hasNext()) {
            ((OnBrightnessListener) it.next()).onBrightnessChanged(this.mCurrentBrightness);
        }
    }

    public final void observe(boolean z) {
        AnonymousClass2 anonymousClass2 = this.mFileObserver;
        if (anonymousClass2 != null) {
            if (!z) {
                anonymousClass2.stopWatching();
                return;
            }
            anonymousClass2.startWatching();
            int brightnessFromFile = getBrightnessFromFile();
            if (this.mCurrentBrightness == brightnessFromFile || brightnessFromFile == -1) {
                return;
            }
            this.mCurrentBrightness = getBrightnessFromFile();
            return;
        }
        if (!z) {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = (IFingerprintService) this.mGetFpService.get();
            }
            IFingerprintService iFingerprintService = this.mIFingerprintService;
            if (iFingerprintService == null) {
                Log.w("BSS_DisplayBrightnessMonitor", "IFingerprintService is NULL");
                return;
            }
            try {
                iFingerprintService.semUnregisterDisplayBrightnessCallback();
                return;
            } catch (RemoteException e) {
                Log.w(
                        "BSS_DisplayBrightnessMonitor",
                        "unregisterDisplayBrightnessCallbackForOpt: " + e.getMessage());
                return;
            }
        }
        AnonymousClass1 anonymousClass1 = this.mBioSysUiDisplayBrightnessCallback;
        if (this.mIFingerprintService == null) {
            this.mIFingerprintService = (IFingerprintService) this.mGetFpService.get();
        }
        IFingerprintService iFingerprintService2 = this.mIFingerprintService;
        int i = 127;
        if (iFingerprintService2 == null) {
            Log.w("BSS_DisplayBrightnessMonitor", "IFingerprintService is NULL");
        } else {
            try {
                i = iFingerprintService2.semRegisterDisplayBrightnessCallback(anonymousClass1);
            } catch (RemoteException e2) {
                Log.w(
                        "BSS_DisplayBrightnessMonitor",
                        "registerDisplayBrightnessCallbackForOpt: " + e2.getMessage());
            }
        }
        this.mCurrentBrightness = i;
    }

    public byte[] readFile(File file) {
        return Utils.readFile(file);
    }

    public final void registerListener(OnBrightnessListener onBrightnessListener) {
        if (onBrightnessListener == null
                || ((CopyOnWriteArrayList) this.mListenerList).contains(onBrightnessListener)) {
            return;
        }
        ((CopyOnWriteArrayList) this.mListenerList).add(onBrightnessListener);
        if (((CopyOnWriteArrayList) this.mListenerList).size() == 1) {
            observe(true);
        }
    }

    public final void unregisterListener(OnBrightnessListener onBrightnessListener) {
        if (onBrightnessListener != null
                && ((CopyOnWriteArrayList) this.mListenerList).contains(onBrightnessListener)) {
            ((CopyOnWriteArrayList) this.mListenerList).remove(onBrightnessListener);
            if (((CopyOnWriteArrayList) this.mListenerList).isEmpty()) {
                this.mH.removeCallbacksAndMessages(null);
                observe(false);
            }
        }
    }
}
