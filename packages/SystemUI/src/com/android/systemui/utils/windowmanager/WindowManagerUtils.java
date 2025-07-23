package com.android.systemui.utils.windowmanager;

import android.content.Context;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowManagerUtils {
    static {
        new WindowManagerUtils();
    }

    private WindowManagerUtils() {
    }

    public static final WindowManager getWindowManager(Context context) {
        Object systemService = context.getSystemService((Class<Object>) WindowManager.class);
        systemService.getClass();
        return (WindowManager) systemService;
    }
}
