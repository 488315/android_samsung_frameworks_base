package com.android.systemui.util;

import android.app.ActivityTaskManager;
import android.content.Context;

public final class ActivityTaskManagerProxy {
    public static final int $stable = 0;

    public final boolean supportsMultiWindow(Context context) {
        return ActivityTaskManager.supportsMultiWindow(context);
    }
}
