package com.samsung.android.audio;

import android.os.Build;
import android.os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

public class Rune {
    public static final boolean SEC_AUDIO_ABS_VOLUME_CONTROL_BT = true;
    public static final boolean SEC_AUDIO_ACPARAM_CMD = true;
    public static final boolean SEC_AUDIO_ACTIVE_STREAM_TYPE = true;
    public static final boolean SEC_AUDIO_ALL_SOUND_MUTE = true;
    public static final boolean SEC_AUDIO_AMP_BOOST = true;
    public static final boolean SEC_AUDIO_AOSP_BUG_FIX = true;
    public static final boolean SEC_AUDIO_APP_MODE = true;
    public static final boolean SEC_AUDIO_ATT_SOFTPHONE;
    public static final boolean SEC_AUDIO_BATTERY_HOTSWAP = true;
    public static final boolean SEC_AUDIO_BIKE_MODE;
    public static final boolean SEC_AUDIO_BLE_DUAL_MODE = true;
    public static final boolean SEC_AUDIO_BLE_DUAL_MODE_VOLUME_SYNC = true;
    public static final boolean SEC_AUDIO_BLUETOOTH_CAST = true;
    public static final String SEC_AUDIO_BRAND_SOUND_VERSION = "3.1.1";
    public static final boolean SEC_AUDIO_BROADCAST_NOISY_INTENT = true;
    public static final boolean SEC_AUDIO_BT_MULTIPLE_PROFILE = true;
    public static final boolean SEC_AUDIO_BT_OFFLOAD = true;
    public static final boolean SEC_AUDIO_BT_VOLUME_MONITOR;
    public static final boolean SEC_AUDIO_CALL_GUARD = true;
    public static final boolean SEC_AUDIO_CALL_MONSTER_SOUND;
    public static final boolean SEC_AUDIO_CALL_RECOVERY = true;
    public static final boolean SEC_AUDIO_CALL_TRANSLATION_MODE = true;
    public static final boolean SEC_AUDIO_CARLIFE;
    public static final boolean SEC_AUDIO_CHECK_USING_AUDIO = true;
    public static final boolean SEC_AUDIO_CONCURRENT_CAPTURE = true;
    public static final boolean SEC_AUDIO_CONFIG_CALL_VOLUME_STEPS = true;
    public static final boolean SEC_AUDIO_CONTEXT_AWARE = true;
    public static final String SEC_AUDIO_COREFX_VERSION = "6.5";
    public static final boolean SEC_AUDIO_CORE_FX_FRAMEWORK = true;
    public static final boolean SEC_AUDIO_COVER = true;
    public static final boolean SEC_AUDIO_CPU_STATE_LOCK;
    public static final boolean SEC_AUDIO_CSC_RINGTONE = true;
    public static final boolean SEC_AUDIO_CUSTOMIZATION = true;
    public static final boolean SEC_AUDIO_CUSTOM_LOG = true;
    public static final boolean SEC_AUDIO_CUSTOM_SHELL_COMMAND;
    public static boolean SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION = false;
    public static final boolean SEC_AUDIO_DELAY_LOSS_AUDIO_FOCUS = true;
    public static final boolean SEC_AUDIO_DEVICE_ALIAS = true;
    public static final boolean SEC_AUDIO_DEX = true;
    public static final boolean SEC_AUDIO_DIRECT_POWER = false;
    public static final boolean SEC_AUDIO_DISABLE_UNSUPPORTED_MIME_TYPE = true;
    public static final boolean SEC_AUDIO_DISPLAY_VOLUME_CONTROL = true;
    public static final boolean SEC_AUDIO_DOLBY_ENABLED;
    public static final boolean SEC_AUDIO_DOLBY_GAME_FX;
    public static final boolean SEC_AUDIO_DUAL_A2DP_VOLUME = true;
    public static final boolean SEC_AUDIO_DUAL_RINGTONE = true;
    public static final boolean SEC_AUDIO_DVFS_BOOSTER = true;
    public static final boolean SEC_AUDIO_EAR_SHOCK_CONDITION = true;
    public static final boolean SEC_AUDIO_EFFECT_NATIVE_ERROR_LISTENER = true;
    public static final boolean SEC_AUDIO_EXTENSION_SITUATION_VOLUME;
    public static final boolean SEC_AUDIO_EXTERNAL_STORAGE_RINGTONE = true;
    public static final boolean SEC_AUDIO_EXTRA_VOLUME = true;
    public static final boolean SEC_AUDIO_FACTORY_TEST_MODE = true;
    public static final boolean SEC_AUDIO_FINE_MEDIA_VOLUME = true;
    public static final boolean SEC_AUDIO_FIXED_SCO_VOLUME;
    public static final boolean SEC_AUDIO_FLEX_MODE_VIDEOCALL = false;
    public static final boolean SEC_AUDIO_FM_RADIO;
    public static final boolean SEC_AUDIO_FM_RADIO_RECORDING;
    public static final boolean SEC_AUDIO_GAMECHAT_SPEAKER_AEC;
    public static final boolean SEC_AUDIO_GAME_LIST = true;
    public static final boolean SEC_AUDIO_GOOD_CATCH = true;
    public static final boolean SEC_AUDIO_HIGHLIGHT_RINGTONE = true;
    public static final boolean SEC_AUDIO_HQM_BIG_DATA = true;
    public static final boolean SEC_AUDIO_IGNORE_DUCKING = true;
    public static final boolean SEC_AUDIO_IGNORE_VOICE_KEY = true;
    public static final boolean SEC_AUDIO_INPUT_SOURCE = true;
    public static final boolean SEC_AUDIO_KARAOKE;
    public static final boolean SEC_AUDIO_KARAOKE_LISTENBACK;
    public static final boolean SEC_AUDIO_KEEP_LEGACY_MEDIA_BUTTON_POLICY = true;
    public static final boolean SEC_AUDIO_KNOX_CUSTOMIZATION_SDK = true;
    public static final boolean SEC_AUDIO_KNOX_MDM = true;
    public static final boolean SEC_AUDIO_LAST_CONNECTED_DEVICE = true;
    public static final boolean SEC_AUDIO_LAUNCH_VOICE_COMMAND = true;
    public static final boolean SEC_AUDIO_MEDIA_OUTPUT_SWITCHER = true;
    public static final boolean SEC_AUDIO_MEDIA_SESSION_AI;
    public static final boolean SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI;
    public static final boolean SEC_AUDIO_MODE_REQUESTER_PACKAGE = true;
    public static final boolean SEC_AUDIO_MULTI_AUDIO_FOCUS = true;
    public static final boolean SEC_AUDIO_MULTI_SOUND = true;
    public static final boolean SEC_AUDIO_MULTI_SOUND_SELECT_MULTIPLE_APPLICATIONS = true;
    public static final boolean SEC_AUDIO_MUSIC_RING = true;
    public static final boolean SEC_AUDIO_MUTE_INTERVAL = true;
    public static final boolean SEC_AUDIO_MYSPACE_EFFECT = true;
    public static final boolean SEC_AUDIO_NB_QUALITY_MODE = true;
    public static final boolean SEC_AUDIO_NO_FADEOUT_FROM_AUDIOFOCUS = true;
    public static final boolean SEC_AUDIO_NO_MUTE_IN_CALL = true;
    public static final int SEC_AUDIO_NUM_OF_SPEAKER = Integer.valueOf("2").intValue();
    public static final boolean SEC_AUDIO_OPEN_THEME = true;
    public static final boolean SEC_AUDIO_PACKAGE_LIST = true;
    public static final boolean SEC_AUDIO_PARAMETERS = true;
    public static final boolean SEC_AUDIO_PHONESTATE_LISTENER = true;
    public static final boolean SEC_AUDIO_PLAYBACK_MONITOR = true;
    public static final boolean SEC_AUDIO_PLAY_SOUND_EFFECT = true;
    public static final boolean SEC_AUDIO_POWER_SOUND = true;
    public static final boolean SEC_AUDIO_PREVENT_OVERHEAT_BY_MAX_VOLUME = true;
    public static final boolean SEC_AUDIO_QUICK_SOUND_PATH = true;
    public static final boolean SEC_AUDIO_RECORDING_POPUP;
    public static final boolean SEC_AUDIO_RECORD_ACTIVE = true;
    public static final boolean SEC_AUDIO_RECOVERY = true;
    public static final boolean SEC_AUDIO_REMOTE_CASTING = true;
    public static final boolean SEC_AUDIO_REMOTE_MIC;
    public static final boolean SEC_AUDIO_RINGER_AFFECTED_STREAMS = true;
    public static final boolean SEC_AUDIO_SAFE_MEDIA_VOLUME;
    public static final boolean SEC_AUDIO_SAFE_VOLUME_COUNTRY;
    public static final boolean SEC_AUDIO_SAMSUNG_BLE_BROADCAST_VOLUME = true;
    public static final boolean SEC_AUDIO_SAMSUNG_BLE_CALL_MODE_VOLUME = true;
    public static final boolean SEC_AUDIO_SAMSUNG_BLE_ROUTE = true;
    public static final boolean SEC_AUDIO_SAMSUNG_BT_A2DP_ROUTE = true;
    public static final boolean SEC_AUDIO_SAMSUNG_BT_SCO_ROUTE = true;
    public static final boolean SEC_AUDIO_SAMSUNG_CALL_ROUTE = true;
    public static final boolean SEC_AUDIO_SAMSUNG_DND = true;
    public static final boolean SEC_AUDIO_SAMSUNG_LE_BROADCAST = true;
    public static final boolean SEC_AUDIO_SAMSUNG_MULTI_CHANNEL_HDMI = true;
    public static final boolean SEC_AUDIO_SAMSUNG_VOLUME = true;
    public static final boolean SEC_AUDIO_SAR_RCV_CONTROL = true;
    public static final boolean SEC_AUDIO_SCREEN_CALL;
    public static final boolean SEC_AUDIO_SCREEN_MIRRORING = true;
    public static final boolean SEC_AUDIO_SCREEN_OFF_MUSIC;
    public static final boolean SEC_AUDIO_SETTING_SOFT_RESET = true;
    public static final boolean SEC_AUDIO_SET_FORCE_USE_MEDIA = true;
    public static final boolean SEC_AUDIO_SITUATION_VOLUME = true;
    public static final boolean SEC_AUDIO_SOUND_APP_POLICY = true;
    public static final boolean SEC_AUDIO_SOUND_ASSISTANT = true;
    public static final boolean SEC_AUDIO_SOUND_BALANCE = true;
    public static final boolean SEC_AUDIO_SPATIAL_AUDIO = true;
    public static final boolean SEC_AUDIO_SPLIT_SOUND = true;
    public static final boolean SEC_AUDIO_STEREO_TO_MONO = true;
    public static final boolean SEC_AUDIO_SUBDIVISION_NOTIFICATION_VOLUME = true;
    public static final boolean SEC_AUDIO_SUPPORT_A2DP_AV_SYNC = true;
    public static final boolean SEC_AUDIO_SUPPORT_ACH_RINGTONE;
    public static final boolean SEC_AUDIO_SUPPORT_DUAL_SPEAKER = true;
    public static final boolean SEC_AUDIO_SUPPORT_FACTORY_INTERPOSER = false;
    public static final boolean SEC_AUDIO_SUPPORT_FOLD_STATE = false;
    public static final boolean SEC_AUDIO_SUPPORT_MODE_CHANGE_INTENT = true;
    public static final boolean SEC_AUDIO_SUPPORT_PTT = true;
    public static final boolean SEC_AUDIO_SUPPORT_SEC_MHL = true;
    public static final boolean SEC_AUDIO_SUPPORT_SOUND_THEME;
    public static final boolean SEC_AUDIO_SUPPORT_VIRTUAL_VIBRATION_SOUND = true;
    public static final boolean SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING;
    public static final boolean SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER;
    public static final boolean SEC_AUDIO_TRANSITION_EFFECT = true;
    public static final boolean SEC_AUDIO_UHQ;
    public static final boolean SEC_AUDIO_UNIFIED_RINGTONE_POOL = true;
    public static final boolean SEC_AUDIO_UPDATE_AUDIO_MODE = true;
    public static final boolean SEC_AUDIO_USB_HEADSET_CALL_SUPPORT;
    public static final boolean SEC_AUDIO_USB_HEADSET_FOR_CAMCORDER;
    public static final boolean SEC_AUDIO_VIBRATOR = true;
    public static final boolean SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT;
    public static final boolean SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT;
    public static final boolean SEC_AUDIO_VOIP_LIVE_TRANSLATE;
    public static final boolean SEC_AUDIO_VOIP_VIA_SMART_VIEW;
    public static final boolean SEC_AUDIO_VOLUME_KEY_SUPPORT_SILENT;
    public static final boolean SEC_AUDIO_VOLUME_LIMIT = true;
    public static final boolean SEC_AUDIO_VOLUME_LONG_PRESS = true;
    public static final boolean SEC_AUDIO_VOLUME_MONITOR;
    public static final boolean SEC_AUDIO_VOLUME_MONITOR_PHASE_3;
    public static final boolean SEC_AUDIO_VOLUME_PANEL_HW_KEY_VI = true;
    public static final boolean SEC_AUDIO_VOLUME_TRACKING = true;
    public static final boolean SEC_AUDIO_WAKEUP_BY_WIRED_HEADSET = true;
    public static final boolean SEC_AUDIO_WB_AMR = true;
    public static final boolean SEC_MUTE_MEDIA_BY_VIBRATE_OR_SILENT_MODE = true;
    public static final boolean TO_DO = false;

    static {
        SEC_AUDIO_UHQ = Integer.parseInt("300") > 0;
        SEC_AUDIO_USB_HEADSET_FOR_CAMCORDER = "OFFLOAD;CALL".contains("OFFLOAD");
        SEC_AUDIO_USB_HEADSET_CALL_SUPPORT = "OFFLOAD;CALL".contains("CALL");
        SEC_AUDIO_GAMECHAT_SPEAKER_AEC = TextUtils.equals("SPK_AEC", "SPK_AEC");
        SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION =
                "wifi-only".equalsIgnoreCase(SystemProperties.get("ro.carrier", "Unknown").trim())
                        || "yes"
                                .equalsIgnoreCase(
                                        SystemProperties.get("ro.radio.noril", "no").trim());
        TextUtils.equals("sep_basic", "sep_basic");
        SEC_AUDIO_SUPPORT_SOUND_THEME = true;
        SEC_AUDIO_SUPPORT_ACH_RINGTONE = "ACH".contains("ACH");
        SEC_AUDIO_DOLBY_ENABLED =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_DOLBY_AUDIO");
        SEC_AUDIO_DOLBY_GAME_FX =
                SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DOLBY_GAME_PROFILE")
                        && SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DEFAULT_ON_DOLBY_IN_GAME");
        SEC_AUDIO_EXTENSION_SITUATION_VOLUME =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_SITUATION_EXTENSION");
        SEC_AUDIO_VOLUME_MONITOR =
                SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOLUME_MONITOR")
                                .contains("FW")
                        || SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOLUME_MONITOR")
                                .contains("DSP");
        SEC_AUDIO_SCREEN_OFF_MUSIC =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_MUSICPLAYER_SUPPORT_SCREEN_OFF_MUSIC");
        SEC_AUDIO_RECORDING_POPUP =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_RECORDING_POPUP");
        SEC_AUDIO_CPU_STATE_LOCK =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_CPU_LOCK_FOR_VOIP");
        SEC_AUDIO_KARAOKE_LISTENBACK = "DSP;AFE".contains("DSP");
        SEC_AUDIO_REMOTE_MIC =
                !SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_REMOTE_MIC")
                        .contains(KeyProperties.DIGEST_NONE);
        SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL")
                        .contains("3MIC");
        SEC_AUDIO_VOLUME_MONITOR_PHASE_3 =
                SemFloatingFeature.getInstance()
                                .getInteger("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE")
                        >= 3;
        SEC_AUDIO_CALL_MONSTER_SOUND =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL")
                        .contains("2MIC");
        SEC_AUDIO_BT_VOLUME_MONITOR =
                SemFloatingFeature.getInstance()
                                .getInteger("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE")
                        >= 2;
        SEC_AUDIO_SCREEN_CALL =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOICE_TX_FOR_INCALL_MUSIC");
        SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOIP_SOUND_LOUDER");
        SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_ANTI_HOWLING");
        SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL")
                        .contains("DEFAULT");
        SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI =
                (SemFloatingFeature.getInstance()
                                        .getString(
                                                "SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL")
                                        .contains("None")
                                && !SemFloatingFeature.getInstance()
                                        .getBoolean(
                                                "SEC_FLOATING_FEATURE_AUDIO_SUPPORT_MICMODE_QUICK_PANEL")
                                && SemCscFeature.getInstance()
                                        .getString(
                                                "SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL")
                                        .contains("None"))
                        ? false
                        : true;
        SEC_AUDIO_VOIP_LIVE_TRANSLATE =
                !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI");
        SEC_AUDIO_MEDIA_SESSION_AI =
                SemFloatingFeature.getInstance()
                        .getBoolean(
                                "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_PERSONALIZED_DATA_CORE");
        SEC_AUDIO_KARAOKE =
                SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_SPEAKER_AEC")
                        || "DSP;AFE".contains("DSP");
        SEC_AUDIO_VOIP_VIA_SMART_VIEW =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOIP_VIA_SMART_VIEW");
        SEC_AUDIO_FM_RADIO =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR")
                        != 0;
        SEC_AUDIO_CARLIFE =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_CARLIFE");
        SEC_AUDIO_BIKE_MODE =
                SemCscFeature.getInstance()
                        .getString("CscFeature_Common_ConfigBikeMode")
                        .contains("bikemode");
        SEC_AUDIO_SAFE_VOLUME_COUNTRY =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Audio_SupportSafeMediaVolumeDialog", false);
        SEC_AUDIO_VOLUME_KEY_SUPPORT_SILENT =
                TextUtils.equals(
                        SemCscFeature.getInstance()
                                .getString("CscFeature_Audio_ConfigActionVolumeKey"),
                        "Silent");
        SEC_AUDIO_FIXED_SCO_VOLUME = Build.VERSION.DEVICE_INITIAL_SDK_INT <= 30;
        SEC_AUDIO_ATT_SOFTPHONE = AudioManagerHelper.isSupportSoftPhone();
        SEC_AUDIO_CUSTOM_SHELL_COMMAND =
                "false".equals(SystemProperties.get("ro.product_ship", "false"));
        SEC_AUDIO_FM_RADIO_RECORDING = SEC_AUDIO_FM_RADIO;
        SEC_AUDIO_SAFE_MEDIA_VOLUME =
                SEC_AUDIO_SAFE_VOLUME_COUNTRY || SEC_AUDIO_VOLUME_MONITOR_PHASE_3;
    }
}
