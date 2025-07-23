package com.android.systemui.util;

import android.app.ActivityTaskManager;
import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActivityTaskManagerProxy {
    public static final int $stable = 0;

    public final boolean supportsMultiWindow(Context context) {
        return ActivityTaskManager.supportsMultiWindow(context);
    }
}
