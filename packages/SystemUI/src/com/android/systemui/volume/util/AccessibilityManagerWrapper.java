package com.android.systemui.volume.util;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        systemService.getClass();
        return ((AccessibilityManager) systemService).getRecommendedTimeoutMillis(0, i);
    }
}
