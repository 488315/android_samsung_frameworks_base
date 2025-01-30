package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.DisplayManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemServiceExtension {
    public static final SystemServiceExtension INSTANCE = new SystemServiceExtension();

    private SystemServiceExtension() {
    }

    public static DisplayManager getDisplayManager(Context context) {
        return (DisplayManager) context.getSystemService(DisplayManager.class);
    }
}
