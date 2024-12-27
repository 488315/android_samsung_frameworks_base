package android.hardware.display;

import android.os.Build;

public final class SemScreenSharingConstants {
    public static final String CONNECTION_TYPE_CHROMECAST = "gcast";
    public static final String CONNECTION_TYPE_DLNA = "dlna";
    public static final String CONNECTION_TYPE_GOOGLE_CAST = "gcast";
    public static final String CONNECTION_TYPE_MIRRORING = "mirroring";
    public static final int FEATURE_ALL = 0;
    public static final int FEATURE_DLNA = 2;
    public static final int FEATURE_GOOGLE_CAST = 3;
    public static final int FEATURE_MIRRORING = 1;
    public static final int FEATURE_NOT_SUPPORT = -1;
    public static final String KEY_CONFIGURATION_CURRENT_VOLUME_LEVEL = "cuvo";
    public static final String KEY_CONFIGURATION_DISPLAY_ID = "dpid";
    public static final String KEY_CONFIGURATION_FORCE_DISCONNECT = "fcdc";
    public static final String KEY_CONFIGURATION_IS_VOLUME_MUTE = "muvo";
    public static final String KEY_CONFIGURATION_MAX_VOLUME_LEVEL = "mivo";
    public static final String KEY_CONFIGURATION_MIN_VOLUME_LEVEL = "mavo";
    public static final String KEY_CONFIGURATION_RESUME_VIDEO_ONLY = "rvon";
    public static final String KEY_CONFIGURATION_SEND_SET_PARAM_MESSAGE = "setp";
    public static final String KEY_CONFIGURATION_SEND_VOLUME_KEY_EVENT = "vkev";
    public static final String KEY_CONFIGURATION_SEND_VOLUME_MUTE_KEY_EVENT = "mkev";
    public static final String KEY_CONFIGURATION_SET_DISPLAY_VOLUME = "svol";
    public static final String KEY_CONFIGURATION_SET_DONGLE_NAME = "res";
    public static final String KEY_CONFIGURATION_SET_LOW_LATENCY_MODE = "lowl";
    public static final String KEY_CONFIGURATION_SET_MAX_BITRATE = "smb";
    public static final String KEY_CONFIGURATION_SET_MUTE = "mute";
    public static final String KEY_CONFIGURATION_SET_VOLUME_MUTE = "smut";
    public static final String KEY_CONFIGURATION_SET_WFD_ENGINE_CONFIGURATION = "setc";
    public static final String KEY_CONFIGURATION_SUPPORT_VOLUME_CONTROL = "suvo";
    public static final String KEY_CONFIGURATION_UPGRADE = "upgd";
    public static final String LOGGING_APP_ID = "com.samsung.android.screenmirroring";
    public static final String LOGGING_DEVICE_TYPE_DLNA = "DLNA";
    public static final String LOGGING_DEVICE_TYPE_GOOGLE_CAST = "GCAST";
    public static final String LOGGING_DEVICE_TYPE_SCREEN_MIRRORING = "SCR_MIR";
    public static final String LOGGING_DEVICE_TYPE_SCREEN_SHARING = "SCR_SHA";
    public static final String LOGGING_FEATURE_CONNECT = "CONN";
    public static final String LOGGING_FEATURE_CONNECT_TYPE = "CNTP";
    public static final String LOGGING_FEATURE_DISCONNET = "DCON";
    public static final String LOGGING_FEATURE_FLOATING_ICON = "FCON";
    public static final String LOGGING_FEATURE_FLOATING_ICON_COUNT = "FCNT";
    public static final String LOGGING_FEATURE_SEARCH_FOR_DEVICE = "SDEV";
    public static final String LOGGING_FEATURE_STOP_AUTO_CONNECTION = "STAC";
    public static final String LOGGING_FEATURE_USING_TIME = "USTM";
    public static final String LOGGING_INTENT = "com.sec.android.screensharing.LOGGING";
    public static final String LOGGING_TYPE_CHANGE_DEVICE = "CHA_DEV";
    public static final String LOGGING_TYPE_FLOATING_ICON = "FIC_ICON";
    public static final String LOGGING_TYPE_MOBILE_TO_TV = "MOBILE_TO_TV";
    public static final String LOGGING_TYPE_MUSIC = "MUSIC";
    public static final String LOGGING_TYPE_QUICK_PANNEL = "QIC_PAN";
    public static final String LOGGING_TYPE_QUICK_SETTING = "QIC_SET";
    public static final String LOGGING_TYPE_SHARE_PANNEL = "SHA_PAN";
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_MUSIC = 2;
    public static final int TYPE_MUSIC_CHN = 3;
    public static final int TYPE_NONE = -1;
    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_VIDEO_HEVC_SUPER_SLOW_MOTION = 4;
    public static final boolean WFD_SERVICE_WITH_GOOGLE_CAST;

    static {
        WFD_SERVICE_WITH_GOOGLE_CAST = Build.VERSION.SEM_PLATFORM_INT >= 80100;
    }

    private SemScreenSharingConstants() {}
}
