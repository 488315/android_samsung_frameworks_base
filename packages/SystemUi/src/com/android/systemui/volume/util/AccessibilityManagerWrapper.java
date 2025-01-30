package com.android.systemui.volume.util;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AccessibilityManagerWrapper {
    public final Context context;

    public AccessibilityManagerWrapper(Context context) {
        this.context = context;
    }

    public final int getRecommendedTimeoutMillis(int i) {
        SystemServiceExtension.INSTANCE.getClass();
        return ((AccessibilityManager) this.context.getSystemService(AccessibilityManager.class)).getRecommendedTimeoutMillis(0, i);
    }
}
