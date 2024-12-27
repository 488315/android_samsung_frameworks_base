package com.samsung.android.audio;

public final class AudioIntent {
    public static final String ACTION_AUDIOCORE_BIGDATA_APP =
            "com.sec.android.intent.action.DHR_HQM_REFRESH_REQ";
    public static final String ACTION_AUDIOCORE_LOGGING = "com.sec.media.action.AUDIOCORE_LOGGING";
    public static final String ACTION_AUDIO_MODE_CHANGE = "android.samsung.media.action.AUDIO_MODE";
    public static final String ACTION_AUDIO_RECEIVER_SAR =
            "android.samsung.media.action.receiver_sar";
    public static final String ACTION_AUDIO_REMOTEMIC_SCO_RESUME =
            "android.samsung.media.action.ACTION_AUDIO_REMOTEMIC_SCO_RESUME";
    public static final String ACTION_AUDIO_SOUND_MUTE = "android.settings.ALL_SOUND_MUTE";
    public static final String ACTION_BATTERY_CONNECTION_STATE_CHANGED =
            "com.samsung.server.BatteryService.action.BATTERY_CONNECTION_STATE_CHANGED";
    public static final String ACTION_BT_CAST_CONNECTION_STATE_CHANGED =
            "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED";
    public static final String ACTION_BT_DEVICE_TYPE_CHANGED =
            "com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED";
    public static final String ACTION_CAR_CONNECTION_UPDATED =
            "androidx.car.app.connection.action.CAR_CONNECTION_UPDATED";
    public static final String ACTION_CHECK_MUTE_INTERVAL = "com.sec.media.action.mute_interval";
    public static final String ACTION_CLOSE_ALL = "com.android.launcher3.quickstep.closeall";
    public static final String ACTION_CONNECTION_STATE_CHANGED =
            "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_CONTEXT_AWARE_HEADSET =
            "com.sec.android.contextaware.HEADSET_PLUG";
    public static final String ACTION_DLNA_STATUS_CHANGED =
            "com.samsung.intent.action.DLNA_STATUS_CHANGED";
    public static final String ACTION_DUAL_PLAY_MODE_ENABLED =
            "com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED";
    public static final String ACTION_GOOD_CATCH_STATE_CHANGED =
            "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED";
    public static final String ACTION_HEADUP_NOTIFICATION_CHANGE_DEVICE =
            "com.samsung.android.audio.headup.changedevice";
    public static final String ACTION_ISSUE_TRACKER = "com.sec.android.ISSUE_TRACKER_ACTION";
    public static final String ACTION_KARAOKE_INSTALLED = "com.samsung.android.intent.karaoke";
    public static final String ACTION_LAUNCH_SETTING = "android.intent.action.Launch_Setting";
    public static final String ACTION_LOCALE_CHANGED = "android.intent.action.LOCALE_CHANGED";
    public static final String ACTION_MULTISOUND_CHANGED =
            "com.samsung.intent.action.MULTISOUND_STATE_CHANGED";
    public static final String ACTION_MULTISOUND_STATE_CHANGE =
            "android.intent.action.MULTISOUND_STATE_CHANGE";
    public static final String ACTION_NB_QUALITY_MODE_CHANGE =
            "com.android.phone.action.PERSONALISE_CALL_SOUND_CHANGED";
    public static final String ACTION_NOTIFICATION_CLOSE =
            "android.intent.action.SAS_NOTIFICATION_CLEAR";
    public static final String ACTION_NOTIFY_MEDIA_SERVER_RESTART =
            "com.samsung.intent.action.MEDIA_SERVER_REBOOTED";
    public static final String ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    public static final String ACTION_PACKAGE_DATA_CLEARED =
            "android.intent.action.PACKAGE_DATA_CLEARED";
    public static final String ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
    public static final String ACTION_SETTING_SOFT_RESET =
            "com.samsung.intent.action.SETTINGS_SOFT_RESET";
    public static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";
    public static final String ACTION_SOUND_APP_POLICY =
            "com.samsung.android.scpm.policy.UPDATE.Audio";
    public static final String ACTION_SOUND_APP_POLICY_CLEAR =
            "com.samsung.android.scpm.policy.CLEAR_DATA";
    public static final String ACTION_SOUND_ASSISTANT_INTENT_SERVICE =
            "com.sec.android.soundassistant.SOUNDASSIST_INTENT_SERVICE";
    public static final String ACTION_SOUND_EVENT_CHANGED =
            "com.samsung.android.intent.action.SOUND_EVENT";
    public static final String ACTION_SPLIT_SOUND = "com.sec.android.intent.action.SPLIT_SOUND";
    public static final String ACTION_SUBINFO_RECORD_UPDATED =
            "android.intent.action.ACTION_SUBINFO_RECORD_UPDATED";
    public static final String ACTION_TURN_OFF_MULTISOUND =
            "android.intent.action.TurnOff_MultiSound";
    public static final String ACTION_UPDATE_WB_AMR = "com.samsung.intent.action.WB_AMR";
    public static final String ACTION_USER_STARTED = "android.intent.action.USER_STARTED";
    public static final String ACTION_VOIP_LIVE_TRANSLATE_ALLOW =
            "com.samsung.android.scpm.policy.UPDATE.voip-live-translate-allow-list-a7f6";
    public static final String ACTION_VOLUME_STAR_SETTING_CHANGED =
            "android.intent.action.VOLUMESTAR_SETTING_CHANGED";
    public static final String EXTRA_ALL_BATTERY_CONNECTED = "all_battery_connected";
    public static final String EXTRA_CAR_CONNECTION_STATE = "CarConnectionState";
    public static final String EXTRA_VALUE_AUDIO_MODE_CHANGE =
            "android.samsung.media.extra.AUDIO_MODE";
    public static final String EXTRA_VALUE_AUDIO_MODE_OWNER = "modeOwner";
    public static final String EXTRA_VALUE_CHANGED_SETTING = "changed_setting";
    public static final String EXTRA_VALUE_DUAL_MODE_ENABLED = "enable";
    public static final String EXTRA_VALUE_RECEIVER_SAR = "android.samsung.media.extra.receiver";
    public static final String PERMISSION_SPLUGIN = "com.samsung.systemui.permission.SPLUGIN";
    public static final String SEM_WIFIDISPLAY_NOTI_CONNECTION_MODE =
            "com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE";
    public static final String SEM_WIFI_DISPLAY_SOURCE_STATE =
            "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE";
    public static final String SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED =
            "com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED";
    public static final int VALUE_CONNECTION_TYPE_NATIVE = 1;
    public static final int VALUE_CONNECTION_TYPE_NOT_CONNECTED = 0;
    public static final int VALUE_CONNECTION_TYPE_PROJECTION = 2;
}
