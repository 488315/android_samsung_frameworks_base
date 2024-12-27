package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.DisplayManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
