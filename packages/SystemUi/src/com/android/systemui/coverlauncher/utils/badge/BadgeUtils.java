package com.android.systemui.coverlauncher.utils.badge;

import android.content.Context;
import android.net.Uri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BadgeUtils {
    public static final Uri BADGE_URI = Uri.parse("content://com.sec.badge/apps");
    public static final String[] COLUMNS = {"package", "class", "badgecount"};
    public final Context mContext;

    public BadgeUtils(Context context) {
        this.mContext = context;
    }
}
