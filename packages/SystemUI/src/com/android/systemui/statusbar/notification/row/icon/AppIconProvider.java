package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.util.Collection;

/* loaded from: classes3.dex */
public interface AppIconProvider {
    Drawable getOrFetchAppIcon(Context context, String str, boolean z);

    void purgeCache(Collection collection);
}
