package android.stats.mediametrics;

public final class Mediametrics {
    public static final int ABANDONED = 15;
    public static final int AUDIO = 0;
    public static final int BUFFERING = 6;
    public static final int CONTENT_TYPE_AD = 2;
    public static final int CONTENT_TYPE_MAIN = 1;
    public static final int CONTENT_TYPE_OTHER = 3;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final int DRM_TYPE_NONE = 0;
    public static final int DRM_TYPE_OTHER = 1;
    public static final int DRM_TYPE_PLAY_READY = 2;
    public static final int DRM_TYPE_WV_L1 = 3;
    public static final int DRM_TYPE_WV_L3 = 4;
    public static final int ENCODED_SURROUND_OUTPUT_ALWAYS = 3;
    public static final int ENCODED_SURROUND_OUTPUT_AUTO = 1;
    public static final int ENCODED_SURROUND_OUTPUT_MANUAL = 4;
    public static final int ENCODED_SURROUND_OUTPUT_NEVER = 2;
    public static final int ENCODED_SURROUND_OUTPUT_UNKNOWN = 0;
    public static final int ENCODING_AAC_ELD = 15;
    public static final int ENCODING_AAC_HE_V1 = 11;
    public static final int ENCODING_AAC_HE_V2 = 12;
    public static final int ENCODING_AAC_LC = 10;
    public static final int ENCODING_AAC_XHE = 16;
    public static final int ENCODING_AC3 = 5;
    public static final int ENCODING_AC4 = 17;
    public static final int ENCODING_DEFAULT = 1;
    public static final int ENCODING_DOLBY_MAT = 19;
    public static final int ENCODING_DOLBY_TRUEHD = 14;
    public static final int ENCODING_DRA = 28;
    public static final int ENCODING_DTS = 7;
    public static final int ENCODING_DTS_HD = 8;
    public static final int ENCODING_DTS_UHD = 27;
    public static final int ENCODING_E_AC3 = 6;
    public static final int ENCODING_E_AC3_JOC = 18;
    public static final int ENCODING_IEC61937 = 13;
    public static final int ENCODING_INVALID = 0;
    public static final int ENCODING_MP3 = 9;
    public static final int ENCODING_MPEGH_BL_L3 = 23;
    public static final int ENCODING_MPEGH_BL_L4 = 24;
    public static final int ENCODING_MPEGH_LC_L3 = 25;
    public static final int ENCODING_MPEGH_LC_L4 = 26;
    public static final int ENCODING_OPUS = 20;
    public static final int ENCODING_PCM_16BIT = 2;
    public static final int ENCODING_PCM_24BIT_PACKED = 21;
    public static final int ENCODING_PCM_32BIT = 22;
    public static final int ENCODING_PCM_8BIT = 3;
    public static final int ENCODING_PCM_FLOAT = 4;
    public static final int ENDED = 11;
    public static final int ERROR_CODE_AUDIOTRACK_INIT = 17;
    public static final int ERROR_CODE_AUDIOTRACK_OTHER = 19;
    public static final int ERROR_CODE_AUDIOTRACK_WRITE = 18;
    public static final int ERROR_CODE_DECODER_DECODE = 14;
    public static final int ERROR_CODE_DECODER_INIT = 13;
    public static final int ERROR_CODE_DECODER_OOM = 15;
    public static final int ERROR_CODE_DECODER_OTHER = 16;
    public static final int ERROR_CODE_DRM_CONTENT_ERROR = 28;
    public static final int ERROR_CODE_DRM_DISALLOWED = 26;
    public static final int ERROR_CODE_DRM_LICENSE_ERROR = 25;
    public static final int ERROR_CODE_DRM_OTHER = 30;
    public static final int ERROR_CODE_DRM_PROVISIONING_FAILED = 24;
    public static final int ERROR_CODE_DRM_REVOKED = 39;
    public static final int ERROR_CODE_DRM_SYSTEM_ERROR = 27;
    public static final int ERROR_CODE_DRM_UNAVAILABLE = 23;
    public static final int ERROR_CODE_MEDIA_MANIFET = 10;
    public static final int ERROR_CODE_MEDIA_OTHER = 12;
    public static final int ERROR_CODE_MEDIA_PARSER = 11;
    public static final int ERROR_CODE_NETWORK_BAD_STATUS = 5;
    public static final int ERROR_CODE_NETWORK_CLOSED = 8;
    public static final int ERROR_CODE_NETWORK_CONNECT = 4;
    public static final int ERROR_CODE_NETWORK_DNS = 6;
    public static final int ERROR_CODE_NETWORK_OFFLINE = 3;
    public static final int ERROR_CODE_NETWORK_OTHER = 9;
    public static final int ERROR_CODE_NETWORK_TIMEOUT = 7;
    public static final int ERROR_CODE_OTHER = 1;
    public static final int ERROR_CODE_PLAYER_BEHIND_LIVE_WINDOW = 21;
    public static final int ERROR_CODE_PLAYER_OTHER = 22;
    public static final int ERROR_CODE_PLAYER_REMOTE = 20;
    public static final int ERROR_CODE_RUNTIME = 2;
    public static final int ERROR_CODE_UNKNOWN = 0;
    public static final int FAILED = 13;
    public static final int HDR_TYPE_DOLBY_VISION = 1;
    public static final int HDR_TYPE_HDR10 = 2;
    public static final int HDR_TYPE_HDR10_PLUS = 4;
    public static final int HDR_TYPE_HLG = 3;
    public static final int HDR_TYPE_UNKNOWN = 0;
    public static final int INTERRUPTED_BY_AD = 14;
    public static final int JOINING_BACKGROUND = 1;
    public static final int JOINING_FOREGROUND = 2;
    public static final int MATCH_CONTENT_FRAMERATE_ALWAYS = 3;
    public static final int MATCH_CONTENT_FRAMERATE_NEVER = 1;
    public static final int MATCH_CONTENT_FRAMERATE_SEAMLESSS_ONLY = 2;
    public static final int MATCH_CONTENT_FRAMERATE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_2G = 4;
    public static final int NETWORK_TYPE_3G = 5;
    public static final int NETWORK_TYPE_4G = 6;
    public static final int NETWORK_TYPE_5G_NSA = 7;
    public static final int NETWORK_TYPE_5G_SA = 8;
    public static final int NETWORK_TYPE_ETHERNET = 3;
    public static final int NETWORK_TYPE_OFFLINE = 9;
    public static final int NETWORK_TYPE_OTHER = 1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 2;
    public static final int NOT_STARTED = 0;
    public static final int OFF = 0;
    public static final int ON = 1;
    public static final int PAUSED = 4;
    public static final int PAUSED_BUFFERING = 7;
    public static final int PLAYBACK_TYPE_LIVE = 2;
    public static final int PLAYBACK_TYPE_OTHER = 3;
    public static final int PLAYBACK_TYPE_UNKNOWN = 0;
    public static final int PLAYBACK_TYPE_VOD = 1;
    public static final int PLAYING = 3;
    public static final int REASON_ADAPTIVE = 4;
    public static final int REASON_INITIAL = 2;
    public static final int REASON_MANUAL = 3;
    public static final int REASON_OTHER = 1;
    public static final int REASON_UNKNOWN = 0;
    public static final int SEEKING = 5;
    public static final int STOPPED = 12;
    public static final int STREAM_SOURCE_DEVICE = 2;
    public static final int STREAM_SOURCE_MIXED = 3;
    public static final int STREAM_SOURCE_NETWORK = 1;
    public static final int STREAM_SOURCE_UNKNOWN = 0;
    public static final int STREAM_TYPE_DASH = 3;
    public static final int STREAM_TYPE_HLS = 4;
    public static final int STREAM_TYPE_OTHER = 1;
    public static final int STREAM_TYPE_PROGRESSIVE = 2;
    public static final int STREAM_TYPE_SS = 5;
    public static final int STREAM_TYPE_UNKNOWN = 0;
    public static final int SUPPRESSED = 9;
    public static final int SUPPRESSED_BUFFERING = 10;
    public static final int TEXT = 2;
    public static final int VIDEO = 1;
}
