package com.samsung.android.sivs.p049ai.sdkcommon.asr;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SpeechRecognitionConst {
    public static final String SERVICE_BIND_ACTION = "android.intellivoiceservice.speech.RecognitionService";
    public static final String SERVICE_SYSTEM_BIND_ACTION = "android.intellivoiceservice.speech.RecognitionSystemService";
    public static final String SYSTEM_PERMISSION_BIND_SERVICE = "com.samsung.android.intellivoiceservice.ai.asr.permission.SYSTEM_BIND_SPEECH_RECOGNITION_SERVICE";
    public static final String SYSTEM_PERMISSION_QUERY_CP = "com.samsung.android.intellivoiceservice.ai.asr.permission.SYSTEM_SPEECH_RECOGNITION_SERVICE_CONFIG_PROVIDER";
    public static final String SYSTEM_URI_STRING = "com.samsung.android.intellivoiceservice.ai.speech2";
    public static final String URI_STRING = "com.samsung.android.intellivoiceservice.ai.speech";
    public static final Integer VERSION = 6;
    public static final Integer SINCE_SPEECH_RECOGNITION = 1;
    public static final Integer SINCE_SPEAKER_DIARISATION = 5;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Key {
        public static final String APP_SERVER_TYPE = "app_server_type";
        public static final String BTC_LOCALE_INFO_LIST = "btc_locale_info_list";
        public static final String CALLER_PACKAGE = "caller_package";
        public static final String CONNECTION_TYPE = "connection_type";
        public static final String DICTATION_TYPE = "dict_type";
        public static final String ENABLED_AUDIO_COMPRESSION = "enabled_audio_compression";
        public static final String ENABLED_PARTIAL = "enabled_partial";
        public static final String ENABLED_SPEAKER_DIARISATION = "enable_speaker_diarisation";
        public static final String ENABLE_CENSOR = "enabled_censor";
        public static final String ENABLE_PROGRESS = "enabled_progress";
        public static final String ERROR_CODE = "error_code";
        public static final String ERROR_MESSAGE = "error_message";
        public static final String IS_AVAILABLE = "is_available";
        public static final String LANGPACK_CONFIG_JSON = "langpack_config_json";
        public static final String LOCALE = "locale";
        public static final String LOCALE_INFO_LIST = "locale_info_list";
        public static final String LOCALE_LIST = "locale_list";
        public static final String META_SUPPORTED_LOCAL_LOCALES = "local_supported_locales";
        public static final String META_SUPPORTED_NETWORK_LOCALES = "network_supported_locales";
        public static final String RESULT = "result";
        public static final String RESULT_DIALOG_INFO = "dialog_info";
        public static final String RESULT_EXT_HYPOTHESIS = "result_ext_hypothesis";
        public static final String RESULT_IMMUTABLE_SENTENCE = "result_immutable_sentence";
        public static final String RESULT_MUTABLE_SENTENCE = "result_mutable_sentence";
        public static final String RESULT_NL_TEXT = "nl_text";
        public static final String RESULT_NL_TIMING_INFO = "nl_timing_info";
        public static final String RESULT_RAW_TEXT = "result_rawtext";
        public static final String RESULT_SERVER_LISTS = "result_server_list";
        public static final String RESULT_TIMING_INFO = "timing_info";
        public static final String RET_PROGRESS = "ext_progress";
        public static final String RET_PROGRESS_EXTRA = "ext_progress_extra";
        public static final String SD_NOTIFY_INTERVAL_TIME = "sd_notify_interval_time";
        public static final String SD_RECORDING_TYPE = "sd_recording_type";
        public static final String SERVER_ASR_TOS_URL = "server_asr_tos_url";
        public static final String SERVER_INFO = "server_info";
        public static final String SERVER_TYPE = "server_type";
        public static final String SOURCE_SAMPLE_RATE = "source_sample_rate";
        public static final String TARGET_LANGPACK_RESOURCE_PACKAGE = "resource_package_name";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Method {
        public static final String CHECK_AVAILABILITY = "check_availability";
        public static final String CHECK_SPEAKER_DIARIZATION_LANGUAGE_SUPPORT = "check_speaker_diarization_language_support";
        public static final String GET_BTC_LOCALE_LIST = "get_btc_locale_list";
        public static final String GET_LANGPACK_CONFIG = "get_langpack_config";
        public static final String GET_LOCALE_LIST = "get_locale_list";
        public static final String GET_SERVER_ASR_TOS_URL = "get_server_asr_tos_url";
        public static final String GET_SERVER_LISTS = "get_server_list";
        public static final String GET_TARGET_LOCAL_RESOURCE_PACKAGE_NAME = "get_target_local_package_name";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public @interface ServerType {
        public static final int DEV_ENG = 3;
        public static final int DEV_INT = 2;
        public static final int PROD = 0;
        public static final int STG = 1;
    }
}
