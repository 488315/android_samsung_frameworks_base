package com.android.systemui.edgelighting.utils;

import android.content.Context;
import android.util.LruCache;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppIconCache {
    public final Context mContext;
    public final LruCache mIconCache = new LruCache(7);
    public final String KEY_SMALL_ICON = "smallIcon";

    public AppIconCache(Context context) {
        this.mContext = context;
    }
}
