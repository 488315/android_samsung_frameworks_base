package com.android.server.display.exynos;

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

/* loaded from: classes2.dex */
public class ExynosDisplaySolutionManagerService extends IExynosDisplaySolutionManager.Stub {
    public final int ATC_ENABLE_DEFAULT;
    public final boolean DEBUG;
    public boolean mAtcAlreadyEnable;
    public boolean mAtcEnableSetting;
    public boolean mBootCompleted;
    public String mColorModeName;
    public final Context mContext;
    public ExynosDisplayATC mExynosDisplayATC;
    public ExynosDisplayColor mExynosDisplayColor;
    public ExynosDisplayFactory mExynosDisplayFactory;
    public ExynosDisplayTune mExynosDisplayTune;
    public final Object mLock;
    public SettingsObserver mSettingsObserver;
    public boolean mTuneEnableSetting;

    public ExynosDisplaySolutionManagerService(Context context) {
        String str = Build.TYPE;
        this.DEBUG = "eng".equals(str) || "userdebug".equals(str);
        this.mLock = new Object();
        this.mExynosDisplayTune = null;
        this.mExynosDisplayColor = null;
        this.mExynosDisplayATC = null;
        this.mExynosDisplayFactory = null;
        this.mBootCompleted = false;
        this.mTuneEnableSetting = false;
        this.ATC_ENABLE_DEFAULT = 1;
        this.mAtcEnableSetting = true;
        this.mAtcAlreadyEnable = false;
        this.mContext = context;
        this.mExynosDisplayTune = new ExynosDisplayTune();
        this.mExynosDisplayColor = new ExynosDisplayColor();
        this.mExynosDisplayATC = new ExynosDisplayATC(context);
        this.mExynosDisplayFactory = new ExynosDisplayFactory(context);
        this.mExynosDisplayColor.setExynosDisplayTune(this.mExynosDisplayTune);
        this.mExynosDisplayFactory.setExynosDisplayATC(this.mExynosDisplayATC);
        this.mSettingsObserver = new SettingsObserver(context.getMainThreadHandler());
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor("dqe_tune_enabled"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("atc_mode_enabled"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("display_color_mode"), false, this.mSettingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        context.registerReceiver(new ScreenBroadcastReceiver(), intentFilter);
        settingChanged();
        Log.d("ExynosDisplaySolutionManagerService", "ExynosDisplaySolutionManagerService created " + str);
    }

    public void setDisplayFeature(String str, int i, int i2, String str2) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setDisplayFeature(): " + str + "  " + i + "  " + i2 + "  " + str2);
            }
            if (str.equals("setDisplayColorFeature")) {
                int countDownTimerCount = this.mExynosDisplayFactory.getCountDownTimerCount();
                if (this.mBootCompleted && countDownTimerCount <= 0) {
                    this.mExynosDisplayColor.setDisplayColorFeature(i, i2, str2);
                    return;
                }
                Log.e("ExynosDisplaySolutionManagerService", "setDisplayColorFeature is not ready: mBootCompleted=" + this.mBootCompleted + ", timer_count=" + countDownTimerCount);
                return;
            }
            boolean z = false;
            if (str.equals("dqe_tune")) {
                if (i == 0) {
                    if (i2 != 0) {
                        z = true;
                    }
                    this.mExynosDisplayTune.enableTuneDQE(z);
                }
                return;
            }
            if (str.equals("hdr_tune")) {
                if (str2 != null) {
                    long parseUnsignedLong = Long.parseUnsignedLong(str2.replaceFirst("^0x", ""), str2.startsWith("0x") ? 16 : 10);
                    Intent intent = new Intent("com.android.server.display.HDR_TUNE_PATTERN_CHANGED");
                    intent.addFlags(1073741824);
                    intent.setPackage("com.android.settings");
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
                        this.mExynosDisplayATC.enableATC(this.mAtcEnableSetting);
                        this.mExynosDisplayATC.enableLightSensor(true);
                        this.mAtcAlreadyEnable = true;
                    }
                    this.mExynosDisplayATC.setLastLuminance(i2);
                }
                return;
            }
            if (!str.equals("atc_tune")) {
                if (str.equals("atc_timer")) {
                    this.mExynosDisplayATC.enableATCTuneMode(true);
                    this.mExynosDisplayATC.setCountDownTimer(i, i2);
                    this.mExynosDisplayATC.startCountDownTimer();
                    return;
                } else {
                    if (str.equals("factory_timer")) {
                        this.mExynosDisplayFactory.startCountDownTimer(null);
                        return;
                    }
                    return;
                }
            }
            if (i == 0 || i == 7) {
                boolean z2 = i2 != 0;
                this.mExynosDisplayATC.enableATCTuneMode(z2);
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
                this.mExynosDisplayATC.enableATCTuneMode(z);
            }
            if (i == 9) {
                this.mExynosDisplayATC.enableATCTuneMode(true);
                this.mExynosDisplayATC.setLastLuminance(i2);
            }
        }
    }

    public String getColorEnhancementMode() {
        String str;
        synchronized (this.mLock) {
            this.mColorModeName = this.mExynosDisplayColor.getColorEnhancementMode();
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "getColorEnhancementMode(): mColorModeName = " + this.mColorModeName);
            }
            str = this.mColorModeName;
        }
        return str;
    }

    public void setColorEnhancementSettingValue(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setColorEnhancementSettingValue(): value = " + i);
            }
            this.mExynosDisplayColor.setColorEnhancement(i);
        }
    }

    public void setColorTempSettingValue(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setColorTempSettingValue(): value = " + i);
            }
            this.mExynosDisplayColor.setColorTempValue(i);
        }
    }

    public void setColorTempSettingOn(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setColorTempSettingOn(): onoff = " + i);
            }
            this.mExynosDisplayColor.setColorTempOn(i);
        }
    }

    public void setEyeTempSettingValue(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setEyeTempSettingValue(): value = " + i);
            }
            this.mExynosDisplayColor.setEyeTempValue(i);
        }
    }

    public void setEyeTempSettingOn(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setEyeTempSettingOn(): onoff = " + i);
            }
            this.mExynosDisplayColor.setEyeTempOn(i);
        }
    }

    public void setRgbGainSettingValue(int i, int i2, int i3) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setRgbGainSettingValue(): r=" + i + ", g=" + i2 + ", b=" + i3);
            }
            this.mExynosDisplayColor.setRgbGainValue(i, i2, i3);
        }
    }

    public void setRgbGainSettingOn(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setRgbGainSettingOn(): onoff = " + i);
            }
            this.mExynosDisplayColor.setRgbGainOn(i);
        }
    }

    public void setSkinColorSettingOn(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setSkinColorSettingOn(): onoff = " + i);
            }
            this.mExynosDisplayColor.setSkinColorOn(i);
        }
    }

    public void setHsvGainSettingValue(int i, int i2, int i3) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setHsvGainSettingValue(): h=" + i + ", s=" + i2 + ", v=" + i3);
            }
            this.mExynosDisplayColor.setHsvGainValue(i, i2, i3);
        }
    }

    public void setHsvGainSettingOn(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setHsvGainSettingOn(): onoff = " + i);
            }
            this.mExynosDisplayColor.setHsvGainOn(i);
        }
    }

    public void setWhitePointColorSettingOn(int i) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setWhitePointColorSettingOn(): onoff = " + i);
            }
            this.mExynosDisplayColor.setWhitePointColorOn(i);
        }
    }

    public void setRgbGain(float f, float f2, float f3) {
        synchronized (this.mLock) {
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "setRgbGain(): r=" + f + ", g=" + f2 + ", b=" + f3);
            }
            this.mExynosDisplayColor.setRgbGain(f, f2, f3);
        }
    }

    public float[] getRgbGain() {
        float[] rgbGain;
        synchronized (this.mLock) {
            rgbGain = this.mExynosDisplayColor.getRgbGain();
            if (this.DEBUG) {
                Log.d("ExynosDisplaySolutionManagerService", "getRgbGain(): r=" + rgbGain[0] + ", g=" + rgbGain[1] + ", b=" + rgbGain[2]);
            }
        }
        return rgbGain;
    }

    public void setEdgeSharpnessSettingValue(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setEdgeSharpnessSettingValue(): value=" + i);
            this.mExynosDisplayColor.setEdgeSharpnessValue(i);
        }
    }

    public void setEdgeSharpnessSettingOn(int i) {
        synchronized (this.mLock) {
            Log.d("ExynosDisplaySolutionManagerService", "setEdgeSharpnessSettingOn(): onoff = " + i);
            this.mExynosDisplayColor.setEdgeSharpnessOn(i);
        }
    }

    public final class ScreenBroadcastReceiver extends BroadcastReceiver {
        public ScreenBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Log.d("ExynosDisplaySolutionManagerService", "ACTION_BOOT_COMPLETED");
                ExynosDisplaySolutionManagerService.this.mBootCompleted = true;
                if (ExynosDisplaySolutionManagerService.this.mTuneEnableSetting) {
                    ExynosDisplaySolutionManagerService.this.mExynosDisplayTune.enableTuneTimer(true);
                }
                if (!ExynosDisplaySolutionManagerService.this.mAtcEnableSetting || ExynosDisplaySolutionManagerService.this.mAtcAlreadyEnable) {
                    return;
                }
                ExynosDisplaySolutionManagerService.this.mExynosDisplayATC.enableATC(ExynosDisplaySolutionManagerService.this.mAtcEnableSetting);
                ExynosDisplaySolutionManagerService.this.mExynosDisplayATC.enableLightSensor(true);
                ExynosDisplaySolutionManagerService.this.mAtcAlreadyEnable = true;
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                if (ExynosDisplaySolutionManagerService.this.mAtcEnableSetting) {
                    ExynosDisplaySolutionManagerService.this.mExynosDisplayATC.enableLightSensor(true);
                }
            } else {
                if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    if (ExynosDisplaySolutionManagerService.this.mAtcEnableSetting) {
                        ExynosDisplaySolutionManagerService.this.mExynosDisplayATC.enableLightSensor(false);
                        return;
                    }
                    return;
                }
                "android.intent.action.USER_PRESENT".equals(action);
            }
        }
    }

    public final void settingChanged() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = Settings.System.getIntForUser(contentResolver, "dqe_tune_enabled", 0, -2) != 0;
        boolean z2 = Settings.System.getIntForUser(contentResolver, "atc_mode_enabled", 1, -2) != 0;
        int intForUser = Settings.System.getIntForUser(contentResolver, "display_color_mode", 0, -2);
        Log.d("ExynosDisplaySolutionManagerService", "settingChanged: mAtcEnableSetting=" + this.mAtcEnableSetting + " atcEnableSetting =" + z2);
        if (this.DEBUG) {
            Log.d("ExynosDisplaySolutionManagerService", "settingChanged: display_color_mode=" + intForUser);
        }
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

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            ExynosDisplaySolutionManagerService.this.settingChanged();
        }
    }
}
