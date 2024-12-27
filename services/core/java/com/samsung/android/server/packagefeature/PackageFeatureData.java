package com.samsung.android.server.packagefeature;

import java.util.concurrent.ConcurrentHashMap;

public class PackageFeatureData extends ConcurrentHashMap {
    public static final String EMPTY_STRING = new String();

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public final String put(String str, String str2) {
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = EMPTY_STRING;
        }
        return (String) super.put((PackageFeatureData) str, str2);
    }
}
