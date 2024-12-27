package com.android.systemui.edgelighting.utils;

import android.content.Context;
import android.util.LruCache;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AppIconCache {
    public final Context mContext;
    public final LruCache mIconCache = new LruCache(7);
    public final String KEY_SMALL_ICON = "smallIcon";

    public AppIconCache(Context context) {
        this.mContext = context;
    }
}
