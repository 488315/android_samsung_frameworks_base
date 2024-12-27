package com.android.server.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

class TalkbackShortcutController {
    public final Context mContext;
    public final PackageManager mPackageManager;

    public TalkbackShortcutController(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public final boolean isTalkBackShortcutGestureEnabled() {
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(),
                        "wear_accessibility_gesture_enabled",
                        0,
                        -2)
                == 1;
    }
}
