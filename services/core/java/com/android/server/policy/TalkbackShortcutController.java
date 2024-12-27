package com.android.server.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class TalkbackShortcutController {
    public final Context mContext;
    public final PackageManager mPackageManager;

    public TalkbackShortcutController(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public final boolean isTalkBackShortcutGestureEnabled() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "wear_accessibility_gesture_enabled", 0, -2) == 1;
    }
}
