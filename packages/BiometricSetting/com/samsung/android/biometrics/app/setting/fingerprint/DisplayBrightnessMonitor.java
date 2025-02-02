package com.samsung.android.biometrics.app.setting.fingerprint;

import android.hardware.fingerprint.IFingerprintService;
import android.os.Build;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class DisplayBrightnessMonitor {
    private static DisplayBrightnessMonitor sInstance;

    @VisibleForTesting
    final ISemBiometricSysUiDisplayBrightnessCallback mBioSysUiDisplayBrightnessCallback;
    private final String mBrightnessFilePath;
    private int mCurrentBrightness;
    private final FileObserver mFileObserver;
    private IFingerprintService mIFingerprintService;

    /* renamed from: mH */
    private final Handler f25mH = new Handler(Looper.getMainLooper());
    private final List<OnBrightnessListener> mListenerList = new CopyOnWriteArrayList();

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor$1 */
    final class C02361 extends ISemBiometricSysUiDisplayBrightnessCallback.Stub {
        C02361() {
        }

        public final void onBrightnessChanged(float f) {
            DisplayBrightnessMonitor.this.f25mH.removeCallbacksAndMessages(null);
            DisplayBrightnessMonitor.this.f25mH.post(new DisplayBrightnessMonitor$1$$ExternalSyntheticLambda0(this, f, 0));
        }
    }

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor$2 */
    final class FileObserverC02372 extends FileObserver {
        FileObserverC02372(File file) {
            super(file, 2);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            float brightnessFromFile = DisplayBrightnessMonitor.this.getBrightnessFromFile();
            if (brightnessFromFile != -1.0f) {
                DisplayBrightnessMonitor.this.f25mH.removeCallbacksAndMessages(null);
                DisplayBrightnessMonitor.this.f25mH.post(new DisplayBrightnessMonitor$1$$ExternalSyntheticLambda0(this, brightnessFromFile, 1));
            }
        }
    }

    public interface OnBrightnessListener {
        void onBrightnessChanged(int i);
    }

    @VisibleForTesting
    DisplayBrightnessMonitor() {
        if ("qcom".equals(Build.HARDWARE)) {
            this.mBrightnessFilePath = "/sys/class/backlight/panel0-backlight/brightness";
        } else {
            this.mBrightnessFilePath = "/sys/class/lcd/panel/device/backlight/panel/brightness";
        }
        if (Utils.Config.FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS) {
            this.mFileObserver = null;
            this.mBioSysUiDisplayBrightnessCallback = new C02361();
        } else {
            this.mBioSysUiDisplayBrightnessCallback = null;
            this.mFileObserver = new FileObserverC02372(new File(this.mBrightnessFilePath));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getBrightnessFromFile() {
        FileInputStream fileInputStream;
        byte[] bArr;
        File file = new File(this.mBrightnessFilePath);
        FileInputStream fileInputStream2 = null;
        r4 = null;
        byte[] bArr2 = null;
        fileInputStream2 = null;
        if (file.exists()) {
            try {
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (Exception e) {
                    e = e;
                    bArr = null;
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
            }
            try {
                bArr2 = new byte[(int) file.length()];
                fileInputStream.read(bArr2);
                try {
                    fileInputStream.close();
                } catch (Exception e2) {
                    Log.w("BSS_DisplayBrightnessMonitor", "failed to close file", e2);
                }
            } catch (Exception e3) {
                e = e3;
                byte[] bArr3 = bArr2;
                fileInputStream2 = fileInputStream;
                bArr = bArr3;
                Log.w("BSS_DisplayBrightnessMonitor", "failure to read file : " + e.getMessage());
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e4) {
                        Log.w("BSS_DisplayBrightnessMonitor", "failed to close file", e4);
                    }
                }
                bArr2 = bArr;
                if (bArr2 != null) {
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e5) {
                        Log.w("BSS_DisplayBrightnessMonitor", "failed to close file", e5);
                    }
                }
                throw th;
            }
        } else {
            Log.d("BSS_DisplayBrightnessMonitor", "Invalid file info, ");
        }
        if (bArr2 != null) {
            return -1;
        }
        try {
            int parseInt = Integer.parseInt(new String(bArr2, StandardCharsets.UTF_8).trim());
            return "4".equals(Utils.Config.FEATURE_CONFIG_CONTROL_AUTO_BRIGHTNESS) ? parseInt / 100 : parseInt;
        } catch (Exception e6) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e6, new StringBuilder("getBrightnessFromFile: "), "BSS_DisplayBrightnessMonitor");
            return -1;
        }
    }

    public static DisplayBrightnessMonitor getInstance() {
        if (sInstance == null) {
            synchronized (DisplayBrightnessMonitor.class) {
                if (sInstance == null) {
                    sInstance = new DisplayBrightnessMonitor();
                }
            }
        }
        return sInstance;
    }

    private void observe(boolean z) {
        int semRegisterDisplayBrightnessCallback;
        if (!Utils.Config.FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS) {
            FileObserver fileObserver = this.mFileObserver;
            if (!z) {
                fileObserver.stopWatching();
                return;
            }
            fileObserver.startWatching();
            int brightnessFromFile = getBrightnessFromFile();
            if (this.mCurrentBrightness == brightnessFromFile || brightnessFromFile == -1) {
                return;
            }
            this.mCurrentBrightness = getBrightnessFromFile();
            return;
        }
        if (!z) {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
            }
            IFingerprintService iFingerprintService = this.mIFingerprintService;
            if (iFingerprintService == null) {
                Log.w("BSS_DisplayBrightnessMonitor", "IFingerprintService is NULL");
                return;
            }
            try {
                iFingerprintService.semUnregisterDisplayBrightnessCallback();
                return;
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("unregisterDisplayBrightnessCallbackForOpt: "), "BSS_DisplayBrightnessMonitor");
                return;
            }
        }
        ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback = this.mBioSysUiDisplayBrightnessCallback;
        if (this.mIFingerprintService == null) {
            this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
        }
        IFingerprintService iFingerprintService2 = this.mIFingerprintService;
        if (iFingerprintService2 != null) {
            try {
                semRegisterDisplayBrightnessCallback = iFingerprintService2.semRegisterDisplayBrightnessCallback(iSemBiometricSysUiDisplayBrightnessCallback);
            } catch (Exception e2) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e2, new StringBuilder("registerDisplayBrightnessCallbackForOpt: "), "BSS_DisplayBrightnessMonitor");
            }
            this.mCurrentBrightness = semRegisterDisplayBrightnessCallback;
        }
        Log.w("BSS_DisplayBrightnessMonitor", "IFingerprintService is NULL");
        semRegisterDisplayBrightnessCallback = 127;
        this.mCurrentBrightness = semRegisterDisplayBrightnessCallback;
    }

    public final int getCurrentBrightness() {
        int brightnessFromFile;
        if (!Utils.Config.FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS && this.mCurrentBrightness != (brightnessFromFile = getBrightnessFromFile()) && brightnessFromFile != -1) {
            this.mCurrentBrightness = getBrightnessFromFile();
        }
        return this.mCurrentBrightness;
    }

    @VisibleForTesting
    protected void handleDisplayBrightnessChanged(float f) {
        int i = (int) f;
        if (Utils.DEBUG) {
            Log.d("BSS_DisplayBrightnessMonitor", "handleDisplayBrightnessChanged: " + this.mCurrentBrightness + " -> " + i);
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

    public final void registerListener(OnBrightnessListener onBrightnessListener) {
        if (onBrightnessListener == null) {
            return;
        }
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) this.mListenerList;
        if (!copyOnWriteArrayList.contains(onBrightnessListener)) {
            copyOnWriteArrayList.add(onBrightnessListener);
        }
        observe(true);
    }

    public final void stop() {
        ((CopyOnWriteArrayList) this.mListenerList).clear();
        this.f25mH.removeCallbacksAndMessages(null);
    }

    public final void unregisterListener(OnBrightnessListener onBrightnessListener) {
        if (onBrightnessListener == null) {
            return;
        }
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) this.mListenerList;
        copyOnWriteArrayList.remove(onBrightnessListener);
        if (copyOnWriteArrayList.isEmpty()) {
            this.f25mH.removeCallbacksAndMessages(null);
            observe(false);
        }
    }
}
