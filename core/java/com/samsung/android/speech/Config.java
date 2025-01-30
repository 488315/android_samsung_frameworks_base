package com.samsung.android.speech;

import android.content.Context;
import android.service.notification.ZenModeConfig;
import android.telecom.Logging.Session;
import com.samsung.android.content.smartclip.SemSmartClipDataRepository;

/* loaded from: classes5.dex */
public class Config {
    public static final int CMD_ALARM = 3;
    public static final int CMD_CALL = 2;
    public static final int CMD_CAMERA = 7;
    public static final int CMD_CANCEL = 9;
    public static final int CMD_GALLERY = 8;
    public static final int CMD_MUSIC = 4;
    public static final int CMD_RADIO = 5;
    public static final int CMD_VIDEO = 6;
    public static final int CMD_VOICETALK_ALL = 0;
    public static final int CMD_VOICETALK_SCHEDULE = 1;
    public static final int CMD_YES_NO = 10;
    public static final int COUNT_DOMAIN = 11;
    public static final int COUNT_LANGUAGE = 15;
    public static final String DEFAULT_EXTRA_LANG_PATH = "/system/voicecommanddata/include/";
    public static final String DEFAULT_OEM_PATH = "/system/voicecommanddata/oem/";
    public static final String DEFAULT_PATH = "/system/voicecommanddata";
    public static final String DEFAULT_PDT_PATH = "/system/voicecommanddata/samsung/";
    public static final String DEFAULT_SAMSUNG_PATH = "/system/voicecommanddata/sasr/";
    public static final int LANGUAGE_BRAZIL_PORTUGUEE = 9;
    public static final int LANGUAGE_EU_FRENCH = 4;
    public static final int LANGUAGE_EU_GERMAN = 5;
    public static final int LANGUAGE_EU_ITALIAN = 6;
    public static final int LANGUAGE_EU_SPAINISH = 3;
    public static final int LANGUAGE_HK_CHINESE = 13;
    public static final int LANGUAGE_JAPANESE = 7;
    public static final int LANGUAGE_KOREAN = 0;
    public static final int LANGUAGE_RUSSIAN = 8;
    public static final int LANGUAGE_SG_CHINESE = 14;
    public static final int LANGUAGE_TRADITIONAL_CHINESE = 2;
    public static final int LANGUAGE_TW_CHINESE = 12;
    public static final int LANGUAGE_UK_ENGLISH = 10;
    public static final int LANGUAGE_US_ENGLISH = 1;
    public static final int LANGUAGE_US_SPAINISH = 11;
    public static final String OEM_MAIN_SUFFIX = ".bin";
    public static final String OEM_SO_FILE_PATH = "/system/lib/libOemBargeInEngine.so";
    public static final String OEM_SO_FILE_PATH_64 = "/system/lib64/libOemBargeInEngine.so";
    public static final String OEM_SUB_SUFFIX = ".bin";
    public static final String PDT_MAIN_SUFFIX = ".bin";
    public static final String PDT_SO_FILE_PATH = "/system/lib/libVoiceCommandEngine.so";
    public static final String PDT_SO_FILE_PATH_64 = "/system/lib64/libVoiceCommandEngine.so";
    public static final String SAMSUNG_SO_FILE_PATH = "/system/lib/libsasr-jni.so";
    public static final String VERSION = "18.11.13";
    private static final String[] MODELS_SAMSUNG = {"models_16k_KOR.bin", "models_hci_daco.bin", "models_16k_CHI.bin", "models_16k_ESP.bin", "models_16k_FRA.bin", "models_16k_GER.bin", "models_16k_ITA.bin", "models_16k_JAPANESE_bi.bin", "models_16k_RUSSIAN_bi.bin", "models_hci_daco.bin", "models_hci_daco.bin", "models_16k_ESP.bin", "models_hci_daco.bin", "models_hci_daco.bin", "models_hci_daco.bin"};
    private static final String[] STRING_SAMSUNG = {"kor", "eng", "chi", "spa", "fra", "ger", "ita", "jap", "rus", "eng", "eng", "spa", "chi", "chi", "chi"};
    private static final String[] STRING_LANGUAGE = {"ko_kr", "en_us", "zh_cn", "es_es", "fr_fr", "de_de", "it_it", "ja_jp", "ru_ru", "pt_br", "en_uk", "es_la", "zh_tw", "zh_hk", "zh_sg"};
    private static final String[] STRING_DOMAIN = {"stop", ZenModeConfig.SCHEDULE_PATH, "call", "alarm", SemSmartClipDataRepository.CONTENT_TYPE_AUDIO, "radio", "video", Context.CAMERA_SERVICE, "gallery", "cancel", "yesno"};
    private static final String[] STRING_LOCALE = {"ko-KR", "en-US", "zh-CN", "es-ES", "fr-FR", "de-DE", "it-IT", "ja-JP", "ru-RU", "pt-BR", "en-UK", "es-LA", "zh-TW", "zh-HK", "zh-SG"};

    public static String GetPDTAM(int language, int domain) {
        if (language >= 15) {
            language = 1;
        }
        if (domain < 11) {
            String PDTModelLangauge = STRING_LANGUAGE[language];
            String PDTModelDomain = STRING_DOMAIN[domain];
            return DEFAULT_PDT_PATH + PDTModelLangauge + "/samsung_voicecommand_" + PDTModelDomain + Session.SESSION_SEPARATION_CHAR_CHILD + PDTModelLangauge;
        }
        return null;
    }

    public static String GetOEMAM(int language, int domain) {
        if (language >= 15) {
            language = 1;
        }
        if (domain < 11) {
            String OEMModelLangauge = STRING_LANGUAGE[language];
            String OEMModelDomain = STRING_DOMAIN[domain];
            return DEFAULT_OEM_PATH + OEMModelLangauge + "/samsung_voicecommand_" + OEMModelDomain + Session.SESSION_SEPARATION_CHAR_CHILD + OEMModelLangauge;
        }
        return null;
    }

    public static String GetSamsungPath(int language) {
        if (language >= 15) {
            language = 1;
        }
        return DEFAULT_SAMSUNG_PATH + STRING_SAMSUNG[language] + "/16k/";
    }

    public static String GetSamsungModels(int language) {
        if (language >= 15) {
            language = 1;
        }
        return GetSamsungPath(language) + "param/" + MODELS_SAMSUNG[language];
    }

    public static String GetSamsungNameList(int domain) {
        if (domain >= 11) {
            return null;
        }
        switch (domain) {
            case 0:
                return "nameList_voicetalk_all.txt";
            case 1:
                return "nameList_voicetalk_schedule.txt";
            default:
                return "nameList_" + STRING_DOMAIN[domain] + ".txt";
        }
    }

    public static String GetLocale(int language) {
        if (language >= 15) {
            language = 1;
        }
        return STRING_LOCALE[language];
    }
}
