package com.android.server.audio;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.AudioSystem;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.audio.Rune;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.FactoryUtils;
import com.samsung.android.server.audio.VolumeMonitorService;
import com.samsung.android.server.audio.utils.CoreFxUtils;
import com.samsung.android.server.audio.utils.SoundAliveUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioServiceExt$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioServiceExt$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                Context context = ((AudioServiceExt) obj).mContext;
                if (!SoundAliveUtils.sIsSoundAliveExist) {
                    r2 =
                            context.getPackageManager()
                                            .resolveContentProvider(
                                                    "com.sec.android.app.soundalive.compatibility.SAContentProvider",
                                                    0)
                                    != null;
                    SoundAliveUtils.sIsSoundAliveExist = r2;
                }
                if (r2) {
                    Log.d(
                            "AS.SoundAliveUtils",
                            "reset concert hall from sound assistant data cleared");
                    try {
                        ContentResolver contentResolver = context.getContentResolver();
                        Uri parse =
                                Uri.parse(
                                        "content://com.sec.android.app.soundalive.compatibility.SAContentProvider");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("CONCERT_HALL_EFFECT", (Integer) 0);
                        contentResolver.insert(parse, contentValues);
                        return;
                    } catch (Exception e) {
                        Log.e("AS.SoundAliveUtils", "resetConcertHall", e);
                        return;
                    }
                }
                return;
            default:
                AudioServiceExt audioServiceExt =
                        ((AudioServiceExt.ExtBroadcastReceiver) obj).this$0;
                ContentResolver contentResolver2 = audioServiceExt.mCr;
                Log.i("AS.AudioServiceExt", "performSoftReset start");
                AudioSettingsHelper audioSettingsHelper = audioServiceExt.mSettingsHelper;
                audioSettingsHelper.getClass();
                try {
                    audioSettingsHelper.mDatabase.execSQL("delete from audio_settings");
                    audioSettingsHelper.mDatabase.execSQL("delete from device_addr");
                    audioSettingsHelper.mDatabase.execSQL("delete from app_volume");
                    audioSettingsHelper.mDatabase.execSQL("delete from selectedpkg");
                } catch (Exception e2) {
                    AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                            e2, new StringBuilder("resetTable error "), "AudioService.DB");
                }
                audioServiceExt.mAllSoundMute = 0;
                Settings.System.putIntForUser(contentResolver2, "all_sound_off", 0, -2);
                audioServiceExt.setAllSoundMute();
                Settings.System.putIntForUser(contentResolver2, "master_mono", 0, -2);
                if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
                    Settings.Global.putInt(contentResolver2, "volume_monitor", 0);
                    audioServiceExt.mVolumeMonitorValue = 0;
                    VolumeMonitorService.getInstance(audioServiceExt.mContext).resetByDataClear();
                }
                audioServiceExt.mAdaptSoundEnabled = 0;
                Settings.System.putIntForUser(contentResolver2, "hearing_musiccheck", 0, -2);
                CoreFxUtils.setAdaptSound(
                        audioServiceExt.mContext, audioServiceExt.mAdaptSoundEnabled);
                audioServiceExt.mUpscalerEnabled = 0;
                Settings.System.putIntForUser(contentResolver2, "k2hd_effect", 0, -2);
                CoreFxUtils.setUpScalerMode(audioServiceExt.mUpscalerEnabled);
                Settings.System.putIntForUser(contentResolver2, "sound_alive_effect", 0, -2);
                Settings.System.putIntForUser(contentResolver2, "tube_amp_effect", 0, -2);
                Settings.System.putIntForUser(
                        contentResolver2,
                        "adjust_media_volume_only",
                        !FactoryUtils.isFactoryMode() ? 1 : 0,
                        -2);
                if (audioServiceExt.mService.mVolumeSteps != null) {
                    Settings.System.putString(audioServiceExt.mCr, "sec_volume_steps", "");
                }
                if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                    AudioServiceExt.mKaraokeListenbackEnabled = 0;
                    Settings.Global.putInt(contentResolver2, "headphone_monitoring", 0);
                }
                audioServiceExt.setNbQualityMode(0);
                Settings.Global.putInt(contentResolver2, "personalise_call_sound_soft", 0);
                audioServiceExt.mMainBalance = FullScreenMagnificationGestureHandler.MAX_SCALE;
                Settings.System.putFloatForUser(
                        audioServiceExt.mCr,
                        "master_balance",
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        -2);
                Settings.System.putFloatForUser(
                        audioServiceExt.mCr,
                        "speaker_balance",
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        -2);
                if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
                    audioServiceExt.mVoipExtraVolume = false;
                    Settings.System.putInt(audioServiceExt.mCr, "voip_extra_volume", 0);
                }
                if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
                    audioServiceExt.mVoipAntiHowling = false;
                    Settings.System.putInt(audioServiceExt.mCr, "voip_anti_howling", 0);
                }
                Settings.Global.putInt(contentResolver2, "multisound_state", 0);
                Settings.System.putString(contentResolver2, "multisound_app", null);
                Settings.System.putInt(contentResolver2, "multisound_devicetype", -1);
                if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
                    Settings.System.putInt(contentResolver2, "call_mic_mode", 0);
                    Settings.System.putInt(contentResolver2, "voip_call_mic_mode", 0);
                }
                AudioService audioService = audioServiceExt.mService;
                audioService.setRingerMode(2, "AS.AudioService.performSoftReset", false);
                int numStreamTypes = AudioSystem.getNumStreamTypes();
                for (int i2 = 0; i2 < numStreamTypes; i2++) {
                    AudioService.VolumeStreamState volumeStreamState =
                            audioService.mStreamStates[i2];
                    if (AudioService.mStreamVolumeAlias[i2] != 3 || i2 == 3) {
                        synchronized (volumeStreamState) {
                            for (int i3 = 0; i3 < volumeStreamState.mIndexMap.size(); i3++) {
                                try {
                                    int keyAt = volumeStreamState.mIndexMap.keyAt(i3);
                                    int i4 = AudioSystem.DEFAULT_STREAM_VOLUME[i2];
                                    if (i2 == 3 && (67109004 & keyAt) != 0) {
                                        i4 = 8;
                                    }
                                    if (i2 == 3 && (32768 & keyAt) != 0) {
                                        i4 = 15;
                                    }
                                    volumeStreamState.mIndexMap.put(keyAt, i4 * 10);
                                    volumeStreamState.applyDeviceVolume_syncVSS(keyAt);
                                    for (int i5 = numStreamTypes - 1; i5 >= 0; i5--) {
                                        int i6 = volumeStreamState.mStreamType;
                                        if (i5 != i6 && AudioService.mStreamVolumeAlias[i5] == i6) {
                                            audioService.mStreamStates[i5]
                                                    .applyDeviceVolume_syncVSS(
                                                            audioService.getDeviceForStream(i5));
                                        }
                                    }
                                    AudioService.sendMsg(
                                            audioService.mAudioHandler,
                                            1,
                                            2,
                                            keyAt,
                                            0,
                                            volumeStreamState,
                                            500);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                }
                Log.i("AS.AudioServiceExt", "performSoftReset end");
                return;
        }
    }
}
