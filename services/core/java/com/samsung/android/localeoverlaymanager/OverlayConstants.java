package com.samsung.android.localeoverlaymanager;

import java.util.HashMap;
import java.util.Map;

public abstract class OverlayConstants {
    public static final Map ISO_639_2_TO_639_1_MAPPING;
    public static final Map SPECIAL_LOCALE_CODES_EQUIVALENTS;

    static {
        HashMap hashMap = new HashMap();
        ISO_639_2_TO_639_1_MAPPING = hashMap;
        HashMap hashMap2 = new HashMap();
        SPECIAL_LOCALE_CODES_EQUIVALENTS = hashMap2;
        hashMap.put("fil", "tl");
        hashMap2.put("he", "iw");
        hashMap2.put("id", "in");
        hashMap2.put("yi", "ji");
    }
}
