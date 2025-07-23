package com.samsung.android.wallpaper.utils;

import android.os.Bundle;

/* loaded from: classes6.dex */
public class WallpaperExtraBundleHelper {
    private static final String TAG = "WallpaperExtraBundleHelper";

    public static Bundle fromJson(String str) {
        return new BundleAndJsonConverter().convertJsonToBundle(str);
    }

    public static String toJson(Bundle bundle) {
        return new BundleAndJsonConverter().convertBundleToJson(bundle);
    }

    public static Bundle cloneBundle(Bundle bundle) {
        return fromJson(toJson(bundle));
    }
}
