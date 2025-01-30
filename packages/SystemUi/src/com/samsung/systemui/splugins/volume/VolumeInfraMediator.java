package com.samsung.systemui.splugins.volume;

import com.android.systemui.volume.util.SALoggingWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface VolumeInfraMediator {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Conditions {
        IS_LCD_OFF,
        IS_VOICE_CAPABLE,
        IS_MEDIA_DEFAULT,
        IS_DUAL_PLAY_MODE,
        IS_AUTO_MUTE,
        IS_SAFE_MEDIA_VOLUME_DEVICE_ON,
        IS_SAFE_MEDIA_VOLUME_PIN_DEVICE_ON,
        HAS_VIBRATOR,
        IS_KEYGUARD_STATE,
        IS_SHADE_LOCKED_STATE,
        IS_ALL_SOUND_OFF,
        IS_BLUETOOTH_SCO_ON,
        IS_USER_IN_CALL,
        IS_KIOSK_MODE_ENABLED,
        IS_BIXBY_SERVICE_ON,
        IS_BIXBY_SERVICE_FOREGROUND,
        IS_SMART_VIEW,
        IS_STANDALONE,
        IS_ZEN_MODE_ENABLED,
        IS_ZEN_MODE_PRIORITY_ONLY,
        IS_ZEN_MODE_NONE,
        IS_ORIENTATION_CHANGED,
        IS_DENSITY_OR_FONT_CHANGED,
        IS_MULTI_SOUND_ON,
        IS_MEDIA_DEFAULT_OPTION_HIDE,
        IS_CAPTION_ENABLED,
        IS_DEX_MODE,
        IS_DISPLAY_TYPE_CHANGED,
        IS_FROM_KEY,
        IS_BUDS_TOGETHER_ENABLED,
        IS_MUSIC_SHARE_ENABLED,
        IS_SETUP_WIZARD_COMPLETE,
        VOLUME_SMART_VIEW_STREAM,
        VOLUME_WARNING_POPUP_WALLET_MINI,
        VOLUME_WARNING_POPUP_SIDE_VIEW,
        VOLUME_BUDS_TOGETHER,
        VOLUME_DUAL_AUDIO,
        IS_AOD_VOLUME_PANEL,
        IS_BLE_CALL_DEVICE_ON
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Values {
        BT_CALL_DEVICE_NAME,
        SMART_VIEW_DEVICE_NAME,
        ACTIVE_BT_DEVICE_NAME,
        PIN_DEVICE,
        EAR_PROTECT_LIMIT,
        SYSTEM_TIME,
        TIMEOUT_CONTROLS,
        TIMEOUT_CONTROLS_TEXT,
        PIN_APP_NAME,
        PIN_DEVICE_NAME,
        DEVICES_FOR_STREAM_MUSIC,
        MUSIC_FINE_VOLUME,
        CUTOUT_HEIGHT,
        FRONT_SUB_DISPLAY,
        MULTI_SOUND_DEVICE,
        AUDIO_CAST_DEVICE_NAME
    }

    void disableSafeMediaVolume();

    Object get(Values values, Object... objArr);

    String getActiveBtDeviceName();

    String getAudioCastDeviceName();

    void getBixbyServiceState();

    String getBtCallDeviceName();

    void getCaptionsComponentState(boolean z);

    int getCutoutHeight();

    int getDevicesForStreamMusic();

    int getEarProtectLimit();

    int getMultiSoundDevice();

    int getMusicFineVolume();

    String getPinAppName(int i);

    int getPinDevice();

    String getPinDeviceName(int i);

    String getSmartViewDeviceName();

    long getSystemTime();

    int getTimeoutControls();

    int getTimeoutControlsText();

    void initSound(int i);

    boolean isAllSoundOff();

    boolean isAodVolumePanel();

    boolean isAudioMirroring();

    boolean isBixbyServiceForeground();

    boolean isBixbyServiceOn();

    boolean isBleCallDeviceOn();

    boolean isBluetoothScoOn();

    boolean isBudsTogetherEnabled();

    boolean isCaptionEnabled();

    boolean isDensityOrFontChanged();

    boolean isDexMode();

    boolean isDisplayTypeChanged();

    boolean isEnabled(Conditions conditions, Object... objArr);

    boolean isHasVibrator();

    boolean isKeyguardState();

    boolean isKioskModeEnabled();

    boolean isLcdOff();

    boolean isLeBroadcasting();

    boolean isMediaDefault();

    boolean isMultiSoundOn();

    boolean isOrientationChanged();

    boolean isSafeMediaVolumeDeviceOn();

    boolean isSafeMediaVolumePinDeviceOn();

    boolean isSetupWizardComplete();

    boolean isShadeLockedState();

    boolean isSmartView();

    boolean isStandalone();

    boolean isSupportTvVolumeSync();

    boolean isUserInCall();

    boolean isVoiceCapable();

    boolean isVolumeStarEnabled();

    boolean isZenModeEnabled(int i);

    boolean isZenModeNone(int i);

    boolean isZenModePriorityOnly(int i);

    void notifyVisible(boolean z);

    void playSound();

    void playSound(int i);

    void sendEventLog(SALoggingWrapper.Event event);

    void sendSystemDialogsCloseAction();

    void setActiveStream(int i);

    void setCaptionEnabled(boolean z);

    void setRingerMode(int i, boolean z);

    void setSafeMediaVolume();

    void setSafeVolumeDialogShowing(boolean z);

    void setStreamVolume(int i, int i2);

    void setStreamVolumeDualAudio(int i, int i2, String str);

    void startHearingEnhancementsActivity();

    void startLeBroadcastActivity();

    void startSettingsActivity();

    void startVolumeSettingsActivity();

    void toggleWifiDisplayMute();

    void userActivity();
}
