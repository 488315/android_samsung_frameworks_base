package com.samsung.android.sdk.scs.base.feature;

import android.content.Context;
import android.os.Build;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Feature {
    public static Boolean sIsVst;
    public static Boolean sIsWatch;
    public static final Map sinceVersionMap;
    public static final List SUPPORTED_SIVS_FEATURES =
            Arrays.asList(
                    "FEATURE_SPEECH_RECOGNITION",
                    "FEATURE_SPEECH_LOCALE_RECOGNITION",
                    "FEATURE_SPEAKER_DIARISATION",
                    "FEATURE_AUDIO_TO_TRANSLATION",
                    "FEATURE_AI_GEN_SUMMARY",
                    "FEATURE_AI_GEN_TRANSLATION",
                    "FEATURE_AI_GEN_TONE",
                    "FEATURE_AI_GEN_CORRECTION",
                    "FEATURE_AI_GEN_SUGGESTION",
                    "FEATURE_AI_GEN_SUGGESTION_ONDEVICE",
                    "FEATURE_AI_GEN_SMART_COVER",
                    "FEATURE_AI_GEN_SMART_REPLY",
                    "FEATURE_AI_GEN_EMOJI_AUGMENTATION",
                    "FEATURE_AI_GEN_NOTES_ORGANIZATION",
                    "FEATURE_AI_GEN_SMART_CAPTURE",
                    "FEATURE_AI_GEN_GENERIC",
                    "FEATURE_AI_GEN_USAGE",
                    "FEATURE_NEURAL_TRANSLATION",
                    "FEATURE_LANGUAGE_LIST_IDENTIFICATION",
                    "FEATURE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE",
                    "FEATURE_NEURAL_TRANSLATION_BY_CHUNK",
                    "FEATURE_NEURAL_TRANSLATION_CLEAR_WITH_SOURCE_ID",
                    "FEATURE_NEURAL_TRANSLATION_TAG_SUPPORTED",
                    "FEATURE_SIVS_CLASSIFICATION",
                    "FEATURE_SIVS_CONFIGURATION",
                    "FEATURE_SIVS_EXTRACTION",
                    "FEATURE_SIVS_EXTRACTION_ONDEVICE",
                    "FEATURE_SIVS_WRITING_COMPOSER",
                    "FEATURE_SIVS_WRITING_COMPOSER_ONDEVICE",
                    "FEATURE_SIVS_FORMAT_CONVERSION",
                    "FEATURE_AI_LANGUAGE_PACK_CONFIGURATION_PROVIDER");
    public static final List SUPPORTED_VISUAL_FEATURES =
            Arrays.asList(
                    "FEATURE_DOWNLOAD",
                    "FEATURE_WALLPAPER",
                    "FEATURE_GEN_EDIT_ON_DEVICE",
                    "FEATURE_PORTRAIT_ON_DEVICE",
                    "FEATURE_SKETCH_TO_IMAGE_ON_DEVICE",
                    "FEATURE_SKETCH_GUIDE_EDITING_ON_DEVICE",
                    "FEATURE_PORTRAIT_RELIGHT_ON_DEVICE");
    public static final List SUPPORTED_VISUAL_CLOUD_FEATURES =
            Arrays.asList(
                    "FEATURE_PORTRAIT",
                    "FEATURE_SKETCH_TO_IMAGE",
                    "FEATURE_SKETCH_GUIDE_EDITING",
                    "FEATURE_SKETCH_GUIDE_EDITING_CROPPING_RECT",
                    "FEATURE_C2PA",
                    "FEATURE_GEN_STICKER",
                    "FEATURE_IMAGE_CONVERSION");

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("FEATURE_IMAGE_GET_BOUNDARIES", 1);
        hashMap.put("FEATURE_IMAGE_GET_LARGEST_BOUNDARY", 1);
        hashMap.put("FEATURE_IMAGE_UPSCALE", 2);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_KEYWORD", 1);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY", 2);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY_DETAILS", 3);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_FOLDER_NAME", 2);
        hashMap.put("FEATURE_TEXT_GET_ENTITY", 2);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_BULK", 24);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_DATETIME_NUMERAL", 9);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_DATETIME_SEARCH", 22);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_PHONE_NUMBER", 9);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_POI", 10);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_BANK", 9);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_MAPPABLE", 15);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_RELATIVE", 15);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_SPECIAL_DAY", 17);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_HAS_YEAR_MONTH_DAY", 18);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_UPI_ID", 16);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_UNIT", 20);
        hashMap.put("FEATURE_TEXT_GET_EVENT", 13);
        hashMap.put("FEATURE_TEXT_GET_EVENT_HAS_YEAR_MONTH_DAY", 18);
        hashMap.put("FEATURE_TEXT_GET_EVENT_INDEX", 21);
        hashMap.put("FEATURE_TEXT_GET_KEY_PHRASE", 3);
        hashMap.put("FEATURE_TEXT_GET_KEY_PHRASE_EVENT_TITLE", 11);
        hashMap.put("FEATURE_TEXT_GET_DOCUMENT_CATEGORY", 5);
        hashMap.put("FEATURE_TEXT_GET_BNLP", 2);
        hashMap.put("FEATURE_TEXT_GET_BNLP_TOKEN", 12);
        hashMap.put("FEATURE_TEXT_DETECT_LANGUAGE", 9);
        hashMap.put("FEATURE_TEXT_CONVERT_UNIT", 19);
        hashMap.put("FEATURE_TEXT_GET_REMINDER_ENTITY", 23);
        hashMap.put("FEATURE_NATURAL_LANGUAGE_QUERY", 1);
        hashMap.put("FEATURE_SPEECH_RECOGNITION", 1);
        hashMap.put("FEATURE_SPEECH_LOCALE_RECOGNITION", 7);
        hashMap.put("FEATURE_SPEAKER_DIARISATION", 5);
        hashMap.put("FEATURE_AUDIO_TO_TRANSLATION", 7);
        hashMap.put("FEATURE_AI_GEN_SUMMARY", 6);
        hashMap.put("FEATURE_AI_GEN_TRANSLATION", 6);
        hashMap.put("FEATURE_AI_GEN_TONE", 6);
        hashMap.put("FEATURE_AI_GEN_CORRECTION", 6);
        hashMap.put("FEATURE_AI_GEN_SUGGESTION", 7);
        hashMap.put("FEATURE_AI_GEN_SUGGESTION_ONDEVICE", 10);
        hashMap.put("FEATURE_AI_GEN_SMART_COVER", 6);
        hashMap.put("FEATURE_AI_GEN_SMART_REPLY", 6);
        hashMap.put("FEATURE_AI_GEN_EMOJI_AUGMENTATION", 6);
        hashMap.put("FEATURE_AI_GEN_NOTES_ORGANIZATION", 6);
        hashMap.put("FEATURE_AI_GEN_SMART_CAPTURE", 6);
        hashMap.put("FEATURE_AI_GEN_GENERIC", 6);
        hashMap.put("FEATURE_NEURAL_TRANSLATION", 1);
        hashMap.put("FEATURE_LANGUAGE_LIST_IDENTIFICATION", 7);
        hashMap.put("FEATURE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE", 8);
        hashMap.put("FEATURE_NEURAL_TRANSLATION_BY_CHUNK", 11);
        hashMap.put("FEATURE_NEURAL_TRANSLATION_TAG_SUPPORTED", 10);
        hashMap.put("FEATURE_NEURAL_TRANSLATION_CLEAR_WITH_SOURCE_ID", 10);
        hashMap.put("FEATURE_SIVS_CLASSIFICATION", 6);
        hashMap.put("FEATURE_SIVS_EXTRACTION", 6);
        hashMap.put("FEATURE_SIVS_EXTRACTION_ONDEVICE", 10);
        hashMap.put("FEATURE_SIVS_WRITING_COMPOSER", 8);
        hashMap.put("FEATURE_SIVS_WRITING_COMPOSER_ONDEVICE", 10);
        hashMap.put("FEATURE_SIVS_FORMAT_CONVERSION", 10);
        hashMap.put("FEATURE_SIVS_CONFIGURATION", 6);
        hashMap.put("FEATURE_AI_GEN_USAGE", 6);
        hashMap.put("FEATURE_AI_LANGUAGE_PACK_CONFIGURATION_PROVIDER", 9);
        hashMap.put("FEATURE_WALLPAPER", 1);
        hashMap.put("FEATURE_DOWNLOAD", 1);
        hashMap.put("FEATURE_PORTRAIT", 1);
        hashMap.put("FEATURE_SKETCH_TO_IMAGE", 1);
        hashMap.put("FEATURE_SKETCH_GUIDE_EDITING", 1);
        hashMap.put("FEATURE_SKETCH_GUIDE_EDITING_CROPPING_RECT", 2);
        hashMap.put("FEATURE_C2PA", 3);
        hashMap.put("FEATURE_GEN_STICKER", 6);
        hashMap.put("FEATURE_IMAGE_CONVERSION", 6);
        hashMap.put("FEATURE_GEN_EDIT_ON_DEVICE", 4);
        hashMap.put("FEATURE_SKETCH_TO_IMAGE_ON_DEVICE", 5);
        sinceVersionMap = Collections.unmodifiableMap(hashMap);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int checkFeature(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 553
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.sdk.scs.base.feature.Feature.checkFeature(android.content.Context):int");
    }

    public static boolean isSIVSAvailableOSVersion(Context context) {
        try {
            if (!isWatch(context) && !isVst(context)) {
                return Build.VERSION.SEM_PLATFORM_INT >= 150100;
            }
            return Build.VERSION.SEM_PLATFORM_INT >= 150000;
        } catch (Exception | NoSuchFieldError unused) {
            return false;
        }
    }

    public static boolean isVst(Context context) {
        Boolean bool = sIsVst;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Boolean valueOf =
                    Boolean.valueOf(
                            context.getPackageManager()
                                    .hasSystemFeature("android.software.xr.immersive"));
            sIsVst = valueOf;
            return valueOf.booleanValue();
        } catch (Error | Exception unused) {
            return false;
        }
    }

    public static boolean isWatch(Context context) {
        Boolean bool = sIsWatch;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Boolean valueOf =
                    Boolean.valueOf(
                            context.getPackageManager()
                                    .hasSystemFeature("android.hardware.type.watch"));
            sIsWatch = valueOf;
            return valueOf.booleanValue();
        } catch (Error | Exception unused) {
            return false;
        }
    }
}
