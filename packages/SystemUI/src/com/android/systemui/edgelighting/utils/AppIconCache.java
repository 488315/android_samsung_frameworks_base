package com.android.systemui.edgelighting.utils;

import android.content.Context;
import android.util.LruCache;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class AppIconCache {
    public final Context mContext;
    public final LruCache mIconCache = new LruCache(7);
    public final String KEY_SMALL_ICON = "smallIcon";

    public AppIconCache(Context context) {
        this.mContext = context;
    }
}
