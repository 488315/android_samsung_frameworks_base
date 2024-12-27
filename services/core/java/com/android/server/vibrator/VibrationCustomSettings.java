package com.android.server.vibrator;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import com.android.server.vibrator.VibrationSettings;
import com.samsung.android.server.vibrator.VibratorHelper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibrationCustomSettings {
    public AudioManager mAudioManager;
    public final Context mContext;
    public final VibratorManagerService mService;
    public final VibrationSettings.SettingsContentObserver mSettingObserver;
    public final Object mLock = new Object();
    public boolean mIsEnableIntensity = false;
    public boolean mIsHapticSupported = false;
    public boolean mIsHapticEngineSupported = false;
    public int[] LEVEL_TO_MAGNITUDE = new int[10];
    public int[] LEVEL_TO_TOUCH_MAGNITUDE = new int[10];
    public int[] LEVEL_TO_FORCE_MAGNITUDE = new int[10];
    public int mTouchMagnitude = 9999;
    public int mCallMagnitude = 9999;
    public int mNotiMagnitude = 9999;
    public int mForceMagnitude = 9999;
    public int mMaxMagnitude = 9999;
    public int mMinMagnitude = 9999;
    public int mMediaMagnitude = 9999;
    public int mMotorType = 0;
    public boolean mOnlyWatchConnected = false;

    public VibrationCustomSettings(Context context, VibrationSettings.SettingsContentObserver settingsContentObserver, VibratorManagerService vibratorManagerService) {
        this.mContext = context;
        this.mSettingObserver = settingsContentObserver;
        this.mService = vibratorManagerService;
        if (VibratorHelper.sInstance == null) {
            VibratorHelper.sInstance = new VibratorHelper();
        }
    }

    public final int loadSystemSetting(int i, String str) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), str, i, -2);
    }

    public final void registerSettingsObserver(Uri uri) {
        this.mContext.getContentResolver().registerContentObserver(uri, true, this.mSettingObserver, -1);
    }
}
