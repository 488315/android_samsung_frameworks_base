package com.android.systemui.volume.util;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import kotlin.jvm.internal.Intrinsics;

public final class AccessibilityManagerWrapper {
    public final Context context;

    public AccessibilityManagerWrapper(Context context) {
        this.context = context;
    }

    public final int getRecommendedTimeoutMillis(int i) {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Object systemService = context.getSystemService((Class<Object>) AccessibilityManager.class);
        Intrinsics.checkNotNull(systemService);
        return ((AccessibilityManager) systemService).getRecommendedTimeoutMillis(0, i);
    }
}
