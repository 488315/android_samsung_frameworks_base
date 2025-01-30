package com.samsung.android.sdk.scs.base.feature;

import com.samsung.android.sivs.p049ai.sdkcommon.asr.SpeechRecognitionConst;
import com.samsung.android.sivs.p049ai.sdkcommon.language.LlmServiceConst;
import com.samsung.android.sivs.p049ai.sdkcommon.translation.TranslationConst;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Feature {
    public static final Map sinceVersionMap;
    public static final List SUPPORTED_SIVS_FEATURES = Arrays.asList("FEATURE_SPEECH_RECOGNITION", "FEATURE_SPEAKER_DIARISATION", "FEATURE_AI_GEN_SUMMARY", "FEATURE_AI_GEN_TRANSLATION", "FEATURE_AI_GEN_TONE", "FEATURE_AI_GEN_CORRECTION", "FEATURE_AI_GEN_SMART_COVER", "FEATURE_AI_GEN_SMART_REPLY", "FEATURE_AI_GEN_EMOJI_AUGMENTATION", "FEATURE_AI_GEN_NOTES_ORGANIZATION", "FEATURE_AI_GEN_SMART_CAPTURE", "FEATURE_AI_GEN_GENERIC", "FEATURE_AI_GEN_USAGE", "FEATURE_NEURAL_TRANSLATION", "FEATURE_LANGUAGE_LIST_IDENTIFICATION", "FEATURE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE", "FEATURE_SIVS_CLASSIFICATION", "FEATURE_SIVS_CONFIGURATION", "FEATURE_SIVS_EXTRACTION");
    public static final List SUPPORTED_SBIS_FEATURES = Arrays.asList("FEATURE_AI_LEX_RANK");

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
        hashMap.put("FEATURE_TEXT_GET_ENTITY_DATETIME_NUMERAL", 9);
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
        hashMap.put("FEATURE_NATURAL_LANGUAGE_QUERY", 1);
        hashMap.put("FEATURE_SPEECH_RECOGNITION", SpeechRecognitionConst.SINCE_SPEECH_RECOGNITION);
        hashMap.put("FEATURE_SPEAKER_DIARISATION", SpeechRecognitionConst.SINCE_SPEAKER_DIARISATION);
        hashMap.put("FEATURE_AI_GEN_SUMMARY", LlmServiceConst.SINCE_AI_SUMMARY);
        hashMap.put("FEATURE_AI_GEN_TRANSLATION", LlmServiceConst.SINCE_AI_TRANSLATION);
        hashMap.put("FEATURE_AI_GEN_TONE", LlmServiceConst.SINCE_AI_TONE);
        hashMap.put("FEATURE_AI_GEN_CORRECTION", LlmServiceConst.SINCE_AI_CORRECTION);
        hashMap.put("FEATURE_AI_GEN_SMART_COVER", LlmServiceConst.SINCE_AI_SMART_COVER);
        hashMap.put("FEATURE_AI_GEN_SMART_REPLY", LlmServiceConst.SINCE_AI_SMART_REPLY);
        hashMap.put("FEATURE_AI_GEN_EMOJI_AUGMENTATION", LlmServiceConst.SINCE_AI_EMOJI_AUGMENTATION);
        hashMap.put("FEATURE_AI_GEN_NOTES_ORGANIZATION", LlmServiceConst.SINCE_AI_NOTES_ORGANIZATION);
        hashMap.put("FEATURE_AI_GEN_SMART_CAPTURE", LlmServiceConst.SINCE_AI_SMART_CAPTURE);
        hashMap.put("FEATURE_AI_GEN_GENERIC", LlmServiceConst.SINCE_AI_GENERIC);
        hashMap.put("FEATURE_NEURAL_TRANSLATION", TranslationConst.SINCE_NEURAL_TRANSLATION);
        hashMap.put("FEATURE_LANGUAGE_LIST_IDENTIFICATION", TranslationConst.SINCE_LANGUAGE_LIST_IDENTIFICATION);
        hashMap.put("FEATURE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE", TranslationConst.SINCE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE);
        hashMap.put("FEATURE_SIVS_CLASSIFICATION", LlmServiceConst.SINCE_SIVS_CLASSIFICATION);
        hashMap.put("FEATURE_SIVS_EXTRACTION", LlmServiceConst.SINCE_SIVS_EXTRACTION);
        hashMap.put("FEATURE_SIVS_CONFIGURATION", LlmServiceConst.SINCE_SIVS_CONFIGURATION);
        hashMap.put("FEATURE_AI_LEX_RANK", 1);
        hashMap.put("FEATURE_AI_GEN_USAGE", LlmServiceConst.SINCE_AI_USAGE);
        sinceVersionMap = Collections.unmodifiableMap(hashMap);
    }
}
