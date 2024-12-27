package com.android.server.vibrator;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.VibrationAttributes;
import android.util.Log;
import android.util.Slog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.samsung.android.server.audio.AudioSettingsHelper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VirtualVibSoundHelper {
    public final AudioSettingsHelper mAudioSettingsHelper;
    public boolean mIsVirtualSoundPlaying;
    public final VibrationSettings mSettings;
    public final SoundPool mSoundPool;
    public int mStreamId;
    public final int mVirtualSoundId;

    public VirtualVibSoundHelper(Context context, VibrationSettings vibrationSettings) {
        this.mSettings = vibrationSettings;
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
        SoundPool build = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setFlags(1).setContentType(4).semAddAudioTag("VIRTUAL_VIB_SOUND").build()).build();
        this.mSoundPool = build;
        this.mIsVirtualSoundPlaying = false;
        this.mVirtualSoundId = build.load("/system/media/audio/ui/VIB_Vibration_Call.ogg", 0);
    }

    public final void playVirtualSoundIfNeeded(String str, VibrationAttributes vibrationAttributes, boolean z) {
        if (z) {
            if (vibrationAttributes.hasTag("VIRTUAL_VIB_SOUND") || this.mAudioSettingsHelper.checkAppCategory(str, "virtual_vibration_sound_allowance")) {
                VibrationSettings vibrationSettings = this.mSettings;
                int usage = vibrationAttributes.getUsage();
                VibrationCustomSettings vibrationCustomSettings = vibrationSettings.mCustomSettings;
                boolean z2 = false;
                if (vibrationCustomSettings.loadSystemSetting(0, "vibration_sound_enabled") != 1) {
                    Log.d("VibratorManagerService", "shouldSoundForRingerMode false");
                } else if (usage == 33) {
                    synchronized (vibrationCustomSettings.mLock) {
                        try {
                            AudioManager audioManager = vibrationCustomSettings.mAudioManager;
                            if (audioManager != null) {
                                if (audioManager.getRingerModeInternal() == 1) {
                                    int i = 0;
                                    for (AudioDeviceInfo audioDeviceInfo : vibrationCustomSettings.mAudioManager.getDevices(2)) {
                                        if (audioDeviceInfo.getType() != 8 && audioDeviceInfo.getType() != 22) {
                                            if (audioDeviceInfo.getType() == 7) {
                                                i++;
                                            }
                                        }
                                        Log.d("VibratorManagerService", "skip virtual sound: connected A2DP or USB_HEADSET");
                                    }
                                    Log.d("VibratorManagerService", "sco Devices:" + i + ", only watch connected:" + vibrationCustomSettings.mOnlyWatchConnected);
                                    if (i == 0 || (i == 1 && vibrationCustomSettings.mOnlyWatchConnected)) {
                                        z2 = true;
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
                if (!z2) {
                    Slog.d("VibratorManagerService", "skip virtual sound: shouldSoundForRingerMode false");
                    return;
                }
                if (this.mSoundPool == null || this.mVirtualSoundId == 0) {
                    Slog.d("VibratorManagerService", "cannot play virtual sound");
                    return;
                }
                float loadSystemSetting = r10.loadSystemSetting(r11, "VIB_RECVCALL_MAGNITUDE") / (this.mSettings.mCustomSettings.LEVEL_TO_MAGNITUDE.length - 1);
                if (loadSystemSetting <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    return;
                }
                Slog.d("VibratorManagerService", "play virtual sound: " + loadSystemSetting + " volume");
                this.mIsVirtualSoundPlaying = true;
                this.mSoundPool.semSetSituationType(this.mVirtualSoundId, "stv_virtual_vib_sound");
                this.mStreamId = this.mSoundPool.play(this.mVirtualSoundId, loadSystemSetting, loadSystemSetting, 0, 20, 1.0f);
            }
        }
    }

    public final void stopVirtualSound(String str) {
        if (this.mIsVirtualSoundPlaying) {
            if (this.mSoundPool == null || this.mVirtualSoundId == 0) {
                Slog.e("VibratorManagerService", "cannot stop virtual sound");
                return;
            }
            try {
                Slog.d("VibratorManagerService", "stop virtual sound : ".concat(str));
                this.mIsVirtualSoundPlaying = false;
                this.mSoundPool.stop(this.mStreamId);
            } catch (Exception unused) {
                Slog.e("VibratorManagerService", "failed stopping virtual sound");
            }
        }
    }
}
