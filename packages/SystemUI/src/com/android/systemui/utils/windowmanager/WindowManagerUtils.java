package com.android.systemui.utils.windowmanager;

import android.content.Context;
import android.view.WindowManager;

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
