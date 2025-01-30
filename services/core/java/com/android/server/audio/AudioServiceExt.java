package com.android.server.audio;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioSystem;
import android.os.Binder;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.AudioHqmHelper;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.CoverHelper;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.DvfsHelper;
import com.samsung.android.server.audio.FactoryUtils;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.OmcRingtoneManager;
import com.samsung.android.server.audio.PhoneStateHelper;
import com.samsung.android.server.audio.VolumeMonitorService;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.CoreFxUtils;
import com.samsung.android.server.audio.utils.KaraokeUtils;
import com.samsung.android.server.audio.utils.SoundAliveUtils;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes.dex */
public class AudioServiceExt {
  public static int mKaraokeListenbackEnabled;
  public int mAdaptSoundEnabled;
  public int mAllSoundMute;
  public final AudioSystemAdapter mAudioSystem;
  public final Context mContext;
  public final CoverHelper mCoverHelper;
  public final ContentResolver mCr;
  public DesktopModeHelper mDesktopModeHelper;
  public DvfsHelper mDvfsHelper;
  public boolean mExtraVolume;
  public boolean mIsBikeMode;
  public boolean mIsPttCallVolumeEnabled;
  public float mMainBalance;
  public int mMainMono;
  public final MicModeManager mMicModeManager;
  public int mNbQualityMode;
  public OmcRingtoneManager mOmcRingtoneManager;
  public PhoneStateHelper mPhoneStateHelper;
  public final AudioService mService;
  public final AudioSettingsHelper mSettingsHelper;
  public ExtSettingsObserver mSettingsObserver;
  public final SoundEffectsHelper mSfxHelper;
  public int mUpscalerEnabled;
  public int mVideoCallVoiceEffectMode;
  public boolean mVoipAntiHowling;
  public boolean mVoipExtraVolume;
  public int mVolumeMonitorValue;
  public final BroadcastReceiver mReceiver = new ExtBroadcastReceiver();
  public final BroadcastReceiver mResetSettingsReceiver = new ResetSettingsReceiver();
  public boolean mScreenCall = false;
  public boolean mCallTranslationMode = false;
  public int mVoiceTxControlMode = 0;
  public int mVoiceRxControlMode = 0;
  public int mVoiceCallMonsterSoundMode = -1;
  public int mVideoCallMonsterSoundMode = -1;

  /* JADX WARN: Multi-variable type inference failed */
  public AudioServiceExt(
      Context context,
      AudioService audioService,
      AudioSystemAdapter audioSystemAdapter,
      AudioSettingsHelper audioSettingsHelper,
      SoundEffectsHelper soundEffectsHelper,
      MicModeManager micModeManager) {
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
    this.mCr = context.getContentResolver();
    this.mOmcRingtoneManager = OmcRingtoneManager.getInstance();
    this.mCoverHelper = CoverHelper.getInstance();
    readPersistedCustomSettings();
  }

  public void systemReady() {
    this.mSettingsObserver = new ExtSettingsObserver();
    registerReceivers();
    if (this.mAllSoundMute == 1) {
      setAllSoundMute();
    }
    this.mDvfsHelper = DvfsHelper.getInstance(this.mContext);
    PhoneStateHelper phoneStateHelper = PhoneStateHelper.getInstance(this.mContext);
    this.mPhoneStateHelper = phoneStateHelper;
    phoneStateHelper.registerPhoneStateListener();
    DesktopModeHelper desktopModeHelper = DesktopModeHelper.getInstance(this.mContext);
    this.mDesktopModeHelper = desktopModeHelper;
    desktopModeHelper.registerListener();
  }

  public void bootCompleted() {
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
      this.mAudioSystem.setParameters("l_call_voip_extra_volume_enable=" + this.mVoipExtraVolume);
    }
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
      this.mAudioSystem.setParameters("l_call_voip_extra_volume_enable=" + this.mVoipAntiHowling);
    }
    this.mCoverHelper.init(this.mContext);
    AudioHqmHelper.startLogging(this.mContext);
    CoreFxUtils.setAdaptSound(this.mContext, this.mAdaptSoundEnabled);
    CoreFxUtils.setUpScalerMode(this.mUpscalerEnabled);
    setNbQualityMode(0);
    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
      restoreMicMode();
    }
  }

  public void onAudioServerDied() {
    setAllSoundMute();
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
      restoreVoipExtraVolume();
    }
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
      restoreVoipAntiHowling();
    }
    if (Rune.SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT) {
      restoreVideoCallVoiceEffect();
    }
    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
      restoreMicMode();
    }
    if (Rune.SEC_AUDIO_CALL_MONSTER_SOUND) {
      restoreCallMonsterSoundMode();
    }
    restoreDexState();
    CoreFxUtils.setUpScalerMode(this.mUpscalerEnabled);
    CoreFxUtils.setAdaptSound(this.mContext, this.mAdaptSoundEnabled);
    AudioUtils.sendBroadcastToUser(
        this.mContext,
        new Intent("com.samsung.intent.action.MEDIA_SERVER_REBOOTED"),
        UserHandle.CURRENT,
        null);
    setNbQualityMode(getNbQualityMode());
    if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
      getVolumeMonitorService().audioServerDied();
    }
    AudioHqmHelper.increaseAudioServerResetCount();
  }

  public final void readPersistedCustomSettings() {
    ContentResolver contentResolver = this.mCr;
    this.mNbQualityMode = Settings.Global.getInt(contentResolver, "personalise_call_sound_soft", 0);
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
      this.mVoipExtraVolume = Settings.System.getInt(contentResolver, "voip_extra_volume", 0) != 0;
    }
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
      this.mVoipAntiHowling = Settings.System.getInt(contentResolver, "voip_anti_howling", 0) != 0;
    }
    this.mAdaptSoundEnabled =
        Settings.System.getIntForUser(contentResolver, "hearing_musiccheck", 0, -2);
    this.mUpscalerEnabled = Settings.System.getIntForUser(contentResolver, "k2hd_effect", 0, -2);
    if (Rune.SEC_AUDIO_BIKE_MODE) {
      this.mIsBikeMode = Settings.Secure.getInt(contentResolver, "isBikeMode", 0) == 1;
    }
    if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
      mKaraokeListenbackEnabled =
          Settings.Global.getInt(contentResolver, "headphone_monitoring", 0);
    }
    if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
      this.mVolumeMonitorValue = Settings.Global.getInt(contentResolver, "volume_monitor", 0);
      getVolumeMonitorService().setVolumeMonitorOnOff(this.mVolumeMonitorValue == 1);
    }
  }

  public final void registerReceivers() {
    IntentFilter intentFilter = new IntentFilter();
    addExtIntentFilter(intentFilter);
    this.mContext.registerReceiverAsUser(
        this.mReceiver, UserHandle.ALL, intentFilter, null, null, 2);
    IntentFilter intentFilter2 = new IntentFilter();
    addResetSettingsIntentFilter(intentFilter2);
    this.mContext.registerReceiverAsUser(
        this.mResetSettingsReceiver,
        UserHandle.ALL,
        intentFilter2,
        "com.sec.android.settings.permission.SOFT_RESET",
        null,
        2);
  }

  public final void addExtIntentFilter(IntentFilter intentFilter) {
    intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
    intentFilter.addAction("android.intent.action.USER_SWITCHED");
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    intentFilter.addAction("android.settings.ALL_SOUND_MUTE");
    intentFilter.addAction("com.samsung.intent.action.WB_AMR");
    intentFilter.addAction("com.sec.media.action.AUDIOCORE_LOGGING");
    intentFilter.addAction("com.sec.android.intent.action.DHR_HQM_REFRESH_REQ");
    intentFilter.addAction("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED");
    intentFilter.addAction("com.android.launcher3.quickstep.closeall");
    intentFilter.addAction("com.android.phone.action.PERSONALISE_CALL_SOUND_CHANGED");
  }

  public class ExtBroadcastReceiver extends BroadcastReceiver {
    public ExtBroadcastReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      try {
        handleIntent(context, intent);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void handleIntent(Context context, Intent intent) {
      String action = intent.getAction();
      if (action.equals("android.intent.action.SCREEN_ON")) {
        AudioServiceExt.this.mDvfsHelper.setScreenOn(true);
        AudioExecutor.execute(
            new Runnable() { // from class:
              // com.android.server.audio.AudioServiceExt$ExtBroadcastReceiver$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                AudioServiceExt.ExtBroadcastReceiver.this.lambda$handleIntent$0();
              }
            });
        return;
      }
      if (action.equals("android.intent.action.SCREEN_OFF")) {
        AudioServiceExt.this.mDvfsHelper.setScreenOn(false);
        AudioExecutor.execute(
            new Runnable() { // from class:
              // com.android.server.audio.AudioServiceExt$ExtBroadcastReceiver$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                AudioServiceExt.ExtBroadcastReceiver.this.lambda$handleIntent$1();
              }
            });
        return;
      }
      if ("android.settings.ALL_SOUND_MUTE".equals(action)) {
        AudioServiceExt.this.mAllSoundMute = intent.getIntExtra("mute", 0);
        AudioServiceExt.this.setAllSoundMute();
        return;
      }
      if (action.equals("android.intent.action.USER_SWITCHED")) {
        if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME) {
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
          Log.d("AS.AudioServiceExt", "ACTION_USER_SWITCHED :: userId = " + intExtra);
          AudioServiceExt.this.updateThemeSound(intExtra, true);
          return;
        }
        return;
      }
      if ("com.samsung.intent.action.WB_AMR".equals(action)) {
        AudioServiceExt.this.updateCallBandInfo(intent);
        return;
      }
      if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
        AudioHqmHelper.sendHqmData(context, false);
        AudioServiceExt.this.mAudioSystem.setParameters("dev_shutdown=true");
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
        AudioServiceExt.this.mPhoneStateHelper.unregisterPhoneStateListener();
        AudioServiceExt.this.mPhoneStateHelper.registerPhoneStateListener();
      } else if ("com.android.launcher3.quickstep.closeall".equals(action)) {
        Log.d("AS.AudioServiceExt", "onReceive close all");
        AudioServiceExt.this.mAudioSystem.setParameters("l_recovery_restarting=true");
      } else if ("com.android.phone.action.PERSONALISE_CALL_SOUND_CHANGED".equals(action)) {
        AudioServiceExt.this.setNbQualityMode(intent.getIntExtra("value", 0));
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$0() {
      SoundAliveUtils.notifyDVFSToSoundAlive(AudioServiceExt.this.mContext, 0, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$1() {
      SoundAliveUtils.notifyDVFSToSoundAlive(AudioServiceExt.this.mContext, 0, false);
    }
  }

  public class ExtSettingsObserver extends ContentObserver {
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
    public void onChange(boolean z) {
      int i;
      super.onChange(z);
      if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME) {
        AudioServiceExt.this.updateThemeSound(ActivityManager.getCurrentUser(), false);
      }
      int intForUser =
          Settings.System.getIntForUser(AudioServiceExt.this.mCr, "hearing_musiccheck", 0, -2);
      int intForUser2 =
          Settings.System.getIntForUser(AudioServiceExt.this.mCr, "k2hd_effect", 0, -2);
      if (AudioServiceExt.this.mAdaptSoundEnabled != intForUser) {
        AudioServiceExt.this.mAdaptSoundEnabled = intForUser;
        CoreFxUtils.setAdaptSound(
            AudioServiceExt.this.mContext, AudioServiceExt.this.mAdaptSoundEnabled);
      } else if (AudioServiceExt.this.mUpscalerEnabled != intForUser2) {
        AudioServiceExt.this.mUpscalerEnabled = intForUser2;
        CoreFxUtils.setUpScalerMode(AudioServiceExt.this.mUpscalerEnabled);
      }
      if (Rune.SEC_AUDIO_BIKE_MODE) {
        AudioServiceExt audioServiceExt = AudioServiceExt.this;
        audioServiceExt.mIsBikeMode =
            Settings.Secure.getInt(audioServiceExt.mContext.getContentResolver(), "isBikeMode", 0)
                == 1;
      }
      if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
        AudioServiceExt.mKaraokeListenbackEnabled =
            Settings.Global.getInt(AudioServiceExt.this.mCr, "headphone_monitoring", 0);
        if (AudioServiceExt.this.mService.getRecordMonitor().isOnlyKaraokeRecordingActive()) {
          KaraokeUtils.setKaraokeListenback(AudioServiceExt.mKaraokeListenbackEnabled);
        }
      }
      if (!Rune.SEC_AUDIO_VOLUME_MONITOR
          || AudioServiceExt.this.mVolumeMonitorValue
              == (i = Settings.Global.getInt(AudioServiceExt.this.mCr, "volume_monitor", 0))) {
        return;
      }
      AudioServiceExt.this.mVolumeMonitorValue = i;
      AudioServiceExt.this.getVolumeMonitorService().setVolumeMonitorOnOff(i == 1);
      if (i == 2) {
        AudioServiceExt.this.getVolumeMonitorService().resetByDataClear();
      }
    }
  }

  public final void addResetSettingsIntentFilter(IntentFilter intentFilter) {
    intentFilter.addAction("com.samsung.intent.action.SETTINGS_SOFT_RESET");
  }

  public class ResetSettingsReceiver extends BroadcastReceiver {
    public ResetSettingsReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(intent.getAction())) {
        AudioExecutor.execute(
            new Runnable() { // from class:
              // com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                AudioServiceExt.ResetSettingsReceiver.this.lambda$onReceive$0();
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReceive$0() {
      AudioServiceExt.this.performSoftReset();
    }
  }

  public final void performSoftReset() {
    ContentResolver contentResolver = this.mCr;
    Log.i("AS.AudioServiceExt", "performSoftReset start");
    this.mSettingsHelper.resetTable();
    this.mAllSoundMute = 0;
    Settings.System.putIntForUser(contentResolver, "all_sound_off", 0, -2);
    setAllSoundMute();
    Settings.System.putIntForUser(contentResolver, "master_mono", 0, -2);
    if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
      Settings.Global.putInt(contentResolver, "volume_monitor", 0);
      this.mVolumeMonitorValue = 0;
      getVolumeMonitorService().resetByDataClear();
    }
    this.mAdaptSoundEnabled = 0;
    Settings.System.putIntForUser(contentResolver, "hearing_musiccheck", 0, -2);
    CoreFxUtils.setAdaptSound(this.mContext, this.mAdaptSoundEnabled);
    this.mUpscalerEnabled = 0;
    Settings.System.putIntForUser(contentResolver, "k2hd_effect", 0, -2);
    CoreFxUtils.setUpScalerMode(this.mUpscalerEnabled);
    Settings.System.putIntForUser(contentResolver, "sound_alive_effect", 0, -2);
    Settings.System.putIntForUser(contentResolver, "tube_amp_effect", 0, -2);
    Settings.System.putIntForUser(
        contentResolver, "adjust_media_volume_only", getDefaultVolumeOption(), -2);
    if (this.mService.getMediaVolumeSteps() != null) {
      Settings.System.putString(this.mCr, "sec_volume_steps", "");
    }
    if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
      mKaraokeListenbackEnabled = 0;
      Settings.Global.putInt(contentResolver, "headphone_monitoring", 0);
    }
    setNbQualityMode(0);
    Settings.Global.putInt(contentResolver, "personalise_call_sound_soft", 0);
    setDefaultMainBalance();
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
      setDefaultModeVoipExtraVolume();
    }
    if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
      setDefaultModeVoipAntiHowling();
    }
    Settings.Global.putInt(contentResolver, "multisound_state", 0);
    Settings.System.putString(contentResolver, "multisound_app", null);
    Settings.System.putInt(contentResolver, "multisound_devicetype", -1);
    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
      Settings.System.putInt(contentResolver, "call_mic_mode", 0);
      Settings.System.putInt(contentResolver, "voip_call_mic_mode", 0);
    }
    this.mService.resetRingerMode();
    Log.i("AS.AudioServiceExt", "performSoftReset end");
  }

  public final void updateCallBandInfo(Intent intent) {
    int intExtra = intent.getIntExtra("EXTRA_RAT", 0);
    int intExtra2 = intent.getIntExtra("EXTRA_STATE", 0);
    if (intExtra2 == 9) {
      Log.d("AS.AudioServiceExt", "wb_amr fb");
      this.mAudioSystem.setParameters("g_call_band=fb");
    } else if (intExtra2 == 8) {
      Log.d("AS.AudioServiceExt", "wb_amr swb");
      this.mAudioSystem.setParameters("g_call_band=swb");
    } else if (intExtra2 == 1) {
      Log.d("AS.AudioServiceExt", "wb_amr wb");
      this.mAudioSystem.setParameters("g_call_band=wb");
    } else {
      Log.d("AS.AudioServiceExt", "wb_amr nb");
      this.mAudioSystem.setParameters("g_call_band=nb");
    }
    if (intExtra != 0) {
      this.mAudioSystem.setParameters("l_call_rat_type=" + intExtra);
    }
  }

  public boolean isScreenCall() {
    return this.mScreenCall;
  }

  public void setScreenCall(boolean z) {
    this.mScreenCall = z;
  }

  public boolean isCallTranslationMode() {
    return this.mCallTranslationMode;
  }

  public void setCallTranslationMode(boolean z) {
    this.mCallTranslationMode = z;
  }

  public int getVoiceTxControlMode() {
    return this.mVoiceTxControlMode;
  }

  public void setVoiceTxControlMode(int i) {
    this.mVoiceTxControlMode = i;
  }

  public int getVoiceRxControlMode() {
    return this.mVoiceRxControlMode;
  }

  public void setVoiceRxControlMode(int i) {
    this.mVoiceRxControlMode = i;
  }

  public void setNbQualityMode(int i) {
    this.mNbQualityMode = i;
    if (i > 0) {
      AudioSystem.setParameters("l_call_nb_quality_enable=true");
    } else {
      AudioSystem.setParameters("l_call_nb_quality_enable=false");
    }
  }

  public int getNbQualityMode() {
    return this.mNbQualityMode;
  }

  public void updateMonoSetting(boolean z) {
    this.mMainMono = z ? 1 : 0;
    if (z != this.mSettingsHelper.getIntValue("mono_audio_db", 0)) {
      this.mSettingsHelper.removeValue("mono_audio_db");
    }
  }

  public void setVoiceCallMonsterSoundMode(int i) {
    this.mVoiceCallMonsterSoundMode = i;
  }

  public void setVideoCallMonsterSoundMode(int i) {
    this.mVideoCallMonsterSoundMode = i;
  }

  public final void restoreCallMonsterSoundMode() {
    if (this.mVoiceCallMonsterSoundMode != 1) {
      this.mAudioSystem.setParameters(
          "l_call_nc_booster_enable=" + this.mVoiceCallMonsterSoundMode);
    }
    if (this.mVideoCallMonsterSoundMode != -1) {
      this.mAudioSystem.setParameters(
          "l_mic_input_control_mode_2mic=" + this.mVideoCallMonsterSoundMode);
    }
  }

  public void setVideoCallVoiceEffectMode(int i) {
    this.mVideoCallVoiceEffectMode = i;
  }

  public final void restoreVideoCallVoiceEffect() {
    if ((!Rune.SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT || this.mVideoCallVoiceEffectMode == -1)
        && (!Rune.SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT
            || this.mVideoCallVoiceEffectMode == 100)) {
      return;
    }
    this.mAudioSystem.setParameters("l_mic_input_control_mode=" + this.mVideoCallVoiceEffectMode);
  }

  public final void restoreMicMode() {
    MicModeManager micModeManager = this.mMicModeManager;
    if (micModeManager != null) {
      micModeManager.restoreMicMode();
      this.mMicModeManager.restoreVoipTranslator();
    }
  }

  public void setVoipExtraVolume(boolean z) {
    this.mVoipExtraVolume = z;
  }

  public void setDefaultModeVoipExtraVolume() {
    this.mVoipExtraVolume = false;
    Settings.System.putInt(this.mCr, "voip_extra_volume", 0);
  }

  public final void restoreVoipExtraVolume() {
    this.mAudioSystem.setParameters("l_call_voip_extra_volume_enable=" + this.mVoipExtraVolume);
  }

  public void setVoipAntiHowling(boolean z) {
    this.mVoipAntiHowling = z;
  }

  public void setDefaultModeVoipAntiHowling() {
    this.mVoipAntiHowling = false;
    Settings.System.putInt(this.mCr, "voip_anti_howling", 0);
  }

  public final void restoreVoipAntiHowling() {
    this.mAudioSystem.setParameters("l_call_voip_extra_volume_enable=" + this.mVoipAntiHowling);
  }

  public boolean isExtraVolume() {
    return this.mExtraVolume;
  }

  public void setExtraVolume(boolean z) {
    this.mExtraVolume = z;
  }

  public float getMainBalance() {
    return this.mMainBalance;
  }

  public void setDefaultMainBalance() {
    this.mMainBalance = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    Settings.System.putFloatForUser(
        this.mCr, "master_balance", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -2);
    Settings.System.putFloatForUser(
        this.mCr, "speaker_balance", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -2);
  }

  public void updateBalance(float f) {
    float floatForUser =
        Settings.System.getFloatForUser(
            this.mCr, "speaker_balance", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -2);
    this.mAudioSystem.setParameters("l_speaker_balance=" + floatForUser);
    this.mMainBalance = f;
    if (Float.compare(
            this.mMainBalance, (this.mSettingsHelper.getIntValue("sound_balance", 50) - 50) / 50.0f)
        != 0) {
      this.mSettingsHelper.removeValue("sound_balance");
    }
  }

  public List getExcludedRingtoneTitles(int i) {
    if (i == 2) {
      return this.mOmcRingtoneManager.getExcludedNotifications();
    }
    return this.mOmcRingtoneManager.getExcludedRingtones();
  }

  public void updateThemeSound(int i, boolean z) {
    String stringForUser = Settings.Global.getStringForUser(this.mCr, "theme_touch_sound", i);
    String stringForUser2 = Settings.System.getStringForUser(this.mCr, "system_sound", i);
    SoundEffectsHelper soundEffectsHelper = this.mSfxHelper;
    if (soundEffectsHelper != null) {
      soundEffectsHelper.updateThemeSound(stringForUser, stringForUser2, z);
    }
  }

  public boolean isCoverOpen() {
    return this.mCoverHelper.isCoverOpen();
  }

  public void setCoverSafetyVolume(boolean z) {
    this.mCoverHelper.setCoverSafetyVolume(z);
  }

  public boolean isCoverSafetyVolume() {
    return this.mCoverHelper.isCoverSafetyVolume();
  }

  public final void restoreDexState() {
    this.mDesktopModeHelper.restoreDexState();
  }

  public boolean isPttCallVolumeEnabled() {
    return this.mIsPttCallVolumeEnabled;
  }

  public void setPttCallVolumeEnabled(boolean z) {
    this.mIsPttCallVolumeEnabled = z;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyDVFSToSoundAlive$0(Context context, int i) {
    SoundAliveUtils.notifyDVFSToSoundAlive(context, i, this.mDvfsHelper.getIsScreenOn());
  }

  public void notifyDVFSToSoundAlive(final Context context, final int i) {
    AudioExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.audio.AudioServiceExt$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            AudioServiceExt.this.lambda$notifyDVFSToSoundAlive$0(context, i);
          }
        });
  }

  public void startCPUBoostForVoIP(Context context) {
    this.mDvfsHelper.startCPUBoostForVoIP(this.mContext);
  }

  public void stopCPUBoostForVoIP() {
    this.mDvfsHelper.stopCPUBoostForVoIP();
  }

  public boolean isBikeMode() {
    return this.mIsBikeMode;
  }

  public int getAllSoundMute() {
    return this.mAllSoundMute;
  }

  public final void setAllSoundMuteToNative() {
    AudioSystemAdapter audioSystemAdapter = this.mAudioSystem;
    StringBuilder sb = new StringBuilder();
    sb.append("l_all_sound_mute_enable=");
    sb.append(this.mAllSoundMute == 1 ? "true" : "false");
    audioSystemAdapter.setParameters(sb.toString());
  }

  public final void setAllSoundMute() {
    setAllSoundMuteToNative();
    SystemProperties.set("persist.audio.allsoundmute", Integer.toString(this.mAllSoundMute));
  }

  public void readAllSoundMuteUserRestriction(int i) {
    this.mAllSoundMute = Settings.System.getIntForUser(this.mCr, "all_sound_off", 0, i);
    Log.d("AS.AudioServiceExt", "readUserRestrictions mAllSoundMute = " + this.mAllSoundMute);
  }

  public VolumeMonitorService getVolumeMonitorService() {
    return VolumeMonitorService.getInstance(this.mContext);
  }

  public static int getDefaultVolumeOption() {
    return !FactoryUtils.isFactoryMode() ? 1 : 0;
  }

  public void setSystemSettingForSoundAssistant(String str, int i) {
    if ("sound_balance".equals(str)
        || "mono_audio_db".equals(str)
        || "adjust_media_volume_only".equals(str)) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if ("sound_balance".equals(str)) {
          Settings.System.putFloatForUser(this.mCr, "master_balance", (i - 50) / 50.0f, -2);
        } else if ("mono_audio_db".equals(str)) {
          Settings.System.putIntForUser(this.mCr, "master_mono", i, -2);
        }
        Settings.System.putIntForUser(this.mCr, str, i, -2);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        this.mSettingsHelper.setIntValue(str, i);
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
  }

  public int getIntValueFromString(String str, int i) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      Log.e("AS.AudioServiceExt", "NumberFormatException", e);
      return i;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$resetConcertHall$1() {
    SoundAliveUtils.resetConcertHall(this.mContext);
  }

  public void resetConcertHall() {
    AudioExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.audio.AudioServiceExt$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            AudioServiceExt.this.lambda$resetConcertHall$1();
          }
        });
  }

  public void updateCallGuardInfo(int i, int i2, boolean z) {
    Log.i("AS.AudioServiceExt", "callguard: mode(" + i + "), pid(" + i2 + "), skipSet(" + z + ")");
    this.mAudioSystem.setParameters(
        new AudioParameter.Builder()
            .setParam("l_guard_call_mode", i)
            .setParam("l_guard_call_mode_calling_pid", i2)
            .setParam("l_guard_call_mode_skip", z ? 1 : 0)
            .build()
            .toString());
  }

  public void addAudioServiceExtDump(PrintWriter printWriter) {
    printWriter.print("  mNbQualityMode=");
    printWriter.println(this.mNbQualityMode);
    if (Rune.SEC_AUDIO_SCREEN_CALL) {
      printWriter.print("  mScreenCall=");
      printWriter.println(this.mScreenCall);
    }
    printWriter.print("  mMasterMono=");
    printWriter.println(this.mMainMono);
    printWriter.print("  mMainBalance=");
    printWriter.println(this.mMainBalance);
    printWriter.print("  mAdaptSoundEnabled=");
    printWriter.println(this.mAdaptSoundEnabled);
    printWriter.print("  mUpscalerEnabled=");
    printWriter.println(this.mUpscalerEnabled);
    printWriter.print("  mIsPttCallVolumeEnabled=");
    printWriter.println(this.mIsPttCallVolumeEnabled);
    printWriter.print("  CPUBoostValueForVoIP=");
    printWriter.println(this.mDvfsHelper.getCPUBoostValueForVoIP());
    printWriter.print("  mAllSoundMute=");
    printWriter.println(this.mAllSoundMute);
    if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
      printWriter.print("  mKaraokeListenbackEnabled=");
      printWriter.println(mKaraokeListenbackEnabled);
    }
    if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
      printWriter.print("  mVolumeMonitorValue=");
      printWriter.println(this.mVolumeMonitorValue);
    }
    printWriter.print("  AudioHqmHelper.ResetCount=");
    printWriter.println(AudioHqmHelper.getAudioServerResetCount());
  }
}
