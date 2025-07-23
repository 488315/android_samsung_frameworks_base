package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.DisplayManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemServiceExtension {
    public static final SystemServiceExtension INSTANCE = new SystemServiceExtension();

    private SystemServiceExtension() {
    }

    public static DisplayManager getDisplayManager(Context context) {
        Object systemService = context.getSystemService((Class<Object>) DisplayManager.class);
        systemService.getClass();
        return (DisplayManager) systemService;
    }
}
