package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.DisplayManager;
import kotlin.jvm.internal.Intrinsics;

public final class SystemServiceExtension {
    public static final SystemServiceExtension INSTANCE = new SystemServiceExtension();

    private SystemServiceExtension() {
    }

    public static DisplayManager getDisplayManager(Context context) {
        Object systemService = context.getSystemService((Class<Object>) DisplayManager.class);
        Intrinsics.checkNotNull(systemService);
        return (DisplayManager) systemService;
    }
}
