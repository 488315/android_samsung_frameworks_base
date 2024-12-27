package com.android.systemui.util;

import android.app.ActivityTaskManager;
import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityTaskManagerProxy {
    public static final int $stable = 0;

    public final boolean supportsMultiWindow(Context context) {
        return ActivityTaskManager.supportsMultiWindow(context);
    }
}
