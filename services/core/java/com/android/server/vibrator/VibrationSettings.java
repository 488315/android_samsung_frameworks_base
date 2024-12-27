package com.android.server.vibrator;

import android.R;
import android.app.ActivityManager;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.app.SynchronousUserSwitchObserver;
import android.app.UidObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.RemoteException;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.vibrator.Flags;
import android.os.vibrator.VibrationConfig;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;

import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.vibrator.VibRune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibrationSettings {
    public AudioManager mAudioManager;
    public boolean mBatterySaverMode;
    public final Context mContext;
    public final VibrationCustomSettings mCustomSettings;
    public final SparseArray mFallbackEffects;
    public boolean mHapticFeedbackEnabled;
    public boolean mKeyboardVibrationOn;
    public final INotificationManager mNotificationManager;
    public boolean mOnWirelessCharger;
    public PowerManagerInternal mPowerManagerInternal;
    public int mRingerMode;
    final SettingsBroadcastReceiver mSettingChangeReceiver;
    final SettingsContentObserver mSettingObserver;
    public final String mSystemUiPackage;
    final VibrationUidObserver mUidObserver;
    final VibrationUserSwitchObserver mUserSwitchObserver;
    public boolean mVibrateInputDevices;
    public boolean mVibrateOn;
    public final VibrationConfig mVibrationConfig;
    public VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    public static final Set BACKGROUND_PROCESS_USAGE_ALLOWLIST =
            new HashSet(Arrays.asList(33, 17, 49, 65, 50, 34));
    public static final Set BATTERY_SAVER_USAGE_ALLOWLIST =
            new HashSet(Arrays.asList(33, 17, 65, 34, 50));
    public static final Set SYSTEM_VIBRATION_SCREEN_OFF_USAGE_ALLOWLIST =
            new HashSet(Arrays.asList(18, 66, 34, 50));
    public static final Set POWER_MANAGER_SLEEP_REASON_ALLOWLIST = new HashSet(Arrays.asList(9, 2));
    public static final IntentFilter INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER =
            new IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
    public final Object mLock = new Object();
    public final List mListeners = new ArrayList();
    public final SparseIntArray mCurrentVibrationIntensities = new SparseIntArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class SettingsBroadcastReceiver extends BroadcastReceiver {
        public SettingsBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                VibrationSettings vibrationSettings = VibrationSettings.this;
                Set set = VibrationSettings.BACKGROUND_PROCESS_USAGE_ALLOWLIST;
                synchronized (vibrationSettings.mLock) {
                    AudioManager audioManager = vibrationSettings.mAudioManager;
                    vibrationSettings.mRingerMode =
                            audioManager == null ? 0 : audioManager.getRingerModeInternal();
                }
                VibrationSettings.this.notifyListeners();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            VibrationSettings vibrationSettings = VibrationSettings.this;
            Set set = VibrationSettings.BACKGROUND_PROCESS_USAGE_ALLOWLIST;
            vibrationSettings.updateSettings(-2);
            VibrationSettings.this.notifyListeners();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class VibrationUidObserver extends UidObserver {
        public final SparseArray mProcStatesCache = new SparseArray();

        public final void onUidGone(int i, boolean z) {
            synchronized (this) {
                this.mProcStatesCache.delete(i);
            }
        }

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (this) {
                this.mProcStatesCache.put(i, Integer.valueOf(i2));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class VibrationUserSwitchObserver extends SynchronousUserSwitchObserver {
        public VibrationUserSwitchObserver() {}

        public final void onUserSwitchComplete(int i) {
            VibrationSettings.this.update();
        }

        public final void onUserSwitching(int i) {
            VibrationSettings vibrationSettings = VibrationSettings.this;
            Set set = VibrationSettings.BACKGROUND_PROCESS_USAGE_ALLOWLIST;
            vibrationSettings.updateSettings(i);
            VibrationSettings.this.notifyListeners();
        }
    }

    public VibrationSettings(
            Context context,
            Handler handler,
            VibrationConfig vibrationConfig,
            VibratorManagerService vibratorManagerService) {
        this.mContext = context;
        this.mVibrationConfig = vibrationConfig;
        SettingsContentObserver settingsContentObserver = new SettingsContentObserver(handler);
        this.mSettingObserver = settingsContentObserver;
        this.mSettingChangeReceiver = new SettingsBroadcastReceiver();
        this.mUidObserver = new VibrationUidObserver();
        this.mUserSwitchObserver = new VibrationUserSwitchObserver();
        this.mSystemUiPackage =
                ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                        .getSystemUiServiceComponent()
                        .getPackageName();
        VibrationEffect createEffectFromResource =
                createEffectFromResource(context.getResources(), 17236428);
        VibrationEffect createEffectFromResource2 =
                createEffectFromResource(
                        context.getResources(), R.array.disallowed_apps_managed_user);
        VibrationEffect createEffectFromResource3 =
                createEffectFromResource(context.getResources(), R.array.wifi_known_signers);
        VibrationEffect createEffectFromResource4 =
                createEffectFromResource(
                        context.getResources(), R.array.config_safeModeEnabledVibePattern);
        SparseArray sparseArray = new SparseArray();
        this.mFallbackEffects = sparseArray;
        sparseArray.put(0, createEffectFromResource);
        sparseArray.put(1, createEffectFromResource2);
        sparseArray.put(2, createEffectFromResource4);
        sparseArray.put(5, createEffectFromResource3);
        sparseArray.put(21, VibrationEffect.get(2, false));
        this.mCustomSettings =
                new VibrationCustomSettings(
                        context, settingsContentObserver, vibratorManagerService);
        this.mNotificationManager = NotificationManager.getService();
        update();
    }

    public static VibrationEffect createEffectFromResource(Resources resources, int i) {
        long[] jArr;
        int[] intArray = resources.getIntArray(i);
        if (intArray == null) {
            jArr = null;
        } else {
            jArr = new long[intArray.length];
            for (int i2 = 0; i2 < intArray.length; i2++) {
                jArr[i2] = intArray[i2];
            }
        }
        if (jArr == null || jArr.length == 0) {
            return null;
        }
        return jArr.length == 1
                ? VibrationEffect.createOneShot(jArr[0], -1)
                : VibrationEffect.createWaveform(jArr, -1);
    }

    public static String intensityToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "UNKNOWN INTENSITY ")
                                        : "HIGH"
                                : "MEDIUM"
                        : "LOW"
                : "OFF";
    }

    public static int toIntensity(int i, int i2) {
        return (i < 0 || i > 3) ? i2 : i;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("VibrationSettings:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("vibrateOn = " + this.mVibrateOn);
                indentingPrintWriter.println(
                        "keyboardVibrationOn = "
                                + this.mKeyboardVibrationOn
                                + ", default: "
                                + this.mVibrationConfig.isDefaultKeyboardVibrationEnabled());
                StringBuilder sb = new StringBuilder("vibrateInputDevices = ");
                sb.append(this.mVibrateInputDevices);
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.println("batterySaverMode = " + this.mBatterySaverMode);
                StringBuilder sb2 = new StringBuilder("ringerMode = ");
                int i = this.mRingerMode;
                sb2.append(
                        i != 0
                                ? i != 1 ? i != 2 ? String.valueOf(i) : "normal" : "vibrate"
                                : "silent");
                indentingPrintWriter.println(sb2.toString());
                indentingPrintWriter.println("onWirelessCharger = " + this.mOnWirelessCharger);
                indentingPrintWriter.println(
                        "processStateCache size = " + this.mUidObserver.mProcStatesCache.size());
                indentingPrintWriter.println("VibrationIntensities:");
                indentingPrintWriter.increaseIndent();
                for (int i2 = 0; i2 < this.mCurrentVibrationIntensities.size(); i2++) {
                    int keyAt = this.mCurrentVibrationIntensities.keyAt(i2);
                    indentingPrintWriter.println(
                            VibrationAttributes.usageToString(keyAt)
                                    + " = "
                                    + intensityToString(
                                            this.mCurrentVibrationIntensities.valueAt(i2))
                                    + ", default: "
                                    + intensityToString(
                                            this.mVibrationConfig.getDefaultVibrationIntensity(
                                                    keyAt)));
                }
                indentingPrintWriter.decreaseIndent();
                this.mVibrationConfig.dumpWithoutDefaultSettings(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(ProtoOutputStream protoOutputStream) {
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366168L, this.mVibrateOn);
            protoOutputStream.write(1133871366169L, this.mKeyboardVibrationOn);
            protoOutputStream.write(1133871366150L, this.mBatterySaverMode);
            protoOutputStream.write(1120986464274L, getCurrentIntensity(17));
            protoOutputStream.write(
                    1120986464275L, this.mVibrationConfig.getDefaultVibrationIntensity(17));
            protoOutputStream.write(1120986464278L, getCurrentIntensity(50));
            protoOutputStream.write(
                    1120986464279L, this.mVibrationConfig.getDefaultVibrationIntensity(50));
            protoOutputStream.write(1120986464263L, getCurrentIntensity(18));
            protoOutputStream.write(
                    1120986464264L, this.mVibrationConfig.getDefaultVibrationIntensity(18));
            protoOutputStream.write(1120986464276L, getCurrentIntensity(19));
            protoOutputStream.write(
                    1120986464277L, this.mVibrationConfig.getDefaultVibrationIntensity(19));
            protoOutputStream.write(1120986464265L, getCurrentIntensity(49));
            protoOutputStream.write(
                    1120986464266L, this.mVibrationConfig.getDefaultVibrationIntensity(49));
            protoOutputStream.write(1120986464267L, getCurrentIntensity(33));
            protoOutputStream.write(
                    1120986464268L, this.mVibrationConfig.getDefaultVibrationIntensity(33));
        }
    }

    public final int getCurrentIntensity(int i) {
        int i2;
        int defaultVibrationIntensity = this.mVibrationConfig.getDefaultVibrationIntensity(i);
        synchronized (this.mLock) {
            i2 = this.mCurrentVibrationIntensities.get(i, defaultVibrationIntensity);
        }
        return i2;
    }

    public final int getCurrentMagnitude(int i) {
        VibrationCustomSettings vibrationCustomSettings = this.mCustomSettings;
        vibrationCustomSettings.getClass();
        if (i == 17) {
            return vibrationCustomSettings.mMaxMagnitude;
        }
        if (i != 18) {
            if (i == 33) {
                return vibrationCustomSettings.mCallMagnitude;
            }
            if (i != 34) {
                return i != 49
                        ? i != 50
                                ? vibrationCustomSettings.mMediaMagnitude
                                : vibrationCustomSettings.mForceMagnitude
                        : vibrationCustomSettings.mNotiMagnitude;
            }
        }
        return vibrationCustomSettings.mTouchMagnitude;
    }

    public final int loadSystemSetting(int i, int i2, String str) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), str, i, i2);
    }

    public final void notifyListeners() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mListeners);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((VibratorManagerService$$ExternalSyntheticLambda0) it.next()).f$0.updateServiceState();
        }
    }

    public final void onSystemReady() {
        int[] iArr;
        boolean z;
        Intent registerReceiver;
        PowerManagerInternal powerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        AudioManager audioManager =
                (AudioManager) this.mContext.getSystemService(AudioManager.class);
        int ringerModeInternal = audioManager.getRingerModeInternal();
        synchronized (this.mLock) {
            this.mPowerManagerInternal = powerManagerInternal;
            this.mAudioManager = audioManager;
            this.mRingerMode = ringerModeInternal;
        }
        int[] iArr2 = null;
        try {
            ActivityManager.getService()
                    .registerUidObserver(this.mUidObserver, 3, -1, (String) null);
        } catch (RemoteException unused) {
        }
        try {
            ActivityManager.getService()
                    .registerUserSwitchObserver(this.mUserSwitchObserver, "VibrationSettings");
        } catch (RemoteException unused2) {
        }
        powerManagerInternal.registerLowPowerModeObserver(
                new PowerManagerInternal
                        .LowPowerModeListener() { // from class:
                                                  // com.android.server.vibrator.VibrationSettings.1
                    public final int getServiceType() {
                        return 2;
                    }

                    public final void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                        VibrationSettings vibrationSettings;
                        boolean z2;
                        synchronized (VibrationSettings.this.mLock) {
                            boolean z3 = powerSaveState.batterySaverEnabled;
                            vibrationSettings = VibrationSettings.this;
                            z2 = z3 != vibrationSettings.mBatterySaverMode;
                            vibrationSettings.mBatterySaverMode = z3;
                            vibrationSettings.mBatterySaverMode = false;
                        }
                        if (z2) {
                            vibrationSettings.notifyListeners();
                        }
                    }
                });
        this.mContext.registerReceiver(
                this.mSettingChangeReceiver, INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER, 2);
        registerSettingsObserver(Settings.System.getUriFor("vibrate_input_devices"));
        registerSettingsObserver(Settings.System.getUriFor("vibrate_on"));
        registerSettingsObserver(Settings.System.getUriFor("haptic_feedback_enabled"));
        registerSettingsObserver(Settings.System.getUriFor("alarm_vibration_intensity"));
        registerSettingsObserver(Settings.System.getUriFor("haptic_feedback_intensity"));
        registerSettingsObserver(Settings.System.getUriFor("hardware_haptic_feedback_intensity"));
        registerSettingsObserver(Settings.System.getUriFor("media_vibration_intensity"));
        registerSettingsObserver(Settings.System.getUriFor("notification_vibration_intensity"));
        registerSettingsObserver(Settings.System.getUriFor("ring_vibration_intensity"));
        registerSettingsObserver(Settings.System.getUriFor("keyboard_vibration_enabled"));
        if (this.mVibrationConfig.ignoreVibrationsOnWirelessCharger()
                && (registerReceiver =
                                this.mContext.registerReceiver(
                                        new BroadcastReceiver() { // from class:
                                                                  // com.android.server.vibrator.VibrationSettings.2
                                            @Override // android.content.BroadcastReceiver
                                            public final void onReceive(
                                                    Context context, Intent intent) {
                                                VibrationSettings vibrationSettings =
                                                        VibrationSettings.this;
                                                vibrationSettings.getClass();
                                                int intExtra = intent.getIntExtra("plugged", 0);
                                                synchronized (vibrationSettings.mLock) {
                                                    vibrationSettings.mOnWirelessCharger =
                                                            intExtra == 4;
                                                }
                                            }
                                        },
                                        new IntentFilter("android.intent.action.BATTERY_CHANGED"),
                                        4))
                        != null) {
            int intExtra = registerReceiver.getIntExtra("plugged", 0);
            synchronized (this.mLock) {
                this.mOnWirelessCharger = intExtra == 4;
            }
        }
        VibrationCustomSettings vibrationCustomSettings = this.mCustomSettings;
        AudioManager audioManager2 =
                (AudioManager)
                        vibrationCustomSettings.mContext.getSystemService(AudioManager.class);
        synchronized (vibrationCustomSettings.mLock) {
            vibrationCustomSettings.mAudioManager = audioManager2;
        }
        VibrationCustomSettings vibrationCustomSettings2 = this.mCustomSettings;
        vibrationCustomSettings2.getClass();
        vibrationCustomSettings2.registerSettingsObserver(
                Settings.System.getUriFor("VIB_FEEDBACK_MAGNITUDE"));
        vibrationCustomSettings2.registerSettingsObserver(
                Settings.System.getUriFor("hardware_haptic_feedback_intensity"));
        vibrationCustomSettings2.registerSettingsObserver(
                Settings.System.getUriFor("SEM_VIBRATION_NOTIFICATION_INTENSITY"));
        vibrationCustomSettings2.registerSettingsObserver(
                Settings.System.getUriFor("VIB_RECVCALL_MAGNITUDE"));
        VibrationCustomSettings vibrationCustomSettings3 = this.mCustomSettings;
        VibratorManagerService vibratorManagerService = vibrationCustomSettings3.mService;
        vibrationCustomSettings3.mMotorType = vibratorManagerService.getSupportedMotorType();
        int[] intArray = vibrationCustomSettings3.mContext.getResources().getIntArray(17236345);
        int[] intArray2 = vibrationCustomSettings3.mContext.getResources().getIntArray(17236346);
        int[] intArray3 = vibrationCustomSettings3.mContext.getResources().getIntArray(17236347);
        int[] intArray4 = vibrationCustomSettings3.mContext.getResources().getIntArray(17236348);
        int[] intArray5 = vibrationCustomSettings3.mContext.getResources().getIntArray(17236350);
        int[] intArray6 = vibrationCustomSettings3.mContext.getResources().getIntArray(17236344);
        if (vibratorManagerService.getDefaultVibratorController() != null) {
            iArr2 =
                    vibratorManagerService
                            .getDefaultVibratorController()
                            .mNativeWrapper
                            .getAmplitudeList(0);
            iArr =
                    vibratorManagerService
                            .getDefaultVibratorController()
                            .mNativeWrapper
                            .getAmplitudeList(1);
        } else {
            iArr = null;
        }
        boolean z2 = VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR;
        if (z2 && vibrationCustomSettings3.mMotorType == 1) {
            if (iArr != null && intArray6 != null && iArr.length == intArray6.length) {
                Log.d("VibratorManagerService", "HAL DC touch amplitude list is used!!");
                z = true;
            }
            z = false;
        } else {
            if (iArr2 != null && iArr != null) {
                Log.d("VibratorManagerService", "HAL amplitude list is used!!");
                z = true;
            }
            z = false;
        }
        int i = vibrationCustomSettings3.mMotorType;
        if (i == 1) {
            if (z2) {
                if (z) {
                    intArray = iArr2;
                }
                vibrationCustomSettings3.LEVEL_TO_MAGNITUDE = intArray;
                if (z) {
                    intArray6 = iArr;
                }
                vibrationCustomSettings3.LEVEL_TO_TOUCH_MAGNITUDE = intArray6;
            }
        } else if (i == 2) {
            vibrationCustomSettings3.mIsEnableIntensity = true;
            vibrationCustomSettings3.mIsHapticSupported = true;
            if (z) {
                intArray = iArr2;
            }
            vibrationCustomSettings3.LEVEL_TO_MAGNITUDE = intArray;
            vibrationCustomSettings3.LEVEL_TO_TOUCH_MAGNITUDE = iArr;
        } else if (i == 3) {
            if (!z) {
                iArr2 = intArray5;
            }
            vibrationCustomSettings3.LEVEL_TO_MAGNITUDE = iArr2;
            if (z) {
                intArray5 = iArr;
            }
            vibrationCustomSettings3.LEVEL_TO_TOUCH_MAGNITUDE = intArray5;
            vibrationCustomSettings3.mIsEnableIntensity = true;
            vibrationCustomSettings3.mIsHapticSupported = false;
        } else if (i == 5) {
            if (z) {
                intArray2 = iArr2;
            }
            vibrationCustomSettings3.LEVEL_TO_MAGNITUDE = intArray2;
            if (z) {
                intArray4 = iArr;
            }
            vibrationCustomSettings3.LEVEL_TO_TOUCH_MAGNITUDE = intArray4;
            VibRune.SET_CIRRUS_HAPTIC(true);
            vibrationCustomSettings3.mIsEnableIntensity = true;
            vibrationCustomSettings3.mIsHapticSupported = true;
        } else if (i == 6 || i == 7) {
            if (z) {
                intArray2 = iArr2;
            }
            vibrationCustomSettings3.LEVEL_TO_MAGNITUDE = intArray2;
            vibrationCustomSettings3.LEVEL_TO_TOUCH_MAGNITUDE = iArr;
            vibrationCustomSettings3.mIsEnableIntensity = true;
            vibrationCustomSettings3.mIsHapticSupported = true;
        } else if (i == 8 || i == 9) {
            if (z) {
                intArray = iArr2;
            }
            vibrationCustomSettings3.LEVEL_TO_MAGNITUDE = intArray;
            if (z) {
                intArray3 = iArr;
            }
            vibrationCustomSettings3.LEVEL_TO_TOUCH_MAGNITUDE = intArray3;
            VibRune.SET_CIRRUS_HAPTIC(true);
            vibrationCustomSettings3.mIsEnableIntensity = true;
            vibrationCustomSettings3.mIsHapticSupported = true;
        }
        if (vibratorManagerService.getDefaultVibratorController() != null) {
            vibrationCustomSettings3.mIsHapticEngineSupported =
                    vibratorManagerService
                            .getDefaultVibratorController()
                            .mNativeWrapper
                            .supportsHapticEngine();
            Log.e(
                    "VibratorManagerService",
                    "mIsHapticEngineSupported : "
                            + vibrationCustomSettings3.mIsHapticEngineSupported);
        }
        VibrationCustomSettings vibrationCustomSettings4 = this.mCustomSettings;
        if (Settings.System.getInt(
                        vibrationCustomSettings4.mContext.getContentResolver(),
                        "ringtone_vibration_sep_index_2",
                        0)
                == 0) {
            Settings.System.putInt(
                    vibrationCustomSettings4.mContext.getContentResolver(),
                    "ringtone_vibration_sep_index_2",
                    vibrationCustomSettings4
                            .mContext
                            .getResources()
                            .getInteger(
                                    R.integer
                                            .leanback_setup_translation_backward_out_content_duration));
        }
        update();
    }

    public final void registerSettingsObserver(Uri uri) {
        this.mContext
                .getContentResolver()
                .registerContentObserver(uri, true, this.mSettingObserver, -1);
    }

    public final boolean shouldVibrateForUserSetting(Vibration.CallerInfo callerInfo) {
        int usage = callerInfo.attrs.getUsage();
        if (Flags.keyboardCategoryEnabled() && this.mVibrationConfig.hasFixedKeyboardAmplitude()) {
            int category = callerInfo.attrs.getCategory();
            if (usage == 18 && category == 1) {
                return this.mKeyboardVibrationOn;
            }
        }
        return (getCurrentIntensity(usage) != 0
                        || callerInfo.attrs.hasTag("INTENSITY_MAX")
                        || callerInfo.attrs.hasTag("INTENSITY_MIN")
                        || callerInfo.attrs.hasTag("INDIVIDUAL_INTENSITY"))
                && getCurrentIntensity(usage) != 0;
    }

    public final String toString() {
        String sb;
        synchronized (this.mLock) {
            try {
                StringBuilder sb2 = new StringBuilder("{");
                for (int i = 0; i < this.mCurrentVibrationIntensities.size(); i++) {
                    int keyAt = this.mCurrentVibrationIntensities.keyAt(i);
                    int valueAt = this.mCurrentVibrationIntensities.valueAt(i);
                    sb2.append(VibrationAttributes.usageToString(keyAt));
                    sb2.append("=(");
                    sb2.append(intensityToString(valueAt));
                    sb2.append(",default:");
                    sb2.append(
                            intensityToString(
                                    this.mVibrationConfig.getDefaultVibrationIntensity(keyAt)));
                    sb2.append("), ");
                }
                sb2.append('}');
                String str =
                        this.mKeyboardVibrationOn
                                + " (default: "
                                + this.mVibrationConfig.isDefaultKeyboardVibrationEnabled()
                                + ")";
                StringBuilder sb3 = new StringBuilder();
                sb3.append("VibrationSettings{mVibratorConfig=");
                sb3.append(this.mVibrationConfig);
                sb3.append(", mVibrateOn=");
                sb3.append(this.mVibrateOn);
                sb3.append(", mKeyboardVibrationOn=");
                sb3.append(str);
                sb3.append(", mVibrateInputDevices=");
                sb3.append(this.mVibrateInputDevices);
                sb3.append(", mBatterySaverMode=");
                sb3.append(this.mBatterySaverMode);
                sb3.append(", mRingerMode=");
                int i2 = this.mRingerMode;
                sb3.append(
                        i2 != 0
                                ? i2 != 1 ? i2 != 2 ? String.valueOf(i2) : "normal" : "vibrate"
                                : "silent");
                sb3.append(", mOnWirelessCharger=");
                sb3.append(this.mOnWirelessCharger);
                sb3.append(", mVibrationIntensities=");
                sb3.append((Object) sb2);
                sb3.append(", mProcStatesCache=");
                sb3.append(this.mUidObserver.mProcStatesCache);
                sb3.append('}');
                sb = sb3.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        return sb;
    }

    public final void update() {
        updateSettings(-2);
        synchronized (this.mLock) {
            AudioManager audioManager = this.mAudioManager;
            this.mRingerMode = audioManager == null ? 0 : audioManager.getRingerModeInternal();
        }
        notifyListeners();
    }

    public final void updateAmplitudeLevel() {
        VibrationCustomSettings vibrationCustomSettings = this.mCustomSettings;
        if (!vibrationCustomSettings.mIsEnableIntensity) {
            if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR
                    && vibrationCustomSettings.mMotorType == 1) {
                try {
                    vibrationCustomSettings.mTouchMagnitude =
                            vibrationCustomSettings
                                    .LEVEL_TO_TOUCH_MAGNITUDE[
                                    vibrationCustomSettings.loadSystemSetting(
                                            vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE.length
                                                    - 1,
                                            "VIB_FEEDBACK_MAGNITUDE")];
                    return;
                } catch (ArrayIndexOutOfBoundsException unused) {
                    Log.i("VibratorManagerService", "failed to set DcHapticFeedbackMagnitude");
                    vibrationCustomSettings.mTouchMagnitude =
                            vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE[1];
                    return;
                }
            }
            return;
        }
        int[] iArr = vibrationCustomSettings.LEVEL_TO_MAGNITUDE;
        int length = iArr.length - 1;
        int[] iArr2 = vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE;
        int length2 = iArr2.length - 1;
        if (length < 2 || length2 < 2) {
            ASKSManagerService$$ExternalSyntheticOutline0.m(
                    length,
                    length2,
                    "magnitudeMaxLevel : ",
                    ", touchMagnitudeMaxLevel : ",
                    "VibratorManagerService");
            return;
        }
        vibrationCustomSettings.mTouchMagnitude = iArr2[length2];
        int i = iArr[length];
        vibrationCustomSettings.mCallMagnitude = i;
        vibrationCustomSettings.mNotiMagnitude = i;
        vibrationCustomSettings.mMaxMagnitude = i;
        vibrationCustomSettings.mMinMagnitude = iArr[1];
        vibrationCustomSettings.mMediaMagnitude = i;
        int loadSystemSetting =
                vibrationCustomSettings.loadSystemSetting(length2, "VIB_FEEDBACK_MAGNITUDE");
        if (loadSystemSetting <= length2) {
            loadSystemSetting = vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE[loadSystemSetting];
        }
        vibrationCustomSettings.mTouchMagnitude = loadSystemSetting;
        int loadSystemSetting2 =
                vibrationCustomSettings.loadSystemSetting(length, "VIB_RECVCALL_MAGNITUDE");
        if (loadSystemSetting2 <= length) {
            loadSystemSetting2 = vibrationCustomSettings.LEVEL_TO_MAGNITUDE[loadSystemSetting2];
        }
        vibrationCustomSettings.mCallMagnitude = loadSystemSetting2;
        int loadSystemSetting3 =
                vibrationCustomSettings.loadSystemSetting(
                        length, "SEM_VIBRATION_NOTIFICATION_INTENSITY");
        if (loadSystemSetting3 <= length) {
            loadSystemSetting3 = vibrationCustomSettings.LEVEL_TO_MAGNITUDE[loadSystemSetting3];
        }
        vibrationCustomSettings.mNotiMagnitude = loadSystemSetting3;
        int loadSystemSetting4 =
                vibrationCustomSettings.loadSystemSetting(length, "media_vibration_intensity");
        if (loadSystemSetting4 <= length) {
            loadSystemSetting4 = vibrationCustomSettings.LEVEL_TO_MAGNITUDE[loadSystemSetting4];
        }
        vibrationCustomSettings.mMediaMagnitude = loadSystemSetting4;
        if (vibrationCustomSettings.mIsHapticEngineSupported) {
            int[] intArray = vibrationCustomSettings.mContext.getResources().getIntArray(17236349);
            vibrationCustomSettings.LEVEL_TO_FORCE_MAGNITUDE = intArray;
            int length3 = intArray.length - 1;
            vibrationCustomSettings.mForceMagnitude = intArray[length3];
            if (length3 < 2) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        length3, "touchForceMagnitudeMaxLevel : ", "VibratorManagerService");
                return;
            }
            int loadSystemSetting5 =
                    vibrationCustomSettings.loadSystemSetting(
                            length3, "SEM_VIBRATION_FORCE_TOUCH_INTENSITY");
            if (loadSystemSetting5 <= length3) {
                loadSystemSetting5 =
                        vibrationCustomSettings.LEVEL_TO_FORCE_MAGNITUDE[loadSystemSetting5];
            }
            vibrationCustomSettings.mForceMagnitude = loadSystemSetting5;
        }
    }

    public final void updateCustomSettings() {
        VibrationCustomSettings vibrationCustomSettings = this.mCustomSettings;
        int length = vibrationCustomSettings.LEVEL_TO_MAGNITUDE.length - 1;
        int length2 = vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE.length - 1;
        int length3 = vibrationCustomSettings.LEVEL_TO_FORCE_MAGNITUDE.length - 1;
        if (length < 2 || length2 < 2) {
            ASKSManagerService$$ExternalSyntheticOutline0.m(
                    length,
                    length2,
                    "magnitudeMaxLevel : ",
                    ", touchMagnitudeMaxLevel : ",
                    "VibratorManagerService");
            return;
        }
        int loadSystemSetting =
                vibrationCustomSettings.loadSystemSetting(length2, "VIB_FEEDBACK_MAGNITUDE");
        if (loadSystemSetting <= length2) {
            loadSystemSetting = vibrationCustomSettings.LEVEL_TO_TOUCH_MAGNITUDE[loadSystemSetting];
        }
        vibrationCustomSettings.mTouchMagnitude = loadSystemSetting;
        int loadSystemSetting2 =
                vibrationCustomSettings.loadSystemSetting(length, "VIB_RECVCALL_MAGNITUDE");
        if (loadSystemSetting2 <= length) {
            loadSystemSetting2 = vibrationCustomSettings.LEVEL_TO_MAGNITUDE[loadSystemSetting2];
        }
        vibrationCustomSettings.mCallMagnitude = loadSystemSetting2;
        int loadSystemSetting3 =
                vibrationCustomSettings.loadSystemSetting(
                        length, "SEM_VIBRATION_NOTIFICATION_INTENSITY");
        if (loadSystemSetting3 <= length) {
            loadSystemSetting3 = vibrationCustomSettings.LEVEL_TO_MAGNITUDE[loadSystemSetting3];
        }
        vibrationCustomSettings.mNotiMagnitude = loadSystemSetting3;
        if (length3 < 2) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    length3, "forceTouchMagnitudeMaxLevel : ", "VibratorManagerService");
            return;
        }
        int loadSystemSetting4 =
                vibrationCustomSettings.loadSystemSetting(
                        length3, "SEM_VIBRATION_FORCE_TOUCH_INTENSITY");
        if (loadSystemSetting4 <= length3) {
            loadSystemSetting4 =
                    vibrationCustomSettings.LEVEL_TO_FORCE_MAGNITUDE[loadSystemSetting4];
        }
        vibrationCustomSettings.mForceMagnitude = loadSystemSetting4;
        int loadSystemSetting5 =
                vibrationCustomSettings.loadSystemSetting(length, "media_vibration_intensity");
        if (loadSystemSetting5 <= length) {
            loadSystemSetting5 = vibrationCustomSettings.LEVEL_TO_MAGNITUDE[loadSystemSetting5];
        }
        vibrationCustomSettings.mMediaMagnitude = loadSystemSetting5;
    }

    public final void updateSettings(int i) {
        synchronized (this.mLock) {
            boolean z = true;
            this.mVibrateInputDevices = loadSystemSetting(0, i, "vibrate_input_devices") > 0;
            this.mVibrateOn = loadSystemSetting(1, i, "vibrate_on") > 0;
            this.mKeyboardVibrationOn =
                    loadSystemSetting(
                                    this.mVibrationConfig.isDefaultKeyboardVibrationEnabled()
                                            ? 1
                                            : 0,
                                    i,
                                    "keyboard_vibration_enabled")
                            > 0;
            if (loadSystemSetting(1, i, "haptic_feedback_enabled") <= 0) {
                z = false;
            }
            this.mHapticFeedbackEnabled = z;
            int intensity =
                    toIntensity(
                            loadSystemSetting(-1, i, "alarm_vibration_intensity"),
                            this.mVibrationConfig.getDefaultVibrationIntensity(17));
            int defaultVibrationIntensity = this.mVibrationConfig.getDefaultVibrationIntensity(18);
            VibratorHelper vibratorHelper = VibratorHelper.sInstance;
            int intensity2 =
                    toIntensity(
                            loadSystemSetting(-1, i, "VIB_FEEDBACK_MAGNITUDE"),
                            defaultVibrationIntensity);
            if (intensity2 != 0) {
                defaultVibrationIntensity = toIntensity(intensity2, defaultVibrationIntensity);
            }
            int intensity3 =
                    toIntensity(
                            loadSystemSetting(-1, i, "hardware_haptic_feedback_intensity"),
                            defaultVibrationIntensity);
            int intensity4 =
                    toIntensity(
                            loadSystemSetting(-1, i, "media_vibration_intensity"),
                            this.mVibrationConfig.getDefaultVibrationIntensity(19));
            int defaultVibrationIntensity2 = this.mVibrationConfig.getDefaultVibrationIntensity(49);
            int intensity5 =
                    toIntensity(
                            loadSystemSetting(-1, i, "SEM_VIBRATION_NOTIFICATION_INTENSITY"),
                            defaultVibrationIntensity2);
            if (intensity5 != 0) {
                defaultVibrationIntensity2 = toIntensity(intensity5, defaultVibrationIntensity2);
            }
            int intensity6 =
                    toIntensity(
                            loadSystemSetting(-1, i, "VIB_RECVCALL_MAGNITUDE"),
                            this.mVibrationConfig.getDefaultVibrationIntensity(33));
            this.mCurrentVibrationIntensities.clear();
            this.mCurrentVibrationIntensities.put(17, intensity);
            this.mCurrentVibrationIntensities.put(49, intensity5);
            this.mCurrentVibrationIntensities.put(19, intensity4);
            this.mCurrentVibrationIntensities.put(0, intensity4);
            this.mCurrentVibrationIntensities.put(33, intensity6);
            this.mCurrentVibrationIntensities.put(65, defaultVibrationIntensity2);
            this.mCurrentVibrationIntensities.put(50, intensity3);
            this.mCurrentVibrationIntensities.put(34, intensity3);
            this.mCurrentVibrationIntensities.put(18, intensity2);
            this.mCurrentVibrationIntensities.put(66, defaultVibrationIntensity);
            updateAmplitudeLevel();
            updateCustomSettings();
        }
    }
}
