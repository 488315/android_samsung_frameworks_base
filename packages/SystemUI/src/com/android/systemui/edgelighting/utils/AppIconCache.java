package com.android.systemui.edgelighting.utils;

import android.content.Context;
import android.util.LruCache;

public final class AppIconCache {
    public final Context mContext;
    public final LruCache mIconCache = new LruCache(7);
    public final String KEY_SMALL_ICON = "smallIcon";

    public AppIconCache(Context context) {
        this.mContext = context;
    }
}
