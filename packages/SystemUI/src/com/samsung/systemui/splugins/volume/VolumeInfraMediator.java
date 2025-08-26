package com.samsung.systemui.splugins.volume;

import com.android.systemui.volume.util.SALoggingWrapper;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes4.dex */
public interface VolumeInfraMediator {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Conditions {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ Conditions[] $VALUES;
        public static final Conditions IS_LCD_OFF = new Conditions("IS_LCD_OFF", 0);
        public static final Conditions IS_VOICE_CAPABLE = new Conditions("IS_VOICE_CAPABLE", 1);
        public static final Conditions IS_MEDIA_DEFAULT = new Conditions("IS_MEDIA_DEFAULT", 2);
        public static final Conditions IS_DUAL_PLAY_MODE = new Conditions("IS_DUAL_PLAY_MODE", 3);
        public static final Conditions IS_AUTO_MUTE = new Conditions("IS_AUTO_MUTE", 4);
        public static final Conditions IS_SAFE_MEDIA_VOLUME_DEVICE_ON = new Conditions("IS_SAFE_MEDIA_VOLUME_DEVICE_ON", 5);
        public static final Conditions IS_SAFE_MEDIA_VOLUME_PIN_DEVICE_ON = new Conditions("IS_SAFE_MEDIA_VOLUME_PIN_DEVICE_ON", 6);
        public static final Conditions HAS_VIBRATOR = new Conditions("HAS_VIBRATOR", 7);
        public static final Conditions IS_KEYGUARD_STATE = new Conditions("IS_KEYGUARD_STATE", 8);
        public static final Conditions IS_SHADE_LOCKED_STATE = new Conditions("IS_SHADE_LOCKED_STATE", 9);
        public static final Conditions IS_ALL_SOUND_OFF = new Conditions("IS_ALL_SOUND_OFF", 10);
        public static final Conditions IS_BLUETOOTH_SCO_ON = new Conditions("IS_BLUETOOTH_SCO_ON", 11);
        public static final Conditions IS_USER_IN_CALL = new Conditions("IS_USER_IN_CALL", 12);
        public static final Conditions IS_KIOSK_MODE_ENABLED = new Conditions("IS_KIOSK_MODE_ENABLED", 13);
        public static final Conditions IS_BIXBY_SERVICE_FOREGROUND = new Conditions("IS_BIXBY_SERVICE_FOREGROUND", 14);
        public static final Conditions IS_SMART_VIEW = new Conditions("IS_SMART_VIEW", 15);
        public static final Conditions IS_STANDALONE = new Conditions("IS_STANDALONE", 16);
        public static final Conditions IS_ZEN_MODE_ENABLED = new Conditions("IS_ZEN_MODE_ENABLED", 17);
        public static final Conditions IS_ZEN_MODE_PRIORITY_ONLY = new Conditions("IS_ZEN_MODE_PRIORITY_ONLY", 18);
        public static final Conditions IS_ZEN_MODE_NONE = new Conditions("IS_ZEN_MODE_NONE", 19);
        public static final Conditions IS_ORIENTATION_CHANGED = new Conditions("IS_ORIENTATION_CHANGED", 20);
        public static final Conditions IS_DENSITY_OR_FONT_CHANGED = new Conditions("IS_DENSITY_OR_FONT_CHANGED", 21);
        public static final Conditions IS_MULTI_SOUND_ON = new Conditions("IS_MULTI_SOUND_ON", 22);
        public static final Conditions IS_MEDIA_DEFAULT_OPTION_HIDE = new Conditions("IS_MEDIA_DEFAULT_OPTION_HIDE", 23);
        public static final Conditions IS_CAPTION_ENABLED = new Conditions("IS_CAPTION_ENABLED", 24);
        public static final Conditions IS_DEX_MODE = new Conditions("IS_DEX_MODE", 25);
        public static final Conditions IS_DISPLAY_TYPE_CHANGED = new Conditions("IS_DISPLAY_TYPE_CHANGED", 26);
        public static final Conditions IS_FROM_KEY = new Conditions("IS_FROM_KEY", 27);
        public static final Conditions IS_BUDS_TOGETHER_ENABLED = new Conditions("IS_BUDS_TOGETHER_ENABLED", 28);
        public static final Conditions IS_MUSIC_SHARE_ENABLED = new Conditions("IS_MUSIC_SHARE_ENABLED", 29);
        public static final Conditions IS_SETUP_WIZARD_COMPLETE = new Conditions("IS_SETUP_WIZARD_COMPLETE", 30);
        public static final Conditions VOLUME_SMART_VIEW_STREAM = new Conditions("VOLUME_SMART_VIEW_STREAM", 31);
        public static final Conditions VOLUME_WARNING_POPUP_WALLET_MINI = new Conditions("VOLUME_WARNING_POPUP_WALLET_MINI", 32);
        public static final Conditions VOLUME_WARNING_POPUP_SIDE_VIEW = new Conditions("VOLUME_WARNING_POPUP_SIDE_VIEW", 33);
        public static final Conditions VOLUME_BUDS_TOGETHER = new Conditions("VOLUME_BUDS_TOGETHER", 34);
        public static final Conditions VOLUME_DUAL_AUDIO = new Conditions("VOLUME_DUAL_AUDIO", 35);
        public static final Conditions IS_AOD_VOLUME_PANEL = new Conditions("IS_AOD_VOLUME_PANEL", 36);
        public static final Conditions IS_BLE_CALL_DEVICE_ON = new Conditions("IS_BLE_CALL_DEVICE_ON", 37);

        private static final /* synthetic */ Conditions[] $values() {
            return new Conditions[]{IS_LCD_OFF, IS_VOICE_CAPABLE, IS_MEDIA_DEFAULT, IS_DUAL_PLAY_MODE, IS_AUTO_MUTE, IS_SAFE_MEDIA_VOLUME_DEVICE_ON, IS_SAFE_MEDIA_VOLUME_PIN_DEVICE_ON, HAS_VIBRATOR, IS_KEYGUARD_STATE, IS_SHADE_LOCKED_STATE, IS_ALL_SOUND_OFF, IS_BLUETOOTH_SCO_ON, IS_USER_IN_CALL, IS_KIOSK_MODE_ENABLED, IS_BIXBY_SERVICE_FOREGROUND, IS_SMART_VIEW, IS_STANDALONE, IS_ZEN_MODE_ENABLED, IS_ZEN_MODE_PRIORITY_ONLY, IS_ZEN_MODE_NONE, IS_ORIENTATION_CHANGED, IS_DENSITY_OR_FONT_CHANGED, IS_MULTI_SOUND_ON, IS_MEDIA_DEFAULT_OPTION_HIDE, IS_CAPTION_ENABLED, IS_DEX_MODE, IS_DISPLAY_TYPE_CHANGED, IS_FROM_KEY, IS_BUDS_TOGETHER_ENABLED, IS_MUSIC_SHARE_ENABLED, IS_SETUP_WIZARD_COMPLETE, VOLUME_SMART_VIEW_STREAM, VOLUME_WARNING_POPUP_WALLET_MINI, VOLUME_WARNING_POPUP_SIDE_VIEW, VOLUME_BUDS_TOGETHER, VOLUME_DUAL_AUDIO, IS_AOD_VOLUME_PANEL, IS_BLE_CALL_DEVICE_ON};
        }

        static {
            Conditions[] conditionsArr$values = $values();
            $VALUES = conditionsArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(conditionsArr$values);
        }

        private Conditions(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static Conditions valueOf(String str) {
            return (Conditions) Enum.valueOf(Conditions.class, str);
        }

        public static Conditions[] values() {
            return (Conditions[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Values {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ Values[] $VALUES;
        public static final Values BT_CALL_DEVICE_NAME = new Values("BT_CALL_DEVICE_NAME", 0);
        public static final Values SMART_VIEW_DEVICE_NAME = new Values("SMART_VIEW_DEVICE_NAME", 1);
        public static final Values ACTIVE_BT_DEVICE_NAME = new Values("ACTIVE_BT_DEVICE_NAME", 2);
        public static final Values PIN_DEVICE = new Values("PIN_DEVICE", 3);
        public static final Values EAR_PROTECT_LIMIT = new Values("EAR_PROTECT_LIMIT", 4);
        public static final Values SYSTEM_TIME = new Values("SYSTEM_TIME", 5);
        public static final Values TIMEOUT_CONTROLS = new Values("TIMEOUT_CONTROLS", 6);
        public static final Values TIMEOUT_CONTROLS_TEXT = new Values("TIMEOUT_CONTROLS_TEXT", 7);
        public static final Values PIN_APP_NAME = new Values("PIN_APP_NAME", 8);
        public static final Values PIN_DEVICE_NAME = new Values("PIN_DEVICE_NAME", 9);
        public static final Values DEVICES_FOR_STREAM_MUSIC = new Values("DEVICES_FOR_STREAM_MUSIC", 10);
        public static final Values MUSIC_FINE_VOLUME = new Values("MUSIC_FINE_VOLUME", 11);
        public static final Values CUTOUT_HEIGHT = new Values("CUTOUT_HEIGHT", 12);
        public static final Values FRONT_SUB_DISPLAY = new Values("FRONT_SUB_DISPLAY", 13);
        public static final Values MULTI_SOUND_DEVICE = new Values("MULTI_SOUND_DEVICE", 14);
        public static final Values AUDIO_CAST_DEVICE_NAME = new Values("AUDIO_CAST_DEVICE_NAME", 15);

        private static final /* synthetic */ Values[] $values() {
            return new Values[]{BT_CALL_DEVICE_NAME, SMART_VIEW_DEVICE_NAME, ACTIVE_BT_DEVICE_NAME, PIN_DEVICE, EAR_PROTECT_LIMIT, SYSTEM_TIME, TIMEOUT_CONTROLS, TIMEOUT_CONTROLS_TEXT, PIN_APP_NAME, PIN_DEVICE_NAME, DEVICES_FOR_STREAM_MUSIC, MUSIC_FINE_VOLUME, CUTOUT_HEIGHT, FRONT_SUB_DISPLAY, MULTI_SOUND_DEVICE, AUDIO_CAST_DEVICE_NAME};
        }

        static {
            Values[] valuesArr$values = $values();
            $VALUES = valuesArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(valuesArr$values);
        }

        private Values(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static Values valueOf(String str) {
            return (Values) Enum.valueOf(Values.class, str);
        }

        public static Values[] values() {
            return (Values[]) $VALUES.clone();
        }
    }

    void disableSafeMediaVolume();

    Object get(Values values, Object... objArr);

    String getActiveBtDeviceName();

    String getAudioCastDeviceName();

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

    void startDoNotDisturbActivity();

    void startHearingEnhancementsActivity();

    void startLeBroadcastActivity();

    void startSettingsActivity();

    void startVolumeSettingsActivity();

    void toggleWifiDisplayMute();

    void userActivity();
}
