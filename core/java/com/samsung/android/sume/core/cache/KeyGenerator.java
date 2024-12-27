package com.samsung.android.sume.core.cache;

import android.telecom.Logging.Session;

public class KeyGenerator {
    public static String getSimpleKey(String token) {
        return token.replaceAll("[:/]", Session.SESSION_SEPARATION_CHAR_CHILD);
    }
}
