package com.samsung.android.wallpaper.utils;

import android.os.Bundle;

public class WallpaperExtraBundleHelper {
    private static final String TAG = WallpaperExtraBundleHelper.class.getSimpleName();

    public static Bundle fromJson(String jsonStr) {
        return new BundleAndJsonConverter().convertJsonToBundle(jsonStr);
    }

    public static String toJson(Bundle bundle) {
        return new BundleAndJsonConverter().convertBundleToJson(bundle);
    }

    public static Bundle cloneBundle(Bundle srcBundle) {
        return fromJson(toJson(srcBundle));
    }
}
