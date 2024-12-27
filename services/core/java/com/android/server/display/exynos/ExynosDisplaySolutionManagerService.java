package com.android.server.display.exynos;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.display.IExynosDisplaySolutionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExynosDisplaySolutionManagerService extends IExynosDisplaySolutionManager.Stub {
    public final boolean DEBUG;
    public boolean mAtcAlreadyEnable;
    public boolean mAtcEnableSetting;
    public boolean mBootCompleted;
    public String mColorModeName;
    public final Context mContext;
    public final ExynosDisplayATC mExynosDisplayATC;
    public final ExynosDisplayColor mExynosDisplayColor;
    public final ExynosDisplayFactory mExynosDisplayFactory;
    public final ExynosDisplayTune mExynosDisplayTune;
    public final Object mLock;
    public boolean mTuneEnableSetting;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenBroadcastReceiver extends BroadcastReceiver {
        public ScreenBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Log.d("ExynosDisplaySolutionManagerService", "ACTION_BOOT_COMPLETED");
                ExynosDisplaySolutionManagerService exynosDisplaySolutionManagerService = ExynosDisplaySolutionManagerService.this;
                exynosDisplaySolutionManagerService.mBootCompleted = true;
                if (exynosDisplaySolutionManagerService.mTuneEnableSetting) {
                    exynosDisplaySolutionManagerService.mExynosDisplayTune.enableTuneTimer(true);
                }
                ExynosDisplaySolutionManagerService exynosDisplaySolutionManagerService2 = ExynosDisplaySolutionManagerService.this;
                boolean z = exynosDisplaySolutionManagerService2.mAtcEnableSetting;
                if (z) {
                    exynosDisplaySolutionManagerService2.mExynosDisplayATC.enableATC(z);
                    ExynosDisplaySolutionManagerService.this.mExynosDisplayATC.enableLightSensor(true);
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                ExynosDisplaySolutionManagerService exynosDisplaySolutionManagerService3 = ExynosDisplaySolutionManagerService.this;
                if (exynosDisplaySolutionManagerService3.mAtcEnableSetting) {
                    exynosDisplaySolutionManagerService3.mExynosDisplayATC.enableLightSensor(true);
                    return;
                }
                return;
            }
            if (!"android.intent.action.SCREEN_OFF".equals(action)) {
                "android.intent.action.USER_PRESENT".equals(action);
                return;
            }
            ExynosDisplaySolutionManagerService exynosDisplaySolutionManagerService4 = ExynosDisplaySolutionManagerService.this;
            if (exynosDisplaySolutionManagerService4.mAtcEnableSetting) {
                exynosDisplaySolutionManagerService4.mExynosDisplayATC.enableLightSensor(false);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            ExynosDisplaySolutionManagerService.this.settingChanged();
        }
    }

    public ExynosDisplaySolutionManagerService(Context context) {
        String str = Build.TYPE;
        this.DEBUG = "eng".equals(str);
        this.mLock = new Object();
        this.mExynosDisplayTune = null;
        this.mExynosDisplayColor = null;
        this.mExynosDisplayATC = null;
        this.mExynosDisplayFactory = null;
        this.mBootCompleted = false;
        this.mTuneEnableSetting = false;
        this.mAtcEnableSetting = false;
        this.mAtcAlreadyEnable = false;
        this.mContext = context;
        ExynosDisplayTune exynosDisplayTune = new ExynosDisplayTune();
        "eng".equals(str);
        exynosDisplayTune.GAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_ext";
        exynosDisplayTune.GAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/gamma";
        exynosDisplayTune.DEGAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/degamma_ext";
        exynosDisplayTune.DEGAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/degamma";
        exynosDisplayTune.HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
        exynosDisplayTune.CGC17_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_idx";
        exynosDisplayTune.CGC17_ENC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_enc";
        exynosDisplayTune.CGC17_DEC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_dec";
        exynosDisplayTune.CGC17_CON_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_con";
        exynosDisplayTune.GAMMA_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_matrix";
        exynosDisplayTune.CGC_DITHER_SYSFS_PATH = "/sys/class/dqe/dqe/cgc_dither";
        exynosDisplayTune.HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
        exynosDisplayTune.HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
        exynosDisplayTune.SCL_SYSFS_PATH = "/sys/class/dqe/dqe/scl";
        exynosDisplayTune.DE_SYSFS_PATH = "/sys/class/dqe/dqe/de";
        exynosDisplayTune.EXTENSION_OFF = "0";
        exynosDisplayTune.EXTENSION_ON = "1";
        exynosDisplayTune.mDelayMs = 1000L;
        exynosDisplayTune.mPeriodMs = 1000L;
        exynosDisplayTune.CALIB_DATA_XML_PATH = "/data/dqe/calib_data.xml";
        this.mExynosDisplayTune = exynosDisplayTune;
        ExynosDisplayColor exynosDisplayColor = new ExynosDisplayColor();
        this.mExynosDisplayColor = exynosDisplayColor;
        this.mExynosDisplayATC = new ExynosDisplayATC(context);
        this.mExynosDisplayFactory = new ExynosDisplayFactory(context);
        exynosDisplayColor.mExynosDisplayTune = exynosDisplayTune;
        int integer = context.getResources().getInteger(R.integer.config_displayWhiteBalanceBrightnessSensorRate);
        this.mAtcEnableSetting = integer == 2 || integer == 3;
        SettingsObserver settingsObserver = new SettingsObserver(context.getMainThreadHandler());
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor("dqe_tune_enabled"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("atc_mode_enabled"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("display_color_mode"), false, settingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        context.registerReceiver(new ScreenBroadcastReceiver(), intentFilter);
        settingChanged();
        Log.d("ExynosDisplaySolutionManagerService", "ExynosDisplaySolutionManagerService created " + str);
    }

    public final String getColorEnhancementMode() {
        String str;
        synchronized (this.mLock) {
            this.mExynosDisplayColor.getClass();
            this.mColorModeName = "Off,NATIVE,DISPLAY_P3,SRGB";
            Log.d("ExynosDisplaySolutionManagerService", "getColorEnhancementMode(): mColorModeName = " + this.mColorModeName);
            str = this.mColorModeName;
        }
        return str;
    }

    public final void setColorEnhancementSettingValue(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setColorEnhancementSettingValue(): value = " + i);
            ExynosDisplayColor exynosDisplayColor = this.mExynosDisplayColor;
            exynosDisplayColor.getClass();
            try {
                exynosDisplayColor.setXMLColorModesImpl(i != 1 ? i != 2 ? i != 3 ? "bypass" : "SRGB" : "DISPLAY_P3" : "NATIVE");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void setColorTempSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setColorTempSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setColorTempOn(i);
        }
    }

    public final void setColorTempSettingValue(int i, int i2) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setColorTempSettingValue(): " + i + " -> " + i2);
            this.mExynosDisplayColor.setColorTempValue(i, i2);
        }
    }

    public final void setDisplayFeature(String str, int i, int i2, String str2) {
        synchronized (this.mLock) {
            try {
                if (this.DEBUG) {
                    Log.d("ExynosDisplaySolutionManagerService", "setDisplayFeature(): " + str + "  " + i + "  " + i2 + "  " + str2);
                }
                if (str.equals("setDisplayColorFeature")) {
                    int i3 = this.mExynosDisplayFactory.mCountDownTimerCount;
                    if (this.mBootCompleted && i3 <= 0) {
                        ExynosDisplayColor exynosDisplayColor = this.mExynosDisplayColor;
                        exynosDisplayColor.getClass();
                        Log.d("ExynosDisplayColor", "setDisplayColorFeature(): " + i + "  " + i2 + "  " + str2);
                        if (i == 0 && i2 == 0 && str2 != null) {
                            exynosDisplayColor.setXMLColorModesImpl(str2);
                        }
                        return;
                    }
                    Log.e("ExynosDisplaySolutionManagerService", "setDisplayColorFeature is not ready: mBootCompleted=" + this.mBootCompleted + ", timer_count=" + i3);
                    return;
                }
                boolean z = false;
                if (str.equals("dqe_tune")) {
                    if (i == 0) {
                        if (i2 != 0) {
                            z = true;
                        }
                        ExynosDisplayTune exynosDisplayTune = this.mExynosDisplayTune;
                        exynosDisplayTune.getClass();
                        Log.d("ExynosDisplayTune", "enableTuneDQE: enable=" + z);
                        if (z) {
                            exynosDisplayTune.setCalibrationDQE(ExynosDisplayUtils.getPathWithPanel(exynosDisplayTune.CALIB_DATA_XML_PATH), "tune");
                        }
                    }
                    return;
                }
                if (str.equals("hdr_tune")) {
                    if (str2 != null) {
                        long parseUnsignedLong = Long.parseUnsignedLong(str2.replaceFirst("^0x", ""), str2.startsWith("0x") ? 16 : 10);
                        Intent intent = new Intent("com.android.server.display.HDR_TUNE_PATTERN_CHANGED");
                        intent.addFlags(1073741824);
                        intent.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
                        intent.putExtra("com.android.server.display.hdr_tune_format", i);
                        intent.putExtra("com.android.server.display.hdr_tune_type", i2);
                        intent.putExtra("com.android.server.display.hdr_tune_color", parseUnsignedLong);
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
                        intent.setPackage("com.android.exynos.hdrdisplaytune");
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
                        Log.d("ExynosDisplaySolutionManagerService", "Send Pattern format: " + i + " pattern: " + i2 + " RGBA: " + Long.toHexString(parseUnsignedLong));
                    }
                    return;
                }
                if (str.equals("atc_user")) {
                    if (i == 0) {
                        if (i2 != 0) {
                            z = true;
                        }
                        this.mExynosDisplayATC.enableATC(z);
                        this.mExynosDisplayATC.enableLightSensor(z);
                    } else if (i == 1) {
                        if (!this.mAtcAlreadyEnable) {
                            this.mExynosDisplayATC.enableATC(true);
                            this.mAtcAlreadyEnable = true;
                        }
                        this.mExynosDisplayATC.getClass();
                        ExynosDisplayATC.enableATCTuneMode(false);
                        this.mExynosDisplayATC.setLastLuminance(i2);
                    } else if (i == 2) {
                        this.mExynosDisplayATC.enableATC(i2 != 0);
                        this.mExynosDisplayATC.enableLightSensor(false);
                    }
                    return;
                }
                if (!str.equals("atc_tune")) {
                    if (!str.equals("atc_timer")) {
                        if (str.equals("factory_timer")) {
                            this.mExynosDisplayFactory.startCountDownTimer(null);
                            return;
                        }
                        return;
                    } else {
                        this.mExynosDisplayATC.getClass();
                        ExynosDisplayATC.enableATCTuneMode(true);
                        this.mExynosDisplayATC.setCountDownTimer(i, i2);
                        this.mExynosDisplayATC.startCountDownTimer();
                        return;
                    }
                }
                if (i == 0 || i == 7) {
                    boolean z2 = i2 != 0;
                    this.mExynosDisplayATC.getClass();
                    ExynosDisplayATC.enableATCTuneMode(z2);
                    if (i != 0 && !z2) {
                        this.mExynosDisplayATC.resetATC();
                        this.mExynosDisplayATC.enableLightSensor(z2);
                    }
                    this.mExynosDisplayATC.enableATC(z2);
                    this.mExynosDisplayATC.enableLightSensor(z2);
                }
                if (i == 8) {
                    if (i2 != 0) {
                        z = true;
                    }
                    this.mExynosDisplayATC.getClass();
                    ExynosDisplayATC.enableATCTuneMode(z);
                }
                if (i == 9) {
                    this.mExynosDisplayATC.getClass();
                    ExynosDisplayATC.enableATCTuneMode(true);
                    this.mExynosDisplayATC.setLastLuminance(i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setEdgeSharpnessSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setEdgeSharpnessSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setEdgeSharpnessOn(i);
        }
    }

    public final void setEdgeSharpnessSettingValue(int i) {
        String str;
        String[] strArr;
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setEdgeSharpnessSettingValue(): value=" + i);
            ExynosDisplayColor exynosDisplayColor = this.mExynosDisplayColor;
            exynosDisplayColor.getClass();
            try {
                strArr = exynosDisplayColor.sharpness_array;
            } catch (Exception e) {
                e.printStackTrace();
                str = null;
            }
            if (strArr != null && strArr.length != 0 && i < strArr.length) {
                str = strArr[i];
                if (str != null) {
                    Log.d("ExynosDisplayColor", "setEdgeSharpnessValue()");
                    ExynosDisplayUtils.sysfsWriteSting(exynosDisplayColor.DE_SYSFS_PATH, str);
                }
            }
        }
    }

    public final void setEyeTempSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setEyeTempSettingOn(): onoff = " + i);
            ExynosDisplayColor exynosDisplayColor = this.mExynosDisplayColor;
            exynosDisplayColor.getClass();
            try {
                if (i != 0) {
                    exynosDisplayColor.eyetemp_array = ExynosDisplayUtils.parserXML(exynosDisplayColor.EYETEMP_XML_FILE_PATH, "eyetemp", "gamma");
                } else {
                    exynosDisplayColor.eyetemp_array = null;
                    exynosDisplayColor.setGammaBypass();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void setEyeTempSettingValue(int i) {
        String str;
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setEyeTempSettingValue(): value = " + i);
            ExynosDisplayColor exynosDisplayColor = this.mExynosDisplayColor;
            exynosDisplayColor.getClass();
            try {
                String[] strArr = exynosDisplayColor.eyetemp_array;
                if (strArr != null) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                        str = null;
                    }
                    if (strArr.length != 0 && i < strArr.length) {
                        str = strArr[i];
                        if (str != null) {
                            Log.d("ExynosDisplayColor", "setGammaValue()");
                            exynosDisplayColor.sysfsWriteGamma(str, exynosDisplayColor.EXTENSION_OFF);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void setHsvGainSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setHsvGainSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setHsvGainOn();
        }
    }

    public final void setHsvGainSettingValue(int i, int i2, int i3) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setHsvGainSettingValue(): h=" + i + ", s=" + i2 + ", v=" + i3);
            this.mExynosDisplayColor.setHsvGainValue(i, i2, i3);
        }
    }

    public final void setRgbGainSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setRgbGainSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setRgbGainOn(i);
        }
    }

    public final void setRgbGainSettingValue(int i, int i2, int i3) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setRgbGainSettingValue(): r=" + i + ", g=" + i2 + ", b=" + i3);
            this.mExynosDisplayColor.setRgbGainValue(i, i2, i3);
        }
    }

    public final void setRgbWeightSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setRgbWeightSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setRgbWeightOn(i);
        }
    }

    public final void setRgbWeightSettingValue(float f, float f2, float f3) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setRgbWeightSettingValue(): r=" + f + ", g=" + f2 + ", b=" + f3);
            ExynosDisplayColor exynosDisplayColor = this.mExynosDisplayColor;
            exynosDisplayColor.getClass();
            try {
                if (exynosDisplayColor.bIsRgbWeightOn) {
                    ExynosDisplayUtils.sysfsWriteSting(exynosDisplayColor.GAMMA_MATRIX_SYSFS_PATH, ExynosDisplayUtils.toString(new float[][]{new float[]{f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, f2, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f3}}));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void setSkinColorSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setSkinColorSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setSkinColorOn(i);
        }
    }

    public final void setWhitePointColorSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setWhitePointColorSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setWhitePointColorOn(i);
        }
    }

    public final void settingChanged() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = Settings.System.getIntForUser(contentResolver, "dqe_tune_enabled", 0, -2) != 0;
        boolean z2 = Settings.System.getIntForUser(contentResolver, "atc_mode_enabled", this.mAtcEnableSetting ? 1 : 0, -2) != 0;
        Settings.System.getIntForUser(contentResolver, "display_color_mode", 0, -2);
        if (this.mTuneEnableSetting != z && this.mBootCompleted) {
            this.mExynosDisplayTune.enableTuneTimer(z);
        }
        this.mTuneEnableSetting = z;
        if (this.mAtcEnableSetting != z2) {
            Log.d("ExynosDisplaySolutionManagerService", "settingChanged: ATC " + this.mAtcEnableSetting + " -> " + z2);
            if (this.mBootCompleted) {
                this.mExynosDisplayATC.enableATC(z2);
                this.mExynosDisplayATC.enableLightSensor(z2);
            }
        }
        this.mAtcEnableSetting = z2;
    }
}
