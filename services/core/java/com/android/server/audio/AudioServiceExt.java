package com.android.server.audio;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioSystem;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import com.samsung.android.audio.Rune;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.AudioHqmHelper;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.CoverHelper;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.DvfsHelper;
import com.samsung.android.server.audio.LiveTranslatorManager;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.OmcRingtoneManager;
import com.samsung.android.server.audio.PhoneStateHelper;
import com.samsung.android.server.audio.VolumeMonitorService;
import com.samsung.android.server.audio.utils.CoreFxUtils;
import com.samsung.android.server.audio.utils.SoundAliveUtils;

import java.util.ArrayList;
import java.util.Iterator;

public final class AudioServiceExt {
    public static int mKaraokeListenbackEnabled;
    public int mAdaptSoundEnabled;
    public int mAllSoundMute;
    public final AudioSystemAdapter mAudioSystem;
    public final HandlerThread mBroadcastHandlerThread;
    public final Context mContext;
    public final CoverHelper mCoverHelper;
    public final ContentResolver mCr;
    public DesktopModeHelper mDesktopModeHelper;
    public DvfsHelper mDvfsHelper;
    public boolean mExtraVolume;
    public boolean mIsBikeMode;
    public boolean mIsPttCallVolumeEnabled;
    public final LiveTranslatorManager mLiveTranslatorManager;
    public float mMainBalance;
    public int mMainMono;
    public final MicModeManager mMicModeManager;
    public int mNbQualityMode;
    public final OmcRingtoneManager mOmcRingtoneManager;
    public PhoneStateHelper mPhoneStateHelper;
    public final AudioService mService;
    public final AudioSettingsHelper mSettingsHelper;
    public final SoundEffectsHelper mSfxHelper;
    public int mUpscalerEnabled;
    public int mVideoCallVoiceEffectMode;
    public boolean mVoipAntiHowling;
    public boolean mVoipExtraVolume;
    public int mVolumeMonitorValue;
    public final ExtBroadcastReceiver mReceiver = new ExtBroadcastReceiver(this, 0);
    public final ExtBroadcastReceiver mResetSettingsReceiver = new ExtBroadcastReceiver(this, 1);
    public boolean mScreenCall = false;
    public boolean mCallTranslationMode = false;
    public int mVoiceTxControlMode = 0;
    public int mVoiceRxControlMode = 0;
    public int mVoiceCallMonsterSoundMode = -1;
    public int mVideoCallMonsterSoundMode = -1;

    public final class ExtBroadcastReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AudioServiceExt this$0;

        public /* synthetic */ ExtBroadcastReceiver(AudioServiceExt audioServiceExt, int i) {
            this.$r8$classId = i;
            this.this$0 = audioServiceExt;
        }

        public void handleIntent(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_ON")) {
                this.this$0.mDvfsHelper.mIsScreenOn = true;
                final int i = 0;
                AudioExecutor.execute(
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.audio.AudioServiceExt$ExtBroadcastReceiver$$ExternalSyntheticLambda0
                            public final /* synthetic */ AudioServiceExt.ExtBroadcastReceiver f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i2 = i;
                                AudioServiceExt.ExtBroadcastReceiver extBroadcastReceiver =
                                        this.f$0;
                                switch (i2) {
                                    case 0:
                                        SoundAliveUtils.notifyDVFSToSoundAlive(
                                                extBroadcastReceiver.this$0.mContext, 0, true);
                                        break;
                                    default:
                                        SoundAliveUtils.notifyDVFSToSoundAlive(
                                                extBroadcastReceiver.this$0.mContext, 0, false);
                                        break;
                                }
                            }
                        });
                return;
            }
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                this.this$0.mDvfsHelper.mIsScreenOn = false;
                final int i2 = 1;
                AudioExecutor.execute(
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.audio.AudioServiceExt$ExtBroadcastReceiver$$ExternalSyntheticLambda0
                            public final /* synthetic */ AudioServiceExt.ExtBroadcastReceiver f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i2;
                                AudioServiceExt.ExtBroadcastReceiver extBroadcastReceiver =
                                        this.f$0;
                                switch (i22) {
                                    case 0:
                                        SoundAliveUtils.notifyDVFSToSoundAlive(
                                                extBroadcastReceiver.this$0.mContext, 0, true);
                                        break;
                                    default:
                                        SoundAliveUtils.notifyDVFSToSoundAlive(
                                                extBroadcastReceiver.this$0.mContext, 0, false);
                                        break;
                                }
                            }
                        });
                return;
            }
            if ("android.settings.ALL_SOUND_MUTE".equals(action)) {
                this.this$0.mAllSoundMute = intent.getIntExtra("mute", 0);
                this.this$0.setAllSoundMute();
                return;
            }
            if (action.equals("android.intent.action.USER_SWITCHED")) {
                if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            intExtra, "ACTION_USER_SWITCHED :: userId = ", "AS.AudioServiceExt");
                    this.this$0.updateThemeSound(intExtra, true);
                    return;
                }
                return;
            }
            if ("com.samsung.intent.action.WB_AMR".equals(action)) {
                AudioServiceExt audioServiceExt = this.this$0;
                audioServiceExt.getClass();
                int intExtra2 = intent.getIntExtra("EXTRA_RAT", 0);
                int intExtra3 = intent.getIntExtra("EXTRA_STATE", 0);
                AudioSystemAdapter audioSystemAdapter = audioServiceExt.mAudioSystem;
                if (intExtra3 == 9) {
                    Log.d("AS.AudioServiceExt", "wb_amr fb");
                    audioSystemAdapter.setParameters("g_call_band=fb");
                } else if (intExtra3 == 8) {
                    Log.d("AS.AudioServiceExt", "wb_amr swb");
                    audioSystemAdapter.setParameters("g_call_band=swb");
                } else if (intExtra3 == 1) {
                    Log.d("AS.AudioServiceExt", "wb_amr wb");
                    audioSystemAdapter.setParameters("g_call_band=wb");
                } else {
                    Log.d("AS.AudioServiceExt", "wb_amr nb");
                    audioSystemAdapter.setParameters("g_call_band=nb");
                }
                if (intExtra2 != 0) {
                    audioSystemAdapter.setParameters("l_call_rat_type=" + intExtra2);
                    return;
                }
                return;
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                AudioHqmHelper.sendHqmData(context, false);
                this.this$0.mAudioSystem.setParameters("dev_shutdown=true");
                return;
            }
            if ("com.sec.media.action.AUDIOCORE_LOGGING".equals(action)) {
                Log.i("AS.AudioServiceExt", "[logging] receive ACTION_AUDIOCORE_LOGGING");
                AudioHqmHelper.sendHqmData(context, false);
                return;
            }
            if ("com.sec.android.intent.action.DHR_HQM_REFRESH_REQ".equals(action)) {
                Log.i("AS.AudioServiceExt", "[logging] receive ACTION_AUDIOCORE_BIGDATA_APP");
                AudioHqmHelper.sendHqmData(context, true);
                return;
            }
            if ("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED".equals(action)) {
                Log.i("AS.AudioServiceExt", "ACTION_SUBINFO_RECORD_UPDATED received");
                PhoneStateHelper phoneStateHelper = this.this$0.mPhoneStateHelper;
                if (phoneStateHelper.mTelephonyManager != null) {
                    for (int i3 = 0; i3 < phoneStateHelper.mSimCount; i3++) {
                        PhoneStateListener phoneStateListener =
                                phoneStateHelper.mPhoneStateListener[i3];
                        if (phoneStateListener != null) {
                            phoneStateHelper.mSpecifiedTm[i3].listen(phoneStateListener, 0);
                            phoneStateHelper.mRilState = -1;
                            DirEncryptService$$ExternalSyntheticOutline0.m(
                                    i3,
                                    "call unregisterPhoneStateListener : ",
                                    "AS.PhoneStateHelper");
                        }
                    }
                }
                this.this$0.mPhoneStateHelper.registerPhoneStateListener();
                return;
            }
            if ("com.android.launcher3.quickstep.closeall".equals(action)) {
                Log.d("AS.AudioServiceExt", "onReceive close all");
                this.this$0.mAudioSystem.setParameters("l_recovery_restarting=true");
                return;
            }
            if ("com.android.phone.action.PERSONALISE_CALL_SOUND_CHANGED".equals(action)) {
                this.this$0.setNbQualityMode(intent.getIntExtra("value", 0));
                return;
            }
            if ("com.samsung.server.BatteryService.action.BATTERY_CONNECTION_STATE_CHANGED"
                    .equals(action)) {
                boolean booleanExtra = intent.getBooleanExtra("all_battery_connected", true);
                int intForUser =
                        Settings.System.getIntForUser(this.this$0.mCr, "all_sound_off", 0, -2);
                StringBuilder sb =
                        new StringBuilder("battery hotswap(connected only 1 battery) = ");
                boolean z = !booleanExtra;
                sb.append(z);
                sb.append(", mAllSoundMute=");
                sb.append(this.this$0.mAllSoundMute);
                sb.append(", allSoundOff=");
                sb.append(intForUser);
                Log.i("AS.AudioServiceExt", sb.toString());
                if (intForUser != 1) {
                    AudioServiceExt audioServiceExt2 = this.this$0;
                    if (z != audioServiceExt2.mAllSoundMute) {
                        audioServiceExt2.mAllSoundMute = z ? 1 : 0;
                        audioServiceExt2.setAllSoundMute();
                    }
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        handleIntent(context, intent);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                default:
                    if ("com.samsung.intent.action.SETTINGS_SOFT_RESET"
                            .equals(intent.getAction())) {
                        AudioExecutor.execute(
                                new AudioServiceExt$$ExternalSyntheticLambda1(1, this));
                        break;
                    }
                    break;
            }
        }
    }

    public final class ExtSettingsObserver extends ContentObserver {
        public ExtSettingsObserver() {
            super(new Handler());
            if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME) {
                AudioServiceExt.this.mCr.registerContentObserver(
                        Settings.Global.getUriFor("theme_touch_sound"), false, this);
                AudioServiceExt.this.mCr.registerContentObserver(
                        Settings.System.getUriFor("system_sound"), false, this, -1);
            }
            AudioServiceExt.this.mCr.registerContentObserver(
                    Settings.System.getUriFor("hearing_musiccheck"), false, this, -1);
            AudioServiceExt.this.mCr.registerContentObserver(
                    Settings.System.getUriFor("k2hd_effect"), false, this, -1);
            if (Rune.SEC_AUDIO_BIKE_MODE) {
                AudioServiceExt.this.mCr.registerContentObserver(
                        Settings.Secure.getUriFor("isBikeMode"), false, this, -1);
            }
            if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                AudioServiceExt.this.mCr.registerContentObserver(
                        Settings.Global.getUriFor("headphone_monitoring"), false, this);
            }
            if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
                AudioServiceExt.this.mCr.registerContentObserver(
                        Settings.Global.getUriFor("volume_monitor"), false, this);
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME) {
                AudioServiceExt.this.updateThemeSound(ActivityManager.getCurrentUser(), false);
            }
            int intForUser =
                    Settings.System.getIntForUser(
                            AudioServiceExt.this.mCr, "hearing_musiccheck", 0, -2);
            int intForUser2 =
                    Settings.System.getIntForUser(AudioServiceExt.this.mCr, "k2hd_effect", 0, -2);
            AudioServiceExt audioServiceExt = AudioServiceExt.this;
            if (audioServiceExt.mAdaptSoundEnabled != intForUser) {
                audioServiceExt.mAdaptSoundEnabled = intForUser;
                CoreFxUtils.setAdaptSound(audioServiceExt.mContext, intForUser);
            } else if (audioServiceExt.mUpscalerEnabled != intForUser2) {
                audioServiceExt.mUpscalerEnabled = intForUser2;
                CoreFxUtils.setUpScalerMode(intForUser2);
            }
            if (Rune.SEC_AUDIO_BIKE_MODE) {
                AudioServiceExt audioServiceExt2 = AudioServiceExt.this;
                audioServiceExt2.mIsBikeMode =
                        Settings.Secure.getInt(
                                        audioServiceExt2.mContext.getContentResolver(),
                                        "isBikeMode",
                                        0)
                                == 1;
            }
            if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                AudioServiceExt.mKaraokeListenbackEnabled =
                        Settings.Global.getInt(AudioServiceExt.this.mCr, "headphone_monitoring", 0);
                RecordingActivityMonitor recordingActivityMonitor =
                        AudioServiceExt.this.mService.mRecordMonitor;
                synchronized (recordingActivityMonitor.mRecordStates) {
                    try {
                        Iterator it =
                                ((ArrayList) recordingActivityMonitor.mRecordStates).iterator();
                        int i = 0;
                        int i2 = 0;
                        while (it.hasNext()) {
                            RecordingActivityMonitor.RecordingState recordingState =
                                    (RecordingActivityMonitor.RecordingState) it.next();
                            if (recordingState.isActiveConfiguration()) {
                                String[] packagesForUid =
                                        recordingActivityMonitor.mPackMan.getPackagesForUid(
                                                recordingState.mConfig.getClientUid());
                                if (packagesForUid != null && packagesForUid.length > 0) {
                                    if (recordingActivityMonitor.mAudioSettingsHelper
                                            .checkAppCategory(
                                                    packagesForUid[0],
                                                    "karaoke_listenback_allow")) {
                                        i++;
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                        }
                        if (i > 0 && i2 == 0) {
                            AudioSystem.setParameters(
                                    "l_effect_listenback_key;state="
                                            + AudioServiceExt.mKaraokeListenbackEnabled);
                        }
                    } finally {
                    }
                }
            }
            if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
                int i3 = Settings.Global.getInt(AudioServiceExt.this.mCr, "volume_monitor", 0);
                AudioServiceExt audioServiceExt3 = AudioServiceExt.this;
                if (audioServiceExt3.mVolumeMonitorValue != i3) {
                    audioServiceExt3.mVolumeMonitorValue = i3;
                    VolumeMonitorService.getInstance(audioServiceExt3.mContext)
                            .setVolumeMonitorOnOff(i3 == 1);
                    if (i3 == 2) {
                        VolumeMonitorService.getInstance(AudioServiceExt.this.mContext)
                                .resetByDataClear();
                    }
                }
            }
        }
    }

    public AudioServiceExt(
            Context context,
            AudioService audioService,
            AudioSystemAdapter audioSystemAdapter,
            AudioSettingsHelper audioSettingsHelper,
            SoundEffectsHelper soundEffectsHelper,
            MicModeManager micModeManager,
            LiveTranslatorManager liveTranslatorManager) {
        this.mVideoCallVoiceEffectMode = Rune.SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT ? 100 : -1;
        this.mVoipExtraVolume = false;
        this.mVoipAntiHowling = false;
        this.mExtraVolume = false;
        this.mIsPttCallVolumeEnabled = false;
        this.mVolumeMonitorValue = 0;
        this.mContext = context;
        this.mService = audioService;
        this.mAudioSystem = audioSystemAdapter;
        this.mSettingsHelper = audioSettingsHelper;
        this.mSfxHelper = soundEffectsHelper;
        this.mMicModeManager = micModeManager;
        this.mLiveTranslatorManager = liveTranslatorManager;
        ContentResolver contentResolver = context.getContentResolver();
        this.mCr = contentResolver;
        HandlerThread handlerThread = new HandlerThread("AudioServiceExt Broadcast");
        this.mBroadcastHandlerThread = handlerThread;
        handlerThread.start();
        if (OmcRingtoneManager.sInstance == null) {
            OmcRingtoneManager.sInstance = new OmcRingtoneManager();
        }
        this.mOmcRingtoneManager = OmcRingtoneManager.sInstance;
        this.mCoverHelper = CoverHelper.getInstance();
        this.mNbQualityMode =
                Settings.Global.getInt(contentResolver, "personalise_call_sound_soft", 0);
        if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
            this.mVoipExtraVolume =
                    Settings.System.getInt(contentResolver, "voip_extra_volume", 0) != 0;
        }
        if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
            this.mVoipAntiHowling =
                    Settings.System.getInt(contentResolver, "voip_anti_howling", 0) != 0;
        }
        this.mAdaptSoundEnabled =
                Settings.System.getIntForUser(contentResolver, "hearing_musiccheck", 0, -2);
        this.mUpscalerEnabled =
                Settings.System.getIntForUser(contentResolver, "k2hd_effect", 0, -2);
        if (Rune.SEC_AUDIO_BIKE_MODE) {
            this.mIsBikeMode = Settings.Secure.getInt(contentResolver, "isBikeMode", 0) == 1;
        }
        if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
            mKaraokeListenbackEnabled =
                    Settings.Global.getInt(contentResolver, "headphone_monitoring", 0);
        }
        if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
            this.mVolumeMonitorValue = Settings.Global.getInt(contentResolver, "volume_monitor", 0);
            VolumeMonitorService.getInstance(context)
                    .setVolumeMonitorOnOff(this.mVolumeMonitorValue == 1);
        }
    }

    public static int getIntValueFromString(int i, String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e("AS.AudioServiceExt", "NumberFormatException", e);
            return i;
        }
    }

    public final void restoreLiveTranslator() {
        LiveTranslatorManager liveTranslatorManager = this.mLiveTranslatorManager;
        if (liveTranslatorManager == null || !liveTranslatorManager.mIsVoiceCapable) {
            return;
        }
        boolean z =
                Settings.System.getInt(LiveTranslatorManager.mCr, "voip_translator_enable", 0) != 0;
        String string =
                Settings.System.getString(LiveTranslatorManager.mCr, "voip_translator_package");
        Log.i(
                "LiveTranslatorManager",
                "restoreVoipTranslator enabled = " + z + ", packageName = " + string);
        if (z) {
            liveTranslatorManager.setVoipTranslator(string, true);
        }
    }

    public final void setAllSoundMute() {
        this.mAudioSystem.setParameters(
                "l_all_sound_mute_enable=".concat(this.mAllSoundMute == 1 ? "true" : "false"));
        SystemProperties.set("persist.audio.allsoundmute", Integer.toString(this.mAllSoundMute));
    }

    public final void setNbQualityMode(int i) {
        this.mNbQualityMode = i;
        if (i > 0) {
            AudioSystem.setParameters("l_call_nb_quality_enable=true");
        } else {
            AudioSystem.setParameters("l_call_nb_quality_enable=false");
        }
    }

    public final void setSystemSettingForSoundAssistant(int i, String str) {
        if ("sound_balance".equals(str)
                || "mono_audio_db".equals(str)
                || "adjust_media_volume_only".equals(str)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if ("sound_balance".equals(str)) {
                    Settings.System.putFloatForUser(
                            this.mCr, "master_balance", (i - 50) / 50.0f, -2);
                } else if ("mono_audio_db".equals(str)) {
                    Settings.System.putIntForUser(this.mCr, "master_mono", i, -2);
                }
                Settings.System.putIntForUser(this.mCr, str, i, -2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                this.mSettingsHelper.setIntValue(i, str);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void updateCallGuardInfo(int i, int i2, boolean z) {
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "callguard: mode(", "), pid(", "), skipSet(");
        m.append(z);
        m.append(")");
        Log.i("AS.AudioServiceExt", m.toString());
        this.mAudioSystem.setParameters(
                new AudioParameter.Builder()
                        .setParam("l_guard_call_mode", i)
                        .setParam("l_guard_call_mode_calling_pid", i2)
                        .setParam("l_guard_call_mode_skip", z ? 1 : 0)
                        .build()
                        .toString());
    }

    public final void updateThemeSound(int i, boolean z) {
        boolean z2;
        boolean z3;
        String stringForUser = Settings.Global.getStringForUser(this.mCr, "theme_touch_sound", i);
        String stringForUser2 = Settings.System.getStringForUser(this.mCr, "system_sound", i);
        SoundEffectsHelper soundEffectsHelper = this.mSfxHelper;
        if (soundEffectsHelper != null) {
            if (TextUtils.isEmpty(stringForUser)) {
                if (!TextUtils.isEmpty(soundEffectsHelper.mThemeTouchSoundPath)) {
                    Log.v("AS.SfxHelper", "updateThemeSound( theme is released )");
                    soundEffectsHelper.mThemeTouchSoundPath = null;
                    z2 = true;
                }
                z2 = false;
            } else {
                if (!TextUtils.equals(stringForUser, soundEffectsHelper.mThemeTouchSoundPath)) {
                    Log.v("AS.SfxHelper", "updateThemeSound( theme is changed )");
                    soundEffectsHelper.mThemeTouchSoundPath = stringForUser;
                    z2 = true;
                }
                z2 = false;
            }
            if (TextUtils.isEmpty(stringForUser2)) {
                if (!TextUtils.isEmpty(soundEffectsHelper.mSystemSoundFromSoundTheme)) {
                    Log.v("AS.SfxHelper", "updateThemeSound( Change to default )");
                    soundEffectsHelper.mSystemSoundFromSoundTheme = null;
                    soundEffectsHelper.mPrevSystemSoundFromSoundTheme = null;
                    z3 = true;
                }
                z3 = false;
            } else {
                if (!TextUtils.equals(
                        stringForUser2, soundEffectsHelper.mSystemSoundFromSoundTheme)) {
                    Log.v("AS.SfxHelper", "updateThemeSound( Change to " + stringForUser2 + " )");
                    soundEffectsHelper.mSystemSoundFromSoundTheme = stringForUser2;
                    if (!TextUtils.equals(stringForUser2, "Open_theme")) {
                        soundEffectsHelper.mPrevSystemSoundFromSoundTheme = stringForUser2;
                    }
                    z3 = true;
                }
                z3 = false;
            }
            if (z || z2 || z3) {
                soundEffectsHelper.mUpdateSystemSound = true;
                if (!soundEffectsHelper.mSfxHandler.hasMessages(1)) {
                    soundEffectsHelper.sendMsg(1, 0, 0, null, 300);
                }
                if (soundEffectsHelper.mSfxHandler.hasMessages(0)) {
                    return;
                }
                soundEffectsHelper.sendMsg(0, 0, 0, null, 300);
            }
        }
    }
}
